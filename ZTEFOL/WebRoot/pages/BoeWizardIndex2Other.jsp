﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*,Business.Claims.*,Common.Location.*"%>
<HTML>
    
    <HEAD>
        <title>
            报销信息
        </title>
        <LINK href="BoeWizardIndex_files/style.css" type="text/css" rel="stylesheet">
        <script language="javascript" src="validate.js">
        </script>
        <link rel="stylesheet" href="css/pikaday.css">
         <script src="pikaday.js"  charset="utf-8">
         </script>
    </HEAD>
    
    <body>
        <form name="Form1" method="post" action="#" id="Form1">
        	<input type="hidden" id="action" name="action" />
            <TABLE id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0">
                <TR height="1">
                    <TD>
                        <FONT face="宋体">
                            <table class="tabbar1" cellspacing="0" cellpadding="0" align="center"
                            border="0">
                                <tbody>
                                    <tr height="24">
                                        <td>
                                            <img class="icon" height="10" src="../../Common/Images/bullet0.gif" />
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
                                                在线填单
                                            </span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <br style="line-height: 1px;">
                            <input name="hiPFlag" type="hidden" id="hiPFlag" style="WIDTH: 40px; HEIGHT: 21px"
                            size="1" />
                            <input name="hiHasFrame" type="hidden" id="hiHasFrame" style="WIDTH: 40px; HEIGHT: 21px"
                            size="1" />
                        </FONT>
                    </TD>
                </TR>
                <TR>
                    <TD vAlign="top">
                        <div id="divXml">
                            
                            <HEAD>
                                <TITLE>
                                    国内差旅费报销信息
                                </TITLE>
                                <script language="javascript">
                                    function submit_data() {
                                        document.getElementById("action").value = "2";
                                        document.getElementById("save").value = "save";
                                        Form1.submit();
                                    }
                               
                                </script>
                            </HEAD>
                            
                            <BODY>
                                <TABLE width="100%" align="center" id="tblHeader" bgColor="#1475b3" class="inputbl"
                                border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td align="left" style="border-right-style:none;width:252px">
                                            <img class="icon" src="BoeWizardIndex_files/icon_note.gif" />
                                            报销头信息
                                            <input id="boe_type_id" type="hidden" name="boe_type_id" readonly="readonly"
                                            value="415" />
                                        </td>
                                        <!--
                                        <td align="right" style="border-left-style:none;">
                                            <a href="javascript:goto_employeeInfo(1);">
                                                查看员工借款情况
                                            </a>
                                            <input id="boe_id" type="hidden" name="boe_id" readonly="readonly" value="11484661"
                                            />
                                            <input id="system_date" type="hidden" name="system_date" readonly="readonly"
                                            value="2013-07-24" />
                                            <input id="cur_territory_code" type="hidden" name="cur_territory_code"
                                            readonly="readonly" value="CN" />
                                            <input id="hiPostBackData" type="hidden" name="hiPostBackData" readonly="readonly"
                                            value="" />
                                        </td>  -->
                                    </tr>
                                </TABLE>
                                <%
                                  BClaim bClaim = (BClaim)request.getAttribute("claim");
                                  boolean isInit = false;
                                  if(bClaim != null && bClaim.getSummary() != null)
                                  {
                                     isInit = true;
                                  }
                                %>
                                <TABLE width="100%" align="center" id="tblMain" bgColor="#1475b3" class="inputbl"
                                border="0" cellPadding="0" cellSpacing="0">
                                    <TR>
                                        <td class="title" nowrap="nowrap">
                                            <div align="left">
                                                摘要
                                                <font color="red">
                                                    *
                                                </font>
                                            </div>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="boe_abstract" name="boe_abstract" 
                                            onpaste="limitPaste(this, 240)" style="width:650px" value="" />
                                        </td>
                                        
                                        <script>
                                        <%if(isInit){%>
              	                             document.getElementById("boe_abstract").value = "<%= bClaim.getSummary()%>";
                                        <%}%>
                                        </script>
                                    </TR>
                                    <tr>
                                        <td class="title" nowrap="nowrap" id="tdInlandOperationTypeID0" style="DISPLAY:none">
                                            <div align="left">
                                                业务类型
                                                <font color="red">
                                                    *
                                                </font>
                                            </div>
                                        </td>
                                        <td id="tdInlandOperationTypeID1" style="DISPLAY:none">
                                            <div nowrap="nowrap">
                                                <input id="operation_type_id" type="hidden" readonly="readonly" name="operation_type_id"
                                                width="100px" value="" />
                                                <input id="operation_type_code" type="hidden" readonly="readonly" name="operation_type_code"
                                                style="width:100px" onpropertychange="operation_type_code_change(1)" value=""
                                                />
                                                <input id="operation_type_dist_flag" type="hidden" readonly="readonly"
                                                name="operation_type_dist_flag" width="100px" value="" />
                                                <input id="operation_type_name" type="text" onchange="boe_change_opr_name(this.id)"
                                                onkeypress="javascript:return selOprTypeEnterKey(this.id,1);" name="operation_type_name"
                                                style="width:200px" value="" />
                                                <img id="imgSelOperationType" src="../../Common/images/page_inspector1.gif"
                                                style="CURSOR:hand;" alt="选择业务类型" onclick="selOperationTypeErrandOnLine(1)"
                                                />
                                            </div>
                                        </td>
                                        <td class="title" nowrap="nowrap">
                                            <div align="left">
                                                员工级别
                                                <font color="red">
                                                    *
                                                </font>
                                            </div>
                                        </td>
                                        <td>
                                        	 <%
                                               HashMap<Integer, String> employee = (HashMap<Integer, String>)request.getAttribute("employeeInfo");
  	                                           Iterator<Integer> employeeIterator = employee.keySet().iterator();
                                           %>
                                            <select id="employee_grade" name="employee_grade" size="0" style="width:150px;">
                                              <% while(employeeIterator.hasNext()){
                                         		      int key = employeeIterator.next();
                                         		   %>
                                         		   <option value=<%= key%>><%= employee.get(key)%></option>
                                         		   <% } %>   
                                            </select>
                                        </td>
                                        <script>
                                        <%if(isInit){%>
              	                             document.getElementById("employee_grade").value = <%= bClaim.getEmployLevel()%>;
                                        <%}%>
                                        </script>
                                        <td class="title" nowrap="nowrap">
                                            <div align="left">
                                                报销币种
                                            </div>
                                        </td>
                                        <td style="width:150px;">
                                            <input id="currency_code" type="text" readonly="readonly" name="currency_code"
                                            style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%"
                                            align="left" value="CNY" />
                                        </td>
                                    </tr>
                                    <tr id="trInlandECT">
                                        <td class="title" nowrap="nowrap" id="tdInlandErrantTitle" style="DISPLAY:none">
                                            <div align="left" id="divInlandErrantTitle">
                                                出差类型
                                                <font color="red">
                                                    *
                                                </font>
                                            </div>
                                        </td>
                                        <td id="tdInlandErrant" style="DISPLAY:none">
                                            <div id="divInlandErrant">
                                                <select id="errant_type" name="errant_type" size="0" style="width:200px;">
                                                    <option value="">
                                                        请选择
                                                    </option>
                                                    <option value="BUSINESS_ERRANT">
                                                        业务差旅费
                                                    </option>
                                                    <option value="MEETING_ERRANT">
                                                        会议差旅费
                                                    </option>
                                                    <option value="NATURE_ERRANT">
                                                        正常差旅费
                                                    </option>
                                                    <option value="STAFFER_TRAINING">
                                                        员工培训费
                                                    </option>
                                                    <option value="USER_TRAINING">
                                                        用户培训费
                                                    </option>
                                                </select>
                                            </div>
                                        </td>
                                        <td class="title" nowrap="nowrap" style="display:none">
                                            <div align="left" id="divHCHeader">
                                                国际合同号
                                            </div>
                                        </td>
                                        <td style="display:none">
                                            <div nowrap="nowrap" id="divHCHeaderSel">
                                                <input id="hc_contract_number" type="hidden" readonly="readonly" name="hc_contract_number"
                                                style="width:140px" value="" />
                                                <input id="hc_con_number_name" type="text" name="hc_con_number_name" style="width:140px"
                                                onchange="boe_change_hccontract_name(this.id,1)" onkeypress="javascript:return selConNumWithKey(this.id,1);"
                                                value="" />
                                                <img src="../../Common/images/page_inspector1.gif" style="CURSOR:hand;"
                                                alt="选择合同" onclick="selContractNumber(this.id,1,1)" id="imgSelContract"
                                                />
                                            </div>
                                        </td>
                                         
                                        <td class="title" nowrap="nowrap" align="left">
                                            <div align="left">
                                                冲账金额
                                            </div>
                                        </td>
                                        <td>
                                            <input id="strike_balance_amount" type="text" name="strike_balance_amount" readonly="readonly" maxlength="15" 
                                             style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%"
                                             validate="DOUBLE2" value="" />
                                        </td>
                                       <script>
                                        <%if(isInit && bClaim.getHedgeAccount() != -1){%>
              	                             document.getElementById("strike_balance_amount").value = <%= bClaim.getHedgeAccount()%>;
                                        <%}%>
                                        </script>
                                        <td class="title" nowrap="nowrap" align="left">
                                            <div align="left">
                                                会计调整
                                            </div>
                                        </td>
                                        <td style="width:150px;">
                                            <input id="adjust_amount" type="text" name="adjust_amount" readonly="readonly" 
                                            style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: transparent; BORDER-BOTTOM-STYLE: none;width:100%"
                                            validate="DOUBLE2" value="" />
                                        </td>
                                        <script>
                                        <%if(isInit && bClaim.getAccountAdjust() != -1){%>
              	                             document.getElementById("adjust_amount").value = <%= bClaim.getAccountAdjust()%>;
                                        <%}%>
                                      </script>                                    
                                    </tr>
                                    <tr id="trEUnitProj" style="display:none">
                                        <td class="title">
                                            <div align="left">
                                                E学院项目
                                            </div>
                                        </td>
                                        <td colspan="11">
                                            <div nowrap="nowrap">
                                                <input id="project_no" readonly="readonly" name="project_no" style="width:200px"
                                                value="" />
                                                <img id="imgSelEUnitProj" src="../../Common/images/page_inspector1.gif"
                                                style="CURSOR:hand;" alt="选择E学院项目" onclick="selEUnivProj()" />
                                            </div>
                                        </td>
                                    </tr>
                                </TABLE>
                                                            
                                <TABLE width="100%" align="center" id="tblButtonInfo" bgColor="#1475b3"
                                class="inputbl" border="0" cellPadding="0" cellSpacing="0">
                                        <tr>
                                            <td align="left" colspan="10" style="border-right-style:none;">
                                                <img class="icon" src="BoeWizardIndex_files/icon_note.gif" />
                                                报销费用信息
                                            </td>
                                             
                                        </tr>
                                    <TR>
                                        <td class="title" nowrap="nowrap">
                                            <div align="left">
                                                报销总金额
                                            </div>
                                        </td>
                                        <td colspan="7">            
                                       <% if(bClaim.getTotalFeeOther()==0) {   %>                       
                                            <input type="text" id="expense_amount_total" name="expense_amount_total" 
                                             style="width:350px"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  />                                             
                                             <%}                                             
                                             else {%>                                             
                                            <input type="text" id="expense_amount_total" name="expense_amount_total" 
                                             style="width:350px"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%= bClaim.getTotalFeeOther()%>" />                                             
                                             <%}%>
                                        </td>
                                    </TR>
                                   
                                    <tr style="DISPLAY:none">
                                        <td colspan="8">
                                            <input id="ets_hotel_sum_amount" type="text" readonly="readonly" name="ets_hotel_sum_amount"
                                            align="left" value="" />
                                            <input id="boe_status" type="text" readonly="readonly" name="boe_status"
                                            align="left" value="101_BOE_DRAFT" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td calss="bottom_center" colspan="11" align="right">
                                            <input id="btnFeePart" value="费用分割" class="stbtm01" style="DISPLAY:none"
                                            onclick="goto_step_feePart()" type="button" />
                                             
                                            <div id="divSave">
                                                <input id="BoeLines_Count" type="hidden" readonly="readonly" name="BoeLines_Count"
                                                align="right" value="1" />
                                                <input id="btnPrev" value="上一步" class="stbtm01" onclick="go_prev()" type="button"
                                                />
                                                <input id="btnSubmit" value="保存" class="stbtm01" type="submit" onclick="submitData()" style="DISPLAY: none"/>
                                                <input id="btnNext" value="下一步" class="stbtm01" onclick="go_next()" type="submit"
                                                />
                                            </div>
                                            
                                              <div id="divReturn" style="DISPLAY:none">
                                            	  <input type="hidden" id="save" name="save" value="no" />
                                                <input id="btnUpdate" value="保存" class="stbtm01"
                                                type="submit" style="DISPLAY: none"/>
                                                <input id="btnReturnBack" value="返回" class="stbtm01" onclick="go_return_back()"
                                                type="button" />
                                            </div>
                                        </td>
                                    </tr>
                                </TABLE>
                                
<!--                                
                                <TABLE width="100%" id="tblInlandHandDesc" align="left" bgColor="#1475b3"
                                class="inputbl" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td colspan="3">
                                            说明
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="1%">
                                            1
                                        </td>
                                        <td>
                                            一三四五营和海外子公司员工需要对国内差旅费进行费用分配，请保存当前信息后，点击“国际项目费用分配”按钮进入分配页面。
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="1%">
                                            2
                                        </td>
                                        <td>
                                            对eTrip上线部门，出差交通工具为‘飞机’时，eTrip机票是必选项，如应选而未选，审核会计将予退单处理。
                                        </td>
                                    </tr>
                                </TABLE>
  -->
                            </BODY>
                        </div>
                    </TD>
                </TR>
                <tr>
                    <td>
                        <div id="divAttach">
                        
<!--                          
                            <table id="tbListHead" class="inputbl" cellspacing="1" cellpadding="0"
                            align="center">
                                <tr>
                                    <td class="bottom_left" valign="middle">
                                        <span id="lblAttachTitle">
                                            附件列表:
                                        </span>
                                        <input type="button" name="btnUpLoad" value="附件上传" onclick="javascript:__doPostBack('btnUpLoad','')"
                                        language="javascript" id="btnUpLoad" class="stbtm01" />
                                    </td>
                                </tr>
                            </table> 
 -->                           
                            
                            <script language="javascript">
                                function delete_Confirm(objID) {
                                    var returnFlag;
                                    if (document.all(objID).disabled == false) {

                                        returnFlag = confirm('确认删除？');
                                        if (returnFlag == true) //确认删除，先删除附件，再保存数据（在线填单第二步）
                                        {
                                            document.all('hiAction_Flag').value = 'save';
                                        }
                                        return returnFlag;
                                    } else {
                                        return false;
                                    }
                                }
                            </script>
                           <!--
                            <table class="datatbl" cellspacing="0" cellpadding="0" rules="all" border="1"
                            id="UploadFile1_dgAttach" style="border-collapse:collapse;">
                                <tr class="head">
                                    <td class="title" nowrap="nowrap" style="width:120px;">
                                        文件名
                                    </td>
                                    <td>
                                        标题
                                    </td>
                                    <td class="title" nowrap="nowrap" style="width:120px;">
                                        文件大小
                                    </td>
                                    <td>
                                        备注
                                    </td>
                                    <td class="title" nowrap="nowrap" align="left">
                                        操作
                                    </td>
                                    <td class="title" nowrap="nowrap" align="center">
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>   
                            -->
                            <br style="line-height:1px;">
                        </div>
                    </td>
                    <td>
                        <br>
                    </td>
                </tr>
            </TABLE>
        </form>
        <script>
        	
        	   function submitData()
        	   {
        	   	  Form1.onsubmit = null;
        	   	  checkFiled();
        	   	  document.getElementById("action").value = 2;
        	   	  document.getElementById("save").value = "save";
        	   }
        	  
        	   function checkFiled()
        	   { 
        	      var validatorFiled = [{
              		name: "boe_abstract",
              		display: '摘要',
              		rules: 'required'
              	},
              	{
              		name: "expense_amount_total",
              		display: '报销总金额',
              		rules: 'required'
              	},
              	{
              		name: "adjust_amount",
              		display: '会计调整',
              		rules: 'numeric'
              	},
              	{
              		name: "strike_balance_amount",
              		display: '冲账金额',
              		rules: 'decimalRegex'
              	}];
              	
 
              	
              	
              	
              	var validator = new FormValidator('Form1',validatorFiled,function(errors,event)
              	  {
              	  	var errorString = "";
              		  for(var i = 0; i < errors.length; i++)
              		  {
              		  	 errorString += errors[i].message + "\n";
              		  }
              		  if(errorString != "")
              		    alert(errorString);
              	  }
              	); 
              	
             } 	
              	

            
            function sumfun(sumFiled,itemFiled)
            {
            	  var sum = document.getElementById(sumFiled);
            	  var total = document.getElementById("expense_amount_total");
            	  total.value = getInt(total.value);
             	  var oldsum = getInt(sum.value);
             	  sum.value = 0;
             	  rowIterator(function(count){
             	  	var value;
             	  	try
             	  	{
             	  		value = parseFloat(document.getElementById(itemFiled + count).value);
             	  	}
             	  	catch(e)
             	  	{}
             	  	if(value)
             	  	{
             	  	   sum.value = parseFloat(sum.value) + value;
             	  	   
             	  	}
             	  	}
             	  );
             	  total.value =  parseFloat(total.value) + parseFloat(sum.value) - parseFloat(oldsum);
            }
            
            function getInt(value)
            {
            	var ret = 0;
            	try{
            	  if(value != "") 
            	    ret = parseFloat(value);
            	}
            	catch(e){}
            	return ret;
            }
            
            function toFloat(val)
            {
                var ret = 0;
            	try{
            	  if(val) 
            	    ret = parseFloat(val).toFixed(1);
            	}
            	catch(e)
            	{
            	  return 0;
            	}
            	return ret;
            }
            
            
             
             function rowIterator(fun)
             {
             	 var table = document.getElementById("tblMainInfo");
             	 var rows = table.rows.length;
             	 var count = "";
             	 for(var i = 3; i < rows; i++)
             	 {
             	 	  if(!isRowEmpty(i))
             	 	  {
             	 	     fun(i);
             	 	     count += i + " ";
             	 	  }
             	 }
             	 document.getElementById("count").value = count;
             } 
             
             function isEmptyStr(input)
             {
                //console.log(input);
             	  return document.getElementById(input).value == "";
             }
        	  function go_next() {
               document.getElementById("action").value = "3";
               document.getElementById("save").value = "next";
        	   	 Form1.onsubmit = null;
        	   	 checkFiled();
           }
           function go_prev() {
               document.getElementById("action").value = "1";
               document.getElementById("save").value = "pre";
               Form1.onsubmit = null;
               Form1.submit();
           }
           

           
            </script>
    </body>

</HTML>
