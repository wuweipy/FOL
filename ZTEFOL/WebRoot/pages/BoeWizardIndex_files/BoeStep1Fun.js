// 2008-09-10 蒋耀禹增加税组,税码 610001006148 结束
// 针对报销基础信息页面进行合法性校验 iCulture:1 中文 2 英文
function isValidForBaseInfo(iCulture)
{
    var culture = GetSysCultureName(iCulture);
    var info = "";
    if(document.all("employee_id").value == "")
    {
        alert(getSieMessage("SIE_BOE_EMPLOYEE_NAME",culture));
        return false;
    }
  // debugger; 
    // 如果所在项目是经营部，核算项目必选
    if(document.all("is_manage_dept").value == "Y" && document.getElementById("check_project_id").value == "")
    {
         if(document.all("is_display_team").value =="Y")
        {
            if(document.all("is_phone_pro").value=="3698")
           {
                alert(getSieMessage("SIE_BOE_CHECK",culture));
                return false;
           }  
        }
        else
        { 
            alert(getSieMessage("SIE_BOE_CHECK",culture));
            return false;
        }
    }
    if(document.all("boe_type_id").value == "")
    {
        alert(getSieMessage("SIE_BOE_TYPE",culture));
        return false;
    }
    /*
    if(document.all("boe_dept_id").value == "")
    {
        alert(getSieMessage("SIE_BOE_DEPT",culture));
        return false;
    }
    */
    
    if( document.all("personal_flag_yes").checked == false)
    {
        if(document.all("personal_flag_no").checked ==false)
        {
            alert(getSieMessage("SIE_BOE_PERSONAL_FLAG",culture));
            return false;
        }
    }
    //如果报销人所在部门的事业部分类为“营销国际”则lov内容只显示类别为‘营销项目’的有效产品项目纪录，该字段为必须项；
    //事业部分类为“系统产品、手机产品、中兴力维”则lov内容显示类别为‘产品项目’的有效产品项目纪录，该字段为必须项；
    //如果是其他的事业部分类，则该字段不允许选择任何项目
    var deptClass = document.all("dept_class").value;//事业部分类 
//    alert(deptClass);
    if(deptClass != '')
    {
        //if(deptClass == 'PROD' || deptClass == 'HANDSET' || deptClass == 'ZTELW' || deptClass == 'ZTEIN')
		//上海中兴在线填单产品项目必输，事业部分类属于“上海中兴技术”，代码：SHZJ； 钱艳丽 2009-01-22
		if(deptClass == 'PROD' || deptClass == 'HANDSET' || deptClass == 'ZTELW' 
			|| deptClass == 'SHZJ' || deptClass == 'SZLW' || deptClass == 'NJLW' || deptClass == 'ZTEGD')
        {
//            alert(document.all("prod_project_name").value);
            if(document.all("prod_project_name").value == "")
            {
                alert(getSieMessage("SIE_BOE_PROJECT",culture));
                return false;
            }
        }
    }
    if(document.all("prod_project_name")!=null)
    {
        if(document.all("dept_class").value == "PROD" || document.all("dept_class").value == "HANDSET")
        {
            if(document.all("prod_project_name").value == "")
            {
                alert(getSieMessage("SIE_BOE_SET_PROJECT",culture));
                return false;
            }
        }
    }    
    if(document.all("quality_cost_type")!=null)
    {
		//alert(1);
        if(document.all("dept_class").value == "PROD" || document.all("dept_class").value == "HANDSET" 
            || document.all("dept_class").value == "KX")
        {
			//alert(2);
			//alert(document.getElementById("quality_cost_type_name").value);
            if(document.getElementById("quality_cost_type_name").value == "")
            {
                alert(getSieMessage("SIE_BOE_QUANLITY_COST",culture));
                return false;
            }
        }
    }
    //2011-07-12 温春梅 取消 康讯作业中心
//    if(document.all("boe_prod_project_id")!=null)
//    {
//        if(document.all("dept_class").value == "KX")
//        {
//            if(document.all("boe_prod_project_id").value == "" || document.all("boe_prod_project_id").value == "0")
//            {
//                alert(getSieMessage("SIE_BOE_PROD_PROJECT",culture));
//                return false;
//            }
//        }
//    }
    if(document.all("bill_pract_num") != null && document.all("bill_pract_num").value == "")
    {
        var orgID = document.all("org_id").value;    
        if( orgID == ""){orgID = document.all("hiOrgID").value;}
        if( orgID != "54" && orgID != "75" && orgID != "200" )
        {
            if(document.all("submit_type_flag_old").checked == true )
            {
                alert(getSieMessage("SIE_BILL_PRACT_NUM",culture));
                return false;
            }
        }
    }
    // 检查票据提交方式
    if (document.all("submit_type_flag_new").checked == true)
    {
        if (document.all("bill_pract_flag_y").checked == false && document.all("bill_pract_flag_n").checked == false)
        {
            alert(getSieMessage("SIE_BILL_PRACT_FLAG",culture));
            return false;
        }
    }
    if(document.all("deliver_area") != null && document.all("deliver_area").value == "")
    {
        var orgID = document.all("org_id").value;    
        if( orgID == ""){orgID = document.all("hiOrgID").value;}
        if( orgID != "54" && orgID != "75" && orgID != "200" )
        {
            if(document.all("submit_type_flag_new").checked == true && document.all("bill_pract_flag_y").checked == true)
            {
                alert(getSieMessage("SIE_BILL_DELIVER_AREA",culture));
                return false;
            }
        }
    }
    
   //费用分类必选校验。
   if (document.getElementById("hiEmpfeetype") != null && document.getElementById("emp_feetype") != null)
   {
       if (document.getElementById("hiEmpfeetype").value == 'Y' && document.getElementById("emp_feetype").value == '')
       {
          alert(getSieMessage("SIE_BOE_EMP_FEETYPE_NULL",culture));
          document.getElementById("emp_feetype").focus();
          return false;
       } 
   }
    return true;
}

//钱艳丽 2008-11-28
// 查询选择的项目对应职务层次树（项目审批职务树）节点的持有人
function GetPosHoderNames(hiID,hiTxtID)
{
    var ProdProjectID = document.all(hiID).value;
        var result = "";
        var obj = new ActiveXObject("Microsoft.XMLHTTP");
        var sysdate = new Date().valueOf();
        obj.open("GET","../../SysCommon/Pages/SieCommonSrv.aspx?srvtype=GET_POS_HODER_NAMES&prodProjectID="+ProdProjectID,false);
        obj.send();
        //服务器端处理返回的是经过escape编码的字符串.
        result = unescape(obj.responseText);
        var name = document.all(hiTxtID).value ;
		if(result != '')
	     {
          name = name + '('+ result + ')';
	     }
    // 改变则要保存：设置数据改变标志
    if(document.all(hiTxtID)!=null)
    {
        document.all(hiTxtID).value = name;
    }    
}

// 隐藏"核算项目",所在项目为经营部项目时，显示核算项目（by 张俊涛）(2011-12-09 史蕾 修改)
function hiis_manage_dept()
{
//debugger
    var manageDept = document.all("is_manage_dept").value;
    var is_phone_pro = document.all("is_phone_pro").value;    
    var is_display_team=  document.all("is_display_team").value; 
    var manag_team_id  = document.all("manag_team_id"); 
     var prod_project_id =  document.all("prod_project_id"); 
   
   if(is_display_team=="Y")
   { 
            // 如果是手机产品,才判断 是否经营部项目,显示"核算项目"
        if(is_phone_pro=="3698") 
        { 
            // 隐藏"核算项目",所在项目为经营部项目时，显示核算项目
	        if(manageDept == "Y" )
            {
               document.all("tdprod_project_name").colSpan="1";
               document.all("tdcheck_project").style.display="block";
               document.all("tdcheck_project_name").style.display="block";               
	        }
	        else 
	        {
	             document.all("tdprod_project_name").colSpan="3";
                 document.all("tdcheck_project").style.display="none";
                 document.all("tdcheck_project_name").style.display="none";
	        }// end if(manageDept == "Y")
	        manag_team_id.value="";  
            document.all("tdManagTeam").style.display="none";
            document.all("tdteam_name").style.display="none"; 
        }	// end  if(is_phone_pro=="3698")  
        // 如果不是 手机产品 ,则显示"经营团队"
        else if(is_phone_pro.length>0||manag_team_id.value!="")//
        {//(is_phone_pro=="0"||(is_phone_pro!="3698"&&is_phone_pro!="")&&is_phone_pro!="0")
             if(prod_project_id.value!="")
             {
                  document.all("tdprod_project_name").colSpan="1";
                  document.all("tdManagTeam").style.display="block";
                  document.all("tdteam_name").style.display="block"; 
             }
             else
             {
                 document.all("tdprod_project_name").colSpan="3";
                 document.all("tdManagTeam").style.display="none";
                 document.all("tdteam_name").style.display="none";
             }
        document.all("tdcheck_project").style.display="none";
        document.all("tdcheck_project_name").style.display="none"; 
             
        } 
        else
        {
             document.all("tdprod_project_name").colSpan="3";
              document.all("tdManagTeam").style.display="none";
              document.all("tdteam_name").style.display="none";
             document.all("tdcheck_project").style.display="none";
             document.all("tdcheck_project_name").style.display="none"; 
        }   
   }
   else if(is_display_team=="N")
   {
           // 隐藏"核算项目",所在项目为经营部项目时，显示核算项目
	       if(manageDept == "Y" )
           {
               document.all("tdprod_project_name").colSpan="1";
               document.all("tdcheck_project").style.display="block";
               document.all("tdcheck_project_name").style.display="block";               
	       }
	       else 
	       {
	             document.all("tdprod_project_name").colSpan="3";
                 document.all("tdcheck_project").style.display="none";
                 document.all("tdcheck_project_name").style.display="none";
	        }// end if  else
	        manag_team_id.value="";  
            document.all("tdManagTeam").style.display="none";
            document.all("tdteam_name").style.display="none"; 
   } // end else if(is_display_team=="N")
     
}// end hiis_manage_dept

// 史蕾 2011-12-12添加 开始
//选择经营团队
function managerTeamLov(languageValue)
{
      OpenCommonLov('SelManageTeamLov','manag_team_code,check_team_name,manag_team_id', 450,480,'EnabledFlag=Y');
 }// end managerTeamLov
 
 //产品项目 在线填单第一步，按组织属性隔离数据 2010-01-19 钱艳丽
function ProdTree()
{
    var dvhiDeptID=document.getElementById("dept_ID").value;
    var deptClass = document.getElementById("dept_class").value;//事业部分类
    var url = "../../Grb/SecondDist/LovProjects.aspx?"
        + "&menuPath=" + document.getElementById("hiMenuPath").value
        + "&resourceID=" + document.getElementById("hiResourceID").value
        + "&LovKey=SelProjectTree"
  //系统产品、手机产品、中兴力维、中兴印度
  if (deptClass == 'PROD' || deptClass == 'HANDSET' || deptClass == 'ZTELW' || deptClass == 'ZTEIN')
  {
       url = url + "&deptId=" + dvhiDeptID
  }//end if
    url = url + "&part=10&prodName=prod_project_name&prodID=prod_project_id&prodMan=is_manage_dept&PhonePro=is_phone_pro&DisplayTeam=is_display_team&isOnLine=Y";
    //按组织属性隔离数据

    url = url + "&IsInsulateByOrg=Y";
    //var sFeatures = "height=480, width=640, toolbar=yes, menubar=yes, scrollbars=no, resizable=yes, location=yes, status=yes";
    opennewwin(url,640,480);
}//end ProdTree()

//核算项目 在线填单第一步，按组织属性隔离数据 2011-10-10 张俊涛
function CheckTree()
{
    var dvhiDeptID=document.getElementById("dept_ID").value;
    var deptClass = document.getElementById("dept_class").value;//事业部分类
    var url = "../../Grb/SecondDist/LovProjects.aspx?"
        + "&menuPath=" + document.getElementById("hiMenuPath").value
        + "&resourceID=" + document.getElementById("hiResourceID").value
        + "&LovKey=SelProjectTree"
  //系统产品、手机产品、中兴力维、中兴印度
  if (deptClass == 'PROD' || deptClass == 'HANDSET' || deptClass == 'ZTELW' || deptClass == 'ZTEIN')
  {
       url = url + "&deptId=" + dvhiDeptID
  }//end if
    url = url + "&part=10&prodName=check_project_name&prodID=check_project_id&isMan=Y&prodMan=check_manage_dept&isOnLine=Y";
    //按组织属性隔离数据

    url = url + "&IsInsulateByOrg=Y";
    //var sFeatures = "height=480, width=640, toolbar=yes, menubar=yes, scrollbars=no, resizable=yes, location=yes, status=yes";
    opennewwin(url,640,480);
}//end CheckTree()

// 史蕾 2011-12-12添加 结束

function selQualityCostType(codeid,nameid)
{
    SelLov("selQualityCostType",codeid,nameid,480,400);
}

//选择首签领导 by zcj
function selLeaderLov()
{
     // onclick="selLeaderShip('pos_stru_element_id','pos_stru_element_name',1)"
       OpenCommonLov('SieLeaderLov','pos_stru_element_holder,pos_stru_element_id,pos_stru_element_name',
		          450,480,'$AutoSel&isAutoQuery=false,EnabledFlag=Y'  );
 }// end selLeaderLov

// 基础信息页面默认个人/对公报销标志
function default_personalFlag()
{   var yFlag = document.all("personal_flag_yes").checked;
    var nFlag = document.all("personal_flag_no").checked;
    if(yFlag == false && nFlag == false)
    {
        document.all("personal_flag_yes").checked = true;
    }
    if(document.all("hiBackFlag")!=null)
    {
        if(document.all("hiBackFlag").value == "true")
        {
            document.all("boe_type_id").disabled = true;
			document.all("employee_name").disabled = true;
			document.all("img_employee_name").style.display = "none";
        }
    }
    var orgID = document.all("org_id").value;    
    if( orgID == ""){orgID = document.all("hiOrgID").value;}
	//增加隐藏的组织：南京力维软件有限公司/深圳市中兴力维软件有限公司
    if( orgID == "54" || orgID == "75" || orgID == "200" || orgID =="10521" 
	    || orgID == "10522"
	     )
    {
//        document.all("trbda1").style.display="none";
//		document.all("trbda2").style.display="none";
        document.all("trbda1").innerHTML='';
        document.all("trbda2").innerHTML='';
    }
	//因保存公司帐户时，提单界面没有正确显示，而注销一列代码
    //var newFlag = document.all("submit_type_flag_new").checked;
    //var oldFlag = document.all("submit_type_flag_old").checked;
    //if(newFlag == false && nFlag == oldFlag)
    //{
    //    document.all("submit_type_flag_new").checked = true;
    //}
    hide_bp_num_td();
    // 隐藏"核算项目",所在项目为经营部项目时，显示核算项目(如果使手机产品,才判断经营部项目,如果不是直接显示"经营团队")
    hiis_manage_dept();
	//隐藏"质量成本",单位为系统产品，手机产品，康讯时要显示 by zcj
	hide_quality_const();
    // 根据票据提交方式，隐藏票据提交地点
    hide_deliver_area();
    
    //隐藏费用分类。组织定义，费用分类显示 属性为 是，显示，并且必填。
    show_empfeetype();
}

// 控制提单票据是否隐藏 
function hide_bp_num_td()
{
    var oldFlag = document.all("submit_type_flag_old").checked;
    if(oldFlag == true )
    {
		document.all("tdBoeType").colSpan="1";
        document.all("divBpNum0").style.display="block";
        document.all("divBpNum1").style.display="block";
		document.all("tdBpNum0").style.display="block";
		document.all("tdBpNum1").style.display="block";
		document.all("trBpSubmit").style.display="none";
    }
    else
    {
		document.all("tdBoeType").colSpan="3";
        document.all("divBpNum0").style.display="none";
        document.all("divBpNum1").style.display="none";
		document.all("tdBpNum0").style.display="none";
		document.all("tdBpNum1").style.display="none";
		document.all("trBpSubmit").style.display="block";
        document.all("bill_pract_num").value = "";
    }
    if( document.all("hiRecorderChange")!=null )
    {
        document.all("hiRecorderChange").value = "1";
    }
}

// 跟据票据提交方式控制票据提交地点
function hide_deliver_area()
{
    var hasFlag = document.all("bill_pract_flag_y").checked;
    if (!hasFlag)
    {
		document.all("tdBpSubmitType").colSpan="3";
        document.all("trbda1").style.display="none";
        document.all("trbda2").style.display="none";
    }
    else
    {
		document.all("tdBpSubmitType").colSpan="1";
        document.all("trbda1").style.display="block";
        document.all("trbda2").style.display="block";
    }
}
  //隐藏费用分类。组织定义，费用分类显示 属性为 是，显示，并且必填。
  function  show_empfeetype()
  {
     if (document.getElementById("hiEmpfeetype") != null)
     {
       if (document.getElementById("hiEmpfeetype").value == 'Y')
       {
          document.getElementById("trEmpfeetype").style.display="block";
       }
     }
  }