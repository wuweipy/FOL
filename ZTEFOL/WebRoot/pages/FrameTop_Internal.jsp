<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0059)http://fol.zte.com.cn/UILoader/Index/FrameTop_Internal.aspx -->
<%@ page contentType="text/html;charset=utf-8"%>
<HTML><HEAD><TITLE>系统服务总线</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type><LINK 
rel=stylesheet type=text/css href="FrameTop_Internal_files/cssCn.css">
<SCRIPT language=javascript src="FrameTop_Internal_files/FrameTop.js"></SCRIPT>


<SCRIPT language=javascript> 
   function logout(){
       window.location.href="login.do";   
   }




</SCRIPT>


<SCRIPT language=javascript>
        function showMsg()
        {
            var bShowMsgId = document.getElementById("showMsgId");
            var now = new Date();
            var shotDate = now.getFullYear() + "年" + (now.getMonth()+1) + "月" + now.getDate() + "日";
            bShowMsgId.value = shotDate + " 欢迎 " 
            + "<%= (String)request.getAttribute("user")%>";
            //+ document.getElementById("userid").value;     
        }
    </SCRIPT>
<!--<body onmousewheel="ScrollWindow();">-->
<META name=GENERATOR content="MSHTML 8.00.6001.23501"></HEAD>
<BODY onselectstart="return false">
<TABLE id=tb_topcontent cellSpacing=0 summary="for layout controlling" 
cellPadding=0>
  <THEAD>
  <TR>
    <TD><!-- start of header -->
      <TABLE id=header cellSpacing=0 cellPadding=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left>
            <TABLE border=0 cellSpacing=0 cellPadding=0>
              <TBODY>
              <TR>
                <TD height=43 vAlign=top rowSpan=2><IMG 
                  src="FrameTop_Internal_files/title_zte.gif"><IMG 
                  src="FrameTop_Internal_files/title_product.gif"></TD>
                <TD>
                  <TABLE border=0 cellSpacing=0 cellPadding=0>
                    <TBODY>
                    <TR>
                      <TD><IMG id=titleLog 
                        src="FrameTop_Internal_files/title1_fol_main_rework.gif"></TD>
                      <TD vAlign=top>
                        <DIV 
              id=version1>V7.8.7</DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
            <TABLE id=tb_topuser border=0 cellSpacing=0 cellPadding=0 
            align=left>
              <TBODY>
              <TR>
                <TD><IMG src="FrameTop_Internal_files/tabv.gif" width=23 
                  height=18></TD>
                <TD style="COLOR: #a8a8a8" title=主页 noWrap><A 
                  onclick="try{top.right.location.href= 'index_files/Index.html';}catch(exception){window.open('../Redirect.aspx?pageCode=main&amp;toolbarId=toolbar.001','u','scrollbars=yes,resizable=yes,dependent,width='+(screen.availWidth-300)+',height='+(screen.availHeight-200)+',left=0,top=0')}" 
                  href="FrameTop_Internal.jsp">首页</A></TD>
                <TD class=vline1>&nbsp;&nbsp;&nbsp;</TD>               
                <TD style="COLOR: #a8a8a8" title=帮助 noWrap><A 
                  href="FinanceHelp.docx">帮助</A></TD>
                <TD class=vline1>&nbsp;&nbsp;&nbsp;</TD>
                <TD style="COLOR: #a8a8a8" title=注销 noWrap><A 
                  href="../logout.do">注销</A></TD>
                <TD>&nbsp;&nbsp;&nbsp;</TD></TR>
              <TR>
                <TD style="HEIGHT: 8px"></TD></TR>
              <TR>
                <TD colSpan=20 align=right><FONT style="COLOR: #c0def8"><INPUT 
                  style="TEXT-ALIGN: right; BORDER-RIGHT-WIDTH: 0px; BACKGROUND-COLOR: transparent; WIDTH: 100%; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; COLOR: #c0def8; BORDER-LEFT-WIDTH: 0px" 
                  id=showMsgId type=text> </FONT></TD></TR></TBODY></TABLE>
            <DIV style="WIDTH: 100%; OVERFLOW: hidden" id=DivTopMenu>
            <IFRAME height=23 marginHeight=0 src="FrameTop_Internal_files/FrameMenuTop.htm" frameBorder=0 width="100%" name=news marginWidth=0 scrolling=no>
            	</IFRAME>
            	</DIV>
            	</TD>
          <!--<TD id=tdshowmovepic class=tab vAlign=bottom width=60>
            <DIV class=tb_toptab1 align=center><IMG style="CURSOR: hand" 
            onclick=movstar(-20,1); 
            src="FrameTop_Internal_files/icon_goback1.gif" width=16 height=16> 
            <IMG style="CURSOR: hand" onclick=movstar(20,1); 
          src="FrameTop_Internal_files/icon_goahead1.gif"></DIV></TD>-->
            </TR></TBODY></TABLE></TD></TR></THEAD></TABLE>

<SCRIPT language=javascript>
            showMsg();
        </SCRIPT>
</BODY></HTML>
