
// 初始化新闻对象
var news = {
    table_id: 'news_dynamic_table',
    table_update_url: '/news',
    current_news_type: '新闻动态',
    current_news_type_en: 'WORK_DYNAMICS',
    create_options: {icon: 'fa-file-text', id: '16', title: '发表新动态', url: 'pages/create-news-detail.html'},
    table_reload_url: '/news/dynamic',
    news_title_id: 'news_title',
    img_upload_success_url: ''
};

// 初始化富文本编辑器
var E = window.wangEditor;
var editor = new E('#editor');
wang_edit_init(editor);
editor.create();

// 监听下拉框的更新
formSelectListener('create_news_category_filter' , news);

// 文件上传
file_upload('upload_news_img' , 'news_img' , '/file/upload/img' ,  'xiang_shan_quality_platorm/img/' , news);

