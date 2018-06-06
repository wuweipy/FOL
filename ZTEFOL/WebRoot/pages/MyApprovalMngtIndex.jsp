<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Business.Claims.*"%>
<HTML>
<HEAD>
<TITLE>我审批的单据</TITLE>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
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
		document.getElementById("txtPayFrom").value = "";
		document.getElementById("txtPayTo").value = "";
		document.getElementById("dplApprovalFlag").value = "-1";
		document.getElementById("txtEmployeeName").value = "";
		return false;
	}

	function query() {
		if (check()) {
			Form1.action = "myApproval.do?action=query";
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

		var ass, aD, aS;
		var bss, bD, bS;
		ass = dateFrom.split("-");//以"-"分割字符串，返回数组；
		aD = new Date(ass[0], ass[1] - 1, ass[2]);//格式化为Date对像;
		aS = aD.getTime();
		bss = dateTo.split("-");
		bD = new Date(bss[0], bss[1] - 1, bss[2]);
		bS = bD.getTime();
		if (aS > bS) {
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
<script>
	function getTableColumnValue(tableId, rowNumber, columnNumber) {
		var tableRef = document.getElementById(tableId);

		var elementRef = tableRef.rows[rowNumber].cells[columnNumber];
		var elementValue = '';

		if (elementRef.textContent) {
			// Firefox
			elementValue = elementRef.textContent;
		} else if (elementRef.innerText) {
			// IE
			elementValue = elementRef.innerText;
		} else {
			// Default
			elementValue = elementRef.innerHTML;
			var regExp = /<\/?[^>]+>/gi;

			elementValue = elementValue.replace(regExp, '');
		}

		//alert(elementValue);

		return elementValue;
	}

	function daochu(tableId) {
		var table = document.getElementById(tableId);
		//行数
		var rows = table.rows.length;
		//列数
		var colums = table.rows[0].cells.length;

		var myArray = new Array()
		for (var i = 0; i < rows; i++) {
			var oneline = getTableColumnValue(tableId, i, 0);
			for (var j = 1; j < colums; j++) {
				oneline = oneline + ',' + getTableColumnValue(tableId, i, j);
			}
			;
			myArray[i] = oneline;
		}
		doSubmit(myArray, colums, rows);
	}

	function doSubmit(myArray, colums, rows) {
		var urlParameter = "myArray=" + myArray + "&colums=" + colums
				+ "&rows=" + rows;

		if (typeof XMLHttpRequest != 'undefined') {
			httpRequest3 = new XMLHttpRequest();
		} else if (typeof ActiveXObject != 'undefined') {
			httpRequest3 = new ActiveXObject('Microsoft.XMLHTTP');
		}
		if (httpRequest3) {
			httpRequest3.open('POST', "/FOL/pages/exportInvoice.do", true);//true为异步
			httpRequest3.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded');
			httpRequest3.onreadystatechange = onComplete;
			httpRequest3.send(urlParameter);
		}

		return;
	}
	function onComplete() {
		if (4 == httpRequest3.readyState) {
			//alert(httpRequest3.responseText);
			if (200 == httpRequest3.status) {
				var retText = httpRequest3.responseText;
				self.location = '/FOL/download/' + retText;
			}
		}
	}
</script>
</HEAD>
<%
	HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
	Iterator<Integer> typeIterator = courseMap.keySet().iterator();

	HashMap<Integer, String> deptInfoMap = (HashMap<Integer, String>) request.getAttribute("DeptInfos");
	Iterator<Integer> deptIterator = deptInfoMap.keySet().iterator();

	HashMap<Integer, String> statuses = (HashMap<Integer, String>) request.getAttribute("statuses");
	Iterator<Integer> statusesIterator = statuses.keySet().iterator();
%>
<BODY>

	<FORM id=Form1 method=post name=Form1 action=myApproval.do>
		<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
			<TBODY>
				<TR height=24>
					<TD><IMG class=icon src="MyProposerIndex_files/bullet0.gif"
						height=10> <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN
							class=11air>:</SPAN>与我有关<SPAN class=arrow_subtitle>&gt;</SPAN>我的单据<SPAN
							class=arrow_subtitle>&gt;</SPAN>我审批的
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

				<tbody>
					<tr>
						<td colspan="4"><img class="icon" src="images/search.gif">
							请填写查询条件</td>
					</tr>
					<tr>
						<td class="title" width="100"><span id="lblBoeNum">单据号</span>
						</td>
						<td><input name="txtInvoiceNo" id="txtInvoiceNo"
							style="width: 159px;" type="text"></td>
						<td class="title" width="160"><span id="lblBoeType">单据类型</span>
						</td>
						<td><select id="typeID" name="typeID" style="WIDTH: 159px">
								<option selected="selected" value="-1"></option>
								<%
									while (typeIterator.hasNext()) {
										int keyT = typeIterator.next();
								%>
								<option value="<%=keyT%>"><%=courseMap.get(keyT)%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>
						<td class="title" style="height: 24px;"><span id="lblDept">部门</span>
						</td>
						<td style="height: 24px;"><select id="deptID" name="deptID"
							style="WIDTH: 159px">
								<option selected="selected" value="-1"></option>
								<%
									while (deptIterator.hasNext()) {
										int keyD = deptIterator.next();
								%>
								<option value="<%=keyD%>"><%=deptInfoMap.get(keyD)%></option>
								<%
									}
								%>
						</select></td>
						<td class="title" style="height: 24px;"><span id="lblBoeDate">提交日期</span>
						</td>
						<td><INPUT style="WIDTH: 70px; HEIGHT: 21px"
							id="txtSubmitDateFrom" name=txtSubmitDateFrom> 至 <INPUT
							style="WIDTH: 70px; HEIGHT: 21px" id="txtSubmitDateTo"
							name=txtSubmitDateTo></td>
					</tr>
					<tr>
						<td class="title" style="height: 24px;"><span
							id="lblApprovalFlag">审批意见</span></td>
						<td style="height: 24px;"><select name="dplApprovalFlag"
							id="dplApprovalFlag" style="width: 159px;">
								<option selected="selected" value="-1"></option>
								<option value="0">拒绝</option>
								<option value="1">同意OK</option>

						</select></td>
						<td class="title" style="height: 24px;"><span
							id="lblBoeStatus">单据状态</span></td>
						<td style="height: 24px;"><select id="statusID"
							name="statusID" style="height: 21px; width: 159px;">
								<option selected="selected" value="-1"></option>
								<%
									while (statusesIterator.hasNext()) {
										int keyS = statusesIterator.next();
								%>
								<option value="<%=keyS%>"><%=statuses.get(keyS)%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>

						<td class="title" style="height: 24px;">本币金额</td>
						<td><input name="txtPayFrom" id="txtPayFrom" type="text"
							validate="DOUBLE2" style="width: 70px;"> 至 <input
							name="txtPayTo" id="txtPayTo" type="text" validate="DOUBLE2"
							style="width: 70px;"></td>
						<td class="title" style="height: 24px;">报销人</td>
						<td style="height: 24px;"><input name="txtEmployeeName"
							id="txtEmployeeName" style="width: 159px;" type="text"></td>
					</tr>
					<tr>
						<td class=bottom colspan="4" align="middle"><INPUT
							id=hiDeptID size=1 type=hidden name=hiDeptID> <INPUT
							id=hiUserID size=1 type=hidden name=hiUserID> &nbsp; <INPUT
							id=btnQuery class=stbtm01 value=查询 type=button name=btnQuery
							onclick="query()"> <INPUT id=hiEmployeeID size=1
							type=hidden name=hiEmployeeID> <INPUT id=hiProdProjectID
							size=1 type=hidden name=hiProdProjectID> <INPUT
							id=btnClear language=javascript class=stbtm01
							onclick="javascript:return clear_text();" value=清空 type=button
							name=btnClear> <input type="button" class=stbtm01
							onclick="daochu('dgApproval');" value="导出" /></td>
					</tr>
				</tbody>
			</table>
		</div>
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
				<TR class="head" id="trHeadOfdgApproval">
					<TD style="WIDTH: 5%" noWrap align=left>单据号</TD>
					<TD style="WIDTH: 5%" noWrap align=left>所属部门</TD>
					<TD style="WIDTH: 5%" noWrap align=left>报销人</TD>
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
					if (claimList == null || claimList.size() == 0) {
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
						href="finInvoiceDetail.do?displayFlag=0&InvoiceNoDe=<%=claimInfo.getInvoiceNo()%>&deptId=<%=claimInfo.getDeptId()%>&productId=<%=claimInfo.getProductId()%>"><%=claimInfo.getInvoiceNo()%></a>
					</TD>
					<TD noWrap align=left><%=claimInfo.getDeptName()%></TD>
					<TD noWrap align=left><%=claimInfo.getName()%></TD>
					<TD noWrap align=left><%=claimInfo.getProductName()%></TD>
					<TD noWrap align=left style="display: none;"><%=claimInfo.getSubmitDate()%></TD>
					<TD noWrap align=left><%=courseMap.get(claimInfo.getPayType())%>
					</TD>
					<TD noWrap align=left><%=claimInfo.getStatusStr()%></TD>
					<TD noWrap align=left><%=claimInfo.getEndDate()%></TD>
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

	var pager = new Pager("dgApproval", "trHeadOfdgApproval");
</script>
</HTML>
