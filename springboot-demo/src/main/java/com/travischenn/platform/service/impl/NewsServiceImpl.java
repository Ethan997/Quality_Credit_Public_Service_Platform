package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.News;
import com.travischenn.platform.domain.DTO.NewsDTO;
import com.travischenn.platform.domain.DTO.UserDTO;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.NewsCaregoryEnum;
import com.travischenn.platform.repository.NewsRepository;
import com.travischenn.platform.service.NewsService;
import com.travischenn.platform.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsServiceImpl
 * 功能描述    : 消息服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 12:21
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News create(News news) {

        UserDTO current_user = userService.findUserByThead();

        if(current_user == null){
            throw new RuntimeException("获取当前登录用户失败,请您备份好当前编辑的元素。重新登录后再次提交");
        }

        news.setCreateUser(current_user.getRealname());
        news.setCreateTime(new Date());
        valid(news , false);
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {

        // 获取对象
        News news_in_db = findById(news.getId());

        // 同步修改数据
        news_in_db.setTitle(news.getTitle());
        news_in_db.setContentType(news.getContentType());
        news_in_db.setContent(news.getContent());

        // 合法用户校验
        valid(news_in_db , true);

        return newsRepository.save(news_in_db);

    }

    @Override
    public News updateField(UpdateBean updateBean) {

        // 获取对象
        News news_in_db = findById(updateBean.getId());

        if(StringUtils.equals(updateBean.getField() , "password")){
            throw new RuntimeException("请通过正规方式修改密码");
        }

        if(StringUtils.equals(updateBean.getField() , "username")){
            throw new RuntimeException("用户名不允许修改");
        }

        // 反射赋值
        Field filed;
        Class<News> newsClass = News.class;

        try {
            filed = newsClass.getDeclaredField(updateBean.getField());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("字段:"+updateBean.getField()+"不存在");
        }

        filed.setAccessible(true);
        ReflectionUtils.setField(filed, news_in_db, updateBean.getValue());

        valid(news_in_db , true);

        return newsRepository.save(news_in_db);

    }

    @Override
    public void deleteByIds(String ids) {

        try {
            List<String> list = Arrays.asList(ids.split(","));
            newsRepository.deleteByIds(list.stream().map(Integer::parseInt).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException("批量删除失败:"+e.getMessage());
        }

    }

    @Override
    public News findById(int id) {

        News news_in_db = newsRepository.findOne(id);

        if(news_in_db ==null){
            throw new RuntimeException("ID:"+id+"对应的消息不存在");
        }

        return news_in_db;

    }

    @Override
    public ListResultBean findByPage(int page, int size) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResultBean findByPage(int page, int size , String category) {

        // 构建分页对象
        PageRequest pageable = new PageRequest(page-1, size , new Sort(Sort.Direction.DESC, "id"));

        // 通过类目分页获取文章列表
        Page<News> news_list_pageable = newsRepository.findNewsByContentType(category , pageable);

        // 将 news_list_pageable 中的枚举值进行转换
        List<News> news_list_to_category = news_list_pageable.getContent().stream().peek(user -> user.setContentType(NewsCaregoryEnum.valueOf(user.getContentType()).getDescribe())).collect(Collectors.toList());

        // 初始化返回对象
        ListResultBean listResultBean = new ListResultBean();

        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(newsRepository.findNewsByContentType(category).size());

        // 将返回结果中的富文本内容进行剥离
        listResultBean.setData(news_list_to_category.stream().map(news -> {
            NewsDTO newsDTO = new NewsDTO();
            BeanUtils.copyProperties(news , newsDTO);
            newsDTO.setContentDetailUrl("news-content.html?id="+newsDTO.getId());
            return newsDTO;
        }).collect(Collectors.toList()));

        return listResultBean;

    }

    @Override
    public void valid(News news , boolean same) {

        // 消息种类
        String contentType = news.getContentType();

        try {
            NewsCaregoryEnum.valueOf(contentType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("消息类型: "+contentType+"异常 , 请修改后重新提交");
        }

    }

}
