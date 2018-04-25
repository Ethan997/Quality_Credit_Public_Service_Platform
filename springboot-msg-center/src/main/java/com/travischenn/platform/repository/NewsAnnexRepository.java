package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.NewsAnnex;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsAnnexRepository
 * 功能描述    : 新闻附件数据库操作类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/11 15:13
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface NewsAnnexRepository extends JpaRepository<NewsAnnex,Integer> {
}
