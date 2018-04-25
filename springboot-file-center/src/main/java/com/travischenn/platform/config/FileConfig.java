package com.travischenn.platform.config;

import com.travischenn.platform.properties.FileCenterProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 14:48
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Configuration
@EnableConfigurationProperties(value = FileCenterProperties.class)
public class FileConfig {


}
