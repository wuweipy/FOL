﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0104)http://fol.zte.com.cn/Boe/Search/MyProposerIndex.aspx -->
<%@page import="Data.Common.Entry"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Business.Budget.DelegateInfo,Data.UserInfo.DUser"%>
<HTML><HEAD><TITLE>子项目功能</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="CountersignIndex_files/style.css"> 

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

<SCRIPT language=javascript src="validate.js"></SCRIPT>

<SCRIPT language=javascript>

        function add()
        {
        	 Form1.action = "ProductServlet.do?action=add";
        	 //Form1.submit();
        }
        
         <% if(request.getAttribute("noAuth") != null) {%> 
           alert("没有操作权限"); 
         <%}%>
        
         <% if(request.getAttribute("AddisSuccess") != null)
         {
          if((Boolean)request.getAttribute("AddisSuccess")) { %>
         	 alert("添加成功");
         	 location = "./ProductServlet.do";
         <%} else {%> 
         	 alert("添加失败");
         	 location = "./ProductServlet.do";
        <%  }
         }
        %>  
         
         <% if(request.getAttribute("DelisSuccess") != null)
         {
          if((Boolean)request.getAttribute("DelisSuccess")) { %>
         	 alert("删除成功");
         	 location = "./ProductServlet.do";
         <%} else {%> 
         	alert("删除失败");
         	location = "./ProductServlet.do";
        <%  }
         }
        %>  
        
         <% if(request.getAttribute("ModifySuccess") != null)
         {
          if((Boolean)request.getAttribute("ModifySuccess")) { %>
         	 alert("修改成功");
         	 location = "./ProductServlet.do";
         <%} else {%> 
         	 alert("修改失败");
         	 location = "./ProductServlet.do";
        <%  }
         }
        %> 
        
        
    </SCRIPT>
</HEAD>

<BODY>
<FORM id=Form1 method=post name=Form1 action=ProductServlet.do>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="MyProposerIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN 
      class=arrow_subtitle>&gt;</SPAN>我的信息<SPAN 
      class=arrow_subtitle>&gt;</SPAN>项目管理</TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyProposerIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
  
  <%   
    int flag = (Integer)request.getAttribute("flag");
    if(flag==1) {
        String deptName = (String)request.getAttribute("deptName");
    	int deptId = (Integer)request.getAttribute("deptId");
%>
<DIV  id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxx>项目名称</SPAN></DIV></TD>
    <TD>        
    <SELECT size="0" style="WIDTH: 180px" id=deptId name=deptId>
    <%
        Integer projectId = (Integer)request.getAttribute("projectId");
    	String projectName = (String)request.getAttribute("projectName");
    %>
	<option selected="" value="<%= projectId %>"><%= projectName%></option>
</SELECT></TD>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxxxxx>子项目名称</SPAN></DIV></TD> 
    <TD>        
    <INPUT id=subProductName style="WIDTH: 180px" size=1  name=subProductName>
</TD>
    </TR>
  <TR>
    <TD class=bottom colSpan=6 align=middle><INPUT id=hiDeptID size=1 
      type=hidden name=hiDeptID><INPUT id=hiUserID size=1 type=hidden 
      name=hiUserID>&nbsp; <INPUT id=btnQuery class=stbtm01 value=添加 onclick="add()" type=submit name=btnQuery><INPUT 
      id=hiEmployeeID size=1 type=hidden name=hiEmployeeID><INPUT 
      id=hiProdProjectID size=1 type=hidden name=hiProdProjectID><INPUT 
      style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture value=zh-CN size=1 
      type=hidden name=hiCulture> </TD></TR></TBODY></TABLE></DIV>
</FORM>
<script>
        function modify(key)
        {
        	 Form2.action = "ProductServlet.do?action=modify&subProductId=" + key;
        	 Form2.submit();
        }
</script>
<FORM id=Form2 method=post name=Form2 action=ProductServlet.do>
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
          <TD  noWrap style="WIDTH: 35%" align=left>项目名称</TD>
          <TD  noWrap style="WIDTH: 35%" align=left>子项目名称</TD>
          <TD  noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl9','')"></A></TD></TR>
         <%
            HashMap<Integer, String> subProducts = (HashMap<Integer,String>)request.getAttribute("subProducts");
            Iterator<Map.Entry<Integer, String>> it = subProducts.entrySet().iterator();
            while (it.hasNext()) {
            	Map.Entry<Integer, String> subProject = it.next();
         %>
         
        <TR class=item>
          <TD noWrap align=left>
          	<%= projectName%>
          </TD>
          <TD noWrap align=left><input style="WIDTH: 180px" type="text" id=subName<%= subProject.getKey()%> name=subName<%= subProject.getKey()%> value=<%= subProject.getValue()%>></TD>
          <TD noWrap style="text-align: center"><a href="#" onclick="modify(<%= subProject.getKey()%>)">修改</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
          <a href="ProductServlet.do?action=delete&subProductId=<%= subProject.getKey()%>">删除</a>
            <INPUT id=dgApproval__ctl2_hiEmployeeIDRow value=244680 type=hidden 
            name=dgApproval:_ctl2:hiEmployeeIDRow> </TD>
         </TR>
         <%
           }           if(subProducts.size()==0){
         %>
        <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>对不起，没有符合条件的记录! </FONT></TD>
       </TR>
       <%  }     %>    
         
         </TBODY></TABLE></DIV>
  <script> 
     var pager = new Pager("dgApproval","tr_Head");    
  </script>
  <% }
  else {
    %>
    <TABLE>您没有此操作权限！</TABLE>
    
  <%
  }
  %>
  </FORM>
 </BODY></HTML>
