
var news = {
    table_id: 'news_dynamic_table',
    table_update_url: '/news',
    current_news_type: '新闻动态',
    current_news_type_en: 'WORK_DYNAMICS',
    create_options: {icon: 'fa-file-text', id: '16', title: '发表新动态', url: 'pages/create-news-detail.html'},
    edit_options: {icon: 'fa-pencil-square-o', id: '17', title: '编辑动态', url: 'pages/update-news-detail.html'},
    table_reload_url: '/news/dynamic'
};

// 表格执行渲染
table.render({

    //渲染对象
    elem: '#demo',

    //表格高度
    height: 'full-100',

    //开启隔行背景
    even: true,

    //请求接口地址
    url: "/news/dynamic",

    //请求参数
    where: {category: 'WORK_DYNAMICS'},

    //开启分页
    page: true,

    //分页层级
    limits: [10, 20, 30, 40, 50],

    // TODO [优化] 分页大小应该与屏幕尺寸进行适配
    //分页大小
    limit: 10,

    //设置容器唯一ID
    id: 'news_dynamic_table',

    //表头选项
    cols: [[

        //列头复选框
        {checkbox: true, LAY_CHECKED: true},

        //表格字段名称,表格可编辑
        {field: 'id', title: 'ID', align: 'center', width: 50},
        {field: 'title', title: '标题', align: 'center', edit: 'text'},
        {field: 'time', title: '发表时间', align: 'center', width: 180, sort: true},
        {field: 'src', title: '发表人', align: 'center', width: 150},
        {field: 'auditStatus', title: '审核状态', width: 150, align: 'center', templet: '#auditStatusSwitchTpl'},
        {field: 'isShow', title: '是否展示', align: 'center', width: 150, templet: '#isShowSwitchTpl'}
    ]],

    text: {
        none: '暂无相关数据,请先添加'
    }
});

// 监听表格审核开关
formSwitchListener('auditStatus', "您确认要修改文章审核状态吗?", news);

// 监听文章展示开关
formSwitchListener('isShow', "您确认要修改文章展示状态吗?", news);

// 监听表格更新
formEditListener('news_dynamic_table_filter', "您确认要进行信息的修改吗?", news);

// 监听下拉框的更新
formSelectListener('news_category_filter' , news);