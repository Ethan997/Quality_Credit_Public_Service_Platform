
var rank_detail_option = {
    table_id: 'rank_detail_table_id',
    table_dom_id: 'rank_detail_table_dom_id',
    table_filter: 'rank_detail_table_filter',
    select_filter: 'rank_detail_select_filter',
    load_url:'/rank/pageable',
    delete_url:'/rank/ids',
    update_url:'/rank/bean',
    name: '榜单',
    cols: [[
        {checkbox: true},
        {field: 'id', title: 'ID' , align: 'center'},
        {field: 'name', title: '企业名称' , align: 'center' , edit:'text'},
        {field: 'type' , title: '榜单类型' , align: 'center' , edit:'text'}
    ]],
    field: 'HORNOR'
};

table_render(rank_detail_option);
form_select_listener(rank_detail_option);
form_edit_listener(rank_detail_option);
