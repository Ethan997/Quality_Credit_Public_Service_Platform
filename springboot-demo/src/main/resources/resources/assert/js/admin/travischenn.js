// 表格对象
var table = layui.table;

// 表单对象
var form = layui.form;

// 文件上传对象
var upload = layui.upload;

// 时间选择器
var laydate = layui.laydate;

// 动态模板
var laytpl = layui.laytpl;

// TAB 组件
var tab = parent.tab;

/**
 * 浏览器可见区域垂直居中
 * 当垂直居中后，可对视觉导致的不居中进行补偿。正数向下补偿，负数向上补偿
 *
 * @param dom         需要居中的元素
 * @param compensate  是否需要进行视觉补偿
 * @param value       补偿的值
 *
 * @author            TravisChenn(陈齐康)
 * @data              2017/12/19
 */
function centerInClient(dom, compensate, value) {

    // 获取显示环境的高度
    var clientHeight = document.documentElement.clientHeight;

    // 获取容器的高度
    var height = dom.style.height;

    // 获取需要偏移的高度
    var offsetHeight = (clientHeight - height.substring(0, height.length - 2)) / 2;

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
 * 从 Url 中获取 key-value
 *
 * @param name
 *           *-----------------
 *           * name   ->   key
 *           *-----------------
 */
function getQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!==null) return r[2]; return '';
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

/**
 * 新增一个选项卡
 *
 * @param options  选项卡配置项
 *                           *-----------------
 *                           * id     ->   索引
 *                           *-----------------
 *                           * title  ->   标题
 *                           *-----------------
 *                           * icon   ->   图标
 *                           *-----------------
 *                           * url    ->   链接
 *                           *-----------------
 */
function create_tabs(options) {
    tab.tabAdd(options);
}

/**
 * 关闭一个选项卡
 *
 * @param id
 *          *-----------------
 *          * id   ->   索引
 *          *-----------------
 */
function delete_tab(id) {
    tab.close(id);
}

/**
 * 携带参数新增一个选项卡
 * [用途]  该方法用于对当前选中的表单项作为索引进行操作的页面
 *
 * @param create_tabs_by_id_options  配置项
 *                                        *----------------------------------------------------------------------
 *                                        * table_id      ->   渲染表格的 ID   [注]: table.render({id: table_id});
 *                                        *----------------------------------------------------------------------
 *
 * @param create_tab_options  Tab 配置项
 *                                     *-----------------
 *                                     * id     ->   索引
 *                                     *-----------------
 *                                     * title  ->   标题
 *                                     *-----------------
 *                                     * icon   ->   图标
 *                                     *-----------------
 *                                     * url    ->   链接
 *                                     *-----------------
 *
 * @param init_id_fun         初始化 全局ID 方法   [例]: parent.global.id = value;   [注]: 1: 需要在父页面初始化 global 对象
 *                                                                                       2: 需要在 global 对象中初始化对应的 id 属性
 *                                                                                 [例]:
 *                                                                                       var global = {
 *                                                                                         id: 1
 *                                                                                       };
 */
function create_tabs_by_id(create_tabs_by_id_options, create_tab_options, init_id_fun) {

    // 选中条目对象
    var checkStatus = table.checkStatus(create_tabs_by_id_options.table_id);

    // 选中条数
    var focus_item_num = checkStatus.data.length;

    // 选中条目数量判断
    if (focus_item_num === 0) {
        toastr.warning('请先选择您要编辑的条目');
        return false;
    } else if (focus_item_num > 1) {
        toastr.warning('同时只能编辑一则条目');
        return false;
    }

    // 更新全局记录 ID
    init_id_fun(checkStatus.data[0].id);

    // 新增一个 Tab
    create_tabs(create_tab_options);

}

/**
 * 监听表格编辑
 *
 * @param form_edit_listener_options   表格编辑配置项
 *                                                 *--------------------------------------------------------------------------------------------------
 *                                                 * table_id      ->   渲染表格的 ID   [注]: table.render({id: table_id});
 *                                                 *--------------------------------------------------------------------------------------------------
 *                                                 * load_url      ->   渲染表格的数据接口
 *                                                 *--------------------------------------------------------------------------------------------------
 *                                                 * update_url    ->   更新表格的数据接口
 *                                                 *--------------------------------------------------------------------------------------------------
 *                                                 * field         ->   渲染表格时需要带上的字段
 *                                                 *--------------------------------------------------------------------------------------------------
 *                                                 * table_filter  ->   表格过滤标识   [注]: <table id="table_dom_id" lay-filter="table_filter"></table>
 *                                                 *--------------------------------------------------------------------------------------------------
 *                                                 * field:        ->   重载时带上的字段值
 *                                                 *--------------------------------------------------------------------------------------------------
 */
function form_edit_listener(form_edit_listener_options) {
    table.on('edit(' + form_edit_listener_options.table_filter + ')', function (result) {
        var update_bean = {
            id: result.data.id,
            field: result.field,
            value: result.value
        };
        table_update(update_bean, form_edit_listener_options);
    });
}

/**
 * 监听下拉框数据变化
 *
 * @param form_select_listener_options   下拉框监听配置项
 *                                                    *------------------------------------------------------------------------------------------------
 *                                                    * select_filter  ->   下拉框过滤标识   [注]: <select name="select_name" lay-filter='select_filter'>
 *                                                    *------------------------------------------------------------------------------------------------
 *                                                    * field:         ->   接收下拉框变化的对象
 *                                                    *------------------------------------------------------------------------------------------------
 */
function form_select_listener(form_select_listener_options) {
    form.on('select(' + form_select_listener_options.select_filter + ')', function (data) {
        form_select_listener_options.field = data.value;
    });
}

/**
 * 监听下拉框数据变化  [回调函数版]
 *
 * @param form_select_listener_options   下拉框监听配置项
 *                                                    *------------------------------------------------------------------------------------------------
 *                                                    * select_filter  ->   下拉框过滤标识   [注]: <select name="select_name" lay-filter='select_filter'>
 *                                                    *------------------------------------------------------------------------------------------------
 *                                                    * fun:           ->   触发回调
 *                                                    *------------------------------------------------------------------------------------------------
 */
function form_select_listener_cb(select_filter , fun) {
    form.on('select(' + select_filter + ')', function (data) {
        fun(data);
    });
}

/**
 * 渲染文件上传
 *
 * [注]: 此处文件上传默认不能重复
 *
 * @param file_upload_options   文件上传配置项
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * upload_dom_id           ->   触发上传的 DOM ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * upload_input_dom_name   ->   渲染表格的数据接口
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * upload_url              ->   更新表格的数据接口
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * exts                    ->   表格过滤标识   [注]: <table id="table_dom_id" lay-filter="table_filter"></table>
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * folder:                 ->   重载时带上的字段值
 *                                          *--------------------------------------------------------------------------------------------------
 */
function file_upload(file_upload_options) {

    upload.render({
        elem: '#' + file_upload_options.upload_dom_id,
        url: file_upload_options.upload_url,
        accept: 'file',
        exts: file_upload_options.exts,
        data: {
            folder: file_upload_options.folder,
            exist: true
        },
        before: function () {
            layer.msg('文件正在上传中,清稍后', {
                icon: 16,
                time: 0,
                shade: 0.3
            });
        },
        done: function (result) {

            layer.closeAll();

            if (result.code === 100001) {

                var upload_success_url = result.data;

                $("input[name='" + file_upload_options.upload_input_dom_name + "']").attr('value', upload_success_url);

                toastr.success('文件上传成功');

            } else {
                toastr.error(result.data);
            }

        },
        error: function (result) {
            layer.closeAll();
            toastr.error(result.data);
        }
    });

}

/**
 * 渲染文件上传    [回调函数版本]
 *
 * [注]: 此处文件上传默认不能重复
 *
 * @param file_upload_options   文件上传配置项
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * upload_dom_id           ->   触发上传的 DOM ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * upload_input_dom_name   ->   渲染表格的数据接口
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * upload_url              ->   更新表格的数据接口
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * exts                    ->   表格过滤标识   [注]: <table id="table_dom_id" lay-filter="table_filter"></table>
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * folder:                 ->   重载时带上的字段值
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * cb_fun:                 ->   上传完成的回调函数
 *                                          *--------------------------------------------------------------------------------------------------
 *
 */
function file_upload_cb(file_upload_options) {

    upload.render({
        elem: '#' + file_upload_options.upload_dom_id,
        url: file_upload_options.upload_url,
        accept: 'file',
        exts: file_upload_options.exts,
        data: {
            folder: file_upload_options.folder,
            exist: true
        },
        before: function () {
            layer.msg('文件正在上传中,清稍后', {
                icon: 16,
                time: 0,
                shade: 0.3
            });
        },
        done: function (result) {

            layer.closeAll();

            if (result.code === 100001) {

                var upload_success_url = result.data;

                $("input[name='" + file_upload_options.upload_input_dom_name + "']").attr('value', upload_success_url);

                toastr.success('文件上传成功');

                file_upload_options.cb_fun();

            } else {
                toastr.error(result.data);
            }

        },
        error: function (result) {
            layer.closeAll();
            toastr.error(result.data);
        }
    });

}

/**
 * 渲染文件更新
 *
 * [注]: 此处文件上传默认不能重复
 *
 * @param file_update_options   文件上传配置项
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_dom_id             ->   触发更新的 DOM ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_callback_dom_id    ->   更新成功回调地址显示 DOM ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_obj_id             ->   更新对象ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_url                ->   更新文件接口
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_exts               ->   允许上传的文件格式   [注]: 中间以 | 分割
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_folder:            ->   上传至的阿里云 OSS 文件夹路径  [例]: folder_one/folder_twos
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_category:          ->   更新选项
 *                                          *--------------------------------------------------------------------------------------------------
 *
 */
function file_upload_update(file_update_options) {

    upload.render({
        elem: '#' + file_update_options.file_update_dom_id,
        url: file_update_options.file_update_url,
        accept: 'file',
        exts: file_update_options.file_update_exts,
        data: {
            id: file_update_options.file_update_obj_id,
            folder: file_update_options.file_update_folder,
            category: file_update_options.file_update_category,
            exist: true
        },
        before: function () {
            layer.msg('文件正在上传中,清稍后', {
                icon: 16,
                time: 0,
                shade: 0.3
            });
        },
        done: function (result) {

            layer.closeAll();

            if (result.code === 100001) {

                var upload_success_url = result.data;

                $('#' + file_update_options.file_update_callback_dom_id).val(upload_success_url);

                toastr.success('文件更新成功');

            } else {
                toastr.error(result.data);
            }

        },
        error: function (result) {
            layer.closeAll();
            toastr.error(result.data);
        }
    });

}

/**
 * 渲染文件更新   [回调版本]
 *
 * [注]: 此处文件上传默认不能重复
 *
 * @param file_update_options   文件上传配置项
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_dom_id             ->   触发更新的 DOM ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_callback_dom_id    ->   更新成功回调地址显示 DOM ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_obj_id             ->   更新对象ID
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_url                ->   更新文件接口
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_exts               ->   允许上传的文件格式   [注]: 中间以 | 分割
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_folder:            ->   上传至的阿里云 OSS 文件夹路径  [例]: folder_one/folder_twos
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * file_update_category:          ->   更新选项
 *                                          *--------------------------------------------------------------------------------------------------
 *                                          * cb_fun:                        ->   回调函数
 *                                          *--------------------------------------------------------------------------------------------------
 */
function file_upload_update_cb(file_update_options) {

    upload.render({
        elem: '#' + file_update_options.file_update_dom_id,
        url: file_update_options.file_update_url,
        accept: 'file',
        exts: file_update_options.file_update_exts,
        data: {
            id: file_update_options.file_update_obj_id,
            folder: file_update_options.file_update_folder,
            category: file_update_options.file_update_category,
            exist: true
        },
        before: function () {
            layer.msg('文件正在上传中,清稍后', {
                icon: 16,
                time: 0,
                shade: 0.3
            });
        },
        done: function (result) {

            layer.closeAll();

            if (result.code === 100001) {

                var upload_success_url = result.data;

                $('#' + file_update_options.file_update_callback_dom_id).val(upload_success_url);

                file_update_options.cb_fun();

                toastr.success('文件更新成功');

            } else {
                toastr.error(result.data);
            }

        },
        error: function (result) {
            layer.closeAll();
            toastr.error(result.data);
        }
    });

}

/**
 * 渲染时间选择器
 *
 * @param date_render_options   时间选择器配置项
 *                                            *--------------------------------------------------------------------------------------------------
 *                                            * laydate_dom_id   ->   渲染 DOM ID   [注]:  <input id="laydate_dom_id" type="text" name="laydate_dom_name" required="" lay-verify="required" placeholder="" class="layui-input">
 *                                            *--------------------------------------------------------------------------------------------------
 *                                            * laydate_type     ->   控件类型
 *                                            *                             *------------------------------------------------------
 *                                            *                             *year       ->   只提供年列表选择
 *                                            *                             *------------------------------------------------------
 *                                            *                             *month      ->   只提供年、月选择
 *                                            *                             *------------------------------------------------------
 *                                            *                             *date       ->   可选择：年、月、日。type默认值，一般可不填
 *                                            *                             *------------------------------------------------------
 *                                            *                             *time       ->   只提供时、分、秒选择
 *                                            *                             *------------------------------------------------------
 *                                            *                             *datetime   ->   可选择：年、月、日、时、分、秒
 *                                            *                             *------------------------------------------------------
 *                                            *--------------------------------------------------------------------------------------------------
 */
function date_render(date_render_options) {
    laydate.render({
        elem: '#' + date_render_options.laydate_dom_id,
        type: date_render_options.laydate_type
    });
}

/**
 * 渲染表格
 *
 * @param render_options 表格渲染配置项
 *                                   *-------------------------------------------------------------------------------------------------------
 *                                   * table_id      ->   渲染表格的 ID
 *                                   *-------------------------------------------------------------------------------------------------------
 *                                   * table_dom_id  ->   显示表格的 DOM ID   [注]: <table id="table_dom_id" lay-filter="table_filter"></table>
 *                                   *-------------------------------------------------------------------------------------------------------
 *                                   * load_url      ->   渲染表格的数据接口
 *                                   *-------------------------------------------------------------------------------------------------------
 *                                   * cols          ->   表格的字段组成
 *                                   *-------------------------------------------------------------------------------------------------------
 *                                   * field         ->   渲染表格时需要带上的字段
 *                                   *-------------------------------------------------------------------------------------------------------
 */
function table_render(render_options) {

    table.render({

        // 渲染对象
        elem: '#' + render_options.table_dom_id,

        // 表格高度
        height: 'full-85',

        // 开启隔行背景
        even: true,

        // 请求接口地址
        url: render_options.load_url,

        // 请求参数
        where: {field: render_options.field},

        // 开启分页
        page: true,

        // 分页层级
        limits: [10, 20, 30, 40, 50],

        // 分页大小
        limit: 10,

        // 设置容器唯一ID
        id: render_options.table_id,

        // 表头选项
        cols: render_options.cols,

        // 分页尺寸字段名称
        request: {
            limitName: 'size'
        }

    });

}

/**
 * 渲染富文本编辑器
 *
 * @param wang_wdit_render_options
 *                               *-------------------------------------------------------------------------------------------------------
 *                               * wang_editor      ->   wang_editor DOM 对象   [例]: var wang_editor = window.wangEditor;
 *                               *-------------------------------------------------------------------------------------------------------
 *                               * wang_edit_id     ->   wang_editor DOM ID    [例]:  <div id="wang_edit_id">
 *                               *-------------------------------------------------------------------------------------------------------
 *                               * upload_img_url   ->   图片上传地址
 *                               *-------------------------------------------------------------------------------------------------------
 *                               * folder           ->   OSS 文件夹路径
 *                               *-------------------------------------------------------------------------------------------------------
 */
function wang_editor_render(wang_wdit_render_options) {

    var E = wang_wdit_render_options.wang_editor;

    // 通过 ID 获取编辑器对象
    var wang_editor = new E('#' + wang_wdit_render_options.wang_edit_id);

    // 配置上传接口
    wang_editor.customConfig.uploadImgServer = wang_wdit_render_options.upload_img_url;

    // 配置上传文件大小   [注]: 单位 KB
    wang_editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;

    // 文件上传名称
    wang_editor.customConfig.uploadFileName = 'file';

    // 上传携带参数
    wang_editor.customConfig.uploadImgParams = {
        exist: true
    };

    wang_editor.customConfig.uploadImgHooks = {
        before: function () {
            layer.msg(name + '图片正在上传中,请稍后', {
                icon: 16,
                time: 0,
                shade: 0.3
            });
        },
        success: function () {
            layer.closeAll();
            toastr.success('图片上传成功');
        },
        fail: function () {
            layer.closeAll();
            toastr.error('图片上传成功,图片插入异常。请检查您的图片格式是否为 [png/jpg/bmp]');
        },
        error: function () {
            layer.closeAll();
            toastr.error('图片上传失败');
        },
        timeout: function () {
            layer.closeAll();
            toastr.error('图片上传超时,请检查您的网络环境是否正常');
        },
        customInsert: function (insertImg, result) {
            var url = result.data;
            log(url);
            insertImg(url)
        }
    };

    wang_editor.create();

    return wang_editor;

}

/**
 * 渲染模板引擎
 *
 *
 * @param template_render_options   渲染模板配置项
 *                                             *--------------------------
 *                                             * tpl             ->   模板
 *                                             *--------------------------
 *                                             * data            ->   数据
 *                                             *--------------------------
 *                                             * target_dom_id   ->   需要被渲染的 DOM ID
 *                                             *--------------------------
 *                                             * {tpl: '' , data: '' , target_dom_id: ''}
 *                                             *--------------------------s
 */
function template_render(template_render_options) {
    layui.use('laytpl', function (laytpl) {

        // 获取模板引擎渲染后的 HTML DOM
        var html = laytpl(template_render_options.tpl).render(template_render_options.data);

        // 将 HTML DOM 插入 指定 DOM 中
        $('#'+template_render_options.target_dom_id).append(html);

    });
}

/**
 * 渲染图片查看器
 *
 * [注]: 需要在当前 DOM 对象中 name 元素上添加图片的地址
 *
 * @param src
 *           *--------------------------
 *           * dom   ->   当前点击的 DOM 对象
 *           *--------------------------
 */
function img_render(dom){

    var src = $(dom).attr('name');

    if(src === "null" || src === null || src === ''){
        toastr.info('没有图片详情,请先添加');
        return false;
    }

    layer.photos({
        photos: {
            "title": "",
            "id": 1,
            "start": 0,
            "data": [
                {
                    "alt": "预览图片",
                    "pid": 1,
                    "src": src,
                    "thumb": ""
                }
            ]
        },
        anim: 5
    });

}

/**
 * 重载表格
 *
 * @param table_reload_options   表格重载配置项
 *                                           *---------------------------------------
 *                                           * table_id   ->   表格ID   [注]: table.render({id: table_id});
 *                                           *---------------------------------------
 *                                           * load_url   ->   重载接口 URL
 *                                           *---------------------------------------
 *                                           * field      ->   重载时带上的字段值
 *                                           *---------------------------------------
 */
function table_reload(table_reload_options) {
    table.reload(table_reload_options.table_id, {
        url: table_reload_options.load_url,
        where: {
            field: table_reload_options.field
        },
    });
}

/**
 * 更新表格中的数据
 *
 * @param update_bean            数据更新对象
 *                                         *----------------------
 *                                         * id      ->   ID
 *                                         *----------------------
 *                                         * field   ->   字段名称
 *                                         *----------------------
 *                                         * value   ->   字段值
 *                                         *----------------------
 *
 * @param table_update_options   表格数据更新配置项
 *                                              *---------------------------------------
 *                                              * table_id     ->   表格ID   [注]: table.render({id: table_id});
 *                                              *---------------------------------------
 *                                              * load_url     ->   重载接口 URL
 *                                              *---------------------------------------
 *                                              * update_url   ->   字段更新接口 URL
 *                                              *---------------------------------------
 *                                              * field        ->   重载时带上的字段值
 *                                              *---------------------------------------
 */
function table_update(update_bean, table_update_options) {

    layer.confirm('您确认进行编辑吗', {icon: 3, title: '提示'}, function (index) {
        $.ajax({
            type: "PUT",
            url: table_update_options.update_url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(update_bean),
            success: function (result) {
                if (result.code === 100001) {
                    toastr.success("更新成功");
                } else {
                    toastr.error(result.data);
                }
                table_reload(table_update_options);
            }
        });
        layer.close(index);
    });

}

/**
 * 删除表格中的数据
 *
 * @param delete_options   删除的对象信息
 *                                     *-----------------------------------------
 *                                     * table_id:    ->   表格ID   [注]: table.render({id: table_id});
 *                                     *-----------------------------------------
 *                                     * load_url     ->   重载接口 URL
 *                                     *-----------------------------------------
 *                                     * delete_url   ->   删除接口 URL
 *                                     *-----------------------------------------
 *                                     * name:        ->   删除选项的名称
 *                                     *-----------------------------------------
 *                                     * field:       ->   重载时带上的字段值
 *                                     *-----------------------------------------
 */
function table_delete(delete_options) {

    // 删除项名称
    var name = delete_options.name;

    // 获取选中的行对象
    var checkStatus = table.checkStatus(delete_options.table_id);

    // 准备分离ID的容器
    var ids = "";

    // 将ID放到容器中
    for (var i = 0; i < checkStatus.data.length; i++) {
        if (i === 0) {
            ids += checkStatus.data[i].id;
        } else {
            ids += "," + checkStatus.data[i].id;
        }
    }

    // 删除用户数量检查
    if (checkStatus.data.length === 0) {
        toastr.warning("请选择您要删除的对象");
    } else {

        // 确认用户动作
        layer.confirm('您确定要删除当前选中的' + name + '吗?', {icon: 2, title: '注意'}, function (index) {

            layer.msg(name + '正在删除中,清稍后', {
                icon: 16,
                time: 0,
                shade: 0.3
            });

            // 请求后台的删除接口
            $.ajax({
                url: delete_options.delete_url + "?ids=" + ids,
                type: "DELETE",
                success: function (result) {

                    // 请求成功回调
                    if (result.code === 100001) {
                        table_reload(delete_options);
                        layer.closeAll();
                        toastr.success('删除成功');
                    } else {
                        layer.closeAll();
                        toastr.warning(result.data);
                    }
                },
                error: function () {
                    layer.closeAll();
                    toastr.error('网络错误');
                }
            });

            // 关闭提示窗
            layer.close(index);

        });
    }
}

/**
 * 清空表单输入框
 *
 * @param form_dom_name
 *                     *---------------------------------------
 *                     * form_dom_name   ->   输入框的 name 属性
 *                     *---------------------------------------
 */
function clear_form(form_dom_name) {
    $("input[name='" + form_dom_name + "']").val('');
}

/**
 * 清空多个消息框
 *
 * @param form_dom_names
 *                     *---------------------------------------
 *                     * form_dom_names   ->   输入框的 name 属性 , 使用逗号分隔
 *                     *---------------------------------------
 */
function form_clear(form_dom_names) {
    var clear_forms_item;

    var split = form_dom_names.split(',');

    for (clear_forms_item = 0; clear_forms_item < split.length; clear_forms_item++) {
        clear_form(split[clear_forms_item]);
    }
}

/**
 * 监听表单提交
 *
 * @param form_submit_options  表单提交配置项
 *                                         *----------------------------------------------------------------------------------------------------------------------------------
 *                                         * form_submit_filter    ->   触发提交的 DOM filter   [注]: <button class="layui-btn" lay-filter="form_submit_filter">立即提交</button>
 *                                         *----------------------------------------------------------------------------------------------------------------------------------
 *                                         * init_obj_fun(field)   ->   初始化请求参数的方法     [注]: field: 所有表单对象
 *                                         *----------------------------------------------------------------------------------------------------------------------------------
 *                                         * submit_url            ->   表单提交的地址
 *                                         *----------------------------------------------------------------------------------------------------------------------------------
 *                                         * finish_fun            ->   提交成功的回调函数
 *                                         *----------------------------------------------------------------------------------------------------------------------------------
 *                                         * is_commit             ->   true: 提交 | false: 更新
 *                                         *----------------------------------------------------------------------------------------------------------------------------------
 */
function form_submit(form_submit_options) {
    form.on('submit(' + form_submit_options.form_submit_filter + ')', function (data) {

        layer.msg(name + '数据提交中,清稍后', {
            icon: 16,
            time: 0,
            shade: 0.3
        });

        var form_field = data.field;

        var post_obj = form_submit_options.init_obj_fun(form_field);

        // 提交登录
        $.ajax({
            type: form_submit_options.is_commit ? 'POST' : 'PUT',
            url: form_submit_options.submit_url,
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(post_obj),
            success: function (result) {
                layer.closeAll();
                if (result.code === 100001) {
                    toastr.success('数据提交成功');
                    form_submit_options.finish_fun();
                } else {
                    toastr.error(result.data);
                }
            },
            error: function () {
                layer.closeAll();
                toastr.error('网络错误');
            }
        });
        return false;
    });
}

/**
 * 初始化数据
 *
 * @param init_form_options   初始化配置项
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * init_url          ->   初始化数据接口
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * id                ->   初始化时请求的参数    [例]: data: { id: init_form_options.id }
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * init_data(data)   ->   初始化函数
 *                                      *--------------------------------------------------------------------------------------------------
 */
function form_init_id(init_form_options) {

    layer.msg(name + '正在加载数据中,请稍后', {
        icon: 16,
        time: 0,
        shade: 0.3
    });

    $.ajax({
        type: 'GET',
        url: init_form_options.init_url,
        data: {
            id: init_form_options.id
        },
        success: function (result) {
            layer.closeAll();
            if (result.code === 100001) {
                toastr.success('数据加载成功');
                init_form_options.init_data(result.data);
            } else {
                toastr.error(result.data);
            }
        },
        error: function () {
            layer.closeAll();
            toastr.error('网络错误');
        }
    });
}

/**
 * 初始化数据
 *
 * @param init_form_options   初始化配置项
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * init_url          ->   初始化数据接口
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * category          ->   初始化时请求的参数    [例]: data: { id: init_form_options.id }
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * value             ->   初始化时请求的参数    [例]: data: { id: init_form_options.id }
 *                                      *--------------------------------------------------------------------------------------------------
 *                                      * init_data(data)   ->   初始化函数
 *                                      *--------------------------------------------------------------------------------------------------
 */
function form_init_category(form_init_category_options) {

    layer.msg(name + '正在加载数据中,请稍后', {
        icon: 16,
        time: 0,
        shade: 0.3
    });

    $.ajax({
        type: 'GET',
        url: form_init_category_options.init_url,
        data: {
            category: form_init_category_options.category,
            value: form_init_category_options.value
        },
        success: function (result) {
            layer.closeAll();
            if (result.code === 100001) {
                toastr.success('数据加载成功');
                form_init_category_options.init_data(result.data);
            } else {
                toastr.error(result.data);
            }
        },
        error: function () {
            layer.closeAll();
            toastr.error('网络错误');
        }
    });
}

/**
 * 控制台打印 消息|对象
 *
 * @param obj 消息|对象
 */
function log(obj) {
    console.log(obj);
}

/**
 * 未开发功能提示
 */
function un_develop() {
    toastr.warning('功能正在开发中');
    return false;
}

/**
 * 登出
 */
function login_out() {

    layer.confirm('您确认退出吗?', function (index) {

        $.ajax({
            type: "GET",
            url: "/logout",
            success: function (data) {

                if (data.code === 100007) {
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
        url: '/user/current',
        success: function (result) {

            if (result.code === 100001) {

                // 性别
                var gender = result.data.gender;

                // 真实姓名
                var realname = result.data.realname;

                // 更新真实姓名
                $('#' + realname_dom_id).text(realname);

                // 根据用户性别更新用户头像
                var head_img_dom = $('#' + head_img_dom_id);

                if (gender === "MALE" || gender === "SECRET") {
                    head_img_dom.attr('src', '../../assert/data/img/male.svg');
                } else if (gender === "FEMALE") {
                    head_img_dom.attr('src', '../../assert/data/img/female.svg');
                } else {
                    toastr.error('错误的性别类型');
                }

            } else {
                toastr.error(result.data + '请重新进行身份认证');
                window.setTimeout(function () {
                    window.location.href = "/page/admin/login.html";
                }, 2000);
            }
        },
        error: function () {
            toastr.error("网络错误");
        }
    });
}