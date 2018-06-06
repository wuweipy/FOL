<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Data.Claims.*,Data.Common.*,Data.UserInfo.*,java.lang.*,View.ParameterUtil,java.sql.Timestamp"%>
<HTML><HEAD><TITLE>财务审批</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5>
<META content=IE=EmulateIE7 http-equiv=X-UA-Compatible><LINK rel=stylesheet 
type=text/css href="CountersignIndex_files/style.css"> 
<LINK rel=stylesheet 
type=text/css href="Pager/Page.css"> 

<SCRIPT language=javascript 
src="AdminApprovalIndex_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="AdminApprovalIndex_files/FbpLovSelect.js"></SCRIPT>

<SCRIPT language=javascript 
src="AdminApprovalIndex_files/AdminPageFunction.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="Pager/AllCheck.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="Pager/Pager.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="AdminApprovalIndex_files/Calendar.js"></SCRIPT>

<SCRIPT language=javascript 
src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>

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
	
<SCRIPT language=javascript>
    function query()
    {
        var str = "";
        var boeNum = document.getElementById("txtBoeNum").value;
        var boeType = document.getElementById("txtBoeType").value;
        var deptId = document.getElementById("txtDeptName").value;
        var productId = document.getElementById("txtProductName").value;
        var employeeName = document.getElementById("txtEmployeeName").value;

        if(boeNum == "")
        {
            str +="&invoiceNo=-1";
        }
        else
        {
            str +="&invoiceNo=" + boeNum;
        }
        if(boeType == "")
        {
            str +="&invoiceType=-1";
        }
        else
        {
            str +="&invoiceType=" + boeType;
        }
        if(deptId == "")
        {
            str +="&deptId=-1";
        }
        else
        {
            str +="&deptId=" + deptId;
        }
        if(employeeName == "")
        {
            str +="&employeeName=-1";
        }
        else
        {
            str +="&employeeName=" + employeeName;
        }
        if(productId == "")
        {
            str +="&productId=-1";
        }
        else
        {
            str +="&productId=" + productId;
        }
        
        Form1.action = "financeApproval.do?action=query" + str;
        Form1.submit();
    }
    
    function appAmountIsNull()
    {
        var itemNum = document.getElementById("itemNum").value;
        for(var Index=0; Index<itemNum; Index++)
        {
            if(document.getElementById("select"+Index).checked == true)
            {
                var appAmount = document.getElementById("txtApprovalAmount"+Index).value;
                if(appAmount == '' || appAmount == null)
                {
                    alert('审批金额不允许为空，请重新输入！');
                    appFlag = 0
                    return false;
                }
            }
        }
        return true;
    }
    
    function approval(flag)
    {
        var appFlag = 0;
        var itemNum = document.getElementById("itemNum").value;
        var selectedCount = 0;
        var varStr = "action=approval";
        for(var selIndex=0; selIndex<itemNum; selIndex++)
        {
            if(document.getElementById("select"+selIndex).checked == true)
            {
                varStr += "&InvoiceNo"+selectedCount+"=";
                varStr += document.getElementById("InvoiceNo"+selIndex).innerHTML;

                varStr += "&invoiceTypeNo"+selectedCount+"=";
                varStr += document.getElementById("invoiceTypeNo"+selIndex).value;

                varStr += "&totalFeeT"+selectedCount+"=";
                varStr += document.getElementById("totalFeeT"+selIndex).value;
                
                varStr += "&ApprovalAmount"+selectedCount+"=";
                varStr += document.getElementById("txtApprovalAmount"+selIndex).value;
                
                varStr += "&baoxiaoNo"+selectedCount+"=";
                varStr += document.getElementById("baoxiaoNo"+selIndex).value;
                
                selectedCount ++;
            }
        }
        
        varStr += "&selectedCount="+selectedCount;
        if(selectedCount > 0)
        {
            if(appAmountIsNull())
            {
                if(flag == 1)
                {
                    varStr += "&flag="+1;
                    if(confirm("您确认要批准吗?"))
                    {
                        appFlag = 1;
                    }
                }
                else if(flag == 0)
                {
                    varStr += "&flag="+0;
                    if(confirm("您确认要退回吗?"))
                    {
                        appFlag = 1;
                    }
                }
            }
            else
            {
                return ;
            }
        }
        else
        {
            alert("您至少需要选择一条记录");
        }
        
        if(appFlag == 1)
        {
            window.top.needAlert = true;
            Form1.action = "financeApproval.do?" + varStr;
            Form1.submit();
        }
    }
    
    /*function setAllChecked()
    {
        var itemNum = document.getElementById("itemNum").value;
        if(document.getElementById("chk_SelAll").checked == true)
        {
            for(var i=0; i<itemNum; i++)
            {
                
                document.getElementById("select"+i).checked = true; 
            }
        }
        else
        {
            for(var i=0; i<itemNum; i++)
            {
                document.getElementById("select"+i).checked = false; 
            }
        }
    }*/
    
    function deptId2Name(id)
    {
         var itemNum = document.getElementById("itemNum").value;
         for(var i=0;i<itemNum;i++)
         {
              var obj = document.getElementById("DeptId"+id+i);
              if(obj != null)
              {
                  obj.innerHTML = document.getElementById("hiDept"+id).value;
              }
         }
    }
    
    function productId2Name(id)
    {
         var itemNum = document.getElementById("itemNum").value;
         for(var i=0;i<itemNum;i++)
         {
              var obj = document.getElementById("ProductId"+id+i);
              if(obj != null)
              {
                  obj.innerHTML = document.getElementById("hiProduct"+id).value;
              }
         }
    }
    
    function showBoeList(boeID,boeNum)
    {
      var resourceID=document.all("hiResourceID").value;
      var menuPath=document.all("hiMenuPath").value;
      var url="AdiminApprovalList.aspx?resourceID="+resourceID+"&menuPath="+menuPath;
      url+="&boeID="+boeID+"&boenum="+boeNum + "&approvaltype=admin";
      var localUrl = location.href;
      if (localUrl.indexOf("uniteappr") > 0)
      {
          url += "&uniteappr=admin";  // 统一审批 - 财务审批
      }
      if (parent != null && parent.ShowPage != null)
      {
          url += "&hasframe=1";
          parent.ShowPage(0,url);
      }
      else
      {
          window.location.href = url;
      }
    }
    
    function clear_text()
    {
      document.all("txtBoeNum").value = "";
      document.all("txtBoeType").value ="";
      document.all("txtDeptName").value="";
      document.all("txtProductName").value="";
      document.all("txtEmployeeName").value="";
      document.all("hiBoeNum").value = "";
      document.all("hiBoeType").value ="";
      document.all("hiEmployeeName").value="";
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
      
      function  CheckDate()
        {
            var dateStart = document.all("txtStartDate");
            var dateEnd = document.all("txtEndDate");

            // 到期日日期格式交验
            if(dateStart != null && dateEnd != null && dateStart.value != "" && dateEnd.value != "")
            {
                if(dateStart.value > dateEnd.value )
                {
                    alert('结束日期不能小于开始日期！');

                    dateStart.value = "";
                    dateEnd.value = "";

                    return false;
                }
            } 
            return true;

        }
    </SCRIPT>
    
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1 action=financeApproval.do>
<INPUT id=hiBoeNum size=1 type=hidden name=hiBoeNum value=<%=request.getParameter("invoiceNo")%>>
<INPUT id=hiBoeType size=1 type=hidden name=hiBoeType value=<%=request.getParameter("invoiceType")%>>
<INPUT id=hiDeptId size=1 type=hidden name=hiDeptId value=<%=request.getParameter("deptId")%>>
<INPUT id=hiProductId size=1 type=hidden name=hiProductId value=<%=request.getParameter("productId")%>>
<INPUT id=hiEmployeeName size=1 type=hidden name=hiEmployeeName value=<%=ParameterUtil.getChineseString(request, "employeeName", "utf-8")%>>

<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="AdminApprovalIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN 
      class=arrow_subtitle>&gt;</SPAN>单据审批<SPAN 
      class=arrow_subtitle>&gt;</SPAN>财务审批</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="AdminApprovalIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD colSpan=6><IMG class=icon src="AdminApprovalIndex_files/search.gif"> 
      请填写查询条件 </TD></TR>
  <TR>
    <TD class=title height=24>
      <DIV align=left noWrap><SPAN id=lblBoeNum>单据号</SPAN></DIV></TD>
    <TD><INPUT style="WIDTH: 133px" id=txtBoeNum name=txtBoeNum></TD>
    <TD class=title>
      <DIV align=left noWrap><SPAN id=lblBoeType>单据类型</SPAN></DIV>
    </TD>
    <TD>        
        <select size="0" style="WIDTH: 120px; HEIGHT: 20px" name="txtBoeType" id="txtBoeType">
	<%    
        HashMap<Integer, String> courseMap = (HashMap<Integer, String>)request.getAttribute("courseMap");
        Iterator<Integer> typeIterator = courseMap.keySet().iterator();
  	    while(typeIterator.hasNext()) { 
    		   int key = typeIterator.next();
    %>
	<option selected="" value="<%= key %>"><%= courseMap.get(key)%></option>
	<%     }   %>
	<option selected="selected" value="-1"></option>
	</select>
    </TD>
    <TD class=title>
      <DIV align=left noWrap><SPAN id=lblDept>报销人部门</SPAN></DIV>
    </TD>
    <TD>
      <select size="0" style="WIDTH: 120px; HEIGHT: 20px" name="txtDeptName" id="txtDeptName">
             <option value=-1></option>
      <% List<DDept> deptsInfo = (List<DDept>)request.getAttribute("deptsInfo");
       if(!deptsInfo.isEmpty())
       {  
      %>
          <%
          for(int index = 0; index < deptsInfo.size(); index++)
          {
          %>
              <option value=<%= deptsInfo.get(index).getId()%>><%= deptsInfo.get(index).getName()%></option>
          <%
          }

          for(int index = 0; index < deptsInfo.size(); index++)
          {
          %>
              <INPUT id=hiDept<%= deptsInfo.get(index).getId()%> size=1 type=hidden name=hiDept<%= deptsInfo.get(index).getId()%> value=<%= deptsInfo.get(index).getName()%>>
          <%
          }
       }
          %>
      </select>
     </TD>
    </TR>
  <TR>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=lblEmployee>报销人</SPAN></DIV></TD>
    <TD style="HEIGHT: 24px">
      <INPUT style="WIDTH: 133px; HEIGHT: 20px" id=txtEmployeeName name=txtEmployeeName>
    </TD>
    <TD class=title>
      <DIV align=left noWrap><SPAN id=lblProject>所属项目</SPAN></DIV>
    </TD>
    <TD>
      <select size="0" style="WIDTH: 120px; HEIGHT: 20px" name="txtProductName" id="txtProductName">
             <option value=-1></option>
      <% List<DProduct> productInfo = (List<DProduct>)request.getAttribute("productInfo");
       if(!productInfo.isEmpty())
       {  
      %>
          <%
          for(int pindex = 0; pindex < productInfo.size(); pindex++)
          {
          %>
              <option value=<%= productInfo.get(pindex).getId()%>><%= productInfo.get(pindex).getName()%></option>
          <%
          }

          for(int pindex = 0; pindex < productInfo.size(); pindex++)
          {
          %>
              <INPUT id=hiProduct<%= productInfo.get(pindex).getId()%> size=1 type=hidden name=hiProduct<%= productInfo.get(pindex).getId()%> value=<%= productInfo.get(pindex).getName()%>>
          <%
          }
       }
      %>
      </select>
     </TD>
  </TR>
  <TR>
    <TD colSpan=6 align=right>
      <DIV><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiDeptID size=1 
      type=hidden name=hiDeptID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiUserID size=1 type=hidden name=hiUserID> <INPUT id=btnQuery class=stbtm01 value=查询 type=button name=btnQuery onclick="query()"> <INPUT id=btnClear language=javascript class=stbtm01 onclick="clear_text()" value=清空 type=submit name=btnClear> 
      <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiEmployeeID size=1 
      type=hidden name=hiEmployeeID><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
      id=hiProdProjectID size=1 type=hidden name=hiProdProjectID> <INPUT 
      style="WIDTH: 32px; HEIGHT: 20px" id=hiClickFlag size=1 type=hidden 
      name=hiClickFlag><INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture 
      value=zh-CN size=1 type=hidden name=hiCulture> 
</DIV></TD></TR></TBODY></TABLE></DIV>
<TABLE id=queryMessage class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD style="BORDER-RIGHT-STYLE: none"><IMG class=icon 
      src="AdminApprovalIndex_files/icon_note.gif"> <SPAN 
      id=lblApprovalList>审批列表</SPAN> </TD></TR></TBODY></TABLE>
<DIV class=autoOverApprove align=center>
<TABLE id=Table2 border=0 cellSpacing=0 cellPadding=1 width="100%">
  <TBODY>
  <TR>
    <TD>
      <DIV></DIV></TD></TR></TBODY></TABLE>
 <TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" id="adminApproval" class="datatbl" border="1" rules="all" cellspacing="0">
  <TBODY>
  <%
  	HashMap<String, DProject> userInfoMap = (HashMap<String, DProject>)request.getAttribute("userInfo");
  %>
  <TR id=tr_Head class=head>
          <TD style="WIDTH: 5%" align=middle>
             <INPUT id=chk_SelAll type=checkbox> 
            <LABEL>全选 </LABEL>
          </TD>
          <TD style="WIDTH: 10%" noWrap align=left>单据号</TD>
          <TD style="WIDTH: 9%" noWrap align=left>报销人工号</TD>
          <TD style="WIDTH: 8%" noWrap align=left>报销人</TD>
          <TD style="WIDTH: 7%" noWrap align=left>所属项目</TD> 
          <TD style="WIDTH: 8%" noWrap align=left>报销项目</TD>                   
          <TD style="WIDTH: 8%" noWrap align=left>申请金额</TD>
          <TD style="WIDTH: 8%" noWrap align=left>审批金额</TD>
          <TD style="WIDTH: 8%" noWrap align=left>提交日期</TD>
          <TD style="WIDTH: 10%" noWrap align=left>单据类型</TD>
          <TD style="WIDTH: 9%" noWrap align=left>审批状态</TD>
          <TD style="WIDTH: 10%" noWrap align=left>摘要</TD>
  </TR>

  
  <% ArrayList<DClaim> claimDetail = (ArrayList<DClaim>)request.getAttribute("claimDetail");
   if(!claimDetail.isEmpty())
   {  
  %>
       <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1 type=hidden name=itemNum value=<%=claimDetail.size()%>>
       <%
       for(int index = 0; index < claimDetail.size(); index++)
       {
       %>
    <TR class=item>
    <TD align=middle><INPUT id=select<%=index%> type=checkbox></TD>
    <TD noWrap>
         <a href="finInvoiceDetail.do?displayFlag=1&InvoiceNoDe=<%= claimDetail.get(index).getInvoiceNo()%>">
            <span id=InvoiceNo<%=index%>><%= claimDetail.get(index).getInvoiceNo()%></span>
         </a>
    </TD>
    <TD noWrap id=No<%=index%>><%= claimDetail.get(index).getNo()%></TD>
    <input  id=baoxiaoNo<%=index%> name=baoxiaoNo<%=index%> value=<%= claimDetail.get(index).getNo()%> type="hidden" style="border: none; width: 200px" />
    <TD noWrap id=employeeName<%= claimDetail.get(index).getNo()%><%=index%>>
        <%= userInfoMap.get(claimDetail.get(index).getNo()).getName()%>
    </TD>
    <TD noWrap id=DeptId<%= claimDetail.get(index).getDeptId()%><%=index%>>
        <script>deptId2Name(<%= claimDetail.get(index).getDeptId()%>)</script>
    </TD>    
    <TD noWrap id=ProductId<%= claimDetail.get(index).getProductId()%><%=index%>>
        <script>productId2Name(<%= claimDetail.get(index).getProductId()%>)</script>
    </TD>    
    <TD noWrap id=TotalFee<%=index%>><%= claimDetail.get(index).getTotalFee()%>
        <input  id=totalFeeT<%=index%> name=totalFeeT<%=index%> value=<%= claimDetail.get(index).getTotalFee()%> type="hidden" style="border: none; width: 200px" />   
    </TD>
    <TD noWrap id=ApprovalAmount<%=index%>>
        <% 
            int apprAmount = 0;
            if(claimDetail.get(index).getApprovalAmount() == 0)
            {
                 apprAmount = (int)claimDetail.get(index).getTotalFee();
            }
            else
            {
                 apprAmount = (int)claimDetail.get(index).getApprovalAmount();
            }
        %>
        <INPUT style="WIDTH: 85px;HEIGHT: 24px" id=txtApprovalAmount<%=index%> name=txtApprovalAmount<%=index%> value=<%=apprAmount%>>
    </TD>
    <TD noWrap id=SubmitDate<%=index%>>
    <%
      Timestamp  timestamp = claimDetail.get(index).getSubmitDate();
      String dateStdr = "";
      dateStdr = (timestamp.getYear()+1900)+ "-" + (timestamp.getMonth()+1)+ "-" + timestamp.getDate();
    %>
    <%= dateStdr%>
    </TD>
    <TD noWrap id=InvoiceType<%=index%>>
        <%
            String invoiceType = courseMap.get(claimDetail.get(index).getInvoiceType());
        %>
        <%= invoiceType %>
        <input  id=invoiceTypeNo<%=index%> name=invoiceTypeNo<%=index%> value=<%= claimDetail.get(index).getInvoiceType()%> type="hidden" style="border: none; width: 200px" />        
    </TD>
    <TD noWrap id=Status<%=index%>>
        <%
            String statusStr[] = {"草稿","待项目经理审批","待所长审批", "待财务审批", "正常关闭", "已被退回", "已撤销", "待总经理审批"};
            String appStatus = statusStr[claimDetail.get(index).getStatus()];
        %>
        <%= appStatus %>
    </TD>
    <TD noWrap id=Summary<%=index%>><%= claimDetail.get(index).getSummary()%></TD>
    </TR>
    <%
       }
    }
    else
    {
    %>
    <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1 type=hidden name=itemNum value=0>
    <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>对不起，没有符合条件的记录! </FONT></TD>
    </TR>
    <%
    }
    %>
  </TBODY>
</TABLE>
</DIV>

  <script>
     var pager = new Pager("adminApproval","tr_Head");
     AllCheck("chk_SelAll",pager,function(i)
     {
         document.getElementById("select" + (i)).checked = document.getElementById("chk_SelAll").checked
     });      
  </script> 
  
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
<TBODY>
  <TR>
  <TD align=right>
      <DIV>
      <INPUT id=btnApproval class=stbtm01 value=批准 type=button name=btnApproval onclick="approval(1)"> 
      <INPUT id=btnNoApproval class=stbtm01 value=退回 type=button name=btnNoApproval onclick="approval(0)" > 
      </DIV>
   </TD>
  </TR>
</TBODY>
</TABLE>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
  <TR>
    <TD align=right></TD></TR>
  <TR>
    <TD style="HEIGHT: 25px" align=right></TD></TR></TBODY></TABLE><INPUT 
id=hiResourceID value=1221 type=hidden name=hiResourceID><INPUT id=hiMenuPath 
type=hidden name=hiMenuPath><INPUT style="WIDTH: 31px; HEIGHT: 20px" 
id=hiCheckedAll value=全选 size=1 type=hidden name=hiCheckedAll>
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
   var itemNum = document.getElementById("itemNum").value;
   if(itemNum > 0)
   {
      document.getElementById("btnApproval").disabled = "";
      document.getElementById("btnNoApproval").disabled = "";
   }
   else
   {
      document.getElementById("btnApproval").disabled="true";
      document.getElementById("btnNoApproval").disabled="true";
   }
   
   var hiBoeNum = document.getElementById("hiBoeNum").value;
   if(hiBoeNum == -1 || hiBoeNum == "" || hiBoeNum == "null")
   {
       document.getElementById("txtBoeNum").value = "";
   }
   else
   {
       document.getElementById("txtBoeNum").value = hiBoeNum;
   }
   
   var hiBoeType = document.getElementById("hiBoeType").value;
   if(hiBoeType == -1 || hiBoeType == "" || hiBoeType == "null")
   {
       document.getElementById("txtBoeType").value = "";
   }
   else
   {
       document.getElementById("txtBoeType").value = hiBoeType;
   }
   
   var hiDeptId = document.getElementById("hiDeptId").value;
   if(hiDeptId == -1 || hiDeptId == "" || hiDeptId == "null")
   {
       document.getElementById("txtDeptName").value = "";
   }
   else
   {
       document.getElementById("txtDeptName").value = hiDeptId;
   }
   
   var hiEmployeeName = document.getElementById("hiEmployeeName").value;
   if(hiEmployeeName == -1 || hiEmployeeName == "" || hiEmployeeName == "null")
   {
       document.getElementById("txtEmployeeName").value = "";
   }
   else
   {
       document.getElementById("txtEmployeeName").value = hiEmployeeName;
   }
   
   var hiProductId = document.getElementById("hiProductId").value;
   if(hiProductId == -1 || hiProductId == "" || hiProductId == "null")
   {
       document.getElementById("txtProductName").value = "";
   }
   else
   {
       document.getElementById("txtProductName").value = hiProductId;
   }
 }
 
//页面初始化
init();

</SCRIPT>
 </FORM></BODY></HTML>
