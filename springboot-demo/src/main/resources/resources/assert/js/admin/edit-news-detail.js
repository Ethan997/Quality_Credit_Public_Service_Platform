
var edit_user_detail_editor = wang_editor_render({wang_editor: window.wangEditor ,  wang_edit_id: 'edit_news_detail_editor' , upload_img_url: '/file/upload' , folder: ''});

form_init_id({init_url: '/news/info' , id:parent.global.edit_news_detail_id  ,init_data: function edit_news_detail_init_data(data){
    $("input[name='title']").attr('value' , data.title);
    $("select[name='news_detail_category']").attr('value' , data.contentType);
    edit_user_detail_editor.txt.html(data.content);
}});

form_submit({form_submit_filter: 'edit_news_detail_button_filter' , init_obj_fun: function edit_news_detail_init_obj_fun(fields){
    return {
        id: parent.global.edit_news_detail_id,
        title: fields.title,
        contentType: fields.news_detail_category,
        content: edit_user_detail_editor.txt.html()
    };
} , submit_url: '/news', finish_fun: function edit_news_detail_finish_fun(){
    delete_tab(11)
} , is_commit:false});



