package com.travischenn.platform.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsDTO
 * 功能描述    : 消息传输层对象
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
public class NewsDTO {

    /** ID */
    @Id
    @GeneratedValue
    private int id;

    /** 标题 */
    @NotBlank(message = "消息标题不能为空")
    private String title;

    /** 创建人真实姓名 */
    private String createUser;

    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 类型 */
    @NotBlank(message = "消息类型不能为空")
    private String contentType;

    /** 文章详情页地址 */
    @NotBlank(message = "文章详情页地址不能为空")
    private String contentDetailUrl;

}
