package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.User;
import com.travischenn.platform.domain.DTO.UserDTO;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.service.CommonService;
import com.travischenn.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResultBean<User> create(@Valid @RequestBody User user , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "创建用户失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , userService.create(user));
    }

    @GetMapping
    @RequestMapping("/current")
    public ResultBean<UserDTO> findUserByThread(){
        return new ResultBean<>(ResultEnum.SUCCESS , userService.findUserByThead());
    }

    @GetMapping
    @RequestMapping("/pageable")
    public ListResultBean findALL(@RequestParam(name = "page", defaultValue = "0") String page,
                                  @RequestParam(name = "size", defaultValue = "10") String size){
        return userService.findByPage(Integer.parseInt(page) , Integer.parseInt(size));
    }

    @PutMapping(produces = "application/jon;charset=UTF-8")
    public ResultBean<User> update(@Valid @RequestBody User user , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "用户更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , userService.update(user));
    }

    @PutMapping(produces = "application/jon;charset=UTF-8")
    @RequestMapping("/bean")
    public ResultBean<User> updateByField(@Valid @RequestBody UpdateBean updateBean , BindingResult bindingResult){
        commonService.validBeanBindResult(bindingResult , "用户更新失败" , log);
        return new ResultBean<>(ResultEnum.SUCCESS , userService.updateField(updateBean));
    }

    @DeleteMapping
    public ResultBean<String> deleteById(@RequestParam(name = "id") Integer id){
        userService.deleteById(id);
        return new ResultBean<>(ResultEnum.SUCCESS , "删除成功");
    }

    @DeleteMapping
    @RequestMapping("/ids")
    public ResultBean<String> deleteByIds(@RequestParam(name = "ids") String ids){
        userService.deleteByIds(ids);
        return new ResultBean<>(ResultEnum.SUCCESS , "批量删除成功");
    }

}
