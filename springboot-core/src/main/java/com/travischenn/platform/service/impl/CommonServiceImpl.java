package com.travischenn.platform.service.impl;

import com.travischenn.platform.service.CommonService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;


/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : CommonServiceImpl
 * 功能描述    : 通用服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 14:27
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public void validBeanBindResult(BindingResult bindingResult , String msg , Logger log) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            bindingResult.getAllErrors().forEach(error-> stringBuilder.append(error.getDefaultMessage()).append("\n"));
            log.error("创建用户失败,失败原因:{}", stringBuilder.toString());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

}
