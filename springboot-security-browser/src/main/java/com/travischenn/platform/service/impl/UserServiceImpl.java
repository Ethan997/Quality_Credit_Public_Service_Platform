package com.travischenn.platform.service.impl;

import com.travischenn.platform.domain.DO.User;
import com.travischenn.platform.domain.DTO.UserDTO;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import com.travischenn.platform.enums.Gender;
import com.travischenn.platform.repository.UserRepository;
import com.travischenn.platform.service.UserService;
import com.travischenn.platform.util.RegexpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UserServiceImpl
 * 功能描述    : 用户服务实现类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 10:40
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {

        boolean is_deleted = false;

        // 处理曾经被删除,现重新被创建的用户
        User user_in_username = userRepository.findUserByUsername(user.getUsername());

        if(user_in_username!=null && Boolean.valueOf(user_in_username.getDeleted())){
            is_deleted = true;
            user.setId(user_in_username.getId());
        }

        // 合法用户校验
        validUser(user , false , is_deleted);

        // 默认设置启用
        user.setDeleted(Boolean.FALSE.toString());

        // 对用户密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {

        // 获取对象
        User user_in_db = findUserById(user.getId());

        // 校验密码是否经过修改
        if(!passwordEncoder.matches(user_in_db.getPassword() , user.getPassword())){
            throw new RuntimeException("请通过正规方式修改密码");
        }

        // 性别更新
        if(!StringUtils.equals(user_in_db.getGender() , user.getGender())){
            user.setGender(Gender.desToEnum(user.getGender()).name());
        }

        // 合法用户校验
        validUser(user , true , false);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateField(UpdateBean updateBean) {

        // 获取对象
        User user_in_db = findUserById(updateBean.getId());

        if(StringUtils.equals(updateBean.getField() , "password")){
            throw new RuntimeException("请通过正规方式修改密码");
        }

        if(StringUtils.equals(updateBean.getField() , "username")){
            throw new RuntimeException("用户名不允许修改");
        }

        if(StringUtils.equals("gender" , updateBean.getField())){
            updateBean.setValue(Gender.desToEnum(updateBean.getValue()).name());
        }

        // 反射赋值
        Field filed;
        Class<User> newsClass = User.class;

        try {
            filed = newsClass.getDeclaredField(updateBean.getField());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("字段:"+updateBean.getField()+"不存在");
        }

        filed.setAccessible(true);
        ReflectionUtils.setField(filed, user_in_db, updateBean.getValue());

        validUser(user_in_db , true,false);

        return userRepository.save(user_in_db);

    }

    @Override
    public void deleteById(Integer id) {

        // 获取对象
        User user_in_db = findUserById(id);

        // 删除对象
        userRepository.delete(user_in_db);

    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {

        // 删除用户列表
        List<String> list = Arrays.asList(ids.split(","));

        // 获取当前登录的用户
        UserDTO userByThead = findUserByThead();

        // 当前登录用户判断
        if(list.contains(String.valueOf(userByThead.getId()))){
            throw new RuntimeException("您不能删除当前登录的用户");
        }

        try {

            list.forEach(id->{

                User user_item = userRepository.findOne(Integer.parseInt(id));

                if(user_item != null){

                    // 超级管理员账户判断  TODO 操作不能写死在代码里面，应该分配权限去做
                    if(StringUtils.equals(user_item.getUsername() , "administrator")){
                        throw new RuntimeException("您不能删除超级管理员账户");
                    }

                    // 设置用户信息
                    user_item.setDeleted(Boolean.TRUE.toString());

                    // 删除用户对象
                    userRepository.save(user_item);

                }

            });

        } catch (Exception e) {
            throw new RuntimeException("批量删除失败:"+e.getMessage());
        }
    }

    @Override
    public User findUserById(Integer id) {

        User user_in_db = userRepository.findOne(id);

        if(user_in_db ==null){
            throw new RuntimeException("ID:"+id+"对应的用户不存在");
        }

        return user_in_db;

    }

    @Override
    public User findUserByUsername(String username) {

        User user_in_db = userRepository.findUserByUsername(username);

        if(user_in_db ==null){
            return null;
        }

        return user_in_db;
    }

    @Override
    public UserDTO findUserByThead() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(findUserByUsername(username) , userDTO);

        return userDTO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResultBean findByPage(int page , int size) {

        PageRequest pageable = new PageRequest(page-1, size);

        Page<User> all = userRepository.findUsersByDeletedEquals(Boolean.FALSE.toString() , pageable);
        List<User> user_list = all.getContent();

        ListResultBean listResultBean = new ListResultBean();
        listResultBean.setCode(0);
        listResultBean.setMsg("");
        listResultBean.setCount(user_list.size());
        listResultBean.setData(user_list.stream().peek(user-> user.setGender(Gender.valueOf(user.getGender()).getDescribe())).collect(Collectors.toList()));

        return listResultBean;

    }

    @Override
    public void validUser(User user, boolean exist , boolean deleted) {

        // 用户名
        String username = user.getUsername();

        // 手机号码
        String mobile = user.getMobile();

        // 性别
        String gender = user.getGender();

        // 通过用户名获取数据库中的对象
        User user_in_db = userRepository.findUserByUsername(username);

        // 判断用户是否存在
        boolean user_exist = user_in_db != null;

        // 用户名存在性校验
        if((user_exist != exist)&&!deleted){
            String flag = exist?"不存在":"已存在";
            throw new RuntimeException("用户名:"+username+flag);
        }

        // 手机号码正确格式校验
        if(!RegexpUtil.isMobile(mobile)){
            throw new RuntimeException("手机号码:"+mobile+"不规范");
        }

        // 用户性别校验
        if(!Gender.contains(gender)){
            throw new RuntimeException("用户性别:"+gender+"不规范");
        }

    }
}
