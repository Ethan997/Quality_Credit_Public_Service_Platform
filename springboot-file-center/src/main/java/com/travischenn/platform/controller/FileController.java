package com.travischenn.platform.controller;

import com.travischenn.platform.domain.VO.BaseMessage;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.service.AlibabaOSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/8 15:05
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @RequestMapping(value = "/upload/img", method = RequestMethod.POST , produces = "application/json;charset=UTF-8")
    @ResponseBody
    public BaseMessage<String> uploadImg(MultipartFile file , @Param("folder") String folder) {

        String alibabaOSSFileUrl;

        try {
            alibabaOSSFileUrl = alibabaOSSService.uploadFile(file, folder);
        } catch (IOException e) {
            e.printStackTrace();
            return new BaseMessage<>(ResultEnum.FAILED , "上传失败,失败原因:" + e.getMessage());
        }

        return new BaseMessage<>(ResultEnum.SUCCESS , alibabaOSSFileUrl);

    }

}
