package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.Department;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.repository.DepartmentRepository;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import com.travischenn.platform.service.DepartmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : DepartmentServiceImpl
 * 功能描述    : 部门服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/23 12:21
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @Override
    public Department create(Department department) {

        valid(department , false);

        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department obj) {

        Department department_in_db = findById(obj.getId());
        BeanUtils.copyProperties(obj , department_in_db);
        department_in_db.setIsPublic(StringUtils.equals("on" , obj.getIsPublic())?"true":"false");

        return departmentRepository.save(department_in_db);
    }

    @Override
    public Department updateField(UpdateBean updateBean) {

        // 获取对象
        Department department_in_db = findById(updateBean.getId());

        // 反射赋值
        Field filed;
        Class<Department> newsClass = Department.class;

        try {
            filed = newsClass.getDeclaredField(updateBean.getField());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("字段:"+updateBean.getField()+"不存在");
        }

        filed.setAccessible(true);
        ReflectionUtils.setField(filed, department_in_db, updateBean.getValue());

        valid(department_in_db , true);

        return departmentRepository.save(department_in_db);

    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {

        try {

            // 分割需要删除的 ID
            List<String> list = Arrays.asList(ids.split(","));

            // 准备 key 容器
            List<String> os_key_list = new ArrayList<>();

            list.forEach(id->{

                Department department_item = departmentRepository.findOne(Integer.parseInt(id));

                if(department_item != null){

                    // 删除部门对象
                    departmentRepository.delete(department_item);

                    // 部门岗位标准
                    String pStandardUrl = department_item.getPStandardUrl();

                    // 部门技术标准
                    String tStandardUrl = department_item.getTStandardUrl();

                    if(!StringUtils.isBlank(pStandardUrl)){

                        // 添加key
                        os_key_list.add(alibabaOSSService.getKeyFromUrl(pStandardUrl));

                        // 删除本地文件记录
                        fileInfoRepository.deleteFileInfoByHref(pStandardUrl);

                    }

                    if(!StringUtils.isBlank(tStandardUrl)){
                        os_key_list.add(alibabaOSSService.getKeyFromUrl(tStandardUrl));
                        fileInfoRepository.deleteFileInfoByHref(tStandardUrl);
                    }

                }else{
                    throw new RuntimeException("ID为: "+id+"的企业不存在");
                }

            });

            // 删除 OSS 文件
            if(os_key_list.size() != 0){
                alibabaOSSService.deleteFiles(os_key_list);
            }

        } catch (Exception e) {
            throw new RuntimeException("批量删除失败:"+e.getMessage());
        }

    }

    @Override
    public Department findById(int id) {

        Department department_in_db = departmentRepository.findOne(id);

        if(department_in_db ==null){
            throw new RuntimeException("ID:"+id+"对应的对象不存在");
        }

        return department_in_db;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResultBean findByPage(int page, int size) {

        // 获取分页对象
        PageRequest pageable = new PageRequest(page-1, size);

        // 分页分类查询
        Page<Department> department_in_page = departmentRepository.findAll(pageable);

        // 封装返回 Bean
        ListResultBean listResultBean = new ListResultBean();
        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(departmentRepository.findAll().size());
        listResultBean.setData(department_in_page.getContent());

        return listResultBean;

    }

    @Override
    public void valid(Department department, boolean exist) {

        String department_name = department.getName();
        Department department_by_name = departmentRepository.findDepartmentByName(department_name);

        // 存在性校验
        if(exist == (department_by_name == null)){
            String result = exist ? "不存在" : "已存在";
            throw new RuntimeException("名称为: "+department_name+"的部门"+result);
        }

        // 判断公开枚举是否合法
        try {
            Boolean.valueOf(department.getIsPublic());
        } catch (Exception e) {
            throw new RuntimeException("公开状态异常");
        }

    }

    @Override
    public Department findByName(String name) {

        Department department = departmentRepository.findDepartmentByName(name);

        if(department == null){
            throw new RuntimeException("部门: "+name+" 不存在");
        }


        // TODO 当提交为 "" 时 sava 会默认至 null
        if(StringUtils.isBlank(department.getPStandardUrl())){
            department.setPStandardUrl("");
        }

        if(StringUtils.isBlank(department.getTStandardUrl())){
            department.setTStandardUrl("");
        }

        return department;
    }
}
