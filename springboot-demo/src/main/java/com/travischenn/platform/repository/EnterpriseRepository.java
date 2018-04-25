package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : EnterpriseRepository
 * 功能描述    : 企业对象数据库操作接口
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 9:49
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    @Modifying
    @Transactional
    @Query("delete from Enterprise e where e.id in ?1")
    void deleteByIds(List<Integer> id);

    Enterprise findEnterpriseByName(String name);

    Enterprise findEnterpriseByAgencyCode(String agencyCode);

}
