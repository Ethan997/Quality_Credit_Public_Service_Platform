form_init_id({
    init_url: '/department/info',
    id: parent.global.edit_department_detail_id,
    init_data: function edit_enterprise_detail_init_data(data) {
        $("input[name='name']").attr('value', data.name);
        $("input[name='tStandardUrl']").attr('value', data.tstandardUrl);
        $("input[name='pStandardUrl']").attr('value', data.pstandardUrl);
        template_render({
            tpl: '{{#  if(d.isPublic === "true"){ }}<input name="isPublic" type="checkbox" lay-skin="switch" lay-text="开|" lay-filter="edit_department_detail_is_public_tpl" checked>{{#  } else { }}<input name="isPublic" type="checkbox" lay-skin="switch" lay-text="开|" lay-filter="edit_department_detail_is_public_tpl">{{#  } }}',
            data: data,
            target_dom_id: 'edit_department_detail_is_public_dom_id'
        });
        form.render();
    }
});

form_submit({
    form_submit_filter: 'edit_department_detail_filter',
    init_obj_fun: function edit_department_detail_init_obj_fun(fields) {
        log(fields);
        return {
            id: parent.global.edit_department_detail_id,
            name: fields.name,
            tStandardUrl: fields.tStandardUrl,
            pStandardUrl: fields.pStandardUrl,
            isPublic: fields.isPublic
        };
    },
    submit_url: '/department',
    finish_fun: function edit_department_detail_finish_fun() {
        delete_tab(8);
    },
    is_commit: false
});

file_upload_update({
    file_update_dom_id: 'tStandardUrl',
    file_update_callback_dom_id: 'input_tStandardUrl',
    file_update_obj_id: parent.global.edit_department_detail_id,
    file_update_url: '/department/update',
    file_update_exts: 'jpg|png|bmp',
    file_update_folder: 'department/',
    file_update_category: 'tStandardUrl'
});

file_upload_update({
    file_update_dom_id: 'pStandardUrl',
    file_update_callback_dom_id: 'input_pStandardUrl',
    file_update_obj_id: parent.global.edit_department_detail_id,
    file_update_url: '/department/update',
    file_update_exts: 'jpg|png|bmp',
    file_update_folder: 'department/',
    file_update_category: 'pStandardUrl'
});