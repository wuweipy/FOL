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
<DIV  id=divSearch>
        <TR class=head>
          <TD noWrap style="WIDTH: 15%" align=left>注：可在"我的信息—项目管理"中进行子项目的管理。请于当月20号前提交下月预算申请，否则会导致下月报销无法审批</TD>
       </TR> 	
  </DIV>     
  </br>
<DIV  id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxxxxx>申请人</SPAN></DIV></TD> 
    <TD>        
    <SELECT size="0" style="WIDTH: 180px" id=userNo name=userNo>
	<%    
        HashMap<String, DUser> userInfoMap = (HashMap<String, DUser>)request.getAttribute("userInfoMap");
        ArrayList<String> budgetAllNos = (ArrayList<String>)request.getAttribute("budgetAllNos");
          ArrayList<Integer> subProIds = (ArrayList<Integer>)request.getAttribute("subProIds");
          HashMap<Integer,String> subProducts = (HashMap<Integer,String>)request.getAttribute("subProducts");	
          String realDeptName = (String)request.getAttribute("realDeptName");
        for(int i=0;i<budgetAllNos.size();i++){
    %>
	<option value="<%= budgetAllNos.get(i) %>"><%= userInfoMap.get(budgetAllNos.get(i)).getName()%><%= budgetAllNos.get(i) %></option>
	<%     }   %>   
	<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum11 size=1 type=hidden name=itemNum11 value=<%=budgetAllNos.size()%>>
</SELECT></TD>
						<TD style="HEIGHT: 24px" class="title">
							<DIV align="left noWrap">
								<SPAN id="budgetmonth">项目名</SPAN>
							</DIV></TD>
						<TD>
      <INPUT readonly style="WIDTH: 150px; HEIGHT: 20px" id="mainitem" type="text" name="mainitem" value=<%=realDeptName%>>
    <TD>    
	 <script>
	 <%
	    String realID = (String)request.getAttribute("realID");
	    if(!realID.equals(""))
	    {
	 %>        document.getElementById("userNo").value ="<%= realID%>";    
        <%}%>
   </script>   
    </TR>
 </TBODY></TABLE></DIV>  
  

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
        <TR class=head id=tr_Head>
          <TD noWrap  align=left>科目</TD>
          <TD noWrap  align=left>总金额</TD>
			  	<%            
             for(int i=0;i<subProIds.size();i++)
             {            
                    %>   
          <TD noWrap align=left><%= subProducts.get(subProIds.get(i))%></TD>                                      
           <%   
             }
          %>          
          <TD noWrap align=left>详细说明</TD>
       </TR>              
          
          <script>                                                     
             function permit()
             {                          
                    if(confirm("您确认要提交吗?"))
                    {
                      var itemNum = document.getElementById("count").value;
                      var varStr = "action=submit&count=" + itemNum;
                      Form1.action = "currentBudget.do?" + varStr;
                      Form1.submit();
                    }                                         
             }
          </script>          
          
          <script>
function OnInput(index,count){
var s = 0;
for(var i=0;i<count;i++)
{
	 document.getElementById("submoney"+index+i).value = document.getElementById("submoney"+index+i).value.replace(/[^(\d)]/g,'');
   s = s + document.getElementById("submoney"+index+i).value/1;
}
document.getElementById("money"+index).value=s;
}

function onPropertychange(index,count){
var s = 0;
for(var i=0;i<count;i++)
{
   document.getElementById("submoney"+index+i).value = document.getElementById("submoney"+index+i).value.replace(/[^(\d)]/g,'');
   s = s + document.getElementById("submoney"+index+i).value/1;
}
document.getElementById("money"+index).value=s;
}

		document.getElementById("userNo").onchange = function() {
			var realUserno = document.getElementById("userNo").value;
			var varStr = "action=refresh&realUserno="+realUserno;
      Form1.action = "currentBudget.do?" + varStr;
      Form1.submit();
		}
		
</script>


       
          <%  
          HashMap<Integer, String> courseMap = (HashMap<Integer, String>)request.getAttribute("courseMap");      
  	  Iterator<Integer> iterator = courseMap.keySet().iterator(); 
  	  int i = 0;
  	    while(iterator.hasNext()) 	   
  	   { 
  	   	int key = iterator.next();
  	   	
  	  %>
  	    <TR id=tr_Head class=item>
            <TD noWrap align=left>
          	<%= courseMap.get(key)%>
          	<input  id=courseName<%= i%> name=courseName<%= i%> value=<%= courseMap.get(key)%> type="hidden" style="border: none; width: 200px" >
            </TD>
							<%   if(subProIds.size()==0) {%>
							<TD noWrap align=left>
								<input type="text" id=money<%= i%> name=money<%= i%>  onkeyup="value=value.replace(/[^(\d)]/g,'')" />
							</TD>
							<% } 
							else{ %>
							<TD noWrap align=left>
								<input readonly type="text" id=money<%= i%> name=money<%= i%> />
							</TD>																																			
	<%  }            
					 for(int j=0;j<subProIds.size();j++) {%>
            <TD noWrap align=left>
          	<input type="text" id="submoney<%=i%><%=j%>" name="submoney<%=i%><%=j%>"  oninput="OnInput(<%=i%>,<%=subProIds.size()%>)" onchange="onPropertychange(<%=i%>,<%=subProIds.size()%>)" />
            </TD>            
            <%}%>   	            
            <TD noWrap align=left>
          	<Textarea id=description<%= i%> name=description<%= i%> wrap="soft"></Textarea>
            </TD>                        
         </TR>
         <% i++; }
         %>  
         <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=count size=1 type=hidden name=count value=<%= i%>> 
       </TBODY></TABLE>       
       </DIV>
  <script>
  
     var pager = new Pager("tbNullMsg","tr_Head");    
  </script>         

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TBODY>      
 <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=right><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiDeptID 
      size=0 type=hidden name=hiDeptID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiUserID size=0 type=hidden name=hiUserID> <INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnQuery class=stbtm01 value=提交  onclick="permit()" type=button name=btnQuery><INPUT 
      style="WIDTH: 50px; HEIGHT: 30px" id=hiEmployeeID size=0 type=hidden 
      name=hiEmployeeID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiProdProjectID size=0 type=hidden name=hiProdProjectID> </DIV></TD></TR></TBODY></TABLE>
            
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