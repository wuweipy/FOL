// 弹出一个固定窗体
 function opennewwin(url,w,h)
 {
       var wWidth = w;
       var wHeight = h;
       if(wWidth < 640)
          wWidth = 640;
       if(wHeight < 480)
          wHeight = 480;
       var wTop = (window.screen.height - wHeight)/2;
       var wLeft = (window.screen.width - wWidth)/2;
       var winTemp = window.open(url,"Lov","top="+wTop+",left="+wLeft+",height="+wHeight+",width="+wWidth+",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
       winTemp.focus();
 }
 // 弹出一个固定窗体(无大小限制)
 function opennewwinNolimit(url,w,h)
 {
       var wWidth = w;
       var wHeight = h;
       var wTop = (window.screen.height - wHeight)/2;
       var wLeft = (window.screen.width - wWidth)/2;
       var winTemp = window.open(url,"Lov","top="+wTop+",left="+wLeft+",height="+wHeight+",width="+wWidth+",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
       winTemp.focus();
 }
 /* 打开通用LOV:lovType -- LOV类型键值,ctrlNames -- 待赋值的控件名称串
              w -- 宽度,h -- 高度,params -- 其他参数
 */
 function OpenCommonLov(lovType,ctrlNames,w,h,params,resourceID)
 {
       var url = "../../WebControl/Lov/CommLovPage.aspx?ControlNames="
				+ctrlNames+"&LovType="+lovType+'&resourceID='+resourceID
			    +"&LovKey="+lovType;
       // 存在参数
       if( params != null )
       {
		   	var prefix = "";
			   // 查看第一个字符
			if( params.substring(0,1) != "&" )
			 {
				prefix = "&";
			 }
			// 如果是指定的特殊值
			if (params.indexOf('$AutoSel') >=0)
			{
				//alert()
			    var ids = ctrlNames.split(",");
				// 至少需要有两个控件id
				if(ids.length < 2) 
				{
					return false; 
				}
				var values = '';
				for (var i =0; i< ids.length; i++)
				{
					if(document.getElementById(ids[i]).value !='')
					{
					   values += document.getElementById(ids[i]).value + ';'
					}
				}
				// 去掉最后的分号
				values = values.substring(0,values.length-1)
				if(url.indexOf('resourceID=1483') >=0)
				{
				    params += "="+escape(values);
				}
				else
				{
				   params += "="+values;
				}
			}
           url += prefix + params;
       }
       opennewwin(url,w,h);
 }
 
 // 在输入框回车自动完成的LOV
function CommLovEnterKey(lovType,ctrlNames,w,h,params)
{
  // ctrlNames:返回值对象ID组合
  // 第一个和第二个固定为ID和文本  
  var key = window.event.keyCode;
  if(key == 13) 
  { 
       var ids = ctrlNames.split(",");
       if(ids.length < 2) 
          return false;    
       var url = "../../WebControl/Lov/CommLovPage.aspx?ControlNames="+ctrlNames+"&LovType="+lovType;
       url = url+"&autoSel=1"+"&autoValue="+document.getElementById(event.srcElement.id).value;    
       // 存在参数
       if( params != null )
       {
			// 如果是指定的特殊值
			if (params =='$AutoSel')
			{
			    var ids = ctrlNames.split(",");
				// 至少需要有两个控件id
				if(ids.length < 2) 
					return false;  
				var values = '';
				for (var i =0; i< ids.length; i++)
				{
					values += ids[i] + ';'
				}
				// 去掉最后的分号
				values = values.substring(0,values.length-1)
				url += "&$AutoSel=" + values;
			}
			else
			{
			   var prefix = "";
			   // 查看第一个字符
			   if( params.substring(0,1) != "&" )
			   {
				   prefix = "&";
			   }
			   url += prefix + params;
		   }
       }
       opennewwin(url,w,h);    
       return false;   
  }
  else
  {
    return key;
  }
}// end LovSelEnterKey
