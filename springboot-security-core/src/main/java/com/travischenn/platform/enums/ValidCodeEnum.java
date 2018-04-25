package com.travischenn.platform.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ValidCodeEnum
 * 功能描述    : 验证码类型枚举
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/17 18:54
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public enum ValidCodeEnum {

    IMAGECODE("图片验证码"),

    SmsCode("短信验证码");

    @Getter
    @Setter
    private String describe;

    ValidCodeEnum(String describe) {
        this.describe = describe;
    }

}
