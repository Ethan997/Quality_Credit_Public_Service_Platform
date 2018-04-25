package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.Enterprise;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.repository.EnterpriseRepository;
import com.travischenn.platform.repository.FileInfoRepository;
import com.travischenn.platform.service.AlibabaOSSService;
import com.travischenn.platform.service.EnterpriseService;
import com.travischenn.platform.util.ConvertUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : EnterpriseServiceImpl
 * 功能描述    : 企业服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/21 19:58
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private AlibabaOSSService alibabaOSSService;

    @Override
    public Enterprise create(Enterprise enterprise) {
        valid(enterprise,true);
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Enterprise update(Enterprise enterprise) {
        Enterprise enterprise_in_db = findById(enterprise.getId());
        BeanUtils.copyProperties(enterprise , enterprise_in_db);
        valid(enterprise_in_db , false);
        return enterpriseRepository.save(enterprise_in_db);
    }

    @Override
    public Enterprise updateField(UpdateBean updateBean) {

        Enterprise enterprise_in_db = findById(updateBean.getId());

        // 反射赋值
        Field filed;
        Class<Enterprise> newsClass = Enterprise.class;

        try {
            filed = newsClass.getDeclaredField(updateBean.getField());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("字段:"+updateBean.getField()+"不存在");
        }

        filed.setAccessible(true);
        ReflectionUtils.setField(filed, enterprise_in_db, updateBean.getValue());

        valid(enterprise_in_db , false);

        return enterpriseRepository.save(enterprise_in_db);

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

                Enterprise enterprise_item = enterpriseRepository.findOne(Integer.parseInt(id));

                if(enterprise_item != null){

                    // 删除企业对象
                    enterpriseRepository.delete(enterprise_item);

                    // 社会责任报告
                    String srrUrl = enterprise_item.getSrrUrl();

                    // 质量信用报告
                    String qcfUrl = enterprise_item.getQcfUrl();

                    // 质量信用档案
                    String srpUrl = enterprise_item.getSrpUrl();

                    if(!StringUtils.isBlank(srrUrl)){

                        // 添加key
                        os_key_list.add(alibabaOSSService.getKeyFromUrl(srrUrl));

                        // 删除本地文件记录
                        fileInfoRepository.deleteFileInfoByHref(srrUrl);

                    }

                    if(!StringUtils.isBlank(qcfUrl)){
                        os_key_list.add(alibabaOSSService.getKeyFromUrl(qcfUrl));
                        fileInfoRepository.deleteFileInfoByHref(qcfUrl);
                    }

                    if(!StringUtils.isBlank(srpUrl)){
                        os_key_list.add(alibabaOSSService.getKeyFromUrl(srpUrl));
                        fileInfoRepository.deleteFileInfoByHref(srpUrl);
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
    public Enterprise findById(int id) {

        Enterprise enterprise_in_db = enterpriseRepository.findOne(id);

        if(enterprise_in_db == null){
            throw new RuntimeException("企业ID为: "+id+"的企业不存在");
        }

        return enterprise_in_db;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResultBean findByPage(int page, int size) {

        PageRequest pageable = new PageRequest(page-1, size);

        Page<Enterprise> all = enterpriseRepository.findAll(pageable);
        List<Enterprise> enterprise_list = all.getContent();

        ListResultBean listResultBean = new ListResultBean();
        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(enterprise_list.size());
        listResultBean.setData(enterprise_list);
        return listResultBean;

    }

    @Override
    public void valid(Enterprise enterprise , boolean same) {

        // 企业名称
        String name = enterprise.getName();

        // 注册时间
        Date registrationTime = enterprise.getRegistrationTime();

        Enterprise enterprise_in_db = enterpriseRepository.findEnterpriseByName(name);

        // 企业名称不得重复
        if(enterprise_in_db != null&&same){
            throw new RuntimeException("企业名称为: "+name+"的企业已经存在");
        }

        LocalDateTime localDateTime = ConvertUtil.DateToLocalDateTime(registrationTime);

        // 注册时间>现在的时间
        if(LocalDateTime.now().isBefore(localDateTime)){
            throw new RuntimeException("企业注册的时间: "+registrationTime+"应该小于当前时间");
        }

        // TODO 社会统一信用代码校验

        // TODO 链接合法性校验

    }

    @Override
    public Enterprise findEnterpriseByCategory(String category , String value) {

        Enterprise enterprise = new Enterprise();

        if(StringUtils.equals(category , "name")){

            Enterprise enterprise_by_name = enterpriseRepository.findEnterpriseByName(value);

            if(enterprise_by_name != null){
                BeanUtils.copyProperties(enterprise_by_name , enterprise);
            }else{
                throw new RuntimeException("名称为"+value+"的公司不存在");
            }

        }else if(StringUtils.equals(category , "agencyCode")){

            Enterprise enterprise_by_agencyCode = enterpriseRepository.findEnterpriseByAgencyCode(value);

            if(enterprise_by_agencyCode != null){
                BeanUtils.copyProperties(enterprise_by_agencyCode , enterprise);
            }else{
                throw new RuntimeException("机构代码为"+value+"的公司不存在");
            }

        }else{
            throw new RuntimeException("搜索项有误");
        }

        return enterprise;

    }
}
