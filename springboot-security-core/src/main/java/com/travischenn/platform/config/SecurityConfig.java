package com.travischenn.platform.config;

import com.travischenn.platform.handler.login.TCLoginOutSuccessHandler;
import com.travischenn.platform.validcode.mobile.SmsCodeSender;
import com.travischenn.platform.validcode.ValidCodeGenerator;
import com.travischenn.platform.validcode.image.ImageCodeGeneratorImpl;
import com.travischenn.platform.validcode.mobile.SmsCodeGeneratorImpl;
import com.travischenn.platform.validcode.mobile.SmsCodeSenderImpl;
import com.travischenn.platform.properties.SecurityProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 21:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    /**
     * Spring Security 配置项
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    /**
     * 用户自定义 [图像验证码生成逻辑] 接口
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidCodeGenerator imageCodeGenerator(){
        ImageCodeGeneratorImpl imageCodeGenerate = new ImageCodeGeneratorImpl();
        imageCodeGenerate.setSecurityProperties(securityProperties);
        return imageCodeGenerate;
    }

    /**
     * 用户自定义 [短信验证码生成逻辑] 接口
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public ValidCodeGenerator smsCodeGenerator(){
        SmsCodeGeneratorImpl smsCodeGenerator = new SmsCodeGeneratorImpl();
        smsCodeGenerator.setSecurityProperties(securityProperties);
        return smsCodeGenerator;
    }

    /**
     * 用户自定义 [短信验证码发送] 接口
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new SmsCodeSenderImpl();
    }

    /**
     * 注册记记住登录状态持久化工具类对象
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        // 当系统启动时自动生成记住我的 persistent-login 表 [只需要生成一次，后续再次启动不需要自动生成]
        //jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }

    /**
     * 注册记记住登录状态持久化工具类对象
     */
    @Bean
    public TCLoginOutSuccessHandler loginOutSuccessHandler(){
        return new TCLoginOutSuccessHandler();
    }

}