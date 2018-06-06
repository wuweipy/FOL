<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*,Business.Budget.*"%>
<HTML><HEAD><TITLE>所长审批</TITLE>
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
	
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算审批<SPAN 
      class=arrow_subtitle>&gt;</SPAN>所长审批</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="CountersignIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">

      <%  
          String budgetId = (String)request.getAttribute("budgetId"); 
          String name = (String)request.getAttribute("name"); 
          String dept = (String)request.getAttribute("dept"); 
          String submitDate = ((String)request.getAttribute("submitDate")).substring(0,16);    	
  	  %>
  	  
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="100%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left>单号：<%=budgetId%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp月份：<%=budgetId.substring(0,4)%>年<%=budgetId.substring(4,6)%>月&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp提单人：<%=name%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp所属项目：<%=dept%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp提交时间：<%=submitDate%></TD>   
      <input style="WIDTH: 650px; HEIGHT: 20px" type="hidden" id=tidanren name=tidanren value=<%= name%>>
      </TR></TBODY></TABLE>
      
      	
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
            <%  
          List<BudgetDetailInfo> budgetDetailInfos = (List<BudgetDetailInfo>)request.getAttribute("budgetDetailInfos");
          ArrayList<Integer> subProIds = (ArrayList<Integer>)request.getAttribute("subProIds");
          HashMap<Integer, String> subProducts = (HashMap<Integer, String>)request.getAttribute("subProducts");
  	  %>
        <TR id=tr_Head class=head>
          <TD noWrap style="WIDTH: 15%" align=left>科目</TD>
          <TD noWrap style="WIDTH: 15%" align=left>总金额</TD>
          <%             
             for(int i=0;i<subProIds.size();i++)
             {            
                    %>   
          <TD noWrap style="WIDTH: 15%" align=left><%= subProducts.get(subProIds.get(i))%></TD>                                      
           <%   
             }
          %>          
          <TD noWrap style="WIDTH: 20%" align=left>详细说明</TD>
          <TD noWrap style="WIDTH: 40%" align=left>所长审批意见</TD>
       </TR>              
          
          <script>                                                     
             function permit()
             {                          
                    if(confirm("您确认要提交审批吗?"))
                    {
                      var itemNum = document.getElementById("count").value;
                      var varStr = "action=submit&agg=0&count=" + itemNum + "&budgetId=" + document.getElementById("newbudgetId").value;
                      for(var i=0; i < itemNum; i++ )
                      {
                        varStr += "&courseName"+i+"=";
                        varStr += document.getElementById("courseName"+i).innerText;
                        varStr += "&instituteOpinion"+i+"=";
                        varStr += document.getElementById("instituteOpinion"+i).value;
                      }
                      Form1.action = "budgetDetail.do?" + varStr;
                      Form1.submit();
                    }                                         
             }
             
             function notPermit()
             {                          
                    if(confirm("您确认要提交审批吗?"))
                    {
                      var itemNum = document.getElementById("count").value;
                      var varStr = "action=submit&agg=1&count=" + itemNum + "&budgetId=" + document.getElementById("newbudgetId").value;
                      for(var i=0; i < itemNum; i++ )
                      {
                        varStr += "&courseName"+i+"=";
                        varStr += document.getElementById("courseName"+i).innerText;
                        varStr += "&instituteOpinion"+i+"=";
                        varStr += document.getElementById("instituteOpinion"+i).value;
                      }
                      Form1.action = "budgetDetail.do?" + varStr;
                      Form1.submit();
                    }                                         
             }
          </script>          
       
          <%  
          int i = 0; 
  	      for(i = 0; i < budgetDetailInfos.size(); i++) 
  	      { 	   	
  	  %>
  	    <TR  class=item>
            <TD noWrap align=left id=courseName<%= i%> name=courseName<%= i%>>
          	<%= budgetDetailInfos.get(i).getCourseName()%>
            </TD>
            <TD noWrap align=left>
          	<%= budgetDetailInfos.get(i).getMoney()%>
            </TD>
            <%for(int j=0;j<subProIds.size();j++) {%>
            <TD noWrap align=left>
                <%
	                String money = "0";
	                if(budgetDetailInfos.get(i).getSubmoneys().size()!=0)
	                {
	                    money = budgetDetailInfos.get(i).getSubmoneys().get(j).getName();
	                }
                %>
          	<%= money%>
            </TD>            
            <%}%>            
            <TD Wrap align=left>
          	<%= budgetDetailInfos.get(i).getDescription()%>
            </TD>    
            <TD noWrap align=left>
          	<input style="WIDTH: 650px; HEIGHT: 20px" id=instituteOpinion<%= i%> name=instituteOpinion<%= i%>>
            </TD>                    
         </TR>

         <% }
         %>    
         <input style="WIDTH: 650px; HEIGHT: 20px" type="hidden" id=newbudgetId name=newbudgetId value=<%= budgetId%>>
         <input style="WIDTH: 650px; HEIGHT: 20px" type="hidden" id=count name=count value=<%= i%>>       
       </TBODY></TABLE></DIV>
       
   <script>
     var pager = new Pager("tbNullMsg","tr_Head");    
  </script> 

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TBODY>      
 <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=right><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiDeptID 
      size=0 type=hidden name=hiDeptID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiUserID size=0 type=hidden name=hiUserID> <INPUT 
      style="WIDTH: 50px; HEIGHT: 30px" id=hiEmployeeID size=0 type=hidden 
      name=hiEmployeeID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiProdProjectID size=0 type=hidden name=hiProdProjectID> <INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnQuery  class=stbtm01 onclick="notPermit()" value=退回 type=button name=btnQuery><INPUT 
      style="WIDTH: 50px; HEIGHT: 30px" id=hiClickFlag size=0 type=hidden 
      name=hiClickFlag><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture 
      value=zh-CN size=0 type=hidden 
      name=hiCulture></DIV></TD></TR></TBODY></TABLE>
            
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