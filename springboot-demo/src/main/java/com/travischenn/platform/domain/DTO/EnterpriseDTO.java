package com.travischenn.platform.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : Enterprise
 * 功能描述    : 企业传输层对象
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
public class EnterpriseDTO {

    /** 企业ID */
    @Id
    @GeneratedValue
    private int id;

    /** 企业名称 */
    @Size(min = 1 , message = "长度不能为0")
    @NotBlank(message = "企业名称不能为空")
    private String name;

    /** 注册时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp registrationTime;

    /** 法人代表 */
    @Size(min = 1 , message = "长度不能为0")
    @NotBlank(message = "法人代表不能为空")
    private String legalRepresentative;

}
