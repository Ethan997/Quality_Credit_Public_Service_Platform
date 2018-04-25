
var complaints_detail_option = {
    table_id: 'complaints_detail_table_id',
    table_dom_id: 'complaints_detail_table_dom_id',
    table_filter: 'complaints_detail_table_filter',
    select_filter: 'complaints_detail_select_filter',
    load_url:'/complaints/pageable',
    delete_url:'/complaints/ids',
    update_url:'/complaints/bean',
    name: '投诉',
    cols: [[
        {checkbox: true},
        {field: 'id', title: 'ID' , align: 'center'},
        {field: 'name', title: '投诉人名称' , align: 'center'},
        {field: 'time', title: '投诉时间' , align: 'center'},
        {field: 'content', title: '投诉内容' , align: 'center'},
        {field: 'mobile', title: '投诉人联系方式' , align: 'center'},
        {field: 'status', title: '投诉处理状态' , align: 'center'}
    ]],
    field: ''
};

table_render(complaints_detail_option);

function complaints_detail_id_init(edit_news_detail_focus_id){
    parent.global.edit_complaints_detail_id = edit_news_detail_focus_id;
}

