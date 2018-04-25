package com.travischenn.platform.controller;

import com.aliyuncs.exceptions.ClientException;
import com.travischenn.platform.validcode.image.ImageCodeProcessor;
import com.travischenn.platform.validcode.mobile.SmsCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ValidCodeController
 * 功能描述    : 验证码控制器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 14:46
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@RestController
@RequestMapping("/validCode")
public class ValidCodeController {

    @Autowired
    private SmsCodeProcessor smsCodeSender;

    @Autowired
    private ImageCodeProcessor imageCodeProcessor;

    /**
     * @param request  请求对象
     * @param response 响应对象
     * @throws IOException 读写异常
     */
    @GetMapping("/imageCode")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException, ClientException {
        imageCodeProcessor.create(new ServletWebRequest(request , response));
    }

    /**
     * 短信验证码
     *
     * @param request 请求对象
     * @throws ServletRequestBindingException Servlet 绑定异常
     * @throws IOException                    读写异常
     */
    @GetMapping("/smsCode")
    public void smsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException, ClientException {
        smsCodeSender.create(new ServletWebRequest(request , response));
    }

}
