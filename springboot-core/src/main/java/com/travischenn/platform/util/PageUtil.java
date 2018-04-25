package com.travischenn.platform.util;

import com.travischenn.platform.Exception.PageException;

import java.util.List;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : PageUtil
 * 功能描述    : 分页工具类
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/4 10:51
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
public class PageUtil<T> {

    /** page | size 分页方式 */
    public static final boolean PAGE_UTIL_INDEX = true;

    /** start | size 分页方式 */
    public static final boolean PAGE_UTIL_START = false;

    public List<T> page(List<T> beanList, int page, int size, boolean model) {

        /** 传入列表长度 */
        int listSize = beanList.size();

        // TODO [后续优化] 传入数据使用注解进行校验
        if (listSize == 0) {
            return null;
        }

        if (size <= 0) {
            throw new PageException("size应该在[0," + listSize + "]之间");
        }

        if (page <= 0 && model) {
            throw new PageException("page不能小于等于0");
        }

        if (!model) {

            if (page % size != 0 || (page < size && page != 0)) {
                throw new PageException(" page 必须是 size 的整数倍 , 请您进行调整");
            }

            page = page / size + 1;
        }


        /** 最大页数 */
        int pageMax = listSize % size == 0 ? listSize / size : listSize / size + 1;

        if (page > pageMax) {
            throw new PageException("传入页数超过最大页数,最大页数为: " + pageMax);
        }

        /** 页数余数 */
        int pageRemainder = listSize % size;

        /** 起始标识 */
        int startNum = (page - 1) * size;

        // 当总数小于size时,全部进行返回
        if (listSize <= size) {
            return beanList;
        } else if (listSize > size && page != pageMax) {
            return beanList.subList(startNum, startNum + size);
        } else if (listSize > size && page == pageMax && pageRemainder != 0) {
            return beanList.subList(startNum, startNum + pageRemainder);
        } else {
            return null;
        }
    }

}
