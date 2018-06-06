// ====== 定义常量 ======
var TREE_IMG_I = "Images/TreeImages/I.gif";
var TREE_IMG_F = "Images/TreeImages/F.gif";
var TREE_IMG_T = "Images/TreeImages/T.gif";
var TREE_IMG_L = "Images/TreeImages/L.gif";
var TREE_IMG_R = "Images/TreeImages/R.gif";

var TREE_IMG_MINUS  = "Images/TreeImages/minus.gif";
var TREE_IMG_FMINUS = "Images/TreeImages/Fminus.gif";
var TREE_IMG_TMINUS = "Images/TreeImages/Tminus.gif";
var TREE_IMG_LMINUS = "Images/TreeImages/Lminus.gif";
var TREE_IMG_RMINUS = "Images/TreeImages/Rminus.gif";

var TREE_IMG_PLUS  = "Images/TreeImages/plus.gif";
var TREE_IMG_FPLUS = "Images/TreeImages/Fplus.gif";
var TREE_IMG_TPLUS = "Images/TreeImages/Tplus.gif";
var TREE_IMG_LPLUS = "Images/TreeImages/Lplus.gif";
var TREE_IMG_RPLUS = "Images/TreeImages/Rplus.gif";

var TREE_IMG_WHITE = "Images/TreeImages/white.gif";

var FOLDER_ClOSE   = "Images/TreeImages/folder_close.gif";
var FOLDER_OPEN    = "Images/TreeImages/folder_open.gif";
var FILE_ClOSE     = "Images/TreeImages/file_close.gif";
var FILE_OPEN      = "Images/TreeImages/file_open.gif";

var TREE_ITEM_IMG_ID_PFIX   = "TreeItemImg_"        // 树项图标<Img>的ID前缀
var TREE_ITEM_TITLE_ID_PFIX = "TreeItemTitle_"      // 树项图标<Span>的ID前缀
var TREE_ITEMS_DIV_ID_PFIX  = "TreeItemsDiv_"       // 树项组<Div>的ID前缀

var BACKCOLOR_SELECTED    = "#0A246A";
var BACKCOLOR_OVER        = "#808DB0";
var BACKCOLOR_OUT         = "#F5F5F5";
var FRONTCOLOR_SELECTED   = "#FFFFFF";
var FRONTCOLOR_UNSELECTED = "#000000";
var FRONTCOLOR_DISABLED   = "#999999";
var BACKCOLOR_DISABLED    = "#F5F5F5";



// ====== 定义全局变量 ======
var numTreeItem = 0;      // 树项的数目
var objPrevSelectedTreeItem = "";      // 预存选则的TreeItem


// ====== 定义对象 ======
////////////////////////////////////////////////////////
// 函数名称：Tree
// 功能说明：定义Tree Class
// 参数说明：name -- 树的名称，可选
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
function Tree(name)
{
	this.name = name;
	this.childs = [];
}

////////////////////////////////////////////////////////
// 函数名称：Tree.prototype.toString
// 功能说明：定义Tree的视图
// 参数说明：无
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
Tree.prototype.toString = function()
{
	var str = "";
	var level = 0;    // 传入菜单的级别
	
	for(var i = 0; i < this.childs.length; i++)
	{
		if(this.childs.length == 1)    // 如果是唯一的孩子
		{
			this.childs[i].isOnlyChild = true;
		}
		
		if(this.childs.length > 1)    // 如果是最大的孩子
		{
			this.childs[0].isFirstChild = true;
		}
		
		if(this.childs.length - 1 == i)    // 如果是最小的孩子
		{
			this.childs[i].isLastChild = true;
		}

		str += this.childs[i].toString(level+1, "");	
		
	}
	
	return str;
}

////////////////////////////////////////////////////////
// 函数名称：Tree.prototype.addItem
// 功能说明：定义Tree的addItem方法，Tree添加Item
// 参数说明：obj -- TreeItem 对象
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
Tree.prototype.addItem = function(obj)
{
	this.childs.push(obj);
}

////////////////////////////////////////////////////////
// 函数名称：TreeItem
// 功能说明：定义TreeItem Class
// 参数说明：title -- 树项的名称
//           url   -- 树项所连接的地址
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
function TreeItem(id, title, tip, url, enabled, frameStr)
{
    this.id       = id;	
    this.itemId   = id;
	this.title    = title;
	this.tip      = tip;
	this.url      = url;
	this.enabled  = enabled;
	this.frameStr = frameStr;
	if(frameStr == null || frameStr == undefined || frameStr == "")
	    this.frameStr = "right";
    
	this.isOnlyChild  = false;
	this.isFirstChild = false;
	this.isLastChild  = false;
	
	this.childs = [];
	this.level  = 0;
}

////////////////////////////////////////////////////////
// 函数名称：TreeItem.prototype.toString
// 功能说明：定义TreeItem的视图
// 参数说明：level   -- 树项所在层级
//           strIcon -- 树项父亲的前引图标
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
TreeItem.prototype.toString = function(level, strIcon)
{
	numTreeItem++;
	//this.itemId = String(numTreeItem);    // 树项的ID放在View中确定
	
	var str = "";
	this.level = level;      // 树项的级别放在View中确定
	
	str += "<div nowrap style='vertical-align:top;width:100%; border:0px red solid'  >" ;
	
	str += strIcon;    // 加入前引图标	
	str += this.getLastIcon();    // 加入最后位置的图标
	
	var urlString = " url=" + this.url;
	if(this.url == "" || this.url == undefined || this.url == null)
	{
	    urlString = "";
	}
	
	if(this.enabled == false)
	{
		str +=  "<span nowrap id=" + TREE_ITEM_TITLE_ID_PFIX + this.itemId + " style='cursor:hand;  padding:2px 2px 0px 2px; color:"+ FRONTCOLOR_DISABLED +"'" + urlString; 
		str +=  " title='" + this.tip + "'>" + this.title + "</span>";    // 鍔犲叆title
	}
	else
	{
		str +=  "<span nowrap id=" + TREE_ITEM_TITLE_ID_PFIX + this.itemId + " style='cursor:hand;  padding:2px 2px 0px 2px'" + urlString + " onclick='TreeTitle_OnClickEvent(this, " + this.childs.length + ",\"" + this.frameStr +"\")'"; 
		str +=  "onmouseover='TreeTitle_OnOverEvent(this,\"over\")' onmouseout='TreeTitle_OnOverEvent(this,\"out\")' title='" + this.tip + "'>" + this.title + "</span>";    // 加入title
	}
	str += "</div>"
	
	str += this.getAllChilds(strIcon);  //递归出所有孩子
	
	return str;
}

////////////////////////////////////////////////////////
// 函数名称：TreeItem.prototype.getLastIcon
// 功能说明：获取TreeItem的最后位置的图标
// 参数说明：无
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
TreeItem.prototype.getLastIcon = function()
{
	var level = this.level;
	var str = "";
	
	// 最后位置的图标判断	
	if(this.childs.length == 0)    // 如果没有孩子
	{	
		
		if(this.isOnlyChild & (level == 1))  // 如果是唯一的孩子且在1级
		{
			str += "<img src=" + TREE_IMG_R + " align=middle border=0 >" ;
		}
		else if(this.isFirstChild & (level == 1))  // 如果是最大的孩子且在1级
		{
			str += "<img src=" + TREE_IMG_F + " align=middle border=0 >";
		}
		else if(this.isLastChild)    // 如果是排行最小的孩子
		{
			str += "<img src=" + TREE_IMG_L + " border=0 align=middle >";
		}
		else
		{
			str += "<img src=" + TREE_IMG_T + " border=0 align=middle >";				  
		}
		str += "<img src=" + FILE_ClOSE + " border=0 align=middle >";

	}
	else      // 如果有孩子
	{
		if(this.isOnlyChild & (level == 1))    // 如果是唯一的孩子且在1级
		{
			str += "<img src=" + TREE_IMG_RPLUS + " align=middle border=0 ";
		}
		else if(this.isFirstChild & (level == 1))    // 如果是最大的孩子且在1级
		{
			str += "<img src=" + TREE_IMG_FPLUS + " align=middle border=0 ";
		}
		else if(this.isLastChild)    // 如果是排行最小的孩子
		{//alert(this.isLastChild);
			str += "<img src=" + TREE_IMG_LPLUS + " align=middle border=0 ";
		}
		else
		{
			str += "<img src=" + TREE_IMG_TPLUS + " align=middle border=0 "	;				  
		}
		
		str += "id=" + TREE_ITEM_IMG_ID_PFIX + this.itemId + " style='cursor:hand' onclick='TreeImg_OnClickEvent(this)'  >";      // 添加图标ID,添加图标Click事件
		str += "<img src=" + FOLDER_ClOSE + " border=0 align=middle >";
	}	
	return str;
}

////////////////////////////////////////////////////////
// 函数名称：TreeItem.prototype.getAllChilds
// 功能说明：获取所有孩子的视图
// 参数说明：strIcon - 父亲的前引图标
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
TreeItem.prototype.getAllChilds = function(strIcon)
{
	var str = "";
	
	// 前引图标判断
	if(!this.isLastChild)    // 不是最小的孩子且不在1级
	{
		strIcon += "<img src=" + TREE_IMG_I + " border=0 align=middle >";
	}
	else if(this.isLastChild)    // 是最小的孩子且不在1级
	{
		strIcon += "<img src=" + TREE_IMG_WHITE + " border=0 align=middle >";
	}
	
	str += "<div id=" + TREE_ITEMS_DIV_ID_PFIX + this.itemId + " style='display:none'>"    // 菜单项隐藏
	for(var i=0; i<this.childs.length; i++)
	{
		if(this.childs.length == 1)    // 如果是唯一的孩子
		{
			this.childs[i].isOnlyChild = true;
		}
		
		if(this.childs.length > 1)    // 如果是最大的孩子
		{
			this.childs[0].isFirstChild = true;
		}
		
		if(this.childs.length - 1 == i)    // 如果是最小的孩子
		{
			this.childs[i].isLastChild = true;
		}
		
		//禁用对象
		if (this.enabled == false)
		{
			this.childs[i].enabled = this.enabled;
		}
		str += this.childs[i].toString(this.level+1, strIcon);	    // 递归调用
		
	}
	str += "</div>";	
	
	return str;
}

////////////////////////////////////////////////////////
// 函数名称：TreeItem.prototype.addItem
// 功能说明：定义TreeItem的addItem方法，TreeItem添加子TreeItem
// 参数说明：obj -- TreeItem对象
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
TreeItem.prototype.addItem = function(obj)
{
	this.childs.push(obj);	
}



// ======================================== 定义事件 ================================

////////////////////////////////////////////////////////
// 函数名称：TreeImg_OnClickEvent
// 功能说明：定义图标单击事件
// 参数说明：obj -- 图标对象
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
function TreeImg_OnClickEvent(obj)
{
	if(obj.src.indexOf("plus.gif")>0)
	{
		changeItemStatus(obj,"expand");
	}
	else if(obj.src.indexOf("minus.gif")>0)
	{
		changeItemStatus(obj,"collapse");	
	}
}

////////////////////////////////////////////////////////
// 函数名称：TreeTitle_OnClickEvent
// 功能说明：定义标题单击事件
// 参数说明：obj -- 标题对象
//           childslength -- TreeItem的孩子数
// 开发人员：tu.zhengjun
// 完成日期：2007-2-25
///////////////////////////////////////////////////////
function TreeTitle_OnClickEvent(obj, childslength,frameStr)
{
	// 改变title的背景色
	if(objPrevSelectedTreeItem != "")
	{
		objPrevSelectedTreeItem.style.background = "";

		if(objPrevSelectedTreeItem.style.color == FRONTCOLOR_DISABLED)
		{
			objPrevSelectedTreeItem.style.color = FRONTCOLOR_DISABLED ;
		}
		else
		{
			objPrevSelectedTreeItem.style.color = "" ;
		}


		if(objPrevSelectedTreeItem.previousSibling.src.indexOf("file_") != -1)
			objPrevSelectedTreeItem.previousSibling.src = objPrevSelectedTreeItem.previousSibling.src.replace("open.gif", "close.gif"); //改变文件图标

	}
	obj.style.background = BACKCOLOR_SELECTED ;
	if(obj.style.color == FRONTCOLOR_DISABLED)
	{
		obj.style.color = FRONTCOLOR_DISABLED ;
	}
	else
	{
		obj.style.color = FRONTCOLOR_SELECTED ;
	}
	objPrevSelectedTreeItem = obj;
	objPrevSelectedTreeItem.style.color = obj.style.color;

	obj.previousSibling.src = obj.previousSibling.src.replace("close.gif", "open.gif");  //改变文件图标

	// 收缩树项
	if(childslength > 0)
	{
		var objDest = document.getElementById(obj.id.replace("Title", "Img"));
		TreeImg_OnClickEvent(objDest)    // 调用图标单击事件函数
	}
	
	// 重新载入<iframe>

	if(obj.url != "" && obj.url != undefined && obj.url != null)
	{
    	if(frameStr == null || frameStr == undefined || frameStr == "")
    	    frameStr = "right";
		var scriptStr = "top." + frameStr +".location.href= '" + obj.url + "';";
		if(frameStr == "true")
		    scriptStr = "window.open('" + obj.url
			        + "','u','scrollbars=yes,dependent,width='+(screen.availWidth-300)+',height='+(screen.availHeight-200)+',left=0,top=0');";
		try
		{
			eval(scriptStr);
		}
		catch(exception)
		{
			scriptStr = "window.open('" + obj.url
				+ "','u','scrollbars=yes,dependent,width='+(screen.availWidth-300)+',height='+(screen.availHeight-200)+',left=0,top=0');";
			eval(scriptStr);
		}
	}

}


////////////////////////////////////////////////////////
// 函数名称：TreeTitle_OnClickEvent
// 功能说明：定义标题鼠标悬浮事件
// 参数说明：obj -- 标题对象
//           childslength -- TreeItem的孩子数
// 开发人员：tu.zhengjun
// 完成日期：2007-9-14
///////////////////////////////////////////////////////
function TreeTitle_OnOverEvent(obj, str)
{
	if(obj == objPrevSelectedTreeItem) return false;

	if(str == "over")
	{		
		obj.style.background = BACKCOLOR_OVER ;
		obj.style.color = FRONTCOLOR_SELECTED ;

	}
	else
	{
		obj.style.background = BACKCOLOR_OUT ;
		obj.style.color = FRONTCOLOR_UNSELECTED ;

	}
}


// ======================================== 定义方法 ================================

////////////////////////////////////////////////////////
// 功能说明：展开收缩树项
// 开发人员：tu.zhengjun
// 完成日期：2007-9-14
////////////////////////////////////////////////////////
function changeItemStatus(obj,str)
{
	if(str== null && str == "")
	{
		return false;
	}

	if(str=="expand")
	{
		obj.src = obj.src.replace("plus.gif", "minus.gif");
		obj.nextSibling.src = obj.nextSibling.src.replace("close.gif", "open.gif");
		
		var strDiv  = obj.id.replace(TREE_ITEM_IMG_ID_PFIX, TREE_ITEMS_DIV_ID_PFIX);
		var objDest = document.getElementById(strDiv);	
		objDest.style.display = "";
	}
	else if(str=="collapse")
	{
		obj.src = obj.src.replace("minus.gif", "plus.gif");
		obj.nextSibling.src = obj.nextSibling.src.replace("open.gif", "close.gif");
		
		var strDiv  = obj.id.replace(TREE_ITEM_IMG_ID_PFIX, TREE_ITEMS_DIV_ID_PFIX);
		var objDest = document.getElementById(strDiv);	
		objDest.style.display = "none";		
	}
}

////////////////////////////////////////////////////////
// 功能说明：展开收缩所有树项
// 开发人员：tu.zhengjun
// 完成日期：2007-9-14
////////////////////////////////////////////////////////
function changeAllItemsStatus(str)
{
	if(str == null && str == "")
	{
		return false;
	}

	var objItems = document.getElementsByTagName("img");

	for(var i=0; i < objItems.length; i++)
	{
		if(objItems[i].src.indexOf("minus.gif") != -1 || objItems[i].src.indexOf("plus.gif") != -1)
		{
			changeItemStatus(objItems[i],str)
		}
	}
}


// ================================ XML DOM =====================================
function createXmlTree(xmlPath)
{
	var xmlDOM = new ActiveXObject("Microsoft.XMLDOM");
	xmlDOM.async = "false";
	xmlDOM.load(xmlPath);

	var treedemo = new Tree(); 
	var xmlNode = xmlDOM.documentElement; 
	createTreeNode(treedemo, xmlNode);
	document.write(treedemo);
}

function createTreeNode(parentNode, xmlNode)
{
	for(var i = 0; i < xmlNode.childNodes.length; i++)
	{	
		var enabled = (xmlNode.childNodes[i].getAttribute("Enabled")=="false") ? false : true;
		var tip = (xmlNode.childNodes[i].getAttribute("Tip")== null) ? "" : xmlNode.childNodes[i].getAttribute("Tip");
		if(xmlNode.childNodes[i].getAttribute("Target") != "" && xmlNode.childNodes[i].getAttribute("Target") != null)
		{
			var childNode = new TreeItem(xmlNode.childNodes[i].getAttribute("Title"),tip, xmlNode.childNodes[i].getAttribute("Target"), enabled);
		}
		else
		{
			var childNode = new TreeItem(xmlNode.childNodes[i].getAttribute("Title"),tip,null,enabled);
		}
		parentNode.addItem(childNode);

		createTreeNode(childNode, xmlNode.childNodes[i]);
	}
}



