package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.News;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.service.CommonService;
import com.travischenn.platform.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private NewsService newsService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<News> create(@Valid @RequestBody News news){
        return new ResultBean<>(ResultEnum.SUCCESS , newsService.create(news));
    }

    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size,
                                  @RequestParam(name = "field", defaultValue = "0") String field){
        return newsService.findByPage(Integer.parseInt(page) , Integer.parseInt(size) , field);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/info")
    public ResultBean<News> findNewsById(@RequestParam(name = "id", defaultValue = "1") int id){
        return new ResultBean<>(ResultEnum.SUCCESS , newsService.findById(id));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<News> update(@Valid @RequestBody News news , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , newsService.update(news));
    }

    @PutMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/bean")
    public ResultBean<News> updateByField(@Valid @RequestBody UpdateBean updateBean , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "信息更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , newsService.updateField(updateBean));
    }

    @DeleteMapping
    @Transactional
    @RequestMapping("/ids")
    public ResultBean<String> deleteByIds(@RequestParam(name = "ids") String ids){
        newsService.deleteByIds(ids);
        return new ResultBean<>(ResultEnum.SUCCESS , "批量删除成功");
    }

}
