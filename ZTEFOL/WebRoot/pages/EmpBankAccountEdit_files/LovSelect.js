// 为了性能考虑，没有文件头

//弹开一个LOV窗口并完成处理返回值 flag1用来传递的参数
function SelLov(type,valid,txtid,otherid1,otherid2, flag1)
{
	
	var url = GetLovTypeUrl(type, flag1);
	
	if ( url.length < 1)
		{
		  alert("不存在供选择的LOV！");
		  return ;
		}
	 url = url.replace(/&/g,"[$CONN_FLAG$]");
	 var val = OpenModalWin("../../FrameCommon/Pages/PubSelFram.aspx?url=" + url,640,500);
	
	 if(val == null || val == "") return ;
	 var vals = val.split("[$SPLIT_FLAG$]");
	 if(valid!="" && document.getElementById(valid)!=null)
	 {
	   document.getElementById(valid).value = vals[0];		
	 }
	 if(txtid!="" && document.getElementById(txtid)!=null)
	 {
	   document.getElementById(txtid).value = vals[1];
	 }
	 if(otherid1!="" && document.getElementById(otherid1)!=null)
	 {
	   document.getElementById(otherid1).value = vals[2];
	 }
	 if(otherid2!="" && document.getElementById(otherid2)!=null)
	 {
	   document.getElementById(otherid2).value = vals[3];
	 }	
	 try {  //韩荣华于2004增加判断页面数据是否已被修改
	    document.getElementById("hiRecorderChange").value = "1";
	 }
	 catch(er) {}
	
}//end SelLov

function SelLovEnterKey(type,valid,txtid,otherid1,otherid2, flag1)
{
	key = window.event.keyCode; 		
	if(key == 13) {  //判断是否按下回车键
	  var url = GetLovTypeUrl(type,flag1);
	  if ( url.length < 1)  {
		  alert("不存在供选择的LOV！");
		  return ;
	  }
	  url = url + "&autoSel=1&autoValue=" + escape(escape(document.all(txtid).value));// 20100621 林少鹏 处理乱码问题
	  url = url.replace(/&/g,"[$CONN_FLAG$]");
 	  var val = OpenModalWin("../../FrameCommon/Pages/PubSelFram.aspx?url=" + url,640,480);
	
	  if(val == null || val == "") return false;
	  var vals = val.split("[$SPLIT_FLAG$]");
	  if(valid!="" && document.getElementById(valid)!=null)
	  {
	    document.getElementById(valid).value = vals[0];		
	  }
	  if(txtid!="" && document.getElementById(txtid)!=null)
	  {
	    document.getElementById(txtid).value = vals[1];
	  }
	  if(otherid1!="" && document.getElementById(otherid1)!=null)
	  {
	    document.getElementById(otherid1).value = vals[2];
	  }
	  if(otherid2!="" && document.getElementById(otherid2)!=null)
	  {
	    document.getElementById(otherid2).value = vals[3];
	  }	
	  try {  //韩荣华于2004增加判断页面数据是否已被修改
	     document.getElementById("hiRecorderChange").value = "1";
	  }
	  catch(er) {}
	  return false;
	}
	else{
	  return key;
	}
}

//根据LOV类型得到LOV页面访问地址
function GetLovTypeUrl(type,flag)
{
	var url = "";
	switch(type)
	{
		case "ParentOrgName":
			//选总机构
			url = "../../SysCommon/Pages/SelectFimOrg.aspx?type=&orgtype=parent";
			break;
		case "ChildOrgName":
			//选分支机构
			url = "../../SysCommon/Pages/SelectFimOrg.aspx?type=&orgtype=child";
			break;
		case "CompanyName": case "CompanyNameZhs":
			//公司中文名称
			//url = "../../../Fbp/Common/Page/SelectFimCompany.aspx?type=Zhs";
			url = "../../FrameCommon/Pages/SelectLovData.aspx?language=Zhs&type=FimCompany";
			break;
		case "CompanyNameUS":
			//公司英文名称
			//url = "../../../Fbp/Common/Page/SelectFimCompany.aspx?type=US";
			url = "../../FrameCommon/Pages/SelectLovData.aspx?language=US&type=FimCompany";
			break;
		case "CountryZhs":
			//选中文国家
			//url = "../../../Fbp/Common/Page/SelectFimCountry.aspx?type=Zhs";
			url = "../../FrameCommon/Pages/SelectLovData.aspx?language=Zhs&type=FimCountry";
			break;
		case "CountryUS":
			//选项英文国家
			//url = "../../../Fbp/Common/Page/SelectFimCountry.aspx?type=US";
			url = "../../FrameCommon/Pages/SelectLovData.aspx?language=US&type=FimCountry";
			break;
		case "Product":
		    //产品
		    url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=1" + ((flag!=null)?"&checkauth="+flag:"");
			break;
		case "Project":
		    //项目
		    url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=0" + ((flag!=null)?"&checkauth="+flag:"");
			break;
		case "ProProject":
		    //产品项目
		    url = "../../SysCommon/Pages/SelectProdProjects.aspx?type=2"+((flag!=null)?"&checkauth="+flag:"");
			break;
		case "ProdProjectTree":
		    //产品项目树全部能选择,
		    url = "../../SysCommon/Pages/ProdProjectLov.aspx";
			break;	
        case "ProductTree0":
		    //选择所有层次的产品项目
		    url = "../../SysCommon/Pages/ProductTreeLov.aspx?data_type=0&flag1="+flag;
			break;
        case "ProductTree1":
		    //只选择第3层产品项目
		    url = "../../SysCommon/Pages/ProductTreeLov.aspx?data_type=1&flag1="+flag;
			break;
        case "ProductTree2":
		    //只选择第3层以下的产品项目树（启用)
		    url = "../../SysCommon/Pages/ProductTreeLov.aspx?data_type=2&flag1="+flag;
			break;
		case "selBudAccElement":
            // 选取预算科目元素
            url = "../../Comm/Inc/SelectElementTree.aspx?";
            break;  
        case "selDimValueCode":
		    // 选择维度值
			url = "../../../Bud/Dist/Web/PseDimValueLov.aspx?";
			break;	
			
       case "selCfmDeptPse":                                              
            // 选取用户有权限的部门                             
            url = "../../PersonalDist/Pages/DeptLov.aspx?flag1=" + flag;
            break;

       case "selCfmDeptPseQuery":                                              
            // 选取用户有权限的部门                             
            url = "../../../Cfm/PersonalDist/Web/DeptLov.aspx?psCode=CFM_FEE_DIST&includedisable=Y";

            break;   
        case "selEmployeeAll":
            //选择所有者(即员工,包括enableflag='N')
            url = "../../Admin/Approval/SelEmployeeLov.aspx?allFlag=Y";    
            break;
        case "selEmployeeAllWithFilter":
            //选择所有者(即员工,包括enableflag='N')   
            url = "../../Admin/Approval/SelEmployeeLov.aspx?allFlag=Y&empnum=" + document.all(flag).value; 
            break;
        case "selCfmDeptTree":
            // 选取用户有权限的部门[可在职务层次控制权限的页面]
            url = "../../Query/DeptDeeQuery/SelPSELov.aspx?";
            break; 
       case "selDistDeptLevel4":
            //选择资金层次为4的费用分解部门
            url = "../../SysCommon/Pages/FeeDistDeptLov.aspx?fundlevel=4";
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


