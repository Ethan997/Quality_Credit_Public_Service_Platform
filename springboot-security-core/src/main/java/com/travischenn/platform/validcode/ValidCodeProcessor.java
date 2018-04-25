package com.travischenn.platform.validcode;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ValidCodeProcessor
 * 功能描述    : 验证码通用流程
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/15 20:06
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public interface ValidCodeProcessor {

    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_VALID_CODE_";

    /**
     * 创建验证码
     * @param request 请求和响应对象封装
     */
    void create(ServletWebRequest request) throws IOException, ServletRequestBindingException, ClientException;

}
