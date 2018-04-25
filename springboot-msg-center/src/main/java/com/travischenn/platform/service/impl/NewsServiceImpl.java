package com.travischenn.platform.service.impl;

import com.travischenn.platform.Properties.NewsProperties;
import com.travischenn.platform.enums.NewsCategoryEnum;
import com.travischenn.platform.enums.NewsExceptionEnum;
import com.travischenn.platform.repository.NewsRepository;
import com.travischenn.platform.service.NewsService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsServiceImpl
 * 功能描述    : 新闻服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/6 19:04
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsProperties newsProperties;

    @Override
    public int getNewsSize(NewsCategoryEnum newsCategoryEnum) {
        return newsRepository.findNewsByCategory(newsCategoryEnum.name()).size();
    }

    @Override
    public boolean isNewUnique(String newsCategoryEnum) {

        // 获取新闻类型
        NewsCategoryEnum newsCategory = findNewsByString(newsCategoryEnum);

        // 当没有唯一的新闻类型时 , 不进行后续判断
        if(StringUtils.isBlank(newsProperties.getAdmin().getUnique())){
            return false;
        }

        // 获取唯一的新闻列表
        String[] uniqueNewsList = newsProperties.getAdmin().getUnique().split("/");

        // 判断传入的新闻类型是否在唯一的新闻列表中

        return ArrayUtils.contains(uniqueNewsList, newsCategory.name());
    }


    @Override
    public NewsCategoryEnum findNewsByString(@NotBlank String newsCategoryEnum) {

        NewsCategoryEnum newsCategory;

        // 新闻消息类型格式校验
        try {
            newsCategory = NewsCategoryEnum.valueOf(newsCategoryEnum);
        } catch (Exception e) {
            throw new RuntimeException(NewsExceptionEnum.INVALID_NEWS_CATEGORY.getDescribe());
        }

        return newsCategory;
    }

}
