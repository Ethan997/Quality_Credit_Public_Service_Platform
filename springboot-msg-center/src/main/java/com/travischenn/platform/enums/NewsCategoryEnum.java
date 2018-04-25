package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsCategoryEnum
 * 功能描述    : 消息类型
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/3 21:32
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum NewsCategoryEnum {

    WORK_DYNAMICS("000001","工作动态"),
    INFORMATION_DISCLOSURE("000002","信息公开"),
    QUALITY_COMMITTED("000003","质量信用承诺"),
    QUALITY_REPORT("000004","社会责任报告"),
    BRAND_CONSTRUCTION_APPRAISE("000005","区域品牌价值评价"),
    BRAND_COMMUNICATE("000006","质量管理与品牌建设交流活动");

    private final String code;
    private final String describe;

}