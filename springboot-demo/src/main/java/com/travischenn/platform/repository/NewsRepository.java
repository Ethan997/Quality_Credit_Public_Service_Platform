package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsRepository
 * 功能描述    : 消息对象数据库操作接口
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 9:49
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface NewsRepository extends JpaRepository<News, Integer> {

    @Modifying
    @Query("delete from News n where n.id in ?1")
    void deleteByIds(List<Integer> id);

    Page<News> findNewsByContentType(String contentType , Pageable pageable);

    List<News> findNewsByContentType(String contentType);

}
