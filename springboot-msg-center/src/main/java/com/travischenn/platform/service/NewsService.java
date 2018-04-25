package com.travischenn.platform.service;

import com.travischenn.platform.enums.NewsCategoryEnum;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/6 19:03
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public interface NewsService {

    /**
     * 获取新闻的数量
     * @param newsCategoryEnum 新闻种类
     */
    int getNewsSize(NewsCategoryEnum newsCategoryEnum);

    /**
     * 判断新闻是否唯一
     * @param newsCategoryEnum 新闻种类
     *
     * @return true   新闻唯一
     *         false  新闻不唯一
     */
    boolean isNewUnique(String newsCategoryEnum);

    /**
     * 判断是否是合法的新闻类型
     *
     * @param newsCategoryEnum 新闻类型字符串
     */
    NewsCategoryEnum findNewsByString(String newsCategoryEnum);

}
