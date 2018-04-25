package com.travischenn.platform.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.travischenn.platform.domain.DO.FileInfo;
import com.travischenn.platform.properties.FileCenterProperties;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import com.travischenn.platform.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OSSClient ossClient;

    @Override
    public String upload(MultipartFile multipartFile , @NotBlank String folder_path  , boolean exist) {

        // 文件名
        String file_name = multipartFile.getOriginalFilename();

        // 文件前置路径
        String remoteFilePath = null;

        // 文件存在检查
        if(exist&&isExit(file_name , folder_path)){
            throw new RuntimeException(file_name+" 已经存在,不能重复上传");
        }

        // 获取文件输入流
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("文件转换流失败");
        }

        String bucket = fileCenterProperties.getAlibaba().getBucketName();

        // 定义二级目录
        if(!StringUtils.isBlank(folder_path)){
            remoteFilePath = folder_path.substring(0, folder_path.length()).replaceAll("\\\\", "/");
        }

        // 创建上传文件的配置项
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(contentType(file_name.substring(file_name.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + file_name);

        // 上传文件
        ossClient.putObject(bucket, folder_path + file_name , inputStream , objectMetadata);

        // 关闭io流
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("文件流关闭失败");
        }

        // 同步文件上传记录
        FileInfo fileInfo = new FileInfo();

        // 获取上传对象
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String realname = userService.findUserByUsername(userDetails.getUsername()).getRealname();

        fileInfo.setCreateUser(realname);
        fileInfo.setName(file_name);
        fileInfo.setCreateTime(new java.util.Date());
        fileInfo.setHref(fileCenterProperties.getAlibaba().getAccessUrl() + "/" + remoteFilePath + file_name);
        fileInfoRepository.save(fileInfo);

        return fileCenterProperties.getAlibaba().getAccessUrl() + "/" + remoteFilePath + file_name;

    }

    @Override
    public void deleteFile(@NotBlank String oss_url) {

        // OSS HREF -> 文件名称
        List<String> oss_href_item = Arrays.asList(oss_url.split("/"));

        // 判断将要删除的文件是否存在
        String file_name = oss_href_item.get(oss_href_item.size()-1);

        if(isExit(file_name , getFolderPathFromUrl(oss_url , file_name))){
            ossClient.deleteObject(fileCenterProperties.getAlibaba().getBucketName() , getKeyFromUrl(oss_url));
        }else{
            throw new RuntimeException("OSS 地址为: "+oss_url+"的文件不存在");
        }

    }

    @Override
    public void deleteFiles(List<String> keys) {

        try {
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(fileCenterProperties.getAlibaba().getBucketName()).withKeys(keys));
            deleteObjectsResult.getDeletedObjects();
        } catch (OSSException oe) {
            throw new RuntimeException("OSS文件删除失败:失败IP地址: "+oe.getHostId()+"失败代码: "+oe.getErrorCode()+" 失败信息: "+oe.getMessage());
        } catch (ClientException ce) {
            throw new RuntimeException("OSS客户端构建失败: "+ce.getMessage());
        }

    }

    @Override
    public boolean isExit(String file_name , String folder_path) {

        // 获取 Bucket
        String bucket = fileCenterProperties.getAlibaba().getBucketName();

        // 本地查询     =====-----=====-----=====-----=====-----

        // 获取文件名称为 filename 的文件列表
        List<FileInfo> file_info_list = fileInfoRepository.findFileInfosByName(file_name);

        // 根据文件夹进行过滤
        List<FileInfo> folder_filter_list = file_info_list.stream().filter(file_info -> {

            // 文件 OSS 路径
            String href = file_info.getHref();

            // 文件夹路径
            String folder_name_item = getFolderPathFromUrl(href , file_name);

            // 判断文件夹路径与当前路径是否相同
            if (StringUtils.equals(folder_path, folder_name_item)) {
                return true;
            }

            return false;

        }).collect(Collectors.toList());

        // OSS 查询     =====-----=====-----=====-----=====-----

        if(folder_filter_list.size() != 0){
            if(ossClient.doesObjectExist(bucket, folder_path + file_name)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String getKeyFromUrl(String url) {
        return StringUtils.substring(url , fileCenterProperties.getAlibaba().getAccessUrl().length()+1);
    }

    @Override
    public String getFolderPathFromUrl(String oss_url , String file_name) {
        return StringUtils.substring(oss_url, fileCenterProperties.getAlibaba().getAccessUrl().length() + 1, oss_url.length() - file_name.length());
    }

    @Override
    public String contentType(String FilenameExtension) {

        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG") || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG") || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".pdf") || FilenameExtension.equals(".PDF")) {
            return "application/pdf";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT") || FilenameExtension.equals(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC") || FilenameExtension.equals(".doc")) {
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
