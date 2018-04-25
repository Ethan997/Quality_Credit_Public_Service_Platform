package com.travischenn.platform.Properties.nest;

import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AdminProperties
 * 功能描述    : 新闻后台配置
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 11:19
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
public class AdminProperties  {

    /** 数量唯一的新闻配置 */
    private String unique;

    /** 榜单数量限制 */
    private String listLimit;

}
