﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*,Business.UserDetail.BUserDetail,Business.Claims.*,Common.*,Common.Location.*,Data.ApprovalInfo.*,Data.UserInfo.*,Data.Claims.*"%>
<HTML>
    <HEAD>
        <title>财务审批</title>
        <LINK href="BoeWizardIndex_files/style.css" type="text/css" rel="stylesheet">
        <SCRIPT language=javascript>
</SCRIPT>
        
    </HEAD>
    <body>
        <form name="Form1" method="post" action="finInvoiceDetail.do">
          <input type="hidden" id="save" name="save">
          <TABLE id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0">
                <TR height="1">
                    <TD><FONT face="......">
<table class="tabbar1" cellspacing="0" cellpadding="0" align="center" border="0">
    <tbody>
        <tr height="24">
            <td>
                <img class="icon" height="10" src="BoeWizardIndex_files/bullet0.gif">
                <span id="TableHeadBar1_lblHead">当前位置<SPAN class="11air">:</SPAN>报销管理<span class="arrow_subtitle">&gt;</span>财务审批<span class="arrow_subtitle">&gt;</span>详单审批</span>
            </td>
            <td align="right" width="16" nowrap>
                <img src="BoeWizardIndex_files/icon_printer.gif" id="TableHeadBar1_BarImg" class="icon" height="16" style="cursor: hand" data="1" onclick="window.print()" />
            </td>
        </tr>
    </tbody>
</table>
<br style="line-height: 1px;">
                                      <%
                                          BUserDetail userInfo = (BUserDetail)request.getAttribute("userInfo");
                                          BClaim bClaim = (BClaim)request.getAttribute("claim");
                            	          HashMap<Integer, String> courseMap = (HashMap<Integer, String>)request.getAttribute("courseMap"); 
                            	          HashMap<Integer, String> products = (HashMap<Integer, String>)request.getAttribute("prodInfo");
                                      %>
</FONT></TD>
                </TR>
                <TR>
                    <TD vAlign="top">
                       <!-- start -->
                       <%@include file="ClaimCommonDetail.jsp"%>
                        <!-- end --> 
                                                
                        
                        
                    </TD>
                </TR>
                <TR>
                    <TD vAlign="top">
                        <div id="divXml"><HEAD><TITLE>.................................</TITLE></HEAD><BODY><TABLE width="100%" align="center" id="tblMainHeader" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0"><tr><td colspan="6" align="left"><img class="icon" src="BoeWizardIndex_files/icon_note.gif" />
                         报销头信息
                         </td>
                         </tr>
                         </TABLE>
                         <TABLE width="100%" align="center" id="tblMain" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
                          <TR>
                         		<td class="title" nowrap="nowrap" style="width:10%">
                              <div align="left">摘要</div>
                              </td>
                              <td colspan="5">
                                <input id="boe_abstract" type="text" name="boe_abstract" readonly="readonly" 
                         				style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:60%" 
                                value="<%= bClaim.getSummary()%>" />
                                </td>
                         				<td class="title" nowrap="nowrap" align="left" style="width:10%">会计调整</td>
                                <td align="left" colspan="5"><input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly"
                         					style="TEXT-ALIGN:center;BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:20%;text-align:right" 
                                  value="<%= bClaim.getAccountAdjust()%>" />
                                  </td>
                                  </TR>
                                  <tr>
                         						<td class="title" nowrap="nowrap" style="width:10%">
                                       <div align="left">员工级别</div>
                                    </td>
                                    <td colspan="2">
                                    <input id="boe_abstract" type="text" name="boe_abstract" readonly="readonly" 
                         			      	style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:25%" 
                                      value="<%= EmployeeMngt.getInstance().getEmployeeType(bClaim.getEmployLevel())%>" />
                                    </td>
                         				<td class="title" nowrap="nowrap" align="left" style="width:10%">报销币种</td>
                                <td align="left" colspan="2"><input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly"
                         					style="TEXT-ALIGN:center;BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:25%;text-align:right" 
                                  value="<%=CurrencyMngt.getInstance().getCurrencyName(bClaim.getCurrencyType())%>" />
                                
                                  </td>
                         					<td class="title" nowrap="nowrap" align="left" style="width:10%">冲账金额</td>
                                <td align="left" colspan="5"><input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly"
                         					style="TEXT-ALIGN:center;BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:20%;text-align:right" 
                                  value="<%= bClaim.getHedgeAccount()%>" />
                                  </tr>
                                  </TABLE>
  <TABLE width="100%" align="center" id="tblMainInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
    <tr><td align="left" colspan="13"><img class="icon" src="BoeWizardIndex_files/icon_note.gif" />报销行信息
        </td></tr>
                	  	<%
                  int flagOther = -1;
        	  if(bClaim != null)
            {
              if(bClaim.getItems()!=null)
              {              
                   flagOther = 1;
              } 
             }  
                
        if(flagOther==1)      { %>  

        <tr><td class="title" nowrap="nowrap"><div align="left">日期起</div>
        	  </td><td class="title" nowrap="nowrap"><div align="left">日期止</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left" nowrap="nowrap">出发城市</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left">出差省份</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left">出差市/县</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left">交通工具</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left">交通费</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left" nowrap="nowrap">住宿费</div></td>
        	  	<td class="title" nowrap="nowrap"><div align="left">公杂费</div></td>
        	  	</tr>
        	<% BClaimItem[] items = bClaim.getItems();
        	for(int i = 0; i < items.length; i++)
              {
                 BClaimItem item = items[i];
        	  	%>
        	  	<tr>
        	  		<td align="left" width="15%" nowrap="nowrap"><%= item.getStartDate()%></td>
        	  		<td width="15%" align="left" nowrap="nowrap"><%= item.getEndDate()%></td>
        	  		<td width="10%" align="left" nowrap="nowrap"><%= item.getStartCity()%></td>
        	  		<td width="10%" nowrap="nowrap"><%= LocationMngt.getInstance().getProvinceName(item.getDesProvince())%></td>
        	  		<td width="10%" align="left" nowrap="nowrap"><%= item.getDesCity()%></td>
        	  		<td width="10%"><%= keyValueMngt.getInstance().getTransport(item.getTransportation())%></td>
        	  		<td width="10%"><%= item.getTransportCost()%></td>
        	  		<td width="10%"><%= item.getAccommodation()%></td>
        	  		<td width="10%"><%= item.getOtherCost()%></td>        	  		
        	  		</tr>
        	  		 <%
                 }
              }
             
            %>
        	  	</TABLE>
        	<TABLE width="100%" align="center" id="tblButtonInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
        		<tr>
        		<% if(flagOther ==1 ){ %>
        			<td class="bottom" colspan="10" align="left">
        			</td>
        			<%}%>
        		</tr>
        		<TR>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">报销总金额</div></td>
        			<% if(flagOther ==1 ){ %>
        			<td colspan="7" style="width:85%" ><%= bClaim.getTotalFee()%></td>
        			<input  name="totalFee" value=<%= bClaim.getTotalFee()%> type="hidden" style="border: none; width: 200px" >
        			<%} else
        			{%>
        			<td colspan="7" style="width:85%" ><%= bClaim.getTotalFeeOther()%></td>        			
        			<input  name="totalFee" value=<%= bClaim.getTotalFeeOther()%> type="hidden" style="border: none; width: 200px" >
        			<%
        			}
        			%>
        			
        		</TR>
        		<% if(flagOther ==1 ){ %>
        		
        		<tr>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">交通费</div></td>
        			<td colspan="3" style="width:35%"><%= bClaim.getTransportCost()%></td>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">住宿费</div></td>
        			<td colspan="3" style="width:35%"><%= bClaim.getAccommodation()%></td>
        		</tr>
        		<tr>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">公杂费</div></td>
        			<td colspan="3" style="width:35%"><%= bClaim.getOtherCost()%></td>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">生活补贴</div></td>
        			<td colspan="3" style="width:35%"><%= bClaim.getAllowance()%></td>
        		</tr>
        		
        		<%}%>
            </TABLE>
            </BODY>
              </TD>
                </TR>
                <TR>
                    <TD vAlign="top">
                    
                        <div id="divPayment"><HEAD><TITLE>............</TITLE></HEAD><BODY>
          <TABLE width="100%" align="center" id="tblInsideInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
            <tr>
              <td width="90%"><div nowrap="nowrap">付款信息<input type="radio" checked="checked" id="personal_flag" name="personal_flag" value="Y" />个人帐户</td>
            </tr>
          </TABLE>
          <TABLE width="100%" align="center" id="tblPaymentInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
            <tr><td colspan="13"><div nowrap="nowrap"><img class="icon" src="BoeWizardIndex_files/icon_note.gif" />内部付款</div></td></tr>
            <tr>
              <td class="title" nowrap="nowrap" colspan="3" align="left">员工编号</td>
              <td class="title" nowrap="nowrap" align="left" colspan="2">名称</td>
              <td class="title" nowrap="nowrap" align="left">支付方式</td>
              <td class="title" nowrap="nowrap" align="left">币种</td>
              <td class="title" nowrap="nowrap" align="center">金额</td>
            </tr>
            <tr>
              <td align="left" width="10%" colspan="3"><div nowrap="nowrap"><%= userInfo.getAllNo()%></div></td>
              <td width="10%" align="left" colspan="2"><%= userInfo.getName()%></td>
              <td width="10%"><%= keyValueMngt.getInstance().getPayType(bClaim.getPayType())%></td>
              <td width="10%">CNY</td>
            	  <% if(flagOther ==1 ){ %>
            	<td width="10%" align="left"><%= bClaim.getTotalFee()%></td> 
            	<%}  else{%>
            	
            	<td width="10%" align="left"><%= bClaim.getTotalFeeOther()%></td>
            	<%}%>
            </tr>
           </TABLE>

<TABLE id=Table1 class=inputbl border=0 cellSpacing=0 cellPadding=0 width="100%" 
align=center>
  <TBODY>
  <TR>
    <TD style="BORDER-RIGHT-STYLE: none; WIDTH: 16px" class=bottom><IMG 
      class=icon src="FinanceApprovalList_files/icon_note.gif"></TD>
    <TD style="BORDER-LEFT-STYLE: none" class=bottom_left><SPAN 
      id=lblApprovalList>审批流程</SPAN></TD></TR></TBODY></TABLE>
      
<TABLE id=tblApprovalList border=0 cellSpacing=0 cellPadding=0 width="100%" 
align=center>
  <TBODY>
  <TR>
    <TD align=left>
      <TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse" id=dgActive 
      class=datatbl border=1 rules=all cellSpacing=0>
        <TBODY>
        <TR class=head>
          <TD style="WIDTH: 5%" align=left>序号</TD>
          <TD noWrap align=left>审批领导</TD>
          <TD noWrap align=left>审批职务</TD>
          <TD noWrap align=left>审批时间</TD>
          <TD noWrap align=left>审批状态</TD>
          <TD noWrap align=left>审批意见</TD></TR>
       <%
          HashMap<Integer, String> roleInfo = (HashMap<Integer, String>)request.getAttribute("roleInfo");
          //HashMap<String, DUser> userInfoNo = (HashMap<String, DUser>)request.getAttribute("userInfoNo");
          //ArrayList<DApprovalInfo> dApprovalInfoList = (ArrayList<DApprovalInfo>)request.getAttribute("dApprovalInfoList");
          if(dApprovalInfoList!=null && dApprovalInfoList.size()>0)
          {
             for(int index = 0; index < dApprovalInfoList.size(); index++)
             {
            	 DApprovalInfo dApprovalInfo =dApprovalInfoList.get(index);
       %>
               <TR class=item>
                  <TD style="WIDTH: 5%" noWrap align=left><SPAN id=dgActive__ctl2_Label1><%= index+1 %></SPAN></TD>
                  <TD noWrap>
                      <SPAN d=dgActive__ctl2_lbl_ApprovalName>
                        <%= dApprovalInfo.getApprovalName()%><%= dApprovalInfo.getApprovalId()%>
                      </SPAN>
                      <INPUT id=hiApprovalName<%= index%> size=1 type=hidden name=hiApprovalName<%= index%> value=<%= dApprovalInfo.getApprovalName()%>>
                  </TD>
                  <TD noWrap>
                      <SPAN d=dgActive__ctl2_lbl_PosElementName>
                        <%= RoleMngt.getInstance().getAllRoleInfo().get(UserMngt.getInstance().getUserInfoMap().get(dApprovalInfo.getApprovalId()).getRoleId()) %>
                      </SPAN>
                  </TD>
                  <TD noWrap>
                      <SPAN d=dgActive__ctl2_lbl_ActiveDate>
                        <%= dApprovalInfo.getAppDate()%></SPAN>
                  </TD>
                  <TD noWrap>
                      <SPAN id=appStatus<%= index%>>
                        
                       审批完毕 
                      </SPAN>
                  </TD>
                  <TD noWrap>
                      <SPAN d=dgActive__ctl2_lbl_Note>
                        <%= dApprovalInfo.getComment()%></SPAN>
                  </TD>
                </TR>
            <%
             }   
         }
            %>
</TBODY></TABLE></TD></TR></TBODY></TABLE>  

<div>
<br><br>
</div>

<TABLE id=tblApprovalAmount border=0 cellSpacing=0 cellPadding=0 align=center bgColor="#1475b3" class="inputbl">
  <TR>
      <TD class="title" noWrap style="width:65px; ">审批金额<font color="red">*</font></TD>
      <TD align=left>
       <% 
            float apprAmount = 0;
            if(bClaim.getApprovalAmount() == 0)
            {
                 apprAmount = bClaim.getTotalFee();
            }
            else
            {
                 apprAmount = bClaim.getApprovalAmount();
            }
        %>
        <INPUT style="WIDTH: 85px;HEIGHT: 24px" id=txtApprovalAmount name=txtApprovalAmount type="text" value=<%=apprAmount%> disabled>
      </TD>
  </TR>
</TABLE>

<div>
<br><br>
</div>

<TABLE id=tblApprovalOpinion border=0 cellSpacing=0 cellPadding=0 width="100%" align=center bgColor="#1475b3" class="inputbl" style="display:none">
  <TR>
      <TD class="title" noWrap style="width:65px">审批意见<font color="red">*</font></TD>
      <TD align=left>
       <textarea id="comment" name="comment" cols="162" rows="5"></textarea>
      </TD>
  </TR>
</TABLE> 

<TABLE id=tblApprovalBtn border=0 cellSpacing=0 cellPadding=0 width="100%" align=center style="display:none">
 <TR>         
    <TD class=bottom colSpan=6>
      <DIV align=center> 
      <INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnApprovalOne class=stbtm01 value=批准 type=button name=btnApprovalOne onclick="approvalOne(1)"> 
      <INPUT style="WIDTH: 50px; HEIGHT: 30px" id=btnNoApprovalOne class=stbtm01 value=退回 type=button name=btnNoApprovalOne onclick="approvalOne(0)"> 
      </DIV>
    </TD>
 </TR>
</TABLE>

<SCRIPT language=javascript>
    function init()
    {
       var display = <%=request.getParameter("displayFlag")%>;
       
       if(display == 1)
       {
           document.getElementById("tblApprovalOpinion").style.display = "";
           document.getElementById("tblApprovalBtn").style.display = "";
           document.getElementById("txtApprovalAmount").disabled = false;
       }
       else if(display == 0)
       {
           document.getElementById("tblApprovalOpinion").style.display = "none";
           document.getElementById("tblApprovalBtn").style.display = "none";
           document.getElementById("txtApprovalAmount").disabled = true;
       }

       //if(document.getElementById("hiApprovalName0") != null)
      // {
          // var firstApprovalName = document.getElementById("hiApprovalName0").value;
           //if(firstApprovalName != "" || firstApprovalName != null)
           //{
              // document.getElementById("lblFeeTypeName1").innerHTML = firstApprovalName
           //}
          // else 
           //{
          // document.getElementById("lblFeeTypeName1").innerHTML = "";          
          // }
       //}
       //else
      // {
        ////   document.getElementById("lblFeeTypeName1").innerHTML = "//userInfoNo.get(bClaim.getApprovalId()).getName()";
       //}    
     
    }
    
    function appAmountOrCommentIsNull()
    {
        var appAmount = document.getElementById("txtApprovalAmount").value;
        if(appAmount == '' || appAmount == null)
        {
             alert('审批金额不允许为空，请重新输入！');
             appFlag = 0
             return false;
        }
        
        var comment = document.getElementById("comment").value;
        if(comment == '' || comment == null)
        {
             alert('审批意见不允许为空，请重新输入！');
             appFlag = 0
             return false;
        }

        return true;
    }
    
    function approvalOne(flag)
    {
        var appFlag = 0;
        
        var varStr = "action=approvalOne";
        varStr += "&InvoiceNoDe=";
        varStr += document.getElementById("lblBoe_Num").innerHTML;
                
        varStr += "&ApprovalAmount=";
        varStr += document.getElementById("txtApprovalAmount").value;
        
        varStr += "&comment=";
        varStr += document.getElementById("comment").value;
        
        varStr += "&lblEmployeeNo=";
        varStr += document.getElementById("employeeNo").value;        
        
        varStr += "&invoiceType=";
        varStr += document.getElementById("invoiceType").value;                

        if(appAmountOrCommentIsNull())
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
        if(appFlag == 1)
        {
            window.top.needAlert = true;
            Form1.action = "financeApproval.do?" + varStr;
            Form1.submit();
        }
    }
    
    init();
</SCRIPT>

</form>
</body>
</HTML>