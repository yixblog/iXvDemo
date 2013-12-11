//start iXv View JavaScript
//event codes
var EVENT_NONE = 0;
var EVENT_UNUSED1 = 1;
var EVENT_UNUSED2 = 2;
var EVENT_ITEM_HIDDEN = 3;
var EVENT_ITEM_SHOWN = 4;
var EVENT_UNUSED5 = 5;
var EVENT_MENU_COMMAND = 6;
var EVENT_DRAG_OUT = 7;
var EVENT_ERROR_OCCURED = 8;
var EVENT_MOUSEIN = 9;
var EVENT_MOUSEOUT = 10;
var EVENT_DOUBLECLICK = 11;
var EVENT_CLICK = 12;
var EVENT_LAYOUTDONE = 13;
var EVENT_FILEDONE = 14;
var EVENT_CONTEXTMENU = 15;
var EVENT_OVERRIDE = 16;
var EVENT_MODIFIED_CHANGED = 17;
var EVENT_UNUSED18 = 18;
var EVENT_SHOW_HELP = 19;
var EVENT_BUFFERREADY = 20;
var EVENT_SELECTIONCHANGED = 21;
var EVENT_WARNING = 22;
var EVENT_TIMEBARCHANGE = 24;


var timeLine = false;
var netscape4 = false;
var manual = false;
//Sniff the browser version
if ((navigator.appName == "Netscape") && (navigator.appVersion.indexOf("4.") == 0)) {
    netscape4 = true;
}
var supportHTML5 = $.support.leadingWhitespace;
var chartsJSPath = $("script:last").attr("src");
var chartsJSBase = chartsJSPath.substring(0, chartsJSPath.lastIndexOf("/") + 1);
/**
 * 针对类型的右键菜单处理器
 * @param options JSON参数，包含typeName[类型的LocalName],menus[菜单定义]
 * 菜单定义细节：{menuid:"菜单文本"},actions[事件处理]：{menuid:function(){处理方法}}
 * @constructor
 */
var ChartTypeMenuHandler = function (options) {
    var typeName = options.typeName;
    var menus = options.menus;
    var actions = options.actions;
    this.handleContext = function () {
        iXvChart.showMenus(menus);
    };

    this.onMenuCommand = function (command) {
        actions[command]();
    };
    this.getTypeName = function () {
        return typeName;
    };
};

var IXVChart = function () {
    var thisChart = this;
    var viewer;
    var viewTable;
    var parentNode;
    var expandBar;
    var initCallback;
    var inited = false;
    this.buildChart = function (parentNode) {
        this.parentNode = parentNode;
        if (viewTable == null) {
            initUI()
        }
        viewTable.appendTo(parentNode);
        fitParent();
        appletReadyCallback();
    };

    this.setInit = function (callback) {
        if (!inited) {
            initCallback = callback;
        } else {
            callback();
        }
    };
    this.getChart = function () {
        return viewer;
    };

    this.setWaiting = function (flag) {
        viewer.showWaiting(flag);
    };

    this.toggleTypeFilter = function (catType) {
        this.setWaiting(true);
        var filter = viewer.createTypeFilter(catType);//This will either create a type filter or if it has laready been
        var isFilterOn = viewer.isFilterOn(filter);
        viewer.filter(filter, !isFilterOn);
        viewer.reorganize();
        this.setWaiting(false);
        return isFilterOn;
    };
    /**
     * 显示Menu列表
     * @param menus menucode:menulabel对应的键值对
     */
    this.showMenus = function (menus) {
        viewer.clearMenuItems();
        for (var id in menus) {
            viewer.addMenuItem(parseInt(id), menus[id]);
        }
        viewer.showMenu();
    };

    this.getTypesInUse = function () {
        return viewer.getEndTypesInUse().split(",");
    };

    /**
     * 扩展节点方法
     * @param ajaxParam 对应于$.ajax()的参数，不需要success
     */
    this.appendVLX = function (ajaxParam) {
        this.setWaiting(true);
        var idSearch = this.getSelectedItemId();
        if (idSearch.length > 0) {
            viewer.expandEnds(idSearch, "", "", true);
        }
        var endType = this.getTypeName(idSearch);
        var selectedItemParams = this.getItemProperties(idSearch);
        if (ajaxParam.data == null) {
            ajaxParam.data = {}
        }
        ajaxParam.data.endType = endType;
        ajaxParam.data.params = JSON.stringify(selectedItemParams);
        ajaxParam.success = function (xml) {
            if (xml.length > 0) {
                viewer.mergeVLXString(xml);
                thisChart.setWaiting(false);
                setTimeout(function () {
                    expandBar.init();
                }, 500);
            }
        };
        $.ajax(ajaxParam)
    };

    this.setVLX = function (xml) {
        viewer.putVLX(xml);
        viewer.reorganize();
        expandBar.init();
    };

    this.clear = function () {
        viewer.clearChart(true);
    };

    this.getItemProperties = function (itemId) {
        if (itemId == null) {
            return {}
        }
        var propertyIds = viewer.getItemsPropertyIDs(itemId).split(',');
        var itemProperties = {};

        $(propertyIds).each(function () {
            itemProperties[this] = viewer.getPropertyValue(itemId, this);
        });
        return itemProperties;
    };

    this.getTypeName = function (itemId) {
        return itemId != null ? viewer.getItemType(itemId) : null;
    };

    this.getSelectedType = function () {
        return this.getTypeName(this.getSelectedItemId());
    };

    /**
     * 显示元件属性
     */
    this.showProperties = function () {
        var itemId = this.getSelectedItemId();
        if (itemId != null) {
            showProperties(viewer, itemId);
        }
    };

    /**
     * 取得当前选择的节点id(只取得第一个节点id)
     * @returns {*}
     */
    this.getSelectedItemId = function () {
        var itemSelected = viewer.getSelectedElements();
        var arrayItemId = itemSelected.split(",");
        return arrayItemId.length > 0 ? arrayItemId[0] : null;
    };

    this.contractEnds = function () {
        var idSearch = this.getSelectedItemId();
        if (idSearch != null) {
            viewer.contractEnds(idSearch, true, "", "", true);
        }
    };

    function initUI() {
        viewTable = $("<table></table>").css({background: "#f0f0f0"});
        var tbody = $("<tbody></tbody>").appendTo(viewTable);
        var firstRow = $("<tr></tr>").appendTo(tbody);
        var toolbarTd = $("<td></td>").appendTo(firstRow);
        initToolbar(toolbarTd);
        var iconsTd = $("<td></td>", {rowSpan: 2}).css({verticalAlign: "top"}).appendTo(firstRow);
        var iconTable = $("<table></table>").appendTo(iconsTd);
        var iconTbody = $("<tbody></tbody>").appendTo(iconTable);
        expandBar = new ExpandBar(iconTbody);

        var chartRow = $("<tr></tr>").appendTo(tbody);
        var chartTD = $("<td></td>").appendTo(chartRow);
        var chart = $("#VLVChart").appendTo(chartTD);
        viewer = chart[0];
    }

    function initToolbar(tdBox) {
        //fullscreen
        var isFullScreen = false;
        var fullscreenDiv;
        var fullScreenBtn = $("<img>", {src: "VLImages/button/fullscreen.gif", "class": "imgbutton", alt: "全屏", title: "全屏"}).appendTo(tdBox);
        fullScreenBtn.hover(function () {
            if ($(this).hasClass("imgbutton")) {
                $(this).attr("class", "imgbuttonover");
            } else {
                $(this).attr("class", "imgbutton");
            }
        }, function () {
            $(this).attr("class", "imgbutton");
        });
        fullScreenBtn.click(function () {
            console.log("is fullscreen->" + isFullScreen);
            console.log(fullscreenDiv);
            return;
            if (!isFullScreen) {
                var topSize = {width: window.innerWidth, height: window.innerHeight};
                var position = supportHTML5 ? "fixed" : "absolute";
                fullscreenDiv = $("<div></div>").css({position: position, top: 0, left: 0, width: topSize.width, height: topSize.height, zIndex: 99999}).appendTo($("body"));
                viewTable.appendTo(fullscreenDiv);
                fitParent();
                $(this).attr("src", "VLImages/button/reback.gif");
                $(this).attr("alt", "还原");
                $(this).attr("title", "还原");
                isFullScreen = true;
            } else {
                viewTable.appendTo(parentNode);
                fitParent();
                fullscreenDiv.remove();
                fullscreenDiv = null;
                $(this).attr("src", "VLImages/button/fullscreen.gif");
                $(this).attr("alt", "全屏");
                $(this).attr("title", "全屏");
                isFullScreen = false;
            }
        });
        $(top.document.body).resize(function () {
            if (fullscreenDiv != null) {
                var topSize = {width: top.window.innerWidth, height: top.window.innerHeight};
                fullscreenDiv.css({width: topSize.width, height: topSize.height})
            }
        });
        if (!supportHTML5) {
            $("body").scroll(function () {
                if (fullscreenDiv != null) {
                    fullscreenDiv.css("top", $(this).scrollTop());
                }
            });
        }
    }

    function fitParent() {
        var tableParent = viewTable.parent();
        viewTable.css({width: tableParent.innerWidth() - 30, height: tableParent.innerHeight()});
        $(viewer).attr("width", tableParent.innerWidth() - 60).attr("height", tableParent.innerHeight() - 30);
    }

    function appletReadyCallback() {
        if (viewer == "Error") {
            window.location.href = location.href
        }
        if (!(viewer != null && /function/.test(typeof(viewer.isActive)) && viewer.isActive())) {
            setTimeout(appletReadyCallback, 500);
            return;
        }
        PollEvent();
        viewer.disableEvent(1);  //select
        viewer.disableEvent(2);  //unselect
        viewer.disableEvent(17); //modified changed
        viewer.disableEvent(20); //buffer ready
        viewer.disableEvent(21); //selection changed
        viewer.disableEvent(22); //warning
        viewer.setEmphasisOn(false);
        if (initCallback != null) {
            initCallback();
        }
    }
};
/**
 * iXv右键菜单处理器
 * @constructor
 */
var ChartMenuManager = function () {
    var thisManager = this;
    var handlers = {};
    this.registerHandler = function (options) {
        var handler = new ChartTypeMenuHandler(options);
        handlers[handler.getTypeName()] = handler;
        return thisManager
    };
    this.handleContextMenu = function (strParam) {
        var itemsSelected = strParam.substring(0, strParam.search(/ : /));
        var selectingIdArray = itemsSelected.length > 0 ? itemsSelected.split(',') : [];
        if (selectingIdArray.length > 0) {
            var itemType = iXvChart.getSelectedType();
            if (handlers[itemType] != null) {
                handlers[itemType].handleContext();
            }
        }
    };
    this.handleMenuCommand = function (command) {
        var itemType = iXvChart.getSelectedType();
        if (itemType != null && handlers[itemType] != null) {
            handlers[itemType].onMenuCommand(command);
        }
    };
};

var chartMenuManager = new ChartMenuManager();

function OnFileDone(viewer) {
    viewer.showWaiting(false);
    disableFilters = false;

    var AddedEndIDs = String(viewer.getAddedEndIDs());
    var AddedLinkIDs = String(viewer.getAddedLinkIDs());
    if (AddedEndIDs == "" && AddedLinkIDs == "") {
        alert("无满足条件的记录！");
    }
    if (AddedEndIDs.length + AddedLinkIDs.length > 0) {
        if (timeLine == false) {
            //if (manual == false)

            //viewer.zoomToFitEx(true,9,false);
            viewer.reorganize();

        }
    }

}

function OnReorgDone(viewer) {
    viewer.showWaiting(false);
    //viewer.zoomToFitAddedEx(1,false);
    viewer.zoomToFitEx(true, 9, false);
}


function OnDragOut(strParam) {
    //document.frmCopy.clipCopy.value = strParam;
    //document.frmCopy.clipCopy.createTextRange().execCommand("Copy");
}

function OnError(strParam) {
    alert(strParam);
}

function OnHelp() {
    //window.open("Redistributable Help/Viewer online help/Viewer.html","helpwin","resizable=yes,toolbar=yes,scrollbars=yes,height=660,width=900").focus();
}

function OnTimeBarChange(strParam, viewer) {
    timeLine = eval(strParam);
    viewer.setTimelineModeOn(eval(strParam));
}

function PollEvent() {
    var viewer = iXvChart.getChart();
    var strEvent = String(viewer.getEvent());
    var iEventID = Number(strEvent.substring(0, strEvent.search(/:/)));
    var strParam = String(strEvent.substring(strEvent.search(/:/) + 1, strEvent.length));
    if (iEventID > 0) {
        switch (iEventID) {
            case EVENT_MENU_COMMAND:
                chartMenuManager.handleMenuCommand(strParam);
                break;
            case EVENT_DRAG_OUT:
                OnDragOut(strParam);
                break;
            case EVENT_ERROR_OCCURED:
                OnError(strParam);
                break;
            case EVENT_LAYOUTDONE:
                OnReorgDone(viewer);
                break;
            case EVENT_FILEDONE:
                OnFileDone(viewer);
                break;
            case EVENT_SHOW_HELP:
                OnHelp();
                break;
            case EVENT_CONTEXTMENU:
                chartMenuManager.handleContextMenu(strParam);
                break;
            case EVENT_CLICK:
                window.status = "选择实体类型: " + viewer.getItemType(strParam.substring(0, strParam.length - 1));
                break;
            case EVENT_DOUBLECLICK:
                viewer.scrollCenter();
                break;
            case EVENT_MOUSEIN:
                window.status = "右键查看实体属性信息和关联实体";
                break;
            case EVENT_MOUSEOUT:
                window.status = "";
                break;
            case EVENT_TIMEBARCHANGE:
                OnTimeBarChange(strParam, viewer);
                break;
            //events below are not used in this example
            case EVENT_NONE:
            case EVENT_ITEM_HIDDEN:
            case EVENT_ITEM_SHOWN:
            case EVENT_OVERRIDE:
            case EVENT_MODIFIED_CHANGED:
            case EVENT_BUFFERREADY:
            case EVENT_SELECTIONCHANGED:
            case EVENT_WARNING:
                break;
        }
        window.setTimeout(PollEvent, 0); //event found so try again immediately
        return;
    }
    window.setTimeout(PollEvent, 200); //no event found so try again in 1/5 sec
}

var iXvChart = new IXVChart();
