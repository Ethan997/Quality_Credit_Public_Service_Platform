package com.travischenn.platform.controller;

import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : authenticationController
 * 功能描述    : 用户登录方式控制
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 20:37
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@RestController
@RequestMapping("/authentication")
public class authenticationController {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * Spring 请求缓存工具
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * Spring 请求跳转工具
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping("/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultBean<String> authenticationDispatcher(HttpServletRequest request , HttpServletResponse response){

        //获取请求的URL
        SavedRequest savedRequest = requestCache.getRequest(request , response);

        if(savedRequest!=null){

            //获取请求的 URL
            String redirectUrl = savedRequest.getRedirectUrl();

            //判断返回的页面是否是 Html
            if(StringUtils.endsWithIgnoreCase(redirectUrl , ".html")){
                try {
                    redirectStrategy.sendRedirect(request , response ,securityProperties.getBrowser().getLoginPageUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResultBean<>( ResultEnum.UNAUTHORIZED,null);
    }

    @GetMapping("/userDetail")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultBean<String> sessionTimeOut(){
        return new ResultBean<>(ResultEnum.FAILED , "Session已过期");
    }

}
