package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : RankCategoryEnum
 * 功能描述    : 榜单管理
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 22:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Getter
@AllArgsConstructor
public enum RankCategoryEnum {

    HORNOR("光荣榜"),

    BLANK("黑名单");

    private String describe;

    public static RankCategoryEnum des2Enum(String describe){

        if(StringUtils.equals(describe , HORNOR.getDescribe())){
            return HORNOR;
        }else if(StringUtils.equals(describe , BLANK.getDescribe())){
            return BLANK;
        }else{
            throw new RuntimeException("榜单类型:"+describe+"非法");
        }

    }

}
