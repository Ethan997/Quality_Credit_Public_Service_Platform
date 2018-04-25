
var update_news_bean = {
    "id":"",
    "title": "",
    img_upload_success_url: "",
    "content": ""
};

var E = window.wangEditor;
var editor = new E('#editor');
wang_edit_init(editor);
editor.create();

update_news_page_init('news_title' , 'news_img' , 'news_category' , editor);

// 文件上传
file_upload('upload_news_img' , 'news_img' , '/file/upload/img' ,  'xiang_shan_quality_platorm/img/' , update_news_bean);
