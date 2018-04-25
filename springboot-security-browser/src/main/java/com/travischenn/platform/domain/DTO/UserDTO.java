package com.travischenn.platform.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : UserDTO
 * 功能描述    : 用户传输层对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/20 15:30
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /** ID */
    private int id;

    /** 用户名 */
    @Size(min = 6 , message = "用户名长度不能小于 6 位数")
    @NotEmpty( message = "用户名不能为空")
    private String username;

    /** 真实姓名 */
    @NotEmpty(message = "用户真实姓名不能为空")
    private String realname;

    /** 性别 */
    @NotEmpty(message = "用户性别不能为空")
    private String gender;

}
