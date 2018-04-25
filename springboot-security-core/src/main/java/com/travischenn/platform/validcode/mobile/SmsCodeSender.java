package com.travischenn.platform.validcode.mobile;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.travischenn.platform.domain.SmsCode;
import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeSenderImpl
 * 功能描述    : 短信发送器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/16 12:23
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public interface SmsCodeSender {

    /**
     * 发送验证码
     * 在使用前需要注册 AlibabaSmsConfig 来进行 API 身份校验
     *
     * @param request        请求响应分装对象
     * @param smsCode        短信验证码
     * @return 发送结果
     */
    SendSmsResponse send(HttpServletRequest request, SmsCode smsCode) throws ServletRequestBindingException, ClientException;

}
