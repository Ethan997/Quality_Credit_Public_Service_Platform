package com.travischenn.platform.config;

import com.travischenn.platform.Properties.NewsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsConfig
 * 功能描述    : 新闻配置类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/6 19:26
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Configuration
@EnableConfigurationProperties(NewsProperties.class)
public class NewsConfig {

}
