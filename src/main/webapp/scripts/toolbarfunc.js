
var scriptPath = $("script:last").attr("src");
var scriptBase = scriptPath.substr(0, scriptPath.lastIndexOf("/") + 1);
//toolbar button


//将图表数据文件保存到服务器数据库中,owner为登陆的用户名。

//导入
var winimport = false;


function zoom(inout) {
    var zooms = viewer.getZoom();

    if (inout == "out") {
        zooms = (zooms * 100 + 20) / 100;
        viewer.setZoom(zooms);
    }
    else {
        zooms = (zooms * 100 - 20) / 100;
        viewer.setZoom(zooms);
    }

}


function setChartLayout(type) {

    viewer.setLayoutManager(type);

    if (viewer.getSelectedElements() == "") {
        viewer.reorganize();
    } else {
        viewer.reorganizeSelection();
    }

}


function showAllItems() {
    var allEnds = String(viewer.getAllEndIDs()).split(/,/);
    var allLinks = String(viewer.getAllLinkIDs()).split(/,/);
    for (var i = 0; i < allEnds.length; i++) {
        viewer.setElementHidden(allEnds[i], false, true);
    }
    for (var j = 0; j < allLinks.length; j++) {
        viewer.setElementHidden(allLinks[j], false, true);
    }
}

var delitemslist = [];
function delItems() {
    var delitems = String(viewer.getSelectedElements()).split(/,/);
    for (var i = 0; i < delitems.length; i++) {
        viewer.setElementDeleted(delitems[i], true, true);
    }
    delitemslist.push(String(delitems));
}


function undoDelItems() {
    if (delitemslist.length < 1) {
        return;
    }
    var delitems = String(delitemslist.pop()).split(/,/);
    var itemslinks;
    for (var i = 0; i < delitems.length; i++) {
        itemslinks = String(viewer.getEndLinks(delitems[i])).split(/,/);
        viewer.setElementDeleted(delitems[i], false, true);
        for (var j = 0; j < itemslinks.length; j++) {
            viewer.setElementDeleted(itemslinks[j], false, true);
        }
    }
}

//列表分析
var winshowItems = false;
var showcolumns = "Type,identityProperty,label,isExpand";
var showcolumnsDisplayName = "类型,标识,标签,是否扩展";
var showlinkcolumns = "Entity A,Entity B,Type,identityProperty,label";
var showlinkcolumnsDisplayName = "实体A,实体B,类型,标识,标签";
var project = "entity";

function showList() {
    if (winshowItems && winshowItems.close)
        winshowItems.close();
    var url = scriptBase + "../toolbar/ShowList.action";
    winshowItems = window.open(url, "iXvServer", "top=200,left=200,width=900,height=450,scrollbars=yes,resizable=yes");
    //winshowItems = window.showModalDialog(url,window,"dialogWidth:900px;dialogHeight:450px;dialogLeft:200;dialogTop:200;help:no;status:no;scroll:yes;resizable:yes");
}


//链接分析

function findLink() {
    var itemID = String(viewer.getSelectedElements()).split(/,/);
    if (itemID.length != 1) {
        alert("您只能选择一个实体进行此操作!");
        return;
    }
    var url = scriptBase + "../toolbar/FindLink.action";
    var ss = window.showModalDialog(url, viewer, "dialogWidth:350px;dialogHeight:200px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");

}


//路径分析
function findPath() {
    var maxdepth = 5;
    var itemID = String(viewer.getSelectedElements()).split(/,/);
    if (itemID.length != 2) {
        alert("必须选择两个实体才能使用此功能");
        return;
    }

    var itempath = "";
    var item1 = "";
    var item2 = "";
    item1 = itemID[0];
    item2 = itemID[1];
    itempath = item1;
    var i, j, k, l, m, n, o, p, q;
    var temp1, temp2;
    var entA, entB, entC, entD, entE, entF, entG, entH, entI;
    var entLinks, entLinks2, entLinks3, entLinks4, entLinks5, entLinks6, entLinks7, entLinks8;
    var breakB = false;
    var breakC = false;
    var breakD = false;
    var breakE = false;
    var breakF = false;
    var breakG = false;
    var breakH = false;
    var breakI = false;
    var pathResult = [];

    entLinks = String(viewer.getEndLinks(item1)).split(/,/);
    Outer:
        for (i = 0; i < entLinks.length; i++) {
            temp1 = String(viewer.getLinkEnd1(entLinks[i]));
            temp2 = String(viewer.getLinkEnd2(entLinks[i]));
            entA = (temp1 == item1) ? temp2 : temp1;
            if (entA == item2) {
                pathResult.push(item1 + "," + entLinks[i] + "," + entA);
            }

            entLinks2 = String(viewer.getEndLinks(entA)).split(/,/);
            for (j = 0; j < entLinks2.length; j++) {
                temp1 = String(viewer.getLinkEnd1(entLinks2[j]));
                temp2 = String(viewer.getLinkEnd2(entLinks2[j]));
                entB = temp1 == entA ? temp2 : temp1;
                if (entB == item2) {
                    if (entLinks[i] != entLinks2[j] && item1 != entB)
                        pathResult.push(item1 + "," + entLinks[i] + "," + entA + "," + entLinks2[j] + "," + entB);
                }

                entLinks3 = String(viewer.getEndLinks(entB)).split(/,/);
                for (k = 0; k < entLinks3.length; k++) {
                    temp1 = String(viewer.getLinkEnd1(entLinks3[k]));
                    temp2 = String(viewer.getLinkEnd2(entLinks3[k]));
                    entC = temp1 == entB ? temp2 : temp1;
                    if (entC == item2) {
                        if (entLinks[i] != entLinks2[j] && entLinks2[j] != entLinks3[k] && item1 != entB && entA != entC)
                            pathResult.push(item1 + "," + entLinks[i] + "," + entA + "," + entLinks2[j] + "," + entB + "," + entLinks3[k] + "," + entC);
                    }

                    entLinks4 = String(viewer.getEndLinks(entC)).split(/,/);
                    for (l = 0; l < entLinks4.length; l++) {
                        temp1 = String(viewer.getLinkEnd1(entLinks4[l]));
                        temp2 = String(viewer.getLinkEnd2(entLinks4[l]));
                        entD = temp1 == entC ? temp2 : temp1;
                        if (entD == item2) {
                            if (entLinks[i] != entLinks2[j] && entLinks2[j] != entLinks3[k] && entLinks3[k] != entLinks4[l] && item1 != entB && entA != entC && entB != entD && item1 != entC && entA != entD)
                                pathResult.push(item1 + "," + entLinks[i] + "," + entA + "," + entLinks2[j] + "," + entB + "," + entLinks3[k] + "," + entC + "," + entLinks4[l] + "," + entD);
                        }
                        entLinks5 = String(viewer.getEndLinks(entD)).split(/,/);
                        for (m = 0; m < entLinks5.length; m++) {
                            temp1 = String(viewer.getLinkEnd1(entLinks5[m]));
                            temp2 = String(viewer.getLinkEnd2(entLinks5[m]));
                            entE = temp1 == entD ? temp2 : temp1;
                            if (entE == item2) {
                                if (entLinks[i] != entLinks2[j] && entLinks2[j] != entLinks3[k] && entLinks3[k] != entLinks4[l] && entLinks4[l] != entLinks5[m] && item1 != entB && entA != entC && entB != entD && entC != entE && item1 != entC && entA != entD && entB != entE && item1 != entD && entA != entE)
                                    pathResult.push(item1 + "," + entLinks[i] + "," + entA + "," + entLinks2[j] + "," + entB + "," + entLinks3[k] + "," + entC + "," + entLinks4[l] + "," + entD + "," + entLinks5[m] + "," + entE);
                            }
                        }
                    }
                }
            }
        }

    //alert(pathResult.length);

    if (pathResult.length == 0) {
        alert("没有找到路径");
        return;
    }
    var eachPath;
    for (i = 0; i < pathResult.length; i++) {
        eachPath = String(pathResult[i]).split(/,/);
        for (j = 0; j < eachPath.length; j++) {
            viewer.setElementSelected(eachPath[j], true);
        }

    }

}


//群集分析
function findCluster(cluster) {
    window.status = "查找群集...";
    var strAllEnds = viewer.getVisibleEndIDs();

    var allEnds = strAllEnds.split(/,/);
    //first round
    var eLinks;
    for (var i = 0; i < allEnds.length; i++) {
        eLinks = String(viewer.getEndLinks(allEnds[i])).split(/,/);
        if (eLinks.length < cluster) {
            //viewer.setElementDeleted(allEnds[i],true,true);
            viewer.setElementHidden(allEnds[i], true, true);
        }
    }

    //second round
    allEnds = String(viewer.getVisibleEndIDs()).split(/,/);
    for (i = 0; i < allEnds.length; i++) {
        eLinks = String(viewer.getEndLinks(allEnds[i])).split(/,/);
        var j = 0;
        var count = eLinks.length;
        for (j = 0; j < eLinks.length; j++) {
            //if (viewer.isElementDeleted(eLinks[j])==true) 
            if (viewer.isElementShown(eLinks[j]) == false) {
                count = count - 1;
            }
        }
        if (count < cluster) {
            //viewer.setElementDeleted(allEnds[i],true,true);
            viewer.setElementHidden(allEnds[i], true, true);
        }
    }

    //third round
    viewer.reorganize();
    viewer.zoomToFitEx(true, -1, false);
    strAllEnds = String(viewer.getVisibleEndIDs()).split(/,/);

    if (strAllEnds.length == 0) {
        alert("没有找到群集!");
        showAllItems();
    }

    alert("查找群集结束...");
    window.status = "查找群集结束...";

}


//查找匹配实体

function matchSearch() {
    var allEnds = String(viewer.getVisibleEndIDs()).split(/,/);
    if (allEnds.length == 0) {
        alert("图表中没有实体!");
        return;
    }
    var url = scriptBase + "../toolbar/MatchSearch.action";
    var ss = window.showModalDialog(url, window, "dialogWidth:830px;dialogHeight:600px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");
}
function filterSearch() {
    var url = scriptBase + "../toolbar/FilterSearch.action";
    var ss = window.showModalDialog(url, window, "dialogWidth:830px;dialogHeight:600px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");
}

function uniteEntity() {
    var url = scriptBase + "../toolbar/unitEntity.vm";
    var arrSelectedEntities = String(viewer.getSelectedElements()).split(',');//得到选中实体
    var num = arrSelectedEntities.length;
    if (num < 2) {
        alert('请选中至少两个实体');
        return false;
    }
    if (num >= 2) {
        var id1 = arrSelectedEntities[0];
        var itempropids1 = String(viewer.getItemsPropertyIDs(id1)).split(/,/);
        var bf = true;
        var strType = viewer.getItemType(id1);
        for (var k = 0; k < num; k++) {
            var id = arrSelectedEntities[k];
            var type = viewer.getItemType(id);
            if (type != strType) {
                bf = false;
                break;
            }
        }
    }
    if (!bf) {
        alert("请选择同一类型的实体");
        return false;
    }
    var ss = window.showModalDialog(url, window, "dialogWidth:400px;dialogHeight:300px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");
}
//实体合并
function doUniteEntity(which) {
    var arrSelectedEntities = String(viewer.getSelectedElements()).split(',');//得到选中实体
    var num = arrSelectedEntities.length;
    if (num >= 2) {
        //var id1=arrSelectedEntities[0];
        var itempropids1 = String(viewer.getItemsPropertyIDs(which)).split(/,/);
        var bf = true;
        var strType = viewer.getItemType(which);
        for (var k = 0; k < num; k++) {
            var id = arrSelectedEntities[k];
            var type = viewer.getItemType(id);
            if (type != strType) {
                bf = false;
                break;
            }
        }

        if (bf) {

            for (var i = 0; i < num; i++) {
                var id2 = arrSelectedEntities[i];
                if (id2 == which) {
                    continue;
                }
                var itempropids2 = String(viewer.getItemsPropertyIDs(id2)).split(/,/);
                for (var j = 0; j < itempropids2.length; j++) {
                    var propType1 = itempropids1[j];
                    var propValue1 = viewer.getPropertyValue(which, propType1);
                    var propType2 = itempropids2[j];
                    var propValue2 = viewer.getPropertyValue(id2, propType2);
                    if (propValue2 != "") {
                        viewer.setPropertyValue(which, propType2, propValue1 + "," + propValue2, false);
                    }
                }

                var entLinks = String(viewer.getEndLinks(id2)).split(/,/);
                var uniteId2 = "";
                for (j = 0; j < entLinks.length; j++) {
                    var linkId = entLinks[j];
                    var linkEnt1 = viewer.getLinkEnd1(linkId);
                    var linkEnt2 = viewer.getLinkEnd2(linkId);
                    if (linkEnt1 == id2) {
                        uniteId2 = linkEnt2;
                    } else {
                        uniteId2 = linkEnt1;
                    }
                    var linkType = viewer.getItemType(linkId);
                    var strmerge = scriptBase + "../toolbar/chart?actionName=getLinkType&end1id=" + which + "&end2id=" + uniteId2 + "&linktype=" + linkType;
                    //alert(strmerge);
                    if (linkType != "") {
                        viewer.mergeVLX(strmerge);
                    } else {
                        viewer.reorganize();
                    }

                }
                viewer.setElementDeleted(id2, true, true);
                //viewer.setElementHidden(id2,true,true);		
            }

            //var VLX=viewer.getVLX(true); 

            viewer.setElementSelected(which, true);
        } else {
            alert("请选择同一类型的实体");
        }

    }
    else {
        alert('请选中至少两个实体');
    }
    //viewer.clearAddedEnds();
}


//可视化搜索
function viewSearch() {
    var allEnds = String(viewer.getAllEndIDs()).split(/,/);
    if (allEnds.length == 0) {
        alert("图表中没有任何实体!");
        return;
    }
    var url = scriptBase + "../toolbar/VisualSearch.action";
    var ss = window.showModalDialog(encodeURI(url), viewer, "dialogWidth:500px;dialogHeight:500px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");
}


//添加实体(for visualanalysis)
function addEnd(ftype, fname) {
    var url = scriptBase + "../toolbar/chart?actionName=addEnd&ftype=" + ftype + "&fname=" + fname;
    viewer.mergeVLX(encodeURI(url));
}


//修改详细属性
var winp = false;


var winl = false;

function modiLink() {

    var objChart = viewer;
    var itemID = String(objChart.getSelectedElements());

    var props = String(objChart.getItemsPropertyIDsEx(itemID, true));

    var arrPropIDs = props.split(',');
    var propertyHTML = "<table cellpadding=0 cellspacing=0><tr><td height='30' bgcolor='#CCCACA'><font size=2 face='Arial' color='#000000'><b>&nbsp;Property</b></font></td><td bgcolor='#CCCACA'><font size=2 face='Arial' color='#000000'><b>&nbsp;Value</b></font></td></tr>";
    var bgcolor = "#F3EFEF";

    if (bgcolor == "#F3EFEF") {
        bgcolor = "white";
    } else {
        bgcolor = "#F3EFEF";
    }
    propertyHTML += "<tr><td bgcolor='" + bgcolor + "' width='200' height='20'>";
    propertyHTML += "<font size=2 face='Arial'><b>&nbsp;ID</b></font>";
    propertyHTML += "</td><td bgcolor='" + bgcolor + "' width='300'>";
    propertyHTML += "<font size=2 face='Arial'>&nbsp;" + itemID + "</font>";
    propertyHTML += "</td></tr>";
    propertyHTML += "<tr><td>&nbsp</td></tr>";


    for (var i = 1; i < arrPropIDs.length; i++) {
        if (objChart.getPropertyValue(itemID, arrPropIDs[i]) != "") {
            if (bgcolor == "#F3EFEF") {
                bgcolor = "white";
            } else {
                bgcolor = "#F3EFEF";
            }
            propertyHTML += "<tr><td bgcolor='" + bgcolor + "' width='200' height='20'>";
            propertyHTML += "<font size=2 face='Arial'><b>&nbsp;" + objChart.getPropertyDisplayName(itemID, arrPropIDs[i]) + "</b></font>";
            propertyHTML += "</td><td bgcolor='" + bgcolor + "' width='300'>";
            propertyHTML += "<font size=2 face='Arial'>&nbsp;" + "<input type='text' size= '50' value='" + objChart.getPropertyValue(itemID, arrPropIDs[i]) + "' id='f" + arrPropIDs[i] + "'></font>";
            propertyHTML += "</td></tr>";
        }
    }
    propertyHTML += "</tr></table>";
    propertyHTML += "<table><tr><td><input type='button' value='OK' name='submit' onclick=psubmit();></td>";
    propertyHTML += "<td><input type='button' value='Cancel' name='cancel' onclick=pcancel();></td></tr>";
    propertyHTML += "</table>";
    //propertyWindow(propertyHTML, "Properties");
    title = "ModiLink";
    var jscript = "<script language='JavaScript'>";
    jscript += "var objChart = window.opener.viewer;";
    jscript += "function pcancel(){window.close();}";
    jscript += "function psubmit(){ var itemID = String(objChart.getSelectedElements()); ";
    jscript += "var props = String(objChart.getItemsPropertyIDsEx(itemID,true));var arrPropIDs = props.split(','); var eval1 = ''; var i=0;for(i=1;i<arrPropIDs.length;i++){";
    jscript += "if(objChart.getPropertyValue(itemID, arrPropIDs[i])!=''){ var einput= document.getElementById('f' + arrPropIDs[i]); eval1= einput.value; objChart.setPropertyValue(itemID,arrPropIDs[i],eval1,false);}}";
    jscript += "window.close();}</script>";

    if (winl && winl.close)
        winl.close();
    winl = window.open("", title, "top=200,left=200,width=600,height=350,scrollbars=yes"); // a window object
    winl.document.open("text/html", "replace");
    winl.document.write("<HTML><meta http-equiv='Content-Type' content='text/xml; charset='UTF-8'><HEAD><TITLE>" + title + "</TITLE>" + jscript + "</HEAD><BODY style='margin:0'><form name='formprop'><div id='content'>" + propertyHTML + "</div></form></BODY></HTML>");
    winl.document.close();

    /*
     if(winp && winp.close)
     winpp.close()
     winMatchp = window.open(scriptBase + "../toolbar/modiproperty.jsp", "modiproperty", "top=150,left=100,width=800,height=450,scrollbars=yes,resizable=yes");
     */
}


//显示详细属性
var win = false;

function modiProperties() {
    document.getElementById('propertyFrame').src = scriptBase + "../toolbar/DoProperty.action?actionType=edit";
    document.getElementById('propertyDiv').style.display = 'inline';
    //var modiProperties = window.showModalDialog(url,window,"dialogWidth:500px;dialogHeight:400px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");
    //var modiProperties = window.open(url,'modiProperty',"dialogWidth:500px;dialogHeight:400px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");
}
function propertyWindow(str, title) {
    if (win && win.close)
        win.close();
    win = window.open("", title, "top=200,left=200,width=500,height=250,scrollbars=yes"); // a window object
    win.document.open("text/html", "replace");
    win.document.write("<HTML><meta http-equiv='Content-Type' content='text/xml; charset=windows-1252'><HEAD><TITLE>" + title + "</TITLE></HEAD><BODY style='margin:0'><div id='content'>" + str + "</div></BODY></HTML>");
    win.document.close();
}

function showSource() {
    var objChart = viewer;
    var arrSelEnds = String(objChart.getSelectedElements()).split(/,/);
    var itemID = String(objChart.getSelectedElements());
    var props = String(objChart.getItemsPropertyIDs(itemID));
    var arrPropIDs = props.split(',');
    var entityType = objChart.getItemType(arrSelEnds[0]);
    var itemLabel = objChart.getPropertyValue(itemID, "label");
    var arrPropStr = "";

    for (var i = 0; i < arrPropIDs.length; i++) {
        if (arrPropIDs[i] != "isExpand") {
            arrPropStr += arrPropIDs[i] + "!!";
            var temp = "";
            temp = objChart.getPropertyValue(itemID, arrPropIDs[i]) + ",";
            arrPropStr += temp;
        }
    }

    var url = scriptBase + "../toolbar/ShowSourceChart.action?entityType=" + entityType + "&arrPropStr=" + arrPropStr;
    url = url + "&itemID=" + itemID + "&itemLabel=" + itemLabel;

    var option = "dialogWidth:800px;dialogHeight:600px;dialogLeft:50;dialogTop:50;help:no;status:no;scroll:yes;resizable:no";
    window.showModalDialog(url, "test", option);
    //window.open(url);
}

function showSourceTable() {
    document.getElementById('sourceFrame').src = scriptBase + "../toolbar/DoSource.action?actionType=show";
    document.getElementById('sourceDiv').style.display = 'inline';
    //window.open(url, 'newwindow', 'left=120,top=30,height=600, width=800, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes');
}


//加入分析收藏夹
function addFavourite(formvlx) {

    document.formvlx.svlx.value = viewer.getVLX(true);
    //document.formvlx.actionName.value="addFavourite";
    document.formvlx.action = scriptBase + "../toolbar/chart?actionName=addFavourite";
    var url = scriptBase + "../toolbar/AddFavourite.action?actionName=addFavourite";
    var str_name = window.showModalDialog(encodeURI(url), "iXvServer", "dialogWidth:375px;dialogHeight:120px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:no;resizable:yes");

    //图表名字

    //alert(str_name.length);
    if (str_name == undefined) {
        return false;
    }
    if (str_name == null) {
        str_name = String(new Date().getTime());
    } else if (str_name.length == 0) {
        //alert();
        str_name = String(new Date().getTime());
    }
    document.formvlx.name.value = str_name;
    document.formvlx.target = "dummy";
    document.formvlx.submit();
}


//打开分析收藏夹
function openFavourite() {
    var url = scriptBase + "../toolbar/OpenFavourite.action";
    var openFavourite = window.showModalDialog(encodeURI(url), window, "dialogWidth:400px;dialogHeight:300px;dialogLeft:400;dialogTop:200;help:no;status:no;scroll:yes;resizable:no");

}


/*
 * 打开查询界面
 */
function openSearch() {
    window.open("./query/editQuery.action?PageType=open", "Search");
}

function isLegalID(idvalue) {
    var rt = false;
    if (typeof(idvalue) == "undefined") {
        rt = false;
    } else if (idvalue == "") {
        alert("ID不能为空!");
        rt = false;
    } else if (idvalue.indexOf("'") != -1 || idvalue.indexOf("\"") != -1) {
        alert("ID有不合法字符!");
        rt = false;
    } else {
        rt = true;
    }
    return rt;
}

function setTimeBar(which) {
    if (which.className == "imgbutton") {
        viewer.setTimebarShown(true);
        which.className = "imgbuttonover";
    } else {
        viewer.setTimebarShown(false);
        which.className = "imgbutton";
    }
}

function setKeyEmphasis(which) {
    if (which.className == "imgbutton") {
        viewer.setEmphasisOn(true);
        which.className = "imgbuttonover";
    } else {
        viewer.setEmphasisOn(false);
        which.className = "imgbutton";
    }
}

function showEndsCount() {
    var allEnds = String(viewer.getAllEndIDs());
    var allLinks = String(viewer.getAllLinkIDs());
    var strEnds = viewer.getSelectedElements();
    var arrEnds = strEnds.split(",");
    var endnum = 0;
    var linknum = 0;

    for (var i = 0; i < arrEnds.length; i++) {
        //alert();
        if (allEnds.indexOf(arrEnds[i]) != -1) {
            endnum++;
        } else if (allLinks.indexOf(arrEnds[i]) != -1) {
            linknum++;
        }
    }

    window.status = "当前选择实体数：" + endnum + ", 链接数：" + linknum;
}

function lock() {
    if (viewer.getSelectedElements() != "") {
        var arrEnds = viewer.getSelectedElements().split(",");
        for (var i = 0; i < arrEnds.length; i++) {
            viewer.setEndFixed(arrEnds[i], true);
        }
    }

}
function unlock() {
    if (viewer.getSelectedElements() != "") {
        var arrEnds = viewer.getSelectedElements().split(",");
        for (var i = 0; i < arrEnds.length; i++) {
            viewer.setEndFixed(arrEnds[i], false);
        }
    }

//选择相关链接(按钮)
    function findAllLinks() {
        alert("ok");
        var arrSelEnds = String(viewer.getSelectedElementsEx(true, false)).split(/,/);

        if (arrSelEnds.length > 0) {
            var itemID = String(viewer.getSelectedElements()).split(/,/);
            var item1 = "";
            var i, j, l, k;
            var temp1, temp2, entA;
            var entLinks;
            var pathResult = [];
            for (k = 0; k < itemID.length; k++) {
                item1 = itemID[k];
                entLinks = String(viewer.getEndLinks(item1)).split(/,/);
                for (i = 0; i < entLinks.length; i++) {
                    temp1 = String(viewer.getLinkEnd1(entLinks[i]));
                    temp2 = String(viewer.getLinkEnd2(entLinks[i]));
                    entA = (temp1 == item1) ? temp2 : temp1;
                    pathResult.push(item1 + "," + entLinks[i] + "," + entA);
                }
            }
            if (entLinks[0] == "") {
                alert("没有相关的链接");
                return;
            }
            var eachPath;
            for (j = 0; j < pathResult.length; j++) {
                eachPath = String(pathResult[j]).split(/,/);
                for (l = 0; l < eachPath.length; l++) {
                    viewer.setElementSelected(eachPath[l], true);
                }

            }
        } else {
            alert("请先选择实体");
        }
    }

//选择相关实体(按钮)
    function findAllEnds() {
        var arrSelEnds = String(viewer.getSelectedElementsEx(false, true)).split(/,/);
        if (arrSelEnds.length > 0) {
            var itemID = String(viewer.getSelectedElements()).split(/,/);
            var item1 = "";
            var i, j, k;
            var temp1, temp2;
            var pathResult = [];
            for (k = 0; k < itemID.length; k++) {
                item1 = itemID[k];
                temp1 = String(viewer.getLinkEnd1(item1));
                temp2 = String(viewer.getLinkEnd2(item1));
                pathResult.push(temp1 + "," + item1 + "," + temp2);
            }
            var eachPath;
            for (i = 0; i < pathResult.length; i++) {
                eachPath = String(pathResult[i]).split(/,/);
                for (j = 0; j < eachPath.length; j++) {
                    viewer.setElementSelected(eachPath[j], true);
                }
            }
        } else {
            alert("请先选择链接");
        }
    }
}