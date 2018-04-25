package com.travischenn.platform.util;

import org.hibernate.validator.constraints.NotBlank;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : FileUtil
 * 功能描述    : 文件工具类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/12 12:22
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public class FileUtil {

    /**
     * 从文件的路径中获取文件的名称
     *
     * @param file_path 文件路径
     * @return 文件名称
     */
    public static String getFileNameFromPath(@NotBlank String file_path) {
        String[] split = file_path.split("/");
        return split[split.length - 1];
    }


}
