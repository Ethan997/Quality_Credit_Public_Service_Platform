package com.travischenn.platform.domain.VO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCode
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/14 21:20
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
public class SmsCode {

    /**
     * 验证码
     */
    private String code;

    /**
     * 验证码过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 验证码构造函数
     * @param code 验证码
     * @param expireSecond 验证码过期时间 = [ 当前时间 + 预期过期时间]  (例: expireSecond =  39584939284[距离 1970 12月 31号 的秒数] + 3600[验证码的有效期] )
     */
    public SmsCode(String code, int expireSecond) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireSecond);
    }

}
