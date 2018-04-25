package com.travischenn.platform.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.travischenn.platform.properties.FileCenterProperties;
import com.travischenn.platform.service.AlibabaOSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AlibabaOSSServiceImpl
 * 功能描述    : 阿里云 OSS 服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 15:13
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class AlibabaOSSServiceImpl implements AlibabaOSSService {

    @Autowired
    private FileCenterProperties fileCenterProperties;

    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) throws IOException {

        // 文件名
        String fileName = multipartFile.getOriginalFilename();

        // 获取文件输入流
        InputStream inputStream = multipartFile.getInputStream();

        // 获取 Bucket
        String bucket = fileCenterProperties.getAlibaba().getBucketName();

        // 初始化OSSClient
        OSSClient ossClient = new OSSClient(fileCenterProperties.getAlibaba().getEndPoint(),
                fileCenterProperties.getAlibaba().getAccessKeyId(),
                fileCenterProperties.getAlibaba().getAccessKeySecret());

        // 定义二级目录
        String remoteFilePath = folder.substring(0, folder.length()).replaceAll("\\\\", "/");

        // 创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(inputStream.available());
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(contentType(fileName.substring(fileName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + fileName);

        // 上传文件
        ossClient.putObject(bucket, folder + fileName , inputStream , objectMetadata);

        // 关闭OSSClient
        ossClient.shutdown();

        // 关闭io流
        inputStream.close();

        return fileCenterProperties.getAlibaba().getAccessUrl() + "/" + remoteFilePath + fileName;

    }

    @Override
    public void downloadFile(String key, String filename) throws IOException {

        // 初始化OSSClient
        OSSClient ossClient = new OSSClient(fileCenterProperties.getAlibaba().getEndPoint(),
                fileCenterProperties.getAlibaba().getAccessKeyId(),
                fileCenterProperties.getAlibaba().getAccessKeySecret());

        // 根据文件名获取文件
        OSSObject object = ossClient.getObject(fileCenterProperties.getAlibaba().getBucketName(), key);

        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();

        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();

        ObjectMetadata objectData = ossClient.getObject(new GetObjectRequest(fileCenterProperties.getAlibaba().getBucketName(), key), new File(filename));

        // 关闭数据流
        objectContent.close();

    }

    @Override
    public void deleteFile(String filePath) {

        // 初始化OSSClient
        OSSClient ossClient = new OSSClient(fileCenterProperties.getAlibaba().getEndPoint(),
                fileCenterProperties.getAlibaba().getAccessKeyId(),
                fileCenterProperties.getAlibaba().getAccessKeySecret());

        ossClient.deleteObject(fileCenterProperties.getAlibaba().getBucketName(), filePath);

    }

    @Override
    public String contentType(String FilenameExtension) {

        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG")
                || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG")
                || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT")
                || FilenameExtension.equals(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC")
                || FilenameExtension.equals(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equals(".apk") || FilenameExtension.equals(".APK")) {
            return "application/octet-stream";
        }
        return "text/html";
    }

}
