package com.travischenn.platform.validcode.mobile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeAuthenticationProvider
 * 功能描述    : Authentication Manager 权限搜索提供者
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/17 10:20
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 获取 Token
        SmsCodeAuthenticationToken smsCodeAuthenticationTokenBefore = (SmsCodeAuthenticationToken) authentication;

        // 根据 Token 中的手机号码拿到用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationTokenBefore.getPrincipal());

        // 用户信息判空
        if(userDetails == null){
            throw  new InternalAuthenticationServiceException("获取用户信息失败");
        }

        // 重新组装已认证的 Token
        SmsCodeAuthenticationToken smsCodeAuthenticationTokenAfter = new SmsCodeAuthenticationToken(userDetails , userDetails.getAuthorities());

        // 同步 details
        smsCodeAuthenticationTokenAfter.setDetails(smsCodeAuthenticationTokenBefore.getDetails());

        return smsCodeAuthenticationTokenAfter;
    }

    /**
     * 判断当前 Provider 所支持的 Token 类型
     * @param authentication Token
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
