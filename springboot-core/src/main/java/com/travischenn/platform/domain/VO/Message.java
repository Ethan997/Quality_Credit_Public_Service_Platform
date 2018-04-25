package com.travischenn.platform.domain.VO;

import com.travischenn.platform.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : Message
 * 功能描述    : 消息
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/5 19:25
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@AllArgsConstructor
public class Message<T> {

    private String status;

    private String msg;

    private T result;

}
