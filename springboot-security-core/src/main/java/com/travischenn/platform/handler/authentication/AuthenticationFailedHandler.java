package com.travischenn.platform.handler.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.enums.AuthRedirectType;
import com.travischenn.platform.enums.LoginEums;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 11:18
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Slf4j
@Component("travisChennAuthenticationFailedHandler")
public class AuthenticationFailedHandler extends SimpleUrlAuthenticationFailureHandler{

    /**
     * Spring JSON 转化工具
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.error("登录失败");

        // 获取认证结果处理方式
        AuthRedirectType authRedirectType = securityProperties.getBrowser().getAuthRedirectType();

        // 判断当前的响应结果 TODO 无法判断登录失败原因
        if(authRedirectType == AuthRedirectType.JSON){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResultBean<>(ResultEnum.FAILED , "登录失败")));
        }else if(authRedirectType == AuthRedirectType.REDIRECT){
            super.onAuthenticationFailure(request , response , exception);
        }
    }
}
