package com.travischenn.platform.util;

import org.apache.commons.lang.StringUtils;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ConvertUtil
 * 功能描述    : 转换工具类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 15:31
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class ConvertUtil {

    public static boolean numberToBoolean(String value){

        if(!StringUtils.isNumeric(value)){
            throw new RuntimeException("只能对数字进行转换");
        }

        if(StringUtils.equals(value , "0")||StringUtils.equals(value , "1")){
            return StringUtils.equals(value, "1");
        }else{
            throw new RuntimeException("只能对数字0|1进行转换");
        }

    }

}
