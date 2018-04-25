package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ResultEnum
 * 功能描述    : 操作结果
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 20:59
 * Created    : IntelliJ IDEA
 * **************************************************************
 */

@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS("100001" , "操作成功"),

    FAILED("100002" ,"操作失败"),

    UNAUTHORIZED("100003" ,"访问权限被拒绝，请引导用户至登录页");

    /**
     * 字段对应的编号
     */
    private String code;

    /**
     * 字段描述
     */
    private String describe;

}
