<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*,Business.Budget.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%   
    ArrayList<String[]> budgetfeeList1 = (ArrayList<String[]>) request.getAttribute("budgetfeeList1");
  	ArrayList<String[]> budgetfeeList2 = (ArrayList<String[]>) request.getAttribute("budgetfeeList2");
  	ArrayList<String[]> sumBudgetUsefeeList = (ArrayList<String[]>) request.getAttribute("sumBudgetUsefeeList");  
    HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
    HashMap<Integer,Integer> getTotalBudgetFee = (HashMap<Integer,Integer>) request.getAttribute("getTotalBudgetFee");
    HashMap<Integer, String> depts = (HashMap<Integer, String>) request.getAttribute("depts");
    ArrayList<Integer> dept = (ArrayList<Integer>) request.getAttribute("dept"); 
    ArrayList<Integer> dept1 = (ArrayList<Integer>) request.getAttribute("dept1"); 
    ArrayList<Integer> dept2 = (ArrayList<Integer>) request.getAttribute("dept2");  
    List<String[]> salesfeelist = (List<String[]>) request.getAttribute("salesfeelist");                            
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
	     Form1.action = "TotalBudgetFin.do?action=query";
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

  var isSales = false;
	function daochu(tableId){
		    var table = document.getElementById(tableId);
		    
		    isSales = tableId == 'salesTable';
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
var urlParameter="myArray="+myArray+"&colums="+colums+"&rows="+rows+"&year="+document.getElementById("year").value+"&month="+document.getElementById("month").value+"&status=3" + "&isSales=" + isSales;
   
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
action="TotalBudgetFin.do">
<input type="hidden" name="ids" id="ids"> 
<SCRIPT type=text/javascript>

</SCRIPT>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算统计<SPAN 
      class=arrow_subtitle>&gt;</SPAN>财务统计表</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="CountersignIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
  <%
	    Object role = request.getAttribute("role");
	    if(role!=null && ((Integer)role == 4)){
	 %>
<DIV style="WIDTH: 98%" id=divSearch>
			
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
								<input type="button" class=stbtm01 onclick="daochu('tbNullMsg');" value="导出总体预算" />	
								<input type="button" class=stbtm01 onclick="daochu('salesTable');" value="导出销售预算" />								
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
  
        <TR id=tr_Head class=head>
          <TD noWrap style="WIDTH: 8%" align=left>科目</TD>
          <TD noWrap style="WIDTH: 8%" align=left>总金额</TD>
          <TD noWrap style="WIDTH: 8%" align=left>武汉研究所</TD>
          <TD noWrap style="WIDTH: 8%" align=left>上海经营办</TD>
          <%
          for(int i=0;i<dept1.size();i++){
          %>
          <TD noWrap style="WIDTH: 5%" align=left>
          	<a href="SearchMyApprovalBudget.do?year=<%= startDate.substring(0,4)%>&month=<%= startDate.substring(4,6)%>&userno&budgetid&deptid=<%= dept1.get(i)%>">
         		 <%= depts.get(dept1.get(i))%></a>          
					</TD>                    
          <%}
          for(int i=0;i<dept2.size();i++){
          %>
          <TD noWrap style="WIDTH: 5%" align=left>
          	<a href="SearchMyApprovalBudget.do?year=<%= startDate.substring(0,4)%>&month=<%= startDate.substring(4,6)%>&userno&budgetid&deptid=<%= dept2.get(i)%>">
         		 <%= depts.get(dept2.get(i))%></a>          
					</TD>               
           <%}  
          %>
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
                <%= myString[6]%>
            </TD>  
            <TD noWrap align=left>
                <%= myString[4]%> 	
            </TD> 
            <TD noWrap align=left>
                <%= myString[5]%>  	
            </TD>    
            <%
            for(int j=0;j<dept1.size();j++){
                int fee1 = 0;
                for(int k=0;k<budgetfeeList1.size();k++){
	                String[] use1 = budgetfeeList1.get(k);
	                int deptxx = Integer.valueOf(use1[1]);
	                if(use1[0].equals(myString[0]) && deptxx==dept1.get(j)){
	                fee1 = Integer.valueOf(use1[2]);
	                }               
                }                
            %>
            <TD noWrap align=left>            
                <%= fee1%>  	
            </TD>                           
         <%}
            for(int j=0;j<dept2.size();j++){
                int fee1 = 0;
                for(int k=0;k<budgetfeeList2.size();k++){
	                String[] use1 = budgetfeeList2.get(k);
	                int deptxx = Integer.valueOf(use1[1]);
	                if(use1[0].equals(myString[0]) && deptxx==(dept2.get(j))){
	                fee1 = Integer.valueOf(use1[2]);
	                }               
                }                
            %>
            <TD noWrap align=left>            
                <%= fee1%>  	
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
          <TD noWrap align=left>            
                <%= getTotalBudgetFee.get(-1)%>    	
          </TD>
          <TD noWrap align=left>            
                <%= getTotalBudgetFee.get(-2)%>    	
          </TD>
            <%
            for(int j=0;j<dept1.size();j++){ 
            int fee1 = getTotalBudgetFee.get(dept1.get(j));
            %>
          <TD noWrap align=left>            
                <%= fee1%>   	
          </TD>
          <% } 
            for(int j=0;j<dept2.size();j++){ 
            int fee1 = getTotalBudgetFee.get(dept2.get(j));
            %>
          <TD noWrap align=left>            
                <%= fee1%>   	
          </TD>
          <% } %>          
	 </TR>
       </TBODY></TABLE>      
			
			</DIV>
     

            
<!-- 销售表 -->
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="100%"
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="CountersignIndex_files/bullet1.gif" width=16 height=16>
      <SPAN>销售预算表</SPAN> </TD></TR></TBODY></TABLE>
      
<DIV class=autoOver2 align=center>
<TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" class="datatbl" border="1" rules="all" cellspacing="0" id="salesTable">
  <TBODY>
     <TR id="sales_tr_head" class=head>
     	    <TD noWrap style="WIDTH: 8%" align=left>科目</TD>
          <% 
            List<DUser> sales = (List<DUser>)request.getAttribute("sales");
     	    if(sales!=null){
            for(int i= 0; i < sales.size(); i++){
          %>
          <TD noWrap style="WIDTH: 5%" align=left>
         		 <%= sales.get(i).getName() %>          
					</TD>                    
          <%
            }}
          %>
      </TR> 
      
      <% 
          if(salesfeelist!=null&& salesfeelist.size() > 0 ) {
            for(int m = 0; m < sumBudgetUsefeeList.size(); m++) {
               String[] myString = sumBudgetUsefeeList.get(m);
  	  %>
  	  <tr class=item>
            <TD noWrap align=left>
                <%= courseMap.get(Integer.valueOf(myString[0]))%>   	
            </TD>
      <%
         for (  int j = 0; j < sales.size(); j++  ) {
      	    for ( int i = 0; i < salesfeelist.size(); i++ )
      	    {
      	        String[] datas = salesfeelist.get(i);
      	        if ( datas[4].equals(sales.get(j).getNo()) && datas[0].equals(myString[0]) ){
      	%>
      	 <td noWrap align=left>            
             <%= datas[2]%>
        </td>
        <%
             }
            }
          }
        %>
      </tr> 
      <% 
         }
       }  else {%>         
         <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>目前该月尚无未审批的预算! </FONT></TD>
         </TR>
        <%
       	} 
      %>   
                                 
    </TBODY>
</TABLE> 
     

<%  	    if(role!=null && (Integer)role == 5){
if(dept!=null && !dept.isEmpty()){  %> 
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TBODY>      
 <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=right><INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnQuery class=stbtm01 value=批准  onclick="permit()" type=button name=btnQuery>
      </DIV></TD></TR></TBODY></TABLE>
<%}}%>
<%}
			else{%>
			<span> 您没有此操作权限！</span>
			<%}%>
 </FORM>
  <script>
     new Pager("tbNullMsg"); 
     new Pager("salesTable");    
  </script> 
</BODY></HTML> 