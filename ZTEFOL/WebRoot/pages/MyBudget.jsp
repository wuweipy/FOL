<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page
	import="java.util.*,Data.Claims.*,Data.Common.*,Data.UserInfo.*,java.lang.*,View.ParameterUtil,java.sql.Timestamp"%>

<html>
	<head>
			<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5>
<LINK rel=stylesheet 
type=text/css href="CountersignIndex_files/style.css"> 
		<title>财务审批</title>
		<LINK rel=stylesheet type=text/css href="Pager/Page.css">
		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/CommonFun.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/FbpLovSelect.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/AdminPageFunction.js"></SCRIPT>

		<SCRIPT type=text/javascript src="Pager/Pager.js"></SCRIPT>

		<SCRIPT type=text/javascript
			src="AdminApprovalIndex_files/Calendar.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>
		
	</head>
	<body>
	
	  <script>
	    if(window.top.needAlert) {
     window.top.needAlert = false;
  <%
	  Object isSucess = request.getAttribute("isSucess");
	  if(isSucess != null)
	   if((Boolean)isSucess)
	  {
  %>
     alert("修改成功!");
  <%	     
	  }
	else
		{
  %>
     alert("修改失败!");
  <%	
		}
	%>
	}
	</script>
	
		<%
			List<String[]> getresult = (List<String[]>) request
					.getAttribute("getresult");
					
			int institute = (Integer)request.getAttribute("institute");
		%>
		

		<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
			<TBODY>
				<TR height=24>
					<TD>
						<IMG class=icon src="AdminApprovalIndex_files/bullet0.gif"
							height=10>
						<SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN
							class=arrow_subtitle>&gt;</SPAN>我的预算<SPAN class=arrow_subtitle>&gt;</SPAN>我申请的</SPAN>
					</TD>
					<TD width=16 noWrap align=right>
						<IMG style="CURSOR: hand" id=TableHeadBar1_BarImg class=icon
							onclick=javascript:ExpandDiv(this);
							src="AdminApprovalIndex_files/icon_collapseall.gif" height=16
							data="1">
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<BR style="LINE-HEIGHT: 1px">
		<DIV id=divSearch>
			<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
				cellPadding=0 width="100%" align=center>
				<TBODY>
					<TR>
						<TD colSpan=6>
							<IMG class=icon src="AdminApprovalIndex_files/search.gif">
							请填写查询条件（按照提交时间）
						</TD>
					</TR>
					<TR>
						<TD class=title height=24>
							<DIV align=left>
								<form action="SearchMyBudget.do" method="post">
									
									<select name="year">
										<option value="">
											不限
										</option>
										<option value="2014">
											2014年
										</option>
										<option value="2015">
											2015年
										</option>
										<option value="2016">
											2016年
										</option>
										<option value="2017">
											2017年
										</option>
										<option value="2018">
											2018年
										</option>
										<option value="2019">
											2019年
										</option>
										<option value="2020">
											2020年
										</option>
										<option value="2021">
											2021年
										</option>
										<option value="2022">
											2022年
										</option>
										<option value="2023">
											2023年
										</option>
										<option value="2024">
											2024年
										</option>
										<option value="2025">
											2025年
										</option>
										<option value="2026">
											2026年
										</option>
										<option value="2027">
											2027年
										</option>
										<option value="2028">
											2028年
										</option>
									</select>
									<SPAN>年</SPAN>
									
									<select name="month">
										<option value="">
											不限
										</option>
										<option value="01">
											1月
										</option>
										<option value="02">
											2月
										</option>
										<option value="03">
											3月
										</option>
										<option value="04">
											4月
										</option>
										<option value="05">
											5月
										</option>
										<option value="06">
											6月
										</option>
										<option value="07">
											7月
										</option>
										<option value="08">
											8月
										</option>
										<option value="09">
											9月
										</option>
										<option value="10">
											10月
										</option>
										<option value="11">
											11月
										</option>
										<option value="12">
											12月
										</option>
									</select>
									<SPAN>月</SPAN>
									<input name="" type="submit" value="查询" />
								</form>
							</DIV>
						</TD>
					</TR>
					<TR>


					</TR>
				</TBODY>
			</TABLE>
			<TABLE id=queryMessage class=inputbl border=0 cellSpacing=0
				cellPadding=0 width="100%" align=center>
				<TBODY>
					<TR>
						<TD style="BORDER-RIGHT-STYLE: none">
							<IMG class=icon src="AdminApprovalIndex_files/icon_note.gif">
							<SPAN id=lblApprovalList>申请列表</SPAN>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<DIV align=center>
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

						<TR id=tr_Head class=head>
							<TD noWrap align=left>
								单号
							</TD>
							<TD noWrap align=left>
								月份
							</TD>						
							<TD  noWrap align=left>
								提单人
							</TD>
							<TD  noWrap align=left>
								委托人
							</TD>
							
							<TD  noWrap align=left>
								所属项目
							</TD>
				
							<TD  noWrap align=left>
								预算总额
							</TD>
							<TD  noWrap align=left>
								状态
							</TD>
							<TD  noWrap align=left>
								提交日期
							</TD>
							<TD  noWrap align=left>
								操作
							</TD>
	<%
		HashMap<String, DUser> userInfoMap = (HashMap<String, DUser>)request.getAttribute("userInfoMap");
	%>
						</TR>
						<%
							if (!getresult.isEmpty()) {

								for (int index = 0; index < getresult.size(); index++) {
						%>
						<TR class=item>

							<TD noWrap align=left>

								<%
									String[] ss = (String[]) getresult.get(index);
								%>
								<%=ss[0]%>
							</TD>
							
							<TD noWrap align=left>
								<%
									String year = ss[0].substring(0,4);
									String month = ss[0].substring(4,6);									
								%>
								<%=year%>年<%=month%>月
							</TD>

							<TD noWrap align=left>
							
								<%=ss[8]%><%=ss[1]%>
							</TD>
							<TD noWrap align=left>
							<%
							 	String ttt;
								if(ss[6].equals("无"))   {
								ttt = "--";
								}
								else{
								ttt = userInfoMap.get(ss[6]).getName() + ss[6];
								}
								%>
								<%=ttt%>
							</TD>
							<TD noWrap align=left>

								<%=ss[2]%>
							</TD>
							<TD noWrap align=left>

								<%=ss[3]%>
							</TD>
							<TD noWrap align=left>
								<%
									String temp = null;
											if (ss[4].equals("0")) {
												temp = "退回，已关闭";
											}
											if (ss[4].equals("1")) {
												temp = "待所长审批";
											}
											if (ss[4].equals("2")) {
												temp = "待财务审批";
											}
											if (ss[4].equals("3")) {
												temp = "待总经理审批";
											}
											if (ss[4].equals("4")) {
												temp = "退回,待修改";
											}
											if (ss[4].equals("5")) {
												temp = "成功，已关闭";
											}
								%>
								<%=temp%>
							</TD>
							<TD noWrap align=left>

								<%=ss[5]%>
							</TD>
							<td align=left>
								<a href="LoadMyBudget.do?budgetid=<%=ss[0]%>&handle=0&budgetstate=<%=java.net.URLEncoder.encode(temp,"utf-8")%>&billTime=<%=ss[5]%>&deptname=<%=java.net.URLEncoder.encode(ss[2],"utf-8")%>"
									target="right">查看</a>&nbsp;&nbsp;
								<%
								if(institute==1){
									if (  ss[4].equals("1") || ss[4].equals("4") || (ss[7].equals("0") && ss[4].equals("5")) ) {
								%>
								<a href="LoadMyBudget.do?budgetid=<%=ss[0]%>&handle=1&budgetstate=<%=temp%>&billTime=<%=ss[5]%>&deptname=<%=ss[2]%>"
									target="right">修改</a>
								<%
							}}
							else if(institute==2){
									if (ss[4].equals("2") || ss[4].equals("4") || (ss[7].equals("0") && ss[4].equals("5"))) {
																	%>
								<a href="LoadMyBudget.do?budgetid=<%=ss[0]%>&handle=1&budgetstate=<%=temp%>&billTime=<%=ss[5]%>&deptname=<%=ss[2]%>"
									target="right">修改</a>
								<%
									}		}							
								%>
							</td>
						</TR>
						<%
							} }
							else {
						%>
						<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=itemNum size=1
							type=hidden name=itemNum value=0>
						<TR>
							<TD width="100%" align=center colspan="11">
								<FONT color=red>对不起，没有符合条件的记录! </FONT>
							</TD>
						</TR>
						<%
							}
							
								%>
	

					</TBODY>
				</TABLE>
			</DIV>

			<script>
     var pager = new Pager("adminApproval","tr_Head");    
  </script>
		
	</body>
</html>