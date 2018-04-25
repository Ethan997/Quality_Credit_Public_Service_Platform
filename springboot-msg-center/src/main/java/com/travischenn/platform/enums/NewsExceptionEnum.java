package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsExceptionEnum
 * 功能描述    : 新闻异常枚举
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/6 19:15
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum NewsExceptionEnum {

    INVALID_NEWS_CATEGORY("20001","非法的新闻类型"),
    NEWS_UNIQUE("20002","当前新闻类型只允许存在一条,请先删除当前新闻或在当前新闻上进行修改"),
    INVALID_NEWS_FIELD("20003","错误的新闻字段 , 请检查后重新进行提交"),
    INVALID_ID("20004","您所更新的新闻ID不存在"),
    NULL_NEWS_ANNEX_FIELD("20005","新闻附件字段为空");

    private  String code;
    private  String describe;

}
