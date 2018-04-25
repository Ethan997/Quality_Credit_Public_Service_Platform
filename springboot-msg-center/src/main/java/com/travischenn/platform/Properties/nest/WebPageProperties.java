package com.travischenn.platform.Properties.nest;

import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : WebPageProperties
 * 功能描述    : 前端页面配置
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 11:17
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
public class WebPageProperties {

    // TODO 后续需要做权限配置 [不可能谁都能请求我们的接口]
    /** 新闻请求接口 */
    private String url;

    /** 静态资源路径 */
    private String statics;

    /** 前台页面路径 */
    private String page;

}
