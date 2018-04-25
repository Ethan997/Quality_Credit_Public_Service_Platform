$(document).ready(function () {

    //数据填充
    var markup =
        "<tr><td style='color:#96734A;font-size:16px;font-weight:bold;'><img src='../../assert/data/img/blackList-user.svg' width='30px' height='30px' alt='' style='padding-right:6px;' >${id}</td><td style='color:#96734A; font-weight:bold;'>${name}</td></tr>";
    $.template("blackListTemplate", markup);
    function getMovies() {
        $.ajax({
            url: '/rank/pageable?field=BLANK&page=1&size=10',
            success: function (data) {
                if (data.code==0) {
                    $("#ranking").fadeIn(3000);
                    var movies = data.data;
                    $("#blackListList").empty();
                    $.tmpl("blackListTemplate", movies).prependTo("#blackList");
                } else if (data.code == 100002) {
                    toastr.error(data.data)
                    $('#btn-next').css('display', 'none');
                }

            },
            error: function () {
                toastr.error("加载失败!");
            }
        });
    };
    //小屏幕导航
    var state = 0;
    $("#nav-side-link-btn").click(function() {

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
    getMovies();

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