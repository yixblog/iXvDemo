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
        url: "data/vlx/person.vlx",
        type: "post",
        data: {personId: 2},
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
        typeName: "person",
        menus: {
            101: "展开人物信息",
            102: "收缩",
            777: "-",
            800: "查看人物属性"
        },
        actions: {
            101: function () {
                iXvChart.appendVLX({
                    url: "data/vlx/personProperties.vlx",
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
        typeName: "personProperty",
        menus: {
            101: "展开信息",
            102: "收缩"
        },
        actions: {
            101: function () {
                var properties = iXvChart.getItemProperties(iXvChart.getSelectedItemId());
                if (properties.uri != null && properties.uri != "") {
                    iXvChart.appendVLX({
                        url: properties.uri,
                        type: "post"
                    })
                }
            },
            102: function () {
                iXvChart.contractEnds();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "netcafe",
        menus: {
            800: '查看网吧信息'
        },
        actions: {
            800: function () {
                iXvChart.showProperties();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "punishRecord",
        menus: {
            800: "查看信息"
        },
        actions: {
            800: function () {
                iXvChart.showProperties();
            }
        }
    });

    chartMenuManager.registerHandler({
        manager: chartMenuManager,
        typeName: "hotel",
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
        typeName: "flight",
        menus: {
            800: "查看航班信息"
        },
        actions: {
            800: function () {
                iXvChart.showProperties();
            }
        }
    });

});
