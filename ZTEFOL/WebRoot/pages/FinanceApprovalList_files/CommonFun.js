//返回请输入的Javascript多语言提示信息
function GetPlsInputMessage(nam,culture) {
  var mess;
  if (culture=="zh-CN")
    mess = "请输入" + nam + "!" 
  else
    mess = "Please input the " + nam + " value!" 
  
  return mess;
}

function getSelectedValue(selectId)
{
    if (document.all(selectId) == null) 
   {
        alert("控件"+selectId+"不存在");
        return null; 
   } 

    for(var i=0; i<document.all(selectId).options.length;i++)
   {
        if (document.all(selectId).options[i].selected)
       return  document.all(selectId).options[i].value;
   } 
}
// 表单验证
function CheckMustInput(culture, controlIds)
{
    // if(arguments[0].constructor == Number) 
    if(arguments[1] != "undefined" && arguments[1] != null) 
    {
        return CheckMustInput2(culture, controlIds);
    }
    var obj;
    // 验证
    for ( i = 0; i < document.all.length; ++i )
    {
        obj = document.all.item(i);
        if (obj.mustInput!=null && obj.mustInput)
        {
            if (!AlertMustInputMessage(obj, false, culture))
            {
                return false;
            }
        }
    }
    return true;
}

// 如果检查对象的value为空，则弹出提示信息
// obj            检查对象
// isCheckControl 是否检查受控控件
function AlertMustInputMessage(obj, isCheckControl, culture)
{
    // 如果没有 objName, value ，不检查，不弹出消息
    if (obj.objName == null || obj.objName == "undefined" || obj.value == null || obj.value == "undefined" || Trim(obj.value) != "")
    {
        return true;
    }
    // 判断是否有受控控件
    if (isCheckControl && obj.controlObj != null && obj.controlObj != "undefined" && obj.controlObj != "")
    {
        var conObj = document.getElementById(obj.controlObj); // 影响检查的控件
        var isCheck = true; // 默认为需要检查非空
        // 判断是否检查非空的逻辑，可以根据 影响检查的控件属性 来灵活设置
        switch (conObj.tagName)
        {
            case "TR":
            case "SPAN":
            case "TD":
            case "DIV":
            case "TABLE":// 包容类 的控件，一般是隐藏则不检查
                if (conObj.style.display.toLowerCase() == "none")
                {
                    isCheck = false;
                }
                break;
            case "INPUT": // 输入的控件，根据类型不同来判断
                if (conObj.type == "radio" && !conObj.checked)  // 如果是单旋钮，并且没有选择，则不检查
                {
                    isCheck = false;
                }
                break;
        }
        
        if (!isCheck)
        {
            return true; // 如果不需要检查，返回true
        }
    }
    
    switch (obj.tagName)
    {
        case "INPUT":
        case "TEXTAREA":
            alert(GetPlsInputMessage(obj.objName, culture));
            break;
        case "SELECT":
            alert(GetPlsChoiceMessage(obj.objName, culture));
            break;
        default:
            alert(GetPlsInputMessage(obj.objName, culture));
            break;
    }
    obj.focus();
    return false;
}

// 概述：根据页面控件ID，循环检查控件的值是否为空(重载方法)
// 参数：culture    多语言
//       controlIds 控件ID的连接串。
//                  针对同一种检查的控件Id以逗号间隔；针对不同检查的控件Id以分号间隔。
//       举例：txtContractNumber,hiContractNumber;txtCurrency;txtFromMoney
//             txtContractNumber,hiContractNumber 一起都是对“合同编号”检查，但第一个为focus控件Id
//             txtCurrency  只针对“币种”检查
//             txtFromMoney 只针对“原币金额”检查
// 返回：true/false
function CheckMustInput2(culture, controlIds)
{
    var itemArr = controlIds.split(";"); //页面上所有检查非空的Item集合(不是控件集合，一个Item可能会检查多个控件是否非空)
    var controls;
    var focusContol = null; // 记录提示错误信息，聚焦的控件
    var itemControl; // 每一个检查非空的控件
    for (var itemIndex = 0; itemIndex < itemArr.length; itemIndex++)
    {
        // 针对一个检查项，只检查一个控件，如举例中的txtCurrency、txtFromMoney
        if (itemArr[itemIndex].indexOf(",") < 0)
        {
            itemControl = document.getElementById(itemArr[itemIndex]);
            if(Trim(itemControl.value) == "")
            {
                focusContol = itemControl;
            }
        }
        // 针对一项，要检查多个控件，如举例中的txtContractNumber,hiContractNumber
        else
        {
            controls = itemArr[itemIndex].split(",");
            for (var subIndex = 0; subIndex < controls.length; subIndex++)
            {
                itemControl = document.getElementById(controls[subIndex]);
                if(Trim(itemControl.value) == "")
                {
                    // 错误信息，从第一个控件的 emptymsg属性上获取
                    focusContol = document.getElementById(controls[0]); // 默认第一个控件是要聚焦
                    break;
                }
            } // end for
        } // end if (itemArr[itemIndex].indexOf(",") < 0)
    
        if (focusContol != null && !AlertMustInputMessage(focusContol, true, culture))
        {
            return false;
        }
        
    } // end for
    
    return true;
} // CheckMustInput

//返回请选择的Javascript多语言提示信息
function GetPlsChoiceMessage(nam,culture) {
  var mess;
  if (culture=="zh-CN")
    mess = "请选择" + nam + "!" 
  else
    mess = "Please choice the " + nam + " list!" 
  
  return mess;
}

// 控制只能录入整数
function OnlyInputNumber(obj)
{
    if(obj.readOnly) return false;
    else
    {
        reg=/^\d*\d{0,0}$/
        inputStr=String.fromCharCode(event.keyCode)
        var docSel    = document.selection.createRange()
        if (docSel.parentElement().tagName != "INPUT")    return false
        oSel = docSel.duplicate()
        oSel.text = ""
        var srcRange    = obj.createTextRange()
        oSel.setEndPoint("StartToStart", srcRange)
        var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length)
        return reg.test(str)
    }
}

// 控制只能录入数值
function OnlyInputDouble(obj)
{
    if(obj.readOnly) return false;
    else
    {
        reg=/^-?\d*\.?\d{0,4}$/
        inputStr=String.fromCharCode(event.keyCode)
        var docSel    = document.selection.createRange()
        if (docSel.parentElement().tagName != "INPUT")    return false
        oSel = docSel.duplicate()
        oSel.text = ""
        var srcRange    = obj.createTextRange()
        oSel.setEndPoint("StartToStart", srcRange)
        var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length)
        return reg.test(str)
    }
}

//弹出一个固定窗体
function opennewwin(url,w,h)
{
    var wWidth = w;
    var wHeight = h;
    //add by pqq 20050802
    if(wWidth<640)
        wWidth=640;
    if(wHeight<480)
        wHeight=480;
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
//LOV选择
function selCustom(type,idname,txtname)
{
    if(type == "CountryListForm")
    {//融资选择国家,取融资前期录入的国家
        alert("LOV选择国家！");        
    }
}

//弹出一个模态窗口
//[王永恒·2005-07-20·修改代码，使弹出窗口居中]
function OpenModalWin(url,w,h)
{
    if (w==null || w==""){ w = 640; }
    if (h==null || h==""){ h = 480; }
    //add by pqq 20050802
    if(w<640) w=640;
    if(h<480) h=480;
    var left = window.screen.availWidth > w ? (window.screen.availWidth - w)/2 : 0;
    var top = window.screen.availHeight > h ? (window.screen.availHeight - h)/2 : 0;
    //alert(wLeft+", "+wTop);
    var sFeatures = "dialogHeight: " + h + "px; dialogWidth:" + w + "px; dialogTop: " + top + "px; dialogLeft: " + left + "px;edge: Raised; center: Yes; help: No; resizable: No; status: No;";
    return window.showModalDialog(url, "LOV", sFeatures);
}


//弹出一个非模态窗口
function OpenModelessWin(url,w,h)
{
    var wWidth = (window.screen.width<w)?window.screen.width:w;
    var wHeight = (window.screen.height<h)?window.screen.height:h;
    var wTop = (window.screen.height - wHeight)/2;
    var wLeft = (window.screen.width - wWidth)/2;
    //add by pqq 20050802
    if(wWidth<640)
        wWidth=640;
    if(wHeight<480)
        wHeight=480;
    
    var sFeatures = "dialogHeight: "+wHeight+"px; dialogWidth:"+wWidth+"px; dialogTop: "+wTop+"px; dialogLeft: "+wLeft+"px;edge: Raised; center: Yes; help: No; resizable: No; status: No;";
    return window.showModelessDialog(url, "NewWindow", sFeatures);
}

//打开LOV窗口
//[王永恒·2005-07-20·修改代码，使弹出窗口居中]
function OpenLov(type,controlNames,width,culture,otherParam)
{ 
    //参数1: type表示LOV类型;
    //参数2:controlNames表示取LOV值的所有控件,以逗号分隔,按顺序返回,
          //select语句中第一列返回到controlName1中,第2列返回到controlName2中,以此类推;
          //因此controlName的数量不能超过select语句的列数,
          //规定select语句的第一列为隐藏列,当只需取1个值时,请重复写2列,
          //如"select col1,col1 from table", "OpenLov(type,'controlName',width)"
    //参数3:width表示打开窗口的宽度;
    //参数4:culture表示语言种类
    //参数5: otherParam表示其它参数,设计者可根据自己的需要来使用这个参数,
            //(如,用此参数来构造复杂的sql语句),此参数可用可不用    
    
    if (width==null) width = 640;
    var url= "../../FrameCommon/Pages/lov.aspx?type=" + type ;                
    url = url + "&controlNames=" + controlNames + "&culture=" + culture
			+"&LovKey=" + type;    
    if (otherParam != null)
    {
        url = url + "&otherParam=" + otherParam;
    }         

    //alert(screen.availWidth + "|" + screen.availHeight);
     //add by pqq 20050802
     if(width<640)
        width=640;
    var height = 480;
    var left = screen.availWidth > width ? (screen.availWidth - width)/2 : 0;
    var top  = screen.availHeight > height ? (screen.availHeight - height)/2 : 0;
    window.open(url,"","left=" + left + ",top=" + top + ",height=" + height + ",width=" + width + ",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");
}

//回车激发打开LOV窗口
function OpenEnterKeyLov(type,controlNames,width,culture,otherParam,val)
{ 
    
    //参数1: type表示LOV类型;
    //参数2:controlNames表示取LOV值的所有控件,以逗号分隔,按顺序返回,
          //select语句中第一列返回到controlName1中,第2列返回到controlName2中,以此类推;
          //因此controlName的数量不能超过select语句的列数,
          //规定select语句的第一列为隐藏列,当只需取1个值时,请重复写2列,
          //如"select col1,col1 from table", "OpenLov(type,'controlName',width)"
    //参数3:width表示打开窗口的宽度;
    //参数4:culture表示语言种类
    //参数5: otherParam表示其它参数,设计者可根据自己的需要来使用这个参数,
            //(如,用此参数来构造复杂的sql语句),此参数可用可不用    
    //参数6:val表示当前值
    
    key = window.event.keyCode;         
    if(key == 13) {  //判断是否按下回车键
      if (width==null) width = 270;
      var url= "../../FrameCommon/Pages/lov.aspx?autoSel=1&type=" + type ;
      url = url + "&autoValue=" + val;
      url = url + "&controlNames=" + controlNames + "&culture=" + culture
			+ "&LovKey=" + type;
      if (otherParam != null)
      {
       url = url + "&otherParam=" + otherParam;
      }
      //add by pqq 20050802
     if(width<640)
        width=640;
    
      window.open(url,"","left=200,top=100,height=480,width=" + width + ",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");      
      return false;   
    }
    else{
      return key;
    }    
}

function OpenEnterKeyLovEx(type,controlNames,width,culture,otherParam,val,postLovFlag)
{ 
    //参数1: type表示LOV类型;
    //参数2:controlNames表示取LOV值的所有控件,以逗号分隔,按顺序返回,
          //select语句中第一列返回到controlName1中,第2列返回到controlName2中,以此类推;
          //因此controlName的数量不能超过select语句的列数,
          //规定select语句的第一列为隐藏列,当只需取1个值时,请重复写2列,
          //如"select col1,col1 from table", "OpenLov(type,'controlName',width)"
    //参数3:width表示打开窗口的宽度;
    //参数4:culture表示语言种类
    //参数5: otherParam表示其它参数,设计者可根据自己的需要来使用这个参数,
            //(如,用此参数来构造复杂的sql语句),此参数可用可不用    
    //参数6:val表示当前值
    
    key = window.event.keyCode;         
    if(key == 13) {  //判断是否按下回车键
        
      if (width==null) width = 640;
      var url= "../../FrameCommon/Pages/lov.aspx?autoSel=1&type=" + type ;
      url = url + "&autoValue=" + val;
      url = url + "&controlNames=" + controlNames + "&culture=" + culture;    
      if (otherParam != null)
      {
       url = url + "&otherParam=" + otherParam;
      }
      url = url + "&PostLovFlag=" + postLovFlag;
       //add by pqq 20050802
     if(width<640)
        width=640;
      window.open(url,"","left=200,top=100,height=480,width=" + width + ",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");      
      return false;   
    }
    else{
      return key;
    }    
}

//格式化数据，把数据格式化为逗号分隔
//例：把1234567.123格式化为1,234,567.123
function formartMoney(val)
{
    if(isNaN(parseFloat(val)))
    {
    return val;
    }
    var temp = val.toString();
    var intVal="";
    var decmVal = "";
    if(temp.indexOf(".")!=-1)
    {
    decmVal = temp.substring(temp.indexOf("."));//小数部分
    temp = temp.substring(0,temp.indexOf("."));//整数部分    
    }
    //格式化整数部分
    var i = temp.length;
    while(i>0)
    {
        i=i-3;
        if(intVal=="")
            intVal=temp.substring(i,i+3);
        else
        {
            if(temp.substring(i,i+3)=="-" || temp.substring(i,i+3)=="+" || temp.substring(i,i+3)==".")
                intVal=temp.substring(i,i+3) + intVal;
            else
                intVal=temp.substring(i,i+3) + "," + intVal;
        }
    }//end while
    return intVal+decmVal;
}

// 清除页面，将所有文本框值设为"",将隐藏域值设为-1
// 如果使用此方法，则会查找页面中数组变量noClearCtls，此数组存放不清除的控件ID
// 在页面中定义：noClearCtls = ("id1","id2","id3");
var noClearCtls;
function clearPage()
{
    var exist = false;
    //清除文本框
    for ( i = 0; i < document.all.tags("input").length; ++i )
    {
        var obj = document.all.tags("input")[i];    
        if (obj.id.indexOf("DataPageControl") != -1 )
        {
            break;
        }        
        if (obj.tagName == "INPUT" && obj.type == "text" && obj.name.indexOf("__") == -1 && obj.CanBeClear != "FALSE" )
        {
            if(noClearCtls != null && noClearCtls.length > 0){
                exist = false;
                for(j=0;j<noClearCtls.length;j++){
                  if(noClearCtls[j] == obj.id){
                      exist = true;
                      break;
                  }
                }
                if(exist == false)
                    obj.value = "";
            }
			else{
                obj.value = "";
            }
        }
        else if (obj.tagName == "INPUT" && obj.type == "hidden" && obj.name.indexOf("__") == -1 && obj.CanBeClear != "FALSE" )
        {
            if(noClearCtls != null && noClearCtls.length > 0){
                exist = false;
                for(j=0;j<noClearCtls.length;j++){
                  if(noClearCtls[j] == obj.id){
                      exist = true;
                      break;
                  }
                }
                if(exist == false)
                    obj.value = "";
			}
            else{
                obj.value = "";
            }
        }
    }
    
    // 将下拉列表设为选中第一项
    for ( i = 0; i < document.all.tags("select").length; ++i )
    {
        var obj = document.all.tags("select")[i];    
        if (obj.CanBeClear != "FALSE" )
        {
            obj.options[0].selected = true;
        }
    }
    return false;
}

//清除页面中的值
function clearAllPage()
{
    //清除文本框
    for ( i = 0; i < document.all.tags("input").length; ++i )
    {
        var obj = document.all.tags("input")[i];    
        if (obj.id.indexOf("DataPageControl") != -1 )
        {
            break;
        }        
        if (obj.tagName == "INPUT" && obj.type == "text" && obj.name.indexOf("__") == -1 && obj.CanBeClear != "FALSE" )
        {
            obj.value = "";
        }
        else if (obj.tagName == "INPUT" && obj.type == "hidden" && obj.name.indexOf("__") == -1 && obj.CanBeClear != "FALSE" )
        {
            obj.value = "0";
        }
    }
    
    // 将下拉列表设为选中第一项
    for ( i = 0; i < document.all.tags("select").length; ++i )
    {
        var obj = document.all.tags("select")[i];    
        if (obj.CanBeClear != "FALSE" )
        {
            obj.options[0].selected = true;
        }
    }
    return false;
}

function cloneValue(idSrc, idDest)
{
    var objSrc = document.getElementById(idSrc);
    var objDest = document.getElementById(idDest);
    
    if (objSrc != null && objDest != null)
    {
        objDest.value = objSrc.value;
    }
}
  
function uploadExcel(fileName,funName,url,param, culture) {
//Excel导入函数
//数据必须放在第一个工作表，且第一行为中文字段名称。
//参数定义：
//fileName:导入的Excel文件名
//funName:标示不同的导入功能，在Zte.Fbp.BasicData.Business.BExcelToDB.cs中定义。
//url:fmsWeb/fbp/basicData/Web/ExcelToDB.aspx 的相对路径
//param:从界面带过来的参数

    var DataToSend ="" ;
    var splitParm    = "\u00ff";      //参数分隔字串
    var splitCol    = "\u00fe";      //列间隔符
    var splitRow    = "\u00fd";      //行间隔符
    var splitSheet    = "\u00fc";      //工作表间隔符
    var myApp ;//excel对象
    var x1Book;
    var Cols;//列数
    var sheets;//工作表数
    var endFlag;//excel结束标志
    var curRow; //当前行
    var strLine;//行字符串
    var str; //整个字符串
    var strsheet; //工作表字符串
    
    var extendName = "";
    if(fileName.length > 3)    
      extendName = fileName.substring(fileName.length-3).toUpperCase();
    if(extendName == ""){
      if(culture=="zh-CN"){
          alert( "请选择文件。");
      }
      else{
          alert( "pls choice the file。");
      }
      return;
    }
    if(extendName!="XLS"){
      if (culture=="zh-CN"){
        alert( "只能导入Excel文件。");
      }
      else{
        alert( "Please check file format !");
      }
      return;
    } // end if  
    
    try
    {   
        myApp = new ActiveXObject("Excel.Application");
        
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }
        //取工作表数
        sheets = 1;
        while (sheets <= myApp.worksheets.Count)
        {
          if (myApp.worksheets(sheets).Cells(1,1).Value != null ) 
          {
            sheets++;
          }
          else
          break;
        }       

        sheets = sheets - 1;
        str = "";
        for(var isheet = 1; isheet <= sheets; isheet ++)
        {
            //取列数
            Cols = 0;
            while(myApp.worksheets(isheet).Cells(1,Cols+1).Value != null ) 
            {
              Cols++;
            }
            
            endFlag = 0;
            curRow =1 ;
            strsheet = "";
            while( endFlag == 0 ) 
            {
            
                endFlag = 1;
                strLine = "";
                for(var i = 0; i <= Cols-1; i ++)
                {
                  
                  if (myApp.worksheets(isheet).Cells(curRow,i+1).Value != null ) 
                  {
                  
                    strLine = strLine + myApp.worksheets(isheet).Cells(curRow,i+1).Value;
                    endFlag = 0;
                    
                  }
                  if (i != Cols.length-1) 
                  {
                  
                    strLine = strLine + splitCol;
                  }
                }
                if (endFlag == 0) //
                {
                  strsheet = strsheet + strLine + splitRow;
                }
                
              curRow++;
            }
            if (isheet == 1)
            {
              str = str + strsheet;
            }
            else
            {
              str = str + splitSheet + strsheet;
            }
        }
    
    }//end try
    catch(e) 
    {
      if (culture=="zh-CN"){
        alert( "请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      }
      else{
        alert( "Please check browser set !");
      }
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally
    // 查看导入数据是否合法
    if(str== "")
    {
       if (culture=="zh-CN"){
         alert( "导入的内容有误，请检查!");
       } 
       else{
         alert( "Please check the content of import file !");
       }
       return "";
    }

    //DataToSend = "funName=" + funName + "&value=" + str + "&param=" + param;
    DataToSend = funName + splitParm + str + splitParm +  param;
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    //xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);
    //alert(xmlhttp.responseXML.xml);
    var errortxt = xmlhttp.responseText;
    if (errortxt == "")
    {      
      if (culture=="zh-CN"){
        alert("导入成功！");
      }
      else{
        alert("Import succeed !");
      }
    }
    else
    {
      alert(errortxt);
    }
    
}

function uploadBoe(fileName,funName,url,param,culture) {
// 离线填单函数
// Excel模版数据提取,提交
// 1.数据必须放在第二个工作表，且第一行为中文字段名称。
//   第一行：系统数据
//     第一格：单据模版文件编号
//     第二格：数据起始行号X
//   第二行：各种数据存放顺序
//     第1-n格存放，取值暂定为表格名称，参考"所有单据信息表及字段"
//   第三行：各种数据占用的行数与第二行匹配
//   第四至X-1行保留
//   第X行：数据定义行
//     第1-n格：数据标识，取值暂定为字段名称（参考"所有单据信息表及字段"），
//              数据标识为空截止
//   第X-X+m行（假定C[3,1]=m,即第一项数据有m行）
//     第1-n格：数据值，与第X行匹配

//参数定义：
//fileName:导入的Excel文件名
//funName:标示不同的导入功能，在Zte.Fbp.BasicData.Business.BExcelToDB.cs中定义。
//url:fmsWeb/fbp/basicData/Web/ExcelToDB.aspx 的相对路径
//param:从界面带过来的参数
//culture:语言


    var splitStr    = "\u00ff";      //分隔字串
    
    var sheetNum    = 3;        //第2张sheet为目标数据sheet
    var sysRow      = 1;        //第1行存放系统数据
    var catHeadRow  = 2;        //第2行存放数据目录
    var sysDataCol  = 2;        //第1行第2,4,6列存放单据类型,版本,语言
    var dataRowCol  = 12;        //第1行第8列存放模版文件数据起始行号

    var catCols;        //目录数据的列数
    var dataCols;       //数据列数
    var dataRow;        //数据的起始行号
    var rowCount;       //数据行数
    
    var myApp ;         //excel对象
    var x1Book;

    
    var DataToSend = "";
    var curRow;         //当前行
    var str;            //整个字符串
    var strSection;     //分段字符串
    
    
    try
    {
        myApp = new ActiveXObject("Excel.Application");
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }
        
        if (myApp.worksheets.Count < sheetNum)
        {
           alert("模版有误, 找不到相关数据");
           return;
        }

        // 总字符串长度+模版单据类型,版本,语言+目录项数+目录列表+目录参数（数据行数）列表+
        // 字段列表1（字段数）+字段列表1（名称）+ 字段列表1（字段值1列表）+ 字段列表1（字段值2列表）+字段数2+....
        

        //0.生成单据类型,版本,语言
        str = myApp.worksheets(sheetNum).Cells(sysRow,sysDataCol).Value;
        str += splitStr + myApp.worksheets(sheetNum).Cells(sysRow,sysDataCol + 2 ).Value;
        str += splitStr + myApp.worksheets(sheetNum).Cells(sysRow,sysDataCol + 4 ).Value;

        //田建强 2005-1-27
        
        str += splitStr + "115543";
        str += splitStr + "p_w";
        str += splitStr + "";

        
        // 1.生成目录的列数,及目录项列表
        // 初始化列数为零
        strSection = "";
        dataCols = 0;
        while (myApp.worksheets(sheetNum).Cells(catHeadRow,dataCols+1).Value != null) 
        {
            dataCols++;
            //生成字段列表
            strSection += splitStr + myApp.worksheets(sheetNum).Cells(catHeadRow,dataCols).Value;
        }
        for (var i = 1; i <= dataCols; i++)
            strSection += splitStr + myApp.worksheets(sheetNum).Cells(catHeadRow+1,i).Value;
        //在字段列表前增加字段数
        str += splitStr + dataCols + strSection;


        //2.按目录顺序提取数据，从1开始
        //获取数据起始行号
        dataRow = myApp.worksheets(sheetNum).Cells(sysRow,dataRowCol).Value;

        catCols = 1;
        while (myApp.worksheets(sheetNum).Cells(catHeadRow,catCols).Value != null)
        {
            //获取数据行数
            rowCount = myApp.worksheets(sheetNum).Cells(catHeadRow+1,catCols).Value;

            // 获取数据的列数,及数据头（字段列表）
            // 初始化列数为零
            strSection = "";
            dataCols = 0;
            while (myApp.worksheets(sheetNum).Cells(dataRow,dataCols+1).Value != null) 
            {
                dataCols++;
                //生成字段列表
                strSection += splitStr + myApp.worksheets(sheetNum).Cells(dataRow,dataCols).Value;
            }
            //在字段列表前增加字段数
            str += splitStr + dataCols + strSection;
            
            //逐行生成字段值列表
            strSection = "";
            for (var i = 1; i <= rowCount; i++)
            {
                //
                for (var j = 1; j <= dataCols; j++)
                {
                    if (myApp.worksheets(sheetNum).Cells(dataRow + i,j).Value == null)
                        strSection  += splitStr;
                    else    
                        strSection  += splitStr + myApp.worksheets(sheetNum).Cells(dataRow + i,j).Value;
                }
            }
            str += strSection;
            
            //转移到下一目录项
            catCols++;
            dataRow += rowCount + 1;
            //alert(str);
        }
        
        //3.设置总长度
        str = str.length + splitStr + str;
        
        //alert(str);
        //return "";
        
    }//end try
    catch(e) 
    {
      alert( "1请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally

    DataToSend = str;   //"funName=" + funName + "&value=" + str + "&param=" + param;
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);
    //alert(xmlhttp.responseXML.xml);
    var errortxt = xmlhttp.responseText;
    if (errortxt.substring(0,1) == "Y")
    {
      alert("提单成功!单据号:" + errortxt.substring(1));
    }
    else
    {
      alert(errortxt.substring(1));
      
    };
}

/***********************************************************************/
/*
 ===============================================================================
 版权所有：版权所有(C) 2005，中兴通讯
 创建日期：2005-06-10 14:00
 作    者：王永恒
 功能描述：完成合并报表的定义
 -------------------------------------------------------------------------------
 修改记录：1
 修改日期：2005-06-10
 修改人员：
 修改摘要：
 ===============================================================================
*/
function currentHeight(obj)
{
    var yy = currentTop(obj);
    //alert("body.clientHeight = " + document.body.clientHeight);
    //alert("fGetXY = " + yy);
    
    //yy 为主调控件当前文档的纵坐标
    // 3 为body 的单边margin
    return document.body.clientHeight - yy - 5;
}
function currentTop(obj)
{
    var Ypx = 0;
    do
    {
      Ypx += obj.offsetTop;
      obj = obj.offsetParent;
    } while(obj.tagName!="BODY");
    return Ypx;
}
function currentHeightLess(obj)
{
    var yy = currentTop(obj);
    //alert("body.clientHeight = " + document.body.clientHeight);
    //alert("fGetXY = " + yy);
    
    //yy 为主调控件当前文档的纵坐标
    // 3 为body 的单边margin
    return document.body.clientHeight - yy - 36;
}
function currentHeightMinus3Bar(obj) // #region 
{
    var yy = currentTop(obj);
    var yb = 0;
    var yb = parseInt(document.getElementById("hiBottomY").value);
    return document.body.clientHeight - yy - yb;
} // #endregion 

function currentHeightMinus3BarMid(obj) // #region 
{
    var yy = currentTop(obj);
    var yb = 0;
    var rtn = 0;
    var yb = parseInt(document.getElementById("hiBottomY").value);
    if (document.getElementById("hiMiddleY").value == "0")
    {
        rtn = document.body.clientHeight - yy - yb;
        rtn =rtn/2;
    } // if
    else
    {
        yb +=parseInt(document.getElementById("hiMiddleY").value);
        rtn = document.body.clientHeight - yy - yb;
    }
    return rtn;
} // #endregion 
 // 获取指定字符的长度（字节数）
function getStrLength(str)
{
   var l=str.length;
   var n=l;
   for (var i=0;i<l;i++)
   {
      if ( str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255 )
      { n++;}
   }
   return n ;
}
// 判断指定字符长度是否大于maxvalue(字节)值
function lengthMoreMaxBytes(str,maxvalue)
{
   var len = getStrLength(str);
   if(len > maxvalue)
   {
      return true;
   }
  return false;
}

function uploadBudImport(fileName,funName,url,param,culture) {
// 预算导入函数
// Excel模版数据提取,提交
// 1.数据必须放在第-个工作表，且第一行为字段名称。
//   第一行：系统数据
//     包括固定列：元素Element_code（或假设值Param_code）、归属节点Belong、年期间Period、金额Amount
//     其它列为维度代码列（不固定）
//   第二行开始：各种数据存放区，其顺序根据列头确定 
//  
//参数定义：
//fileName:导入的Excel文件名
//funName:标示不同的导入功能，在Zte.Fbp.BasicData.Business.BExcelToDB.cs中定义。
//url:fmsWeb/bud/up/Import/UpImportExcel.aspx 的相对路径
//param:从界面带过来的参数,这里固定为申报的节点ID
//culture:语言
    var DataToSend ="" ;
    var splitParm    = "\u00ff";      //参数分隔字串
    var splitCol    = "\u00fe";      //列间隔符
    var splitRow    = "\u00fd";      //行间隔符
    var splitSheet    = "\u00fc";      //工作表间隔符
    var myApp ;//excel对象
    var x1Book;
    var Cols;//列数
    var sheets;//工作表数
    var endFlag;//excel结束标志
    var curRow; //当前行
    var strLine;//行字符串
    var str; //整个字符串
    var strsheet; //工作表字符串
    var sTemplateCode = ""; // 模板代码
    
    try
    {
        if (fileName == "")
        {
            if (culture=="zh-CN")
            {
                alert( "请选择文件！");
            }
            else
            {
                alert( "Please check file!");
            }
            return false;
        }

        myApp = new ActiveXObject("Excel.Application");
        
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }
        //取工作表数
        sheets = 1;
        str = "";
        for(var isheet = 1; isheet <= sheets; isheet ++)
        {
            //取列数
            Cols = 0;
            while(myApp.worksheets(isheet).Cells(1,Cols+1).Value != null ) 
            {
              Cols++;
            }
            
            endFlag = 0;
            curRow =1 ;
            strsheet = "";
            sTemplateCode = myApp.worksheets(isheet).Name;
            // 获取模板是否已导入数据的标志:若已导入数据则提示是否重新导入
            DataToSend = funName+"_IMPORT_FLAG" + splitParm + param + splitParm + sTemplateCode ;
            var xmlhttp1 = new ActiveXObject("Microsoft.XMLHTTP");
            xmlhttp1.Open("POST",url,false);
            xmlhttp1.setrequestheader("content-length",DataToSend.length);
            xmlhttp1.setRequestHeader("Content-Type", "application/zip");
            xmlhttp1.send(DataToSend);
   
            var impFlag  = xmlhttp1.responseText;
            if( impFlag == "Y" )
            {
               var tmpInfo = (culture == "zh-CN")?"该模版的数据已经存在，建议先检查该模版数据。是否重新导入?":"The template data has been exists.Are you confirm to import?";
               if(!confirm(tmpInfo))
               {
                  return "";
               }
            }
            
            while( endFlag == 0 ) 
            {
            
                endFlag = 1;
                strLine = "";
                for(var i = 0; i <= Cols-1; i ++)
                {
                  
                  if (myApp.worksheets(isheet).Cells(curRow,i+1).Value != null ) 
                  {
                  
                    strLine = strLine + myApp.worksheets(isheet).Cells(curRow,i+1).Value;
                    endFlag = 0;
                    
                  }
                  if (i != Cols.length-1) 
                  {
                  
                    strLine = strLine + splitCol;
                  }
                }
                if (endFlag == 0) //
                {
                  strsheet = strsheet + strLine + splitRow;
                }
                
              curRow++;
            }
            if (isheet == 1)
            {
              str = str + strsheet;
            }
            else
            {
              str = str + splitSheet + strsheet;
            }
          
        }// 结束 for(var isheet = 1;
    
    }//end try
    catch(e) 
    {
      if (culture=="zh-CN"){
        alert( "请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      }
      else{
        alert( "Please check browser set !");
      }
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally

    //DataToSend = funName + splitParm + str + splitParm +  param + splitParm + sTemplateCode ;
    DataToSend = funName + splitParm +  param + splitParm + sTemplateCode + splitParm + str ;
    
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    //xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);
    //alert(xmlhttp.responseXML.xml);
    var errortxt = xmlhttp.responseText;
    if (errortxt == "")
    {      
      return true;
//      if (culture=="zh-CN"){
//        alert("导入成功！");
//      }
//      else{
//        alert("Import succeed !");
//      }
//      history.go(-1);
    }
    else
    {
      alert(errortxt);
      return false;
    }
}

function BudRptRefImport(fileName,url,pseID,largeVersionNumber,culture) {
// 预算报表元素导入函数
// Excel模版数据提取,提交
// 1.数据必须放在第-个工作表，且第一行为字段名称。
//   第一行：系统数据
//     包括固定列：元素Element_code（或假设值Param_code）、归属节点Belong、年期间Period、数值Numerical_Value
//     其它列为维度代码列（不固定）
//   第二行开始：各种数据存放区，其顺序根据列头确定 
//  
//参数定义：
//fileName:导入的Excel文件名
//url:fmsWeb/bud/Report/Import/RefImportExcel.aspx 的相对路径
//pseID:节点ID
//largeVersionNumber:小版本号
//culture:语言
    var DataToSend ="" ;
    var splitParm    = "\u00ff";      //参数分隔字串
    var splitCol    = "\u00fe";      //列间隔符
    var splitRow    = "\u00fd";      //行间隔符
    var splitSheet    = "\u00fc";      //工作表间隔符
    var myApp ;//excel对象
    var x1Book;
    var Cols;//列数
    var sheets;//工作表数
    var endFlag;//excel结束标志
    var curRow; //当前行
    var strLine;//行字符串
    var str; //整个字符串
    var strsheet; //工作表字符串
    var sTemplateCode = ""; // 模板代码
    
    try
    {
       
        myApp = new ActiveXObject("Excel.Application");
        
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }
        //取工作表数
        sheets = 1;
        str = "";
        for(var isheet = 1; isheet <= sheets; isheet ++)
        {
            //取列数
            Cols = 0;
            while(myApp.worksheets(isheet).Cells(1,Cols+1).Value != null ) 
            {
              Cols++;
            }
            
            endFlag = 0;
            curRow =1 ;
            strsheet = "";           
            
            sTemplateCode = myApp.worksheets(isheet).Name;
            // 获取模板是否已导入数据的标志:若已导入数据则提示是否重新导入
            while( endFlag == 0 ) 
            {
            
                endFlag = 1;
                strLine = "";
                for(var i = 0; i <= Cols-1; i ++)
                {
                  
                  if (myApp.worksheets(isheet).Cells(curRow,i+1).Value != null ) 
                  {
                  
                    strLine = strLine + myApp.worksheets(isheet).Cells(curRow,i+1).Value;
                    endFlag = 0;
                    
                  }
                  if (i != Cols.length-1) 
                  {
                  
                    strLine = strLine + splitCol;
                  }
                }
                if (endFlag == 0) //
                {
                  strsheet = strsheet + strLine + splitRow;
                }
                
              curRow++;
            }
            if (isheet == 1)
            {
              str = str + strsheet;
            }
            else
            {
              str = str + splitSheet + strsheet;
            }
          
        }// 结束 for(var isheet = 1;
    
    }//end try
    catch(e) 
    {
      if (culture=="zh-CN"){
        alert( "请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      }
      else{
        alert( "Please check browser set !");
      }
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally

    DataToSend = pseID + splitParm + largeVersionNumber + splitParm  + str + splitParm + sTemplateCode ;
    
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);    
    // 返回信息
    var infotxt = xmlhttp.responseText;
    return infotxt;
}


function BudAdjustImport(fileName,url,smallVersionNumber,memo,culture) {
// 预算调整数据导入函数
// Excel模版数据提取,提交
// 1.数据必须放在第-个工作表，且第一行为字段名称。
//   第一行：系统数据
//     包括固定列：元素Element_code、归属节点Belong、年期间Period、数值Numerical_Value
//     其它列为维度代码列（不固定）
//   第二行开始：各种数据存放区，其顺序根据列头确定
//  
//参数定义：
//fileName:导入的Excel文件名
//url:fmsWeb/bud/DataManage/BudgetAdjust/AdjustImportExcel.aspx 的相对路径
//smallVersionNumber:小版本号
//memo:调整原因
//culture:语言
    var DataToSend ="" ;
    var splitParm    = "\u00ff";      //参数分隔字串
    var splitCol    = "\u00fe";      //列间隔符
    var splitRow    = "\u00fd";      //行间隔符
    var splitSheet    = "\u00fc";      //工作表间隔符
    var myApp ;//excel对象
    var x1Book;
    var Cols;//列数
    var sheets;//工作表数
    var endFlag;//excel结束标志
    var curRow; //当前行
    var strLine;//行字符串
    var str; //整个字符串
    var strsheet; //工作表字符串
    var sTemplateCode = ""; // 模板代码
    
    try
    {
       
        myApp = new ActiveXObject("Excel.Application");
        
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }

        //取工作表数
        isheet = 1;
        str = "";
        sTemplateCode = myApp.worksheets(isheet).Name;
        
        //取列数
        Cols = 0;
        while(myApp.worksheets(isheet).Cells(1,Cols+1).Value != null ) 
        {
            Cols++;
        }
        
        endFlag = 0;
        curRow =1 ;
        strsheet = "";           
        
        while( endFlag == 0 ) 
        {
        
            endFlag = 1;
            strLine = "";
            for(var i = 0; i <= Cols-1; i ++)
            {
                
                if (myApp.worksheets(isheet).Cells(curRow,i+1).Value != null ) 
                {
                
                strLine = strLine + myApp.worksheets(isheet).Cells(curRow,i+1).Value;
                endFlag = 0;
                
                }
                if (i != Cols.length-1) 
                {
                
                strLine = strLine + splitCol;
                }
            }
            if (endFlag == 0) //
            {
                strsheet = strsheet + strLine + splitRow;
            }
            
            curRow++;
        }// 结束while

        str = strsheet;
    
    }//end try
    catch(e) 
    {
      if (culture=="zh-CN"){
        alert( "请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      }
      else{
        alert( "Please check browser set !");
      }
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally

    
    DataToSend = smallVersionNumber + splitParm + memo + splitParm + sTemplateCode + splitParm + str ;
    
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);    
    // 返回信息
    var infotxt = xmlhttp.responseText;
    return infotxt;
}

function FpeExpenseImport(fileName,url,accID,culture) {
// 发生额导入函数
// Excel模版数据提取,提交
// 1.数据必须放在第-个工作表，且第一行为字段名称。
//   第一行：系统数据
//     包括固定列：支出日期    业务类型代码    金额    兑美元汇率    支出说明
//   第二行开始：各种数据存放区，其顺序根据列头确定 
//  
//参数定义：
//fileName:导入的Excel文件名
//url:FMSWeb/Fpe/AbroadBank/Expense/ExpenseImportExcel.aspx 的相对路径
//accID:海外账户ID
//culture:语言
    var DataToSend ="" ;
    var splitParm    = "\u00ff";      //参数分隔字串
    var splitCol    = "\u00fe";      //列间隔符
    var splitRow    = "\u00fd";      //行间隔符
    var splitSheet    = "\u00fc";      //工作表间隔符
    var myApp ;//excel对象
    var x1Book;
    var Cols;//列数
    var sheets;//工作表数
    var endFlag;//excel结束标志
    var curRow; //当前行
    var strLine;//行字符串
    var str; //整个字符串
    var strsheet; //工作表字符串
    var sTemplateCode = ""; // 模板代码
    
    var extendName = "";
    var msg = "";
    if(fileName.length > 3)    
      extendName = fileName.substring(fileName.length-3).toUpperCase();
    if(extendName!="XLS"){
      if (culture=="zh-CN"){
        msg = "只能导入Excel文件。";
      }
      else{
        msg = "Please check file format !";
      }
      return "0[$SPLIT_FLAG$]"+msg;
    } // end if     

    try
    {
       
        myApp = new ActiveXObject("Excel.Application");
        
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }
        //取工作表数
        sheets = 1;
        str = "";
        for(var isheet = 1; isheet <= sheets; isheet ++)
        {
            //取列数
            Cols = 0;
            while(myApp.worksheets(isheet).Cells(1,Cols+1).Value != null ) 
            {
              Cols++;
            }
            
            endFlag = 0;
            curRow =1 ;
            strsheet = "";           
            
            while( endFlag == 0 ) 
            {
            
                endFlag = 1;
                strLine = "";
                for(var i = 0; i <= Cols-1; i ++)
                {
                  
                  if (myApp.worksheets(isheet).Cells(curRow,i+1).Value != null ) 
                  {
                  
                    strLine = strLine + myApp.worksheets(isheet).Cells(curRow,i+1).Value;
                    endFlag = 0;
                    
                  }
                  if (i != Cols.length-1) 
                  {
                  
                    strLine = strLine + splitCol;
                  }
                }
                if (endFlag == 0) //
                {
                  strsheet = strsheet + strLine + splitRow;
                }
                
              curRow++;
            }
            if (isheet == 1)
            {
              str = str + strsheet;
            }
            else
            {
              str = str + splitSheet + strsheet;
            }
          
        }// 结束 for(var isheet = 1;
    
    }//end try
    catch(e) 
    {
      if (culture=="zh-CN"){
        alert( "请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      }
      else{
        alert( "Please check browser set !");
      }
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally

    DataToSend = accID + splitParm + str ;
    
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);    
    // 返回信息
    var infotxt = xmlhttp.responseText;
    return infotxt;
}

function uploadCfmDistExcel(fileName,funName,url,param, culture) {
//Excel导入函数
//数据必须放在第一个工作表，且第一行为中文字段名称。
//参数定义：
//fileName:导入的Excel文件名
//funName:标示不同的导入功能，在Zte.Fbp.BasicData.Business.BExcelToDB.cs中定义。
//url:fmsWeb/fbp/basicData/Web/ExcelToDB.aspx 的相对路径
//param:从界面带过来的参数

    var DataToSend ="" ;
    var splitParm    = "\u00ff";      //参数分隔字串
    var splitCol    = "\u00fe";      //列间隔符
    var splitRow    = "\u00fd";      //行间隔符
    var splitSheet    = "\u00fc";      //工作表间隔符
    var myApp ;//excel对象
    var x1Book;
    var Cols;//列数
    var sheets;//工作表数
    var endFlag;//excel结束标志
    var curRow; //当前行
    var strLine;//行字符串
    var str; //整个字符串
    var strsheet; //工作表字符串
    
    try
    {
       
        myApp = new ActiveXObject("Excel.Application");
        
        if (myApp != null)
        {
            x1Book = myApp.Workbooks.open(fileName);
        }
        //取工作表数
        sheets = 1;
        while (sheets <= myApp.worksheets.Count)
        {
          if (myApp.worksheets(sheets).Cells(1,1).Value != null ) 
          {
            sheets++;
          }
          else
          break;
        }       

        sheets = sheets - 1;
        str = "";
        for(var isheet = 1; isheet <= sheets; isheet ++)
        {
            //取列数
            Cols = 0;
            while(myApp.worksheets(isheet).Cells(1,Cols+1).Value != null ) 
            {
              Cols++;
            }
            
            endFlag = 0;
            curRow =1 ;
            strsheet = "";
            while( endFlag == 0 ) 
            {
            
                endFlag = 1;
                strLine = "";
                for(var i = 0; i <= Cols-1; i ++)
                {
                  
                  if (myApp.worksheets(isheet).Cells(curRow,i+1).Value != null ) 
                  {
                  
                    strLine = strLine + myApp.worksheets(isheet).Cells(curRow,i+1).Value;
                    endFlag = 0;
                    
                  }
                  if (i != Cols.length-1) 
                  {
                  
                    strLine = strLine + splitCol;
                  }
                }
                if (endFlag == 0) //
                {
                  strsheet = strsheet + strLine + splitRow;
                }
                
              curRow++;
            }
            if (isheet == 1)
            {
              str = str + strsheet;
            }
            else
            {
              str = str + splitSheet + strsheet;
            }
          
        }
    
    }//end try
    catch(e) 
    {
      if (culture=="zh-CN"){
        alert( "请检查文件是否存在并且浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。");
      }
      else{
        alert( "Please check browser set !");
      }
      return "";
    }
    finally
    {
        if (myApp != null)
        {
            //结束excel进程，退出完成
            x1Book.close;
            myApp.Quit();
            myApp=null;
        }
    }// end finally

    //DataToSend = "funName=" + funName + "&value=" + str + "&param=" + param;
    DataToSend = funName + splitParm + str + splitParm +  param;
    var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.Open("POST",url,false);
    //xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.setrequestheader("content-length",DataToSend.length);
    xmlhttp.setRequestHeader("Content-Type", "application/zip");
    xmlhttp.send(DataToSend);
    //alert(xmlhttp.responseXML.xml);
    var errortxt = xmlhttp.responseText;
    var result = errortxt.split("[$SPLIT_FLAG$]");
    if (result[0] == "" && result[1] != "")
    {      
      if (culture=="zh-CN"){
        alert("导入成功！");
      }
      else{
        alert("Import succeed !");
      }
      document.all("hiDataBatchID").value = result[1];
    }
    else
    {
      alert(result[0]);
    }
    
}
//设置文本框和下拉框的宽度,该函数调用应放在页面底部('</form>'标签下)
function SetWith(intWidth)
{
    for(var i =0;i < document.all.length;i++)
    {
        if((document.all.item(i).tagName == "INPUT" && document.all.item(i).type == "text") || document.all.item(i).tagName =="SELECT")
        {
            // 判断是否是分页控件的文本框
            if((document.all.item(i).id.indexOf('txtMaxRows') == -1) &&(document.all.item(i).name.indexOf('tbSelYear') == -1)&&(document.all.item(i).name.indexOf('tbSelMonth') == -1)&&
                (document.all.item(i).id.indexOf('txtCurPageIndex') == -1) &&
                document.all.item(i) != document.all.tbSelYear && document.all.item(i) != document.all.tbSelMonth)
            {
                document.all.item(i).style.width = intWidth + "px";
            }
        }
    }
    return true;
}

//去左空格; 
function Ltrim(s){ return s.replace( /^\s*/, ""); } 
//去右空格; 
function Rtrim(s){ return s.replace( /\s*$/, ""); } 
//去左右空格; 
function Trim(s){ return Rtrim(Ltrim(s)); }
//控制多行文本框输入字数
String.prototype.len=function() { return this.replace(/[^\x00-\xff]/g,"***").length; }  
 
//Set maxlength for multiline TextBox  
function setMaxLength(object,length)  
{ 
    var result = true;  
    var controlid = document.selection.createRange().parentElement().id;  
    var controlValue = document.selection.createRange().text;  
    var tempString=object.value; 
     
    var tt="";  
    for(var i=0;i<length;i++)  
        {  
            if(tt.len()<length)  
                tt=tempString.substr(0,i+1);  
            else  
                break;  
        }  
    if(tt.len()>length) 
	{
        tt=tt.substr(0,tt.length-1); 
	}
    object.value=tt; 
     
}  
 
//Check maxlength for multiline TextBox when paste  
function limitPaste(object,length)  
{  
    var tempLength = 0;  
    if(document.selection)  
    {  
        if(document.selection.createRange().parentElement().id == object.id)  
        {  
            tempLength = document.selection.createRange().text.len();  
        }  
    }  
    var tempValue = window.clipboardData.getData("Text");  
    tempLength = object.value.len() + tempValue.len() - tempLength;  

    if (tempLength > length)  
    {  
        tempLength -= length;  
        var tt="";  
        for(var i=0;i<tempValue.len()-tempLength;i++)  
            {  
                if(tt.len()<(tempValue.len()-tempLength))  
                    tt=tempValue.substr(0,i+1);  
                else  
                    break;  
            }  
        if(tt.len()<=0) 
        {     
            window.event.returnValue=false; 
             
        } 
        else 
        { 
            tempValue=tt;  
            window.clipboardData.setData("Text", tempValue);  
            window.event.returnValue = true;  
        } 
    }
}  
 
function PressLength() 
{ 
    if(event.srcElement.type=="text" || event.srcElement.type=="textarea" ) 
    { 
        if(event.srcElement.length!=null) 
            setMaxLength(event.srcElement,event.srcElement.length); 
         
    } 
} 
 
function LimitLength() 
{ 
    if(event.srcElement.type=="text" || event.srcElement.type=="textarea" ) 
    { 
        if(event.srcElement.length!=null) 
            limitPaste(event.srcElement,event.srcElement.length); 
    } 
} 


//dropdownlist 设置tooltip的属性
function showdropitem()
{
    var el = document.getElementsByTagName("select");
    for(i=0;i<el.length;i++)
    {
        for(j=0;j<el[i].options.length;j++)
        {
            el[i].options[j].title = el[i].options[j].text;
        }
    }
}

// 计算字符串的长度
function GetLength(str)
{
    var Cnt = 0;
    var i;
    
    // 循环计算字符串的长度
    for(i = 0; i < str.length; i++)
    {
        // 计算字符的长度，UTF-8 汉字算3个长度（回车算2个长度）
        if((str.charCodeAt(i) >= 0) && (str.charCodeAt(i) <= 255))
        {
		   if(str.charCodeAt(i)==13)
		   {
		      Cnt = Cnt + 2;
		   }
		   else
		   {
            Cnt = Cnt + 1;
			}
        }
        else
        {
            Cnt = Cnt + 3;
        }
    }
    return   Cnt;
} //  end GetLength

//查看EPM单据发票信息，单点EPM页面
function showContractList(Oid,InvoiceType)
{
       var cul = document.all("hiCulture").value; 
      // 测试环境
      // http://epmtest.zte.com.cn:9081/epmdevtest/uiloader/index.html?urlStr=/invoiceManage/action/InvoiceAction.do?operation=getInvoiceMsg&adveBO.invoiceVO.oid=发票号&adveBO.invoiceVO.source=发票类型&invoiceFlag=find
      //正式环境
      // http://ecc.zte.com.cn/epm/uiloader/index.html?urlStr=/invoiceManage/action/InvoiceAction.do?operation=getInvoiceMsg&adveBO.invoiceVO.oid=发票号&adveBO.invoiceVO.source=发票类型&invoiceFlag=find
              
        //提供外系统连接页面
        var url = "http://ecc.zte.com.cn/epm/uiloader/index.html?urlStr=";
		var par = "/invoiceManage/action/InvoiceAction.do?operation=getInvoiceMsg&adveBO.invoiceVO.oid="
                 + Oid + "&adveBO.invoiceVO.source=" + InvoiceType + "&invoiceFlag=find";
        if (cul !='zh-CN')
        {
           par = par + "&eccLanguage=English";
        }
		url = url + escape(par);
        opennewwinNolimit(url,700,500);
}
	
//查看EPM单据发票信息，单点EPM页面,传参数，电子发票ID和发票类型
function goto_EpmInvoiceInfo()
{
	 var epmOid = document.all("epm_oid").value;
	 var epmInvoiceType = document.all("epm_invoice_type").value;
	 
	 if (epmOid != '' && epmInvoiceType != '')
	 {
	    showContractList(epmOid,epmInvoiceType);   
	 }
}	

 //财资 币种LOV
 function setCurrencyLov()
 {
      var handleOrgID=document.getElementById('hiHandleOrgID').value;
      if (handleOrgID == "1") //中兴通讯组织下，用原来的LOV不变
      {
        OpenCommonLov('SelLookupCode','hiCurrencycode,txtCurrencycode', 450, 480, 'lookupType=FPE_BANKBILL_CURRENCYCODE&enabledFlag=Y');
      }
      else
      {
        OpenCommonLov('CURRENCY_LIST', 'txtCurrencycode', 450, 480, 'CURRENCY_FLAG=Y&ENABLED_FLAG=Y&isMulti=0');
	  }
 }
 // 内容摘要：将字符串转换成数字
// strValue: 待转换成数字的字符串
function ParseStringToFloat(strValue)
{
    var floatValue = 0;
    if (strValue != "" && isNaN(strValue) == false)
    {
        floatValue = parseFloat(strValue);
    }
    return floatValue;
} // end ParseStringToFloat


// 内容摘要：格式化数字
// pointRightCount:保留小数点后的尾数
function formatNumber(number, pointRightCount)
{
    return number.toFixed(pointRightCount).toString().replace( /[0]*$/, "").replace( /[.]*$/, "");
}