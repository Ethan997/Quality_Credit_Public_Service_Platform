$(document).ready(function () {
    var height=$(window).height();
    $('#banner-img').css('height',(height-60-250)+'px');
    var markup1 = '<h2><a href=${contentDetailUrl}>${title}</a></h2>';
    var markup4 = '<h2><a href=${contentDetailUrl}>${name}</a></h2>';
    var markup2 = '<h2><a href="./news-redList.html">${name}</a></h2>';
    var markup3 = '<h2><a href="./quality-credit-blackList.html">${name}</a></h2>';
    $.template("newsTemplate1", markup1);
    $.template("newsTemplate4", markup4);
    $.template("newsTemplate2", markup2);
    $.template("newsTemplate3", markup3);
    $.ajax({
        url: '/news/pageable?field=WORK_DYNAMICS&page=1&size=6',
        success: function (data) {
            if (data.code === 0) {
                var newsList = data.data;
                $('#index-tab-content-1').empty();
                $.tmpl("newsTemplate1", newsList).appendTo("#index-tab-content-1");
            } else if (data.code == 100002) {
                toastr.error('工作动态' + data.msg)
            }
        }
    })
    $.ajax({
        url: '/news/pageable?field=POLICY_AND_REGULATION_DOCUMENTS&page=1&size=6',
        success: function (data) {
            if (data.code === 0) {
                var newsList = data.data;
                $('#index-tab-content-2').empty();
                $.tmpl("newsTemplate1", newsList).appendTo("#index-tab-content-2");
            } else if (data.code == 100002) {
                toastr.error('政策法规' + data.msg)
            }
        }
    })
    $.ajax({
        url: '/rank/pageable?field=HORNOR&page=1&size=6',
        success: function (data) {
            if (data.code === 0) {
                var newsList = data.data;
                $('#index-tab-content-3').empty();
                $.tmpl("newsTemplate2", newsList).appendTo("#index-tab-content-3");
            } else if (data.code == 100002) {
                toastr.error('红榜' + data.msg)
            }
        }
    })
    $.ajax({
        url: '/rank/pageable?field=BLANK&page=1&size=6',
        success: function (data) {
            if (data.code === 0) {
                var newsList = data.data;
                $('#index-tab-content-4').empty();
                $.tmpl("newsTemplate3", newsList).appendTo("#index-tab-content-4");
            } else if (data.code == 100002) {
                toastr.error('黑名单' + data.msg)
            }

        }
    })
    layui.use('element', function () {
        var element = layui.element;

    });

    //小屏侧导航点击事件
    var state = 0;
    $("#nav-side-link-btn").click(function () {

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

    //搜索板块
    function getSearchVal() {

        $("#mysearch-btn").click(function () {
            var searchVal = $("#mysearch-input").val();
            var searchType = $("#select-type").val();
            location.href = './quality-credit-archives.html?searchType=' + encodeURI(searchType) + '&searchVal=' + encodeURI(searchVal);
        })

    }

    getSearchVal();

})

/*判断浏览器版本是否过低*/
$(document).ready(function () {
    var b_name = navigator.appName;
    var b_version = navigator.appVersion;
    var version = b_version.split(";");
    var trim_version = version[1].replace(/[ ]/g, "");
    if (b_name == "Microsoft Internet Explorer") {
        /*如果是IE6或者IE7*/
        if (trim_version == "MSIE8.0" || trim_version == "MSIE7.0" || trim_version == "MSIE6.0") {
            alert("IE浏览器版本过低，请到指定网站去下载最新浏览器");
            //然后跳到需要连接的下载网站
            window.location.href = "http://www.chromeliulanqi.com/";
        }
    }

    //回车绑定
    $(function () {
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#mysearch-btn").click();
            }

        })
    });

});