<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*,View.ParameterUtil,Business.Budget.*"%>
<HTML><HEAD><TITLE>总经理审批</TITLE>
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

<SCRIPT language=javascript>			
			function clear_text()
			{
				document.all("budgetId").value="";
				document.all("userName").value="";
				document.all("department").value="";	
				return false;
			}
			
		   function query()
                        {
        	            Form1.action = "budgetManagerServlet.do?action=query";
        	            Form1.submit();
                        }
        
    </SCRIPT>
</HEAD>

<BODY>
<FORM id=Form1 method=post name=Form1 
action="currentBudget.do">
<INPUT id=budgetIdIN size=1 type=hidden name=budgetIdIN value=<%=request.getParameter("budgetId")%>>
<INPUT id=departmentIN size=1 type=hidden name=departmentIN value=<%=ParameterUtil.getChineseString(request, "department", "utf-8")%>>
<INPUT id=userNameIN size=1 type=hidden name=userNameIN value=<%=ParameterUtil.getChineseString(request, "userName", "utf-8")%>>
<SCRIPT type=text/javascript>

</SCRIPT>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算审批<SPAN 
      class=arrow_subtitle>&gt;</SPAN>总经理审批</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="CountersignIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
  
<DIV id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD colSpan=6><IMG class=icon src="AdminApprovalIndex_files/search.gif"> 
      请填写查询条件 </TD></TR>
  <TR>
  <TR>   
    <TD class=title height=24>
      <DIV align=left noWrap><SPAN id=invoicenoxx>单号</SPAN></DIV></TD>
    <TD><INPUT style="WIDTH: 180px; HEIGHT: 20px" id=budgetId 
    name=budgetId></TD>
    
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxx>提单人</SPAN></DIV></TD>
          <TD><Input style="WIDTH: 180px; HEIGHT: 20px" id=userName 
    name=userName></TD>  

    <TD class=title>
      <DIV align=left noWrap><SPAN id=departmentxx>部门</SPAN></DIV></TD>
    <TD><SELECT size="0" style="WIDTH: 180px" id=department name=department>    
        <OPTION value=""></OPTION>
    <%    
         HashMap<Integer, String> deptInfo = (HashMap<Integer, String>)request.getAttribute("deptInfo");
  	 Iterator<Integer> deptIterator = deptInfo.keySet().iterator();
  	  while(deptIterator.hasNext()) { 
    		   int deptkey = deptIterator.next();
    %>
    <Option value=<%= deptInfo.get(deptkey)%>><%= deptInfo.get(deptkey)%></option>
   <%
     }
   %>    

</SELECT></TD></TR>

  <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=right><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiDeptID 
      size=0 type=hidden name=hiDeptID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiUserID size=0 type=hidden name=hiUserID> <INPUT id=btnQuery class=stbtm01 value=查询  onclick="query()" type=button name=btnQuery><INPUT 
      style="WIDTH: 31px; HEIGHT: 20px" id=hiEmployeeID size=0 type=hidden 
      name=hiEmployeeID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiProdProjectID size=0 type=hidden name=hiProdProjectID> <INPUT id=btnClear language=javascript class=stbtm01 onclick="javascript:return clear_text();" value=清空 type=button name=btnClear><INPUT 
      style="WIDTH: 32px; HEIGHT: 20px" id=hiClickFlag size=0 type=hidden 
      name=hiClickFlag><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture 
      value=zh-CN size=0 type=hidden 
name=hiCulture></DIV></TD>

</TR></TBODY></TABLE></DIV>  
	
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="100%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="CountersignIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>审批列表</SPAN> </TD></TR></TBODY></TABLE>
      
<DIV class=autoOver2 align=center>
<TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" id="tbNullMsg" class="datatbl" border="1" rules="all" cellspacing="0">
  <TBODY>
        <TR id=tr_Head class=head>
          <TD noWrap style="WIDTH: 12%" align=left>单号</TD>
          <TD noWrap style="WIDTH: 12%" align=left>月份</TD>
          <TD noWrap style="WIDTH: 12%" align=left>提单人</TD>
          <TD noWrap style="WIDTH: 12%" align=left>委托人</TD>          
          <TD noWrap style="WIDTH: 12%" align=left>所属项目</TD>
          <TD noWrap style="WIDTH: 12%" align=left>预算总额</TD>
          <TD noWrap style="WIDTH: 12%" align=left>状态</TD>
          <TD noWrap style="WIDTH: 18%" align=left>提交日期</TD>
       </TR>                                
       
          <%  
          List<BudgetInfo> budgetInfos = (List<BudgetInfo>)request.getAttribute("budgetInfos"); 
          if(!budgetInfos.isEmpty())     {
  	      for(int i = 0; i < budgetInfos.size(); i++) 
  	      {
  	   	
  	  %>
  	    <TR class=item>
            <TD noWrap align=left>
          	<a href="budgetManagerDetailServlet.do?budgetId=<%= budgetInfos.get(i).getBudgetId()%>&name=<%= budgetInfos.get(i).getName() + budgetInfos.get(i).getNo()%>&dept=<%= budgetInfos.get(i).getDept()%>&submitDate=<%= budgetInfos.get(i).getSubmitDate()%>">
          	<%= budgetInfos.get(i).getBudgetId()%></a>                     	
            </TD>
							<TD noWrap align=left>
								<%
									String year = budgetInfos.get(i).getBudgetId().substring(0,4);
									String month = budgetInfos.get(i).getBudgetId().substring(4,6);									
								%>
								<%=year%>年<%=month%>月
							</TD>            
            <TD noWrap align=left>
            <%= budgetInfos.get(i).getName() + budgetInfos.get(i).getNo()%>
            </TD>
            <TD noWrap align=left>
            <%= budgetInfos.get(i).getAgencyName() + budgetInfos.get(i).getAgencyNo()%>
            </TD>               
            <TD noWrap align=left>
            <%= budgetInfos.get(i).getDept()%>
            </TD>
            <TD noWrap align=left>
          	<%= budgetInfos.get(i).getTotalMoney()%>
            </TD>
            <TD noWrap align=left>
            待总经理审批
            </TD>
            <TD noWrap align=left>
            <%= budgetInfos.get(i).getSubmitDate().toString().substring(0,16)%>
            </TD>                           
         </TR>
         <% }}
          else{%>         
         <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>对不起，没有符合条件的记录! </FONT></TD>
         </TR>
        <% }
         %>  

       </TBODY></TABLE></DIV>
       
  <script>
     var pager = new Pager("tbNullMsg","tr_Head");    
  </script> 
<SCRIPT language=javascript>

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

function init()
{
   var budgetIdIN = document.getElementById("budgetIdIN").value;
   if(budgetIdIN == -1 || budgetIdIN == "" || budgetIdIN == "null")
   {
       document.getElementById("budgetId").value = "";
   }
   else
   {
       document.getElementById("budgetId").value = budgetIdIN;
   }  
   
   var departmentIN = document.getElementById("departmentIN").value;
   if(departmentIN == -1 || departmentIN == "" || departmentIN == "null")
   {
       document.getElementById("department").value = "";
   }
   else
   {
       document.getElementById("department").value = departmentIN;
   }   

   var userNameIN = document.getElementById("userNameIN").value;
   if(userNameIN == -1 || userNameIN == "" || userNameIN == "null")
   {
       document.getElementById("userName").value = "";
   }
   else
   {
       document.getElementById("userName").value = userNameIN;
   }   
 }
 
//页面初始化
init();

</SCRIPT>   
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