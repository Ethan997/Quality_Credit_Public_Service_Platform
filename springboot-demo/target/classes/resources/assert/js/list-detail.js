
var list = {

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
    url: "/list/dynamic",

    //请求参数
    where: {field: 'HORNOR'},

    //开启分页
    page: true,

    //分页层级
    limits: [10, 20, 30, 40, 50],

    // TODO [优化] 分页大小应该与屏幕尺寸进行适配
    //分页大小
    limit: 10,

    //设置容器唯一ID
    id: 'list_detail_table',

    //表头选项
    cols: [[

        //列头复选框
        {checkbox: true, LAY_CHECKED: true},

        //表格字段名称,表格可编辑
        {field: 'id', title: 'ID', align: 'center', width: 80},
        {field: 'name', title: '企业名称', align: 'center'}

    ]],

    text: {
        none: '暂无相关数据,请先添加'
    }
});

// 监听表格更新
formEditListener('news_dynamic_table_filter', "您确认要进行信息的修改吗?", news);

// 监听下拉框的更新
formSelectListener('news_category_filter' , news);

