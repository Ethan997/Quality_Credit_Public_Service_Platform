package com.travischenn.platform.service;

import com.travischenn.platform.domain.DO.Rank;
import com.travischenn.platform.domain.VO.ListResultBean;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : RankService
 * 功能描述    : 榜单服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 22:53
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface RankService extends CommonTableService<Rank>{

    ListResultBean findByPage(int page , int size , String category);

}
