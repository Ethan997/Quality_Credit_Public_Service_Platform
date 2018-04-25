package com.travischenn.platform.validcode;

import com.travischenn.platform.domain.SmsCode;

import javax.servlet.http.HttpServletRequest;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ValidCodeGenerator
 * 功能描述    : 验证码生成器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/14 16:32
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public interface ValidCodeGenerator<T> {

    /**
     * 验证码生成器
     * @param request 请求对象
     * @return 图片验证码对象
     */
    T validCodeGenerate(HttpServletRequest request);

}
