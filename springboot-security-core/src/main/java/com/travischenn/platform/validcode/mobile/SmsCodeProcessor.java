package com.travischenn.platform.validcode.mobile;

import com.aliyuncs.exceptions.ClientException;
import com.travischenn.platform.domain.VO.SmsCode;
import com.travischenn.platform.validcode.AbstractValidCodeProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeProcessor
 * 功能描述    : 短信验证码处理器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/16 13:44
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Component(value = "smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidCodeProcessorImpl<SmsCode> {

    // 短信发送器
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest servletWebRequest, SmsCode validCode) throws IOException, ServletRequestBindingException, ClientException {
        smsCodeSender.send(servletWebRequest.getRequest() , validCode);
    }

}
