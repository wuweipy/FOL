<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java"
	import="java.util.*,Data.Claims.*,Data.Common.*,Data.UserInfo.*,java.lang.*,View.ParameterUtil,java.sql.Timestamp"%>
<HTML>
<HEAD>
<TITLE>审批</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema
	content=http://schemas.microsoft.com/intellisense/ie5>
<META content=IE=EmulateIE7 http-equiv=X-UA-Compatible>
<LINK rel=stylesheet type=text/css
	href="CountersignIndex_files/style.css">
<LINK rel=stylesheet type=text/css href="Pager/Page.css">
<SCRIPT language=javascript src="AdminApprovalIndex_files/CommonFun.js"></SCRIPT>
<SCRIPT language=javascript src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>
<SCRIPT language=javascript src="js/common.js"></SCRIPT>
<SCRIPT language=javascript
	src="AdminApprovalIndex_files/FbpLovSelect.js"></SCRIPT>
<SCRIPT language=javascript
	src="AdminApprovalIndex_files/AdminPageFunction.js"></SCRIPT>
<SCRIPT type=text/javascript src="Pager/AllCheck.js"></SCRIPT>
<SCRIPT type=text/javascript src="Pager/Pager.js"></SCRIPT>
<SCRIPT type=text/javascript src="AdminApprovalIndex_files/Calendar.js"></SCRIPT>
<SCRIPT language=javascript src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>
<SCRIPT language=javascript>
	function init2() {
		var display = "${displayFlag}";
		if (display == 1) {
			document.getElementById("txtApprovalAmount").disabled = false;
		} else if (display == 0) {
			document.getElementById("txtApprovalAmount").disabled = true;
		}
	}
	//init();
	function query() {
		var str = "";
		var boeNum = document.getElementById("txtBoeNum").value;
		var boeType = document.getElementById("txtBoeType").value;
		var deptId = document.getElementById("txtDeptName").value;
		var productId = document.getElementById("txtProductName").value;
		var employeeName = document.getElementById("txtEmployeeName").value;

		if (boeNum == "") {
			str += "&invoiceNo=-1";
		} else {
			str += "&invoiceNo=" + boeNum;
		}
		if (boeType == "") {
			str += "&invoiceType=-1";
		} else {
			str += "&invoiceType=" + boeType;
		}
		if (deptId == "") {
			str += "&deptId=-1";
		} else {
			str += "&deptId=" + deptId;
		}
		if (employeeName == "") {
			str += "&employeeName=-1";
		} else {
			str += "&employeeName=" + employeeName;
		}
		if (productId == "") {
			str += "&productId=-1";
		} else {
			str += "&productId=" + productId;
		}

		Form1.action = "adminApproval.do?action=query" + str;
		Form1.submit();
	}

	function approval(flag) {
		var appFlag = 0;
		var itemNum = document.getElementById("itemNum").value;
		var selectedCount = 0;
		var varStr = "action=approval";
		for (var selIndex = 0; selIndex < itemNum; selIndex++) {
			if (document.getElementById("select" + selIndex).checked == true) {
				varStr += "&InvoiceNo" + selectedCount + "=";
				varStr += document.getElementById("InvoiceNo" + selIndex).innerHTML;
				varStr += "&invoiceType" + selectedCount + "=";
				varStr += document.getElementById("invoiceType" + selIndex).value;
				varStr += "&TotalFee" + selectedCount + "=";
				varStr += document.getElementById("TotalFee" + selIndex).value;
				varStr += "&productId" + selectedCount + "=";
				varStr += document.getElementById("ProductId" + selIndex).value;
				varStr += "&subproductId" + selectedCount + "=";
				varStr += document.getElementById("SubProductId" + selIndex).value;
				varStr += "&ApprovalFlowId" + selectedCount + "=";
				varStr += document.getElementById("ApprovalFlowId" + selIndex).value;
                varStr += "&ApprovalAmount"+selectedCount+"=";
                varStr += document.getElementById("txtApprovalAmount"+selIndex).value;
				selectedCount++;
			}
		}

		varStr += "&selectedCount=" + selectedCount;
		if (selectedCount > 0) {
			if (flag == 1) {
				varStr += "&flag=" + 1;
				if (confirm("您确认要批准吗?")) {
					appFlag = 1;
				}
			} else if (flag == 0) {
				varStr += "&flag=" + 0;
				if (confirm("您确认要退回吗?")) {
					appFlag = 1;
				}
			}
		} else {
			alert("您至少需要选择一条记录");
		}

		if (appFlag == 1) {
			window.top.needAlert = true;
			Form1.action = "adminApproval.do?" + varStr;
			Form1.submit();
		}
	}

	function deptId2Name(id) {
		var itemNum = document.getElementById("itemNum").value;
		for (var i = 0; i < itemNum; i++) {
			var obj = document.getElementById("DeptId" + id + i);
			if (obj != null) {
				obj.innerHTML = document.getElementById("hiDept" + id).value;
			}
		}
	}

	function productId2Name(index) {
		var itemNum = document.getElementById("itemNum").value;
		for (var i = 0; i < itemNum; i++) {
			var obj = document.getElementById("ProductId" + id + i);
			if (obj != null) {
				obj.innerHTML = document.getElementById("hiProduct" + id).value;
			}
		}
	}

	function showBoeList(boeID, boeNum) {
		var resourceID = document.all("hiResourceID").value;
		var menuPath = document.all("hiMenuPath").value;
		var url = "AdiminApprovalList.aspx?resourceID=" + resourceID
				+ "&menuPath=" + menuPath;
		url += "&boeID=" + boeID + "&boenum=" + boeNum + "&approvaltype=admin";
		var localUrl = location.href;
		if (localUrl.indexOf("uniteappr") > 0) {
			url += "&uniteappr=admin"; // 统一审批 - 业务审批
		}
		if (parent != null && parent.ShowPage != null) {
			url += "&hasframe=1";
			parent.ShowPage(0, url);
		} else {
			window.location.href = url;
		}
	}

	function clear_text() {
		document.all("txtBoeNum").value = "";
		document.all("txtBoeType").value = "";
		document.all("txtDeptName").value = "";
		document.all("txtProductName").value = "";
		document.all("txtEmployeeName").value = "";
		document.all("hiBoeNum").value = "";
		document.all("hiBoeType").value = "";
		document.all("hiEmployeeName").value = "";
		return false;
	}
	function setClickFlag() {
		var sflag = document.all("hiClickFlag").value;
		if (sflag != "") {
			return false;
		} else {
			document.all("hiClickFlag").value = "ClickBtn";
			return true;
		}
	}

	function CheckDate() {
		var dateStart = document.all("txtStartDate");
		var dateEnd = document.all("txtEndDate");

		// 到期日日期格式交验
		if (dateStart != null && dateEnd != null && dateStart.value != ""
				&& dateEnd.value != "") {
			if (dateStart.value > dateEnd.value) {
				alert('结束日期不能小于开始日期！');

				dateStart.value = "";
				dateEnd.value = "";

				return false;
			}
		}
		return true;

	}
</SCRIPT>

<script>
	if (window.top.needAlert) {
		window.top.needAlert = false;
		var chao = "${chao}";
		var notPermit = "${notPermit}".replace('[', '').replace(']', '').split(
				",");
		var notPermitNo = "";
		if (chao == 1) {
			if (notPermit != null && notPermit.length != 0) {
				for (i = 0; i < notPermit.length; i++) {
					notPermitNo += notPermit[i].trim();
				}
				notPermitNo = "以下单号审批失败:" + notPermitNo + "超出预算或者尚未提交预算";
				alert(notPermitNo);
			} else {
				alert("审批失败，超出预算或者尚未提交预算");
			}
		}
	}
</script>

</HEAD>
<BODY>
	<FORM id=Form1 method=post name=Form1 action=adminApproval.do>
		<INPUT id=hiBoeNum size=1 type=hidden name=hiBoeNum
			value=<%=request.getParameter("invoiceNo")%>> <INPUT
			id=hiBoeType size=1 type=hidden name=hiBoeType
			value=<%=request.getParameter("invoiceType")%>> <INPUT
			id=hiDeptId size=1 type=hidden name=hiDeptId
			value=<%=request.getParameter("deptId")%>> <INPUT
			id=hiProductId size=1 type=hidden name=hiProductId
			value=<%=request.getParameter("productId")%>> <INPUT
			id=hiEmployeeName size=1 type=hidden name=hiEmployeeName
			value=<%=ParameterUtil.getChineseString(request, "employeeName", "utf-8")%>>

		<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
			<TBODY>
				<TR height=24>
					<TD><IMG class=icon src="AdminApprovalIndex_files/bullet0.gif"
						height=10> <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN
							class=11air>:</SPAN>报销管理<SPAN class=arrow_subtitle>&gt;</SPAN>单据审批</TD>
					<TD width=16 noWrap align=right><IMG style="CURSOR: hand"
						id=TableHeadBar1_BarImg class=icon
						onclick=javascript:ExpandDiv(this);
						src="AdminApprovalIndex_files/icon_collapseall.gif" height=16
						data="1"></TD>
				</TR>
			</TBODY>
		</TABLE>
		<BR style="LINE-HEIGHT: 1px">
		<DIV id=divSearch>
			<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
				cellPadding=0 width="100%" align=center>
				<TBODY>
					<TR>
						<TD colSpan=6><IMG class=icon
							src="AdminApprovalIndex_files/search.gif"> 请填写查询条件</TD>
					</TR>
					<TR>
						<TD class=title height=24>
							<DIV align=left noWrap>
								<SPAN id=lblBoeNum>单据号</SPAN>
							</DIV>
						</TD>
						<TD><INPUT style="WIDTH: 133px" id=txtBoeNum name=txtBoeNum></TD>
						<TD class=title>
							<DIV align=left noWrap>
								<SPAN id=lblBoeType>单据类型</SPAN>
							</DIV>
						</TD>
						<TD><select size="0" style="WIDTH: 120px; HEIGHT: 20px"
							name="txtBoeType" id="txtBoeType">
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
						<TD class=title>
							<DIV align=left noWrap>
								<SPAN id=lblDept>报销人部门</SPAN>
							</DIV>
						</TD>
						<TD><select size="0" style="WIDTH: 120px; HEIGHT: 20px"
							name="txtDeptName" id="txtDeptName">
								<option value=-1></option>
								<%
									List<DDept> deptsInfo = (List<DDept>) request.getAttribute("deptsInfo");
									if (!deptsInfo.isEmpty()) {
								%>
								<%
									for (int index = 0; index < deptsInfo.size(); index++) {
								%>
								<option value=<%=deptsInfo.get(index).getId()%>><%=deptsInfo.get(index).getName()%></option>
								<%
									}

										for (int index = 0; index < deptsInfo.size(); index++) {
								%>
								<INPUT id=hiDept <%=deptsInfo.get(index).getId()%> size=1
								type=hidden name=hiDept <%=deptsInfo.get(index).getId()%>
								value=<%=deptsInfo.get(index).getName()%>>
								<%
									}
									}
								%>
						</select></TD>
					</TR>
					<TR>
						<TD style="HEIGHT: 24px" class=title>
							<DIV align=left noWrap>
								<SPAN id=lblEmployee>报销人</SPAN>
							</DIV>
						</TD>
						<TD style="HEIGHT: 24px"><INPUT
							style="WIDTH: 133px; HEIGHT: 20px" id=txtEmployeeName
							name=txtEmployeeName></TD>
						<TD class=title>
							<DIV align=left noWrap>
								<SPAN id=lblProject>报销项目</SPAN>
							</DIV>
						</TD>
						<TD><select size="0" style="WIDTH: 120px; HEIGHT: 20px"
							name="txtProductName" id="txtProductName">
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
					</TR>
					<TR>
						<TD colSpan=6 align=right>
							<DIV>
								<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiDeptID size=1
									type=hidden name=hiDeptID><INPUT
									style="WIDTH: 31px; HEIGHT: 20px" id=hiUserID size=1
									type=hidden name=hiUserID> <INPUT id=btnQuery
									class=stbtm01 value=查询 type=button name=btnQuery
									onclick="query()"> <INPUT id=btnClear class=stbtm01
									value=清空 type=submit name=btnClear onclick="clear_text()">
								<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiEmployeeID size=1
									type=hidden name=hiEmployeeID><INPUT
									style="WIDTH: 31px; HEIGHT: 20px" id=hiProdProjectID size=1
									type=hidden name=hiProdProjectID> <INPUT
									style="WIDTH: 32px; HEIGHT: 20px" id=hiClickFlag size=1
									type=hidden name=hiClickFlag><INPUT
									style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture value=zh-CN
									size=1 type=hidden name=hiCulture>
							</DIV>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		<TABLE id=queryMessage class=inputbl border=0 cellSpacing=0
			cellPadding=0 width="100%" align=center>
			<TBODY>
				<TR>
					<TD style="BORDER-RIGHT-STYLE: none"><IMG class=icon
						src="AdminApprovalIndex_files/icon_note.gif"> <SPAN
						id=lblApprovalList>审批列表</SPAN></TD>
				</TR>
			</TBODY>
		</TABLE>
		<DIV class=autoOverApprove align=center>
			<TABLE id=Table2 border=0 cellSpacing=0 cellPadding=1 width="100%">
				<TBODY>
					<TR>
						<TD>
							<DIV></DIV>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse"
				id="adminApproval" class="datatbl" border="1" rules="all"
				cellspacing="0">
				<TBODY>
					<%
						HashMap<String, DUser> userInfoMap = (HashMap<String, DUser>) request.getAttribute("userInfo");
					%>
					<TR id=tr_Head class=head>
						<TD style="WIDTH: 5%" align=middle><INPUT id=chk_SelAll
							type=checkbox> <LABEL>全选 </LABEL></TD>
						<TD style="WIDTH: 5%" noWrap align=left>单据号</TD>
						<TD style="WIDTH: 5%" noWrap align=left>报销人工号</TD>
						<TD style="WIDTH: 5%" noWrap align=left>报销人</TD>
						<TD style="WIDTH: 5%" noWrap align=left>所属部门</TD>
						<TD style="WIDTH: 5%" noWrap align=left>报销项目</TD>
						<TD style="WIDTH: 5%" noWrap align=left>申请金额</TD>
						<TD style="WIDTH: 5%" noWrap align=left>审批金额</TD>
						<TD style="WIDTH: 5%" noWrap align=left>提交日期</TD>
						<TD style="WIDTH: 5%" noWrap align=left>单据类型</TD>
						<TD style="WIDTH: 5%" noWrap align=left>审批状态</TD>
						<TD style="WIDTH: *" noWrap align=left>摘要</TD>
					</TR>

					<%
						ArrayList<DClaim> claimDetail = (ArrayList<DClaim>) request.getAttribute("claimDetail");
						if (!claimDetail.isEmpty()) {
					%>
					<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1
						type=hidden name=itemNum value=<%=claimDetail.size()%>>
					<%
					    int displayFlag= (Integer)request.getAttribute("displayFlag");
						boolean disabled;
						if(displayFlag==1){
							disabled=false;
						}else{
							disabled=true;
						}
						for (int index = 0; index < claimDetail.size(); index++) {
								DClaim claim = claimDetail.get(index);
					%>
					<TR class=item>
						<TD align=middle><INPUT id="select<%=index%>" type=checkbox></TD>
						<TD noWrap><a
							href="approvalDetail.do?doFlag=<%=(Boolean) request.getAttribute("doFlag")%>&InvoiceNoDe=<%=claim.getInvoiceNo()%>&deptId=<%=claim.getDeptId()%>&productId=<%=claim.getProductId()%>">
								<span id=InvoiceNo<%=index%>><%=claim.getInvoiceNo()%></span>
						</a></TD>
						<TD noWrap id=No<%=index%>><%=claim.getNo()%></TD>
						<TD noWrap id=EmployeeName<%=index%>><%=userInfoMap.get(claim.getNo()).getName()%>
						</TD>
						<TD noWrap><%=claim.getDeptName()%></TD>

						<TD noWrap><%=claim.getProductName()%></TD>

						<TD noWrap id=TotalFeexx><%=claim.getTotalFee()%> <input
							id=TotalFee<%=index%> name=TotalFee<%=index%>
							value=<%=claim.getTotalFee()%> type="hidden"
							style="border: none; width: 200px" /></TD>
						<TD noWrap id=ApprovalAmount<%=index%>>
							<%
								int apprAmount = 0;
										if (claim.getApprovalAmount() == 0) {
											apprAmount = (int) claim.getTotalFee();
										} else {
											apprAmount = (int) claim.getApprovalAmount();
										}
							%> 
							<%if(disabled) {%>
							<INPUT disabled style="WIDTH: 85px; HEIGHT: 24px;" id=txtApprovalAmount<%=index%> name=txtApprovalAmount<%=index%>
							value=<%=apprAmount%>>
							<%}else{ %>
							<INPUT style="WIDTH: 85px; HEIGHT: 24px;" id=txtApprovalAmount<%=index%> name=txtApprovalAmount<%=index%>
							value=<%=apprAmount%>>
							<%} %>
						</TD>
						<TD noWrap id=SubmitDate<%=index%>>
							<%
								Timestamp timestamp = claim.getSubmitDate();
										String dateStdr = "";
										dateStdr = (timestamp.getYear() + 1900) + "-" + (timestamp.getMonth() + 1) + "-"
												+ timestamp.getDate();
							%> <%=dateStdr%>
						</TD>
						<TD noWrap id=InvoiceTypexxx<%=index%>>
							<%
								String invoiceType = courseMap.get(claim.getInvoiceType());
							%> <%=invoiceType%> <input id=invoiceType<%=index%>
							name=invoiceType<%=index%> value=<%=claim.getInvoiceType()%>
							type="hidden" style="border: none; width: 200px" />
						</TD>
						<TD noWrap id=Status<%=index%>><%=claim.getStatusDesc()%></TD>
						<TD id=Summary<%=index%>><%=claim.getSummary()%></TD>

						<input id=SubProductId<%=index%> type="hidden"
							value="<%=claim.getSubProductId()%>">
						<input id=DeptId<%=index%> type="hidden"
							value="<%=claim.getDeptId()%>">
						<input id=ProductId<%=index%> type="hidden"
							value="<%=claim.getProductId()%>">
						<input id=ApprovalFlowId<%=index%> type="hidden"
							value="<%=claim.getApprovalFlowId()%>">
					</TR>
					<%
						}
						} else {
					%>
					<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1
						type=hidden name=itemNum value=0>
					<TR>
						<TD width="100%" align=center colspan="11"><FONT color=red>对不起，没有符合条件的记录!
						</FONT></TD>
					</TR>
					<%
						}
					%>
				</TBODY>
			</TABLE>
		</DIV>

		<script>
			var pager = new Pager("adminApproval", "tr_Head");
			AllCheck("chk_SelAll", pager, function(i) {
				document.getElementById("select" + (i)).checked = document
						.getElementById("chk_SelAll").checked
			});
		</script>

		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
			<TBODY>
				<TR>
					<TD align=right>
						<DIV>
							<INPUT id=btnApproval class=stbtm01 value=批准 type=button
								name=btnApproval onclick="approval(1)"> <INPUT
								id=btnNoApproval class=stbtm01 value=退回 type=button
								name=btnNoApproval onclick="approval(0)">
						</DIV>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
			<TBODY>
				<TR>
					<TD align=right></TD>
				</TR>
				<TR>
					<TD style="HEIGHT: 25px" align=right></TD>
				</TR>
			</TBODY>
		</TABLE>
		<INPUT id=hiResourceID value=1221 type=hidden name=hiResourceID><INPUT
			id=hiMenuPath type=hidden name=hiMenuPath><INPUT
			style="WIDTH: 31px; HEIGHT: 20px" id=hiCheckedAll value=全选 size=1
			type=hidden name=hiCheckedAll>
		<SCRIPT language=javascript>
			function ExpandDiv(imgObject) {
				if (imgObject.data == "1") {
					imgObject.data = "0";
					imgObject.src = "../../Common/Images/icon_expandall.gif";
				} else {
					imgObject.data = "1";
					imgObject.src = "../../Common/Images/icon_collapseall.gif";
				}
				if (document.all["divSearch"] != null)
					document.all["divSearch"].style.display = (imgObject.data == "0") ? "none"
							: "block";

			}

			function init() {
				var itemNum = document.getElementById("itemNum").value;
				if (itemNum > 0) {
					document.getElementById("btnApproval").disabled = "";
					document.getElementById("btnNoApproval").disabled = "";
				} else {
					document.getElementById("btnApproval").disabled = "true";
					document.getElementById("btnNoApproval").disabled = "true";
				}

				var hiBoeNum = document.getElementById("hiBoeNum").value;
				if (hiBoeNum == -1 || hiBoeNum == "" || hiBoeNum == "null") {
					document.getElementById("txtBoeNum").value = "";
				} else {
					document.getElementById("txtBoeNum").value = hiBoeNum;
				}
				var hiBoeType = document.getElementById("hiBoeType").value;
				if (hiBoeType == -1 || hiBoeType == "" || hiBoeType == "null") {
					document.getElementById("txtBoeType").value = "";
				} else {
					document.getElementById("txtBoeType").value = hiBoeType;
				}
				var hiDeptId = document.getElementById("hiDeptId").value;
				if (hiDeptId == -1 || hiDeptId == "" || hiDeptId == "null") {
					document.getElementById("txtDeptName").value = "";
				} else {
					document.getElementById("txtDeptName").value = hiDeptId;
				}
				var hiEmployeeName = document.getElementById("hiEmployeeName").value;
				if (hiEmployeeName == -1 || hiEmployeeName == ""
						|| hiEmployeeName == "null") {
					document.getElementById("txtEmployeeName").value = "";
				} else {
					document.getElementById("txtEmployeeName").value = hiEmployeeName;
				}
				var hiProductId = document.getElementById("hiProductId").value;
				if (hiProductId == -1 || hiProductId == ""
						|| hiProductId == "null") {
					document.getElementById("txtProductName").value = "";
				} else {
					document.getElementById("txtProductName").value = hiProductId;
				}
			} //页面初始化 init();
			//init2();
		</SCRIPT>
	</FORM>
</BODY>

</HTML>
