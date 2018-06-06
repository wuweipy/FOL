<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0104)http://fol.zte.com.cn/Admin/Employee/MyInfoIndex.aspx -->
<%@ page language="java"
	import="java.util.*,Business.UserDetail.BUserDetail" pageEncoding="utf-8"%>
<HTML>
	<HEAD>
		<TITLE>职员详细信息</TITLE>
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
		 function isValid()
			{
			   
			    if(document.getElementById("txtOldPassword").value == "")
				{					
					alert("请输入用户旧密码");
					document.getElementById("txtOldPassword").focus();
					return false;
				}
				
				var newPassword = document.getElementById('txtNewPassword').value;
				var confNewPassword = document.getElementById('txtConfirmNewPassword').value;
				
				if(newPassword == "")
				{					
					alert("请输入用户新密码");
					document.getElementById("txtNewPassword").focus();
					return false;
				}
				
				if(confNewPassword == "")
				{					
					alert("请输入用户确认新密码");
					document.getElementById("txtConfirmNewPassword").focus();
					return false;
				}
				
				if(confNewPassword != newPassword)
				{					
					alert("两次输入的新密码不一致，请重新输入。");
					document.getElementById("txtNewPassword").focus();
					return false;
				}
				document.getElementById('Form1').submit();
				return true;
			}
          
         <%if (request.getAttribute("wrongPassword") != null) {
		     if ((Boolean) request.getAttribute("wrongPassword")) {%>
         	 alert("原密码输入错误，请重新输入。"); 
             <%} 
		   }%>  
			
			   
         <%if (request.getAttribute("success") != null) {
				if ((Boolean) request.getAttribute("success")) {%>
         	 alert("保存成功");
         <%} else {%> 
         	alert("保存失败");
        <%}
			}%>  
                    
    </SCRIPT>
	</HEAD>
	<BODY>
		<FORM id=Form1 method=post name=Form1 action="modifyPassword.do">
		
			<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0
				align=center>
				<TBODY>

					<TR height=24>
						<TD>
							<IMG class=icon src="MyInfoIndex_files/bullet0.gif" height=10>
							<SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN
								class=arrow_subtitle>&gt;</SPAN>我的信息<SPAN class=arrow_subtitle>&gt;</SPAN>修改密码</SPAN>
						</TD>
						<TD width=16 noWrap align=right>
							<IMG style="CURSOR: hand" id=TableHeadBar1_BarImg class=icon
								onclick=javascript:ExpandDiv(this);
								src="MyInfoIndex_files/icon_collapseall.gif" height=16 data="1">
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<BR style="LINE-HEIGHT: 1px">
			
			<TABLE id=divSearch class=inputbl cellSpacing=0 cellPadding=0
				width="100%">
				<TBODY>
					<TR>
						<TD class=title width="10%">
							<SPAN id=lblName>旧密码</SPAN><FONT color=#ff0000>*</FONT>
						</TD>
						<TD bgColor=#efefef width="80%">							
							<INPUT id="txtOldPassword" type="text" name="oldPassword" value=""/> 
						</TD>
						
					</TR>
					
					<TR>
						<TD class=title width="10%">
							<SPAN id=lblName>新密码</SPAN><FONT color=#ff0000>*</FONT>
						</TD>
						<TD bgColor=#efefef width="80%">							
							<INPUT id="txtNewPassword" type="text"  name="newPassword" value=""/>
						</TD>
						
					</TR>
					
					<TR>
						<TD class=title width="10%">
							<SPAN id=lblName>确认新密码</SPAN><FONT color=#ff0000>*</FONT>
						</TD>
						<TD bgColor=#efefef width="80%">							
							<INPUT id="txtConfirmNewPassword" type="text" name="confirmNewPassword" value=""/>  
						</TD>
						
					</TR>

							<TR align=middle>
								<TD class=bottom colSpan=4>
									&nbsp;
									<INPUT id="btnSave"  class=stbtm01
										onclick="javascript:return isValid();" value=保存 type="button"
										name="btnSave">
									&nbsp; &nbsp;
								</TD>
							</TR>
				</TBODY>
			</TABLE>




		</FORM>
	</BODY>
</HTML>
