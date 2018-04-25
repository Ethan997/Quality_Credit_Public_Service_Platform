package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.Complaints;
import com.travischenn.platform.domain.DO.News;
import com.travischenn.platform.domain.DO.Rank;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.ComplaintsStatusEnum;
import com.travischenn.platform.enums.RankCategoryEnum;
import com.travischenn.platform.repository.ComplaintsReposity;
import com.travischenn.platform.service.ComplaintsService;
import com.travischenn.platform.util.RegexpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ComplaintsServiceImpl
 * 功能描述    : 投诉服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/24 1:23
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class ComplaintsServiceImpl implements ComplaintsService {

    @Autowired
    private ComplaintsReposity complaintsReposity;

    @Override
    public Complaints create(Complaints complaints) {

        // 初始化投诉状态
        complaints.setStatus(ComplaintsStatusEnum.INIT.name());

        // 合法投诉校验
        valid(complaints , true);

        // 初始化投诉时间
        complaints.setTime(new Date());

        // 初始化投诉结果
        complaints.setResult("无");

        return complaintsReposity.save(complaints);
    }

    @Override
    public Complaints update(Complaints complaints) {

        // 获得当前投诉的 ID
        Complaints complaints_in_db = findById(complaints.getId());

        // 更新投诉进程状态
        complaints_in_db.setStatus(ComplaintsStatusEnum.des2Enum(complaints.getStatus()).name());

        // 更新投诉结果
        complaints_in_db.setResult(complaints.getResult());

        // 合法投诉校验
        valid(complaints_in_db , true);

        return complaintsReposity.save(complaints_in_db);

    }

    @Override
    public Complaints updateField(UpdateBean updateBean) {

        // 获取对象
        Complaints complaints_in_db = findById(updateBean.getId());

        if(StringUtils.equals(updateBean.getField() , "password")){
            throw new RuntimeException("请通过正规方式修改密码");
        }

        if(StringUtils.equals(updateBean.getField() , "username")){
            throw new RuntimeException("用户名不允许修改");
        }

        // 反射赋值
        Field filed;
        Class<Complaints> newsClass = Complaints.class;

        try {
            filed = newsClass.getDeclaredField(updateBean.getField());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("字段:"+updateBean.getField()+"不存在");
        }

        filed.setAccessible(true);
        ReflectionUtils.setField(filed, complaints_in_db, updateBean.getValue());

        valid(complaints_in_db , true);

        return complaintsReposity.save(complaints_in_db);

    }

    @Override
    public void deleteByIds(String ids) {

        try {
            List<String> list = Arrays.asList(ids.split(","));
            complaintsReposity.deleteByIds(list.stream().map(Integer::parseInt).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException("批量删除失败:"+e.getMessage());
        }

    }

    @Override
    public Complaints findById(int id) {

        Complaints complaints_in_db = complaintsReposity.findOne(id);

        if(complaints_in_db ==null){
            throw new RuntimeException("ID:"+id+"对应的对象不存在");
        }

        return complaints_in_db;
    }

    @Override
    @SuppressWarnings("unckecked")
    public ListResultBean findByPage(int page, int size) {

        // 获取分页对象
        PageRequest pageable = new PageRequest(page-1, size , new Sort(Sort.Direction.DESC, "id"));

        // 分页分类查询
        Page<Complaints> complaints_in_page = complaintsReposity.findAll(pageable);

        // 封装返回 Bean
        ListResultBean listResultBean = new ListResultBean();
        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(complaintsReposity.findAll().size());
        listResultBean.setData(complaints_in_page.getContent().stream().peek(complaints-> complaints.setStatus(ComplaintsStatusEnum.valueOf(complaints.getStatus()).getDescribe())).collect(Collectors.toList()));

        return listResultBean;

    }

    @Override
    public void valid(Complaints complaints, boolean same) {

        if(!RegexpUtil.isMobile(complaints.getMobile())){
            throw new RuntimeException("手机号码: "+complaints.getMobile()+"是非法手机号");
        }

        try {
            ComplaintsStatusEnum.valueOf(complaints.getStatus());
        }catch (Exception e){
            throw new RuntimeException("提交状态: "+complaints.getStatus()+"是一个非法状态");
        }

    }

    @Override
    public List<Complaints> findAll() {
        return complaintsReposity.findAll();
    }
}
