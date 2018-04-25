package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AuthEnums
 * 功能描述    : 用户权限
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 15:39
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum AuthEnums {

    MEMBER("用户"),

    ADMINISTRATOR("管理员");

    /**
     * 字段描述
     */
    private String describe;

}
