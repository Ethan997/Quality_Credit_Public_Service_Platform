
var user_detail_options = {
    table_id: 'user_detail_table_id',
    table_dom_id: 'user_detail_dom_id',
    table_filter: 'user_detail_filter',
    load_url:'/user/pageable',
    delete_url:'/user/ids',
    update_url:'/user/bean',
    name: '用户',
    cols: [[
               {checkbox: true},
               {field: 'id', title: 'ID', align: 'center'},
               {field: 'username', title: '用户名', align: 'center'},
               {field: 'password', title: '密码', align: 'center'},
               {field: 'realname', title: '真实姓名', align: 'center', edit: 'text'},
               {field: 'gender', title: '性别', align: 'center', edit: 'text'},
               {field: 'mobile', title: '手机号码', align: 'center', edit: 'text'},
    ]],
    field: ''
};

table_render(user_detail_options);

form_edit_listener(user_detail_options);