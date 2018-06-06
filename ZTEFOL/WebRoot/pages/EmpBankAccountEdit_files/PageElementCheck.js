/*
版权所有：版权所有(C) 2004，中兴通讯
文件编号：
文件名称：PageElementCheck.js
系统编号：Z0004001
系统名称：财务管理系统
模块编号：05
模块名称：
设计文档：
完成日期：2004-8-13
作　　者：王柱辉
内容摘要：主要是完成页面中文本录入框的部分验证
	   

文件调用：调用必须把文件加入页面中,要在页面中在</BODY>后加入如下代码
<script src ="../../../Fbp/Common/Js/PageElementCheck.js" language=javascript></script>	
只能录入正整数使用示例：
<asp:textbox id="txtTest" runat="server" validate="+INT" style="IME-MODE:disabled"></asp:textbox>
对应的	validate="+INT"中	validate的可选项有：
 
 DOUBLE	 ：数值型
 DOUBLE2 ：二位小数的数据型
 DOUBLE4 ：四位小数的数据型
 +INT    ：正整数
 INT     ：负整数
 LETTER  ：二十六字母
 WORD    ：二十六字母和下划线
 
 */ 
 
 
//验证方法
function regInput(obj, reg, inputStr)
{
		if(obj.readOnly)
		{
			return;
		}
		else
		{
		var docSel	= document.selection.createRange()
		if (docSel.parentElement().tagName != "INPUT")	return false
		oSel = docSel.duplicate()
		oSel.text = ""
		var srcRange	= obj.createTextRange()
		oSel.setEndPoint("StartToStart", srcRange)
		var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length)
		return reg.test(str);
		}
}

//获得页面中的事件
function keypress()
{
	var objTxt = event.srcElement;
	if(objTxt.validate != undefined)
	{
		return regInput(objTxt,GetReg(objTxt.validate),String.fromCharCode(event.keyCode));			
	}
}

//获得页面中的粘贴事件
function paste()
{
	var objTxt = event.srcElement;
	if(objTxt.validate != undefined)
	{
		return regInput(objTxt,GetReg(objTxt.validate),window.clipboardData.getData('Text'));			
	}
}
//获得页面中的拖放
function drop()
{
	var objTxt = event.srcElement;
	if(objTxt.validate != undefined)
	{
		return regInput(objTxt,GetReg(objTxt.validate),event.dataTransfer.getData('Text'));			
	}
}

//根据类型得到验证正则表达式
function GetReg(type)
{
	var returnvalue = null;
	switch(type.toUpperCase())
	{
		case "DOUBLE":
			//数值
			returnvalue = /^-?\d*\.?\d*$/;
			break;
		case "DOUBLE2":
			//二位小数的数
			returnvalue = /^-?\d*\.?\d{0,2}$/;
			break;
	        case "DOUBLE4":
			//二位小数的数
			returnvalue = /^-?\d*\.?\d{0,4}$/;
			break;
		case "+DOUBLE":
			//正数值
			returnvalue = /^\d*\.?\d*$/;
			break;
		case "+DOUBLE2":
			//正二位小数的数
			returnvalue = /^\d*\.?\d{0,2}$/;
			break;
		case "+DOUBLE4":
			//正二位小数的数
			returnvalue = /^\d*\.?\d{0,4}$/;
			break;
		case "+DOUBLE8":
			//正二位小数的数
			returnvalue = /^\d*\.?\d{0,8}$/;
			break;
		case "+INT":
			//正整数
			returnvalue = /^[1-9]+\d*$/;
			break;
		case "INT":
			//整数
			returnvalue = /^-?\d*$/;
			break;
		case "LETTER":
			//二十六个字母
			returnvalue = /^[A-za-z]+$/;
			break;
		case "WORD":
			//二十六字线和数字下划线
			returnvalue = /^\w+$/;
			break;
	}
	return returnvalue;
}

//为页面中录入文本框加事件
function PageElementCheckInit()
{
  var objs = document.getElementsByTagName("input");
  for ( i=0;i<objs.length;i++)
  {
	if(objs[i].type.toUpperCase() == "TEXT")
	{
	  if ( objs[i].validate != undefined)
	  {
		objs[i].attachEvent("onkeypress",keypress);
		objs[i].attachEvent("onpaste",paste);
		objs[i].attachEvent("ondrop",drop);
		objs[i].attachEvent("onchange",onchange);
	  }		
	}
  }
}

function onchange()
{
    //debugger;
	var objTxt = event.srcElement;
	if(objTxt.validate != undefined)
	{
	    var strAllowChar="-0123456789.";
	    var str =document.getElementById(objTxt.id).value;
	    for(i=0;i<str.length;i++)
	    {
	       var strOne=str.substring(i,i+1);
	       if (strAllowChar.indexOf(strOne)==-1)
	       {
	           if(document.getElementById('hiCulture') != null && document.all.hiCulture.value ==  'en-US')
	           {
	               alert('Please Input Number !');
	           }
	           else
	           {
	               alert("输入内容应为数字！");
	           }
	           
    	       document.getElementById(objTxt.id).focus();
    	       document.getElementById(objTxt.id).value="";
	           return false;
	       }
	    }
	}
	return true;
}

PageElementCheckInit();


