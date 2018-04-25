package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ListTypeEnum
 * 功能描述    : 榜单种类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/12 13:31
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum ListTypeEnum {

    HORNOR("300001","光荣榜"),
    BLANK("300002","黑榜");

    private final String code;
    private final String describe;

}
