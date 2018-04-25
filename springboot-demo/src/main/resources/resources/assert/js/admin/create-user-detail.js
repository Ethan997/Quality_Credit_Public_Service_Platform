

layui.use('form', function () {

    // 获取表单操作对象
    var form = layui.form;

    // 表单提交监听
    form.on('submit(create_user_detail_filter)', function (data) {

        var create_user_detail_form_field = data.field;

        var create_user_detail_form_obj = {
            username: create_user_detail_form_field.username,
            password: create_user_detail_form_field.password,
            realname: create_user_detail_form_field.realname,
            mobile: create_user_detail_form_field.mobile,
            gender: create_user_detail_form_field.gender
        };

        // 提交登录
        $.ajax({
            type: 'POST',
            url: "/user",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(create_user_detail_form_obj),
            success: function (result) {
                if (result.code === 100001) {
                    toastr.success('添加成功');
                    clear_form('username');
                    clear_form('password');
                    clear_form('realname');
                    clear_form('mobile');
                    delete_tab(2);
                } else {
                    toastr.error('添加失败:' + result.data);
                }
            },
            error: function () {
                toastr.error('网络错误');
            }
        });
        return false;
    });

});