package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsCaregoryEnum
 * 功能描述    : 消息
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 10:47
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum NewsCaregoryEnum {

    POLICY_AND_REGULATION_DOCUMENTS("政策法规文件"),
    WORK_DYNAMICS("工作动态"),
    INFORMATION_DISCLOSURE("信息公开"),
    QUALITY_CREDIT_COMMITMENT("质量信用承诺"),
    QUALITY_CREDIT_FILE("质量信用档案"),
    SOCIAL_RESPONSIBILITY_REPORT("社会责任报告"),
    ACHIEVEMENTS_SHOW("成果展示"),
    REGIONAL_BRAND_VALUE_EVALUATION("区域品牌价值评价"),
    QUALITY_MANAGEMENT_AND_BRAND_BUILDING_EXCHANGE_ACTIVITIES("质量管理与品牌建设交流活动"),
    ANALYSIS_OF_SERVICE_QUALITY_OF_NINGBO_FILM_CULTURE_INDUSTRY("宁波影视文化产业服务质量状况分析报告"),
    CUSTOMER_SATISFACTION_REPORT("顾客满意度报告");

    private String describe;

}
