<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<!-- saved from url=(0104)http://fol.zte.com.cn/Boe/Search/MyProposerIndex.aspx -->
<%@ page language="java" import="java.util.*,Business.Budget.DelegateInfo,Data.UserInfo.DUser"%>
<HTML><HEAD><TITLE>委托功能</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=GENERATOR content="MSHTML 8.00.6001.23501">
<META name=CODE_LANGUAGE content=C#>
<META name=vs_defaultClientScript content=JavaScript>
<META name=vs_targetSchema 
content=http://schemas.microsoft.com/intellisense/ie5><LINK rel=stylesheet 
type=text/css href="MyProposerIndex_files/style.css">
<SCRIPT language=javascript src="MyProposerIndex_files/CommonFun.js"></SCRIPT>

<SCRIPT language=javascript 
src="MyProposerIndex_files/FbpLovSelect.js"></SCRIPT>

<LINK rel=stylesheet 
type=text/css href="Pager/Page.css"> 

<SCRIPT language=javascript 
src="Pager/Pager.js"></SCRIPT>

<SCRIPT language=javascript 
src="MyProposerIndex_files/AdminPageFunction.js"></SCRIPT>

<SCRIPT type=text/javascript src="MyProposerIndex_files/Calendar.js"></SCRIPT>

<SCRIPT type=text/javascript src="MyProposerIndex_files/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="MyProposerIndex_files/jquery.blockUI.js"></SCRIPT>

<SCRIPT type=text/javascript 
src="MyProposerIndex_files/itp_investigate.js"></SCRIPT>

<SCRIPT language=javascript type=text/javascript 
src="MyProposerIndex_files/CommLovFun.js"></SCRIPT>

<SCRIPT language=javascript src="validate.js"></SCRIPT>

<SCRIPT language=javascript>

        function add()
        {
        	 Form1.action = "DelegateServlet.do?action=add";
        	 //Form1.submit();
        }
        
        
         <% if(request.getAttribute("AddisSuccess") != null)
         {
          if((Boolean)request.getAttribute("AddisSuccess")) { %>
         	 alert("添加成功");
         <%} else {%> 
         	alert("添加失败");
        <%  }
         }
        %>  
        
         <% if(request.getAttribute("DelisSuccess") != null)
         {
          if((Boolean)request.getAttribute("DelisSuccess")) { %>
         	 alert("删除成功");
         <%} else {%> 
         	alert("删除失败");
        <%  }
         }
        %>  
    </SCRIPT>
</HEAD>

<BODY>
<FORM id=Form1 method=post name=Form1 action=DelegateServlet.do>
<TABLE class=tabbar1 border=0 cellSpacing=0 cellPadding=0 align=center>
  <TBODY>
  <TR height=24>
    <TD><IMG class=icon src="MyProposerIndex_files/bullet0.gif" height=10> 
      <SPAN id=TableHeadBar1_lblHead>当前位置<SPAN class=11air>:</SPAN>与我有关<SPAN 
      class=arrow_subtitle>&gt;</SPAN>我的信息<SPAN 
      class=arrow_subtitle>&gt;</SPAN>委托功能</TD>
    <TD width=16 noWrap align=right><IMG style="CURSOR: hand" 
      id=TableHeadBar1_BarImg class=icon onclick=javascript:ExpandDiv(this); 
      src="MyProposerIndex_files/icon_collapseall.gif" height=16 data="1"> 
  </TD></TR></TBODY></TABLE><BR style="LINE-HEIGHT: 1px">
  
  <%   
    int roleId = (Integer)request.getAttribute("roleId");
    if(roleId==3 || roleId==5) {
%>
<DIV  id=divSearch>
<TABLE id=queryPane class=inputbl border=0 cellSpacing=0 cellPadding=0 
width="100%" align=center>
  <TBODY>
  <TR>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxx>功能类型</SPAN></DIV></TD>
    <TD>        
    <SELECT size="0" style="WIDTH: 180px" id=delegate name=delegate>
	<%    
        HashMap<Integer, String> delegateMap = (HashMap<Integer, String>)request.getAttribute("delegateMap");
        Iterator<Integer> typeIterator = delegateMap.keySet().iterator();
  	    while(typeIterator.hasNext()) { 
    		   int key = typeIterator.next();
    %>
	<option selected="" value="<%= key %>"><%= delegateMap.get(key)%></option>
	<%     }   %>
</SELECT></TD>
    <TD style="HEIGHT: 24px" class=title>
      <DIV align=left noWrap><SPAN id=noxxxxx>委托人</SPAN></DIV></TD> 
    <TD>        
    <SELECT size="0" style="WIDTH: 180px" id=userNo name=userNo>
<!--	<%    
        HashMap<String, DUser> userInfoMap = (HashMap<String, DUser>)request.getAttribute("userInfoMap");
        Iterator<String> userIterator = userInfoMap.keySet().iterator();
  	    while(userIterator.hasNext()) { 
    		   String key = userIterator.next();
    %>
	<option value="<%= key %>"><%= userInfoMap.get(key).getName()%><%= key %></option>
	<%     }   %>   -->
	
       <option value="0224000067">倪欢0224000067</option>
       <option value="0224000065">彭伟0224000065</option>
       <option value="0224000046">苏凡0224000046</option>
       <option value="0224000006">徐玉铮0224000006</option>
</SELECT></TD>
    </TR>
  <TR>
    <TD class=bottom colSpan=6 align=middle><INPUT id=hiDeptID size=1 
      type=hidden name=hiDeptID><INPUT id=hiUserID size=1 type=hidden 
      name=hiUserID>&nbsp; <INPUT id=btnQuery class=stbtm01 value=添加 onclick="add()" type=submit name=btnQuery><INPUT 
      id=hiEmployeeID size=1 type=hidden name=hiEmployeeID><INPUT 
      id=hiProdProjectID size=1 type=hidden name=hiProdProjectID><INPUT 
      style="WIDTH: 31px; HEIGHT: 20px" id=hiCulture value=zh-CN size=1 
      type=hidden name=hiCulture> </TD></TR></TBODY></TABLE></DIV>
</FORM>
<TABLE style="MARGIN-TOP: 5px" border=0 cellSpacing=0 cellPadding=0 width="98%" 
bgColor=#ffffff>
  <TBODY>
  <TR>
    <TD class=tb_titlebar colSpan=6 align=left><IMG 
      src="MyProposerIndex_files/bullet1.gif" width=16 height=16> <SPAN 
      id=lblApprovalList>功能列表</SPAN> </TD></TR></TBODY></TABLE>
<DIV style="WIDTH: 98%" class=autoOver2 align=center>
      <TABLE style="BORDER-COLLAPSE: collapse" id=dgApproval class=datatbl 
      border=1 rules=all cellSpacing=0>
        <TBODY>
        <TR class=head id=tr_Head>
          <TD  noWrap style="WIDTH: 35%" align=left>功能</TD>
          <TD  noWrap style="WIDTH: 35%" align=left>委托人</TD>
          <TD  noWrap align=left><A 
            href="javascript:__doPostBack('dgApproval$_ctl1$_ctl9','')"></A></TD></TR>
         <%
            ArrayList<DelegateInfo> delegateInfos = (ArrayList<DelegateInfo>)request.getAttribute("delegateInfo");
            for(int i=0;i<delegateInfos.size();i++)
            {
            DelegateInfo delegateInfo = delegateInfos.get(i);
         %>
         
        <TR class=item>
          <TD noWrap align=left>
          	<%= delegateMap.get(delegateInfo.getActionId())%>
          </TD>
          <TD noWrap align=left><SPAN 
            id=dgApproval__ctl2_lbl_CourseName><%= userInfoMap.get(delegateInfo.getAgency()).getName()%><%= delegateInfo.getAgency()%></SPAN> </TD>
          <TD noWrap style="text-align: center"><a href="DelegateServlet.do?action=delete&agencyNo=<%= delegateInfo.getAgency()%>&actionId=<%=delegateInfo.getActionId()%>">删除</a>
            <INPUT id=dgApproval__ctl2_hiEmployeeIDRow value=244680 type=hidden 
            name=dgApproval:_ctl2:hiEmployeeIDRow> </TD>
         </TR>
         <%
           }           if(delegateInfos.size()==0){
         %>
        <TR>
       <TD width="100%" align=center colspan="11"><FONT color=red>对不起，没有符合条件的记录! </FONT></TD>
       </TR>
       <%  }     %>    
         
         </TBODY></TABLE></DIV>
  <script> 
     var pager = new Pager("dgApproval","tr_Head");    
  </script>
  <% }
  else {
    %>
    <TABLE>您没有此操作权限！</TABLE>
    
  <%
  }
  %>
  
 </BODY></HTML>