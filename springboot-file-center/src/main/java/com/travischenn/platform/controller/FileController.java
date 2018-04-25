package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.FileInfo;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : FileController
 * 功能描述    : 文件控制器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 15:05
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    /**
     * 上传文件
     *
     * @param file   文件对象
     * @param folder OSS 前置路径
     * @param exist  文件是否需要进行存在性检查
     *
     * @return OSS 文件路径
     */
    @PostMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/upload")
    public ResultBean<String> upload(@RequestParam MultipartFile file, @Param("folder") String folder , @RequestParam("exist") boolean exist) {
        return new ResultBean<>(ResultEnum.SUCCESS, alibabaOSSService.upload(file, folder , exist));
    }

    /**
     * 批量删除文件
     *
     * @param ids   删除对象 ID 字符串
     */
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/ids")
    public ResultBean<String> upload(@NotBlank @RequestParam("ids") String ids) {

        // 分割需要删除的 ID
        List<String> list = Arrays.asList(ids.split(","));

        // 准备 key 容器
        List<String> os_key_list = new ArrayList<>();

        list.forEach(id->{

            FileInfo fileInfo = fileInfoRepository.findOne(Integer.parseInt(id));
            fileInfoRepository.delete(Integer.parseInt(id));

            if(fileInfo != null){
                os_key_list.add(alibabaOSSService.getKeyFromUrl(fileInfo.getHref()));
            }

        });

        alibabaOSSService.deleteFiles(os_key_list);

        return new ResultBean<>(ResultEnum.SUCCESS, "");
    }

}
