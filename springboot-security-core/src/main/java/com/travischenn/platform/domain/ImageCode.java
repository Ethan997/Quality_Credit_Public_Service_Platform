package com.travischenn.platform.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ImageCode
 * 功能描述    : 图形验证码
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/11 14:49
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCode extends SmsCode {

    /**
     * 图形验证码图片对象
     */
    private BufferedImage image;

    /**
     * @param image 图片验证码
     */
    public ImageCode(BufferedImage image , String validCode , int expireSecond) {
        super(validCode , expireSecond);
        this.image = image;
    }

}