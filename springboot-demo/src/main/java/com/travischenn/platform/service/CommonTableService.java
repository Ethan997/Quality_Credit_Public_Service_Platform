package com.travischenn.platform.service;

import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : CommonTableService
 * 功能描述    : 通用服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/21 19:50
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface CommonTableService<T> {

    T create(T obj);

    T update(T obj);

    T updateField(UpdateBean updateBean);

    void deleteByIds(@NotBlank String ids);

    T findById(int id);

    ListResultBean findByPage(int page , int size);

    void valid(T obj , boolean same);

}
