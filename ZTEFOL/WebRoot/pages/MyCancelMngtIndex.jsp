<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Business.Claims.*"%>
<HTML>
	<HEAD>
		<TITLE>我撤销的单据</TITLE>
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		<META name=GENERATOR content="MSHTML 8.00.6001.23501">
		<META name=CODE_LANGUAGE content=C#>
		<META name=vs_defaultClientScript content=JavaScript>
		<META name=vs_targetSchema
			content=http://schemas.microsoft.com/intellisense/ie5>
		<LINK rel=stylesheet type=text/css
			href="MyProposerIndex_files/style.css">
		<LINK rel=stylesheet type=text/css href="css/pikaday.css">
		<LINK rel=stylesheet type=text/css href="Pager/Page.css">
		<SCRIPT language=javascript src="MyProposerIndex_files/CommonFun.js"></SCRIPT>

		<SCRIPT language=javascript
			src="MyProposerIndex_files/FbpLovSelect.js"></SCRIPT>

		<SCRIPT language=javascript
			src="MyProposerIndex_files/AdminPageFunction.js"></SCRIPT>

		<SCRIPT type=text/javascript src="pikaday.js"></SCRIPT>
		<SCRIPT type=text/javascript src="Pager/Pager.js"></SCRIPT>
		<SCRIPT type=text/javascript src="Pager/AllCheck.js"></SCRIPT>
		<SCRIPT type=text/javascript src="MyProposerIndex_files/jquery.js"></SCRIPT>

		<SCRIPT type=text/javascript
			src="MyProposerIndex_files/jquery.blockUI.js"></SCRIPT>

		<SCRIPT type=text/javascript
			src="MyProposerIndex_files/itp_investigate.js"></SCRIPT>

		<SCRIPT language=javascript type=text/javascript
			src="MyProposerIndex_files/CommLovFun.js"></SCRIPT>

		<SCRIPT language=javascript>
		function clear_text()
		{
			document.getElementById("txtInvoiceNo").value="";
			document.getElementById("typeID").value="-1";
			document.getElementById("txtSubmitDateFrom").value="";
			document.getElementById("txtSubmitDateTo").value="";
			//document.getElementById("txtEmployeeName").value="";
			return false;
		}
		
		function query()
        {
        	if(check())
			{
	        	Form1.action = "myCancel.do";
	        	Form1.submit();
			}
        }
        
        function toCancel()
    {
        var appFlag = 0;
        var itemNum = document.getElementById("itemNum").value;
        var selectedCount = 0;
        var varStr = "action=cancel&InvoiceNo=";
        for(var selIndex=0; selIndex<itemNum; selIndex++)
        {
            if(document.getElementById("select"+selIndex).checked == true)
            {
                varStr = varStr+";"+ document.getElementById("InvoiceNo"+selIndex).innerHTML;
                selectedCount ++;
            }
        }
                
        if(selectedCount>0)
        {
            if(confirm("您确认要撤销吗?"))
            {
                Form1.action = "myCancel.do?" + varStr;
                Form1.submit();
            }
        }
        else
        {
            alert("您至少需要选择一条记录");
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
        	if( dateTo < dateFrom)
        	{
        	    alert("结束日期不能小于开始日期，请重新输入");
        	    return false;
        	}
        	
        	return true;   
        }
		     
		
		function showBpHis(bpNum)
		{
		    var resourceID=document.all("hiResourceID").value;
			var menuPath=document.all("hiMenuPath").value;
			var url="../../BpManage/Pages/BpActiveHis.aspx?resourceID="+resourceID+"&menuPath="+menuPath;
			url+="&bpnum="+bpNum + "&pflag=MyProposerIndex";
		    //window.navigate(url);
		    parent.ShowPage(0,url);
		}
        
        
         <%if (request.getAttribute("cancelFail") != null) {
		     if ((Boolean) request.getAttribute("cancelFail")) {%>
         	 alert("撤销过程中出错，请选择需要撤销的单据"); 
             <%} 
		   }%> 
    </SCRIPT>
	</HEAD>
	<BODY>

		<FORM id=Form1 method=post name=Form1 action=myCancel.do>
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
			<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0
				align=center>
				<TBODY>
					<TR height=24>
						<TD>
							<IMG class=icon src="MyProposerIndex_files/bullet0.gif" height=10>
							<SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN
								class=arrow_subtitle>&gt;</SPAN>我的单据<SPAN class=arrow_subtitle>&gt;</SPAN>我要撤销</SPAN>
						</TD>
						<TD width=16 noWrap align=right>
							<IMG style="CURSOR: hand" id=TableHeadBar1_BarImg class=icon
								onclick=javascript:ExpandDiv(this);
								src="MyProposerIndex_files/icon_collapseall.gif" height=16
								data="1">
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<BR style="LINE-HEIGHT: 1px">
			<DIV style="WIDTH: 98%" id=divSearch>
				<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
					cellPadding=0 width="100%" align=center>
					<TBODY>
						<TR>
							<TD class=title nowrap="" height=24>
								单据号
							</TD>
							<TD>
								<INPUT style="WIDTH: 170px; HEIGHT: 21px" id="txtInvoiceNo"
									name=txtInvoiceNo>
							</TD>
							
							<TD class=title nowrap="" height=24>
								单据类型
							</TD>
							<TD>
								<select id="typeID" name="typeID" style="WIDTH: 170px">
									<%    
        HashMap<Integer, String> typeInfoMap = (HashMap<Integer, String>)request.getAttribute("TypeInfos");
        Iterator<Integer> typeIterator = typeInfoMap.keySet().iterator();
  	    while(typeIterator.hasNext()) { 
    		   int key = typeIterator.next();
    %>
									<option selected="" value="<%= key %>"><%= typeInfoMap.get(key)%></option>
									<%
     }
   %>
									<option selected="selected" value="-1"></option>
								</select>
							</TD>
							<!--<TD class=title nowrap="" height=24>
								报销人
							</TD>
							<TD>
								<INPUT style="WIDTH: 170px; HEIGHT: 21px" id="txtEmployeeName"
									name=txtEmployeeName>
							</TD>  -->
						</TR>
						<TR>
							<TD class=title nowrap="" height=24>
								提交日期
							</TD>
							<TD>
								<INPUT style="WIDTH: 76px; HEIGHT: 21px" id="txtSubmitDateFrom"
									name=txtSubmitDateFrom>
								至
								<INPUT style="WIDTH: 76px; HEIGHT: 21px" id="txtSubmitDateTo"
									name=txtSubmitDateTo>
							</TD>
							
							
						</TR>
						<TR>
							<TD class=bottom colSpan=6 align=middle>
								<INPUT id=hiDeptID size=1 type=hidden name=hiDeptID>
								<INPUT id=hiUserID size=1 type=hidden name=hiUserID>
								&nbsp;
								<INPUT id=btnQuery class=stbtm01 value=查询 type=button
									name=btnQuery onclick="query()">
								<INPUT id=hiEmployeeID size=1 type=hidden name=hiEmployeeID>
								<INPUT id=hiProdProjectID size=1 type=hidden
									name=hiProdProjectID>
								<INPUT id=btnClear language=javascript class=stbtm01
									onclick="javascript:return clear_text();" value=清空 type=button
									name=btnClear>
								<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture
									value=zh-CN size=1 type=hidden name=hiCulture>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
		</FORM>
		<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0
			width="98%" bgColor=#ffffff>
			<TBODY>
				<TR>
					<TD class=tb_titlebar colSpan=6 align=left>
						<IMG src="MyProposerIndex_files/bullet1.gif" width=16 height=16>
						<SPAN id=lblApprovalList>审批列表</SPAN>
					</TD>
					<TD>
					<INPUT id=btnCancel class=stbtm01 value=撤销  type=button name=btnCancel onclick="toCancel()"> 
                    
					</TD>
					
				</TR>
			</TBODY>
		</TABLE>
		<DIV style="WIDTH: 98%;" class=autoOver2 align=center>
			
							<TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval
								class=datatbl border=1 rules=all cellSpacing=0>
								<TBODY>
									<TR class=head id="trdgApprovalHead">
									<TD style="WIDTH: 2%" align=middle>
             <INPUT id=chk_SelAll type=checkbox> 
            <LABEL>全选 </LABEL>
          </TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											单据号
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											报销部门
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											报销人
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											所属项目
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											提交日期
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											单据类型
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											审批状态
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											审批结束日期
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											票据号码
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											摘要
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											申请金额
										</TD>
									</TR>

									<%    
          ArrayList<BMyClaim> claimList = (ArrayList<BMyClaim>)request.getAttribute("claimInfos");
         if(claimList.size()==0)
          {
           %>
           <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1 type=hidden name=itemNum value=0>
									<TR class=item>
										<TD width="100%" align=left colspan="12">
											<FONT color=red>对不起，没有符合条件的记录! </FONT>
										</TD>
									</TR>
									<%
          }
          else{
          %>
       <INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1 type=hidden name=itemNum value=<%=claimList.size()%>>
       <%
          for(int i = 0; i < claimList.size(); i++){
      		   BMyClaim claimInfo = claimList.get(i);
       %>
									<TR class=item>
									
									    <TD style="WIDTH: 2%" align=middle><INPUT id=select<%=i%> type=checkbox></TD>
										<TD noWrap align=left id=InvoiceNo<%=i%>>
											<%= claimInfo.getInvoiceNo()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getDeptName()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getName()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getProductName()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getSubmitDate()%>
										</TD>
										<TD noWrap align=left>
											<%= typeInfoMap.get(claimInfo.getPayType())%>
										</TD>
										<TD noWrap align=left>
											待<%= claimInfo.getStatusStr()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getEndDate()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getBillNo()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getSummary()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getTotalFee()%>
										</TD>

									</TR>
									<%
           }}
         %>
								</TBODY>
							</TABLE>
						
		</DIV>
		
	</BODY>
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
	
	var pager = new Pager("dgApproval","trdgApprovalHead");
	 AllCheck("chk_SelAll",pager,function(i)
     {
         document.getElementById("select" + (i)).checked = document.getElementById("chk_SelAll").checked
     });  
	
</script>
</HTML>
