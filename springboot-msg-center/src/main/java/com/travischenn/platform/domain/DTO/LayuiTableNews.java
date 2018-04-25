package com.travischenn.platform.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.travischenn.platform.deserializer.CustomJsonDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : LayuiTableNews
 * 功能描述    : 工作动态
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/4 19:36
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LayuiTableNews {

    /** ID */
    @Id
    @GeneratedValue
    private String id;

    /** 标题 */
    private String title;

    /** 发表时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**  发表人 */
    private String src;

    /** 审核状态 */
    private String auditStatus;

    /** 是否展示 */
    private String isShow;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public void setTime(Date time) {
        this.time = time;
    }

}
