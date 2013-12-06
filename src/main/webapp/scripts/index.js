/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-8
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function () {
    iXvChart.buildChart($("body"));
    $.ajax({
        url: "vlx/loadPerson",
        success: function (xml) {
            if (xml.length > 0) {
                iXvChart.setInit(function () {
                    iXvChart.setVLX(xml);
                })
            }
        }
    });
    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "impperson",
        menus: {
            101: "展开人物信息",
            102: "收缩",
            777: "-",
            800: "查看人物属性"
        },
        actions: {
            101: function () {
                iXvChart.appendVLX({
                    url: "vlx/expandNode",
                    type: "post"
                })
            },
            102: function () {
                iXvChart.contractEnds();
            },
            800: function () {
                iXvChart.showProperties();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "personproperty",
        menus: {
            101: "展开信息",
            102: "收缩"
        },
        actions: {
            101: function () {
                iXvChart.appendVLX({
                    url: "vlx/expandNode",
                    type: "post"
                })
            },
            102: function () {
                iXvChart.contractEnds();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "car",
        menus: {
            101: "展开交通事故信息",
            102: "收缩",
            777: "-",
            800: "查看车辆属性"
        },
        actions: {
            101: function () {
                iXvChart.appendVLX({
                    url: "vlx/expandNode",
                    type: "post"
                })
            },
            102: function () {
                iXvChart.contractEnds();
            },
            800: function () {
                iXvChart.showProperties();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "hotelRecord",
        menus: {
            800: "查看属性"
        },
        actions: {
            800: function () {
                iXvChart.showProperties();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "trafficAccident",
        menus: {
            800: "查看交通事故属性"
        },
        actions: {
            800: function () {
                iXvChart.showProperties();
            }
        }
    });
});
