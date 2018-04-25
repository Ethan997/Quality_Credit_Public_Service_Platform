$(document).ready(function () {
    var markup = '<p  class="feedback-list-p"><span>姓名</span>:${name}<span>投诉内容</span>:${content}******<span>处理结果</span>:${result}</p>'
    $.template("serviceTemplate", markup);

    function getMovies() {
        $.ajax({
            url: '/complaints',
            success: function (data) {
                /* Get the movies array from the data */
                if(data.code==='100001'){
                    var list = data.data;

                    /* Remove current set of movie template items */
                    $("#feedback-list").empty();

                    /* Render the template items for each movie
                    and insert the template items into the "movieList" */
                    $.tmpl("serviceTemplate", list)
                        .prependTo("#feedback-list");
                }else if(data.code==='100002'){
                    toastr.info('平台暂无投诉')
                }

            },error:function () {
                toastr.error('网路错误！');
            }
        });
    };
    getMovies();
    var state = 0;
    $("#nav-side-link-btn").click(function(){

        if (state === 0) {
            $(".nav-side").css("display", "block");
            $(".nav-side").css({
                "position": "absolute",
                "top": "60px"
            });
            state = 1;
        } else if (state === 1) {
            $(".nav-side").css("display", "none");
            state = 0;
        }
    })
});


layui.use('element', function () {
    var element = layui.element;

});

layui.use('form', function () {
    var form = layui.form;

    form.on('submit(formDemo)', function (data) {

        var user_name=JSON.stringify(data.field.name);
        var phone=$('#mobile').val();

        var post_obj = {
            name:data.field.name,
            mobile:data.field.mobile,
            content:data.field.content
        };

        if(!(/^1[34578]\d{9}$/.test(phone))){
            toastr.error("请填写正确的手机号码")
            $('#mobile').focus();
        }else {
            $.ajax({
                method:'POST',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(post_obj),
                url:'/complaints',
                success:function (data) {

                    if(data.code === 0){
                        layer.msg('提交成功,请耐心等待处理');
                    }else{
                        layer.msg('提交失败:'+data.data);
                    }
                },
                error:function (data) {
                    layer.msg(data.data)
                }
            })
        }
        return false;
    });
});
/*判断浏览器版本是否过低*/
$(document).ready(function() {
    var b_name = navigator.appName;
    var b_version = navigator.appVersion;
    var version = b_version.split(";");
    var trim_version = version[1].replace(/[ ]/g, "");
    if (b_name == "Microsoft Internet Explorer") {
        /*如果是IE6或者IE7*/
        if (trim_version == "MSIE8.0" ||trim_version == "MSIE7.0" || trim_version == "MSIE6.0") {
            alert("IE浏览器版本过低，请到指定网站去下载最新浏览器");
            //然后跳到需要连接的下载网站
            window.location.href="http://www.chromeliulanqi.com/";
        }
    }
});