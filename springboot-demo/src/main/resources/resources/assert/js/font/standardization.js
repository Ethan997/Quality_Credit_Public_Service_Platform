'use strict';

/*Fixing iframe window.innerHeight 0 issue in Safari*/
document.body.clientHeight;




layui.use('element', function () {
    var element = layui.element;

});

/*判断浏览器版本是否过低*/
var b_name = navigator.appName;
var b_version = navigator.appVersion;
var version = b_version.split("; ");
var trim_version = version[1].replace(/[ ]/g, " ");
if (b_name == "Microsoft Internet Explorer ") {
    /*如果是IE6或者IE7*/
    if (trim_version == "MSIE8.0 " || trim_version == "MSIE7.0 " || trim_version == "MSIE6.0 ") {
        alert("IE浏览器版本过低，请到指定网站去下载最新浏览器 ");
        //然后跳到需要连接的下载网站
        window.location.href = "http://www.chromeliulanqi.com/ ";
    }
}
var tree = new G6.Tree({
    id: 'mountNode',
    height: 600,
    width: window.innerWidth - 20,
    fitView: 'autoZoom',
    behaviourFilter: ['wheelZoom', 'dragBlank', 'dragCanvas'],
    layoutCfg: {
        direction: 'TB', // 方向（LR/RL/H/TB/BT/V）
        getHGap: function getHGap() /* d */{
            // 横向间距
            return 1;
        },
        getVGap: function getVGap() /* d */{
            // 竖向间距
            return 100;
        }
    }
});
tree.source({
    label: '总经理A',
    children: [{
        label: '副总经理A',
        children: [{
            label: '办公室',
            children: [{
                label: '行政管理'
            }, {
                label: '后勤管理'
            }, {
                label: '培训考核'
            }, {
                label: '人事管理'
            }, {
                label: '企业文化建设'
            }]
        }, {
            label: '财务部',
            children: [{
                label: '会计核算'
            }, {
                label: '票务收银'
            }]
        }, {
            label: '票务中心'
        }]
    }, {
        label: '总经理B',
        isCollapsed: false,
        children: [{
            label: '宣传部策划部',
            children: [{
                label: '宣传策划'
            }, {
                label: '产品开发'
            }, {
                label: '演艺管理'
            }, {
                label: '节庆策划'
            }, {
                label: '智慧景区'
            }]
        }]
    }, {
        label: '常务副总',
        children: [{
            label: '经管管理部',
            children: [{
                label: '游客服务',
                children: [{
                    label: '游客中心'
                }, {
                    label: '检票服务'
                }, {
                    label: '医疗服务'
                }, {
                    label: '导游服务'
                }, {
                    label: '接待服务'
                }]
            }, {
                label: '安保管理',
                children: [{
                    label: '消防管理'
                }, {
                    label: '交通指挥'
                }, {
                    label: '景区安全管理'
                }]
            }, {
                label: '景区环境',
                children: [{
                    label: '绿化'
                }, {
                    label: '清卫'
                }]
            }, {
                label: '投诉管理'
            }]

        }, {
            label: '工程部',
            children: [{
                label: '工程建设维护'
            }, {
                label: '工程设备管理'
            }, {
                label: '工程项目管理'
            }, {
                label: '景区绿化'
            }, {
                label: '氛围维护'
            }]
        }, {
            label: '安委办',
            children: [{
                label: '危险仓库',
                children: [{
                    label: '烟火仓库'
                }, {
                    label: '枪械仓库'
                }]
            }, {
                label: '区内安全管控'
            }, {
                label: '对外安全衔接'
            }]
        }, {
            label: '商业管理部',
            children: [{
                label: '商业管理'
            }, {
                label: '婚纱摄影'
            }]
        }]
    }, {
        label: '副总经理B',
        children: [{
            label: '影视部',
            children: [{
                label: '剧组管理',
                children: [{
                    label: '外景管理'
                }, {
                    label: '内景管理'
                }, {
                    label: '摄影棚管理',
                    children: [{
                        label: '数字摄影棚'
                    }, {
                        label: '水下摄影棚'
                    }, {
                        label: '其他摄影棚'
                    }]
                }, {
                    label: '剧组接待'
                }, {
                    label: '安全管理'
                }, {
                    label: '游客服务'
                }, {
                    label: '对外宣传'
                }]
            }, {
                label: '演员工会',
                children: [{
                    label: '领队管理'
                }, {
                    label: '群众演员'
                }]

            }]
        }]
    }, {
        label: '副总经理C',
        children: [{
            label: '市场营销部',
            children: [{
                label: '市场营销'
            }, {
                label: '电子商务'
            }]
        }, {
            label: '宁波办事处'
        }]
    }]
});
tree.tooltip(true);
tree.node().tooltip('label');
tree.on('click', function (ev) {
    var nameSting = ev.item.get('model').label;
    layer.open({
        content: "点击按钮查看详情",
        btn: ['岗位标准', '技术标准'],
        btn1: function btn1(ev, layero) {
            //按钮【按钮一】的回调
            $.ajax({
                url: '/department/name',
                data: {
                    name: nameSting
                },
                success: function success(data) {
                    if (data.code === 100001) {
                        window.location.href = data.data.pstandardUrl;
                    } else {
                        toastr.error(nameSting + ' 信息不公开');
                    }
                }
            });
        },
        btn2: function btn2(ev, layero) {
            $.ajax({
                url: '/department/name',
                data: {
                    name: nameSting
                },
                success: function success(data) {
                    if (data.code === 100001) {
                        window.location.href = data.data.tstandardUrl;
                    } else {
                        toastr.error(nameSting + ' 信息不公开');
                    }
                }
            });
            //return false 开启该代码可禁止点击该按钮关闭
        },
        cancel: function cancel() {
            //右上角关闭回调

            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
});
tree.edge().shape('smooth');
tree.render();
toastr.info('单击部门方框显示详情');
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