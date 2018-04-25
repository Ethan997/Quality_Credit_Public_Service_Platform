
form_submit({
    form_submit_filter: 'create_news_detail_button_filter',
    init_obj_fun: function create_news_detail_init_obj_fun(fields) {
        return {
            title: fields.title,
            contentType: fields.news_detail_category,
            content: create_news_detail_editor.txt.html()
        };
    }
    ,
    submit_url: '/news',
    finish_fun: function create_news_detail_finish_fun() {
        delete_tab(10);
    },
    is_commit: true
});

var create_news_detail_editor = wang_editor_render({
    wang_editor: window.wangEditor,
    wang_edit_id: 'edit_news_detail_editor',
    upload_img_url: '/file/upload',
    folder: ''
});