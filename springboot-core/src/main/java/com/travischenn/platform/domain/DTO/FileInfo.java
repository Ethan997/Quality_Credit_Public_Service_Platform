package com.travischenn.platform.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : FileInfo
 * 功能描述    : 文件详情
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/2 14:36
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@AllArgsConstructor
public class FileInfo {

    /**文件路径*/
    private String path;

}
