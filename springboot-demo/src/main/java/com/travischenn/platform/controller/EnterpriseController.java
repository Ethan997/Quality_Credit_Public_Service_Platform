package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.Enterprise;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import com.travischenn.platform.service.CommonService;
import com.travischenn.platform.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

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
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Enterprise> create(@Valid @RequestBody Enterprise enterprise , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "企业用户失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , enterpriseService.create(enterprise));
    }

    /**
     * 更新指定 ID 企业的对象
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
        Enterprise enterprise = enterpriseService.findById(id);

        // 当文件详情存在时,删除文件详情以及 OSS 对象
        if(enterprise != null){

            if(StringUtils.equals(category , "srrUrl")){

                String srrUrl = enterprise.getSrrUrl();

                if(!StringUtils.isBlank(srrUrl)){
                    alibabaOSSService.deleteFile(srrUrl);
                    fileInfoRepository.deleteFileInfoByHref(srrUrl);

                }

            }else if(StringUtils.equals(category , "qcfUrl")){

                String qcfUrl = enterprise.getQcfUrl();

                if(!StringUtils.isBlank(qcfUrl)){
                    alibabaOSSService.deleteFile(qcfUrl);
                    fileInfoRepository.deleteFileInfoByHref(qcfUrl);
                }

            }else if(StringUtils.equals(category , "srpUrl")){

                String srpUrl = enterprise.getSrpUrl();

                if(!StringUtils.isBlank(srpUrl)){
                    fileInfoRepository.deleteFileInfoByHref(srpUrl);
                    alibabaOSSService.deleteFile(srpUrl);
                }

            }else{
                throw new RuntimeException("非法的文件类型");
            }


        }else{
            throw new RuntimeException("您要更新的企业对象不存在,请刷新页面");
        }

        String upload_success_url;

        // 上传文件
        upload_success_url = alibabaOSSService.upload(file, folder, exist);

        // 同步文件信息
        enterpriseService.updateField(new UpdateBean(id , category , upload_success_url));

        return new ResultBean<>(ResultEnum.SUCCESS , upload_success_url);

    }

    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size){
        return enterpriseService.findByPage(Integer.parseInt(page) , Integer.parseInt(size));
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Enterprise> findEnterpriseById(@RequestParam(name = "id", defaultValue = "1") int id){
        return new ResultBean<>(ResultEnum.SUCCESS , enterpriseService.findById(id));
    }

    @GetMapping
    @RequestMapping("/search")
    public ResultBean<Enterprise> findEnterpriseByName(@RequestParam(name = "category") String category , @RequestParam(name = "value") String value) throws UnsupportedEncodingException {

//        String str=new String(category.getBytes("UTF-8"),"GBK");

        return new ResultBean<>(ResultEnum.SUCCESS , enterpriseService.findEnterpriseByCategory(category , value));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Enterprise> update(@Valid @RequestBody Enterprise enterprise , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "企业信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , enterpriseService.update(enterprise));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/bean")
    public ResultBean<Enterprise> updateByField(@Valid @RequestBody UpdateBean updateBean , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "企业信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , enterpriseService.updateField(updateBean));
    }

    @DeleteMapping
    @RequestMapping("/ids")
    public ResultBean<String> deleteByIds(@RequestParam(name = "ids") String ids){
        enterpriseService.deleteByIds(ids);
        return new ResultBean<>(ResultEnum.SUCCESS , "企业信息批量删除成功");
    }

}
