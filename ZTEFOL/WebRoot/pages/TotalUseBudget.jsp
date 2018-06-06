<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,Data.Common.*,Business.Budget.*"%>
<%
String path = request.getContextPath();
%>
<HTML><HEAD><TITLE>费用使用表</TITLE>
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
        	if(check())
			{
	        	Form1.action = "totalUse.do?action=query";
	        	Form1.submit();
			}
        }
        
    function checkIsValidDate(str)
{   
    var result;
    var ntemp;
    //如果为空，则通过校验
    if(str == "")
        return "";
    var pattern = /^((\d{4})|(\d{2}))-(\d{1,2})-(\d{1,2})$/g;
    if(!pattern.test(str))
        return "INVALID DATE";    
}
        
   function check()
   {
        	var dateFrom = document.getElementById("txtSubmitDateFrom").value;
        	var dateTo = document.getElementById("txtSubmitDateTo").value;
        	var  result = checkIsValidDate(dateFrom);
	  if (result=="INVALID DATE") {      
	    alert("无效的开始日期值,请重新输入!");
	    return false;
	  }
  
	  result = checkIsValidDate(dateTo);
	  //alert(result);
	  if (result=="INVALID DATE") { 
	    alert("无效的结束日期值,请重新输入!");
	    return false;
	  }

	var ass,aD,aS;
	var bss,bD,bS;
	ass=dateFrom.split("-");//以"-"分割字符串，返回数组；
	aD=new Date(ass[0],ass[1]-1,ass[2]);//格式化为Date对像;
	aS=aD.getTime(); 
	bss=dateTo.split("-");
	bD=new Date(bss[0],bss[1]-1,bss[2]);
	bS=bD.getTime();
	if(aS>bS){
	    alert("结束日期不能小于开始日期，请重新输入");
	    return false;
	}
	return true;  
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
var urlParameter="myArray="+myArray+"&colums="+colums+"&rows="+rows;
   
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
            httpRequest3.open('POST', "<%= path%>/pages/exportInvoice.do", true);//true为异步
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
action="totalUse.do">
<input type="hidden" name="ids" id="ids"> 
<SCRIPT type=text/javascript>

</SCRIPT>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>预算统计<SPAN 
      class=arrow_subtitle>&gt;</SPAN>费用使用表</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="CountersignIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
  
<DIV style="WIDTH: 98%" id=divSearch>
			<%
	    Object role = request.getAttribute("role");
	    if(role!=null && ((Integer)role == 4  || (Integer)role==5)){
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
								日期范围
							</td>
							<td colspan="1" width=900>
								<INPUT style="WIDTH: 150px; HEIGHT: 21px" id="txtSubmitDateFrom"
									name=txtSubmitDateFrom>
								至
								<INPUT style="WIDTH: 150px; HEIGHT: 21px" id="txtSubmitDateTo"
									name=txtSubmitDateTo>&nbsp&nbsp
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
    String endDate = (String)request.getAttribute("endDate");
    startDate = startDate.substring(0,10) ;
    endDate = endDate.substring(0,10) ;
%>
</br>
日期范围：<%= startDate%> 至 
<%= endDate%>  

<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="100%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="CountersignIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>费用使用列表</SPAN>
       </TD></TR></TBODY></TABLE>
      
<DIV class=autoOver2 align=center>
<TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" id="tbNullMsg" class="datatbl" border="1" rules="all" cellspacing="0">
  <TBODY>
  <%
  	ArrayList<String[]> usefeeList1 = (ArrayList<String[]>) request.getAttribute("usefeeList1");
  	ArrayList<String[]> usefeeList2 = (ArrayList<String[]>) request.getAttribute("usefeeList2");
  	ArrayList<String[]> sumUsefeeList = (ArrayList<String[]>) request.getAttribute("sumUsefeeList");  
        HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
        HashMap<Integer,Float> getTotalFee = (HashMap<Integer,Float>) request.getAttribute("getTotalFee");
	HashMap<Integer, String> projectMap = (HashMap<Integer, String>) request.getAttribute("projectMap");
	ArrayList<Integer> projects = (ArrayList<Integer>) request.getAttribute("projects");    
        HashMap<Integer, String> depts = (HashMap<Integer, String>) request.getAttribute("depts");
        ArrayList<String> depts1 = new ArrayList<String>();
        ArrayList<Integer> depts11 = new ArrayList<Integer>();
        ArrayList<String> depts2 = new ArrayList<String>();
        ArrayList<Integer> depts22 = new ArrayList<Integer>();        
  %>
        <TR id=tr_Head class=head>
          <TD noWrap style="WIDTH: 6%" align=left>科目</TD>
          <TD noWrap style="WIDTH: 6%" align=left>总使用金额</TD>
          <%
          for(int i=0;i<projects.size();i++){
          %>
          <TD noWrap style="WIDTH: 8%" align=left><%= projectMap.get(projects.get(i))%></TD>                            
           <%}  
          %>
       </TR>                                
       
          <%  
            for(int i=0;i<sumUsefeeList.size();i++){
            
               String[] myString = sumUsefeeList.get(i);
  	  %>
  	 <TR  class=item>
            <TD noWrap align=left>
                <%= courseMap.get(Integer.valueOf(myString[0]))%>   	
            </TD>
            <TD noWrap align=left>
                <%= String.format("%.2f", Float.valueOf(myString[3]))%>   	
            </TD>  
            <%
            for(int j=0;j<projects.size();j++){
                String fee = "0";
                for(int k=0;k<usefeeList1.size();k++){
	                String[] use1 = usefeeList1.get(k);
	                String projectxx = projectMap.get(Integer.valueOf(use1[1]));
	                if(use1[0].equals(myString[0]) && projectxx.equals(projectMap.get(projects.get(j)))){
	                    fee = use1[2];
	                }               
                }
            %>
            <TD noWrap align=left>            
                 <%= String.format("%.2f", Float.valueOf(fee))%>   	
            </TD>                                                     
         <%}%>                                
         </TR>
	<%}%>
	 <TR  class=item>
	  <TD noWrap align=left>            
                合计   	
          </TD>
          <TD noWrap align=left>            
                <%= String.format("%.2f", Float.valueOf(getTotalFee.get(-3)))%>   	
          </TD>
            <%
            for(int j=0;j<projects.size();j++){ %>
          <TD noWrap align=left>            
                <%= String.format("%.2f", Float.valueOf(getTotalFee.get(projects.get(j)))) %>   	
          </TD>
          <% } %>      
	 </TR>
       </TBODY></TABLE>
<script type="text/javascript">
	var pickerFrom = new Pikaday( {
		field : document.getElementById("txtSubmitDateFrom"),
		firstDay : 1,
		minDate : new Date('2000-01-01'),
		maxDate : new Date('2030-12-31'),
		yearRange : [ 2000, 2030 ]
	});

	var pickerTo = new Pikaday( {
		field : document.getElementById("txtSubmitDateTo"),
		firstDay : 1,
		minDate : new Date('2000-01-01'),
		maxDate : new Date('2030-12-31'),
		yearRange : [ 2000, 2030 ]
	});
</script>      
  <script>
  
     var pager = new Pager("tbNullMsg","tr_Head");    
  </script>        
			<%}
			else{%>
			<TABLE>您没有此操作权限！</TABLE>
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