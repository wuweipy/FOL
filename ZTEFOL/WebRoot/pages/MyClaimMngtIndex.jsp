<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Business.Claims.*"%>
<HTML>
<HEAD>
<TITLE>我报销的单据</TITLE>
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

<SCRIPT language=javascript src="MyProposerIndex_files/FbpLovSelect.js"></SCRIPT>

<SCRIPT language=javascript
	src="MyProposerIndex_files/AdminPageFunction.js"></SCRIPT>

<SCRIPT type=text/javascript src="pikaday.js"></SCRIPT>
<SCRIPT type=text/javascript src="Pager/Pager.js"></SCRIPT>
<SCRIPT type=text/javascript src="MyProposerIndex_files/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript
	src="MyProposerIndex_files/jquery.blockUI.js"></SCRIPT>

<SCRIPT type=text/javascript
	src="MyProposerIndex_files/itp_investigate.js"></SCRIPT>

<SCRIPT language=javascript type=text/javascript
	src="MyProposerIndex_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript>
	function clear_text() {
		document.getElementById("txtInvoiceNo").value = "";
		document.getElementById("deptID").value = "-1";
		document.getElementById("typeID").value = "-1";
		document.getElementById("txtSubmitDateFrom").value = "";
		document.getElementById("txtSubmitDateTo").value = "";
		document.getElementById("statusID").value = "-1";
		document.getElementById("txtBillNo").value = "";
		document.getElementById("txtPayFrom").value = "";
		document.getElementById("txtPayTo").value = "";
		return false;
	}

	function query() {
		if (check()) {
			Form1.action = "myClaim.do";
			Form1.submit();
		}
	}

	function checkIsValidDate(str) {
		var result;
		var ntemp;
		//如果为空，则通过校验
		if (str == "")
			return "";
		var pattern = /^((\d{4})|(\d{2}))-(\d{1,2})-(\d{1,2})$/g;
		if (!pattern.test(str))
			return "INVALID DATE";
	}

	function check() {
		var dateFrom = document.getElementById("txtSubmitDateFrom").value;
		var dateTo = document.getElementById("txtSubmitDateTo").value;
		var result = checkIsValidDate(dateFrom);
		if (result == "INVALID DATE") {
			alert("无效的开始日期值,请重新输入!");
			return false;
		}

		result = checkIsValidDate(dateTo);
		//alert(result);
		if (result == "INVALID DATE") {
			alert("无效的结束日期值,请重新输入!");
			return false;
		}
		if (dateTo < dateFrom) {
			alert("结束日期不能小于开始日期，请重新输入");
			return false;
		}

		var payFrom = document.getElementById("txtPayFrom").value;
		var payTo = document.getElementById("txtPayTo").value;
		if (payTo < payFrom) {
			alert("最大本币金额不能小于最小本币金额");
			return false;
		}

		return true;
	}

	function showBpHis(bpNum) {
		var resourceID = document.all("hiResourceID").value;
		var menuPath = document.all("hiMenuPath").value;
		var url = "../../BpManage/Pages/BpActiveHis.aspx?resourceID="
				+ resourceID + "&menuPath=" + menuPath;
		url += "&bpnum=" + bpNum + "&pflag=MyProposerIndex";
		//window.navigate(url);
		parent.ShowPage(0, url);
	}
</SCRIPT>
</HEAD>
<BODY>

	<FORM id=Form1 method=post name=Form1 action=myClaim.do>
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
					<TD><IMG class=icon src="MyProposerIndex_files/bullet0.gif"
						height=10> <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN
							class=11air>:</SPAN>与我有关<SPAN class=arrow_subtitle>&gt;</SPAN>我的单据<SPAN
							class=arrow_subtitle>&gt;</SPAN>我报销的
					</SPAN></TD>
					<TD width=16 noWrap align=right><IMG style="CURSOR: hand"
						id=TableHeadBar1_BarImg class=icon
						onclick=javascript:ExpandDiv(this);
						src="MyProposerIndex_files/icon_collapseall.gif" height=16
						data="1"></TD>
				</TR>
			</TBODY>
		</TABLE>
		<BR style="LINE-HEIGHT: 1px">
		<DIV style="WIDTH: 98%" id=divSearch>
			<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
				cellPadding=0 width="100%" align=center>
				<TBODY>
					<TR>
						<TD class=title nowrap="" height=24>单据号</TD>
						<TD><INPUT style="WIDTH: 170px; HEIGHT: 21px"
							id="txtInvoiceNo" name=txtInvoiceNo></TD>
						<TD class=title nowrap="" height=24>所属项目</TD>
						<TD><select id="deptID" name="deptID" style="WIDTH: 170px">
								<%
									HashMap<Integer, String> projects = (HashMap<Integer, String>) request.getAttribute("projects");
									Iterator<Integer> projectIterator = projects.keySet().iterator();
									while (projectIterator.hasNext()) {
										int key = projectIterator.next();
								%>
								<option selected="" value="<%=key%>"><%=projects.get(key)%></option>
								<%
									}
								%>
								<option selected="selected" value="-1"></option>
						</select></TD>
						<TD class=title nowrap="" height=24>单据类型</TD>
						<TD><select id="typeID" name="typeID" style="WIDTH: 170px">
								<%
									HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
									Iterator<Integer> typeIterator = courseMap.keySet().iterator();
									while (typeIterator.hasNext()) {
										int key = typeIterator.next();
								%>
								<option selected="" value="<%=key%>"><%=courseMap.get(key)%></option>
								<%
									}
								%>
								<option selected="selected" value="-1"></option>
						</select></TD>
					</TR>
					<TR>
						<TD class=title nowrap="" height=24>提交日期</TD>
						<TD><INPUT style="WIDTH: 76px; HEIGHT: 21px"
							id="txtSubmitDateFrom" name=txtSubmitDateFrom> 至 <INPUT
							style="WIDTH: 76px; HEIGHT: 21px" id="txtSubmitDateTo"
							name=txtSubmitDateTo></TD>
						<TD class=title nowrap="" height=24>单据状态</TD>
						<TD><select id="statusID" name="statusID"
							style="WIDTH: 170px">
								<%
									HashMap<Integer, String> statuses = (HashMap<Integer, String>) request.getAttribute("statuses");
									Iterator<Integer> statusIterator = statuses.keySet().iterator();
									while (statusIterator.hasNext()) {
										int key = statusIterator.next();
								%>
								<option selected="" value="<%=key%>"><%=statuses.get(key)%></option>
								<%
									}
								%>
								<option selected="selected" value="-1"></option>
						</select></TD>
						<TD class=title nowrap="" height=24>票据号码</TD>
						<TD><INPUT style="WIDTH: 170px; HEIGHT: 21px" id="txtBillNo"
							name=txtBillNo></TD>
					</TR>
					<TR>
						<TD class=title nowrap="" height=24>本币金额</TD>
						<TD><INPUT style="WIDTH: 76px; HEIGHT: 21px" id=txtPayFrom
							name=txtPayFrom> 至 <INPUT
							style="WIDTH: 76px; HEIGHT: 21px" id=txtPayTo name=txtPayTo>
						</TD>
					</TR>
					<TR>
						<TD class=bottom colSpan=6 align=middle><INPUT id=hiDeptID
							size=1 type=hidden name=hiDeptID> <INPUT id=hiUserID
							size=1 type=hidden name=hiUserID> &nbsp; <INPUT
							id=btnQuery class=stbtm01 value=查询 type=button name=btnQuery
							onclick="query()"> <INPUT id=hiEmployeeID size=1
							type=hidden name=hiEmployeeID> <INPUT id=hiProdProjectID
							size=1 type=hidden name=hiProdProjectID> <INPUT
							id=btnClear language=javascript class=stbtm01
							onclick="javascript:return clear_text();" value=清空 type=button
							name=btnClear> <INPUT style="WIDTH: 31px; HEIGHT: 20px"
							id=hiCulture value=zh-CN size=1 type=hidden name=hiCulture>
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
				<TD class=tb_titlebar colSpan=6 align=left><IMG
					src="MyProposerIndex_files/bullet1.gif" width=16 height=16> <SPAN
					id=lblApprovalList>审批列表</SPAN></TD>
			</TR>
		</TBODY>
	</TABLE>
	<DIV style="WIDTH: 98%;" class=autoOver2 align=center>

		<TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval class=datatbl
			border=1 rules=all cellSpacing=0>
			<TBODY>
				<TR class=head id="trdgApprovalHead">
					<TD style="WIDTH: 5%" noWrap align=left>单据号</TD>
					<TD style="WIDTH: 5%" noWrap align=left>报销人</TD>
					<TD style="WIDTH: 6%" noWrap align=left>所属部门</TD>
					<TD style="WIDTH: 5%" noWrap align=left>报销项目</TD>
					<TD style="WIDTH: 5%;display: none;" noWrap align=left>提交日期</TD>
					<TD style="WIDTH: 5%" noWrap align=left>单据类型</TD>
					<TD style="WIDTH: 5%" noWrap align=left>审批状态</TD>
					<TD style="WIDTH: 5%" noWrap align=left>审批结束日期</TD>
					<TD style="WIDTH: 5%;display: none;" noWrap align=left>票据号码</TD>
					<TD style="WIDTH: 5%" noWrap align=left>申请金额</TD>
					<TD style="WIDTH: 5%" noWrap align=left>审核金额</TD>
					<TD style="WIDTH: *" noWrap align=left>摘要</TD>
				</TR>

				<%
					ArrayList<BMyClaim> claimList = (ArrayList<BMyClaim>) request.getAttribute("claimInfos");
					if (claimList.size() == 0) {
				%>
				<TR class=item>
					<TD width="100%" align=left colspan="12"><FONT color=red>对不起，没有符合条件的记录!
					</FONT></TD>
				</TR>
				<%
					} else {
						for (int i = 0; i < claimList.size(); i++) {
							BMyClaim claimInfo = claimList.get(i);
				%>
				<TR class=item>
					<TD noWrap align=left><a
						href="finInvoiceDetail.do?displayFlag=0&InvoiceNoDe=<%=claimInfo.getInvoiceNo()%>"><%=claimInfo.getInvoiceNo()%></a>
					</TD>
					<TD noWrap align=left><%=request.getAttribute("userName")%></TD>
					<TD noWrap align=left><%=claimInfo.getDeptName()%></TD>
					<TD noWrap align=left><%=claimInfo.getProductName()%></TD>
					<TD noWrap align=left style="display: none;"><%=claimInfo.getSubmitDate().toString().substring(0, 16)%>
					</TD>
					<TD noWrap align=left><%=courseMap.get(claimInfo.getPayType())%>
					</TD>
					<TD noWrap align=left><%=claimInfo.getStatusStr()%></TD>
					<TD noWrap align=left>
						<%
							if (claimInfo.getEndDate() != null && !claimInfo.getEndDate().equals("")) {
						%> <%=claimInfo.getEndDate().substring(0, 16)%> <%
 	}
 %>
					</TD>
					<TD noWrap align=left style="display: none;"><%=claimInfo.getBillNo()%></TD>
					<TD noWrap align=left><%=claimInfo.getTotalFee()%></TD>
					<TD noWrap align=left><%=claimInfo.getCheckFee()%></TD>
					<TD  align=left><%=claimInfo.getSummary()%></TD>

				</TR>
				<%
					}
					}
				%>
			</TBODY>
		</TABLE>

	</DIV>

</BODY>
<script type="text/javascript">
	var pickerFrom = new Pikaday({
		field : document.getElementById("txtSubmitDateFrom"),
		firstDay : 1,
		minDate : new Date('2000-01-01'),
		maxDate : new Date('2030-12-31'),
		yearRange : [ 2000, 2030 ]
	});

	var pickerTo = new Pikaday({
		field : document.getElementById("txtSubmitDateTo"),
		firstDay : 1,
		minDate : new Date('2000-01-01'),
		maxDate : new Date('2030-12-31'),
		yearRange : [ 2000, 2030 ]
	});

	var pager = new Pager("dgApproval", "trdgApprovalHead");
</script>
</HTML>
