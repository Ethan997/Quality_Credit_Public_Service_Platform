package com.travischenn.platform.properties;

import com.aliyun.oss.internal.OSSUtils;
import com.travischenn.platform.properties.nest.AlibabaOSSProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : FileCenterProperties
 * 功能描述    : 文件中心配置项
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 14:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Data
@ConfigurationProperties(value = "travischenn.file")
public class FileCenterProperties {

    /**
     * 阿里云 OSS 对象存储服务
     */
    @NestedConfigurationProperty
    AlibabaOSSProperties alibaba;

}
