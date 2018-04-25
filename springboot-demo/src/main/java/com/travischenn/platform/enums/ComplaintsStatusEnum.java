package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ComplaintsStatusEnum
 * 功能描述    : 投诉进程
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/24 2:05
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */


@AllArgsConstructor
public enum ComplaintsStatusEnum {

    INIT("未处理"),

    FINISH("已处理");

    @Getter
    private String describe;

    public static ComplaintsStatusEnum des2Enum(String describe){

        if(StringUtils.equals(describe , INIT.getDescribe())){
            return INIT;
        }else if(StringUtils.equals(describe , FINISH.getDescribe())){
            return FINISH;
        }else{
            throw new RuntimeException("投诉状态类型:"+describe+"非法");
        }

    }

}
