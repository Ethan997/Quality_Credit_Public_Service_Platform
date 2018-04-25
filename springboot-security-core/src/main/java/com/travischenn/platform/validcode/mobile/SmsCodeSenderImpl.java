package com.travischenn.platform.validcode.mobile;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.travischenn.platform.domain.SmsCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeSenderImpl
 * 功能描述    : 短信验证码发送类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/16 11:57
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Slf4j
public class SmsCodeSenderImpl implements SmsCodeSender {

    @Override
    public SendSmsResponse send(HttpServletRequest httpServletRequest, SmsCode smsCode) throws ServletRequestBindingException, ClientException {

        // 从请求对象中获取手机号码
        String mobile = ServletRequestUtils.getStringParameter(httpServletRequest, "mobile");

        log.info("发送了一条短信,发送的手机号码是:{},发送的内容是:{}", mobile, smsCode.getCode());

        //初始化acsClient
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIDZlwxaMAEJFk", "WILuvfV8x9dGC4XnBU7HCpQqbXMUpc");
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();

        // 设置手机号
        request.setPhoneNumbers(mobile);

        // 设置签名信息
        request.setSignName("QC团队");

        // 设置消息模板编号
        request.setTemplateCode("SMS_117525345");

        // 设置消息模板内容
        request.setTemplateParam("{\"code\":\"" + smsCode.getCode() + "\"}");

        SendSmsResponse sendSmsResponse = null;

        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            throw new RuntimeException(e.getMessage());
        }

        return sendSmsResponse;

    }
}
