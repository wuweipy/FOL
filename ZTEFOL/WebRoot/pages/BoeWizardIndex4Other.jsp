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
     alert("�ύ�ɹ�!");
  <%	     
	  }
	else
		{
  %>
     alert("�ύʧ��!");
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
                <span id="TableHeadBar1_lblHead">��ǰλ��<SPAN class="11air">:</SPAN>��������<span class="arrow_subtitle">&gt;</span>��������<span class="arrow_subtitle">&gt;</span>�����</span>
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
                                            <P class="zteLightBlue" align="center"><span id="lblCompanyBill"><%= courseMap.get(bClaim.getInvoiceType())%>������</span></P>
                                        </DIV>
                                    </TD>

                                    <TD class="title" height="23" style="width: 150px"><span id="lblBoeNum">���ݺ�</span></TD>
                                    <TD class="smallInput" align="left" bgColor="#efefef" height="23"><span id="lblBoe_Num"><%= bClaim.getInvoiceNo()%></span></TD>
                                    <TD class="title" height="23"><span id="lblBillPractNum">ʵ��Ʊ�ݺ�</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23">
                                        <span id="lblBpNum">
                                        	<%= bClaim.getBillNo()%>
                                        	</span></TD>
                                </TR>
                                <tr>
                                    <TD class="title" height="23"><span id="lblEN">����������</span></TD>
                                    <TD class="smallInput" align="left" bgColor="#efefef" height="23"><span id="lblEmployeeName">

                                    	<%= userInfo.getName()%>
                                    	</span></TD>
                                    <TD class="title" height="23"><span id="lblEnum">�����˹���</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23"><span id="lblEmployeeNumber"><%= userInfo.getAllNo()%></span></TD>
                                </tr>
                                <TR>
                                    <TD class="title" width="8%" height="23"><span id="lblDN">��������</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23"><span id="lblDeptName"><%= userInfo.getDept()%></span></TD>
                                    <TD class="title"><span id="lblPP">������Ŀ</span></TD>
                                    <TD align="left" bgColor="#efefef"><span id="lblProdProjectName"><%= bClaim.getProductName()%></span></TD>
                                </TR>
                                <tr>
                                     <TD class="title" width="8%" height="23"><span id="lblDN">��������Ŀ</span></TD>
                                    <TD align="left" bgColor="#efefef" height="23"><span id="lblDeptName"><%= bClaim.getSubProductName()%></span></TD>
                                    <TD class="title"><span id="lblPP">��ǰ������</span></TD>
                                    <TD align="left" bgColor="#efefef"><span id="lblProdProjectName"><%= bClaim.getApprovalName()%></span></TD>
                                </tr>                                  
                                
                                
                            </TABLE>
                        </div>
                                                
                        
                        
                    </TD>
                </TR>
                <TR>
                    <TD vAlign="top">
                        <div id="divXml"><HEAD><TITLE>.................................</TITLE></HEAD><BODY><TABLE width="100%" align="center" id="tblMainHeader" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0"><tr><td colspan="6" align="left"><img class="icon" src="BoeWizardIndex_files/icon_note.gif" />
                         ����ͷ��Ϣ
                         </td>
                         </tr>
                         </TABLE>
                         <TABLE width="100%" align="center" id="tblMain" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
                         	<TR>
                         		<td class="title" nowrap="nowrap" style="width:10%">
                         			<div align="left">ժҪ<font color="red">*</font></div>
                         			</td>
                         			<td colspan="5">
                         				<input id="boe_abstract" type="text" name="boe_abstract" readonly="readonly" 
                         				style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:60%" 
                         				value="<%= bClaim.getSummary()%>" />
                         				</td>
                         				<td class="title" nowrap="nowrap" align="left" style="width:10%">��Ƶ���</td>
                         				<td align="left" colspan="5"><input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly"
                         					style="TEXT-ALIGN:center;BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:20%;text-align:right" 
                         					value="<%= bClaim.getAccountAdjust()%>" />
                         					</td>
                         	</TR>
                         					<tr>
                         						<td class="title" nowrap="nowrap" style="width:10%">
                         			         <div align="left">Ա������<font color="red">*</font></div>
                         			      </td>
                         			      <td colspan="2">
                         				    <input id="boe_abstract" type="text" name="boe_abstract" readonly="readonly" 
                         			      	style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:25%" 
                         				      value="<%= EmployeeMngt.getInstance().getEmployeeType(bClaim.getEmployLevel())%>" />
                         				    </td>
                         				<td class="title" nowrap="nowrap" align="left" style="width:10%">��������</td>
                         				<td align="left" colspan="2"><input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly"
                         					style="TEXT-ALIGN:center;BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:25%;text-align:right" 
                         					value="<%=CurrencyMngt.getInstance().getCurrencyName(bClaim.getCurrencyType())%>" />
                         				
                         					</td>
                         					<td class="title" nowrap="nowrap" align="left" style="width:10%">���˽��</td>
                         				<td align="left" colspan="5"><input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly"
                         					style="TEXT-ALIGN:center;BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:20%;text-align:right" 
                         					value="<%= bClaim.getHedgeAccount()%>" />
                         					</tr>
                         					</TABLE>
	<TABLE width="100%" align="center" id="tblMainInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
		<tr><td align="left" colspan="13"><img class="icon" src="BoeWizardIndex_files/icon_note.gif" />��������Ϣ
        </td></tr>
        	  	</TABLE>
        	<TABLE width="100%" align="center" id="tblButtonInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
        		<TR>
        			<td class="title" nowrap="nowrap" style="width:15%"><div align="left">�����ܽ��</div></td>
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
        			<td width="90%"><div nowrap="nowrap">������Ϣ<input type="radio" checked="checked" id="personal_flag" name="personal_flag" value="Y" />�����ʻ�</td>
        		</tr>
        	</TABLE>
        	<TABLE width="100%" align="center" id="tblPaymentInfo" bgColor="#1475b3" class="inputbl" border="0" cellPadding="0" cellSpacing="0">
        		<tr><td colspan="13"><div nowrap="nowrap"><img class="icon" src="BoeWizardIndex_files/icon_note.gif" />�ڲ�����</div></td></tr>
        		<tr>
        			<td class="title" nowrap="nowrap" colspan="3" align="left">Ա�����<font color="red">*</font></td>
        			<td class="title" nowrap="nowrap" align="left" colspan="2">����</td>
        			<td class="title" nowrap="nowrap" align="left">֧����ʽ<font color="red">*</font></td>
        			<td class="title" nowrap="nowrap" align="left">����<font color="red">*</font></td>
        			<td class="title" nowrap="nowrap" align="center">���</td>
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
          				<input id="btnPrev" value="��һ��" class="stbtm01" onclick="goto_prev()" type="button" />
          				<!--<input id="btnSaveAsDraft" value="���Ϊ�ݸ�" class="stbtm01" onclick="save_as_draft(1)" type="button" />-->
          				<input id="btnSubmit" value="�ύ" class="stbtm01" onclick="submit_data()" type="button" />
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