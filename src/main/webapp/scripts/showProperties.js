var win = false;

function showProperties(chart, itemID){
	
	var props = new String(chart.getItemsPropertyIDs(itemID));
	var propIDs = props.split(',');
	var propertyHTML=""
	var hasDisplayProps = false;
	
	propertyHTML+="<table cellpadding=0 cellspacing=0><tr><td height='30' bgcolor='#F3EFEF'><font size=2 face='Arial'><b>&nbsp;Type</b></font></td><td bgcolor='#F3EFEF'><font size=2 face='Arial'><b>&nbsp;Value</b></font></td></tr>";
	var bgcolor="#F3EFEF";
	var i=0;
	for(i=0;i<propIDs.length;i++){
		if(chart.getPropertyValue(itemID, propIDs[i])!=""){
			if(bgcolor=="#F3EFEF"){
				bgcolor = "white";
			}else{
				bgcolor = "#F3EFEF";
			}
			propertyHTML+="<tr><td bgcolor='"+bgcolor+"' width='200' height='20' valign='top'>";
			propertyHTML+="<font size=2 face='Arial'>&nbsp;"+chart.getPropertyDisplayName(itemID, propIDs[i])+"</font>";
			propertyHTML+="</td><td bgcolor='"+bgcolor+"' width='300'>";
			propertyHTML+="<font size=2 face='Arial'>&nbsp;"+chart.getMultiplePropertyValues(itemID, propIDs[i], ",<br>&nbsp;")+"</font>";
			propertyHTML+="</td></tr>";
			hasDisplayProps = true;
		}
	}
	propertyHTML+="</tr></table>";
	
	if(!hasDisplayProps)
	{
		propertyHTML = "<br>&nbsp;<font size=2 face='Arial'>No properties to display on this item</font>";
	}

	propertyWindow(propertyHTML, "Properties");
}

function propertyWindow(str, title){
	if(win && win.close)
		win.close()
	win = window.open("", title, "top=200,left=200,width=500,height=250,scrollbars=yes"); // a window object
	win.document.open("text/html", "replace");
	win.document.write("<HTML><meta http-equiv='Content-Type' content='text/xml; charset=windows-1252'><HEAD><TITLE>"+title+"</TITLE></HEAD><BODY style='margin:0'><div id='content'>"+str+"</div></BODY></HTML>");
	win.document.close();
}