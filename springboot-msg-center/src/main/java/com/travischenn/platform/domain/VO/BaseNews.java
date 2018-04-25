package com.travischenn.platform.domain.VO;

import com.travischenn.platform.domain.DO.News;
import com.travischenn.platform.domain.DTO.NewsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : BaseNews
 * 功能描述    : 消息推送
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/5 19:28
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseNews {

    /** 消息频道 */
    private String channel;

    /** 消息数量 */
    private int num;

    /** 消息列表 */
    private List<NewsDTO> list;

}
