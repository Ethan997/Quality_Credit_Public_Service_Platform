package com.travischenn.platform.Properties;

import com.travischenn.platform.Properties.nest.AdminProperties;
import com.travischenn.platform.Properties.nest.WebPageProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsProperties
 * 功能描述    : 新闻配置项
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/6 19:24
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@ConfigurationProperties(prefix = "travischenn.news")
public class NewsProperties {

    @NestedConfigurationProperty
    private AdminProperties admin;

    @NestedConfigurationProperty
    private WebPageProperties web;

}
