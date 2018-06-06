<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*,Business.UserDetail.BUserDetail,Business.Claims.*"%>
    <HTML>
        
        <HEAD>
            <title>
                付款信息
            </title>
            <meta content="Microsoft Visual Studio .NET 7.1" name="GENERATOR">
            <meta content="C#" name="CODE_LANGUAGE">
            <meta content="JavaScript" name="vs_defaultClientScript">
            <meta content="http://schemas.microsoft.com/intellisense/ie5" name="vs_targetSchema">
            <LINK href="BoeWizardIndex_files/style.css" type="text/css" rel="stylesheet">
 
            <script>
                function validateExName(path, culture) {
                    var temp = (path != "" && path.substr(path.length - 3, 3).toUpperCase() == "XLS");
                    if (!temp) {
                        culture = 'zh-CN' ? alert("请选择.xls格式文档(如为.xlsx,请另存为.xls)") : alert("Please select .xls document(If it's .xlsx,please save a copy as .xls");
                    }
                    return temp;
                }
                function ImportExcel(import_type) {
                    var culture = document.all('hiCulture').value;
                    var tempID;
                    var file;
                    if (import_type == "SIE_PAYMENT_INSIDE") {
                        file = document.all('upfile').value;
                        if (validateExName(file, culture)) {
                            uploadExcel(file, import_type, '../../SysBasicData/Pages/ExcelToDB.aspx', document.all('hiBoeID').value + '|' + document.all('personal_flag').value, culture);
                            document.all("chkHKSurrogate").checked = false;
                            document.all("chkLOCAL").checked = false;
                            submit_data();
                        }
                    } else if (import_type == "SIE_PAYMENT_OUTSIDE") {
                        file = document.all('upfile1').value;
                        if (validateExName(file, culture)) {
                            uploadExcel(file, import_type, '../../SysBasicData/Pages/ExcelToDB.aspx', document.all('hiBoeID').value, culture);
                            document.all("chkHKSurrogate").checked = false;
                            document.all("chkLOCAL").checked = false;
                            submit_data();
                        }
                    }

                    else if (import_type == "SIE_PAYMENT_HK") {
                        file = document.all('upfile2').value;
                        if (validateExName(file, culture)) {
                            uploadExcel(file, import_type, '../../SysBasicData/Pages/ExcelToDB.aspx', document.all('hiBoeID').value, culture);
                            document.all("chkHKSurrogate").checked = true;
                            document.all("chkLOCAL").checked = false;
                            submit_data();
                        }
                    } else if (import_type == "SIE_PAYMENT_LOCAL") {
                        file = document.all('upfile3').value;
                        if (validateExName(file, culture)) {
                            uploadExcel(file, import_type, '../../SysBasicData/Pages/ExcelToDB.aspx', document.all('hiBoeID').value, culture);
                            document.all("chkHKSurrogate").checked = false;
                            document.all("chkLOCAL").checked = true;
                            submit_data();
                        }
                    }
                }
            </script>
        </HEAD>
        
        <body>
            <form name="Form1" method="post" action="boe.do"
            id="Form1">
                <input type="hidden" name="save" id="save" value="no" />
                <input type="hidden" name="action" id="action" value="" />
                <TABLE id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0">
                    <TR height="1">
                        <TD>
                            <FONT face="宋体">
                                <table class="tabbar1" cellspacing="0" cellpadding="0" align="center"
                                border="0">
                                    <tbody>
                                        <tr height="24">
                                            <td>
                                                <img class="icon" height="10" src="BoeWizardIndex_files/bullet0.gif">
                                                <span id="TableHeadBar1_lblHead">
                                                    当前位置
                                                    <SPAN class="11air">
                                                        :
                                                    </SPAN>
                                                    报销管理
                                                    <span class="arrow_subtitle">
                                                        &gt;
                                                    </span>
                                                    单据制作
                                                    <span class="arrow_subtitle">
                                                        &gt;
                                                    </span>
                                                    单据草稿
                                                </span>
                                            </td>
                                            <td align="right" width="16" nowrap>
                                                <img src="BoeWizardIndex_files/icon_collapseall.gif" id="TableHeadBar1_BarImg"
                                                class="icon" height="16" style="cursor: hand" data="1" onclick="javascript:ExpandDiv(this);"
                                                />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br style="line-height: 1px;">
                                <input name="hiPFlag" type="hidden" id="hiPFlag" style="WIDTH: 40px; HEIGHT: 21px"
                                size="1" />
                            </FONT>
                        </TD>
                    </TR>
                    <TR>
                        <TD valign="top">
                            <div id="divXml">
                                
                                <HEAD>
                                    <TITLE>
                                        付款信息
                                    </TITLE>
                                    <script language="javascript">
                                        function submit_data() {
                                           // save_payment_data("save");
                                           document.getElementById("save").value = "save";
                                           document.getElementById("action").value = "3";
                                           Form1.submit();
                                        }
                                        function go_next() {
                                           document.getElementById("save").value = "next";
                                           document.getElementById("action").value = "3";
                                           Form1.submit();
                                        }
                                        function save_payment_data(actionFlag) {
                                            if (!isValidForPayment(1, actionFlag + "_bp")) {
                                                return;
                                            }
                                            document.all("InsidesRows_Count").value = document.all("tblInsideInfo").rows.length - 2;
                                            document.all("OutsidesRows_Count").value = document.all("tblOutsideInfo").rows.length - 2;
                                            document.all("HKRows_Count").value = document.all("tblHKInfo").rows.length - 2;
                                            document.all("LOCALRows_Count").value = document.all("tblLOCALInfo").rows.length - 2;
                                            document.Form1.hiAction_Flag.value = actionFlag;
                                            submit_to_disabled_button();
                                            Form1.submit();
                                        }
                                        function goto_prev() {
                                           document.getElementById("save").value = "pre";
                                           document.getElementById("action").value = "2";
                                           Form1.submit();
                                        }
                                        function goto_return_back() {
                                            document.Form1.hiAction_Flag.value = "return_back";
                                            Form1.submit();
                                        }
                                        function insides_import() {
                                            alert("尚未开发");
                                        }
                                        function outsides_import() {
                                            alert("尚未开发");
                                        }
                                    </script>
                                </HEAD>
                                
                                <BODY>
                                    <TABLE align="center" id="tblMainInfo" bgColor="#1475b3" class="inputbl"
                                    border="0" cellPadding="0" cellSpacing="0" width="100%">
                                        <tr>
                                            <td colspan="3" align="left">
                                                <img class="icon" src="BoeWizardIndex_files/icon_note.gif" />
                                                报销信息
                                                <input id="boe_type_id" type="hidden" name="boe_type_id" readonly="readonly"
                                                width="60px" value="415" />
                                                <input id="boe_id" type="hidden" name="boe_id" readonly="readonly" width="60px"
                                                value="11481889" />
                                                <input id="payment_type" type="hidden" name="bopayment_typee_id" readonly="readonly"
                                                width="60px" value="" />
                                                <input id="BalanceRows_Count" type="hidden" name="BalanceRows_Count" readonly="readonly"
                                                width="60px" value="1" />
                                            </td>
                                        </tr>
                                        <TR>
                                            <td class="title" nowrap="nowrap" align="left">
                                                币种
                                            </td>
                                            <td class="title" nowrap="nowrap" align="left">
                                                支付金额
                                            </td>
                                            <!--<td class="title" nowrap="nowrap" align="left" id="tdPaymentBL0" width="50%">
                                                冲帐金额
                                            </td>-->
                                        </TR>
                                       <%  
                            	             BUserDetail userInfo = (BUserDetail)request.getAttribute("userInfo");
                            	             BClaim bClaim = (BClaim)request.getAttribute("claim");
                            	         %>
                                        <tr>
                                            <td align="left">
                                                <input type="text" readonly="readonly" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%;"
                                                name="sum_currency_code1" id="sum_currency_code1" value="CNY" />
                                            </td>
                                           <% if(bClaim.getInvoiceType()==5)   {%>
                                            <td nowrap="nowrap" align="left">
                                                <input type="text" readonly="readonly" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%;text-align: left"
                                                name="sum_amount1" id="sum_amount1" value="<%= bClaim.getTotalFee()%>" />
                                            </td>
                                            <%}
                                            else{%>
                                            <td nowrap="nowrap" align="left">
                                                <input type="text" readonly="readonly" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%;text-align: left"
                                                name="sum_amount1" id="sum_amount1" value="<%= bClaim.getTotalFeeOther()%>" />
                                            </td>                                                                                        
                                            <%}%>
                                            <!--<td nowrap="nowrap" id="tdPaymentBL1">
                                                <input type="text" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%;text-align: left;"
                                                validate="+DOUBLE2" maxlength="15" name="sum_balance_amount1" id="sum_balance_amount1"
                                                value="0" />
                                            </td>-->
                                        </tr>
                                    </TABLE>
                                    <TABLE align="center" id="tblInsideInfo" bgColor="#1475b3" class="inputbl"
                                    border="0" cellPadding="0" cellSpacing="0" width="100%">
                                        <tr>
                                            <td colspan="9">
                                                <input type="hidden" id="personal_flag" name="personal_flag" value="Y"
                                                />
                                                <div nowrap="nowrap">
                                                    <span title="付款给公司内部员工请填写此项">
                                                        <img class="icon" src="BoeWizardIndex_files/icon_note.gif" />
                                                        内部付款
                                                    </span>
                                                    <span title="打入个人帐户的款项请选择此项">
                                                        <input id="personal_flag_yes" type="radio" groupname="groupPersonalFlag"
                                                        readonly="readonly" name="personal_flag_value" width="100px" onclick="set_personal_flag()"
                                                        checked="1" />
                                                    </span>
                                                    <span title="打入个人帐户的款项请选择此项">
                                                        个人帐户
                                                    </span>
                                                    <!--<span title="打入对公帐户的款项请选择此项">
                                                        <input id="personal_flag_no" type="radio" name="personal_flag_value" width="100px"
                                                        onclick="set_personal_flag()" onpropertychange="load_personal_flag()" />
                                                    </span>
                                                    -->
                                                    <!--
                                                    <span title="打入对公帐户的款项请选择此项">
                                                        公司帐户
                                                    </span>

                                                    <input id="btnInsidesAdd" value="新增" class="stbtm01" onclick="boePaymentInsidesAddRows('tblInsideInfo','zh-CN',1)"
                                                    type="button" />
                                                    
                                                                                                        -->
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title" nowrap="nowrap">
                                                <div align="left">
                                                    <!--<div id="VenNum">
                                                        供应商编号
                                                        <font color="red">
                                                            *
                                                        </font>
                                                    </div>
                                                    -->
                                                    <div id="EmpNum">
                                                        员工编号
                                                        <font color="red">
                                                            *
                                                        </font>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="title" nowrap="nowrap" align="left">
                                                名称
                                            </td>
                                           <!-- <td class="title" nowrap="nowrap" align="left">
                                                境外银行地址
                                            </td>
                                            -->
                                            <td class="title" nowrap="nowrap" align="left">
                                                支付方式
                                                <font color="red">
                                                    *
                                                </font>
                                            </td>
                                            <!--
                                            <td class="title" nowrap="nowrap" align="center" id="tdInsidesBalanceType0">
                                                结算方式
                                            </td>
                                            -->
                                            <td class="title" nowrap="nowrap">
                                                <div align="left">
                                                    币种
                                                    <font color="red">
                                                        *
                                                    </font>
                                                </div>
                                            </td>
                                            <td class="title" nowrap="nowrap">
                                                <div align="left">
                                                    金额
                                                    <font color="red">
                                                        *
                                                    </font>
                                                </div>
                                            </td>
                                            <!--
                                            <td class="title" nowrap="nowrap">
                                                <div align="left">
                                                    汇款用途
                                                    <font color="red">
                                                        *
                                                    </font>
                                                </div>
                                            </td>
                                            
                                            <td class="title" nowrap="nowrap" align="left" width="1%">
                                                操作
                                            </td>
                                            -->
                                        </tr>
                                        <tr>
                                            <td align="left" style="20%">
                                                <div nowrap="nowrap">
                                                    <input type="text" maxlength="50" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;" onchange="boe_change_ve_code(this.id)"
                                                    onkeypress="javascript:return selCurEmployeeWithKey(this.id)" name="inside_ve_code1"
                                                    id="inside_ve_code1" value="<%= userInfo.getAllNo()%>" />
                                                </div>
                                            </td>
                                            <td align="left" style="20%">
                                                <input type="text" maxlength="50" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;"
                                                name="inside_ve_name1" id="inside_ve_name1" value="<%= userInfo.getName()%>" />
                                            </td>
                                             
                                            <td align="left" style="20%">
                                                <select style="width:80px;" name="paymentMode" id="paymentMode">
                                                    <option selected="selected" value="0">
                                                        银行存款
                                                    </option>
                                                    <option value="1">
                                                        现金
                                                    </option>
                                                </select>
                                            </td>
                                             <script>
                                             	 document.getElementById("paymentMode").value = <%= bClaim.getPayType()%>;
                                             	</script>
                                            <td align="left" style="20%">
                                                <input type="text" onfocus="blur();" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;"
                                                 name="inside_currency_code1"
                                                id="inside_currency_code1" value="CNY" />
                                            </td>
                                            <td align="left" style="20%">
                                            <% if(bClaim.getInvoiceType()==5)   {%>
                                                <input type="text" maxlength="15" align="left" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;"
                                                validate="+DOUBLE2" name="inside_payment_amount1" id="inside_payment_amount1"
                                                value="<%= bClaim.getTotalFee()%>" />
                                            </td>  
                                            
                                            <%}
                                            else {%>
                                                <input type="text" maxlength="15" align="left" style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;"
                                                validate="+DOUBLE2" name="inside_payment_amount1" id="inside_payment_amount1"
                                                value="<%= bClaim.getTotalFeeOther()%>" />
                                            </td>  
                                            
                                            <%}%>
                                            
                                        </tr>
                                    </TABLE>
                                        <table align="right" bgColor="#1475b3" class="inputbl"
                                    border="0" cellPadding="0" cellSpacing="0" width="100%">
                                        <tr>
                                            <td calss="bottom_center" colspan="14" align="center">
                                                <div id="divSave">
                                                    <input id="btnPrev" value="上一步" class="stbtm01" onclick="goto_prev()"
                                                    type="button" />
                                                    <input id="btnSubmit" value="保存" class="stbtm01" onclick="submit_data()"
                                                    type="button" style="DISPLAY: none" />
                                                    <input id="btnNext" value="下一步" class="stbtm01" onclick="go_next()" type="button"
                                                    />
                                                    <input id="InsidesRows_Count" type="hidden" readonly="readonly" name="InsidesRows_Count"
                                                    align="right" value="2" />
                                                    <input id="OutsidesRows_Count" type="hidden" readonly="readonly" name="OutsidesRows_Count"
                                                    align="right" value="1" />
                                                    <input id="HKRows_Count" type="hidden" readonly="readonly" name="HKRows_Count"
                                                    align="right" value="1" />
                                                    <input id="LOCALRows_Count" type="hidden" readonly="readonly" name="LOCALRows_Count"
                                                    align="right" value="1" />
                                                    <input id="outside_payment_mode_cash" type="hidden" name="outside_payment_mode_cash"
                                                    style="width:100px" value="10001" />
                                                    <input id="outside_payment_mode_bank" type="hidden" name="outside_payment_mode_bank"
                                                    style="width:100px" value="10002" />
                                                </div>
                                                <div id="divReturn" style="DISPLAY:none">
                                                    <input id="btnUpdate" value="保存" class="stbtm01" onclick="submit_data()"
                                                    type="button" style="DISPLAY: none"/>
                                                    <input id="btnReturnBack" value="返回" class="stbtm01" onclick="goto_return_back()"
                                                    type="button" />
                                                </div>
                                            </td>
                                        </tr>
                                    </TABLE>
                                    
                                </BODY>
                            </div>
                        </TD>
                    </TR>
                </TABLE>
                <input name="hiAction_Flag" type="hidden" id="hiAction_Flag" />
                <input name="hiBoeID" type="hidden" id="hiBoeID" value="11481889" />
                <input name="hiBoeTypeID" type="hidden" id="hiBoeTypeID" value="415" />
                <input name="hiResourceID" type="hidden" id="hiResourceID" value="1212"
                />
                <input name="hiMenuPath" type="hidden" id="hiMenuPath" />
                <input name="hiDefaultEmployeeID" type="hidden" id="hiDefaultEmployeeID"
                value="244680" />
                <input name="hiDefaultEmployeeNumber" type="hidden" id="hiDefaultEmployeeNumber"
                value="201207644083" />
                <input name="hiDefaultEmployeeName" type="hidden" id="hiDefaultEmployeeName"
                value="刘闯" />
                <input name="hiEmployeeID" type="hidden" id="hiEmployeeID" value="244680"
                />
                <input name="hiCulture" type="hidden" id="hiCulture" value="zh-CN" />
                <input name="hiSaveFlag" type="hidden" id="hiSaveFlag" value="true" />
                <input name="hiSourceFlag" type="hidden" id="hiSourceFlag" />
                <input name="hiEditFlag" type="hidden" id="hiEditFlag" />
                <input name="hiRecorderChange" type="hidden" id="hiRecorderChange" value="0"
                />
                <input name="hiBoeTemplateCode" type="hidden" id="hiBoeTemplateCode" value="INLAND_ERRAND1"
                />
                <input name="hiTaskID" type="hidden" id="hiTaskID" />
                <input name="hiProdId" type="hidden" id="hiProdId" value="39125" />
                <input name="hiBoeStatus" type="hidden" id="hiBoeStatus" value="101_BOE_DRAFT"
                />
                <input name="hiOperateTypeCode" type="hidden" id="hiOperateTypeCode" value="4009004"
                />
                <input name="hiBoeTypeCode" type="hidden" id="hiBoeTypeCode" value="SALESREP_INLAND_ERRAND"
                />
                <script language='javascript'>                   
                    function ExpandDiv(imgObject) {
                        if (imgObject.data == "1") {
                            imgObject.data = "0";
                            imgObject.src = "BoeWizardIndex_files/icon_expandall.gif";
                        } else {
                            imgObject.data = "1";
                            imgObject.src = "BoeWizardIndex_files/icon_collapseall.gif";
                        }
                        if (document.all["divSearch"] != null) document.all["divSearch"].style.display = (imgObject.data == "0") ? "none": "block";
                    }                    
                </script>
            </form>
            <script language="javascript" src="../../Common/Js/PageElementCheck.js">
            </script>
            <script src="../../Common/Js/DataChangeRecorder.js">
            </script>
        </body>
    
    </HTML>