package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.Department;
import com.travischenn.platform.domain.DO.Enterprise;
import com.travischenn.platform.domain.DO.FileInfo;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.properties.FileCenterProperties;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UserController
 * 功能描述    : 用户控制器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 14:16
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @Autowired
    private FileCenterProperties fileCenterProperties;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    /**
     * 更新文件对象
     *
     * @param file       需要更新的文件
     * @param id         需要更新的企业 ID
     * @param folder     OSS 前置路径
     * @param exist      是否允许文件已经存在
     *
     * @return  更新成功后的文件对象
     */
    @PostMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/update")
    @Transactional
    public ResultBean<String> update(@RequestParam MultipartFile file ,
                                     @NotBlank @RequestParam("folder") String folder ,
                                     @RequestParam("exist") boolean exist ,
                                     @NotBlank @RequestParam("id") int id) {

        // 上传文件
        if(alibabaOSSService.isExit(file.getOriginalFilename() , folder)){
            throw new RuntimeException("文件: "+file.getOriginalFilename()+"已经存在");
        }

        // 获取之前指定类别的文件详情对象
        FileInfo fileInfo = fileInfoRepository.findOne(id);

        // 当文件详情存在时,删除文件详情以及 OSS 对象
        if(fileInfo != null){
            alibabaOSSService.deleteFile(fileInfo.getHref());
            fileInfoRepository.deleteFileInfoByHref(fileInfo.getHref());
        }else{
            throw new RuntimeException("您要更新的对象不存在,请刷新页面");
        }

        String upload_success_url;

        // 上传文件
        upload_success_url = alibabaOSSService.upload(file, folder, exist);

        // 同步文件信息
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setHref(upload_success_url);
        fileInfoRepository.save(fileInfo);

        return new ResultBean<>(ResultEnum.SUCCESS , upload_success_url);

    }


    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size,
                                  @RequestParam(name = "field", defaultValue = "0") String field){

        Page<FileInfo> file_info_page = fileInfoRepository.findFileInfoByHrefStartingWith(fileCenterProperties.getAlibaba().getAccessUrl() + "/report/" + field, new PageRequest(Integer.parseInt(page)-1, Integer.parseInt(size) , new Sort(Sort.Direction.DESC , "id")));
        List<FileInfo> file_info_all = fileInfoRepository.findFileInfoByHrefStartingWith(fileCenterProperties.getAlibaba().getAccessUrl() + "/report/" + field);

        ListResultBean listResultBean = new ListResultBean();
        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(file_info_all.size());
        listResultBean.setData(file_info_page.getContent());

        return listResultBean;
    }

}
