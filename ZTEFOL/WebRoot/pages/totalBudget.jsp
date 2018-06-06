<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*,Business.Budget.*"%>
<%
String path = request.getContextPath();
%>
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

<SCRIPT language=javascript>		
  function query()
        {
	     Form1.action = "TotalBudget.do?action=query";
	     Form1.submit();
        }
    </SCRIPT>
    
        <script>

	function getTableColumnValue(tableId, rowNumber, columnNumber) {
            var tableRef = document.getElementById(tableId);

            var elementRef = tableRef.rows[rowNumber].cells[columnNumber];
            var elementValue = '';

            if (elementRef.textContent) {
                // Firefox
                elementValue = elementRef.textContent;
            }
            else if (elementRef.innerText) {
                // IE
                elementValue = elementRef.innerText;
            }
            else {
                // Default
                elementValue = elementRef.innerHTML;
                var regExp = /<\/?[^>]+>/gi;

                elementValue = elementValue.replace(regExp, '');
            }

            //alert(elementValue);

            return elementValue;
    }

	function daochu(tableId){
		    var table = document.getElementById(tableId);
		    //行数
			var rows = table.rows.length;
			//列数
   			var colums = table.rows[0].cells.length;
   		
			var myArray=new Array()
			for (var i = 0; i <rows; i++) {
				var oneline = getTableColumnValue(tableId, i, 0);
				for (var j = 1; j < colums; j++) {
					oneline = oneline + ','+getTableColumnValue(tableId, i, j);
				};
				myArray[i]=oneline;
			}
			doSubmit(myArray,colums,rows);
	}	

function doSubmit(myArray,colums,rows)
{
var urlParameter="myArray="+myArray+"&colums="+colums+"&rows="+rows+"&year="+document.getElementById("year").value+"&month="+document.getElementById("month").value+"&status=5";
   
    if (typeof XMLHttpRequest != 'undefined')
        {
            httpRequest3 = new XMLHttpRequest();
        }
        else if (typeof ActiveXObject != 'undefined')
        {
            httpRequest3 = new ActiveXObject('Microsoft.XMLHTTP');
        }
        if (httpRequest3)
        {
            httpRequest3.open('POST', "<%= path%>/pages/exportBudget.do", true);//true为异步
            httpRequest3.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            httpRequest3.onreadystatechange=onComplete;
            httpRequest3.send(urlParameter);
        }

    return;
}
    function onComplete()
    {
        if(4==httpRequest3.readyState)
        {
            //alert(httpRequest3.responseText);
            if(200==httpRequest3.status)
            {               
                var retText=httpRequest3.responseText;
                self.location='<%= path%>/download/'+retText;
            }
        }
    }   
	</script>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 
action="TotalBudget.do">
<input type="hidden" name="ids" id="ids"> 
<SCRIPT type=text/javascript>

</SCRIPT>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算统计<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算总表</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="CountersignIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
  
<DIV style="WIDTH: 98%" id=divSearch>
			<%
	    Object role = request.getAttribute("role");
	    Object delegate = request.getAttribute("delegate");
	    if((role!=null && ((Integer)role == 4  || (Integer)role==5)) || (Boolean)delegate){
	 %>
				<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
					cellPadding=0 width="100%" align=center>
					
					<tbody>
						<tr>
							<td colspan="4">
								<img class="icon" src="images/search.gif">
								请填写查询条件 (未选择默认为当前年或月)
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
									<select  id="month" name="month" width=100>
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
								<input type="button" class=stbtm01 onclick="daochu('tbNullMsg');" value="导出" />									
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
  	ArrayList<String[]> budgetfeeList2 = (ArrayList<String[]>) request.getAttribute("budgetfeeList2");
  	ArrayList<String[]> sumBudgetUsefeeList = (ArrayList<String[]>) request.getAttribute("sumBudgetUsefeeList");  
        HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
        HashMap<Integer,Float> getTotalBudgetFee = (HashMap<Integer,Float>) request.getAttribute("getTotalBudgetFee");
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
          	<a href="SearchMyApprovalBudget.do?year=<%= startDate.substring(0,4)%>&month=<%= startDate.substring(4,6)%>&userno&budgetid&project=<%= projectMap.get(projects.get(i))%>">
         		 <%= projectMap.get(projects.get(i))%></a>          
					</TD>                    
          <%}%>
       </TR>                                
       
          <%  
            for(int i=0;i<sumBudgetUsefeeList.size();i++){
            
               String[] myString = sumBudgetUsefeeList.get(i);
  	  %>
  	 <TR  class=item>
            <TD noWrap align=left>
                <%= courseMap.get(Integer.valueOf(myString[0]))%>   	
            </TD>
            <TD noWrap align=left>
                <%= String.format("%.2f",Float.valueOf(myString[6]))%>
            </TD>   
            <%
            for(int j=0;j<projects.size();j++){
                float fee1 = 0;
                for(int k=0;k<budgetfeeList1.size();k++){
	                String[] use1 = budgetfeeList1.get(k);
	                int projectxx = Integer.valueOf(use1[1]);
	                if(use1[0].equals(myString[0]) && projectxx==projects.get(j)){
	                	fee1 = Float.valueOf(use1[2]);
	                }               
                }                
            %>
            <TD noWrap align=left>            
                <%= String.format("%.2f", fee1)%>  	
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
                Float fee1 = getTotalBudgetFee.get(projects.get(j));
            %>
          <TD noWrap align=left>            
                <%= String.format("%.2f", fee1)%>
          </TD>
          <% } %>          
	 </TR>
       </TBODY></TABLE>
  <script>
  
     var pager = new Pager("tbNullMsg","tr_Head");    
  </script>        
       
			<%}
			else{%>
			<span> 您没有此操作权限！</span>
			<%}%>
			</DIV>
     

            
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