$(document).ready(function () {


    //新闻列表+分页
    function getNewsList() {
        var start = 1; //从第多少条开始
        var num=0; //总条数
        var markup ="<section class='news'><div class='news-list'><article><div class='news-content'><h2 class='news-title'><a href=${href} target='_blank'>${name}</a></h2></div><div class='meta'><div class='meta-author meta-date'><span>提交人：${createUser}</span><span> | </span><span>发表于：${createTime}</span></div><div class='meta-date'> </div></div></article></div></section>";
        $.template("newsTemplate", markup); //模板
        var ui_getNewsList = document.getElementById("btn-back");
        ui_getNewsList.style.display = "none";

        //第一次请求新闻
        $.ajax({
            method: 'GET',
            url: '/report/pageable?field=SOCIAL_RESPONSIBILITY_REPORT&page=' + start + '&size=10',
            success: function (data) {
                if (data.code ===0) {
                    var newsList_frist = data.data;
                    var num_frist = data.count;
                    num= Math.ceil(num_frist/10);
                    if (num_frist <=10) {
                        $('#btn-next').css('display', 'none');
                    }
                    $("#newsList").empty();
                    $.tmpl("newsTemplate", newsList_frist).appendTo("#newsList");
                } else if (data.code == 100002) {
                    toastr.error(data.data)
                    $('#btn-next').css('display', 'none');
                }
            },
            error: function () {
                toastr.error('网络错误！')
                $('#btn-next').css('display', 'none');
            }
        });

        //下一页功能
        $("#btn-next").click(function () {
            var ui_next = document.getElementById("btn-back");
            ui_next.style.display = "";
            if(start===1){
                $('#btn-back').css('display', 'none');
            }
            start += 1;
            $.ajax({
                method: 'GET',
                url:  '/report/pageable?field=SOCIAL_RESPONSIBILITY_REPORT&page=' + start + '&size=10',
                success: function(data) {

                    if (data.code ===0&&start<=num) {
                        var newsList_next = data.data;
                        $("#newsList").empty();
                        $.tmpl("newsTemplate", newsList_next).appendTo("#newsList");

                        $("html, body").animate({
                            scrollTop: $("#newsList").offset().top
                        }, {
                            duration: 500,
                            easing: "swing"
                        });
                        if(start===num){
                            $('#btn-next').css('display', 'none');
                        }
                    }
                    var ui_back = document.getElementById("btn-back");
                    ui_back.style.display = "";
                },
                error: function () {
                    toastr.error('网路错误！')
                    $('#btn-next').css('display', 'none');
                }
            })
        })

        //上一页功能
        $("#btn-back").click(function () {
            start -= 1;
            if(start===1){
                $('#btn-back').css('display', 'none');
            }
            if(start>0&&start<num){
                $.ajax({
                    method: 'GET',
                    url:  '/report/pageable?field=SOCIAL_RESPONSIBILITY_REPORT&page=' + start + '&size=10',
                    async:false,
                    success: function(data) {
                        if (data.code ===0) {
                            var newsList_back = data.data;
                            var num_back = data.count;
                            $("#newsList").empty();
                            $.tmpl("newsTemplate", newsList_back).appendTo("#newsList");
                            $("html, body").animate({
                                scrollTop: $("#newsList").offset().top
                            }, {
                                duration: 500,
                                easing: "swing"
                            });
                            var ui_next = document.getElementById("btn-next");
                            ui_next.style.display = "";

                        }
                    },
                    error: function () {
                        toastr.error('网路错误！')
                        $('#btn-next').css('display', 'none');
                    }
                })
            }
        })
    };
    getNewsList();
    //导航依赖
    layui.use('element', function () {
        var element = layui.element;
    });
    //小屏幕侧边导航
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