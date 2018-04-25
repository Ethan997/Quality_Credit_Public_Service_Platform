package com.travischenn.platform.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

    /**
     * Date -> LocalDateTime
     *
     * @param date java.util.Date 类型的时间对象
     *
     * @return LocalDataTime 时间对象
     */
    public static LocalDateTime DateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

}
