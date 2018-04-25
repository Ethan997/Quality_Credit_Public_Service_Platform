package com.travischenn.platform.controller;

import com.travischenn.platform.Exception.PageException;
import com.travischenn.platform.domain.DO.News;
import com.travischenn.platform.domain.DO.NewsAnnex;
import com.travischenn.platform.domain.DTO.LayuiTableNews;
import com.travischenn.platform.domain.DTO.NewsDTO;
import com.travischenn.platform.domain.DTO.UpdateBean;
import com.travischenn.platform.domain.VO.BaseMessage;
import com.travischenn.platform.domain.VO.BaseNews;
import com.travischenn.platform.domain.VO.LayuiPage;
import com.travischenn.platform.domain.VO.Message;
import com.travischenn.platform.enums.NewsCategoryEnum;
import com.travischenn.platform.enums.NewsExceptionEnum;
import com.travischenn.platform.enums.ResultEnum;
import com.travischenn.platform.repository.MemberRepository;
import com.travischenn.platform.repository.NewsAnnexRepository;
import com.travischenn.platform.repository.NewsRepository;
import com.travischenn.platform.service.NewsService;
import com.travischenn.platform.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsController
 * 功能描述    : 新闻控制器
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/3 21:48
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    /**
     * 新闻数据库查询对象
     */
    @Autowired
    private NewsRepository newsRepository;

    /**
     * 新闻附件数据库查询对象
     */
    @Autowired
    private NewsAnnexRepository newsAnnexRepository;

    /**
     * 用户数据库查询对象
     */
    @Autowired
    private MemberRepository memberRepository;

    // 新闻服务
    @Autowired
    private NewsService newsService;

    // 分页助手
    PageUtil<News> pageUtil = new PageUtil<>();

    /**
     * 新增新闻
     * <p>
     * [注 1]: 针对需要唯一性设置的新闻可在 travischenn.newsDTO.unique 下进行设置 (指定类型的新闻只允许存在一条)
     * [注 2]: 新闻的提交人默认使用当前记录在Sesion中的登录对象 , 新闻的提交时间默认为请求的时间
     *
     * @param newsDTO 新闻实体对象
     *                [注]: 请求参数中只需要携带 title , category , content 即可
     *                <p>
     *                [例]: var json = {
     *                "title": news_title,
     *                "time": "",
     *                "src": "",
     *                "category": news_type,
     *                "pic": news_img,
     *                "content": news_content
     *                };
     * @return 增加成功的新闻实体对象
     */
    @PostMapping(produces = "application/json;charset=UTF-8")
    public BaseMessage<String> create(@Valid @RequestBody NewsDTO newsDTO) {

        // 根据传入的字符串判断新闻类型
        NewsCategoryEnum newsCategory = newsService.findNewsByString(newsDTO.getCategory());

        // 判断新闻条数是否需要唯一
        boolean newUnique = newsService.isNewUnique(newsCategory.name());

        // 新闻唯一性判断
        if (newUnique) {

            // 查看数据库中指定新闻的条数
            int newsSize = newsService.getNewsSize(newsCategory);

            if (newsSize >= 1) {
                throw new RuntimeException(NewsExceptionEnum.NEWS_UNIQUE.getDescribe());
            }

        }

        // 获取当前登录的对象
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // 根据用户名获取对象真实姓名
        String realname = memberRepository.findMemberByUsername(username).getRealname();

        // 同步到 NewsDTO 对象中
        newsDTO.setSrc(realname);
        newsDTO.setTime(new java.util.Date());

        // 持久化 NewsAnnex 部分
        if (org.apache.commons.lang.StringUtils.isBlank(newsDTO.getContent()) || org.apache.commons.lang.StringUtils.isBlank(newsDTO.getImg())) {
            throw new RuntimeException(NewsExceptionEnum.NULL_NEWS_ANNEX_FIELD.getDescribe());
        }

        NewsAnnex newsAnnex = new NewsAnnex();
        BeanUtils.copyProperties(newsDTO, newsAnnex);
        NewsAnnex news_annex_in_db = newsAnnexRepository.save(newsAnnex);

        // 持久化 News 部分
        News news = new News();
        BeanUtils.copyProperties(newsDTO, news);
        news.setAnnexId(news_annex_in_db.getId());
        News news_in_db = newsRepository.save(news);

        return new BaseMessage<>(ResultEnum.SUCCESS, "新增新闻成功,ID: " + news_in_db.getId());

    }

    /**
     * 删除一条新闻
     * <p>
     * [注 1]: 提交的ID必须是一个整型的数字
     *
     * @param ids 新闻ID集合使用 , 分割
     * @return 删除结果消息对象
     */
    @Transactional
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public BaseMessage<Object> delete(@RequestParam("ids") String ids) {

        try {

            // TODO 目前的删除是物理删除,一旦删除之后无法进行恢复.后续进行优化
            List<String> list = Arrays.asList(ids.split(","));
            newsRepository.deleteByIds(list);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new BaseMessage<>(ResultEnum.SUCCESS, "删除成功");
    }

    /**
     * 更新一个新闻实体字段
     *
     * @param updateBean 更新对象
     *                   [注 1]: 更新对象中的 id field 不能为空
     *                   [注 2]: field 应为 News 中的字段
     *                   <p>
     *                   [例]: var json = {
     *                   "id": obj.data.id,
     *                   "field": obj.field,
     *                   "value": obj.value
     *                   };
     * @return 更新之后的新闻对象
     */
    @PutMapping(produces = "application/json;charset=UTF-8")
    @Modifying
    public BaseMessage<News> updateBean(@Valid @RequestBody UpdateBean updateBean) {

        // 从数据库中取出对应ID的新闻对象
        News news = newsRepository.findOne(updateBean.getId());

        if (news == null) {
            throw new RuntimeException("更新消息不存在,请刷新页面");
        } else {

            // BaseNews
            Class<News> newsClass = News.class;

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
            ReflectionUtils.setField(filed, news, updateBean.getValue());

        }

        return new BaseMessage<>(ResultEnum.SUCCESS, newsRepository.save(news));

    }

    /**
     * 更新一个新闻实体对象
     *
     * @param news_dto 新闻实体对象
     *             [注]: 请求参数中只需要携带 title , img , content 即可
     *             <p>
     *             [例]: var json = {
     *                      id: news_id,
     *                      title: news_title,
     *                      time: "",
     *                      src: "",
     *                      category: news_type,
     *                      pic: news_img,
     *                      content: news_content
     *                   };
     * @return 更新之后的新闻对象
     */
    @PutMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/all")
    @Modifying
    public BaseMessage<String> update(@Valid @RequestBody NewsDTO news_dto) {

        try {
            // 更新基本属性
            News news_in_db = newsRepository.findOne(news_dto.getId());
            news_in_db.setTitle(news_dto.getTitle());
            news_in_db.setAuditStatus("0");
            news_in_db.setIsShow("0");
            newsRepository.save(news_in_db);

            // 更新附件属性
            NewsAnnex news_annex = newsAnnexRepository.findOne(news_in_db.getAnnexId());

            if(!StringUtils.equals(news_annex.getContent() , news_dto.getContent())|| !StringUtils.isBlank(news_dto.getImg())){

                news_annex.setContent(news_dto.getContent());
                news_annex.setImg(news_dto.getImg());
                newsAnnexRepository.save(news_annex);

            }

        } catch (Exception e) {
            throw new RuntimeException(NewsExceptionEnum.INVALID_ID.getDescribe());
        }

        return new BaseMessage<>(ResultEnum.SUCCESS, "更新成功");
    }

    /**
     * 获取指定类目的新闻
     *
     * @param id 新闻ID
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/single")
    public Message<NewsDTO> queryById(@Param("id") String id) {

        News news;

        try {
            // 查询基本字段
            news = newsRepository.findOne(id);
        } catch (Exception e) {
            throw new RuntimeException("查询新闻失败,请重试");
        }

        // 查询附件字段
        NewsAnnex newsAnnex = newsAnnexRepository.findOne(news.getAnnexId());

        // 合并属性
        NewsDTO news_dto = new NewsDTO();
        BeanUtils.copyProperties(news , news_dto);
        BeanUtils.copyProperties(newsAnnex , news_dto);

        // 最后做一下小处理 , img 只显示名称不需要显示全 OSS 路径 TODO 后续肯定是要做成图片形式不可能只显示一个字符串的
        String[] split = news_dto.getImg().split("/");
        news_dto.setImg(split[split.length-1]);

        // 返回数据
        return new Message<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDescribe(), news_dto);

    }

    /**
     * 获取指定类目的新闻
     *
     * @param page     当前页数
     * @param size     每一页的新闻条数
     * @param model    模式
     * @param category 新闻种类
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    public Message<BaseNews> query(@RequestParam("page") String page,
                                   @RequestParam("size") String size,
                                   @RequestParam("model") String model,
                                   @RequestParam("category") String category) {

        /** 文章种类 */
        NewsCategoryEnum newsCategoryEnum;

        // 判断 Category 正确性
        try {
            newsCategoryEnum = NewsCategoryEnum.valueOf(category);
        } catch (Exception e) {
            throw new PageException("类目 [" + category + "] 不存在");
        }

        // 获取到审核通过的 消息列表 [通过审核,允许展示,指定类目]
        List<News> newsList = newsRepository.findNewsByCategoryWithPass(newsCategoryEnum.name());

        // 将消息列表进行对应算法的分类
        List<News> pageNewsList = pageUtil.page(newsList, Integer.parseInt(page), Integer.parseInt(size), Boolean.valueOf(model));

        int pageNewsListSize = pageNewsList.size();

        // 添加跳转的url -> TODO 这是一个很神奇的问题 既然详情页都是写死的其实传一个 ID 过去就可以了
        List<NewsDTO> collect = pageNewsList.stream().map(news -> {
            NewsDTO newsDTO = new NewsDTO();
            BeanUtils.copyProperties(news, newsDTO);
            newsDTO.setUrl("news-content.html?id=" + newsDTO.getId());
            return newsDTO;
        }).collect(Collectors.toList());

        // 数据拼装
        BaseNews baseNews = new BaseNews(newsCategoryEnum.getDescribe(), pageNewsListSize, collect);

        // 返回数据
        return new Message<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDescribe(), baseNews);

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
    public LayuiPage<List> queryNewsDynamic(@RequestParam(name = "field", defaultValue = "WORK_DYNAMICS") String field,
                                            @RequestParam(name = "page", defaultValue = "1") String page,
                                            @RequestParam(name = "size", defaultValue = "10") String size) {

        List<LayuiTableNews> resultList = null;

        /** 文章种类 */
        NewsCategoryEnum newsCategoryEnum;

        // 判断 Category 正确性
        try {
            newsCategoryEnum = NewsCategoryEnum.valueOf(field);
        } catch (Exception e) {
            throw new PageException("类目 [" + field + "] 不存在");
        }

        // 获取指定类目的列表
        List<News> newsByCategoryList = newsRepository.findNewsByCategory(newsCategoryEnum.name());

        // 对列表进行分页
        List<News> newsByCategoryPageable = pageUtil.page(newsByCategoryList, Integer.parseInt(page), Integer.parseInt(size), true);

        if (newsByCategoryPageable != null) {
            // 将消息类型进行转换
            resultList = newsByCategoryPageable.stream().map(news -> {
                LayuiTableNews layuiTableNews = new LayuiTableNews();
                BeanUtils.copyProperties(news, layuiTableNews);
                return layuiTableNews;
            }).collect(Collectors.toList());
        }

        return new LayuiPage<>("0", "", newsByCategoryList == null ? 0 : newsByCategoryList.size(), resultList);
    }


    /**
     * 查询单独新闻内容
     *
     * @param category 新闻类别
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping("/unique")
    public BaseMessage<Object> queryUniqueNews(@RequestParam("category") String category){

        /** 文章种类 */
        NewsCategoryEnum newsCategoryEnum;

        // 判断 Category 正确性
        try {
            newsCategoryEnum = NewsCategoryEnum.valueOf(category);
        } catch (Exception e) {
            throw new PageException("类目 [" + category + "] 不存在");
        }

        // 唯一性判断
        boolean newUnique = newsService.isNewUnique(newsCategoryEnum.name());

        if(newUnique){

            List<News> newsByCategory = newsRepository.findNewsByCategory(newsCategoryEnum.name());

            if(newsByCategory.size() == 0){
                return new BaseMessage<>(ResultEnum.FAILED , "当前查询新闻数量为0,请添加后再进行查询");
            }else{

                News news = newsByCategory.get(0);

                int annexId = news.getAnnexId();
                NewsAnnex one = newsAnnexRepository.findOne(annexId);

                NewsDTO newsDTO = new NewsDTO();
                BeanUtils.copyProperties(news , newsDTO);
                BeanUtils.copyProperties(one , newsDTO);
                return new BaseMessage<>(ResultEnum.SUCCESS , newsDTO);
            }
        }else{
            return new BaseMessage<>(ResultEnum.FAILED , "新闻数量不唯一,请检查您的新闻种类");
        }

    }

}