$(document).ready(function () {

    if(getQueryString('searchType')=== null ||getQueryString('searchType')=== "" ){
        toastr.error("请返回首页重新搜索");
    }else{
        form_init_category({init_url: '/enterprise/search' , category: getQueryString('searchType') , value: decodeURI(getQueryString('searchVal')) , init_data: function enterprise_init(data){

            $('#name').text(data.name);
            $('#agencyCode').text(data.agencyCode);
            $('#location').text(data.location);
            $('#registrationTime').text(data.registrationTime);
            $('#legalRepresentative').text(data.legalRepresentative);
            $('#businessScope').text(data.businessScope);
            $('#srpUrl').attr('name' , data.srpUrl);
            $('#qcfUrl').attr('name' , data.qcfUrl);
            $('#srrUrl').attr('name' , data.srrUrl);

        }});
    }

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

    function getSearchVal() {
        $("#mysearch-btn").click(function () {
            var searchVal = $("#mysearch-input").text();
            var searchType = $("#select-type").text();
        })

    }



    getSearchVal();

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