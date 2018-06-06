<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java"
	import="java.util.*,Business.UserDetail.BUserDetail,Business.Claims.*,Data.Common.Entry"%>
<HTML>
<HEAD>
<TITLE>报销基础信息</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema
	content=http://schemas.microsoft.com/intellisense/ie5>
<LINK rel=stylesheet type=text/css href="BoeWizardIndex_files/style.css">

<SCRIPT language=javascript>
function hasSubProduct() {
	var subObj = document.getElementById("subProductId");
	
	var len = subObj.length;
	if (len > 0) {
		for (j = len - 1; j > -1; j--) {
			subObj.remove(j);
		}
	}

	var subProductArr = "${subProducts}".replace('{', '').replace('}', '')
			.split(',');
	var curSubPro = ${curSubProject};
	var prod = document.getElementById("product").value;
	var arr = new Array();
	for (i = 0; i < subProductArr.length; i++) {
		var item = subProductArr[i];
		var itemArr = item.split("=");
		var optArr=itemArr[0].replace(/(^\s*)/g,'').replace(/(\s*$)/g,'').split("__@__");
		if (optArr[0] == prod) {
			var opt = document.createElement('option');
			if(optArr[1] == curSubPro)
				opt.selected = true;
			opt.value = optArr[1];
			opt.text = itemArr[1];
			subObj.options.add(opt);
		}
	}
	
	
	//document.getElementById("subP").style.display = "block";
	//document.getElementById("subProduct").style.display ="block";
	document.getElementById("producttXT").colSpan = '1';
	////document.getElementById("subP").style.display = (flag == true) ? "block"
			//: "none";
	//document.getElementById("subProduct").style.display = (flag == true) ? "block"
			//: "none";
	//document.getElementById("producttXT").colSpan = (flag == true) ? '1'
			//: '3';

}

	function submit_data() {
		if (document.getElementById("direct_finance_audit_flag").checked) {
			document.getElementById("directTofinance").value = "true";
		}
		document.getElementById("save").value = "save";
		Form1.submit();
	}

	function go_next() {
		document.getElementById("save").value = "next";
		document.getElementById("action").value = "2";
		Form1.submit();
	}
</SCRIPT>
</HEAD>
<%
	BUserDetail userInfo = (BUserDetail) request.getAttribute("userInfo");
	HashMap<Integer, String> products = (HashMap<Integer, String>) request.getAttribute("prodInfo");
	Iterator<Integer> prodIterator = products.keySet().iterator();

	HashMap<Integer, String> citys = (HashMap<Integer, String>) request.getAttribute("cityInfo");
	Iterator<Integer> cityIterator = citys.keySet().iterator();

	HashMap<Integer, String> courseMap = (HashMap<Integer, String>) request.getAttribute("courseMap");
	Iterator<Integer> courseIterator = courseMap.keySet().iterator();
	
	Integer curProject = (Integer) request.getAttribute("curProject");
	Integer curInvoiceType = (Integer) request.getAttribute("curInvoiceType");
	Integer curlocation = (Integer) request.getAttribute("curlocation");
%>
<BODY onload="hasSubProduct();">
	<FORM id=Form1 method=post name=Form1 action="boe.do">
		<input type="hidden" id="action" name="action">
		<TABLE id=Table1 border=0 cellSpacing=0 cellPadding=0 width="100%">
			<TBODY>
				<TR height=1>
					<TD>
						<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0
							align=center>
							<TBODY>
								<TR height=24>
									<TD><IMG class=icon src="BoeWizardIndex_files/bullet0.gif"
										height=10> <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN
											class=11air>:</SPAN>报销管理<SPAN class=arrow_subtitle>&gt;</SPAN>单据制作<SPAN
											class=arrow_subtitle>&gt;</SPAN>在线填单
									</SPAN></TD>
									<TD width=16 noWrap align=right><IMG style="CURSOR: hand"
										id=TableHeadBar1_BarImg class=icon
										onclick=javascript:ExpandDiv(this);
										src="BoeWizardIndex_files/icon_collapseall.gif" height=16
										data="1"></TD>
								</TR>
							</TBODY>
						</TABLE> <BR style="LINE-HEIGHT: 1px">
					</TD>
				</TR>
				<TR>
					<TD vAlign=top>
						<DIV id=divXml>

							<TABLE class=inputbl border=0 cellSpacing=0 cellPadding=0
								width="100%" bgColor=#1475b3 align=center>
								<TBODY>
									<TR>
										<TD colSpan=4><IMG class=icon
											src="BoeWizardIndex_files/icon_note.gif"> 基础信息</TD>
									</TR>
									<TR>
										<TD class=title width="20%" noWrap>
											<DIV>
												报销人<FONT color=red>*</FONT>
											</DIV> <INPUT id=employee_id value=244680 readOnly width=60
											type=hidden name=employee_id>
										</TD>
										<TD width="30%"><%=userInfo.getName() + userInfo.getNo()%></TD>
										<TD class=title width="20%" noWrap>
											<DIV>所属部门</DIV>
										</TD>
										<TD><INPUT id=dept_class value=PROD readOnly type=hidden
											name=dept_class><INPUT id=dept_ID value=23254
											readOnly type=hidden name=dept_ID><INPUT
											style="BORDER-BOTTOM-STYLE: none; BORDER-RIGHT-STYLE: none; BACKGROUND-COLOR: transparent; WIDTH: 100%; BORDER-TOP-STYLE: none; BORDER-LEFT-STYLE: none"
											id=dept_name value=<%=userInfo.getDeptStr()%> readOnly
											name=dept_name></TD>
									</TR>

									<TD class=title noWrap>报销项目<FONT color=red>*</FONT>
									</TD>
									<TD id=producttXT colspan="1"><select
										onchange="hasSubProduct()" style="WIDTH: 180px" id="product"
										name="product">
											<%
												while (prodIterator.hasNext()) {
													int key = prodIterator.next();
												if(key == curProject) {
											%>
											    <option value=<%=key%> selected = "selected"><%=products.get(key)%></option>
											<% 
												}else{ 
											%>
											    <option value=<%=key%>><%=products.get(key)%></option>
											<%
												}}
											%>
									</select></TD>
									<TD id=subP class=title noWrap>子项目<FONT color=red>*</FONT>
									</TD>
									<TD id=subProduct colspan="1"><select style="WIDTH: 200px"
										id="subProductId" name="subProductId">
									</select></TD>
									<TD style="DISPLAY: none" id=tdcheck_project class=title noWrap>核算项目<FONT
										color=red>*</FONT><INPUT id=check_project_id readOnly width=60
										type=hidden name=check_project_id><INPUT
										id=check_manage_dept readOnly width=60 type=hidden
										name=check_manage_dept runat="server"></TD>
									<TD style="DISPLAY: none" id=tdcheck_project_name><INPUT
										style="WIDTH: 200px" id=check_project_name readOnly
										name=check_project_name value=<%=userInfo.getName()%>></TD>
									<TD style="DISPLAY: none" id=tdManagTeam class=title noWrap>经营团队<INPUT
										id=manag_team_id readOnly width=60 type=hidden
										name=manag_team_id><INPUT id=check_manage_dept
										readOnly width=60 type=hidden name=check_manage_dept
										runat="server"></TD>
									<TD style="DISPLAY: none" id=tdteam_name><INPUT
										style="WIDTH: 200px" id=manag_team_code readOnly type=hidden
										name=manag_team_code><INPUT style="WIDTH: 200px"
										id=check_team_name readOnly name=check_team_name><IMG
										style="CURSOR: hand" id=img_manage_team class=icon
										onclick=javascript:managerTeamLov();
										src="BoeWizardIndex_files/calendar.gif" runat="server"></TD>
									</TR>
									<TR>
										<TD class=title noWrap>
											<DIV>
												单据类型<FONT color=red>*</FONT>
											</DIV>
										</TD>
										<TD id=tdBoeTypeSel colspan="3"><SELECT
											style="WIDTH: 180px" id=boe_type_id name=boe_type_id>
												<%
													while (courseIterator.hasNext()) {
														int key = courseIterator.next();
													if(key == curInvoiceType) {
												%>
												    <option value=<%=key%> selected = "selected"><%=courseMap.get(key)%></OPTION>
												<% 
												}else{ 
											    %>
												    <option value=<%=key%>><%=courseMap.get(key)%></OPTION>
												<%
													}}
												%>
										</SELECT></TD>
									</TR>
									<TR id=trBpSubmit>
										<TD class=title noWrap>票据提交方式<FONT color=red>*</FONT></TD>
										<TD id=tdBpSubmitType><INPUT id="hasBill" width=100
											CHECKED type=radio name="hasBill" groupname="bill"> <input
											type="hidden" id="isHasBill" name="isHasBill" value="true"><SPAN>有实物票据</SPAN><INPUT
											id="bill_pract_flag_n" width="100" type="radio"
											name="hasBill" groupname="bill" value="true"><SPAN>无实物票据</SPAN></TD>
										<TD id=trbda1 class=title noWrap>
											<DIV id=divbda1>
												票据提交地点<FONT color=red>*</FONT>
											</DIV>
										</TD>
										<TD id=trbda2>
											<DIV id=divbda2>
												<SELECT style="WIDTH: 200px" id=deliver_area
													name=deliver_area>
													<%
														while (cityIterator.hasNext()) {
															int key = cityIterator.next();
															if(key == curlocation) {
													%>
													    <option value=<%=key%> selected = "selected"><%=citys.get(key)%></option>
													<% 
												    }else{ 
											        %>
											            <option value=<%=key%>><%=citys.get(key)%></option>
													<%
														}}
													%>
												</SELECT>
											</DIV>
										</TD>
									</TR>
									<!--<TD colSpan=4> -->
									<INPUT type="hidden" id=direct_finance_audit_flag type=checkbox
										name=direct_finance_audit_flag>
									<!--直接提交财务审核(经财务审核人员允许，方可选择)-->
									</INPUT>
									<input type="hidden" id="directTofinance"
										name="directTofinance" value="false">

									<TR>
										<TD colSpan=4 align=right calss="bottom_center"><input
											type="hidden" id="save" name="save" value="no"> <INPUT
											id=btnSubmit class=stbtm01 onclick=submit_data() value=保存
											type=button style="DISPLAY: none"> <INPUT id=btnNext class=stbtm01
											onclick=go_next() value=下一步 type=button> <INPUT
											id=org_id readOnly width=200 type=hidden name=org_id></TD>
									</TR>
								</TBODY>
							</TABLE>
							<TABLE style="DISPLAY: none" border=0 cellSpacing=0 cellPadding=1
								width="100%" align=center>
								<TBODY>
									<TR>
										<TD colSpan=3><B>说明</B></TD>
									</TR>
									<TR>
										<TD>
										<TD>借款单据</TD>
										<TD>此单据只能填写CNY、HKD、USD三个币种，其他币种的借款请填写“国际借款单据”</TD>
									</TR>
									<TR>
										<TD>
										<TD>报销单据</TD>
										<TD>1、除差旅费之外的其他开支项目报销时均填写此单。</TD>
									</TR>
									<TR>
										<TD>
										<TD>
										<TD>2、此单据只能填写CNY、HKD、USD三个币种，其他币种的报销请填写“国际报销单据”</TD>
									</TR>
									<TR>
										<TD>
										<TD>增值税单据</TD>
										<TD>此单据填写所有涉及增值税进项税报销的业务</TD>
									</TR>
									<TR>
										<TD>
										<TD>工会费报销</TD>
										<TD>请手工选择首签领导为工会负责人</TD>
									</TR>
								</TBODY>
							</TABLE>
						</DIV>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE class=PromptTips border=0 cellSpacing=0 cellPadding=0
			width="100%" align=center>
			<TBODY>
				<TR vAlign=center>
					<TD style="PADDING-LEFT: 10px"><SPAN style="FONT-SIZE: 20px">1.若报销人部门不对或者部门领导不对，请到</SPAN><SPAN
						style="COLOR: red; FONT-SIZE: 20px">与我有关-&gt;我的信息-&gt;个人信息</SPAN><SPAN
						style="FONT-SIZE: 20px">维护您的所属部门，详情参考帮助</SPAN><SPAN
						style="FONT-SIZE: 20px">。</SPAN></TD>
				</TR>
			</TBODY>
		</TABLE>
		<DIV></DIV>
		</TD>
		</TR>
		<TR>
			<TD></TD>
		</TR>
		</TABLE>
	</FORM>
</BODY>
</HTML>
