package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ListRepository
 * 功能描述    : 列表消息处理类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/12 13:16
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface ListRepository extends JpaRepository<List , Integer> {

    @Modifying
    @Query("delete from List l where l.id in ?1")
    void deleteByIds(java.util.List<String> id);

    java.util.List<List> findListByType(String type);

}
