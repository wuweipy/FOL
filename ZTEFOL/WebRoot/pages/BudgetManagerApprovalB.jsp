<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*,Business.Budget.*"%>
<HTML><HEAD><TITLE>预算跟踪表</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="CountersignIndex_files/style.css"> 

<LINK rel=stylesheet 
type=text/css href="Pager/Page.css"> 

<LINK rel=stylesheet type=text/css href="css/pikaday.css">

<SCRIPT type=text/javascript src="pikaday.js"></SCRIPT>

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

  <script>
  <%
	  Object isSucess = request.getAttribute("flag");
	  if(isSucess != null)
	   if((Boolean)isSucess)
	  {
  %>
     alert("预算审批成功!");
  <%	     
	  }
	else
		{
  %>
     alert("预算审批失败!");
  <%	
		}
	%>
	</script>

<SCRIPT language=javascript>		
  function query()
      {
	     Form1.action = "budgetManagerServletB.do?action=query";
	     Form1.submit();
      }
        
    function permit()
        {
       		 if(confirm("您确认要提交审批吗?")){
	     			Form1.action = "budgetManagerServletB.do?action=permit";
	     			Form1.submit();
	    		 }
        }       
    </SCRIPT>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 
action="budgetManagerServletB.do">
<input type="hidden" name="ids" id="ids"> 
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
  
<DIV style="WIDTH: 98%" id=divSearch>
			<%
	    Object role = request.getAttribute("role");
	    if(role!=null && (Integer)role == 5){
	 %>
				<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
					cellPadding=0 width="100%" align=center>
					
					<tbody>
						<tr>
							<td colspan="4">
								<img class="icon" src="images/search.gif">
								请填写查询条件
							</td>
						</tr>
						<tr>							
							<td class="title" width=70 align="center">
								年月选择
							</td>
							<td colspan="1" width=900>
									<select id="year" name="year" width=100>
										<option value="">
										</option>
										<%for(int i=2014; i<2025; i++) {%>
										<option value=<%= i%>>
										<%= i%>
										</option>
										<%}%>
									</select>
										<span>年</span>
									<select id="month" name="month" width=100>
										<option value="">
										</option>
										<%for(int i=1; i<13; i++) {%>
										<option value=<%= i%>>
										<%= i%>
										</option>
										<%}%>
									</select>
										<span>月</span>	
								<INPUT id=btnQuery class=stbtm01 value=查询 type=button
									name=btnQuery onclick="query()">									
							</td>
						
						</tr>
					</tbody>
				</table>
			</div>  

<% 
    String startDate = (String)request.getAttribute("startDate");
    startDate = startDate.replace("-", "");
%>


<SCRIPT language=javascript>
document.getElementById("year").value = <%= startDate.substring(0,4)%>;
document.getElementById("month").value = <%= startDate.substring(4,6)%>;
    </SCRIPT>
    
    
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="100%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="CountersignIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>预算总表</SPAN> </TD></TR></TBODY></TABLE>
      
<DIV class=autoOver2 align=center>
<TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" id="tbNullMsg" class="datatbl" border="1" rules="all" cellspacing="0">
  <TBODY>
  <%
  	ArrayList<String[]> budgetfeeList1 = (ArrayList<String[]>) request.getAttribute("budgetfeeList1");
  	ArrayList<String[]> sumBudgetUsefeeList = (ArrayList<String[]>) request.getAttribute("sumBudgetUsefeeList");  
    HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
    HashMap<Integer,Integer> getTotalBudgetFee = (HashMap<Integer,Integer>) request.getAttribute("getTotalBudgetFee");
    HashMap<Integer, String> projectMap = (HashMap<Integer, String>) request.getAttribute("projectMap");
    ArrayList<Integer> projects = (ArrayList<Integer>) request.getAttribute("projects");                       
  %>
  
        <TR id=tr_Head class=head>
          <TD noWrap style="WIDTH: 4%" align=left>科目</TD>
          <TD noWrap style="WIDTH: 4%" align=left>总金额</TD>
          
          <% 
          for(int i=0;i<projects.size();i++){
          %>
          <TD noWrap style="WIDTH: 5%" align=left>
          	<a href="budgetManagerServlet.do?action=query&budgetId&userName&project=<%= projectMap.get(projects.get(i))%>">
         		 <%= projectMap.get(projects.get(i))%></a>          
					</TD>                    
          <%}%>
       </TR>                                
       
          <%  if(!projects.isEmpty()){ 
            for(int i=0;i<sumBudgetUsefeeList.size();i++){
            
               String[] myString = sumBudgetUsefeeList.get(i);
  	  %>
  	 <TR  class=item>
            <TD noWrap align=left>
                <%= courseMap.get(Integer.valueOf(myString[0]))%>   	
            </TD>
            <TD noWrap align=left>
                <%= myString[6]%>
            </TD>  
            <%
            for(int j=0;j<projects.size();j++){
                int fee1 = 0;
                int budgetId = 0;
                int courseId  = 0;
                for(int k=0;k<budgetfeeList1.size();k++){
	                String[] use1 = budgetfeeList1.get(k);
	                int projectxx = Integer.valueOf(use1[1]);
	                if(use1[0].equals(myString[0]) && projectxx == projects.get(j)){
	                   fee1 = Integer.valueOf(use1[2]);
	                   budgetId = Integer.valueOf(use1[3]);
	                   courseId = Integer.valueOf(use1[0]);
	                }               
                }                
            %>
            <TD noWrap align=left>            
              <input style="width:50px" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="<%= budgetId + "_" + courseId%>" value="<%= fee1%>" />	
            </TD>                                               
         <%} %>                              
         </TR>
         <%} %>          
	 <TR  class=item>
	  <TD noWrap align=left>            
                合计   	
          </TD>
          <TD noWrap align=left>            
                <%= getTotalBudgetFee.get(-3)%>   	
          </TD>
            <%
            for(int j=0;j<projects.size();j++){ 
            int fee1 = getTotalBudgetFee.get(projects.get(j));
            %>
          <TD noWrap align=left>            
                <%= fee1%>   	
          </TD>
          <% } %>          
	 </TR>
	  <% }  
	            else{%>         
         <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>目前该月尚无未审批的预算! </FONT></TD>
         </TR>
        <% }
         %>  
       </TBODY></TABLE>                        
			<%}
			else{%>
			<span> 您没有此操作权限！</span>
			<%}%>
			</DIV>
  
<% if(role!=null && (Integer)role == 5){
ArrayList<Integer> projects = (ArrayList<Integer>) request.getAttribute("projects"); 
if(!projects.isEmpty()){  %> 
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TBODY>      
 <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=right><INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnQuery class=stbtm01 value=批准  onclick="permit()" type=button name=btnQuery>
      </DIV></TD></TR></TBODY></TABLE>
<%}}%>
 </FORM>
  <script>
     new Pager("tbNullMsg","tr_Head"); 
     new Pager("salesTable","sales_tr_head");    
  </script> 
</BODY></HTML> 