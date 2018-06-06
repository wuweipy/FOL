<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<!-- saved from url=(0111)http://fol.zte.com.cn/Admin/Employee/EmpBankAccountInfo.aspx -->
<HTML><HEAD><TITLE>EmpBankAccountInfo</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="EmpBankAccountInfo_files/style.css">
<SCRIPT language=javascript 
src="EmpBankAccountInfo_files/AdminPageFunction.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountInfo_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountInfo_files/FbpLovSelect.js"></SCRIPT>

<SCRIPT language=javascript src="EmpBankAccountInfo_files/Calendar.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountInfo_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript>
    <!--
        function delConfirm()
        {
            return confirm("确实要删除此项吗？");
        }
    //-->
    
       function newAccount()
       {
       	  window.location.href = "bankAccount.do?action=add";
       }
    </SCRIPT>
</HEAD>
<BODY>
<FORM id=Form1 method=post name=Form1>
 
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
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="EmpBankAccountInfo_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN 
      class=arrow_subtitle>&gt;</SPAN>我的信息<SPAN 
      class=arrow_subtitle>&gt;</SPAN>报销帐户</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="EmpBankAccountInfo_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV id=divSearch>
<TABLE class=tb_titlebar border=0 cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
  <TR height=24>
    <TD width=16><IMG src="EmpBankAccountInfo_files/bullet1.gif" height=16></TD>
    <TD>内部付款账户： </TD>
    <TD align=right><INPUT id=btnAddInner class=stbtm01 value=新增 type="button" sytle="padding: 1px 6px;" onclick="newAccount()" name=btnAddInner></TD></TR></TBODY></TABLE>
<TABLE style="BORDER-COLLAPSE: collapse" id=dtgInnerList class=datatbl border=1 
rules=all cellSpacing=0>
  <TBODY>
  <TR class=head>
    <TD style="WIDTH: 5%" noWrap>省份</TD>
    <TD style="WIDTH: 10%" noWrap>城市</TD>
    <TD style="WIDTH: 10%" noWrap>银行类别</TD>
    <TD style="WIDTH: 10%">币种</TD>
    <TD>开户银行</TD>
    <TD style="WIDTH: 10%" noWrap>收款户名</TD>
    <TD style="WIDTH: 10%" noWrap>收款帐号</TD>
    <TD style="WIDTH: 80px">操作</TD></TR>
  <TR class=item>
<% String[] bankAccountInfo = (String[])(request.getAttribute("bankAccountStrings"));
   if(bankAccountInfo != null)
   {
   %>
    <TD noWrap><%= bankAccountInfo[0]%></TD>
    <TD noWrap><%= bankAccountInfo[1]%></TD>
    <TD noWrap><%= bankAccountInfo[2]%></TD>
    <TD><%= bankAccountInfo[3]%></TD>
    <TD><%= bankAccountInfo[4]%></TD>
    <TD noWrap><%= bankAccountInfo[5]%></TD>
    <TD noWrap><%= bankAccountInfo[6]%></TD>
    <TD align=middle><a href="bankAccount.do?action=edit">修改</a> <a href="bankAccount.do?action=delete">删除</a> 
    </TD>
    <%
    }
    %>
    </TR></TBODY></TABLE></DIV>

<!-- 隐藏域 开始 --><INPUT 
id=hiResourceID value=1523 type=hidden name=hiResourceID> <INPUT id=hiMenuPath 
type=hidden name=hiMenuPath> <!-- 隐藏域 结束 -->
<SCRIPT language=javascript>
<!--
function ExpandDiv(imgObject)
{
if(imgObject.data == "1")
{
	imgObject.data = "0";
	imgObject.src = "../../Common/Images/icon_expandall.gif";
}
else
{
	imgObject.data = "1";
	imgObject.src = "../../Common/Images/icon_collapseall.gif";
}
if(document.all["divSearch"] != null)
	document.all["divSearch"].style.display = (imgObject.data == "0")?"none":"block";
}
	 //-->
</SCRIPT>
</FORM></BODY></HTML>
