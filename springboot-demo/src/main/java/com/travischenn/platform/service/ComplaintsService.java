package com.travischenn.platform.service;

import com.travischenn.platform.domain.DO.Complaints;

import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ComplaintsService
 * 功能描述    : 投诉服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/24 1:22
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface ComplaintsService extends CommonTableService<Complaints> {

    List<Complaints> findAll();

}
