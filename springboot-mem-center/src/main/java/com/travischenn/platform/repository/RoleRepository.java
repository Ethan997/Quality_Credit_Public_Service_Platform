package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 20:17
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
