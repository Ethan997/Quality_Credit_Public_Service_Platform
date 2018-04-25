package com.travischenn.platform.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsDTO
 * 功能描述    : 新闻完整对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 19:17
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Data
public class NewsDTO {

    /** ID */
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

    /** 图片 */
    private String img;

    private String content;

    /** 审核状态 */
    private String auditStatus;

    /** 是否展示 */
    private String isShow;

    /** 跳转URL */
    private String url;

}
