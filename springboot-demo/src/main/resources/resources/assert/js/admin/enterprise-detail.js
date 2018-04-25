
var enterprise_detail_options = {
    table_id: 'enterprise_detail_table_id',
    table_dom_id: 'enterprise_detail_dom_id',
    table_filter: 'enterprise_detail_filter',
    load_url:'/enterprise/pageable',
    delete_url:'/enterprise/ids',
    update_url:'/enterprise/bean',
    name: '企业',
    cols: [[
        {checkbox: true},
        {field: 'id', title: 'ID', align: 'center',width:100},
        {field: 'name', title: '企业名称', align: 'center',width:300},
        {field: 'industry', title: '所属行业', align: 'center',width:100},
        {field: 'agencyCode', title: '机构代码', align: 'center',width:300},
        {field: 'registrationTime', title: '注册时间', align: 'center',width:300},
        {field: 'businessScope', title: '经营范围', align: 'center',width:400},
        {field: 'administrativeDivision', title: '行政区域划分', align: 'center',width:100},
        {field: 'legalRepresentative', title: '法人代表', align: 'center',width:200},
        {field: 'location', title: '通讯地址', align: 'center',width:300},
        {field: 'srrUrl', title: '社会责任报告', align: 'center' , width:150 , templet: '#srrUrl'},
        {field: 'qcfUrl', title: '质量信用档案', align: 'center' , width:150 , templet: '#qcfUrl'},
        {field: 'srpUrl', title: '质量信用承诺', align: 'center' , width:150 , templet: '#srpUrl'},
    ]],
    field: ''
};

table_render(enterprise_detail_options);

form_edit_listener(enterprise_detail_options);

function edit_enterprise_detail(edit_enterprise_detail_focus_id){
    parent.global.edit_enterprise_detail_id = edit_enterprise_detail_focus_id;
}