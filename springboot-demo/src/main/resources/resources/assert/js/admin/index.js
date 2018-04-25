layui.config({
    base: 'src/js/'
}).use(['app','tab'], function () {
    var app = layui.app;
    app.set({
        type: 'iframe'
    }).init();
});

login_success_ui_init('username','head');

var global = {
    edit_enterprise_detail_id: 1,
    edit_news_detail_id: 1,
    edit_complaints_detail_id: 1,
    edit_department_detail_id: 1,
    edit_report_detail_id: 1
};