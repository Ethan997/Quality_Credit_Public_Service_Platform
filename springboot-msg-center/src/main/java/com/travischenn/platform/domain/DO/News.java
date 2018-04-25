package com.travischenn.platform.domain.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : BaseNews
 * 功能描述    : 新闻 -> 实体类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/3 21:11
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@DynamicUpdate
public class News {

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

    /** 类目 */
    private String category;

    /** 附件ID */
    private int annexId;

    /** 审核状态 */
    private String auditStatus;

    /** 是否展示 */
    private String isShow;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setTime(Date time) {
        this.time = time;
    }

}
