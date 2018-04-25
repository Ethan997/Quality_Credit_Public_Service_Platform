package com.travischenn.platform.service;

import com.travischenn.platform.domain.DO.News;
import com.travischenn.platform.domain.VO.ListResultBean;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsService
 * 功能描述    : 消息服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 12:20
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface NewsService extends CommonTableService<News> {
    ListResultBean findByPage(int page , int size , String category);
}
