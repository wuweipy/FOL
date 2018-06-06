<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<!-- saved from url=(0104)http://fol.zte.com.cn/Boe/Search/MyProposerIndex.aspx -->
<%@ page language="java" import="java.util.*,Data.Project.*,Data.UserInfo.*"%>
<HTML><HEAD><TITLE>我申请的单据</TITLE>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="MyProposerIndex_files/style.css">
<SCRIPT language=javascript src="MyProposerIndex_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="MyProposerIndex_files/FbpLovSelect.js"></SCRIPT>

<LINK rel=stylesheet 
type=text/css href="Pager/Page.css"> 

<SCRIPT language=javascript 
src="Pager/Pager.js"></SCRIPT>

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

		function clear_text()
		{
			document.all("txtWorkNo").value="";
			document.all("txtProjectId").value="";	
			document.all("txtProjectName").value="";	
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

        function add()
        {
        	if(check())
			{
	        	Form1.action = "projectMngt.do?action=add";
	        	Form1.submit();
			}
        }
        
        function check()
        {
        	if(document.getElementById("txtWorkNo").value == "")
        	{
        		alert("请输入项目经理工号");
        		document.getElementById("txtWorkNo").focus();
        		return false;
        	}
        	if(document.getElementById("txtProjectId").value == "")
        	{
        		alert("请输入项目ID");
        		document.getElementById("txtProjectId").focus();
        		return false;
        	}
        	if(document.getElementById("txtProjectName").value == "")
        	{
        		alert("请输入项目名字");
        		document.getElementById("txtProjectName").focus();
        		return false;
        	}
        	return true;   
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
<FORM id=Form1 method=post name=Form1 action=projectMngt.do>
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
      class=arrow_subtitle>&gt;</SPAN>项目管理 </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyProposerIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV style="WIDTH: 98%" id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD class=title nowrap="" height=24>项目ID<font color="#ff0000">*</font> </TD>
    <TD><INPUT style="WIDTH: 170px; HEIGHT: 21px" id=txtProjectId 
    name=txtProjectId></TD>
    <TD class=title nowrap="" height=24>项目名<font color="#ff0000">*</font> </TD>
    <TD><INPUT style="WIDTH: 170px; HEIGHT: 21px" id=txtProjectName 
    name=txtProjectName></TD>
    <TD class=title nowrap="" height=24>项目经理工号<font color="#ff0000">*</font> </TD>
    <TD><INPUT style="WIDTH: 170px" id=txtWorkNo size=14 
      name=txtWorkNo></TD>
  </TR>
  <TR>
    <TD class=bottom colSpan=6 align=middle><INPUT id=hiDeptID size=1 
      type=hidden name=hiDeptID><INPUT id=hiUserID size=1 type=hidden 
      name=hiUserID>&nbsp; <INPUT id=btnQuery class=stbtm01 value=添加 onclick="add()" type=button name=btnQuery><INPUT 
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
      id=lblApprovalList>项目列表</SPAN> </TD></TR></TBODY></TABLE>
<DIV style="WIDTH: 98%" class=autoOver2 align=center>
      <TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval class=datatbl 
      border=1 rules=all cellSpacing=0>
        <TBODY>
        <TR class=head id=tr_Head>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">项目ID</A></TD>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">项目名</A></TD>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">项目经理工号</A></TD>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">项目经理名字</A></TD>
          <TD noWrap align=left></TD></TR>

      <%  
      	  HashMap<String, DUser> UserInfoMap = (HashMap<String, DUser>)request.getAttribute("UserInfoMap");
          ArrayList<DProject> projectList = (ArrayList<DProject>)request.getAttribute("ProjectsInfo");
          for(int i = 0; i < projectList.size(); i++){
      		   DProject projectInfo = projectList.get(i);
       %>
        <TR class=item >
          <TD noWrap align=left>
          	<%= projectInfo.getProjectid()%>
          </TD>
          <TD noWrap align=left>
          	<%= projectInfo.getProjectname()%>
          </TD>
          <TD noWrap align=left>
          	<%= projectInfo.getOwnerno()%>
          </TD>
          <TD noWrap align=left>
          	<%= (UserInfoMap.get(projectInfo.getOwnerno())).getName()%>
          </TD>
          <TD noWrap style="text-align: center"><a href="projectMngt.do?action=delete&projectId=<%= projectInfo.getProjectid()%>">删除</a>
            <INPUT id=dgApproval__ctl2_hiEmployeeIDRow value=244680 type=hidden 
            name=dgApproval:_ctl2:hiEmployeeIDRow> </TD>
         </TR>
         <%
           }
         %></TBODY></TABLE></DIV>
  <script> 
     var pager = new Pager("dgApproval","tr_Head");    
  </script> 
 </BODY></HTML>
