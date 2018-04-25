
file_upload({
    upload_dom_id: 'pStandardUrl',
    upload_input_dom_name: 'pStandardUrl',
    upload_url: '/file/upload',
    exts: 'jpg|png|bmp',
    folder: 'department/'
});

file_upload({
    upload_dom_id: 'tStandardUrl',
    upload_input_dom_name: 'tStandardUrl',
    upload_url: '/file/upload',
    exts: 'jpg|png|bmp',
    folder: 'department/'
});

form_submit({
    form_submit_filter: 'create_department_detail_filter',
    init_obj_fun: function create_department_detail_init_obj_fun(fields) {
        return {
            name: fields.name,
            tStandardUrl: fields.tStandardUrl,
            pStandardUrl: fields.pStandardUrl,
            isPublic: fields.isPublic === 'on' ? 'true' : 'false',
        };
    },
    submit_url: '/department',
    finish_fun: function create_department_detail_finish_fun() {
        delete_tab(7);
    },
    is_commit: true
});