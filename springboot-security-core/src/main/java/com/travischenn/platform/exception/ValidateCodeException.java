package com.travischenn.platform.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ValidateCodeException
 * 功能描述    : 验证码异常
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 17:03
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String explanation) {
        super(explanation);
    }

}
