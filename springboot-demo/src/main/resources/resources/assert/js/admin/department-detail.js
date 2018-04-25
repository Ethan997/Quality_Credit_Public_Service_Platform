
var department_detail_option = {
    table_id: 'department_detail_table_id',
    table_dom_id: 'department_detail_dom_id',
    table_filter: 'department_detail_table_filter',
    load_url:'/department/pageable',
    delete_url:'/department/ids',
    update_url:'/department/bean',
    name: '部门',
    cols: [[
        {checkbox: true},
        {field: 'id', title: 'ID' , align: 'center'},
        {field: 'name', title: '部门名称' , align: 'center'},
        {field: 'pStandardUrl' , title: '部门岗位标准' , align: 'center' , templet: '#pStandardUrl_tpl'},
        {field: 'tStandardUrl' , title: '部门技术标准' , align: 'center' , templet: '#tStandardUrl_tpl'},
        {field: 'isPublic' , title: '是否公开' , align: 'center' , templet: '#isPublic_tpl'}
    ]],
    field: ''
};

table_render(department_detail_option);

function department_detail_option_init_id(edit_department_detail_focus_id){
    parent.global.edit_department_detail_id = edit_department_detail_focus_id;
}