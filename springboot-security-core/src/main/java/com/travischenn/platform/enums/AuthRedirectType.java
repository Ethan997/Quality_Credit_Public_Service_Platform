package com.travischenn.platform.enums;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AuthRedirectType
 * 功能描述    : 认证跳转方式
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 13:59
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public enum AuthRedirectType {

    JSON("JSON字符串"),

    REDIRECT("重定向");

    /**
     * 字段描述
     */
    private String describe;

    AuthRedirectType(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

}
