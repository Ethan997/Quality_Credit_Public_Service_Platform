package com.travischenn.platform.properties;

import com.travischenn.platform.properties.nest.ImageCodeNestedProperties;
import com.travischenn.platform.properties.nest.SmsCodeNestedProperties;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeNestedProperties
 * 功能描述    : 验证码配置项
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/14 14:25
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
public class ValidCodeProperties {

    /**
     * 短信验证码配置项
     */
    @NestedConfigurationProperty
    SmsCodeNestedProperties smsCode = new SmsCodeNestedProperties();

    /**
     * 图形验证码配置项
     */
    @NestedConfigurationProperty
    ImageCodeNestedProperties imageCode = new ImageCodeNestedProperties();

}
