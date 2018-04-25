package com.travischenn.platform.properties.nest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ImageCodeNestedProperties
 * 功能描述    : 图形验证码配置参数
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/14 14:32
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCodeNestedProperties extends SmsCodeNestedProperties {

    /**
     * 图形验证码长度  [默认: 4位数]
     */
    public ImageCodeNestedProperties() {
        setLength(4);
    }

    /**
     * 图形验证码宽度
     */
    private int width = 67;

    /**
     * 图形验证码长度
     */
    private int height = 23;

    /**
     * 图形验证码拦截URI
     */
    private String filterUri;

}