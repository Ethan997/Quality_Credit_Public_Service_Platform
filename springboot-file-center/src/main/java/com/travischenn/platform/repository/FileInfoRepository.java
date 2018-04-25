package com.travischenn.platform.repository;

import com.travischenn.platform.domain.DO.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.File;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : FileInfoRepository
 * 功能描述    : 文件详情数据库操作对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/16 12:17
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface FileInfoRepository extends JpaRepository<FileInfo , Integer> {

    List<FileInfo> findFileInfosByName(String name);

    Page<FileInfo> findFileInfoByHrefStartingWith(String name , Pageable pageable);

    List<FileInfo> findFileInfoByHrefStartingWith(String name);

    void deleteFileInfoByHref(String href);

    @Modifying
    @Query("delete from FileInfo f where f.id in ?1")
    void deleteByIds(List<Integer> id);
}
