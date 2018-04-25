package com.travischenn.platform.domain.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : Department
 * 功能描述    : 部门对象
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
public class Department {

    /** 部门ID */
    @Id
    @GeneratedValue
    private int id;

    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /** 部门岗位标准URL */
    private String pStandardUrl;

    /** 部门技术标准URL */
    private String tStandardUrl;

    /** 标准是否公开 */
    private String isPublic;

}
