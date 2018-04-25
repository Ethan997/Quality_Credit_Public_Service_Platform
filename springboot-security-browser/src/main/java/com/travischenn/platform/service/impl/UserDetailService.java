package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.Member;
import com.travischenn.platform.enums.LoginEums;
import com.travischenn.platform.repository.MemberRepository;
import com.travischenn.platform.repository.RoleRepository;
import com.travischenn.platform.repository.relation.MemberRoleRelationRepository;
import com.travischenn.platform.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepoeposity;

    @Autowired
    private MemberRoleRelationRepository memberRoleRelationRepository;

    /**
     * 根据用户名查找用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        // 根据用户名查询用户详情
        Member member = memberRepository.findMemberByUsername(username);

        if(member != null){

            // 根据 用户ID 获取 用户的权限ID
            int roleId = memberRoleRelationRepository.findMemberRoleRelationByMemId(member.getId()).getRoleId();

            // 根据 权限ID 获取 权限名称
            String roleName = roleRepoeposity.findOne(roleId).getName();

            return new User(username ,
                    member.getPassword(),
                    !ConvertUtil.numberToBoolean(member.getEnable()),
                    true,
                    true,
                    !ConvertUtil.numberToBoolean(member.getLocked()),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + roleName));

        }else{
            throw new RuntimeException(LoginEums.LOGIN_IN_FAILED_BECAUSE_NULL_USERNAME.getDescribe());
        }
    }

}
