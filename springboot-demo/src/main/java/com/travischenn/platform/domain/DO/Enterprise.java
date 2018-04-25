package com.travischenn.platform.domain.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : Enterprise
 * 功能描述    : 企业对象
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
public class Enterprise {

    /** 企业ID */
    @Id
    @GeneratedValue
    private int id;

    /** 企业名称 */
    @NotBlank(message = "企业名称不能为空")
    private String name;

    /** 所属行业 */
    @NotBlank(message = "所属行业不能为空")
    private String industry;

    /** 机构代码 */
    @NotBlank(message = "机构代码不能为空")
    private String agencyCode;

    /** 注册时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationTime;

    /** 经营范围 */
    @NotBlank(message = "注册时间不能为空")
    private String businessScope;

    /** 行政区域划分 */
    @NotBlank(message = "行政区域划分不能为空")
    private String administrativeDivision;

    /** 法人代表 */
    @NotBlank(message = "法人代表不能为空")
    private String legalRepresentative;

    /** 通讯地址 */
    @NotBlank(message = "通讯地址不能为空")
    private String location;

    /** 社会责任报告URL */
    private String srrUrl;

    /** 质量信用档案URL */
    private String qcfUrl;

    /** 质量信用承诺URL */
    private String srpUrl;

}
