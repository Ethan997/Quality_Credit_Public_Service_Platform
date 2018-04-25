package com.travischenn.platform.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;
import java.util.Map;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SecurityProperties
 * 功能描述    : 权限认证配置项
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 21:48
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@ConfigurationProperties(prefix = "travischenn.security")
public class SecurityProperties {

    /**
     * 浏览器配置项
     */
    @NestedConfigurationProperty
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置项
     */
    @NestedConfigurationProperty
    private ValidCodeProperties validCode = new ValidCodeProperties();

}
