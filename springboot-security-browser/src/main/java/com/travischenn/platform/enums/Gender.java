package com.travischenn.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : Gender
 * 功能描述    : 用户性别
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/21 13:14
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("男"),
    FEMALE("女"),
    SECRET("保密");

    /**
     * 性别描述
     */
    private String describe;

    /**
     * 判断 字符串 是否包含在枚举中
     *
     * @param gender_obj   进行检验的枚举对象
     *
     * @return   true  包含
     *           false 不包含
     */
    public static boolean contains(String gender_obj){

        Gender gender = valid(gender_obj);

        for(Gender gender_item : Gender.values()){
            if(StringUtils.equals(gender_item.name() , gender.name())){
                return true;
            }
        }
        return false;
    }

    /**
     * 将描述转换为枚举对象
     *
     * @param gender_describe   进行检验的枚举对象
     *
     * @return   Gender   gender_describe合法则返回对应的字符串
     *           NULL                  不合法返回 NULL
     */
    public static Gender desToEnum(String gender_describe){

        for(Gender gender_item : Gender.values()){
            if(StringUtils.equals(gender_item.getDescribe() , gender_describe)){
                return gender_item;
            }
        }

        throw new RuntimeException(gender_describe+"对应的性别不存在");

    }

    /**
     * 性别合法性校验
     *
     * @param gender_obj 进行校验的字符串对象
     *
     * @return 成功转换后的枚举对象
     */
    public static Gender valid(String gender_obj){

        Gender gender_convert;

        try {
            gender_convert = Gender.valueOf(gender_obj);
        }catch(Exception e){
            throw new RuntimeException("性别枚举类: "+gender_obj+"不存在");
        }

        return gender_convert;

    }

}
