package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 14:33
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum LoginEums {

    LOGIN_IN_SUCCESS(100004 , "登录成功"),

    LOGIN_IN_FAILED_BECAUSE_INVALID_USERNAME_PASSWORD(100005 , "登录失败,用户名密码错误"),

    LOGIN_IN_FAILED_BECAUSE_NULL_USERNAME(100006 , "登录失败,用户名对应的用户不存在"),

    LOGIN_OUT_SUCCESS(100007 , "登出成功"),

    LOGIN_OUT_FAILED(100008 , "登出失败");

    /**
     * 字段对应的编号
     */
    private Integer code;

    /**
     * 字段描述
     */
    private String describe;

}