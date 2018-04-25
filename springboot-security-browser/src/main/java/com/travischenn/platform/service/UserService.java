package com.travischenn.platform.service;

import com.travischenn.platform.domain.DO.User;
import com.travischenn.platform.domain.DTO.UserDTO;
import com.travischenn.platform.domain.VO.ListResultBean;
import com.travischenn.platform.domain.VO.UpdateBean;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UserService
 * 功能描述    : 用户服务
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 9:56
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
public interface UserService {

    /**
     * 创建用户
     *
     *        1：用户名
     *                  --不得重复
     *
     *        4：手机号码
     *                  --必须符合手机号码的表达式
     *
     * @param user 用户对象
     *
     * @return 创建成功的用户对象
     *
     */
    User create(User user);

    /**
     * 更新用户对象
     *
     *        1：更新用户的 ID 必须存在
     *
     *        2：用户密码不可通过该服务进行更改
     *
     *        3：更新的手机号码必须经过校验
     *
     *        4：更新的用户名必须通过校验
     *
     * @param user 更新的用户对象
     *
     * @return 更新成功后的用户对象
     *
     */
    User update(User user);

    /**
     * 更新单个字段
     * @param updateBean 更新 Bean 对象
     *
     * @return 更新成功的对象
     */
    User updateField(UpdateBean updateBean);

    /**
     * 删除单个用户
     *
     * @param id 删除用户对象的 ID
     *
     */
    void deleteById(@NotNull Integer id);

    /**
     * 删除多个用户
     *
     * @param ids 删除用户对象的 ID 字符串使用 , 进行分割
     *
     */
    void deleteByIds(@NotBlank String ids);

    /**
     * 通过 ID 查找对象
     *
     * @param id 用户 ID
     *
     * @return 查找到的用户
     */
    User findUserById(Integer id);

    /**
     * 通过 用户名 查找对象
     *
     * @param username 用户名
     *
     * @return 通过用户名查找用户
     */
    User findUserByUsername(String username);

    /**
     * 通过 UserDetailService 获取到当前登录的对象
     *
     * @return 获取当前登录的对象
     */
    UserDTO findUserByThead();

    /**
     * 获取所有用户对象
     * @return 用户对象列表
     */
    ListResultBean findByPage(int page , int size);

    /**
     * 判断是否是合法的用户
     *
     * [注]: 进行校验的用户性别属性为 -> 枚举对象
     *
     *                      exist     expect     result
     *                   0  true      true       true
     *                   1  true      false      false
     *                   2  false     true       false
     *                   3  false     false      true
     *
     * @param user     进行校验的用户
     * @param exist    判断用户存在还是不存在
     * @param deleted  用户是否被删除
     *
     */
    void validUser(User user, boolean exist , boolean deleted);

}
