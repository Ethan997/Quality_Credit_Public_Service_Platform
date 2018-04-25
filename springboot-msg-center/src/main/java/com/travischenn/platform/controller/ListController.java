package com.travischenn.platform.controller;

import com.travischenn.platform.Exception.PageException;
import com.travischenn.platform.domain.DO.List;
import com.travischenn.platform.domain.DTO.UpdateBean;
import com.travischenn.platform.domain.VO.BaseMessage;
import com.travischenn.platform.domain.VO.LayuiPage;
import com.travischenn.platform.enums.ListTypeEnum;
import com.travischenn.platform.enums.NewsExceptionEnum;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.repository.ListRepository;
import com.travischenn.platform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : ListController
 * 功能描述    : 榜单控制器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/12 13:19
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    ListRepository listRepository;

    PageUtil<List> pageUtil = new PageUtil<>();

    /**
     * 新增榜单
     *
     * [注 1]: 针对新增数量的上限需要在配置文件中进行配置
     *
     * @param list    列表实体对象
     *                [注]: 请求参数中只需要携带 title , category , content 即可
     *
     *                [例]: var json = {
     *                          name: org_name,
     *                          type: list_type
     *                };
     *
     * @return 增加成功的列表实体对象
     *
     */
    @PostMapping(produces = "application/json;charset=UTF-8")
    public BaseMessage<String> create(@Valid @RequestBody List list) {
        listRepository.save(list);
        return new BaseMessage<>(ResultEnum.SUCCESS , "添加成功");
    }

    /**
     * 删除一条榜单
     * <p>
     * [注 1]: 提交的ID必须是一个整型的数字
     *
     * @param ids 榜单ID集合使用 , 分割
     * @return 删除结果消息对象
     */
    @Transactional
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public BaseMessage<Object> delete(@RequestParam("ids") String ids) {

        try {

            // TODO 目前的删除是物理删除,一旦删除之后无法进行恢复.后续进行优化
            java.util.List<String> list = Arrays.asList(ids.split(","));
            listRepository.deleteByIds(list);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new BaseMessage<>(ResultEnum.SUCCESS, "删除成功");
    }

    /**
     * 更新一个榜单实体字段
     *
     * @param updateBean 更新对象
     *                   [注 1]: 更新对象中的 id field 不能为空
     *                   [注 2]: field 应为 News 中的字段
     *                   <p>
     *                   [例]: var json = {
     *                             "id": obj.data.id,
     *                             "field": obj.field,
     *                             "value": obj.value
     *                   };
     *
     * @return 更新之后的榜单对象
     */
    @PutMapping(produces = "application/json;charset=UTF-8")
    @Modifying
    public BaseMessage<List> updateBean(@Valid @RequestBody UpdateBean updateBean) {

        // 从数据库中取出对应ID的新闻对象
        List list = listRepository.findOne(Integer.parseInt(updateBean.getId()));

        if (list == null) {
            throw new RuntimeException("更新消息不存在,请刷新页面");
        } else {

            // BaseNews
            Class<List> newsClass = List.class;

            // 根据字段名称反射出字段对象
            Field filed;

            // 取出字段对象
            try {
                filed = newsClass.getDeclaredField(updateBean.getField());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(NewsExceptionEnum.INVALID_NEWS_FIELD.getDescribe());
            }

            // 将字段可见
            filed.setAccessible(true);

            // 设置值
            ReflectionUtils.setField(filed, list, updateBean.getValue());

        }

        return new BaseMessage<>(ResultEnum.SUCCESS, listRepository.save(list));

    }

    /**
     * 根据 Layui 进行数据表格字段的筛选
     *
     * @param field 文章种类
     * @param page  当前页数
     * @param size  页数大小
     * @return 文章列表
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/dynamic")
    public LayuiPage<java.util.List<List>> queryNewsDynamic(@RequestParam(name = "field", defaultValue = "BLANK") String field,
                                            @RequestParam(name = "page", defaultValue = "1") String page,
                                            @RequestParam(name = "size", defaultValue = "10") String size) {

        /** 文章种类 */
        ListTypeEnum listTypeEnum;

        // 判断 Category 正确性
        try {
            listTypeEnum = ListTypeEnum.valueOf(field);
        } catch (Exception e) {
            throw new PageException("类目 [" + field + "] 不存在");
        }

        // 获取指定类目的列表
        java.util.List<List> newsByCategoryList = listRepository.findListByType(listTypeEnum.name());

        // 对列表进行分页
        java.util.List<List> newsByCategoryPageable = pageUtil.page(newsByCategoryList, Integer.parseInt(page), Integer.parseInt(size), true);

        return new LayuiPage<>("0", "", newsByCategoryList == null ? 0 : newsByCategoryPageable.size(), newsByCategoryPageable);

    }


}
