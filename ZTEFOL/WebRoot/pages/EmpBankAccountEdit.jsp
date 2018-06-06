<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0108)http://fol.zte.com.cn/Admin/Employee/EmpBankAccountEdit.aspx -->
<%@ page language="java" import="java.util.*,Common.Location.*,Business.BankAccount.*"%>
<%@ page contentType="text/html;charset=utf-8"%>
<HTML><HEAD><TITLE>BankInfo Edit</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<meta.http-equiv="X-UA-Compatible" content="IE=edge">
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="EmpBankAccountEdit_files/style.css">
<style type="text/css">
 
</style>
<SCRIPT language=javascript 
src="EmpBankAccountEdit_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountEdit_files/LovSelect.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountEdit_files/PageElementCheck.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountEdit_files/FbpLovSelect.js"></SCRIPT>

<SCRIPT language=javascript 
src="EmpBankAccountEdit_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript>
	  <%
  	   HashMap<Integer, String> currencyInfo = (HashMap<Integer, String>)request.getAttribute("currencyInfo");
  	   Iterator<Integer> currencyIterator = currencyInfo.keySet().iterator();
  	   HashMap<Integer, BProvince> provinceInfo = (HashMap<Integer, BProvince>)request.getAttribute("provinceInfo");
  	   Iterator<Integer> provinceIterator = provinceInfo.keySet().iterator();
  	   HashMap<Integer, String> bankInfo = (HashMap<Integer, String>)request.getAttribute("bankInfo");
  	   Iterator<Integer> bankIterator = bankInfo.keySet().iterator();
  	%>

	    var vcities = {
  	 	    <% while(provinceIterator.hasNext()) { 
    		   int key = provinceIterator.next();
    	    %>
    	    "<%= provinceInfo.get(key).getName()%>" :{
    	    	<% BProvince province = provinceInfo.get(key);
    	    	   for(int i = 0; i < province.getCities().size(); i++)
    	    	   {
    	    	%>
    	    	     "<%= province.getCities().get(i).getId()%>":
    	    	     "<%= province.getCities().get(i).getName()%>"
    	    	     <% if(i < province.getCities().size() - 1) {%>
    	    	     	,
    	    	    <%}%>
    	    	           
    	    	   <%
    	    	   }
    	    	   %>
    	    }
    	    <% 
    	    if(provinceIterator.hasNext())
    	    {
    	    %>
    	    ,
  	      <%
  	        }
    	    } 
    	    provinceIterator = provinceInfo.keySet().iterator();
    	   %>
    	   
  	  };	
  	  	  
  	  function selectChange()
		  {
			 var provSelect = document.getElementById("province");
			 var province = provSelect.options[provSelect.selectedIndex].text;
			 var cities = vcities[province];
			 var citySelect = document.getElementById("selectCity");
			 clearOptions(citySelect.options);
			 for(var key in cities)
			 {
			 	  var option = new Option(cities[key],key);
			 	  citySelect.options.add(option);
			 }
			 /**
			 for(var i = 0; i < cities.length; i++)
			 {
			 	  var city = cities[i];
			 	  var option = new Option(city.key,city[city.key]);
			 	  citySelect.options.add(option);
			 }
			 */
		}
		
		  function clearOptions(colls){
        var length = colls.length;
        for(var i = length-1 ; i >= 0; i--)
        {
               colls.remove(i);
        }

       }
       
       function save()
       {
       	  <%
       	    if(request.getAttribute("action").equals("edit"))
       	    {
       	   %>
       	      Form1.action = "bankAccount.do?action=edit";
       	   <%
       	    }
       	    else
       	  	{
       	   %>
       	  	  Form1.action = "bankAccount.do?action=add";
       	  <%
       	  	}
       	  %>
       	  Form1.submit();
       }
       
       function back()
       {
       	  window.location.href = "bankAccount.do";
       }
	    
		 function SelectCity()
		 {
			var province = "";
			if(document.all("hiProvince")!=null)
			{
				province = document.all("hiProvince").value;
			}
			//if(province == ""){alert(getSieMessage("SIE_BOE_CHINA_PROVINCE",culture));return;}
			var param = "provincecode="+province;
		    SelLovWithParams("selPaymentChinaCity","hiCity","txtCity",480,400,param);
		 }
		 function SelectProvince()
		 {
			var param = "seltype=9";
			param+="&dtype=1";
			SelLovWithParams("selPaymentTerritory","hiProvince","txtProvince",480,400,param);
		 }
		
		function CheckInput()
		{
		    if(document.getElementById("txtProvince").value == ''
		       || document.getElementById("txtCity").value == '' 
		       || document.getElementById("txtProvince").value == '' 
		       || document.getElementById("txtAccountBank").value == '' 
		       || document.getElementById("txtAccountName").value == '' 
		       || document.getElementById("txtAccountNumber").value == ''
		       || document.getElementById("dplCurrency").value == ''
		       || document.getElementById("dptBankType").value == '')
		    {
		        alert('请输入必输项');
		        return false;
		    }
		    // 正则匹配收款帐号为纯数字
		    var str=document.getElementById("txtAccountNumber").value;
		    var reg = /^\d+$/;
		    if (str.match(reg)==null || str.match(reg) =="")
		    {
		        alert('请输入正确的收款帐号');
		        document.getElementById("txtAccountNumber").focus();
		        return false;
		    }
		    return true;
		}
		
		//根据银行总行选择开户银行（分行）
		function SelectAccountBank()
		{
		   //取银行总行N6编码
		   var bankN6 = document.getElementById("dptBankType").value;
		   var param = "OutsideBankName=" + bankN6;
		   var paramCtrol = "hiAccountBankN6,txtAccountBank";
           OpenCommonLov('SelBranchBankSingle', paramCtrol , 450, 480, param);
		   
		}
		
		         <% if(request.getAttribute("success") != null)
         {
          if((Boolean)request.getAttribute("success")) { %>
         	 alert("保存成功");
         <%} else {%> 
         	alert("保存失败");
        <%  }
         }
        %> 
		

		</SCRIPT>
</HEAD>
<BODY leftMargin=0>
<FORM id=Form1 method=post name=Form1>
 
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="EmpBankAccountEdit_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN 
      class=arrow_subtitle>&gt;</SPAN>我的信息<SPAN 
      class=arrow_subtitle>&gt;</SPAN>报销帐户</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="EmpBankAccountEdit_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
<DIV id=divSearch align=left>
<TABLE id=tblQuery class=inputbl border=0 cellSpacing=0 width="100%" 
align=center>
  <TBODY>
  <TR>
    <TD class=title noWrap>币种<FONT color=#ff0000>*</FONT> </TD>
    <TD colSpan=5>
    	<SELECT id=dplCurrency name=dplCurrency> 
    		<% while(currencyIterator.hasNext()) { 
    		   int key = currencyIterator.next();
    	  %>
    		<OPTION selected value=<%= key%>><%= currencyInfo.get(key)%></OPTION>
    		<% } %>
    		</SELECT> 
    		</TD>
    		</TR>
  <TR>
    <TD class=title noWrap>省份<FONT color=#ff0000>*</FONT> </TD>
    <TD>
       <SELECT onchange="selectChange()" id=province name=province style="width: 150px" > 
    		<% while(provinceIterator.hasNext()) { 
    		   int key = provinceIterator.next();
    	  %>
    		<OPTION selected value=<%= key%>><%= provinceInfo.get(key).getName()%></OPTION>
    		<% } %>
    		</SELECT> 
      </TD>
    <TD class=title noWrap>城市<FONT color=#ff0000>*</FONT> </TD>
    <TD><select id=selectCity name=selectCity style="width: 131px"></select></TD>
    <TD class=title noWrap>银行类别<FONT color=#ff0000>*</FONT> </TD>
    <TD><SELECT style="WIDTH: 150px" id=dptBankType name=dptBankType> 
        <% while(bankIterator.hasNext()) { 
    		   int key = bankIterator.next();
    	  %>
    		<OPTION selected value=<%= key%>><%= bankInfo.get(key)%></OPTION>
    		<% } %>
        </SELECT> </TD></TR>
  <TR>
    <TD class=title noWrap>开户银行<FONT color=#ff0000>*</FONT> </TD>
    <TD><INPUT style="WIDTH: 150px" id=txtAccountBank 
      name=txtAccountBank> </TD>
    <TD class=title noWrap>收款户名<FONT color=#ff0000>*</FONT> </TD>
    <TD><INPUT id=txtAccountName  maxLength=120 name=txtAccountName> 
    </TD>
    <TD class=title noWrap>收款帐号<FONT color=#ff0000> *</FONT> </TD>
    <TD><INPUT style="WIDTH: 150px" id=txtAccountNumber 
      maxLength=20 name=txtAccountNumber> </TD></TR>
  <TR>
    <TD class=bottom colSpan=6>
      <DIV><INPUT style="WIDTH: 44px" id=btnSave language=javascript class=stbtm01 onclick="save()" value=保存 type=submit name=btnSave> 
      &nbsp;<INPUT style="WIDTH: 44px" id=btnReturn class=stbtm01 value='返回' onclick="back()" name=btnReturn> 
      <INPUT id=__hdCurPage value=1 type=hidden name=__hdCurPage> 
  </DIV></TD></TR></TBODY></TABLE>
<TABLE>
  <TBODY>
  <TR>
    <TD><SPAN id=lblRemark>注意事项：<BR>一、收款帐号不能有空格、横杠、括号、说明等数字以外的任何符号。如‘0755 
      8606-4666’应正确填为‘075586064666’；<BR>二、开户银行，因为已经选择了银行类别，只须填写分支行。如‘招商银行凤凰路支行’应正确填写为‘凤凰路支行’；<BR>三、省份、城市，必须选择报销帐户开户银行地区 
      <BR></SPAN></TD></TR></TBODY></TABLE></DIV>
<SCRIPT language=javascript>

<%
 BBankAccount bankAccount = (BBankAccount)request.getAttribute("bankAccount");
 if(bankAccount != null){ 
%>	
function init()
{
	document.getElementById("dplCurrency").value = <%= bankAccount.getCurrency()%>;
	document.getElementById("province").value = <%= bankAccount.getProvince()%>;
	document.getElementById("selectCity").value = <%= bankAccount.getCity()%>;
	document.getElementById("dptBankType").value = <%= bankAccount.getBank()%>;
	document.getElementById("txtAccountBank").value = "<%= bankAccount.getAccountBank()%>";
	document.getElementById("txtAccountName").value = "<%= bankAccount.getAccountName()%>";
	document.getElementById("txtAccountNumber").value = "<%= bankAccount.getAccount()%>";
}	

init();
<%
}
%>
selectChange();
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
