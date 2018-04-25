package com.travischenn.platform.validcode.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeAuthenticationFilter
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/17 10:04
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // 请求参数名称
    public static final String TRAVISCHENN_FORM_MOBILE_KEY = "mobile";

    private String mobileParameter = TRAVISCHENN_FORM_MOBILE_KEY;

    // 只支持 POST 方法
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        // 设置过滤器默认的拦截请求
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    /**
     * 拼装 Token 并传递给 AuthenticationManager
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws AuthenticationException 认证失败异常
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 请求方法拦截
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("当前权限认证不支持: " + request.getMethod() + "方式");
        }

        // 从请求中获取手机号码
        String mobile = obtainMobile(request);

        // 手机号码判空
        if (mobile == null) {
            mobile = "";
        }

        // 删除手机号码中的空格
        mobile = mobile.trim();

        // 组装 SmsToken
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 从请求中拿到手机号码
     *
     * @param request 请求对象封装
     * @return 手机号码
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 设置手机号码
     *
     * @param mobileParameter 手机号码
     */
    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "手机号码不能为空");
        this.mobileParameter = mobileParameter;
    }

    /**
     * 判断是否只支持 POST 形式请求
     *
     * @param postOnly true 支持 ， false 不支持
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

}
