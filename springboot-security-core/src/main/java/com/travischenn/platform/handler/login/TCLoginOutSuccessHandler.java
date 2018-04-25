package com.travischenn.platform.handler.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travischenn.platform.domain.VO.BaseMessage;
import com.travischenn.platform.enums.LoginEums;
import com.travischenn.platform.enums.ResultEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : TCLoginOutSuccessHandler
 * 功能描述    : 默认登录成功回调
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 14:31
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class TCLoginOutSuccessHandler implements LogoutSuccessHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new BaseMessage<>(LoginEums.LOGIN_OUT_SUCCESS.getCode() , LoginEums.LOGIN_OUT_SUCCESS.getDescribe() ,"")));
    }

}
