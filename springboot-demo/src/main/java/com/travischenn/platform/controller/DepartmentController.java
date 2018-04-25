package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.Department;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import com.travischenn.platform.service.CommonService;
import com.travischenn.platform.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Department> create(@Valid @RequestBody Department department , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "企业用户失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , departmentService.create(department));
    }

    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size){
        return departmentService.findByPage(Integer.parseInt(page) , Integer.parseInt(size));
    }

    /**
     * 更新指定 ID 部门的对象
     *
     * @param file       需要更新的文件
     * @param id         需要更新的企业 ID
     * @param category   需要更新的企业 ID
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
                                     @NotBlank @RequestParam("id") int id ,
                                     @NotBlank @RequestParam("category") String category) {

        // 上传文件
        if(alibabaOSSService.isExit(file.getOriginalFilename() , folder)){
            throw new RuntimeException("文件: "+file.getOriginalFilename()+"已经存在");
        }

        // 获取之前指定类别的文件详情对象
        Department department = departmentService.findById(id);

        // 当文件详情存在时,删除文件详情以及 OSS 对象
        if(department != null){

            if(StringUtils.equals(category , "tStandardUrl")){

                String tStandardUrl = department.getTStandardUrl();

                if(!StringUtils.isBlank(tStandardUrl)){
                    alibabaOSSService.deleteFile(tStandardUrl);
                    fileInfoRepository.deleteFileInfoByHref(tStandardUrl);
                }

            }else if(StringUtils.equals(category , "pStandardUrl")){

                String pStandardUrl = department.getPStandardUrl();

                if(!StringUtils.isBlank(pStandardUrl)){
                    alibabaOSSService.deleteFile(pStandardUrl);
                    fileInfoRepository.deleteFileInfoByHref(pStandardUrl);
                }

            }else{
                throw new RuntimeException("非法的文件类型");
            }

        }else{
            throw new RuntimeException("您要更新的对象不存在,请刷新页面");
        }

        String upload_success_url;

        // 上传文件
        upload_success_url = alibabaOSSService.upload(file, folder, exist);

        // 同步文件信息
        departmentService.updateField(new UpdateBean(id , category , upload_success_url));

        return new ResultBean<>(ResultEnum.SUCCESS , upload_success_url);

    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/name")
    public ResultBean<Department> findByName(@RequestParam(name = "name") String name){

        Department department = departmentService.findByName(name);

        if(!Boolean.valueOf(department.getIsPublic())){
            throw new RuntimeException(department.getName()+"信息不公开");
        }

        return new ResultBean<>(ResultEnum.SUCCESS , department);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/info")
    public ResultBean<Department> findById(@RequestParam(name = "id", defaultValue = "1") int id){
        return new ResultBean<>(ResultEnum.SUCCESS , departmentService.findById(id));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Department> update(@Valid @RequestBody Department department , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , departmentService.update(department));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/bean")
    public ResultBean<Department> updateByField(@Valid @RequestBody UpdateBean updateBean , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , departmentService.updateField(updateBean));
    }

    @DeleteMapping
    @Transactional
    @RequestMapping("/ids")
    public ResultBean<String> deleteByIds(@RequestParam(name = "ids") String ids){
        departmentService.deleteByIds(ids);
        return new ResultBean<>(ResultEnum.SUCCESS , "信息批量删除成功");
    }

}
