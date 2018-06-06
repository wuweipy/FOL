// 为了性能考虑，没有文件头   作者：李智勇

// 在输入框回车自动完成的LOV
function LovSelEnterKey(type,retids,w,h)
{
  // retids:返回值对象ID
  // 第一个和第二个固定为ID和文本  
  var key = window.event.keyCode;
  if(key == 13) 
  { 
    // 判断是否按下回车键
    var ids = retids.split(",");
    if(ids.length < 2) 
      return false;
    var url = GetLovTypeUrl(type);
    if ( url.length < 1)  
    {
      alert("不存在供选择的LOV！");
      return false;
    }// end if
    url = url+"&autoSel=1"+"&autoValue="+escape(escape(document.getElementById(ids[1]).value));// 20100621 林少鹏 处理乱码问题
    url = url.replace(/&/g,"[$CONN_FLAG$]");    
    if( w < 640) 
      w = 640;
    if( h < 480) 
      h = 480;
    var val = OpenModalWin("../../FrameCommon/Pages/PubSelFram.aspx?url=" + url,w,h);
    if(val == null || val == "") return ;
    // 值
    var vals = val.split("[$SPLIT_FLAG$]");
    for(i=0;i<ids.length;i++)
    {
      if(document.getElementById(ids[i]) != null && vals.length > i)
      {
        document.getElementById(ids[i]).value = vals[i];
      }// end if
    }//end for
    
    return false;   
  }
  else
  {
    return key;
  }
}// end LovSelEnterKey

// 点击按钮弹出的LOV选择
function LovSelClick(type,retids,w,h)
{
  var ids = retids.split(",");
  if(ids.length < 2) 
    return false;
  var url = GetLovTypeUrl(type);
  if ( url.length < 1)  
  {
    alert("不存在供选择的LOV！");
    return false;
  }// end if
  url = url.replace(/&/g,"[$CONN_FLAG$]");    
  if( w < 400)
    w = 400;
  if( h < 300) 
    h = 300;
  var val = OpenModalWin("../../FrameCommon/Pages/PubSelFram.aspx?url=" + url,w,h);    
  if(val == null || val == "") return ;
  // 值
  var vals = val.split("[$SPLIT_FLAG$]");
  for(i=0;i<ids.length;i++)
  {
    if(document.getElementById(ids[i]) != null && vals.length > i)
    {
      document.getElementById(ids[i]).value = vals[i];
    }// end if
  }//end for
  
}// end LovSelClick



//方式1：在输入框回车自动选择
//弹开一个LOV窗口并自动完成处理返回值(不含不定参数)
//用户在输入框回车自动进行LOV选择。如果当结果只有一个，自动完成选择
function SelLovEnterKey(type,valid,txtid,w,h,flag1,param)
{
//debugger;
    key = window.event.keyCode;         
    if(key == 13) {  //判断是否按下回车键
      var url = GetLovTypeUrl(type,flag1);
      if ( url.length < 1)  {
          alert("不存在供选择的LOV！");
          return ;
      }
      url = url+"&autoSel=1"+"&autoValue="+escape(escape(document.getElementById(txtid).value));// 20100621 林少鹏 处理乱码问题
      SelLovWithUrl(url,type,valid,txtid,w,h,flag1,param);
      return false;   
    }
    else{
      return key;
    }
}

//方式2：点击按钮自动选择
//弹开一个LOV窗口并自动完成处理返回值(不含不定参数)
//如果当结果只有一个，自动完成选择
function SelLovAutoSel(type,valid,txtid,w,h,flag1)
{
    var url = GetLovTypeUrl(type,flag1);
    
    if ( url.length < 1) {
          alert("不存在供选择的LOV！");
          return ;
        }
    url = url+"&autoSel=2"+"&autoValue="+escape(escape(document.getElementById(txtid).value));// 20100621 林少鹏 处理乱码问题
    SelLovWithUrl(url,type,valid,txtid,w,h,flag1);
}

//方式3：点击按钮手工选择，而且不过滤数据
//弹开一个LOV窗口并完成处理返回值(不含不定参数)
function SelLov(type,valid,txtid,w,h,flag1)
{
     var url = GetLovTypeUrl(type,flag1);
     
     
     
    if ( url.length < 1)
        {
          alert("不存在供选择的LOV！");
          return ;
        }
    SelLovWithUrl(url,type,valid,txtid,w,h,flag1);
}
//弹开一个LOV窗口并完成处理返回值(不含不定参数)
function SelLovWithUrl(url,type,valid,txtid,w,h,flag1,param)
{
    /*
    var url = GetLovTypeUrl(type,flag1);
    
    if ( url.length < 1)
        {
          alert("不存在供选择的LOV！");
          return ;
        }
    */
    url = url.replace(/&/g,"[$CONN_FLAG$]");
    //add by pqq 20050802
    if(w<640)
        w=640;
    if(h<480)
        h=480;
    
    if(url.indexOf('LovKey') < 0)
    {
	    if(url.substring(url.length -5,url.length).indexOf('.aspx') >= 0)
	    {
		    url +='?LovKey=' + type;
	    }
	    else
	    {
		    url +='[$CONN_FLAG$]LovKey=' + type;
	    }
	}
	
    var val = OpenModalWin("../../FrameCommon/Pages/PubSelFram.aspx?url=" + url,w,h);

    if(val == null || val == "") return ;
    var vals = val.split("[$SPLIT_FLAG$]");
    if(valid != "")
    {
        document.getElementById(valid).value = vals[0];
		
        if(txtid != "")
        {
           document.getElementById(txtid).value = vals[1].replace("&nbsp;"," ");
        }
    }
    else
    {    
        document.getElementById(txtid).value = vals[1].replace("&nbsp;"," ");
    }
    
    if(param!=undefined)
	{
        if(txtid != "")
        {
           document.getElementById(txtid).value = vals[2].replace("&nbsp;"," ");
        }
		document.all(param).value = vals[1].replace("&nbsp;"," ");
	}
    
    try {  //韩荣华于2004增加判断页面数据是否已被修改
       document.getElementById("hiRecorderChange").value = "1";
    }
    catch(er) {}
}

//弹开一个LOV窗口并完成处理返回值（含不定参数,要在Lov窗体中使用）
function SelLovWithParams(type,valid,txtid,w,h,params)
{
	
    var url = GetLovTypeUrl(type)+"&"+encodeURI(encodeURI(params));// 20100621 林少鹏 处理乱码问题
    if ( url.length < 1)
        {
          alert("不存在供选择的LOV！");
          return ;
        }
    url = url.replace(/&/g,"[$CONN_FLAG$]");
    //add by pqq 20050802
    if(w<640)
        w=640;
    if(h<480)
        h=480;
    var val = OpenModalWin("../../FrameCommon/Pages/PubSelFram.aspx?url=" + url,w,h);
   
	//alert(val);
	if(val == null || val == "") return ;
    var vals = val.split("[$SPLIT_FLAG$]");
	// 在线填单第二步，返回ID/项目名称/是否在产品树标志,在产品项目树上返回隐藏值
	//alert(vals);
	var culture = "zh-CN";
	//culture = GetSysCultureName(1);
    if(document.all("hiCulture")!=null)
    {
        culture = document.all("hiCulture").value;
    }
    else
	{
        culture = "en-US";
	}
	var boeTypeID ="";
	// 需要提示的报销模板（总部国外差旅费单据/国际报销单据
	///国际借款单据/总部国内差旅费单据/营销国外差旅费单据）
	if ( document.all("hiBoeTypeID") != null)
	{
		boeTypeID = document.all("hiBoeTypeID").value;
	}
	//alert(boeTypeID);
	// 2012-09-18 合并非公务卡报销单据
	if (vals.length > 2 && vals[2] =="Y" 
			&& (boeTypeID =="131" || boeTypeID =="331" || boeTypeID =="415" ||
		boeTypeID =="423" || boeTypeID =="421" || boeTypeID =="151"))
	{
		
		// 修改记录2012-09-13 

		//document.all('hiFprojFlag').value = vals[2];
		// LOV返回了营销项目,且才产品项目树上的营销项目
		// 在线填单第一步没有选择所在项目，第二步选择了营销项目，
		// 则提示：此项目已经纳入资源管控，请返回提单第一页选择“所在项目”填写 
		alert(getSieMessage("SIE_BOE_PRODJECT_FIRST_ISNULL",culture)); 
		
        //专项资金业务类型ZTEYX_SPECIAL_FUND对应业务类型及业务类型+部门为代办属性的，
        //报销行、分解页面的营销项目选择提示保持不变；否则，进行强管控：不允许保存、下一步
		if ( !((document.getElementById("hiZTEYXOpt") != null 
		    && document.getElementById("hiZTEYXOpt").value =='Y')
		    ||
		    (document.getElementById("hiOptPROXYFlag") != null
		    && document.getElementById("hiOptPROXYFlag").value == 'Y')))
		    
		{
		    return;
		}

	}
	//alert(document.all('hiFprojFlag').value);
		if(valid != "")
		{
			if(vals[0].indexOf('[$SPLIT_FLAG2$]') > 0)
			{
				var nameList = vals[0].split("[$SPLIT_FLAG2$]");
				document.getElementById(valid).value = nameList[0];
				if(document.all('pos_stru_element_holder')!=null)
				{
					document.all('pos_stru_element_holder').value =nameList[1];
				}
			}
			else
			{
				document.getElementById(valid).value = vals[0];
			}
			if(txtid != "")
			{
				document.getElementById(txtid).value = vals[1].replace("&nbsp;"," ");
			}
			if(document.all(params)!=null && vals.length > 2)
			{
				document.all(params).value = vals[2].replace("&nbsp;"," ");
			}
			else
			{
				var tmp = params.split("&");
				for(var k = 0;k<tmp.length;k++)
				{
					if(document.all(tmp[k])!=null)
					{
						var iTmp = 2 + k;
						if(iTmp < vals.length)
						{
							
							document.all(tmp[k]).value = vals[iTmp].replace("&nbsp;"," ");
						}
					}
				}
			}
		}
		else
		{    
			document.getElementById(txtid).value = vals[1];
		}
    try {  //韩荣华于2004增加判断页面数据是否已被修改
       document.getElementById("hiRecorderChange").value = "1";
    }
    catch(er) {}
}

//根据LOV类型得到LOV页面访问地址
function GetLovTypeUrl(type,flag)
{
    var url = "";
    
    switch(type)
    {
        case "selCdmProject":
            //选择营销项目
            url = "../../SysCommon/Pages/CdmProjectLov.aspx?type=modal";
            break;
        case "selDeptOnlyBase":
            //选择部门只能选基层部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&isonlybase=true";
            break;
        case "selPositionName":
            //选择职务名称
            url = "../../Admin/Position/PositionLov.aspx";
            break;
        case "selDeterminerDEPT"://modified by pqq 2005-06-13
            //选限定词类型为部门的限定词
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&dummy=true";
            break;
        case "selDeterminerPROD_PROJECT":
            //选限定词类型为产品项目的限定词
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=2"; 
            break;
        case "selDeterminerDEPT_CLASS":
            // 选择事业部分类（单选）
            url = "../../SysBasicData/BoeAdmin/SelDeptClass.aspx?single=true";
            break;
        case "selDept":
            //选择部门
            // 对责任支出查询功能页面作特殊处理，增加flag参数，部门树显示全部，禁用部门用红色显示
            url = "../../Admin/Dept/DeptLov.aspx?type=modal" + ((flag!=null)?"&resourceName="+flag:"");
            break;
	    case "sel_ERP_Dept":
            //选择ERP部门
            // 对责任支出查询功能页面作特殊处理，增加flag参数，部门树显示全部，禁用部门用红色显示
            url = "../../Admin/Dept/DeptLov.aspx?type=erpDept" + ((flag!=null)?"&resourceName="+flag:"");
            break;
		case "sel_ERP_Office":
              //选择ERP的科室
            // 对责任支出查询功能页面作特殊处理，增加flag参数，部门树显示全部，禁用部门用红色显示
            url = "../../Admin/Dept/DeptLov.aspx?type=erpOffice" + ((flag!=null)?"&resourceName="+flag:"");
            break;
        case "sel_budDept":
            //选择部门类别
            // 对责任支出查询功能页面作特殊处理，增加flag参数，部门树显示全部，禁用部门用红色显示
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&dept=budDept" + ((flag!=null)?"&resourceName="+flag:"");
            break;         
        case "selDeptLevel4":
            //选择资金层次为4的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=4";
            break;
        case "selDeptLevel9":
            //选择资金层次为4或5的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=9";
            break;
        case "selDeptLevel10":
            //选择最底层的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=10";
            break;
        // 以下四个部门树都为部门包括所有已禁用部门
        case "selDeptAll":
            //选择部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&IncludeEnabled=Y";
            break;
        case "selDeptLevel4All":
            //选择资金层次为4的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=4&IncludeEnabled=Y";
            break;
        case "selDeptLevel9All":
            //选择资金层次为4或5的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=9&IncludeEnabled=Y";
            break;
            // 选择四,五层部门,不包括禁用的
        case "selDeptLevel4|5":
        //选择资金层次为4或5的部门
        url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=9&IncludeEnabled=N";
        
        break;
        case "selDeptLevel10All":
            //选择最底层的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&fundlevel=10&IncludeEnabled=Y";
            break;
        // 以上四个部门树为部门都包括已禁用部门
        case "selDeptLevelfilter4":
            //选择最底层的部门
            url = "../../Admin/Dept/DeptLov.aspx?type=modal&filter=4" + ((flag!=null)?"&resourceName="+flag:"");
            break;
        // 以上四个部门树为部门都包括已禁用部门     
        case "selEmployeePosition":
            //选择职员职务
            url = "../../Admin/Employee/EmployeePositionLov.aspx?param1=hiChangeFlag";
            break;
        case "selLocationCode":
            // 选择职员所在地
            url = "../../Admin/Approval/ObjectValuesLov.aspx?";
            break;
		case "selFileType":
            // 文件类别
            url = "../../Admin/Approval/ObjectValuesLov.aspx?";
            break;
        case "selAdjustReasonCode":
            // 选择调整原因
            url = "../../Admin/Approval/ObjectValuesLov.aspx?";
            break;
        case "selProdProject":
            // 选择产品项目
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=2";
            break;
        case "selResource":
            //选择资源
            url = "../../Admin/Resource/ResourceLov.aspx?type=modal";
            break;
        case "selLookupType":
            //选择快速编码类型
            url = "../../SysBasicData/Pages/LookupLov.aspx?";
            break;
        case "SelHrDeptList":
            //回车查询人事部门Lov
            url = "../../Admin/Approval/SelDeptLov.aspx?"+flag;
            break;
        case "SelRelDeptList":
            //回车查询财务部门Lov
            url = "../../Admin/Approval/SelDeptLov.aspx?"+flag;
            break;            
        case "selEmployee":
            //选择所有者(即员工)
            url = "../../Admin/Approval/SelEmployeeLov.aspx?"+flag;
            break;
        case "selCfmEmployee":
            //选择所有者(即员工)
            url = "../../Admin/Approval/SelEmployeeLov.aspx?flag1=cfm_employeee";
            break;
        case "selEmployeeAll":
            //选择所有者(即员工,包括enableflag='N')
            url = "../../Admin/Approval/SelEmployeeLov.aspx?allFlag=Y";
            break;
            
        case "selDeptClass":
            // 选择事业部分类（多选）
            url = "../../SysBasicData/BoeAdmin/SelDeptClass.aspx?";
            break;
        case "SalesDept":
            //所属营销事业部
            
            break;
        case "selLeaderShip":
            // 选择首签领导
            url = "../../SysCommon/Pages/SelLeaderShipLov.aspx?";
            break;
        case "selLeaderShipPSE":
            // 选择首签领导,职务层次树，要求传入单据类型ID
            url = "../../SysCommon/Pages/SelPSELov.aspx?";
            break;
        case "selVendor":
            // 选择供应商
            url = "../../SysCommon/Pages/VendorLov.aspx?";
            break;
		case "selVendor2":
            // 选择供应商
            url = "../../SysCommon/Pages/VendorLov.aspx?";
            break;
        //************************************    以下代码由Joise.LI 添加于2004-08-27 09:45 *********************
        case "FirstBudgetAccount":
            // 一级预算科目 [Joise.LI@2004-08-27 09:45]
            url = "../../SysCommon/Pages/SelectFstBudgetAccount.aspx?language=US&type=FimCountry";    
            break;
        case "SecondBudgetAccount":
            // 一级预算科目 [Joise.LI@2004-08-27 09:45]
            url = "../../SysCommon/Pages/SelectSndBudgetAccount.aspx?language=US&type=FimCountry";    
            break;        
        //************************************    结束Joise.LI 添加**********************************************
        //add by pqq 05-04-13
        //case "ProductNotShared":
        //    //非共享产品
        //    url = "../../SysCommon/Pages/SelectProdProjects.aspx?Shared=N&type=1" + ((flag!=null)?"&checkauth="+flag:"");
        //    break;
        case "ProdProjectTree":
            //产品项目树全部能选择,
            url = "../../SysCommon/Pages/ProductTreeLov.aspx?data_type=0";
            break;
        case "ProductTree0":
            //选择所有层次的产品项目
            url = "../../SysCommon/Pages/ProductTreeLov.aspx?data_type=0&flag1="+flag;
            break;
        case "Product":
            //产品
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=1" + ((flag!=null)?"&checkauth="+flag:"");
            break;
        case "ProductAll":
            //可选择一到三级产品
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=3" + ((flag!=null)?"&checkauth="+flag:"");
            break;                
        case "Project":
            //项目
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=0" + ((flag!=null)?"&checkauth="+flag:"");
            break;
         case "ProjTypeProject":
            // 07-07-17 新增：选择项目类别为“产品项目”的项目
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=10" + ((flag!=null)?"&checkauth="+flag:"");
            break;
        case "ProjTypeProject_SaleType":
            // 07-07-31 新增：选择项目类别为“产品项目”的项目
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=20" + ((flag!=null)?"&checkauth="+flag:"");
            break;  
        case "ProProject":
            //产品项目
            url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=2"+((flag!=null)?"&checkauth="+flag:"");
            break;
        case "selOperationType":
            //网上报销的单据向导选择业务类型
            url = "../../SysCommon/Pages/OperationTypeLov.aspx?";
            break;
        case "selProdProjectType":
            //选产品大类
            url = "../../SysCommon/Pages/ProductTypeLov.aspx?";
            break;
        //add by pqq 05-04-13
        case "selProdNotShared":
            //选产品
            url = "../../SysCommon/Pages/ProductLov.aspx?";
            break;
        case "selPaymentTerritory":
            //选国家/地区
            url = "../../SysCommon/Pages/SIECountryProvinceLov.aspx?";
            break;
        case "selPaymentChinaCity":
            //选县/市
            url = "../../SysCommon/Pages/ChinaCitysLov.aspx?";
            break;
        case "selQualityCostType":
            // 选择质量成本
            url = "../../Admin/Approval/ObjectValuesLov.aspx?lookuptype=FBP_QUALITY_COST_TYPE";
            break;
        case "selPaymentVendor":
            // 选择付款的供应商
            url = "../../SysCommon/Pages/VendorLov.aspx?vendortype=EMPLOYEE";
            break;
        case "selDeterminerOPERATION_TYPE":
            //网上报销的限定词为业务类型
            url = "../../SysCommon/Pages/OperationTypeLov.aspx?";
            break;
        case "kxProject":
            //只选择康讯的项目
            url = "../../SysCommon/Pages/kxProdProjectLov.aspx?parentcode=18";
            break;
        case "selSIProject":
            //选择营销/投资项目
            url = "../../SysCommon/Pages/SalesInvestProjectLov.aspx?";
            break;
        case "selErpContract":
            //选择ERP合同号
            url = "../../SysCommon/Pages/ERPContractLov.aspx?";
            break;
        case "selDistDeptLevel4":
            //选择资金层次为4的费用分解部门
            url = "../../SysCommon/Pages/FeeDistDeptLov.aspx?fundlevel=4";
            break;
        case "BudgetAccountTree":
            // 预算科目
            url = "../../FundRepQuery/Lov/SelectTreeView.aspx?type=BUDGET_ACCOUNT";
            break;    
        case "selHKInvBankAcc":
            // 银行帐号
            url = "../../SysCommon/Pages/HKInvBankAccLov.aspx?";
            break;
        case "selProvince":
            // 选择省份
            url = "../../SysCommon/Pages/ProvincesLov.aspx?";
            break;
        case "selKpiDims":
            // 选择维度
            url = "../../Dist/Pages/PseDimEdit.aspx?";
            break;
        case "selKpiParams":
            // 选择假设值
            url = "../../Dist/Pages/PseParamEdit.aspx?";
            break;
        case "selDimValueCode":
            // 选择维度值
            url = "../../Dist/Pages/PseDimValueLov.aspx?";
            break;
        case "selReportDef":
            // 选择定义的报表
            url = "../../Report/ReportRun/RptLov.aspx?";
            break;
        case "selBudPse":
            // 选择所属节点(预算申报树)
            url = "../../Report/ReportRun/RptPseLov.aspx?";
            break;
        case "selBatchDims":
            // 选择成批改写维度
            url = "../../Report/ReportRun/RptDimLov.aspx?";
            break;
        case "selKpiDimsForDvOwner":
            // 选择维度信息
            url = "../../Report/Pages/RptDvOwner.aspx?";
            break;
        case "selTriaDimsForDvOwner":
            // 试算校验选择维度信息
            url = "../../Report/Tria/TriaSelDim.aspx?";
            break;
        case "selAbroadBankAcc":
            // 选择银行帐户
            url = "../../FundBasicData/AbroadBank/AccountSelect.aspx?";
            break;
        case "selMuchBudPse":
             // 选择预算节点(多选)
             url = "../../Comm/Inc/PseSelectLov.aspx?";
             break;
        case "selBudFileReport":
             //
             url = "../../Report/FileReport/FileReportLov.aspx?";
             break;
        case "selReportElement":
            // 选取数据报表元素
            url = "../../Report/DataRptRun/SelRptRunElement.aspx?";
            break;
        case "selCollectType":
            // 选取汇总方式
            url = "../../Report/DataRptRun/SelRptCollectType.aspx?";
            break;
        case "selBudAccElement":
            // 选取预算科目元素
            url = "../../Comm/Inc/SelectElementTree.aspx?";
            break;
        case "selHrDept":
            // 选取预算科目元素
            url = "../../SysCommon/Pages/SelHrDept.aspx?";
            break;
        case "selUserQueryHrDept":
            // 选取按用户查询权限隔离的人事部门
            url = "../../SysCommon/Pages/SelUserHrDept.aspx?type=uq&includedisable=Y";
            break;
        case "selFbpOrg":
            // 选取组织
            url = "../../SysCommon/Pages/OrgLov.aspx?includedisable=N";
            break;

        case "selCfmDept":
            // 选取用户有权限的部门
            //debugger;
            url = "../../PersonalDist/Pages/DeptLov.aspx?";
            if (flag != null && flag != "")
            {
                url += "psCode=" + flag;
                if (flag == "CFM_FEE_DIST")
                {
                    url += "&includedisable=Y";
                }
            }
            break;
         case "selCfmDeptTree":
            // 选取用户有权限的部门
            //url = "../../Query/DeptDeeQuery/SelPSELov.aspx?";
            url = "../../Query/DeptDeeQuery/SelPSELov.aspx?deptName=" + flag;
            break;
         case "selCdmHcCon":
            // 选取cdm文本合同
            url =  "../../SysCommon/Pages/CdmHcConNumberLov.aspx?";
            break;
	     case "selEmployeeWithNum":
            //选择所有者(即员工),返回ID、姓名+短工号
            url = "../../Admin/Approval/SelEmployeeLov.aspx?withShortNum='Y'";
            
            //debugger;
            var strTxtDept = document.getElementById("txtDept");
            var strHiDept = document.getElementById("hiDept");
             if(strTxtDept != null && strTxtDept != 'undefined' && strHiDept != null && strHiDept != 'undefined')
             {
                if(strTxtDept.value == undefined)
                {
                    strTxtDept.value = strTxtDept.innerText;
                }
                url = url + "&strDeptName=" + escape(escape(strTxtDept.value)) + "&strDeptID=" + escape(escape(strHiDept.value));
             }
     
            break;
	 
            
    }
    /*
	if ( url != "")
    {
    if ( document.getElementById("hiMenuPath") != null )
        url = url + "&menuPath=" + document.getElementById("hiMenuPath").value;
    if ( document.getElementById("hiResourceID") != null )
        url = url + "&resourceID=" + document.getElementById("hiResourceID").value;
    }        
	*/
	if( url.substring(url.length -1,url.length).indexOf('?') >=0)
	{
		url += "LovKey=" + type ;
	}
    else if( url.substring(url.length -4,url.length).indexOf('aspx') >=0)
	{
		url += "?LovKey=" + type ;
	}
	else
	{
	url += "&LovKey=" + type ;
	}
	

     
    return url;
}



