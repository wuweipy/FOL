
  //差旅单据，出差申请单LOV 温春梅 2012-06-28
  function selErrandApplycode()
  {
 	var param = "errand_apply_code";
	var otherparam = '';
	var EmployeeId = 0;

	if(document.all("hiEmployeeID") != null)
	 {
        EmployeeId = document.all("hiEmployeeID").value;
	 }
	 
	 otherparam = "EmployeeId=" + EmployeeId;
     OpenCommonLov('SelErrandApplyCode',param,450,480,otherparam);
  }

 // 报销单据，不需要进行费用分解，外事SSC申请单校验必输项目
  function checkSSCinfo(culture,lineNum,idNum)
  {
    //debugger;
    // 第一次保存，operation_type_dist_flag为空(不需要费用分解),则不校验外事SSC
    //var distFlag = document.all("operation_type_dist_flag").value;
    // 第二次保存，不需要费用分解的单据，校验外事SSC申请单
    if(document.getElementById("divBoeSSCInfo") != null 
        && document.getElementById("divBoeSSCInfo").style.display != 'none'
        //&& distFlag != ""
        && !validIsNeedFeePart(culture,'N'))
    {
        alert(getSieMessage1("SIE_BOE_FOREIGN_SSC_EMPITY",culture,lineNum));
        document.getElementById("foreign_ssc_app"+idNum).focus();
        return false;
    }
    
    return true;
  }
  
  //单据待财务审核，外事SSC可手工输入
  function setSscReadly()
  {
       var prefix = "foreign_ssc_app";
       var inputObjId;
       var boeStatus = document.all("hiBoeStatusF").value;
       for(var i = 0 ; i < document.getElementsByTagName("input").length ; i ++)
       {	
		    inputObjId = document.getElementsByTagName("input")(i).id;
	        if( inputObjId != null && inputObjId.indexOf(prefix) > -1)
		    {
			    if (boeStatus == "301_FINANCE_AUDIT")
		        {
	                document.getElementsByTagName("input")(i).readOnly = false;
	                document.getElementsByTagName("input")(i).maxLength = 15;
		        }
		        else
		        {
		            document.getElementsByTagName("input")(i).readOnly = true;    
		        }
		    }//end if
	    }//end for
	}

// 修改记录2012-09-13 wenchunmei
// 业务类型修改，获取业务类型科目属性
function setOptPROXYFlag()
{
    //optTypeId,orgId,deptId 查找科目属性需要参数

   //页面加载时，取 hiOptPROXYFlag 的值。
   if(document.getElementById("hiOptPROXYFlag") != null)
   {
      var optTypeId = document.getElementById("operation_type_id").value;
      var orgId = document.getElementById("hiOrgID").value;
      var deptId = document.getElementById("hiDeptID").value;
      
      var result = "";
      var obj = new ActiveXObject("Microsoft.XMLHTTP");
      var sysdate = new Date().valueOf();
	  
     obj.open("GET","../../SysCommon/Pages/SieCommonSrv.aspx?srvtype=GET_OptPROXY_Flag&optTypeId="+optTypeId + "&orgId=" + orgId + "&deptId=" + deptId,false);
     obj.send();
     //服务器端处理返回的是经过escape编码的字符串.
     result = unescape(obj.responseText);
	 document.getElementById("hiOptPROXYFlag").value = result;
	 
   }
	
} // end setOptPROXYFlag


// 业务类型修改，专项资金，或业务类型+部门的科目属性为代办，为方便强管控项目，清空营销项目
// 如果为非管控项目，可重新选择
function clearCDMProject()
{
	var cdm_project_id = document.getElementById("cdm_project_id0");
    var cdm_project_name = document.getElementById("cdm_project_name0");
    var cdm_hc_contract_number = document.getElementById("cdm_hc_contract_number0");
    
    var optPROXYFlag = "";
    if (document.getElementById("hiOptPROXYFlag") != null)
    {
        optPROXYFlag = document.getElementById("hiOptPROXYFlag").value;
    }
    var zTEYXOpt =  "";
    if (document.getElementById("hiZTEYXOpt") != null)
    {
        zTEYXOpt = document.getElementById("hiZTEYXOpt").value;
    }
    // 借款模板
    var imgSelCdmProjectTmp = document.getElementById("imgSelCdmProject0");
        var boeTypeCode = document.getElementById("hiBoeTypeCode");
    //imgSelCdmProject0 
    //--国际借款单固定一行
    // 国际报销单，非公务卡报销单多行
    // 分配页面不清空操作包含报销单，差旅单（修改业务类型会自动删除分配行）
    // tblMainInfo包含控件数循环
    // imgSelCdmProject包含字符串的控件数据，从1开始，清空3个控件的值
  
    if (imgSelCdmProjectTmp != null && boeTypeCode != null
        && boeTypeCode.value == 'OVERSEAS_LOAN')
    {
        var imgSelCdmProStyle = imgSelCdmProjectTmp.style.display;
        if (imgSelCdmProStyle != "none" && optPROXYFlag != 'Y' && zTEYXOpt != 'Y')
        {
            cdm_project_id.value = "";
            cdm_project_name.value = "";
            cdm_hc_contract_number.value = "";
                
        }
    }
    
    //报销模板
    var imgSelCdmProjectTmp1 = document.getElementById("imgSelCdmProject1");
    //imgSelCdmProject0 
    //--国际借款单固定一行
    // 国际报销单，非公务卡报销单多行
    // 分配页面不清空操作包含报销单，差旅单（修改业务类型会自动删除分配行）
    // tblMainInfo包含控件数循环
    // imgSelCdmProject包含字符串的控件数据，从1开始，清空3个控件的值

    if (imgSelCdmProjectTmp1 != null && boeTypeCode != null
        && (boeTypeCode.value == 'OVERSEAS_EXPENSES'|| boeTypeCode.value == 'UNIVERSAL_EXPENSES'))
    {   
        var imgNum = document.getElementById('tblMainInfo').getElementsByTagName('img').length;
        

        var imgSelCdmProStyle = imgSelCdmProjectTmp1.style.display;       
        if (imgSelCdmProStyle != "none" && optPROXYFlag != 'Y' && zTEYXOpt != 'Y')
        {
            //循环设置清空营销项目，文本合同值
            for(var i = 1 ; i < imgNum + 1 ; i ++)
	        { 
	            var idcdm_project_id = "cdm_project_id" + i;
	            var idcdm_project_name = "cdm_project_name" + i;
	            var idcdm_hc_con_number = "cdm_hc_contract_number" + i;
	            
	            
	            if (document.getElementById(idcdm_project_id) != null)
	            {
	                document.getElementById(idcdm_project_id).value = "";
	                document.getElementById(idcdm_project_name).value = "";
	                document.getElementById(idcdm_hc_con_number).value = "";   
	                
	            }
	            
	        }        

                
        }
    }
    
}

function setAmountReadly()
{
   if(document.all("hiBoeTypeCode").value == "OVERSEAS_CARD")
   {
      var prefix = "expense_amount";
      var inputObjId;
      var boeStatus = document.all("hiBoeStatusF").value;
      for(var i = 0 ; i < document.getElementsByTagName("input").length ;
      i ++ )
      {
         inputObjId = document.getElementsByTagName("input")(i).id;
         if( inputObjId != null && inputObjId.indexOf(prefix) > - 1)
         {
            if (boeStatus == "301_FINANCE_AUDIT")
            {
               document.getElementsByTagName("input")(i).readOnly = false;
               document.getElementsByTagName("input")(i).maxLength = 15;
            }
            else
            {
               document.getElementsByTagName("input")(i).readOnly = true;
            }
         } // end if
      } // end for
   }
}
 // 非公务卡报销单据，
 // 对接类型为IOA车辆维修业务类型，对应快速编码SIE_IOAAPPNO_OPERATETYPE，校验必输
  function checkJoinObjinfo(culture,lineNum,idNum)
  {
    //debugger;
    // 需要确认：费用分解行，增值税报销单据应该可以同调用次方法校验
    // 判断业务类型是否是快速编码定义业务类型
    var operateType = "";
    var joinOgjType = "";
    var boeType = "";
    // 组织定义是否车辆对接
    var strIsIoaCar = "";
    if(document.all("hiIsIoacarFlag")!=null)
    {
        strIsIoaCar = document.all("hiIsIoacarFlag").value;
    } 
    if (document.all("operation_type_code") != null)
    {
        // 报销业务类型
        operateType  = document.all("operation_type_code").value;
    }
    
    if (document.all("hiIoaAppNoValues") != null)
    {
        // 定义对接业务类型 
        joinOgjType = document.all("hiIoaAppNoValues").value;
    }
    
    if (document.all("hiBoeTypeCode") != null)
    {
        // 定义对接单据类型 
        boeType = document.all("hiBoeTypeCode").value;
    }

    // 不需要费用分解的单据，才校验IOA车辆维修必输
    if( operateType != "" && joinOgjType != "" && joinOgjType.indexOf(operateType) > -1
        && !validIsNeedFeePart(culture,'N')
        && (boeType == "UNIVERSAL_EXPENSES" || boeType == "TAX_EXPENSES")
        && strIsIoaCar == 'Y')
    {
        alert(getSieMessage1("SIE_BOE_IOA_APP_NUM",culture,lineNum));
        document.all("join_obj_id"+idNum).focus();
        return false;
    }
    
    return true;
  }