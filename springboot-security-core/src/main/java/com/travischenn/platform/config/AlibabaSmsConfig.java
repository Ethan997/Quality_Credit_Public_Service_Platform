package com.travischenn.platform.config;

import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AlibabaSmsConfig
 * 功能描述    : 阿里云短信发送配置项
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/16 16:15
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
public class AlibabaSmsConfig {

    /**
     * API 秘钥ID
     */
    private String accessKeyId;

    /**
     * API 秘钥密码
     */
    private String accessKeySecret;

}
