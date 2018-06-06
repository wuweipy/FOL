<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page contentType="text/html;charset=GB2312" %>
<%@ page language="java" import="java.util.*,Business.UserDetail.BUserDetail,Business.Claims.*,Common.*,Common.Location.*"%>
<HTML>
    <HEAD>
        <title>..................</title>
        <LINK href="BoeWizardIndex_files/style.css" type="text/css" rel="stylesheet">
    </HEAD>
    <body>
        <form name="Form1" method="post" action="boe.do">
        	<input type="hidden" id="save" name="save">
  <script>
  <%
	  Object isSucess = request.getAttribute("isSucess");
	  if(isSucess != null)
	   if((Boolean)isSucess)
	  {
  %>
     alert("提交成功!");
  <%	     
	  }
	else
		{
  %>
     alert("提交失败!");
  <%	
		}
	%>
	</script>
        	<TABLE id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0">
                <TR height="1">
                    <TD><FONT face="......">
<table class="tabbar1" cellspacing="0" cellpadding="0" align="center" border="0">
    <tbody>
        <tr height="24">
            <td>
                <img class="icon" height="10" src="BoeWizardIndex_files/bullet0.gif">
                <span id="TableHeadBar1_lblHead">当前位置<SPAN class="11air">:</SPAN>报销管理<span class="arrow_subtitle">&gt;</span>单据制作<span class="arrow_subtitle">&gt;</span>在线填单</span>
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
                        <div id="divSearch">
                            <TABLE class="inputbl" id="queryPane" cellSpacing="1" cellPadding="1" width="100%" align="center"
                                border="0">
                                <TR>
                                    <TD id="tdLogo" class="logontitle" width="14%" rowSpan="6">
                                        <DIV align="center">
                                            <P class="zteLightBlue" align="center"><span id="lblCompanyBill"><%= courseMap.get(bClaim.getInvoiceType())%>报销单</span></P>
                                        </DIV>
                                    </TD>

                                    <TD class="title" height="23" style="width: 150px"><span id="lblBoeNum">单据号</span></TD>
                                    <TD class="smallInput" align="left" bgColor="#efefef" height="23"><span id="lblBoe_Num"><%= bClaim.getInvoiceNo()%></span></TD>
                                    <TD class="title" height="23"><span id="lblBillPractNum">实物票据号</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23">
                                        <span id="lblBpNum">
                                        	<%= bClaim.getBillNo()%>
                                        	</span></TD>
                                </TR>
                                <tr>
                                    <TD class="title" height="23"><span id="lblEN">报销人姓名</span></TD>
                                    <TD class="smallInput" align="left" bgColor="#efefef" height="23"><span id="lblEmployeeName">

                                    	<%= userInfo.getName()%>
                                    	</span></TD>
                                    <TD class="title" height="23"><span id="lblEnum">报销人工号</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23"><span id="lblEmployeeNumber"><%= userInfo.getAllNo()%></span></TD>
                                </tr>
                                <TR>
                                    <TD class="title" width="8%" height="23"><span id="lblDN">所属部门</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23"><span id="lblDeptName"><%= userInfo.getDept()%></span></TD>
                                    <TD class="title"><span id="lblPP">报销项目</span></TD>
                                    <TD align="left" bgColor="#efefef"><span id="lblProdProjectName"><%= bClaim.getProductName()%></span></TD>
                                </TR>
                                <tr>
                                     <TD class="title" width="8%" height="23"><span id="lblDN">报销子项目</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23"><span id="lblDeptName"><%= bClaim.getSubProductName()%></span></TD>
                                    <TD class="title"><span id="lblPP">当前审批人</span></TD>
                                    <TD align="left" bgColor="#efefef"><span id="lblProdProjectName"><%= bClaim.getApprovalName()%></span></TD>
                                </tr>                                  
                                
                                
                            </TABLE>
                        </div>
                                                
                        
                        
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
                         			<div align="left">摘要<font color="red">*</font></div>
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
                         			         <div align="left">员工级别<font color="red">*</font></div>
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
        	  	</TABLE>
        	<TABLE width="100%" align="center" id="tblButtonInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
        		<TR>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">报销总金额</div></td>
        			<td colspan="7" style="width:85%"><%= bClaim.getTotalFeeOther()%></td>
        		</TR>
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
        			<td class="title" nowrap="nowrap" colspan="3" align="left">员工编号<font color="red">*</font></td>
        			<td class="title" nowrap="nowrap" align="left" colspan="2">名称</td>
        			<td class="title" nowrap="nowrap" align="left">支付方式<font color="red">*</font></td>
        			<td class="title" nowrap="nowrap" align="left">币种<font color="red">*</font></td>
        			<td class="title" nowrap="nowrap" align="center">金额</td>
            </tr>
            <tr>
            	<td align="left" width="10%" colspan="3"><div nowrap="nowrap"><%= userInfo.getAllNo()%></div></td>
            	<td width="10%" align="left" colspan="2"><%= userInfo.getName()%></td>
            	<td width="10%"><%= keyValueMngt.getInstance().getPayType(bClaim.getPayType())%></td>
            	<td width="10%">CNY</td>
            	<td width="10%" align="left"><%= bClaim.getTotalFeeOther()%></td>
            </tr>
           </TABLE>
           <% 
               Boolean submit = (Boolean)request.getAttribute("submit");
               if( submit == false ) { 
           %>
          	<TABLE width="100%" align="center" id="tblSubmitInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
          		<tr id="trSubmit">
          			<td colspan="2" align="right">
          				<input id="btnPrev" value="上一步" class="stbtm01" onclick="goto_prev()" type="button" />
          				<!--<input id="btnSaveAsDraft" value="另存为草稿" class="stbtm01" onclick="save_as_draft(1)" type="button" />-->
          				<input id="btnSubmit" value="提交" class="stbtm01" onclick="submit_data()" type="button" />
          			</td>
          		</tr>
          		</table>
          		<% } %>
          		<script>
          		   function goto_prev()
          		   {
          		   	   document.getElementById("save").value = "pre";
                       Form1.submit();
          		   }
          		   
          		   function save_as_draft()
          		   {
          		   	
          		   }
          		   
          		   function submit_data()
          		   {
          		   	   document.getElementById("save").value = "save";
                       Form1.submit();
          		   }
          		</script>
          		
</form>
</body>
</HTML>