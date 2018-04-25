package com.travischenn.platform.validcode.image;

import com.travischenn.platform.domain.VO.ImageCode;
import com.travischenn.platform.exception.ValidateCodeException;
import com.travischenn.platform.validcode.ValidCodeProcessor;
import com.travischenn.platform.handler.authentication.AuthenticationFailedHandler;
import com.travischenn.platform.properties.SecurityProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ImageCodeFilter
 * 功能描述    : 图片验证码过滤器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 15:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class ImageCodeFilter extends OncePerRequestFilter implements InitializingBean{

    /**
     * Spring Session 工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * Spring Security 默认错误处理工具类
     */
    @Getter
    @Setter
    private AuthenticationFailedHandler authenticationFailedHandler;

    /**
     * Spring 路径匹配工具
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * Spring Security 配置项
     */
    @Getter
    @Setter
    private SecurityProperties securityProperties;

    private Set<String> authImageCodeUriSet = new HashSet<>();

    /**
     * 在初始化所有配置项后进行路径参数初始化
     */
    @Override
    public void afterPropertiesSet() throws ServletException {

        super.afterPropertiesSet();

        //将路径分解成数组
        String[] authImageCodeUriArray = StringUtils.splitByWholeSeparator(securityProperties.getValidCode().getImageCode().getFilterUri(), ",");

        //将数组中的内容放入 Set 中
        authImageCodeUriSet.addAll(Arrays.asList(authImageCodeUriArray));

        //将默认的验证路径加入容器中
        authImageCodeUriSet.add("/authentication/form");

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求的 URL
        String redirectUrl = request.getRequestURI();

        // 将 Set 中的参数进行遍历比对
        boolean isFilter = false;
        for(String item : authImageCodeUriSet){
            if(pathMatcher.match(item , redirectUrl)){
                isFilter = true;
                break;
            }
        }

        //对请求进行拦截判断
        if(isFilter){
            try {
                // 进行验证码校验
                validImageCode(request);
            } catch (ValidateCodeException exception) {
                //交由SpringSecurity认证失败处理器处理
                authenticationFailedHandler.onAuthenticationFailure(request , response , exception);
                return;
            }
        }
        filterChain.doFilter(request , response);
    }

    /**
     * 校验验证码
     */
    private void validImageCode(HttpServletRequest request) throws ServletRequestBindingException, ValidateCodeException {

        // 获取 Session 中的验证码对象
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(new ServletWebRequest(request), ValidCodeProcessor.SESSION_KEY_PREFIX + "IMAGECODE");

        // 控制某些没有页面生成的验证码对象造成的空指针异常
        if(imageCode == null){
            throw new ValidateCodeException("验证码已过期 , 请重新生成");
        }

        // 获取 imageCode 中的验证码对象
        String imageCodeInSession = imageCode.getCode();

        // 获取 请求参数中的验证码
        String imageCodeInRequestParameter = ServletRequestUtils.getStringParameter(request, "imageCode");

        // 请求参数中用户名密码校验
        if(StringUtils.isBlank(imageCodeInRequestParameter)){
            throw new ValidateCodeException("验证码不能为空");
        }

        // Session 中用户名密码校验
        if(StringUtils.isBlank(imageCodeInSession)){
            throw new ValidateCodeException("验证码已过期请更换验证码");
        }

        // 判断验证码是否匹配
        if(!StringUtils.equals(imageCodeInSession , imageCodeInRequestParameter)){
            throw new ValidateCodeException("验证码不匹配");
        }

        // 判断验证码是否过期
        if(LocalDateTime.now().isAfter(imageCode.getExpireTime())){
            throw new ValidateCodeException("验证码已过期，请重新登录");
        }

        //验证通过移除 Session 中的验证码
        sessionStrategy.removeAttribute(new ServletWebRequest(request) , ValidCodeProcessor.SESSION_KEY_PREFIX + "IMAGECODE");

    }

}
