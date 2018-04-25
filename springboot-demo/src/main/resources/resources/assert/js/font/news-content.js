$(document).ready(function () {
    var markup ='<iframe class="article-content"  src="../../assert/data/img/2017年象山影视城服务质量满意度报告.pdf" width="100%" height="1200"></iframe>';
    $.template("pdfTemplate", markup);
    var id;
    var windowUrl=location.search;
    if(windowUrl.indexOf('?')!==-1){
        id=windowUrl.substr(4);
    }
    function getMovies() {
        $.ajax({
            url: '/news/info',
            data:{
                id:id
            },
            success: function (data) {
                var title = data.data.title;
                var src = data.data.createUser;
                var time = data.data.createTime;
                var article = data.data.content;

                if (data.code ==100001) {
                    $("#article-content").prepend(article);
                    $("#article-title").prepend(title);
                    $("#article-author").after(src);
                    $("#article-time").after(time);
                } else if (data.code == 100002) {
                    toastr.error(data.msg)
                }

            },
            error:function(){
                toastr.error("网路错误!");
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

    layui.use('element', function () {
        var element = layui.element;

    });

    /*判断浏览器版本是否过低*/
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