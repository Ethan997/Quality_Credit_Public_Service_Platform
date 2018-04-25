
centerInClient(document.getElementById("formLoginContainer"), true, 0);

// 表单中提交的信息
var formObj = null;

// 监听表单相关操作
layui.use('form', function () {

    // 获取表单操作对象
    var form = layui.form;

    // 表单提交监听
    form.on('submit(login_submit)', function (data) {

        // 拿到表单中提交的信息
        formObj = data.field;

        // 提交登录
        $.ajax({
            type: 'POST',
            url: "/authentication/form",
            data: {
                'username': formObj.username,
                'password': formObj.password,
                'remember-me': formObj.remember_me
            },
            success: function (result) {

                //登录结果判断
                if (result.code === 100001) {
                    window.location.href = "/page/admin/index.html";
                } else {
                    toastr.error(result.data);
                }

            },
            error: function () {
                toastr.error('网络错误');
            }
        });

        // 不跳转
        return false;
    });

});