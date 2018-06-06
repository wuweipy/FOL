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
		<LINK rel=stylesheet type=text/css
			href="AdminApprovalIndex_files/style.css">
		<LINK rel=stylesheet type=text/css href="Pager/Page.css">
		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/CommonFun.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/FbpLovSelect.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/AdminPageFunction.js"></SCRIPT>

		<SCRIPT type=text/javascript src="Pager/AllCheck.js"></SCRIPT>

		<SCRIPT type=text/javascript src="Pager/Pager.js"></SCRIPT>

		<SCRIPT type=text/javascript
			src="AdminApprovalIndex_files/Calendar.js"></SCRIPT>

		<SCRIPT language=javascript
			src="AdminApprovalIndex_files/CommLovFun.js"></SCRIPT>

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
                
                varStr += "&ApprovalAmount"+selectedCount+"=";
                varStr += document.getElementById("txtApprovalAmount"+selIndex).value;
                
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
            Form1.action = "financeApproval.do?" + varStr;
            Form1.submit();
        }
    }
    
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
	</head>
	<body>
		<%
			List<String[]> getresult = (List<String[]>) request
					.getAttribute("getresult");
		%>

		<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
			<TBODY>
				<TR height=24>
					<TD>
						<IMG class=icon src="AdminApprovalIndex_files/bullet0.gif"
							height=10>
						<SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN
							class=arrow_subtitle>&gt;</SPAN>我的预算<SPAN class=arrow_subtitle>&gt;</SPAN>我审批的</SPAN>
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
							请填写查询条件
						</TD>
					</TR>
					<TR>
						<TD class=title height=24>
							<DIV align=left>
								<form action="SearchMyApprovalBudget.do" method="post">

									<select name="year">
										<option value="">
											不限
										</option>
										<option value="2004">
											2004年
										</option>
										<option value="2005">
											2005年
										</option>
										<option value="2006">
											2006年
										</option>
										<option value="2007">
											2007年
										</option>
										<option value="2008">
											2008年
										</option>
										<option value="2009">
											2009年
										</option>
										<option value="2010">
											2010年
										</option>
										<option value="2011">
											2011年
										</option>
										<option value="2012">
											2012年
										</option>
										<option value="2013">
											2013年
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
									<SPAN>月</SPAN> &nbsp;&nbsp;&nbsp;&nbsp;
									<select name="deptid">
									<option value="">不限</option>
										<%
											List<String[]> getCombolist = (List<String[]>) request.getAttribute("getCombolist");
											if (getCombolist.size() != 0) {
												for (int i = 0; i < getCombolist.size(); i++) {
													String[] getone = getCombolist.get(i);
										%>
										<option value=<%=getone[0]%>><%=getone[1]%></option>
										<%
											}
											}
										%>
									</select>
									部门&nbsp;&nbsp;&nbsp;&nbsp; 提单人：
									<input name="userno" type="text" />
									&nbsp;&nbsp;&nbsp;&nbsp;单号:
									<input name="budgetid" type="text" />
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
							<TD  noWrap align=left>
								单号
							</TD>

							<TD  noWrap align=left>
								提单人
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
							</TD >

							<TD noWrap align=left>

								<%=ss[1]%>
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
												temp = "退回，待修改";
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
							<td  align=left>
								<a
									href="LoadMyApprovalBudget.do?getid=<%=ss[6]%>&budgetid=<%=ss[0]%>&handle=0&budgetstate=<%=java.net.URLEncoder.encode(temp, "utf-8")%>&billTime=<%=ss[5]%>&deptname=<%=java.net.URLEncoder.encode(ss[2], "utf-8")%>"
									target="right">查看</a>&nbsp;&nbsp;
							</td>
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
			
	</body>
</html>