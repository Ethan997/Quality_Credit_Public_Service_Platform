package com.travischenn.platform.domain.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : FileInfo
 * 功能描述    : 文件详情对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/19 21:30
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
public class FileInfo {

    /** 文件ID */
    @Id
    @GeneratedValue
    private int id;

    /** 名称 */
    @NotBlank(message = "文件名称划分不能为空")
    private String name;

    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人真实姓名 */
    @NotBlank(message = "创建人真实姓名不能为空")
    private String createUser;

    /** 文件 OSS 路径 */
    @NotBlank(message = "文件 OSS 路径不能为空")
    private String href;

}
