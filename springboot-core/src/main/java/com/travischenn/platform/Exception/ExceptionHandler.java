package com.travischenn.platform.Exception;

import com.travischenn.platform.domain.VO.ResultBean;
import com.travischenn.platform.enums.ResultEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-example-starter
 * 类 名 称    : HandlerException
 * 功能描述    : 异常 -> AOP 统一拦截
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/11/4 11:12
 * Created    : IntelliJ IDEA
 * **************************************************************
 */

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean<String> handle(Exception exception) {
        return new ResultBean<>(ResultEnum.FAILED , exception.getMessage());
    }

}