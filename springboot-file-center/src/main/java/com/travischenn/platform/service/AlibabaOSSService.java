package com.travischenn.platform.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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

    /**
     * 上传文件
     *
     * @param multipartFile  需要上传的文件对象
     * @param remotePath     OSS 前置路径
     * @param exist          上传文件是否允许重复
     *
     * @return               上传成功后的文件路径  [注]: 该路径为未加密路径
     */
    String upload(MultipartFile multipartFile, String remotePath , boolean exist);

    /**
     * 删除单个文件
     *
     * @param key 文件 key
     */
    void deleteFile(String key);

    /**
     * 删除多个文件
     *
     * @param key_list key 列表
     */
    void deleteFiles(List<String> key_list);

    /**
     * 判断 key 对应的 对象是否存在
     *
     * @param file_name   文件名
     * @param folder_name 文件夹路径   [例]: folder_1/folder_2/
     *
     * @return true 存在 | false 不存在
     */
    boolean isExit(String file_name , String folder_name);

    /**
     * 从完整的 OSS 路径中获取文件 key 值
     *
     * @param url 从完整的 OSS 路径
     */
    String getKeyFromUrl(String url);

    /**
     * 从 OSS 路径中获取文件夹路径
     *
     * @param oss_url    OSS 路径
     * @param file_name  文件名称
     *
     * @return 文件夹路径
     */
    String getFolderPathFromUrl(String oss_url , String file_name);

    /**
     * 根据文件后缀获取文件上传头
     *
     * @param FilenameExtension 文件名后缀
     *
     * @return 上传头
     */
    String contentType(String FilenameExtension);

}
