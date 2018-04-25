package com.travischenn.platform.handler.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.enums.AuthRedirectType;
import com.travischenn.platform.enums.LoginEums;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        AuthRedirectType authRedirectType = securityProperties.getBrowser().getAuthRedirectType();

        if (authRedirectType == AuthRedirectType.JSON) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResultBean<>(ResultEnum.SUCCESS, LoginEums.LOGIN_IN_SUCCESS)));
        } else if (authRedirectType == AuthRedirectType.REDIRECT) {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
