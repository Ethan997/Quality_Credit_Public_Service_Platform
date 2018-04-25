package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.Rank;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.RankCategoryEnum;
import com.travischenn.platform.repository.RankRepository;
import com.travischenn.platform.service.RankService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 22:54
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankRepository rankRepository;

    @Override
    public Rank create(Rank rank) {
        valid(rank , true);
        return rankRepository.save(rank);
    }

    @Override
    public Rank update(Rank rank) {
        // 获取对象
        Rank rank_in_db = findById(rank.getId());

        // 同步修改数据
        rank_in_db.setName(rank.getName());
        rank_in_db.setType(RankCategoryEnum.des2Enum(rank.getType()).name());

        // 合法用户校验
        valid(rank_in_db , true);

        return rankRepository.save(rank_in_db);
    }

    @Override
    public Rank updateField(UpdateBean updateBean) {

        // 获取对象
        Rank rank_in_db = findById(updateBean.getId());

        if(StringUtils.equals(updateBean.getField() , "password")){
            throw new RuntimeException("请通过正规方式修改密码");
        }

        if(StringUtils.equals(updateBean.getField() , "username")){
            throw new RuntimeException("用户名不允许修改");
        }

        // 反射赋值
        Field filed;
        Class<Rank> newsClass = Rank.class;

        try {
            filed = newsClass.getDeclaredField(updateBean.getField());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("字段:"+updateBean.getField()+"不存在");
        }

        filed.setAccessible(true);
        ReflectionUtils.setField(filed, rank_in_db, RankCategoryEnum.des2Enum(updateBean.getValue()).name());

        valid(rank_in_db , true);

        return rankRepository.save(rank_in_db);
    }

    @Override
    public void deleteByIds(String ids) {
        try {
            List<String> list = Arrays.asList(ids.split(","));
            rankRepository.deleteByIds(list.stream().map(Integer::parseInt).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException("批量删除失败:"+e.getMessage());
        }
    }

    @Override
    public Rank findById(int id) {

        Rank rank_in_db = rankRepository.findOne(id);

        if(rank_in_db == null){
            throw new RuntimeException("ID: "+id+"对应的榜单对象不存在");
        }

        return rank_in_db;
    }

    @Override
    public ListResultBean findByPage(int page, int size) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResultBean findByPage(int page , int size , String category) {

        // 获取分页对象
        PageRequest pageable = new PageRequest(page-1, size , new Sort(Sort.Direction.DESC, "id"));

        // 分页分类查询
        List<Rank> rank_in_page = rankRepository.findRankByType(category , pageable);

        // 封装返回 Bean
        ListResultBean listResultBean = new ListResultBean();
        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(rankRepository.findRankByType(category).size());
        listResultBean.setData(rank_in_page.stream().peek(rank-> rank.setType(RankCategoryEnum.valueOf(rank.getType()).getDescribe())).collect(Collectors.toList()));

        return listResultBean;

    }

    @Override
    public void valid(Rank rank, boolean same) {
        try {
            RankCategoryEnum.valueOf(rank.getType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("榜单类型:"+rank.getType()+"为非法类型");
        }

    }
}
