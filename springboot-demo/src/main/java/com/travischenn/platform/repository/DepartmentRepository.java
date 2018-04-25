package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : DepartmentRepository
 * 功能描述    : 部门对象数据库操作接口
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 9:48
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department findDepartmentByName(String name);

    @Modifying
    @Query("delete from Department d where d.id in ?1")
    void deleteByIds(List<Integer> id);

}
