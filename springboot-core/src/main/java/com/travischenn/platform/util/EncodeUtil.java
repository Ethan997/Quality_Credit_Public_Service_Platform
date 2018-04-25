package com.travischenn.platform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/15 16:13
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public class EncodeUtil {

    /**
     * 将字符串转换成 UTF-8 的形式
     * @param str  需要转换的字符串
     */
    public static String getUTF8XMLString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        String xmlUTF8="";
        try {
            xmlUTF8 = URLEncoder.encode(new String(sb.toString().getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return xmlUTF8;
    }

}
