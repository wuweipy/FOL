<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<!-- saved from url=(0104)http://fol.zte.com.cn/Boe/Search/MyProposerIndex.aspx -->
<%@ page language="java" import="java.util.*,Data.UserInfo.*"%>
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
			document.all("txtWorkNum").value="";
			document.all("txtUserName").value="";	
			document.all("txtPassword").value="";
			document.all("deptID").value="";	
			document.all("roleID").value="";		
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
	        	Form1.action = "userMngt.do?action=add";
	        	Form1.submit();
			}
        }
        
        function check()
        {
        	if(document.getElementById("txtWorkNum").value == "")
        	{
        		alert("请输入员工工号");
        		document.getElementById("txtWorkNum").focus();
        		return false;
        	}
        	if(document.getElementById("txtUserName").value == "")
        	{
        		alert("请输入员工姓名");
        		document.getElementById("txtUserName").focus();
        		return false;
        	}
        	if(document.getElementById("txtPassword").value == "")
        	{
        		alert("请输入初始密码");
        		document.getElementById("txtPassword").focus();
        		return false;
        	}
        	if(document.getElementById("deptID").value == "")
        	{
        		alert("请选择所属科室");
        		document.getElementById("deptID").focus();
        		return false;
        	}
        	if(document.getElementById("roleID").value == "")
        	{
        		alert("请选择所属角色");
        		document.getElementById("roleID").focus();
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
<FORM id=Form1 method=post name=Form1 action=userMngt.do>
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
      class=arrow_subtitle>&gt;</SPAN>用户管理 </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyProposerIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV style="WIDTH: 98%" id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD class=title nowrap="" height=24>工号<font color="#ff0000">*</font> </TD>
    <TD><INPUT style="WIDTH: 170px; HEIGHT: 21px" id=txtWorkNum 
    name=txtWorkNum></TD>
    <TD class=title nowrap="" height=24>姓名<font color="#ff0000">*</font> </TD>
    <TD><INPUT style="WIDTH: 170px; HEIGHT: 21px" id=txtUserName 
    name=txtUserName></TD>
    <TD class=title nowrap="" height=24>密码<font color="#ff0000">*</font> </TD>
    <TD><INPUT style="WIDTH: 170px" id=txtPassword size=14 
      name=txtPassword></TD>
  </TR>
  <TR>
    <TD class=title nowrap="" height=24>科室<font color="#ff0000">*</font> </TD>
    <TD><select id="deptID" name="deptID" style="WIDTH: 170px">
    <%    
        HashMap<Integer, String> deptInfoMap = (HashMap<Integer, String>)request.getAttribute("DeptInfos");
        Iterator<Integer> deptIterator = deptInfoMap.keySet().iterator();
  	    while(deptIterator.hasNext()) { 
    		   int key = deptIterator.next();
    %>
    <option selected="" value="<%= key %>"><%= deptInfoMap.get(key)%></option>
   <%
     }
   %>
    <option selected="selected" value=""></option>
    </select></TD>
    <TD class=title nowrap="" height=24>角色<font color="#ff0000">*</font> </TD>
    <TD><select id="roleID" name="roleID" style="WIDTH: 170px">
    <%    
    	HashMap<Integer, String> roleMap = (HashMap<Integer, String>)request.getAttribute("RoleInfos");
        Iterator<Integer> roleIterator = roleMap.keySet().iterator();
        while(roleIterator.hasNext()){
            int key = roleIterator.next();
    %>
    <option selected="" value="<%= key %>"><%= roleMap.get(key)%></option>
   <%
        }
   %>
	<option selected="selected" value=""></option>
    </select></TD>
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
      id=lblApprovalList>用户列表</SPAN> </TD></TR></TBODY></TABLE>
<DIV style="WIDTH: 98%" class=autoOver2 align=center>
      <TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval class=datatbl 
      border=1 rules=all cellSpacing=0>
        <TBODY>
        <TR class=head id=tr_Head>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">工号</A></TD>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">用户名</A></TD>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">科室</A></TD>
          <TD style="WIDTH: 20%" noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">角色</A></TD>
          <TD noWrap align=left></TD></TR>

      <%    
          ArrayList<DUser> userList = (ArrayList<DUser>)request.getAttribute("UserInfos");
          for(int i = 0; i < userList.size(); i++){
      		   DUser userInfo = userList.get(i);
       %>
        <TR class=item >
          <TD noWrap align=left>
          	<%= userInfo.getNo()%>
          </TD>
          <TD noWrap align=left>
          	<%= userInfo.getName()%>
          </TD>
          <TD noWrap align=left>
          	<%= deptInfoMap.get(userInfo.getDeptId())%>
          </TD>
          <TD noWrap align=left>
          	<%= roleMap.get(userInfo.getRoleId())%>
          </TD>
          <TD noWrap style="text-align: center"><a href="userMngt.do?action=delete&id=<%= userInfo.getNo()%>">删除</a>
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
