
var news_detail_option = {
    table_id: 'news_detail_table_id',
    table_dom_id: 'news_detail_dom_id',
    table_filter: 'news_detail_table_filter',
    select_filter: 'news_detail_select_filter',
    load_url:'/news/pageable',
    delete_url:'/news/ids',
    update_url:'/news/bean',
    name: '消息',
    cols: [[
        {checkbox: true},
        {field: 'id', title: 'ID' , align: 'center'},
        {field: 'title', title: '标题' , align: 'center'},
        {field: 'createUser' , title: '创建人姓名' , align: 'center'},
        {field: 'createTime' , title: '创建时间' , align: 'center'},
        {field: 'contentType' , title: '类型' , align: 'center'}
    ]],
    field: 'POLICY_AND_REGULATION_DOCUMENTS'
};

table_render(news_detail_option);
form_select_listener(news_detail_option);
function edit_news_detail(edit_news_detail_focus_id){
    parent.global.edit_news_detail_id = edit_news_detail_focus_id;
}