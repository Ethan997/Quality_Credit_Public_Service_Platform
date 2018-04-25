package com.travischenn.platform.handler.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travischenn.platform.domain.DO.Member;
import com.travischenn.platform.domain.VO.BaseMessage;
import com.travischenn.platform.enums.AuthRedirectType;
import com.travischenn.platform.enums.LoginEums;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.properties.SecurityProperties;
import com.travischenn.platform.repository.MemberRepository;
import com.travischenn.platform.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AuthenticationSuccessHandler
 * 功能描述    : 权限认证成功处理器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 11:00
 * Created    : IntelliJ IDEA
 * **************************************************************
 */

@Slf4j
@Component("travisChennAuthenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Spring JSON 转换工具
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 权限控制配置项
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 用户数据库操作类
     */
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("登录成功");

        // 修改用户 [ 最后登录时间 , 最后登录IP , 登录次数 ] ==========     ==========     ==========     ==========

        // 获取当前登录对象的用户名
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // 根据用户名获取用户实体
        Member member = memberRepository.findMemberByUsername(username);

        // 设置最后登录的IP地址
        member.setLastLoginIp(RequestUtil.getIpAddress(request));

        // 设置登录次数
        member.setLoginCount(String.valueOf(Integer.parseInt(member.getLoginCount()) + 1));

        // 同步用户更新时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        member.setLastLoginTime(simpleDateFormat.format(new Date()));

        // 同步用户数据
        memberRepository.save(member);

        // 返回操作结果==========     ==========     ==========     ==========

        // 获取认证结果处理方式
        AuthRedirectType authRedirectType = securityProperties.getBrowser().getAuthRedirectType();

        if (authRedirectType == AuthRedirectType.JSON) {
            response.setContentType("application/text;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new BaseMessage<>(ResultEnum.SUCCESS, LoginEums.LOGIN_IN_SUCCESS)));
        } else if (authRedirectType == AuthRedirectType.REDIRECT) {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
