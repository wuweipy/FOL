<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0114)http://fol.zte.com.cn/BoeApproval/Pages/MultiApprovalIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Common.Countersign.*,Data.UserInfo.*,View.ParameterUtil,Data.Common.*"%>
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

<SCRIPT language=javascript>
			function showBoeList(boeID,boeNum)
			{
				var resourceID=document.all("hiResourceID").value;
				var menuPath=document.all("hiMenuPath").value;
				var url="AdiminApprovalList.aspx?resourceID="+resourceID+"&menuPath="+menuPath;
				url+="&boeID="+boeID+"&boenum="+boeNum + "&approvaltype=mult";
			    window.navigate(url);
			}
			
			function clear_text()
			{
				document.all("invoiceNo").value="";
				document.all("invoiceType").value="-1";
				document.all("department").value="";
				document.all("userName").value="";
				document.all("product").value="";		
				return false;
			}
			function setClickFlag()
			{
			    var sflag = document.all("hiClickFlag").value;
				if(sflag !="")
				{
				    return false;
				}	
				else
				{	
				    document.all("hiClickFlag").value = "ClickBtn";	
				    return true;
				}
			}
			
		        function query()
                        {
        	            Form1.action = "countersign.do?action=query";
        	            Form1.submit();
                        }
        
    </SCRIPT>
  <script>
    if(window.top.needAlert) {
     window.top.needAlert = false;
  <%
	  int chao = (Integer)request.getAttribute("chao");
	  ArrayList<String> notPermit = (ArrayList<String>)request.getAttribute("notPermit");
	  String notPermitNo = "";
	  if(chao == 1)
	  {
		  if(notPermit!=null && notPermit.size()!=0)
		  {
		    for(int i=0;i<notPermit.size();i++)
		    {
		       notPermitNo += notPermit.get(i) + ",";
		    } 
		    notPermitNo = "以下单号审批失败:"+ notPermitNo + "超出预算或者尚未提交预算";
		    %>	    
		    alert("<%= notPermitNo%>");
		   <% 
		    
		  }
	  
	  else{
  %>
     alert("审批失败，超出预算或者尚未提交预算");
  <%	     
		}

}
	%>
	 }
	</script>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 
action="countersign.do">
<INPUT id=invoiceNoIN size=1 type=hidden name=invoiceNoIN value=<%=request.getParameter("invoiceNo")%>>
<INPUT id=departmentIN size=1 type=hidden name=departmentIN value=<%=request.getParameter("department")%>>
<INPUT id=invoicetypeIN size=1 type=hidden name=invoicetypeIN value=<%=request.getParameter("invoiceType")%>>
<INPUT id=productIN size=1 type=hidden name=productIN value=<%=request.getParameter("product")%>>
<INPUT id=userNameIN size=1 type=hidden name=userNameIN value=<%=ParameterUtil.getChineseString(request, "userName", "utf-8")%>>
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
    <TD><IMG class=icon src="CountersignIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>单据审批<SPAN 
      class=arrow_subtitle>&gt;</SPAN>所长审批</SPAN> </TD>
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
      <DIV align=left noWrap><SPAN id=invoicenoxx>单据号</SPAN></DIV></TD>
    <TD><INPUT style="WIDTH: 180px; HEIGHT: 20px" id=invoiceNo 
    name=invoiceNo></TD>
    <TD class=title>
      <DIV align=left noWrap><SPAN id=invoicetypexx>单据类型</SPAN></DIV></TD>
    <TD>
        <SELECT size="0" style="WIDTH: 180px" id=invoiceType name=invoiceType>
	<%    
        HashMap<Integer, String> courseMap = (HashMap<Integer, String>)request.getAttribute("courseMap");
        Iterator<Integer> typeIterator = courseMap.keySet().iterator();
  	    while(typeIterator.hasNext()) { 
    		   int key = typeIterator.next();
    %>
	<option selected="" value="<%= key %>"><%= courseMap.get(key)%></option>
	<%     }   %>
	<option selected="selected" value="-1"></option>
</SELECT>

</TD>

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
    <Option value=<%= deptkey%> ><%= deptInfo.get(deptkey)%></option>
   <%
     }
   %>    

</SELECT></TD>
        
</TR>
  <TR>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxx>报销人</SPAN></DIV></TD>
          <TD><Input style="WIDTH: 180px; HEIGHT: 20px" id=userName 
    name=userName></TD>  
       
<TD class=title>
      <DIV align=left noWrap><SPAN id=productxx>所属项目</SPAN></DIV></TD>
    <TD><SELECT size="0" style="WIDTH: 180px" id=product name=product>    
        <OPTION value=""></OPTION>
    <%   
         List<DProduct> productList = (List<DProduct>)request.getAttribute("productInfo");
         HashMap<Integer, String> productMap = new HashMap<Integer, String>();
  	 for(int i = 0; i < productList.size(); i++) 
  	 { 
           productMap.put(productList.get(i).getId(),productList.get(i).getName());
    %>
    <Option value=<%= productList.get(i).getId()%> ><%= productList.get(i).getName()%></option>
   <%
     }
   %>    
  </SELECT></TD>  
</TR>
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
        <TR class=head>
          <TD style="WIDTH: 5%" align=middle><Input id=chk_SelAll type=checkbox> 
            <LABEL>全选 </LABEL></TD>
          <TD noWrap style="WIDTH: 8%" align=left>单据号</TD>
          <TD noWrap style="WIDTH: 8%" align=left>报销人</TD>
          <TD noWrap style="WIDTH: 8%" align=left>报销人工号</TD>
          <TD noWrap style="WIDTH: 8%" align=left>所属项目</TD> 
          <TD noWrap style="WIDTH: 8%" align=left>报销项目</TD>                   
          <TD noWrap style="WIDTH: 8%" align=left>申请金额</TD>         
          <TD style="WIDTH: 12%" noWrap>提交日期</TD>
          <TD noWrap style="WIDTH: 8%" align=left>单据类型</TD>
          <TD noWrap style="WIDTH: 8%" align=left>审批状态</TD>
          <TD noWrap style="WIDTH: 17%" align=left>摘要</TD>
       </TR>
          <script>                                           
             function ifhavebox()
             {
               var j = 1 ;
               var havebox = 0;
               var itemNum = document.getElementById("itemNum").value;               
               while(j<= itemNum)
               {
                  if(document.getElementById("isCheck"+ j ).checked ==  true ) 
                    {
                     havebox = 1;
                     j = itemNum;
                    }   
                  j++;
               }
               return havebox;
             }
             
             function permit()
             {                          
               if(ifhavebox() == 1)
                 {
                  var appFlag = 0;
                    if(confirm("您确认要批准吗?"))
                    {
                    appFlag = 1 ; 
                    }                               
                   if(appFlag==1)
                   {
                   window.top.needAlert = true;
                   Form1.action = "countersign.do?action=permit";
                   Form1.submit();
                    }  
                }
               else
               {
                alert("您至少需要选择一条记录");
               }              
             }
             
             function notPermit()
             { 
               if(ifhavebox() == 1)
                 {
                  var appFlag = 0;
                    if(confirm("您确认要退回吗?"))
                    {
                    appFlag = 1 ; 
                    }                               
                   if(appFlag==1)
                   {
                   window.top.needAlert = true;                               
                   Form1.action = "countersign.do?action=notPermit";
                   Form1.submit();
                    }  
                }
               else
               {
                alert("您至少需要选择一条记录");
               }               
             }

          </script>          
          <%  
          HashMap<Integer, DCountersign> countersignMap = (HashMap<Integer, DCountersign>)request.getAttribute("countersignInfo");
          HashMap<String, DUser> userInfoMap = (HashMap<String, DUser>)request.getAttribute("userInfo");         
  	   Iterator<Integer> iterator = countersignMap.keySet().iterator(); 
  	   int i = 0;
  	   if(countersignMap.size()>0){
  	   while(iterator.hasNext()) 	   
  	   { 
  	   	int key = iterator.next();
  	   	i++;
  	   %>  	
          <TR id=tr_Head class=item>
          <TD  align=middle>
            <INPUT TYPE=checkbox name=isCheck<%= i%> id=isCheck<%= i%> >
          </TD>
          <TD noWrap  align=left>
          	<a href="invoiceDetail.do?InvoiceNoDe=<%= countersignMap.get(key).getInvoiceNo()%>">
          	<%= countersignMap.get(key).getInvoiceNo()%></a>
          	<input  name=InvoiceNo<%= i%> value=<%= countersignMap.get(key).getInvoiceNo()%> type="hidden" style="border: none; width: 200px" >
          </TD>
          <TD noWrap align=left>
          	<%= userInfoMap.get(countersignMap.get(key).getNo()).getName()%>
          </TD>
          <TD noWrap align=left>
          	<%= countersignMap.get(key).getNo()%>
          	<input  name=UserNo<%= i%> value=<%= countersignMap.get(key).getNo()%> type="hidden" style="border: none; width: 200px" >
          </TD>          
          <TD noWrap align=left>
          	<%= deptInfo.get(countersignMap.get(key).getDeptId())%>
          	<input  name=DeptId<%= i%> value=<%= countersignMap.get(key).getDeptId()%> type="hidden" style="border: none; width: 200px" >
          </TD> 
          <TD noWrap  align=left>
          	<%= productMap.get(countersignMap.get(key).getProductId())%>
          	<input  name=productId<%= i%> value=<%= countersignMap.get(key).getProductId()%> type="hidden" style="border: none; width: 200px" >          	
          </TD>                     
          <TD noWrap align=left>
          	<%= countersignMap.get(key).getTotalFee()%>
          	<input  name=TotalFee<%= i%> value=<%= countersignMap.get(key).getTotalFee()%> type="hidden" style="border: none; width: 200px" >
          </TD>                  
          <TD noWrap  align=left>
          	<%= countersignMap.get(key).getSubmitDate()%>
          </TD>    
          <TD noWrap align=left>
        <%
            String invoiceType = courseMap.get(countersignMap.get(key).getInvoiceType());
        %>
        <%= invoiceType %>
          <input  name=invoiceType<%= i%> value=<%= countersignMap.get(key).getInvoiceType()%> type="hidden" style="border: none; width: 200px" >
          <input  name=invoiceTypeName<%= i%> value=<%= invoiceType%> type="hidden" style="border: none; width: 200px" >
          </TD>          
               
          <TD noWrap align=left>
                    <SPAN>
               <% String statusStr[] = {"草稿","待科长审批","待所长审批", "待财务审批", "正常关闭", "已被退回" ,"已撤销" ,"待总经理审批" };%>    
          	<%= statusStr[countersignMap.get(key).getStatus()]%></SPAN>
          </TD>          
          <TD noWrap align=left>
          	<%= countersignMap.get(key).getSummary()%>
          </TD>                    
         </TR>
         <%
       }
       } 
       else {
          %>
        <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>对不起，没有符合条件的记录! </FONT></TD>
       </TR>
       <%  }
         %>  
       <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1 type=hidden name=itemNum value=<%=countersignMap.size()%>>     
       </TBODY></TABLE></DIV>
          
   <script>
     var pager = new Pager("tbNullMsg","tr_Head");
     AllCheck("chk_SelAll",pager,function(i)
     {
         document.getElementById("isCheck" + (i + 1)).checked = document.getElementById("chk_SelAll").checked
     });      
  </script> 

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TBODY>      
 <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=right><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiDeptID 
      size=0 type=hidden name=hiDeptID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiUserID size=0 type=hidden name=hiUserID> <INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnQuery class=stbtm01 value=批准  onclick="permit()" type=button name=btnQuery><INPUT 
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
   var invoiceNoIN = document.getElementById("invoiceNoIN").value;
   if(invoiceNoIN == -1 || invoiceNoIN == "" || invoiceNoIN == "null")
   {
       document.getElementById("invoiceNo").value = "";
   }
   else
   {
       document.getElementById("invoiceNo").value = invoiceNoIN;
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
   
   var invoicetypeIN = document.getElementById("invoicetypeIN").value;
   if(invoicetypeIN == -1 || invoicetypeIN == "" || invoicetypeIN == "null")
   {
       document.getElementById("invoiceType").value = "-1";
   }
   else
   {
       document.getElementById("invoiceType").value = invoicetypeIN;
   }  
   
   var productIN = document.getElementById("productIN").value;
   if(productIN == -1 || productIN == "" || productIN == "null")
   {
       document.getElementById("product").value = "";
   }
   else
   {
       document.getElementById("product").value = productIN;
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
 </FORM></BODY></HTML> 