package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsRepository
 * 功能描述    : 新闻数据库操作类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/3 21:28
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public interface NewsRepository extends JpaRepository<News, String> {

    @Query(value = "select * from news n where n.category = ?1 AND is_show = 1 AND audit_status = 1" , nativeQuery = true)
    List<News> findNewsByCategoryWithPass(@Param("category")String category);

    @Modifying
    @Query("delete from News n where n.id in ?1")
    void deleteByIds(List<String> id);

    List<News> findNewsByCategory(String category);

}
