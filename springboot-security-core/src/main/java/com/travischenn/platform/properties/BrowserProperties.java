package com.travischenn.platform.properties;

import com.travischenn.platform.enums.AuthRedirectType;
import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 9:33
 * Created    : IntelliJ IDEA
 * **************************************************************
 */

@Data
public class BrowserProperties {

    /**
     * 用户自定义登录页面路径
     */
    private String loginPageUrl = "/DIYLoginPage.html";

    /**
     * 认证结果返回方式  [默认值: JSON格式]
     */
    private AuthRedirectType authRedirectType = AuthRedirectType.JSON;

    /**
     * 记住登录状态的时间  [默认值: 一周]
     */
    private int rememberMeSecond = 60 * 60 * 7;

}
