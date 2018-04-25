package com.travischenn.platform.properties.nest;

import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeNestedProperties
 * 功能描述    : 验证码基类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/15 11:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
public class SmsCodeNestedProperties {

    /**
     * 验证码长度
     */
    private int length = 4;

    /**
     * 过期时间  [单位: 秒]
     */
    private int expireSecond = 60 * 60;

    /**
     * 图形验证码拦截URI
     */
    private String filterUri;

}