<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page language="java" import="java.util.*,Business.Claims.*"%>
<HTML>
	<HEAD>
		<TITLE>�ұ����ĵ���</TITLE>
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		<META name=GENERATOR content="MSHTML 8.00.6001.23501">
		<META name=CODE_LANGUAGE content=C#>
		<META name=vs_defaultClientScript content=JavaScript>
		<META name=vs_targetSchema
			content=http://schemas.microsoft.com/intellisense/ie5>
		<LINK rel=stylesheet type=text/css
			href="MyProposerIndex_files/style.css">
		<LINK rel=stylesheet type=text/css href="css/pikaday.css">
		<LINK rel=stylesheet type=text/css href="Pager/Page.css">
		<SCRIPT language=javascript src="MyProposerIndex_files/CommonFun.js"></SCRIPT>

		<SCRIPT language=javascript
			src="MyProposerIndex_files/FbpLovSelect.js"></SCRIPT>

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
		function clear_text()
		{
			document.getElementById("txtInvoiceNo").value="";
			document.getElementById("deptID").value="-1";	
			document.getElementById("typeID").value="-1";
			document.getElementById("txtSubmitDateFrom").value="";
			document.getElementById("txtSubmitDateTo").value="";	
			document.getElementById("statusID").value="-1";	
			document.getElementById("txtBillNo").value="";
			document.getElementById("txtPayFrom").value="";
			document.getElementById("txtPayTo").value="";
			return false;
		}
		
		function query()
        {
        	if(check())
			{
	        	Form1.action = "myClaim.do";
	        	Form1.submit();
			}
        }
        
        function checkIsValidDate(str)
{   
    var result;
    var ntemp;
    //���Ϊ�գ���ͨ��У��
    if(str == "")
        return "";
    var pattern = /^((\d{4})|(\d{2}))-(\d{1,2})-(\d{1,2})$/g;
    if(!pattern.test(str))
        return "INVALID DATE";    
}
        
        function check()
        {
        	var dateFrom = document.getElementById("txtSubmitDateFrom").value;
        	var dateTo = document.getElementById("txtSubmitDateTo").value;
        	var  result = checkIsValidDate(dateFrom);
  if (result=="INVALID DATE") {      
    alert("��Ч�Ŀ�ʼ����ֵ,����������!");
    return false;
  }
  
  result = checkIsValidDate(dateTo);
  //alert(result);
  if (result=="INVALID DATE") { 
    alert("��Ч�Ľ�������ֵ,����������!");
    return false;
  }
        	if( dateTo < dateFrom)
        	{
        	    alert("�������ڲ���С�ڿ�ʼ���ڣ�����������");
        	    return false;
        	}
        	
        	var payFrom = document.getElementById("txtPayFrom").value;
        	var payTo = document.getElementById("txtPayTo").value;
        	if( payTo < payFrom)
        	{
        	    alert("��󱾱ҽ���С����С���ҽ��");
        	    return false;
        	}
        	
        	return true;   
        }
		     
		
		function showBpHis(bpNum)
		{
		    var resourceID=document.all("hiResourceID").value;
			var menuPath=document.all("hiMenuPath").value;
			var url="../../BpManage/Pages/BpActiveHis.aspx?resourceID="+resourceID+"&menuPath="+menuPath;
			url+="&bpnum="+bpNum + "&pflag=MyProposerIndex";
		    //window.navigate(url);
		    parent.ShowPage(0,url);
		}
        
    </SCRIPT>
	</HEAD>
	<BODY>

		<FORM id=Form1 method=post name=Form1 action=myClaim.do>
			<SCRIPT type=text/javascript>
<!--
var theForm = document.forms['Form1'];
if (!theForm) {
    theForm = document.Form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}

// -->
</SCRIPT>
			<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0
				align=center>
				<TBODY>
					<TR height=24>
						<TD>
							<IMG class=icon src="MyProposerIndex_files/bullet0.gif" height=10>
							<SPAN id=TableHeadBar1_lblHead>��ǰλ��<SPAN class=11air>:</SPAN>�����й�<SPAN
								class=arrow_subtitle>&gt;</SPAN>�ҵĵ���<SPAN class=arrow_subtitle>&gt;</SPAN>�ұ�����</SPAN>
						</TD>
						<TD width=16 noWrap align=right>
							<IMG style="CURSOR: hand" id=TableHeadBar1_BarImg class=icon
								onclick=javascript:ExpandDiv(this);
								src="MyProposerIndex_files/icon_collapseall.gif" height=16
								data="1">
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<BR style="LINE-HEIGHT: 1px">
			<DIV style="WIDTH: 98%" id=divSearch>
				<TABLE id=queryPane class=inputbl border=0 cellSpacing=0
					cellPadding=0 width="100%" align=center>
					<TBODY>
						<TR>
							<TD class=title nowrap="" height=24>
								���ݺ�
							</TD>
							<TD>
								<INPUT style="WIDTH: 170px; HEIGHT: 21px" id="txtInvoiceNo"
									name=txtInvoiceNo>
							</TD>
							<TD class=title nowrap="" height=24>
								����
							</TD>
							<TD>
								<select id="deptID" name="deptID" style="WIDTH: 170px">
									<%    
        HashMap<Integer, String> deptInfoMap = (HashMap<Integer, String>)request.getAttribute("DeptInfos");
        Iterator<Integer> deptIterator = deptInfoMap.keySet().iterator();
  	    while(deptIterator.hasNext()) { 
    		   int key = deptIterator.next();
    %>
									<option selected="" value="<%= key %>"><%= deptInfoMap.get(key)%></option>
									<%
     }
   %>
									<option selected="selected" value="-1"></option>
								</select>
							</TD>
							<TD class=title nowrap="" height=24>
								��������
							</TD>
							<TD>
								<select id="typeID" name="typeID" style="WIDTH: 170px">
									<%    
        HashMap<Integer, String> typeInfoMap = (HashMap<Integer, String>)request.getAttribute("TypeInfos");
        Iterator<Integer> typeIterator = typeInfoMap.keySet().iterator();
  	    while(typeIterator.hasNext()) { 
    		   int key = typeIterator.next();
    %>
									<option selected="" value="<%= key %>"><%= typeInfoMap.get(key)%></option>
									<%
     }
   %>
									<option selected="selected" value="-1"></option>
								</select>
							</TD>
						</TR>
						<TR>
							<TD class=title nowrap="" height=24>
								�ύ������
							</TD>
							<TD>
								<INPUT style="WIDTH: 76px; HEIGHT: 21px" id="txtSubmitDateFrom"
									name=txtSubmitDateFrom>
								��
								<INPUT style="WIDTH: 76px; HEIGHT: 21px" id="txtSubmitDateTo"
									name=txtSubmitDateTo>
							</TD>
							<TD class=title nowrap="" height=24>
								����״̬
							</TD>
							<TD>
								<select id="statusID" name="statusID" style="WIDTH: 170px">
									<!--0���ݸ壬1����ҵ��������2�����쵼��ǩ��3��������������4���رգ�5�����˻غ��״̬  -->
									<option selected="" value="0">
										�ݸ�
									</option>
									<option selected="" value="1">
										��ҵ������
									</option>
									<option selected="" value="2">
										���쵼��ǩ
									</option>
									<option selected="" value="3">
										����������
									</option>
									<option selected="" value="4">
										�ر�
									</option>
									<option selected="" value="5">
										���˻�
									</option>
									<option selected="selected" value="-1"></option>
								</select>
							</TD>
							<TD class=title nowrap="" height=24>
								Ʊ�ݺ���
							</TD>
							<TD>
								<INPUT style="WIDTH: 170px; HEIGHT: 21px" id="txtBillNo"
									name=txtBillNo>
							</TD>
						</TR>
						<TR>
							<TD class=title nowrap="" height=24>
								���ҽ��
							</TD>
							<TD>
								<INPUT style="WIDTH: 76px; HEIGHT: 21px" id=txtPayFrom
									name=txtPayFrom>
								��
								<INPUT style="WIDTH: 76px; HEIGHT: 21px" id=txtPayTo
									name=txtPayTo>
							</TD>
						</TR>
						<TR>
							<TD class=bottom colSpan=6 align=middle>
								<INPUT id=hiDeptID size=1 type=hidden name=hiDeptID>
								<INPUT id=hiUserID size=1 type=hidden name=hiUserID>
								&nbsp;
								<INPUT id=btnQuery class=stbtm01 value=��ѯ type=button
									name=btnQuery onclick="query()">
								<INPUT id=hiEmployeeID size=1 type=hidden name=hiEmployeeID>
								<INPUT id=hiProdProjectID size=1 type=hidden
									name=hiProdProjectID>
								<INPUT id=btnClear language=javascript class=stbtm01
									onclick="javascript:return clear_text();" value=��� type=button
									name=btnClear>
								<INPUT style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture
									value=zh-CN size=1 type=hidden name=hiCulture>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
		</FORM>
		<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0
			width="98%" bgColor=#ffffff>
			<TBODY>
				<TR>
					<TD class=tb_titlebar colSpan=6 align=left>
						<IMG src="MyProposerIndex_files/bullet1.gif" width=16 height=16>
						<SPAN id=lblApprovalList>�����б�</SPAN>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<DIV style="WIDTH: 98%;" class=autoOver2 align=center>
			<TABLE id=Table2 border=0 cellSpacing=0 cellPadding=1 width="100%">
				<TBODY>
					<TR>
						<TD>
							<TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval
								class=datatbl border=1 rules=all cellSpacing=0>
								<TBODY>
									<TR class=head>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl0','')">���ݺ�</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl1','')">��������</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl2','')">������</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl3','')">������Ŀ</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl4','')">�ύ����</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl5','')">��������</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl6','')">����״̬</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl7','')">������������</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl8','')">Ʊ�ݺ���</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl9','')">ժҪ</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl10','')">������</A>
										</TD>
										<TD style="WIDTH: 8%" noWrap align=left>
											<A
												href="javascript:__doPostBack('dgApproval$_ctl1$_ctl11','')">��˽��</A>
										</TD>
									</TR>

									<%    
          ArrayList<BMyClaim> claimList = (ArrayList<BMyClaim>)request.getAttribute("claimInfos");
          if(claimList.size()==0)
          {
           %>
									<TR class=item>
										<TD width="100%" align=left colspan="12">
											<FONT color=red>�Բ���û�з��������ļ�¼! </FONT>
										</TD>
									</TR>
									<%
          }
          else{
          for(int i = 0; i < claimList.size(); i++){
      		   BMyClaim claimInfo = claimList.get(i);
       %>
									<TR class=item>
										<TD noWrap align=left>
											<a href="finInvoiceDetail.do?displayFlag=0&InvoiceNoDe=<%= claimInfo.getInvoiceNo()%>" ><%= claimInfo.getInvoiceNo()%></a>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getDeptName()%>
										</TD>
										<TD noWrap align=left>
											<%= request.getAttribute("userName")%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getProductName()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getSubmitDate()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getPayTypeStr()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getStatusStr()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getEndDate()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getBillNo()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getSummary()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getTotalFee()%>
										</TD>
										<TD noWrap align=left>
											<%= claimInfo.getCheckFee()%>
										</TD>

									</TR>
									<%
           }}
         %>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		
	</BODY>
	<script type="text/javascript">
 
	var pickerFrom = new Pikaday( {
		field : document.getElementById("txtSubmitDateFrom"),
		firstDay : 1,
		minDate : new Date('2000-01-01'),
		maxDate : new Date('2030-12-31'),
		yearRange : [ 2000, 2030 ]
	});

	var pickerTo = new Pikaday( {
		field : document.getElementById("txtSubmitDateTo"),
		firstDay : 1,
		minDate : new Date('2000-01-01'),
		maxDate : new Date('2030-12-31'),
		yearRange : [ 2000, 2030 ]
	});
	
	var pager = new Pager("dgApproval");
	
</script>
</HTML>
