package com.travischenn.platform.validcode.image;

import com.travischenn.platform.domain.ImageCode;
import com.travischenn.platform.validcode.ValidCodeGenerator;
import com.travischenn.platform.properties.SecurityProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ImageCodeGeneratorImpl
 * 功能描述    : 图片验证码生成器实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/14 16:33
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class ImageCodeGeneratorImpl implements ValidCodeGenerator {

    /**
     * 图形验证码配置项
     */
    @Getter
    @Setter
    private SecurityProperties securityProperties;

    @Override
    public ImageCode validCodeGenerate(HttpServletRequest request) {

        //宽度 -> [注意: 此处是一个请求级的配置]
        int width = ServletRequestUtils.getIntParameter(request, "width" , securityProperties.getValidCode().getImageCode().getWidth());

        //高度 -> [注意: 此处是一个请求级的配置]
        int height = ServletRequestUtils.getIntParameter(request, "height" , securityProperties.getValidCode().getImageCode().getHeight());

        //图形验证码长度
        int length = securityProperties.getValidCode().getImageCode().getLength();

        //图形验证码过期时间[单位: 秒]
        int expireInSecond = securityProperties.getValidCode().getImageCode().getExpireSecond();

        //新建一个图片对象
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB);

        //从图片对象中获取画笔对象
        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();

        //验证码长度 -> [注意: 这里是一个应用级的配置]
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 25);
        }

        g.dispose();

        return new ImageCode(image, sRand.toString(), expireInSecond);
    }


    /**
     * 生成随机背景条纹
     *
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
