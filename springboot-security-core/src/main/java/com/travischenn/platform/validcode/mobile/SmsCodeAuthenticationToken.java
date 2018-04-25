package com.travischenn.platform.validcode.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeAuthenticationToken
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/17 9:36
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 手机号码
     */
    private final Object mobile;

    /**
     * 默认构造函数
     *
     * @param mobile 手机号码
     */
    public SmsCodeAuthenticationToken(Object mobile) {
        super(null);
        this.mobile = mobile;
        setAuthenticated(false);
    }

    /**
     * 权限修改构造函数
     *
     * @param mobile      手机号码
     * @param authorities 权限列表
     */
    public SmsCodeAuthenticationToken(Object mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.mobile = mobile;
        super.setAuthenticated(true); // must use super, as we override
    }

    /**
     * 获取密码
     *
     * [注意]: 手机与缓存中的数据判断基于过滤器链实现
     */
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.mobile;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("无法在此处进行授权 - 请使用含有 Collection<? extends GrantedAuthority> authorities 的构造函数进行该操作");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
