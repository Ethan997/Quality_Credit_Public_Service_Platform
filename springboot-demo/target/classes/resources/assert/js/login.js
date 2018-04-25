// UI ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====
centerInClient(document.getElementById("formLoginContainer"), true, -60);
graduallyShow("logo", 0.5, 2000, true, "layui-anim layui-anim-scale");
graduallyShow("fieldset", 0.68, null, false, "layui-anim layui-anim-up");
graduallyShow("username", 0.74, null, true, "layui-anim layui-anim-up");
graduallyShow("password", 0.88, null, true, "layui-anim layui-anim-up");
graduallyShow("imageCode", 0.88, null, true, "layui-anim layui-anim-up");
graduallyShow("remember-me", 0.94, null, true, "layui-anim layui-anim-up");
graduallyShow("forget-password", 1.02, null, true, "layui-anim layui-anim-up");
graduallyShow("submit", 1.10, null, true, "layui-anim layui-anim-up");

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
                'imageCode': formObj.imageCode,
                'auth': formObj.auth,
                'remember-me': formObj.remember_me
            },
            success: function (data) {

                //获取登录的结果
                var result = JSON.parse(data);

                //登录结果判断
                if (result.code === "100001") {
                    window.location.href = "/page/admin/index.html";
                } else {
                    layer.msg(result.data);
                }

            },
            error: function (data) {
                var result = JSON.parse(data);
                layer.msg(result.data);
            }
        });

        // 不跳转
        return false;
    });

});