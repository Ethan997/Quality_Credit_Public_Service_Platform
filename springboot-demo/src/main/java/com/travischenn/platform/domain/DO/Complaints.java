package com.travischenn.platform.domain.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaints {

    /** 投诉ID */
    @Id
    @GeneratedValue
    private Integer id;

    /** 投诉人名称 */
    @NotBlank(message = "投诉人不能为空")
    private String name;

    /** 投诉时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /** 投诉内容 */
    @Size(min = 5)
    private String content;

    /** 投诉人联系方式 */
    @NotBlank(message = "投诉人联系方式不能为空")
    private String mobile;

    /** 投诉处理进程 */
    private String status;

    /** 投诉处理结果 */
    private String result;

}
