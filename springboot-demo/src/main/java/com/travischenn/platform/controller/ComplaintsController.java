package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.Complaints;
import com.travischenn.platform.domain.DTO.ComplaintsDTO;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ComplaintsStatusEnum;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.service.CommonService;
import com.travischenn.platform.service.ComplaintsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping("/complaints")
public class ComplaintsController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ComplaintsService complaintsService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Complaints> create(@Valid @RequestBody Complaints complaints, BindingResult bindingResult) {
        commonService.validBeanBindResult(bindingResult, "企业用户失败", log);
        return new ResultBean<>(ResultEnum.SUCCESS, complaintsService.create(complaints));
    }

    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size) {
        return complaintsService.findByPage(Integer.parseInt(page), Integer.parseInt(size));
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Complaints> findById(@RequestParam(name = "id", defaultValue = "1") int id) {
        return new ResultBean<>(ResultEnum.SUCCESS, complaintsService.findById(id));
    }

    @GetMapping
    @RequestMapping("/list")
    public ResultBean<List<ComplaintsDTO>> findAll() {

        List<Complaints> complaints_list = complaintsService.findAll();

        if (complaints_list.size() == 0) {
            throw new RuntimeException("无投诉信息");
        }

        List<ComplaintsDTO> complaintsdto_list = complaints_list.stream().map(complaint -> {

            ComplaintsDTO complaintsDTO = new ComplaintsDTO();
            BeanUtils.copyProperties(complaint , complaintsDTO);

            // 处理字符串
            complaintsDTO.setName(complaintsDTO.getName().substring(0, 1) + "**");
            complaintsDTO.setContent(complaintsDTO.getContent().substring(0, 5) + "***");

            // 处理信息反馈状态
            complaintsDTO.setStatus(ComplaintsStatusEnum.valueOf(complaintsDTO.getStatus()).getDescribe());

            return complaintsDTO;

        }).collect(Collectors.toList());

        return new ResultBean<>(ResultEnum.SUCCESS, complaintsdto_list);

    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Complaints> update(@Valid @RequestBody Complaints complaints, BindingResult bindingResult) {
        commonService.validBeanBindResult(bindingResult, "信息更新失败", log);
        return new ResultBean<>(ResultEnum.SUCCESS, complaintsService.update(complaints));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/bean")
    public ResultBean<Complaints> updateByField(@Valid @RequestBody UpdateBean updateBean, BindingResult bindingResult) {
        commonService.validBeanBindResult(bindingResult, "信息更新失败", log);
        return new ResultBean<>(ResultEnum.SUCCESS, complaintsService.updateField(updateBean));
    }

    @DeleteMapping
    @Transactional
    @RequestMapping("/ids")
    public ResultBean<String> deleteByIds(@RequestParam(name = "ids") String ids) {
        complaintsService.deleteByIds(ids);
        return new ResultBean<>(ResultEnum.SUCCESS, "信息批量删除成功");
    }

}
