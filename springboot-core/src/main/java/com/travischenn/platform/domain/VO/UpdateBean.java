package com.travischenn.platform.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UpdateBean
 * 功能描述    : 数据更新对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/5 11:44
 * Created    : IntelliJ IDEA
 * **************************************************************
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBean {

    /** 对象ID */
    private int id;

    /** 要更新的字段 */
    @NotBlank(message = "更新字段不能为空")
    private String field;

    /** 更新字段对应的值 */
    private String value;

}
