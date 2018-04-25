package com.travischenn.platform.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : Complaints
 * 功能描述    : 投诉对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/19 21:29
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
public class ComplaintsDTO {

    /** 投诉人名称 */
    @NotBlank(message = "投诉人不能为空")
    private String name;

    /** 投诉内容 */
    @NotBlank(message = "投诉内容不能为空")
    @Size(min = 5)
    private String content;

    /** 投诉处理进程 */
    private String status;

}
