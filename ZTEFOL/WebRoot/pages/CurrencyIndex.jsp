<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<!-- saved from url=(0104)http://fol.zte.com.cn/Boe/Search/MyProposerIndex.aspx -->
<%@ page language="java" import="java.util.*"%>
<HTML><HEAD><TITLE>我申请的单据</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="MyProposerIndex_files/style.css">
<SCRIPT language=javascript src="MyProposerIndex_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="MyProposerIndex_files/FbpLovSelect.js"></SCRIPT>

<SCRIPT language=javascript 
src="MyProposerIndex_files/AdminPageFunction.js"></SCRIPT>

<SCRIPT type=text/javascript src="MyProposerIndex_files/Calendar.js"></SCRIPT>

<SCRIPT type=text/javascript src="MyProposerIndex_files/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="MyProposerIndex_files/jquery.blockUI.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="MyProposerIndex_files/itp_investigate.js"></SCRIPT>

<SCRIPT language=javascript type=text/javascript 
src="MyProposerIndex_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript src="validate.js"></SCRIPT>

<SCRIPT language=javascript>
			function showBoeList(boeID,boeNum,employeeID)
			{
				var resourceID=document.all("hiResourceID").value;
				var menuPath=document.all("hiMenuPath").value;
				var url="../../BoeApproval/Pages/FinanceApprovalList.aspx?resourceID="+resourceID+"&menuPath="+menuPath;
				url+="&boeID="+boeID+"&boenum="+boeNum + "&employeeID="+employeeID + "&pflag=MyProposerIndex&hasframe=1";
				parent.ShowPage(0,url);
			    //window.navigate(url);
			}
			
			function clear_text()
			{
				document.all("txtCurrencyName").value="";
				document.all("txtCurrencyID").value="";		
				return false;
			}
			
			function showBpHis(bpNum)
			{
			    var resourceID=document.all("hiResourceID").value;
				var menuPath=document.all("hiMenuPath").value;
				var url="../../BpManage/Pages/BpActiveHis.aspx?resourceID="+resourceID+"&menuPath="+menuPath;
				url+="&bpnum="+bpNum + "&pflag=MyProposerIndex";
			    //window.navigate(url);
			    parent.ShowPage(0,url);
			}
			
		function getEmpoyeeId()
        {
            //页面已经有员工短工号，直接使用，没有短工号的需要CS中SESSION获取
            return document.getElementById("hiCurrentShortNum").value;
        }    
        
        function getLanguage()
        {
            var curCulture = document.getElementById("hiCulture").value;
            return curCulture;
        }     
        
        
        function  CheckDate(Obj)
        {
               var dateStart = Obj.parentNode.childNodes[0];
                var dateEnd = Obj;
 
                // 到期日日期格式交验
                if(dateStart != null && dateEnd != null)
                {
                    if(dateStart.value != "" && dateEnd.value != "")
                    {
                        if(dateStart.value > dateEnd.value )
                        {
                            alert('结束日期不能小于开始日期！');
 
                            dateStart.value = "";
                            dateEnd.value = "";
 
                            return false;
                        }
                    }
                } 
                return true;

        }
        
        function add()
        {
        	 Form1.action = "currency.do?action=add";
        	 //Form1.submit();
        }
        
        
         <% if(request.getAttribute("AddisSuccess") != null)
         {
          if((Boolean)request.getAttribute("AddisSuccess")) { %>
         	 alert("添加成功");
         <%} else {%> 
         	alert("添加失败");
        <%  }
         }
        %>  
        
         <% if(request.getAttribute("DelisSuccess") != null)
         {
          if((Boolean)request.getAttribute("DelisSuccess")) { %>
         	 alert("删除成功");
         <%} else {%> 
         	alert("删除失败");
        <%  }
         }
        %>  
    </SCRIPT>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 action=currency.do>
<SCRIPT type=text/javascript>
<!--
var theForm = document.forms['Form1'];
if (!theForm) {
    theForm = document.Form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
// -->
</SCRIPT>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="MyProposerIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>信息管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>币种管理 </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyProposerIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV style="WIDTH: 98%" id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD class=title height=24>币种名称<font color="#ff0000">*</font></TD>
    <TD><INPUT style="WIDTH: 172px; HEIGHT: 21px" id=txtCurrencyName 
    name=txtCurrencyName></TD>
    <TD class=title><SPAN id=lblCurrency>币种ID<font color="#ff0000">*</font></SPAN></TD>
    <TD><INPUT style="WIDTH: 128px" id=txtCurrencyID size=14 
      name=txtCurrencyID></TD>
    </TR>
  <TR>
    <TD class=bottom colSpan=6 align=middle><INPUT id=hiDeptID size=1 
      type=hidden name=hiDeptID><INPUT id=hiUserID size=1 type=hidden 
      name=hiUserID>&nbsp; <INPUT id=btnQuery class=stbtm01 value=添加 onclick="add()" type=submit name=btnQuery><INPUT 
      id=hiEmployeeID size=1 type=hidden name=hiEmployeeID><INPUT 
      id=hiProdProjectID size=1 type=hidden name=hiProdProjectID> <INPUT id=btnClear language=javascript class=stbtm01 onclick="javascript:return clear_text();" value=清空 type=button name=btnClear><INPUT 
      style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture value=zh-CN size=1 
      type=hidden name=hiCulture> </TD></TR></TBODY></TABLE></DIV>
</FORM>
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="98%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="MyProposerIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>币种列表</SPAN> </TD></TR></TBODY></TABLE>
<DIV style="WIDTH: 98%" class=autoOverIpt align=center>
<TABLE id=Table2 border=0 cellSpacing=0 cellPadding=1 width="100%">
  <TBODY>
  <TR>
    <TD>
      <TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval class=datatbl 
      border=1 rules=all cellSpacing=0>
        <TBODY>
        <TR class=head>
          <TD style="WIDTH: 33%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl0','')">币种名称</A></TD>
          <TD style="WIDTH: 33%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">币种ID</A></TD>
          <TD noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl9','')"></A></TD></TR>
         <%
            HashMap<Integer, String> currencyMap = (HashMap<Integer, String>)request.getAttribute("currencyInfo");
  	        Iterator<Integer> iterator = currencyMap.keySet().iterator();
            while(iterator.hasNext())
            {
            int key = iterator.next();
         %>
         
        <TR class=item>
          <TD noWrap align=left>
          	<%= currencyMap.get(key)%>
          </TD>
          <TD noWrap align=left><SPAN 
            id=dgApproval__ctl2_lbl_CurrencyName><%= key%></SPAN> </TD>
          <TD noWrap style="text-align: center"><a href="currency.do?action=delete&&id=<%=key%>">删除</a>
            <INPUT id=dgApproval__ctl2_hiEmployeeIDRow value=244680 type=hidden 
            name=dgApproval:_ctl2:hiEmployeeIDRow> </TD>
         </TR>
         <%
           }
         %></TBODY></TABLE></TD></TR></TBODY></TABLE></DIV>
<TABLE style="WIDTH: 98%" id=Table1 class=tabbar border=0 cellSpacing=0 
cellPadding=0>
  <TBODY>
  <TR>
    <TD>
      <DIV id=ZteWebPager1>
      <TABLE class=tabbar border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR>
          <TD style="WIDTH: 35%" vAlign=center align=left></TD>
          <TD noWrap align=right>第<SPAN 
            name="ZteWebPager1_ci">1</SPAN>页/共<SPAN 
            name="ZteWebPager1_pc">1</SPAN>页&nbsp;&nbsp;共<SPAN 
            name="ZteWebPager1_rc">1</SPAN>条记录&nbsp;&nbsp;每页<SELECT 
            style="TEXT-ALIGN: left; IME-MODE: disabled; WIDTH: 56px" 
            id=ZteWebPager1_PageSize 
            onchange="document.all['ZteWebPager1'][1].click();" 
            name=ZteWebPager1_PageSize value="10"> <OPTION selected 
              value=10>10</OPTION><OPTION value=50>50</OPTION><OPTION 
              value=100>100</OPTION><OPTION value=500>500</OPTION><OPTION 
              value=1000>1000</OPTION><OPTION 
            value=5000>5000</OPTION></SELECT>行&nbsp;<IMG border=0 align=baseline 
            src="MyProposerIndex_files/first.gif"><SPAN 
            style="WIDTH: 5px"></SPAN><IMG border=0 align=baseline 
            src="MyProposerIndex_files/prev.gif"><SPAN 
            style="WIDTH: 5px"></SPAN><INPUT 
            style="TEXT-ALIGN: left; IME-MODE: disabled; WIDTH: 40px" 
            onkeydown="if (event.keyCode == 13) document.all['ZteWebPager1'][1].click();" 
            id=ZteWebPager1_PageIndex 
            onkeypress="if (event.keyCode < 48 || event.keyCode >57) {event.returnValue = false;}" 
            value=1 name=ZteWebPager1_PageIndex>&nbsp;<INPUT style="BORDER-BOTTOM: #96adc3 1px solid; FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#FFFFFF,endColorStr=#EAEAE7); BORDER-LEFT: #96adc3 1px solid; WIDTH: 21px; PADDING-RIGHT: 1px; FONT: bold 12px Arial; BACKGROUND: none transparent scroll repeat 0% 0%; HEIGHT: 21px; COLOR: #285a74; BORDER-TOP: #96adc3 1px solid; CURSOR: hand; BORDER-RIGHT: #96adc3 1px solid; PADDING-TOP: 2px" id=ZteWebPager1 class=stbtm value=Go type=submit name=ZteWebPager1>&nbsp;<IMG 
            border=0 align=baseline src="MyProposerIndex_files/next.gif"><SPAN 
            style="WIDTH: 5px"></SPAN><IMG border=0 align=baseline 
            src="MyProposerIndex_files/last.gif"><SPAN 
          style="WIDTH: 5px"></SPAN></TD></TR></TBODY></TABLE></DIV></TD></TR></TBODY></TABLE><INPUT 
id=hiResourceID value=1511 type=hidden name=hiResourceID><INPUT id=hiMenuPath 
type=hidden name=hiMenuPath> <INPUT id=hiCurrentShortNum value=10144183 
type=hidden name=hiCurrentShortNum>
<SCRIPT language=javascript>
<!--
function ExpandDiv(imgObject)
{
if(imgObject.data == "1")
{
	imgObject.data = "0";
	imgObject.src = "../../Common/Images/icon_expandall.gif";
}
else
{
	imgObject.data = "1";
	imgObject.src = "../../Common/Images/icon_collapseall.gif";
}
if(document.all["divSearch"] != null)
	document.all["divSearch"].style.display = (imgObject.data == "0")?"none":"block";
}
	 //-->
</SCRIPT>

<SCRIPT language=javascript>
  new FormValidator('Form1',[
  {
     name:'txtCurrencyName',
     display:'币种名称',
     rules:'required'
  },
  {
     name:'txtCurrencyID',
     display:'币种ID',
     rules:'required|numeric'
  }
  ],function(errors,event)
    	  {
    	  	var errorString = "";
    		  for(var i = 0; i < errors.length; i++)
    		  {
    		  	 errorString += errors[i].message + "\n";
    		  }
    		  if(errorString != "")
    		  {
    		  alert(errorString);
    		  }
    	  }
  ); 

</SCRIPT>
 </BODY></HTML>
