
var report_detail_option = {
    table_id: 'report_detail_table_id',
    table_dom_id: 'report_detail_dom_id',
    table_filter: 'report_detail_table_filter',
    select_filter: 'report_detail_select_filter',
    load_url:'/report/pageable',
    delete_url:'/file/ids',
    update_url:'/update/bean',
    name: '报告',
    cols: [[
        {checkbox: true},
        {field: 'id', title: 'ID' , align: 'center'},
        {field: 'name', title: '报告名称' , align: 'center'},
        {field: 'createTime' , title: '创建时间' , align: 'center'},
        {field: 'createUser' , title: '创建人真实姓名' , align: 'center'},
        {field: 'href' , title: '预览文件' , align: 'center' , templet: '#href'}
    ]],
    field: 'SOCIAL_RESPONSIBILITY_REPORT'
};

table_render(report_detail_option);

form_select_listener(report_detail_option);

function edit_report_detail(edit_report_detail_focus_id){
    parent.global.edit_report_detail_id = edit_report_detail_focus_id;
}