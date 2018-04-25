package com.travischenn.platform.domain.VO;

import com.travischenn.platform.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ResultBean
 * 功能描述    : 返回对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 10:25
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean<T> {

    /** 状态码 */
    private Integer code;

    /** 消息 */
    private String msg;

    /** 列表 */
    private T data;

    public ResultBean (ResultEnum resultEnum , T msg){
        this.code = Integer.valueOf(resultEnum.getCode());
        this.msg = resultEnum.getDescribe();
        this.data = msg;
    }

}
