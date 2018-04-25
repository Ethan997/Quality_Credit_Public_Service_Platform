package com.travischenn.platform.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.travischenn.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UserDetailService
 * 功能描述    : 用户登录信息校验服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 18:26
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Slf4j
@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名查找用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        // 根据用户名查询用户详情
        com.travischenn.platform.domain.DO.User user = userService.findUserByUsername(username);

        // 获取是否启用
        boolean is_deleted = !Boolean.valueOf(user.getDeleted());

        return new User(username,
                user.getPassword(),
                is_deleted,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));

    }
}
