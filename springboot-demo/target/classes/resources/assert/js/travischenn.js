// 表格对象
var table = layui.table;

// 表单对象
var form = layui.form;

// 文件上传对象
var upload = layui.upload;

/**
 * 浏览器可见区域垂直居中
 * 当垂直居中后，可对视觉导致的不居中进行补偿。正数向下补偿，负数向上补偿
 *
 * @param dom         需要居中的元素
 * @param compensate  是否需要进行视觉补偿
 * @param value       补偿的值
 *
 * @author            TravisChenn(陈齐康)
 * @data              2017/12/19*/
function centerInClient(dom, compensate, value) {

    // 获取显示环境的高度
    let clientHeight = document.documentElement.clientHeight;

    // 获取容器的高度
    let height = dom.style.height;

    // 获取需要偏移的高度
    let offsetHeight = (clientHeight - height.substring(0, height.length - 2)) / 2;

    // 判断是否需要进行视觉补偿
    if (compensate) {
        offsetHeight += value;
    }

    // 将容器定位设置为 relative
    dom.style.position = "relative";

    // 偏移 dom
    dom.style.top = offsetHeight + "px";

}

/**
 * 定时淡入一个元素
 * @param id              需要操作的 dom 对象 ID
 * @param second          动画等待的时间
 * @param isAnimation     是否执行动画
 * @param fadeInSpeed     淡入的速度
 * @param animationClass  动画类名称*/
function graduallyShow(id, second, fadeInSpeed, isAnimation, animationClass) {

    // 获取 dom 元素
    let dom = $('#' + id);

    // 延时显示
    window.setTimeout(function () {

        // 淡入
        if (fadeInSpeed === null) {
            fadeInSpeed = "slow";
        }

        dom.fadeIn(fadeInSpeed);

        if (isAnimation) {
            // 从上往上移动的动画效果
            dom.addClass(animationClass);
        }

    }, second * 1000);

}

/**
 * 新增一个选项卡
 *
 * @param tab_options  选项卡配置项*/
function create_tabs(tab_options) {
    var tab = parent.tab;
    tab.tabAdd(tab_options);
}

/**
 * 新建一个新闻
 *
 * @param news           新闻对象
 * @param editor         富文本编辑器对象
 * */
function create_news(news , editor) {

    var news_title_id = news.news_title_id;
    var news_type = news.current_news_type_en;
    var news_img_url = news.img_upload_success_url;

    // 新闻标题
    let news_title = $("#" + news_title_id).val();

    // 新闻内容
    let news_content = editor.txt.html();

    if (news_title === "" || news_title === null || news_img_url === "" || news_content === '<p><br></p>' || news_content === null) {
        toastr.error("文章标题|文章图片|文章内容,不能为空");
    } else {

        var json = {
            "title": news_title,
            "time": "",
            "src": "",
            "category": news_type,
            "img": news_img_url,
            "content": news_content
        };

        $.ajax({
            type: "POST",
            url: '/news',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(json),
            success: function (result) {

                if(result.code === "100001"){
                    toastr.success("文章新建成功,请等待管理员审核");
                }else{
                    toastr.error("文章提交失败,原因: " + result.data);
                }

            },
            error: function () {
                toastr.error("文章提交失败,请检查网络");
            }
        });

    }

}

/**
 * 删除表格中的数据
 * @param obj            删除的对象信息
 * @param splitSymble    分割符*/
function delete_news(obj , splitSymble) {

    // TODO 将业务逻辑写到框架中是很蠢的行为。后期修正===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

    var table_id = obj.table_id;
    var reload_url = obj.table_reload_url;
    var reload_value = obj.current_news_type_en;
    var field_name = obj.current_news_type;

    // ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====


    // [注]: 默认的分隔符为 ,
    if (splitSymble === null || splitSymble === "") {
        splitSymble = ',';
    }

    // 获取选中的行对象
    var checkStatus = table.checkStatus(table_id);

    // 准备分离ID的容器
    var ids = "";

    // 将ID放到容器中
    for (var i = 0; i < checkStatus.data.length; i++) {

        if (i === 0) {
            ids += checkStatus.data[i].id;
        } else {
            ids += splitSymble + checkStatus.data[i].id;
        }

    }

    // 删除用户数量检查
    if (checkStatus.data.length === 0) {
        toastr.warning("请选择您要删除的对象");
    } else {

        // 确认用户动作
        layer.confirm('您确定要删除当前选中的' + field_name + '吗?', {icon: 2, title: '注意'}, function (index) {

            // 请求后台的删除接口
            $.ajax({
                url: "/news?ids=" + ids,
                type: "DELETE",
                success: function (result) {

                    // 请求成功回调
                    if (result.code === "100001") {
                        reload_table(table_id, reload_url , reload_value);
                        toastr.success("删除成功");
                    } else {
                        toastr.warning(result);
                    }
                },
                error: function () {
                    toastr.error("网络错误,请检查网络");
                }
            });

            // 关闭提示窗
            layer.close(index);

        });
    }
}

/**
 * 编辑新闻
 * @param news                需要被编辑的新闻对象
 */
function edit_tabs(news){

    // 获取选中的行对象
    var checkStatus = table.checkStatus(news.table_id);

    // 获取选中的消息条数
    let focus_news_num = checkStatus.data.length;

    // 编辑条数判断
    if(focus_news_num === 0){
        toastr.error('请选择您要编辑的新闻');
        return false;
    }else if(focus_news_num > 1){
        toastr.error('同时只能编辑一条新闻');
        return false;
    }

    // 更新全局新闻更新对象ID
    parent.global.id = checkStatus.data[0].id;

    // 新增一个 Tab
    create_tabs(news.edit_options);

}

/**
 * 更新表格中的数据
 * @param confirm_msg  确认信息
 * @param update_bean  更新提交的对象
 * @param obj          用于更新数据的对象
 * */
function update_news(confirm_msg, update_bean, obj) {

    // TODO 将业务逻辑写到框架中是很蠢的行为。后期修正===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

    var update_url = obj.table_update_url;
    var table_id = obj.table_id;
    var reload_url = obj.table_reload_url;
    var reload_value = obj.current_news_type_en;

    // ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

    // 打开提示用户窗口
    layer.confirm(confirm_msg, {icon: 3, title: '提示'}, function (index) {

        // 更新表格
        $.ajax({
            type: "PUT",
            url: update_url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(update_bean),
            success: function (data) {

                // 请求成功回调
                if (data.code === "100001") {
                    reload_table(table_id, reload_url , reload_value)
                }

                toastr.success("更新成功");
            }
        });

        // 关闭提示用户窗口
        layer.close(index);
    });

}

function update_news_all(news , editor){

    news.id = parent.global.id;
    news.title = $('#news_title').val();
    news.img = news.img_upload_success_url;
    news.content = editor.txt.html();

    $.ajax({
        type: "PUT",
        url: '/news/all',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(news),
        success: function (result) {

            if(result.code === "100001"){
                toastr.success("文章修改成功,等待管理员审核后即可发布");
            }else{
                toastr.error("文章提交失败,原因: " + result.data);
            }

        },
        error: function () {
            toastr.error("文章提交失败,请检查网络");
        }
    });

}

/**
 * 监听数据表格中的开关并进行实时更新
 *
 * @param filter   开关过滤器
 * @param confirm_msg     开关确认信息
 * @param obj            更新对象
 *
 * */
function formSwitchListener(filter, confirm_msg, obj) {

    form.on('switch(' + filter + ')', function (data) {

        // 拼装请求数据
        var update_bean = {
            "id": data.elem.name,
            "field": filter,
            "value": data.elem.checked ? 1 : 0
        };

        update_news(confirm_msg , update_bean , obj);

    });
}

/**
 * 监听数据表格中的单元格编辑
 *
 * @param filter       过滤器
 * @param confirm_msg  更新内容名称
 * @param obj          更新对象
 * */
function formEditListener( filter, confirm_msg , obj) {

    table.on('edit(' + filter + ')', function (result) {

        // 拼装请求数据
        var update_bean = {
            "id": result.data.id,
            "field": result.field,
            "value": result.value
        };

        update_news(confirm_msg , update_bean , obj);

    });

}

/**
 * 监听下拉框新闻
 *
 * @param select_filter  下拉框过滤标识
 * @param obj            用于接收修改之后的对象
 *
 */
function formSelectListener(select_filter , obj){
    form.on('select(' + select_filter + ')', function(data){

        // TODO 根据下拉框的选项变化修改对象的属性 , 将业务逻辑写到框架中是很蠢的行为。后期修正===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

        obj.current_news_type_en = data.value;

        switch (obj.current_news_type_en) {
            case 'WORK_DYNAMICS':
                obj.current_news_type = '工作动态';
                break;
            case 'INFORMATION_DISCLOSURE':
                obj.current_news_type = '信息公开';
                break;
            case 'QUALITY_COMMITTED':
                obj.current_news_type = '质量信用承诺';
                break;
            case 'QUALITY_REPORT':
                obj.current_news_type = '社会责任报告';
                break;
            case 'BRAND_CONSTRUCTION_APPRAISE':
                obj.current_news_type = '区域品牌价值评价';
                break;
            case 'BRAND_COMMUNICATE':
                obj.current_news_type = '质量管理与品牌建设交流活动';
                break;
            default:
                layer.msg('异常的消息种类');
        }

        //===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

    });
}

/**
 * 重新渲染表格
 *
 * @param table_id      表格ID
 * @param reload_url    重新渲染选项
 * @param reload_value  重新渲染之后的字段值
 *
 * */
function reload_table(table_id, reload_url , reload_value) {

    table.reload(table_id, {
        url: reload_url,
        where: {
            field: reload_value
        }
    });

}

/**
 * 未开发功能提示*/
function un_develop() {
    toastr.warning('功能正在开发中');
    return false;
}

/**
 * 登出*/
function login_out() {

    layer.confirm('您确认退出吗?', function (index) {

        $.ajax({
            type: "GET",
            url: "/logout",
            success: function (data) {

                if (data.code === "100007") {
                    toastr.success("退出成功,即将退出到登录页");
                    window.setTimeout(function () {
                        window.location.href = "/page/admin/login.html";
                    }, 1000);
                } else {
                    toastr.error("退出失败,失败原因: " + data);
                }

            },
            error: function () {
                toastr.error('网络错误');
            }
        });

        layer.close(index);
    });

}

/**
 * 用户登录成功后 , 更新UI
 *
 * @param realname_dom_id  用户真实姓名 DOM ID
 * @param head_img_dom_id  用户头像 DOM ID
 */
function login_success_ui_init(realname_dom_id, head_img_dom_id) {

    $.ajax({
        type: "GET",
        url: '/member/dto',
        success: function (data) {

            // 获取用户性别
            let gender = data.data.gender;

            // 获取用户真实姓名
            let realname = data.data.realname;

            // 更新真实姓名
            $('#' + realname_dom_id).text(realname);

            // 根据用户性别更新用户头像
            let head_img_dom = $('#' + head_img_dom_id);

            if (gender === "男") {
                head_img_dom.attr('src', '../../assert/data/img/male.svg');
            } else if (gender === "女") {
                head_img_dom.attr('src', '../../assert/data/img/female.svg');
            } else {
                toastr.error('错误的性别类型');
            }
        },
        error: function () {
            toastr.error("网络错误");
        }
    });

}

/**
 * 文件上传
 *
 * @param upload_dom_id        文件上传事件触发ID
 * @param upload_input_dom_id  文件上传输入框ID
 * @param upload_url           文件上传URL
 * @param oss_folder_name      上传至 OSS 服务中的指定文件夹
 * @param obj                  用于接收上传成功的地址
 *
 * [注]: obj 需要有 img_upload_success_url 属性来承接结果
 *
 */
function file_upload(upload_dom_id , upload_input_dom_id , upload_url , oss_folder_name , obj) {
    layui.use('upload', function () {

        var upload = layui.upload;

        upload.render({
            elem: '#' + upload_dom_id,
            url: upload_url,
            accept: 'images',
            data: {
                folder: oss_folder_name
            },
            before: function () {
                layer.msg('文件正在上传中,清稍后', {
                    icon: 16,
                    time: 0,
                    shade: 0.3
                });
            },
            done: function (result) {

                // 关闭正在上传提示窗
                layer.closeAll();

                // 获取云端文件地址
                let img_upload_success_url = result.data;

                // 将文件名称赋值到输入框中
                let split = img_upload_success_url.split('/');
                let length = split.length;
                $('#' + upload_input_dom_id).attr('value' ,split[length-1] );

                // 将结果进行赋值
                obj.img_upload_success_url = img_upload_success_url;

                toastr.success('图片上传成功');

            },
            error: function (result) {
                layer.closeAll();
                toastr.error('上传失败,失败原因:' + result.data);
            }
        });

    });
}

/**
 * 初始化 WangEditor  // TODO 配置都写死了，后期修正
 * @param editor WangEdit 富文本编辑器对象
 */
function wang_edit_init(editor){
    editor.customConfig.uploadImgShowBase64 = true;
    editor.customConfig.uploadFileName = 'file';
    editor.customConfig.uploadImgParams = {
        folder: 'xiang_shan_quality_platorm/img/'
    };
    editor.customConfig.uploadImgMaxLength = 1;
}

/**
 * 控制台打印 消息|对象
 *
 * @param obj 消息|对象
 */
function log(obj){
    console.log(obj);
}

/**
 * 初始化新闻更新页面
 *
 * @param news_title_id         新闻标题 dom id
 * @param news_img_dom_id       新闻图片 dom id
 * @param news_category_dom_id  新闻种类 dom id
 * @param editor                WangEdit 对象
 */
function update_news_page_init(news_title_id , news_img_dom_id , news_category_dom_id , editor){

    log(parent.id);

    $.ajax({
        type: "GET",
        url: '/news/single',
        data: {
            id:parent.global.id
        },
        success: function (result) {

            if(result.status === '100001'){

                var current_news_type;

                switch (result.result.category) {
                    case 'WORK_DYNAMICS':
                        current_news_type = '工作动态';
                        break;
                    case 'INFORMATION_DISCLOSURE':
                        current_news_type = '信息公开';
                        break;
                    case 'QUALITY_COMMITTED':
                        current_news_type = '质量信用承诺';
                        break;
                    case 'QUALITY_REPORT':
                        current_news_type = '社会责任报告';
                        break;
                    case 'BRAND_CONSTRUCTION_APPRAISE':
                        current_news_type = '区域品牌价值评价';
                        break;
                    case 'BRAND_COMMUNICATE':
                        current_news_type = '质量管理与品牌建设交流活动';
                        break;
                    default:
                        layer.msg('异常的消息种类');
                }

                $("#"+ news_title_id).attr('value' , result.result.title);
                $("#"+ news_img_dom_id).attr('value' , result.result.img);
                $("#"+ news_category_dom_id).attr('value' , current_news_type);
                editor.txt.html(result.result.content);

            }else{
                toastr.warn("获取文章信息失败,失败原因: " + result.data);
            }

        },
        error: function () {
            toastr.error("获取文章信息失败,请检查网络");
        }
    });

}

/**
 * 窗口中网页的相对路径
 *
 * [注]: 高度和宽度需要带上 px
 *
 * @param width     窗口宽度
 * @param height    窗口高度
 * @param url_href  窗口路径
 */
function open_dialog(width , height , url_href){

    layer.open({

        //iframe
        type: 2,

        //不显示标题
        title: false,

        //不显示确认按钮
        btn: false,

        //关闭模态窗效果
        shade: 0,

        //设置大小
        area: [width, height],

        //设置弹出动画
        anim: 5,

        //显示目标页面
        content: [url_href]

    });

}