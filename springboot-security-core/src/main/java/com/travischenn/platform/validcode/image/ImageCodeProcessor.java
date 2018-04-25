package com.travischenn.platform.validcode.image;

import com.travischenn.platform.domain.ImageCode;
import com.travischenn.platform.validcode.AbstractValidCodeProcessorImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ImageCodeProcessor
 * 功能描述    : 图形验证码发送器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/16 11:15
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Component(value = "imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidCodeProcessorImpl<ImageCode> {

    @Override
    protected void send(ServletWebRequest servletWebRequest, ImageCode imageCode) throws IOException {

        // 写出到返回对象的输出流中
        ImageIO.write(imageCode.getImage() , "JPEG" , servletWebRequest.getResponse().getOutputStream());

    }

}
