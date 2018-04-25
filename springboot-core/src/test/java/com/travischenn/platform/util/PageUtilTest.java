package com.travischenn.platform.util;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/4 11:29
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class PageUtilTest {

    @Test
    public void test() {


        String a = "d";


        String b[] = {"a" , "b" , "c"};

        System.out.println(ArrayUtils.contains(b , a));

    }

}