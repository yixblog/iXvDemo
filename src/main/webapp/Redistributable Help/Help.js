//sniff browser
if (document.layers) 
{//Browser is Netscape 4
	var gBrowser = "Netscape4";
	var origWidth = innerWidth;
	var origHeight = innerHeight;
}
else if (navigator.userAgent.indexOf("MSIE") != -1 && navigator.userAgent.indexOf("Opera") == -1)
{//Browser is IE 5.0+	
	var gBrowser = "IE";	
}
else if (navigator.userAgent.indexOf("MSIE") != -1 && navigator.userAgent.indexOf("Opera") != -1)
{//Browser is Opera	
	var gBrowser = "Opera";	
}
else if (navigator.userAgent.indexOf("Netscape") != -1)
{//Browser is Netscape 6.2.3 - 7.2	
	var gBrowser = "Netscape6to7"; 
}
else if (navigator.userAgent.indexOf("Firefox") != -1)
{//Browser is Mozilla Firefox, Netscape 8.0+	
	var gBrowser = "Firefox"; 
}
else
{//Browser could be Mozilla Firebird, Safari...
	var gBrowser = "Other"; 
}
//alert (gBrowser)

function bodyLoad()
{
	alignBullets();
	alignNumbers();
	document.body.onclick = hidePopups;
}

function showPopup(id, x, y)
{
	hidePopups();
	var div = document.getElementById(id);
	if (div) 
	{
		div.style.left = x;
		div.style.top = y;
		div.style.visibility = "visible";
	}
}

function hidePopups()
{
	var pres = document.getElementsByTagName("DIV");
	var pre;

	if (pres) {
		for (var iPre = 0; iPre < pres.length; iPre++) {
			pre = pres[iPre];
			if (pre.className) {
				if (pre.className == "popup") {
					pre.style.visibility = "hidden";
				}
			};
		}
	}
}

var gSwap;
function swapChart(chart)
{
	var chartId = document.getElementById("chart");
    if (!gSwap)
    {//user clicking on a button, so show the chart for that button. 
		chartId.src = "graphics\\" + chart;
	    gSwap = true;
	}
	else
    {
		//Workaround for weird Firefox and NS 8 bug that escapes final forward slash in the image URL.
		var actualChartMozilla = chartId.src.replace(/%5C/g, "/");
		actualChart = actualChartMozilla.substr(actualChartMozilla.lastIndexOf("/") + 1);
		if (actualChart == chart)
		{//user clicking on the same button, so turn the chart off.
		    chartId.src = "graphics\\Viewer5-ImageSwap1.gif";
		}
		else
		{//user clicking on a different button, so show the chart for that button instead.
		    chartId.src = "graphics\\" + chart;
		}
	    gSwap = false;
    }
}

//Dropdowns (Can't See only...)
function dropdown(divIdentity)
{
    var text = document.getElementById(divIdentity);
    if (text)
    {
	    if (text.style.display == 'none')
	    {
		    text.style.display = '';
	    }
	    else
	    {
		    text.style.display = 'none';
	    }
	}
}
		
//Title bar highlight 
function highlight(element, color)
{
    element.style.backgroundColor = color;
	element.style.cursor = "hand";
}	

//Align bullets in different browsers. Might need to tweak CSS classes for different page margins.
function alignBullets()
{
    var bullets = document.getElementsByTagName("ul");
    var i;
	if (bullets)
	{
		for (var i = 0; i < bullets.length; i++)
		{
			bullet = bullets[i];
	        if (gBrowser == "Netscape4")
	        {
		        bullet.className = "bullet-ns4";
	        }
	        else if  ((gBrowser == "Netscape6to7") || (gBrowser == "Firefox") || (gBrowser == "Other"))
	        {
	            //alert ("ns");
		        bullet.className = "bullet-ns6";
	        }
            else
	        {//IE, Opera
	            //alert ("ie");
		        bullet.className = "bullet-ie";
	        }
	    }
    }
}

//Align numbers in different browsers. Might need to tweak CSS classes for different page margins.
function alignNumbers()
{
    var numbers = document.getElementsByTagName("ol");
    var i;
    
	if (numbers)
	{
		for (var i = 0; i < numbers.length; i++)
		{
			number = numbers[i];
	        if (gBrowser == "Netscape4")
	        {
		        number.className = "number-ns4";
	        }
	        else if  ((gBrowser == "Netscape6to7") || (gBrowser == "Firefox") || (gBrowser == "Other"))
	        {
	            //alert ("ns");
		        number.className = "number-ns6";
	        }
            else
	        {//IE, Opera
	            //alert ("ie");
		        number.className = "number-ie";
	        }
	    }
    }
}

