<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*"%>
<HTML><HEAD><TITLE>下月预算申请</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="CountersignIndex_files/style.css"> 
<style type="text/css" rel="stylesheet">
a:link{
color:red;
}
a:hover{
color:orangered;
}
a:active{
color:steelblue;
}
a:visited{
color:steelblue;
}
</style>

<LINK rel=stylesheet 
type=text/css href="Pager/Page.css"> 

<SCRIPT language=javascript 
src="CountersignIndex_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="CountersignIndex_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="Pager/Pager.js"></SCRIPT>
<SCRIPT language=javascript 
src="Pager/AllCheck.js"></SCRIPT>


<SCRIPT language=javascript 
src="CountersignIndex_files/AdminPageFunction.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="CountersignIndex_files/Calendar.js"></SCRIPT>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 
action="currentBudget.do">
<input type="hidden" name="ids" id="ids"> 
<SCRIPT type=text/javascript>

</SCRIPT>

  <script>
  <%
	  Object isSucess = request.getAttribute("isSucess");
	  if(isSucess != null)
	   if((Boolean)isSucess)
	  {
  %>
     alert("提交成功,请进入“我申请的”栏进行查看修改!");
  <%	     
	  }
	else
		{
  %>
     alert("提交失败!");
  <%	
		}
	%>
	</script>
	
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算申请<SPAN 
      class=arrow_subtitle>&gt;</SPAN>下月预算申请</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="CountersignIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
	
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="100%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="CountersignIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>科目列表</SPAN> </TD></TR></TBODY></TABLE>
      
<DIV class=autoOver2 align=center>
<TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" id="tbNullMsg" class="datatbl" border="1" rules="all" cellspacing="0">
  <TBODY>

<TR class=head>
          <TD noWrap style="WIDTH: 15%" align=left>科目</TD>
          <TD noWrap style="WIDTH: 15%" align=left>金额</TD>
          <TD noWrap style="WIDTH: 50%" align=left>详细说明</TD>
       </TR>                 
    <%
	  int flag = (Integer)request.getAttribute("flag");
	  if(flag == 1)
	  {
  %>      
  	 <TR id=tr_Head class=item>
            <TD noWrap align=left>
          	您下月的预算已提交，请进入“我申请的”栏进行查看修改！ 
            </TD>                     
         </TR>
   <% }
	  else 
	  {
  %>      
  	 <TR id=tr_Head class=item>
            <TD noWrap align=left>
          	您没有此操作权限！ 
            </TD>                     
         </TR>
   <% } %> 

       </TBODY></TABLE></DIV>
            
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TBODY>
  <TR>
    <TD class=bottom align=right></TD></TR></TBODY></TABLE><INPUT id=hiResourceID 
value=1222 type=hidden name=hiResourceID><INPUT id=hiMenuPath type=hidden 
name=hiMenuPath><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCheckedAll 
value=全选 size=1 type=hidden name=hiCheckedAll>
<SCRIPT language=javascript>
</SCRIPT>
 </FORM></BODY></HTML> 