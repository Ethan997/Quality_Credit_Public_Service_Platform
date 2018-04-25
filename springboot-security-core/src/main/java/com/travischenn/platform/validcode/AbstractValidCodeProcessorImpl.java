package com.travischenn.platform.validcode;

import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : AbstractValidCodeProcessorImpl
 * 功能描述    : 验证码流程处理器实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/15 20:08
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public abstract class AbstractValidCodeProcessorImpl<T> implements ValidCodeProcessor {

    /**
     * Spring 容器启动后,搜索所有 ValidCodeGenerator 类的实现
     */
    @Autowired
    private Map<String , ValidCodeGenerator> validCodeGeneratorMap;

    /**
     * Spring Session 工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 验证码类型
     */
    private String validCodeType;

    @Override
    public void create(ServletWebRequest servletWebRequest) throws IOException, ServletRequestBindingException, ClientException {

        // 生成指定类型的验证码
        T validCode = generate(servletWebRequest);

        // 将验证码储存到 Session 中
        save(servletWebRequest , validCode);

        // 将验证码发送出去
        send(servletWebRequest , validCode);

    }

    /**
     * 生成验证码
     * @param servletWebRequest 请求响应对象
     * @return 验证码子类
     */
    @SuppressWarnings("unchecked")
    protected T generate(ServletWebRequest servletWebRequest){

        // 从请求路径中拿到验证码的类型
        validCodeType = StringUtils.substringAfter(servletWebRequest.getRequest().getRequestURI(), "/validCode/");

        // 根据请求的验证码类型获取指定的验证码生成器
        ValidCodeGenerator validCodeGenerator = validCodeGeneratorMap.get(validCodeType + "Generator");

        // 生成验证码
        return (T) validCodeGenerator.validCodeGenerate(servletWebRequest.getRequest());

    }

    /**
     * 将验证码储存到 Session 中
     * @param servletWebRequest 请求返回封装对象
     * @param validCode 验证码
     */
    protected void save( ServletWebRequest servletWebRequest  , T validCode){
        sessionStrategy.setAttribute(servletWebRequest , ValidCodeProcessor.SESSION_KEY_PREFIX + StringUtils.upperCase(validCodeType), validCode);
    }

    /**
     * 发送验证码 , 由子类实现
     * @param servletWebRequest 请求返回封装对象
     * @param validCode 验证码
     */
    protected abstract void send( ServletWebRequest servletWebRequest , T validCode) throws IOException, ServletRequestBindingException, ClientException;

}