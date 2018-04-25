package com.travischenn.platform.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AlibabaOSSService
 * 功能描述    : 阿里云对象存储服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 15:06
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface AlibabaOSSService {

    String uploadFile(MultipartFile multipartFile, String remotePath) throws IOException;

    void downloadFile(String key, String filename) throws IOException;

    void deleteFile(String filePath);

    String contentType(String FilenameExtension);

}
