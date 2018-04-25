package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.User;
import org.springframework.data.domain.Example;
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
 * 类 名 称    : UserRepository
 * 功能描述    : 用户对象数据库操作接口
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 9:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("delete from User u where u.id in ?1")
    void deleteByIds(List<Integer> id);

    User findUserByUsername(String username);

    Page<User> findUsersByDeletedEquals(String delete_status , Pageable pageable);

}
