package com.travischenn.platform.config;

import com.travischenn.platform.Properties.NewsProperties;
import com.travischenn.platform.handler.login.TCLoginOutSuccessHandler;
import com.travischenn.platform.validcode.image.ImageCodeFilter;
import com.travischenn.platform.handler.authentication.AuthenticationFailedHandler;
import com.travischenn.platform.handler.authentication.AuthenticationSuccessHandler;
import com.travischenn.platform.properties.SecurityProperties;
import com.travischenn.platform.validcode.mobile.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UserCenterConfig
 * 功能描述    : 浏览器 Web 相关配置类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 17:04
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private NewsProperties newsProperties;

    @Autowired
    private AuthenticationSuccessHandler travisChennAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailedHandler travisChennAuthenticationFailedHandler;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private TCLoginOutSuccessHandler loginOutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
        imageCodeFilter.setAuthenticationFailedHandler(travisChennAuthenticationFailedHandler);
        imageCodeFilter.setSecurityProperties(securityProperties);
        imageCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailedHandler(travisChennAuthenticationFailedHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore( smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore( imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/authentication/form")
                    .successHandler(travisChennAuthenticationSuccessHandler)
                    .failureHandler(travisChennAuthenticationFailedHandler)
                    .and()
                .rememberMe()
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSecond())
                    .tokenRepository(persistentTokenRepository)
                    .userDetailsService(userDetailsService)
                    .and()
                .logout()
                    .logoutSuccessHandler(loginOutSuccessHandler)
                    .deleteCookies("JESSIONID")
                    .and()
                .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .antMatchers("/shutdown" ,
                            newsProperties.getWeb().getUrl() ,
                            newsProperties.getWeb().getStatics() ,
                            newsProperties.getWeb().getPage() ,
                            "/authentication/require" ,
                            securityProperties.getBrowser().getLoginPageUrl() ,
                            "/validCode/imageCode" ,
                            "/validCode/smsCode").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .csrf()
                    .disable()
                    .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .headers()
                    .frameOptions()
                    .disable();
    }
}
