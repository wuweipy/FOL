<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page
	import="java.util.*,Data.Claims.*,Data.Common.*,Data.UserInfo.*,java.lang.*,Business.Budget.*,View.ParameterUtil,java.sql.Timestamp"%>
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
			
				  <script>
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
	</script>
				  <script>	
	             function notPermit()
             { 
                   updatemybudgetform.action = "myBudget.do?";
                   updatemybudgetform.submit();              
             }
             
             	             function submitt()
             { 
                   window.top.needAlert = true;
                   updatemybudgetform.action = "Updatemybudget.do?action=submit";
                   updatemybudgetform.submit();              
             }
	</script>
	
<script>
function sumhe(index,count){
var s = 0;
for(var i=0;i<count;i++)
{
   document.getElementById("submoney"+index+i).value = document.getElementById("submoney"+index+i).value.replace(/[^(\d)]/g,'');
   s = s + document.getElementById("submoney"+index+i).value/1;
}
document.getElementById("sum"+index).value=s;
}
</script>

	</head>
	<body>
		<FORM id="updatemybudgetform" method="post" name="updatemybudgetform"
			action="Updatemybudget.do?action=submit">
			<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0
				align=center>
				<TBODY>
					<TR height=24>
						<TD>
							<IMG class=icon src="AdminApprovalIndex_files/bullet0.gif"
								height=10>
							<SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>报销管理<SPAN
								class=arrow_subtitle>&gt;</SPAN>预算申请<SPAN class=arrow_subtitle>&gt;</SPAN>我申请的</SPAN>
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
			<%
				String budgetid = request.getAttribute("budgetid").toString();
				String deptname = request.getAttribute("deptname").toString();
				String budgetstate = request.getAttribute("budgetstate").toString();
				String billTime = request.getAttribute("billTime").toString();
				String handle = request.getAttribute("handle").toString();
	  List<BudgetDetailInfo> budgetDetailInfos = (List<BudgetDetailInfo>)request.getAttribute("budgetDetailInfos");
          ArrayList<Integer> subProIds = (ArrayList<Integer>)request.getAttribute("subProIds");
          HashMap<Integer,String> subProducts = (HashMap<Integer,String>)request.getAttribute("subProducts");				
			%>
			单号：<%=budgetid%>&nbsp&nbsp&nbsp&nbsp
			月份：<%=budgetid.substring(0,4)%>年<%=budgetid.substring(4,6)%>月&nbsp&nbsp&nbsp&nbsp
			所属项目：<%=deptname%>&nbsp&nbsp&nbsp&nbsp
			提单日期：<%=billTime%>&nbsp&nbsp&nbsp&nbsp
			状态：<% String temp = null;
				if (budgetstate.equals("0")) {
					temp = "退回，已关闭";
				}
				if (budgetstate.equals("1")) {
					temp = "待所长审批";
				}
				if (budgetstate.equals("2")) {
					temp = "待财务审批";
				}
				if (budgetstate.equals("3")) {
					temp = "待总经理审批";
				}
				if (budgetstate.equals("4")) {
					temp = "退回,待修改";
				}
				if (budgetstate.equals("5")) {
					temp = "成功，已关闭";
				}%>
				<%=temp%>
			<TABLE id=queryMessage class=inputbl border=0 cellSpacing=0
				cellPadding=0 width="100%" align=center>
				<TBODY>
					<TR>
						<TD style="BORDER-RIGHT-STYLE: none">
							<IMG class=icon src="AdminApprovalIndex_files/icon_note.gif">
							<SPAN id=lblApprovalList>查看列表</SPAN>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<DIV  align=center>
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
							<TD style="WIDTH: 10%" noWrap align=left>
								科目
							</TD>
							<TD style="WIDTH: 7%" noWrap align=left>
								总金额
							</TD>
	<%            
             for(int i=0;i<subProIds.size();i++)
             {            
                    %>   
          <TD noWrap style="WIDTH: 7%" align=left><%= subProducts.get(subProIds.get(i))%></TD>                                      
           <%   
             }
          %>   
							<TD style="WIDTH: 9%" noWrap align=left>
								详细说明
							</TD>
							<TD style="WIDTH: 8%" noWrap align=left>
								所长审批
							</TD>
							<TD style="WIDTH: 8%" noWrap align=left>
								财务审批
							</TD>
							<TD style="WIDTH: 8%" noWrap align=left>
								总经理审批
							</TD>
						</TR>
						<%
							List<String[]> getresult = (List<String[]>) request
									.getAttribute("getresult");							
							if (!getresult.isEmpty()) {

								for (int index = 0; index < getresult.size(); index++) {
						%>

						<TR class=item>

							<TD noWrap align=left>

								<%
									String[] ss = (String[]) getresult.get(index);%>
								<%=ss[0]%>
							</TD>
							<input type="hidden" name="course<%=index%>" value="<%=ss[0]%>" />
							<% if(handle.equals("1")) { 
							   if(subProIds.size()==0) {%>
							<TD noWrap align=left>
								<input type="text" name="sum<%=index%>" value="<%=ss[1]%>" onkeyup="value=value.replace(/[^(\d)]/g,'')" />
							</TD>
							<% } 
							else{%>
							<TD noWrap align=left>
								<input readonly type="text" id="sum<%=index%>" name="sum<%=index%>" value="<%=ss[1]%>" />
							</TD>																																			
	<%  }
	for(int j=0;j<subProIds.size();j++) {%>
            <TD noWrap align=left>
                <%
	                String money = "0";
	                if(budgetDetailInfos.get(index).getSubmoneys().size()!=0)
	                {
	                    money = budgetDetailInfos.get(index).getSubmoneys().get(j).getName();
	                }
                %>
          	<input type="text" id="submoney<%=index%><%=j%>" name="submoney<%=index%><%=j%>" value="<%=money%>" onkeyup="sumhe(<%=index%>,<%=subProIds.size()%>)" />
            </TD>            
            <%}%>   							
							<TD noWrap align=left>
								<textarea type="text" name="dsc<%=index%>"><%=ss[2]%></textarea>
							</TD>
							<%
								} else {
							%>
							<TD noWrap align=left>
								<%=ss[1]%>
							</TD>
	<%for(int j=0;j<subProIds.size();j++) {%>
            <TD noWrap align=left>
                <%
	                String money = "0";
	                if(budgetDetailInfos.get(index).getSubmoneys().size()!=0)
	                {
	                    money = budgetDetailInfos.get(index).getSubmoneys().get(j).getName();
	                }
                %>
          	<%= money%>
            </TD>            
            <%}%>   							
							<TD wrap align=left>
								<%=ss[2]%>
							</TD>
							<%
								}
							%>
							<TD noWrap align=left>

								<%=ss[3]%>
							</TD>
							<TD noWrap align=left>

								<%=ss[4]%>
							</TD>
							<TD noWrap>

								<%=ss[5]%>
							</TD>


						</TR>
						<%
							}
							} else {
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

			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
				<TBODY>
					<TR>
					</TR>
				</TBODY>
			</TABLE>
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
				<TBODY>
					<TR>
						<TD align=right></TD>
					</TR>
					<TR>
						<TD style="HEIGHT: 27px" align=right>
							<%
								if (handle.equals("1")) {
							%>
							<input style="WIDTH: 50px; HEIGHT: 30px"  type=button onclick="submitt()" value="提交" />
							<input style="WIDTH: 50px; HEIGHT: 30px" type=button onclick="notPermit()" value=返回>
							<%
								}
							%>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<input type="hidden" value="<%=getresult.size()%>" name="resultsize">
			<input type="hidden" value="<%=budgetid%>" name="budgetid">
			<input type="hidden" value="<%=handle%>" name="handle">
		
			<input type="hidden" value="<%=deptname%>" name="deptname">
			<input type="hidden" value="<%=budgetstate%>" name="budgetstate">
			<input type="hidden" value="<%=billTime%>" name="billTime">
		</FORM>
		<%
			String[] times = (String[]) request.getAttribute("times");
			if(!times[0].equals("")){
		%>
		所长审批：<%=times[0]%>  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<%=times[1]%>  
		</br>  		<% } 
					if(!times[2].equals("")){
		%>
		财务审批：<%=times[2]%> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<%=times[3]%>  
		</br>  		<% } 
					if(!times[4].equals("")){
		%>
		总经理审批：<%=times[4]%> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<%=times[5]%>  
		</br>  		<% } %>
		

	</body>
</html>