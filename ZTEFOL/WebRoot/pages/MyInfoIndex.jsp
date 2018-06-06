<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0104)http://fol.zte.com.cn/Admin/Employee/MyInfoIndex.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Business.UserDetail.BUserDetail"%>
<HTML><HEAD><TITLE>职员详细信息</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5>
<LINK rel=stylesheet type=text/css href="MyInfoIndex_files/style.css">
<style type="text/css">

 td * {
 	 margin: 0;
 }
 td select {
   width: 130px;
   background-color: white;
   border: 1px solid #9d9d9d;
 }	
</style>
<SCRIPT language=javascript src="MyInfoIndex_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript src="MyInfoIndex_files/FbpLovSelect.js"></SCRIPT>

<SCRIPT language=javascript src="MyInfoIndex_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript>
		 
			function deptchange()
            {
                document.all("txtDummyDeptName").value= "";
                document.getElementById("hiddenChangeDept").value = "Y";
                document.getElementById("hiItemId").value = "";
                document.getElementById("txtItemName").value = "";  
                Form1.submit();
                
            }
            
            function ProdTree()
            {
                var projType = document.getElementById("hiDeptClass").value;
//                //if(projType != '')
//                //{
//                    var param = 'ProjType=' + projType;
//                    OpenCommonLov('SelProdTree','hiItemId,txtItemName',640,480,param);
//                //}
                //判断部门
                if(document.all("hiDeptId").value == "")
                {
                    //alert('请选择部门!');
                    alert(document.all("hiJsDeptInfo").value);
                }
                else
                {
                   
                    var url = "../../Grb/SecondDist/LovProjects.aspx?"
                        + "&part=10&deptId=" +document.getElementById("hiDeptId").value
                        + "&prodName=txtItemName&prodID=hiItemId&sel=SelProdTree"
                        + "&LovKey=FPE_PROD_PROJECT";
                        + "&deptClass=" + projType;
                    var sFeatures = "height=480, width=640, toolbar=yes, menubar=yes, scrollbars=no, resizable=yes, location=yes, status=yes";
                    opennewwin(url,640,480);
                    
                }
            }
            
			function isValid()
			{
			    if(document.all("hiEmployeeId").value == "")
				{
					alert(document.all("hiJsInitInfo").value);
					return false;
				}
//				if(document.all("hiDeptId").value == "" || document.all("hiDeptId").value == "0" || document.all("txtDeptName").value == "")
//				{
//					alert(document.all("hiJsDeptInfo").value);
//					return false;
//				}
//				if(document.all("dplCheckUnit").value == "")
//				{
//					alert(document.all("hiJsCheckUnitInfo").value);
//					return false;
//				}
			  
//				if(document.all("hiLocationCode").value == "")
//				{
//					//alert("请选择所在地!");
//					//return false;
//				}
				
				return true;
			}
			
			function return_back()
			{
			    var deptid = document.all("hiDepartmentID").value;
				window.navigate("EmployeeList.aspx?deptid="+deptid);
			}
			
			function navigate_role()
			{
			    var deptid = document.all("hiDepartmentID").value;
			    var employeeid = document.all("hiEmployeeId").value;
				window.navigate("EmployeeRole.aspx?deptid="+deptid+"&employeeid="+employeeid);
			}
		
			function openSelLocationCode()
			{
				var lookuptype = document.all("hiLocationLookupType").value;
				SelLovWithParams("selLocationCode","hiLocationCode","txtCity",480,400,"lookuptype="+lookuptype);
			}
			function openSelEmployeePosition()
			{
				var url = "EmployeePositionLov.aspx?id=hiPositionsID&name=hiPositionsName&param1=hiChangeFlag";
				opennewwin(url,640,480);
			}
			
			function hasSelPositions()
			{
				Form1.submit();
			}
			
			 function OpenUpdatePwd()
            {
                var employeeid = document.all("hiEmployeeId").value;
                var url = "../../Admin/Employee/UpdateEmpPwd.aspx?is_admin=0&employee_id="+employeeid;
                url+='&resourceID='+'1521' 
					+'&menuPath='+'';
                 url+="&purl="+"%2fAdmin%2fEmployee%2fMyInfoIndex.aspx%3fmenupath%3d%26resourceid%3d1521%26menuId%3dssb.menu.00001521";
                 window.navigate(url);
                
               
            }
            
          function goUrl()
          {

            var resourceID=document.all("hiResourceID").value;
            var menuPath=document.all("hiMenuPath").value;
            var url="../../MyOBC/OBCBill/OBCBillIndex.aspx?resourceID="+resourceID+"&menuPath="+menuPath;	            
            if (parent!=null && parent.showPage != null)
            {
                parent.showPage(0,url);
            }
            else
            {
                 // 不存在框架转入则直接转入
                window.location=url;
            } // end if 
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
<BODY>
<FORM id=Form1 method=post name=Form1 action="myInfo.do"> 
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <%
  	BUserDetail userDetails = (BUserDetail)request.getAttribute("userDetail");
  	HashMap<Integer, String> depts = (HashMap<Integer, String>)request.getAttribute("detpInfo");
  	Iterator<Integer> iterator = depts.keySet().iterator();
  	HashMap<Integer, String> products = (HashMap<Integer, String>)request.getAttribute("prodInfo");
  	Iterator<Integer> prodIterator = products.keySet().iterator();
  %>
  <TR height=24>
    <TD><IMG class=icon src="MyInfoIndex_files/bullet0.gif" height=10> <SPAN 
      id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN 
      class=arrow_subtitle>&gt;</SPAN>我的信息<SPAN 
      class=arrow_subtitle>&gt;</SPAN>个人信息</SPAN> </TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyInfoIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px"><INPUT 
id=hiddenChangeDept type=hidden name=hiddenChangeDept> <INPUT id=hiDeptClass 
value=PRODUCT_TYPE type=hidden name=hiDeptClass> 
<TABLE id=divSearch class=inputbl cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
  <TR>
    <TD class=title width="18%"><SPAN id=lblName>姓名</SPAN></TD>
    <TD bgColor=#efefef width="34%"><SPAN style="WIDTH: 133px" 
      id=lblEmployeeName><%=userDetails.getName()%></SPAN></TD>
    <TD class=title width="18%"><SPAN id=lblEmployeeNum>工号</SPAN></TD>
    <TD bgColor=#efefef><SPAN style="WIDTH: 133px" 
      id=lblEmployeeCode><%= userDetails.getAllNo()%></SPAN></TD></TR>
  <TR>
    <TD class=title width="18%"><SPAN id=lblDept>所属部门</SPAN></TD>
    <TD bgColor=#efefef width="34%"><SPAN style="WIDTH: 133px" 
      id=lblDeptName><%=userDetails.getDeptStr()%></SPAN></TD>
  	<TD style="HEIGHT: 24px" class=title><SPAN id=lblEmailName>Email</SPAN></TD>
    <TD style="HEIGHT: 24px" bgColor=#efefef><INPUT 
      style="WIDTH: 133px; HEIGHT: 22px" id=txtEmail 
      name="email" value=<%=userDetails.getEmail()%>></TD></TR>
  <TR>
    <TD class=title><SPAN id=lblPhoneNumber>电话号码</SPAN></TD>
    <TD bgColor=#efefef><INPUT id=txtPhone name="tel" value=<%=userDetails.getTel()%>></TD>
</TR>
      <!--
  <TR>
    <TD class=title><SPAN id=lblPosition>当前职务</SPAN></TD>
    <TD bgColor=#efefef><SELECT style="WIDTH: 133px" id=lstPosition size=3 
      name=lstPosition></SELECT><INPUT id=hiPositionsID size=1 type=hidden 
      name=hiPositionsID><INPUT id=hiPositionsName size=1 type=hidden 
      name=hiPositionsName><INPUT onpropertychange=hasSelPositions() 
      id=hiChangeFlag size=1 type=hidden name=hiChangeFlag value=<%=userDetails.getTechTitle()%>></TD></TR>   -->
  <TR style="display:none;">
    <TD class=title>
      <P><SPAN id=lblEnabledFlag>有效性</SPAN></P></TD>
    <TD bgColor=#efefef><SPAN disabled><INPUT id=rbYes disabled value=rbYes 
      CHECKED type=radio name=rbDisabled><LABEL 
      for=rbYes>启用</LABEL></SPAN>&nbsp; <SPAN disabled><INPUT id=rbNo disabled 
      value=rbNo type=radio name=rbDisabled><LABEL for=rbNo>禁用</LABEL></SPAN></TD>
    <TD class=title><SPAN id=lblDummyDept>虚拟部门</SPAN></TD>
    <TD bgColor=#efefef><INPUT 
      style="BORDER-BOTTOM-STYLE: none; BORDER-RIGHT-STYLE: none; BACKGROUND-COLOR: transparent; WIDTH: 125px; BORDER-TOP-STYLE: none; HEIGHT: 22px; BORDER-LEFT-STYLE: none" 
      id=txtDummyDeptName class=lefttext onfocus=blur(); 
    name=txtDummyDeptName></TD></TR>
  <TR align=middle>
    <TD class=bottom colSpan=4>&nbsp; <INPUT id=btnSave language=javascript class=stbtm01 onclick="javascript:return isValid();" value=保存 type=submit name=btnSave>&nbsp; 
      &nbsp; </TD></TR></TBODY></TABLE>

<DIV align=center>

<TABLE align=left>
  <TBODY>
  <TR>
    <TD>
      <P><SPAN><FONT color=red><STRONG>温馨提示: 
      </STRONG>若您已调动部门，请找管理员设置所属部门
  </FONT></SPAN></P></TD></TR></TBODY></TABLE><BR><BR><BR><BR><BR><BR></DIV><INPUT 
id=hiResourceID value=1521 type=hidden name=hiResourceID> <INPUT id=hiMenuPath 
size=1 type=hidden name=hiMenuPath> <INPUT id=hiEmployeeId value=244680 
type=hidden name=hiEmployeeId> <INPUT id=hiDepartmentID type=hidden 
name=hiDepartmentID> <INPUT id=hiLocationLookupType value=EMPLOYEE_LOCATION_CODE 
type=hidden name=hiLocationLookupType> <INPUT id=hiJsInitInfo value=页面初始化错误! 
type=hidden name=hiJsInitInfo><INPUT id=hiJsDeptInfo 
value=首次使用财务在线系统，请正确维护您报销所在部门，具体部门信息可以咨询部门其他同事。 type=hidden 
name=hiJsDeptInfo><INPUT id=hiJsCheckUnitInfo value=您维护的部门没有核算体系，请确认您维护的部门是否正确。 
type=hidden name=hiJsCheckUnitInfo><INPUT id=hiJsSaveInfo value=保存成功! 
type=hidden name=hiJsSaveInfo> <INPUT id=hiCheckUnitNotExists 
value=您所选部门的默认核算体系不存在，请确认是否选择了正确的部门、以及该部门是否有默认的核算体系，若还有问题请联系系统管理员！ type=hidden 
name=hiCheckUnitNotExists>
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
