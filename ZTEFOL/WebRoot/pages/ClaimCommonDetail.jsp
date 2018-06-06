<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java"
	import="java.util.*,Business.UserDetail.BUserDetail,Business.Claims.*,Common.*,Common.Location.*,Data.ApprovalInfo.*,Data.UserInfo.*"%>

<div id="divSearch">
	<TABLE class="inputbl" id="queryPane" cellSpacing="1" cellPadding="1"
		width="100%" align="center" border="0">
		<TR>
			<TD id="tdLogo" class="logontitle" width="14%" rowSpan="6">
				<DIV align="center">
					<P class="zteLightBlue" align="center">
						<span id="lblCompanyBill"><%=courseMap.get(bClaim.getInvoiceType())%>报销单</span>
					</P>
					<input name="invoiceType" id="invoiceType"
						value=<%=bClaim.getInvoiceType()%> type="hidden"
						style="border: none; width: 200px">
				</DIV>
			</TD>

			<TD class="title" height="23" style="width: 150px"><span
				id="lblBoeNum">单据号</span></TD>
			<TD class="smallInput" align="left" bgColor="#efefef" height="23"><span
				id="lblBoe_Num"><%=bClaim.getInvoiceNo()%></span></TD>
			<input name="lblBoe_No" value=<%=bClaim.getInvoiceNo()%>
				type="hidden" style="border: none; width: 200px">
			<TD class="title" height="23"><span id="lblBillPractNum">实物票据号</span></TD>
			<TD align="left" bgColor="#efefef" height="23"><span
				id="lblBpNum"> <%=bClaim.getBillNo()%>
			</span></TD>
		</TR>
		<tr>
			<TD class="title" height="23"><span id="lblEN">报销人姓名</span></TD>
			<TD class="smallInput" align="left" bgColor="#efefef" height="23"><span
				id="lblEmployeeName"> <%=userInfo.getName()%>
			</span></TD>
			<TD class="title" height="23"><span id="lblEnum">报销人工号</span></TD>
			<TD align="left" bgColor="#efefef" height="23"><span
				id="lblEmployeeNumber"><%=userInfo.getAllNo()%></span> <input
				name="lblEmployeeNumber" id="employeeNo"
				value=<%=userInfo.getAllNo()%> type="hidden"
				style="border: none; width: 200px"></TD>
		</tr>
		<input type="hidden" name="deptId" value=<%=bClaim.getDeptId()%> />
		<input type="hidden" name="productId" value=<%=bClaim.getProductId()%> />
		<input type="hidden" name="subproductId" value=<%=bClaim.getSubProductId()%> />
		<TR>
			<TD class="title" width="8%" height="23"><span id="lblDN">所属部门</span></TD>
			<TD align="left" bgColor="#efefef" height="23"><span
				id="lblDeptName"><%=bClaim.getDeptName()%></span></TD>
			<TD class="title"><span id="lblPP">报销项目</span></TD>
			<TD align="left" bgColor="#efefef"><span id="lblProdProjectName"><%=bClaim.getProductName()%></span></TD>
		</TR>
		<%
			ArrayList<DApprovalInfo> dApprovalInfoList = (ArrayList<DApprovalInfo>) request
					.getAttribute("dApprovalInfoList");
			HashMap<String, DUser> userInfoNo = (HashMap<String, DUser>) request.getAttribute("userInfoNo");
			String name = "";
			if (dApprovalInfoList != null && dApprovalInfoList.size() > 0) {
				name = dApprovalInfoList.get(0).getApprovalName();
			} else {
				if(UserMngt.getInstance().getUserInfoMap().get(bClaim.getApprovalId())!=null)
				name = UserMngt.getInstance().getUserInfoMap().get(bClaim.getApprovalId()).getName();
			}
		%>
		<tr>
			<TD class="title" width="8%" height="23"><span id="lblDN">报销子项目</span></TD>
			<TD align="left" bgColor="#efefef" height="23"><span
				id="lblDeptName"><%=bClaim.getSubProductName()%></span></TD>
			<TD class="title"><span id="lblPP">当前审批人</span></TD>
			<TD align="left" bgColor="#efefef"><span id="lblProdProjectName"><%=bClaim.getApprovalName()%></span></TD>
		</tr>


	</TABLE>
</div>
