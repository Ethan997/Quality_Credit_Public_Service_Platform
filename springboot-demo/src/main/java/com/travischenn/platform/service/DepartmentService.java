package com.travischenn.platform.service;

import com.travischenn.platform.domain.DO.Department;
import com.travischenn.platform.domain.DO.Enterprise;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : DepartmentService
 * 功能描述    : 部门服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/21 19:32
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface DepartmentService extends CommonTableService<Department> {

    Department findByName(String name);

}
