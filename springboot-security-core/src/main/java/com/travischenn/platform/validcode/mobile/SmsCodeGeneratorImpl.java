package com.travischenn.platform.validcode.mobile;

import com.travischenn.platform.domain.SmsCode;
import com.travischenn.platform.validcode.ValidCodeGenerator;
import com.travischenn.platform.properties.SecurityProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : SmsCodeGeneratorImpl
 * 功能描述    : 验证码生成器实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/15 10:56
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class SmsCodeGeneratorImpl implements ValidCodeGenerator {

    @Getter
    @Setter
    private SecurityProperties securityProperties;

    @Override
    public SmsCode validCodeGenerate(HttpServletRequest request) {

        // 生成用户指定长度的随机数
        String randomNumeric = RandomStringUtils.randomNumeric(securityProperties.getValidCode().getSmsCode().getLength());

        // 获取用户指定的过期时间
        int expireSecond = securityProperties.getValidCode().getSmsCode().getExpireSecond();

        return new SmsCode(randomNumeric , expireSecond);

    }

}
