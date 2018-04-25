package com.travischenn.platform.domain.VO;

import com.travischenn.platform.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : LayuiPage
 * 功能描述    : Layui分页组件
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/4 14:06
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@AllArgsConstructor
public class LayuiPage<T> {

    /** 返回的状态码 */
    private String code;

    /** 返回的消息 */
    private String msg;

    /** 返回消息总数 */
    private int count;

    /** 返回的数据 */
    private T data;

    public LayuiPage(ResultEnum resultEnum , int total , T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getDescribe();
        this.count = total;
        this.data = data;
    }

}
