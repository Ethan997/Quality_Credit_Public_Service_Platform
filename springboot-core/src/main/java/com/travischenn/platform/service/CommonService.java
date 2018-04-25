package com.travischenn.platform.service;

import org.slf4j.Logger;
import org.springframework.validation.BindingResult;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : CommonService
 * 功能描述    : 通用服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 14:26
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface CommonService {

    /**
     * 判断传进来的字段合法性
     *
     * @param bindingResult 错误校验对象
     * @param msg           错误前置消息
     * @param log           日志对象
     */
    void validBeanBindResult(BindingResult bindingResult , String msg , Logger log);

}
