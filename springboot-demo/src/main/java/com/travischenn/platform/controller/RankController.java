package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.Rank;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.service.CommonService;
import com.travischenn.platform.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 23:43
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Slf4j
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private RankService rankService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Rank> create(@Valid @RequestBody Rank rank){
        return new ResultBean<>(ResultEnum.SUCCESS , rankService.create(rank));
    }

    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size,
                                  @RequestParam(name = "field", defaultValue = "0") String field){
        return rankService.findByPage(Integer.parseInt(page) , Integer.parseInt(size) , field);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Rank> findRankById(@RequestParam(name = "id", defaultValue = "1") int id){
        return new ResultBean<>(ResultEnum.SUCCESS , rankService.findById(id));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<Rank> update(@Valid @RequestBody Rank rank , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , rankService.update(rank));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/bean")
    public ResultBean<Rank> updateByField(@Valid @RequestBody UpdateBean updateBean , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , rankService.updateField(updateBean));
    }

    @DeleteMapping
    @Transactional
    @RequestMapping("/ids")
    public ResultBean<String> deleteByIds(@RequestParam(name = "ids") String ids){
        rankService.deleteByIds(ids);
        return new ResultBean<>(ResultEnum.SUCCESS , "批量删除成功");
    }

}
