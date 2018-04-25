package com.travischenn.platform.properties.nest;

import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AlibabaOSSProperties
 * 功能描述    : 阿里巴巴OSS对象存储服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 14:51
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Data
public class AlibabaOSSProperties {

    // TODO 做一个阿里云通用配置类 ， 避免多个阿里服务需要配置多次
    /** 开发者ID */
    private String accessKeyId;

    /** 开发者秘钥 */
    private String accessKeySecret;

    /** 分支地址 */
    private String endPoint;

    /** 文件仓库名称 */
    private String BucketName;

    /** 访问域名 */
    private String accessUrl;

}
