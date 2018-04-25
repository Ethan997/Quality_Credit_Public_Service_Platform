package com.travischenn.platform.controller;

import com.travischenn.platform.domain.DO.Member;
import com.travischenn.platform.domain.DTO.MemberDTO;
import com.travischenn.platform.domain.VO.BaseMessage;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.repository.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : MemberController
 * 功能描述    : 用户控制器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/20 15:33
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    /** 用户数据库查询对象 */
    @Autowired
    private MemberRepository memberRepository;

    /**
     * 获取当前登录用户的可见信息
     *
     * 可见信息 = 真实姓名 + 性别
     */
    @GetMapping
    @RequestMapping("/dto")
    public BaseMessage<MemberDTO> getCurrentLoginUserMessage(){

        // 获取当前登录的对象
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // 根据用户名获取对象真实姓名
        Member member = memberRepository.findMemberByUsername(username);

        // 新建用户可见对象用于接收用户信息
        MemberDTO memberDTO = new MemberDTO();

        // 将用户信息复制到用户可见对象中
        BeanUtils.copyProperties(member , memberDTO);

        return new BaseMessage<>(ResultEnum.SUCCESS , memberDTO);

    }

}
