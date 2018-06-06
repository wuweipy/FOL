<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<!-- saved from url=(0104)http://fol.zte.com.cn/Boe/Search/MyProposerIndex.aspx -->
<%@ page language="java" import="java.util.*"%>
<HTML><HEAD><TITLE>ϒʪȫµĵ¥¾ۼ/TITLE>
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
				document.all("txtStartBoeDate").value="";
				document.all("txtEndBoeDate").value="";
				document.all("txtStartBaseBoeAmount").value = "";
				document.all("txtEndBaseBoeAmount").value = "";
				document.all("txtBoeNum").value = "";
				document.all("txtBoeType").value ="";
				document.all("hiBoeTypeId").value ="";
				document.all("hiBankID").value="";
				document.all("txtBankName").value="";
				document.all("hiEmployeeID").value="";
				document.all("txtEmployeeName").value="";
				document.all("dplBoeStatus").value="";		
				document.all("txtBillPractNum").value="";		
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
            //ҳĦӑ¾­ԐԱ¹¤¶̹¤ºţ¬ֱ½Ԋ¹ԃ£¬ûԐ¶̹¤ºŵŐ钪CSאSESSION»򈟍
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
 
                // µ½ǚɕɕǚ¸򊽽»ҩ
                if(dateStart != null && dateEnd != null)
                {
                    if(dateStart.value != "" && dateEnd.value != "")
                    {
                        if(dateStart.value > dateEnd.value )
                        {
                            alert('½⋸ɕǚ²»ŜСԚ¿ªʼɕǚ£¡');
 
                            dateStart.value = "";
                            dateEnd.value = "";
 
                            return false;
                        }
                    }
                } 
                return true;

        }
        
        function CheckInput()
		{
		    if(document.getElementById("txtBankName").value == '')
		    {
		        alert('ӸѐĻ³Ʋ»ŜΪ¿ӧ);
		        document.getElementById("txtBankName").focus();
		        return false;
		    }
		    
		    if(document.getElementById("txtBankID").value == '')
		    {
		    	alert('ӸѐID²»ŜΪ¿ӧ);
		        document.getElementById("txtBankID").focus();
		        return false;
		    }
		    else
		    {
			    // ֽղƥƤӸIDºƎª´¿˽ؖ
			    var str=document.getElementById("txtBankID").value;
			    var reg = /^\d+$/;
			    if (str.match(reg)==null || str.match(reg) =="")
			    {
			        alert('ȫˤɫֽȷµŒ�');
			        document.getElementById("txtBankID").focus();
			        return false;
			    }
		    }
		    return true;
		}
        
        function add()
        {
        	 if(CheckInput() == true)
        	 {
        	     Form1.action = "bank.do?action=add";
        	     Form1.submit();
        	 }
        }
        
        <% if(request.getAttribute("isDelSuccess") != null)
           {
               if((Boolean)request.getAttribute("isDelSuccess")) 
               { 
        %>
         	       alert("ɾ³�);
             <%} 
               else 
               {
             %> 
         	       alert("ɾ³�);
             <%}
           }
             %>
        <% if(request.getAttribute("isAddSuccess") != null)
           {
               if((Boolean)request.getAttribute("isAddSuccess")) 
               { 
        %>
         	       alert("ͭ¼ӳɹ¦");
             <%} 
               else 
               {
             %> 
         	       alert("ͭ¼Ԋ§°ڢ);
             <%}
           }
             %> 
    </SCRIPT>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 action=bank.do>
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
      <SPAN id=TableHeadBar1_lblHead>µ±ǰλ׃<SPAN class=11air>:</SPAN>ԫϒԐ¹ּSPAN 
      class=arrow_subtitle>&gt;</SPAN>ϒµĵ¥¾ۼSPAN 
      class=arrow_subtitle>&gt;</SPAN>ϒʪȫµ¼/SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyProposerIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV style="WIDTH: 98%" id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD class=title height=24><SPAN id=lblBankName>ӸѐĻ³ļ/SPAN></TD>
    <TD><INPUT style="WIDTH: 172px; HEIGHT: 21px" id=txtBankName 
    name=txtBankName></TD>
    <TD class=title><SPAN id=lblBankID>ӸѐID</SPAN></TD>
    <TD><INPUT style="WIDTH: 128px" id=txtBankID size=14 
      name=txtBankID></TD>
    </TR>
  <TR>
    <TD class=bottom colSpan=6 align=middle>
    <INPUT id=hiBankID size=1 type=hidden name=hiBankID>
      <INPUT id=hiBankID size=1 type=hidden  name=hiBankID>&nbsp; 
      <INPUT id=btnQuery class=stbtm01 value=ͭ¼Ѡonclick="add()" type=submit name=btnQuery>
      <INPUT id=hiEmployeeID size=1 type=hidden name=hiEmployeeID>
      <INPUT id=hiProdProjectID size=1 type=hidden name=hiProdProjectID> 
      <INPUT id=btnClear language=javascript class=stbtm01 onclick="javascript:return clear_text();" value=ȥ¿Ӡtype=submit name=btnClear>
      <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture value=zh-CN size=1 type=hidden name=hiCulture> 
      </TD></TR></TBODY></TABLE></DIV>
</FORM>
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="98%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="MyProposerIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>ʳƺ±뺯SPAN> </TD></TR></TBODY></TABLE>
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
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl0','')">ӸѐĻ³ļ/A></TD>
          <TD style="WIDTH: 33%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">ӸѐID</A></TD>
          <TD noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl9','')"></A></TD></TR>
         <%
            HashMap<Integer, String> banks = (HashMap<Integer, String>)request.getAttribute("bankInfo");
  	        Iterator<Integer> iterator = banks.keySet().iterator();
            while(iterator.hasNext())
            {
            int key = iterator.next();
         %>
         
        <TR class=item>
          <TD noWrap align=left>
          	<%= banks.get(key)%>
          </TD>
          <TD noWrap align=left><SPAN 
            id=dgApproval__ctl2_lbl_DeptName><%= key%></SPAN> </TD>
          <TD noWrap style="text-align: center"><!--  <a href="#">ў¸¼/a>&nbsp;&nbsp;&nbsp; --><a href="bank.do?action=delete&&id=<%=key%>">ɾ³�
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
          <TD noWrap align=right>µؼSPAN 
            name="ZteWebPager1_ci">1</SPAN>ҳ/¹²<SPAN 
            name="ZteWebPager1_pc">1</SPAN>ҳ&nbsp;&nbsp;¹²<SPAN 
            name="ZteWebPager1_rc">1</SPAN>͵¼Ȃ¼&nbsp;&nbsp;ÿҳ<SELECT 
            style="TEXT-ALIGN: left; IME-MODE: disabled; WIDTH: 56px" 
            id=ZteWebPager1_PageSize 
            onchange="document.all['ZteWebPager1'][1].click();" 
            name=ZteWebPager1_PageSize value="10"> <OPTION selected 
              value=10>10</OPTION><OPTION value=50>50</OPTION><OPTION 
              value=100>100</OPTION><OPTION value=500>500</OPTION><OPTION 
              value=1000>1000</OPTION><OPTION 
            value=5000>5000</OPTION></SELECT>ѐ&nbsp;<IMG border=0 align=baseline 
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
 </BODY></HTML>
