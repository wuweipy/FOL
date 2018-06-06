/*
功能说明 ：日期选择控件

修改记录：1
修改人员 ：王永恒
修改日期 ：2005-05-25
修改说明 ：登录版本序号更新。 

修改记录：2
修改人员：王永恒
修改日期：2006-08-23
修改内容：新增一个事件document.onmousedown()，一个方法；
          日历的DIV添加 class='popCalendar' 属性。
          以实现当日历出现后，鼠标点击非日历区可关闭此日历。
*/
var curCtl = null;
var curCulture = "en-US";
//************************************************************************************************************
// 在输入框回车自动判断日期值
// 如果是日期则回显正确的格式，否则提示错误日期
function DateInputEnterKey(culture)
{
    key = window.event.keyCode;
    var result;
    if(key == 13)
    {
        //判断是否按下回车键
        var src = event.srcElement;
        var sname = src.id;
        //如果值为空则显示日历控件
        if (src.value=="")
        {
            PopCalendar(culture);
        }
        else
        {
            try
            {
                HiddenDiv();
            }
            catch(er)
            {}
            result = checkIsValidDate(src.value);
            if (result!="INVALID DATE")
            {
                //格式正确
                src.value = result;
            }
            else if (culture=="zh-CN")
            {
                alert("无效的日期值,请重新输入!");
                //格式错误
                src.value = "";
                src.focus();
                return false;
            }
            else
            {
                alert("Invalid date value,please input again!");
                src.value = "";
                src.focus();
                return false;
            }
        } // end if src.value==""
        return false;
    }
    else
    {
        return key;
    }
}

// 韩荣华 2005-01-19 为日期输入增加的方法
// 检验页面输入框控件的值是否为日期型
// 如果是日期则回显示正确的格式，返回true；
// 否则提示错误日期，并返回false
function ValidDateInput(txtid,culture)
{
    var src = document.getElementById(txtid);
    var result;
    try
    {
        HiddenDiv();
    }
    catch(er)
    {}

    result = checkIsValidDate(src.value);

    if (result!="INVALID DATE")
    {
        //格式正确
        src.value = result;
        return true;
    }
    else if (culture=="zh-CN")
    {
        alert("无效的日期值,请重新输入!");
        //格式错误
        src.value = "";
        src.focus();
        return false;
    }
    else
    {
        alert("Invalid date value,please input again!");
        src.value = "";
        src.focus();
        return false;
    }
}

// 2009-05-25 钱艳丽 模板里日期校验函数
//重载方法
 function ValidDateInput(culture) {
  var txtid = window.event.srcElement.id;
  var src = document.getElementById(txtid);
  var result;
  try {
       HiddenDiv();
    }
  catch(er) {}
  result = checkIsValidDate(src.value);
  if (result!="INVALID DATE") { //格式正确
    src.value = result;
    return true;
  }
  else if (culture=="en-US") {  //格式错误
    alert("Invalid date value,please input again!");
	src.value = "";
    src.focus();
    return false;
  }
  else {  
    alert("无效的日期值,请重新输入!");
	src.value = "";
    src.focus();
    return false;
  }
  alert('688');
}


/**
* 韩荣华 2005-01-19 为日期输入增加的方法
*校验字符串是否为日期型
*返回值：
*如果为空，定义校验通过，           返回空
*如果字串为日期型，校验通过，       返回正确格式的值
*如果日期不合法，                   返回INVALID DATE    参考提示信息：输入域的时间不合法！（yyyy-MM-dd）
*/
function checkIsValidDate(str)
{   
    var result;
    var ntemp;
    //如果为空，则通过校验
    if(str == "")
        return "";
    var pattern = /^((\d{4})|(\d{2}))-(\d{1,2})-(\d{1,2})$/g;
    if(!pattern.test(str))
        return "INVALID DATE";
    var arrDate = str.split("-");
    if(parseInt(arrDate[0],10) < 100)
        arrDate[0] = 2000 + parseInt(arrDate[0],10) + "";
    var date =  new Date(arrDate[0],(parseInt(arrDate[1],10) -1)+"",arrDate[2]);
    var dyear = date.getYear();
    if(dyear < 100) dyear = 1900 + dyear;// 因为1900 - 1999 的年度得到的只是二位年
    if(dyear == arrDate[0]
       && date.getMonth() == (parseInt(arrDate[1],10) -1)+""
       && date.getDate() == arrDate[2]) {
        //return true result
        result = dyear + "-";
        ntemp = date.getMonth()+1;
        result = result + (ntemp<10 ? "0" + ntemp : ntemp) + "-";
        ntemp = date.getDate();
        result = result + (ntemp<10 ? "0" + ntemp : ntemp);
        return result;
    }
    else
        return "INVALID DATE";
}


function getNowDate()
{
   var nn=new Date();
   year1=nn.getYear();
   mon1=nn.getMonth()+1;
   date1=nn.getDate();
   var monstr1;
   var datestr1
   if(mon1<10) 
    monstr1="0"+mon1;
   else
    monstr1=""+mon1;
     
   if(date1<10) 
     datestr1="0"+date1;
   else
     datestr1=""+date1;
   return year1+"-"+monstr1+"-"+datestr1;
}
function getlastweekDate()
{
   var nn=new Date();
   year1=nn.getYear();
   mon1=nn.getMonth()+1;
   date1=nn.getDate();
   
   var mm=new Date(year1,mon1-1,date1);
   var tmp1=new Date(2000,1,1);
   var tmp2=new Date(2000,1,15);
   var ne=tmp2-tmp1;
   var mm2=new Date();
   mm2.setTime(mm.getTime()-ne);
   
   
   year2=mm2.getYear();
   mon2=mm2.getMonth()+1;
   date2=mm2.getDate();
    
    
     if(mon2<10) 
    monstr2="0"+mon2;
   else
    monstr2=""+mon2;
     
   if(date2<10) 
     datestr2="0"+date2;
   else
     datestr2=""+date2;
 
   
    return year2+"-"+monstr2+"-"+datestr2;
}

var gdCtrl = new Object();
var goSelectTag = new Array();
var gcGray   = "#808080";
var gcToggle = "#FB8664";
var gcBG = "#e5e6ec";
var previousObject = null;

var gdCurDate = new Date();
var giYear = gdCurDate.getFullYear();
var giMonth = gdCurDate.getMonth()+1;
var giDay = gdCurDate.getDate();



function fSetDate(iYear, iMonth, iDay){
  VicPopCal.style.visibility = "hidden";
  if ((iYear == 0) && (iMonth == 0) && (iDay == 0)){
      gdCtrl.value = "";
  }else{
      iMonth = iMonth + 100 + "";
      iMonth = iMonth.substring(1);
      iDay   = iDay + 100 + "";
      iDay   = iDay.substring(1);
      if(gdCtrl.tagName == "INPUT"){
            gdCtrl.value = iYear+"-"+iMonth+"-"+iDay;
      }else{
            gdCtrl.innerText = iYear+"-"+iMonth+"-"+iDay;
      }
  }
  
  for (i in goSelectTag)
      goSelectTag[i].style.visibility = "visible";
  goSelectTag.length = 0;
  
  window.returnValue=gdCtrl.value;
  //window.close();

}

function HiddenDiv()
{
    var i;
  VicPopCal.style.visibility = "hidden";
  for (i in goSelectTag)
      goSelectTag[i].style.visibility = "visible";
  goSelectTag.length = 0;

}
function fSetSelected(aCell){
  var iOffset = 0;
  var iYear = parseInt(tbSelYear.value);
  var iMonth = parseInt(tbSelMonth.value);
  
  aCell.bgColor = gcBG;
  with (aCell.children["cellText"]){
      var iDay = parseInt(innerText);
      if (color==gcGray)
        iOffset = (Victor<10)?-1:1;
    iMonth += iOffset;
    if (iMonth<1) {
        iYear--;
        iMonth = 12;
    }else if (iMonth>12){
        iYear++;
        iMonth = 1;
    }
  }
  fSetDate(iYear, iMonth, iDay);
}

function Point(iX, iY){
    this.x = iX;
    this.y = iY;
}

function fBuildCal(iYear, iMonth) {
  var aMonth=new Array();
  for(i=1;i<7;i++)
      aMonth[i]=new Array(i);
  
  var dCalDate=new Date(iYear, iMonth-1, 1);
  var iDayOfFirst=dCalDate.getDay();
  var iDaysInMonth=new Date(iYear, iMonth, 0).getDate();
  var iOffsetLast=new Date(iYear, iMonth-1, 0).getDate()-iDayOfFirst+1;
  var iDate = 1;
  var iNext = 1;

  for (d = 0;d < 7;d++)
    aMonth[1][d] = (d<iDayOfFirst)?-(iOffsetLast+d):iDate++;
  for (w = 2;w < 7;w++)
      for (d = 0;d < 7;d++)
        aMonth[w][d] = (iDate<=iDaysInMonth)?iDate++:-(iNext++);
  return aMonth;
}

function fDrawCal(iYear, iMonth, iCellHeight, sDateTextSize) {
  var WeekDay = new Array("日","一","二","三","四","五","六");
  var styleTR = " bgcolor='#b6d9f5' valign='middle' align='center' height='"+iCellHeight+"' style='border:1 outset;font:bold arial "+sDateTextSize+";";//Coded by Liming Weng(Victor Won) email:victorwon@sina.com
  var styleTD = " bgcolor='"+gcBG+"' valign='middle' align='center' height='"+iCellHeight+"' style='border:1 outset;font:bold arial "+sDateTextSize+";";//Coded by Liming Weng(Victor Won) email:victorwon@sina.com

  with (document) {
    write("<tr>");
    for(i=0;i<7;i++){
        write("<td "+styleTR+"color:#990099' >"+ WeekDay[i] + "</td>");
    }
    write("</tr>");

      for (w = 1;w < 7;w++) {
        write("<tr>");
        for (d = 0;d < 7;d++) {
            write("<td id=calCell "+styleTD+" ;cursor:hand;' onMouseOver='this.bgColor=gcToggle' onMouseOut='this.bgColor=gcBG' onclick='fSetSelected(this)'>");
            write("<font id=cellText Victor='Liming Weng'> </font>");
            write("</td>")
        }
        write("</tr>");
    }
  }
}

function fUpdateCal(iYear, iMonth) {
  myMonth = fBuildCal(iYear, iMonth);
  var i = 0;
  for (w = 0;w < 6;w++)
    for (d = 0;d < 7;d++)
        with (cellText[(7*w)+d]) {
            Victor = i++;
            if (myMonth[w+1][d]<0) {
                color = gcGray;
                innerText = -myMonth[w+1][d];
            }else{
                color = ((d==0)||(d==6))?"red":"black";
                innerText = myMonth[w+1][d];
            }
        }
}

function fSetYearMon(iYear, iMon){
  tbSelMonth.options[iMon-1].selected = true;
  for (i = 0;i < tbSelYear.length;i++)
    if (tbSelYear.options[i].value == iYear)
        tbSelYear.options[i].selected = true;
  fUpdateCal(iYear, iMon);
}
var giListSel = 1;
function fPrev()
{
        if (giListSel==0)
            fPrevYear();
        else
            fPrevMonth();
}
function fNext()
{
        if (giListSel==0)
            fNextYear();
        else
            fNextMonth();
}
function fPrevYear(){
  var iMon = tbSelMonth.value;
  var iYear = tbSelYear.value;
  
  iYear--;
  
  fSetYearMon(iYear, iMon);
}

function fNextYear(){
  var iMon = tbSelMonth.value;
  var iYear = tbSelYear.value;
  
  iYear++;
  
  fSetYearMon(iYear, iMon);
}

function fPrevMonth(){
  var iMon = tbSelMonth.value;
  var iYear = tbSelYear.value;
  
  if (--iMon<1) {
      iMon = 12;
      iYear--;
  }
  
  fSetYearMon(iYear, iMon);
}

function fNextMonth(){
  var iMon = tbSelMonth.value;
  var iYear = tbSelYear.value;
  
  if (++iMon>12) {
      iMon = 1;
      iYear++;
  }
  
  fSetYearMon(iYear, iMon);
}

function fToggleTags(){
  with (document.all.tags("SELECT")){
     for (i=0;i<length;i++)
         if ((item(i).Victor!="Won")&&fTagInBound(item(i))){
             item(i).style.visibility = "hidden";
             goSelectTag[goSelectTag.length] = item(i);
         }
  }
}

function fTagInBound(aTag)
{
    /*
    with (VicPopCal.style)
    {
        var l = parseInt(left);
        var t = parseInt(top);
        var r = l+parseInt(width);
        var b = t+parseInt(height);
        var ptLT = fGetXY(aTag);
        return !((ptLT.x>=r)||(ptLT.x+aTag.offsetWidth<=l)||(ptLT.y>=b)||(ptLT.y+aTag.offsetHeight<=t));
    }
    */
    var ptLT = fGetXY(aTag);
    var l = parseInt(VicPopCal.offsetLeft);
    var t = parseInt(VicPopCal.offsetTop);
    var r = l+parseInt(VicPopCal.offsetWidth);
    var b = t+parseInt(VicPopCal.offsetHeight);
    return !((ptLT.x>=r)||(ptLT.x+aTag.offsetWidth<=l)||(ptLT.y>=b)||(ptLT.y+aTag.offsetHeight<=t));
}

function fGetXY(aTag){
  var oTmp = aTag;
  var pt = new Point(0,0);
  do {
      pt.x += oTmp.offsetLeft;
      pt.y += oTmp.offsetTop;
      oTmp = oTmp.offsetParent;
  } while(oTmp.tagName!="BODY");
  return pt;
}

function clickOnOther(){
    var closeSelectDateWindow="false";
    if(previousObject == null){
        return false;
    }
    if(window.event.srcElement.name != previousObject.name){
        closeSelectDateWindow = "true";
    }
    if(window.event.srcElement.name == "PrevMonth"){
        closeSelectDateWindow = "false";
    }
    if(window.event.srcElement.name == "tbSelYear"){
        closeSelectDateWindow = "false";
    }
    if(window.event.srcElement.name == "tbSelMonth"){
        closeSelectDateWindow = "false";
    }
    
    if(closeSelectDateWindow == "true"){
        HiddenDiv();
    }
}

// Main: popCtrl is the widget beyond which you want this calendar to appear;
//       dateCtrl is the widget into which you want to put the selected date.
// i.e.: <input type="text" name="dc" style="text-align:center" readonly><INPUT type="button" value="V" onclick="fPopCalendar(dc,dc);return false">
function fPopCalendar(popCtrl, dateCtrl,strDate){
    popCtrl =document.getElementById(popCtrl);
    dateCtrl =document.getElementById(dateCtrl);
  if (popCtrl == previousObject){
          if (VicPopCal.style.visibility == "visible"){
          HiddenDiv();
          return true;
      }
      
  }
  previousObject = popCtrl;
  gdCtrl = dateCtrl;
  fInitialDate(strDate);
  fSetYearMon(giYear, giMonth);
  var point = fGetXY(popCtrl);
  with (VicPopCal.style)
  {
    left = point.x;
    top  = point.y+popCtrl.offsetHeight;
    //width = VicPopCal.offsetWidth;
    //height = VicPopCal.offsetHeight;
    //width = 209;
    //height = 200;
    fToggleTags(point);
    visibility = 'visible';
  }
}

// Added by Danian Zhang/SUI
function fInitialDate(strDate)
{
    if( strDate == null || strDate.length != 10 )
        return false;

    var sYear  = strDate.substring(0,4);
    var sMonth = strDate.substring(5,7);
    var sDay   = strDate.substring(8,10);

    if( sMonth.charAt(0) == '0' ) { sMonth = sMonth.substring(1,2);}
    if( sDay.charAt(0)   == '0' ) { sDay   = sDay.substring(1,2);}

    var nYear  = parseInt(sYear );
    var nMonth = parseInt(sMonth);
    var nDay   = parseInt(sDay  );
    
    if ( isNaN(nYear ) )    return false;
    if ( isNaN(nMonth) )    return false;
    if ( isNaN(nDay  ) )    return false;

    var arrMon = new Array(12);
    arrMon[ 0] = 31;arrMon[ 1] = nYear % 4 == 0 ? 29:28;
    arrMon[ 2] = 31;arrMon[ 3] = 30;
    arrMon[ 4] = 31;arrMon[ 5] = 30;
    arrMon[ 6] = 31;arrMon[ 7] = 31;
    arrMon[ 8] = 30;arrMon[ 9] = 31;
    arrMon[10] = 30;arrMon[11] = 31;

    if ( nYear  < 1900 || nYear > 2100 )            return false;
    if ( nMonth < 1 || nMonth > 12 )                return false;
    if ( nDay < 1 || nDay > arrMon[nMonth - 1] )    return false;

    giYear  = nYear;
    giMonth = nMonth;
    giDay   = nDay;
    return true;
}

var gMonths = new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");
with (document)
{
    write("<Div id='VicPopCal' class='popCalendar' style='POSITION:absolute;VISIBILITY:hidden;OVERFLOW:hidden;background-color:buttonface;border:1px outset;z-index:100;'>");
    write("<br style='line-height:3px;'/><fieldset style='cursor:default;width:200;margin:3;'>");
    write("<legend title='关闭日历' style='cursor:hand;' onclick='HiddenDiv();'><b disabled>日历窗口</b></legend>");
    write("<TABLE border=0 width=100% align='center' valign='middle' bgcolor='buttonface'>");
    write("<TR>");
    write("<td valign='middle' align='center' class='title21'><input type='button' name='PrevMonth' style='border-width:1px;height:20;width:20;FONT:bold' value='<' onClick='fPrev()'>");
    write("&nbsp;<SELECT name='tbSelYear' onclick = 'giListSel=0;' onChange='fUpdateCal(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");
    for(i=1950;i<2031;i++)
        write("<OPTION value='"+i+"'>"+i+" 年</OPTION>");
    write("</SELECT>");
    write("&nbsp;<select name='tbSelMonth' onclick= 'giListSel=1;' onChange='fUpdateCal(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");
    for (i=0;i<12;i++)
        write("<option value='"+(i+1)+"'>"+gMonths[i]+"</option>");
    write("</SELECT>");
    write("&nbsp;<input type='button' name='PrevMonth' style='border-width:1px;height:20;width:20;FONT:bold' value='>' onclick='fNext()'>");
    write("</td>");
    write("</TR><TR class='title21'>");
    write("<td align='center'class='title21'>");
    write("<DIV class='popCalendar' style='background-color:teal'><table width='100%' cellspacing=0 cellpadding=0 border=0 style='border:1 inset;' rules=all>");
    fDrawCal(giYear, giMonth, 14, '9');//Modified by daijinnian 2002-11-18,origin:20,'12'
    write("</table></DIV>");
    write("</td>");
    write("</TR><TR><TD align='center' class='title21'>");
    write("<TABLE width='100%' cellspacing=0 cellpadding=0 border=0 style='border:1 inset;'><TR><TD style='border:1 outset;font-family:宋体' align='center'>");
    write("<B style='cursor:hand' onclick='fSetDate(0,0,0)' onMouseOver='this.style.color=gcToggle' onMouseOut='this.style.color=0'>清空</B>");
    write("</td><td style='border:1 outset;font-family:宋体' algin='center' class='title21'>");
    write("<B style='cursor:hand' onclick='fSetDate(giYear,giMonth,giDay)' onMouseOver='this.style.color=gcToggle' onMouseOut='this.style.color=0'>今天: "+giYear+"-"+giMonth+"-"+giDay+"</B>");
    write("</td></tr></table>");
    write("</TD></TR>");
    write("</TABLE><br style='line-height:3px;'/></fieldset></Div>");
}


//------------------------------------- 以下为英文日历 ---------------------------------------------------------------
//calander
function getNowDateUS()
{
    var nn=new Date();
    year1=nn.getYear();
    mon1=nn.getMonth()+1;
    date1=nn.getDate();
    var monstr1;
    var datestr1;
    if(mon1<10) 
        monstr1="0"+mon1;
    else
        monstr1=""+mon1;
            
    if(date1<10) 
        datestr1="0"+date1;
    else
        datestr1=""+date1;
    return year1+"-"+monstr1+"-"+datestr1;
}
function getlastweekDateUS()
{
    var nn=new Date();
    year1=nn.getYear();
    mon1=nn.getMonth()+1;
    date1=nn.getDate();
        
    var mm=new Date(year1,mon1-1,date1);
    var tmp1=new Date(2000,1,1);
    var tmp2=new Date(2000,1,15);
    var ne=tmp2-tmp1;
    var mm2=new Date();
    mm2.setTime(mm.getTime()-ne);
        
        
    year2=mm2.getYear();
    mon2=mm2.getMonth()+1;
    date2=mm2.getDate();
        
        
        if(mon2<10) 
        monstr2="0"+mon2;
    else
        monstr2=""+mon2;
            
    if(date2<10) 
        datestr2="0"+date2;
    else
        datestr2=""+date2;
        
        
        return year2+"-"+monstr2+"-"+datestr2;
}

    var gdCtrl = new Object();
    var goSelectTag = new Array();
    var gcGray   = "#808080";
    var gcToggle = "#FB8664";
    var gcBG = "#e5e6ec";
    var previousObject = null;

    var gdCurDate = new Date();
    var giYear = gdCurDate.getFullYear();
    var giMonth = gdCurDate.getMonth()+1;
    var giDay = gdCurDate.getDate();

function fSetDateUS(iYear, iMonth, iDay)
{
    VicPopCalUS.style.visibility = "hidden";
    if ((iYear == 0) && (iMonth == 0) && (iDay == 0))
    {
          gdCtrl.value = "";
    }
    else
    {
          iMonth = iMonth + 100 + "";
          iMonth = iMonth.substring(1);
          iDay   = iDay + 100 + "";
          iDay   = iDay.substring(1);
          if(gdCtrl.tagName == "INPUT")
          {
                gdCtrl.value = iYear+"-"+iMonth+"-"+iDay;
          }
          else
          {
                gdCtrl.innerText = iYear+"-"+iMonth+"-"+iDay;
          }
    }
        
    for (i in goSelectTag)
          goSelectTag[i].style.visibility = "visible";
    goSelectTag.length = 0;
        
    window.returnValue=gdCtrl.value;
    //window.close();

}

function HiddenDivUS()
{
    var i;
    VicPopCalUS.style.visibility = "hidden";
    for (i in goSelectTag)
          goSelectTag[i].style.visibility = "visible";
    goSelectTag.length = 0;

}
function fSetSelectedUS(aCell)
{
    var iOffset = 0;
    var iYear = parseInt(tbSelYearUS.value);
    var iMonth = parseInt(tbSelMonthUS.value);
        
    aCell.bgColor = gcBG;
    with (aCell.children["cellTextUS"])
    {
          var iDay = parseInt(innerText);
          if (color==gcGray)
            iOffset = (Victor<10)?-1:1;
        iMonth += iOffset;
        if (iMonth<1) 
        {
            iYear--;
            iMonth = 12;
        }
        else
        if (iMonth>12)
        {
            iYear++;
            iMonth = 1;
        }
    }
    fSetDateUS(iYear, iMonth, iDay);
}

function PointUS(iX, iY)
{
    this.x = iX;
    this.y = iY;
}

function fBuildCalUS(iYear, iMonth) 
{
    var aMonth=new Array();
    for(i=1;i<7;i++)
          aMonth[i]=new Array(i);
        
    var dCalDate=new Date(iYear, iMonth-1, 1);
    var iDayOfFirst=dCalDate.getDay();
    var iDaysInMonth=new Date(iYear, iMonth, 0).getDate();
    var iOffsetLast=new Date(iYear, iMonth-1, 0).getDate()-iDayOfFirst+1;
    var iDate = 1;
    var iNext = 1;

    for (d = 0;d < 7;d++)
        aMonth[1][d] = (d<iDayOfFirst)?-(iOffsetLast+d):iDate++;
    for (w = 2;w < 7;w++)
          for (d = 0;d < 7;d++)
            aMonth[w][d] = (iDate<=iDaysInMonth)?iDate++:-(iNext++);
    return aMonth;
}

function fDrawCalUS(iYear, iMonth, iCellHeight, sDateTextSize) 
{
    var WeekDay = new Array("Su","Mo","Tu","We","Th","Fr","Sa");

    var styleTR = " bgcolor='#b6d9f5' valign='middle' align='center' height='"+iCellHeight+"' style='border:1 outset;font:bold arial "+sDateTextSize+";";
    var styleTD = " bgcolor='"+gcBG+"' valign='middle' align='center' height='"+iCellHeight+"' style='border:1 outset;font:bold arial "+sDateTextSize+";";

    with (document) 
    {
        write("<tr>");
        for(i=0;i<7;i++)
        {
            write("<td "+styleTR+"color:#990099' >"+ WeekDay[i] + "</td>");
        }
        write("</tr>");

          for (w = 1;w < 7;w++) 
          {
            write("<tr>");
            for (d = 0;d < 7;d++) 
            {
                write("<td id=calCell "+styleTD+"cursor:hand;' onMouseOver='this.bgColor=gcToggle' onMouseOut='this.bgColor=gcBG' onclick='fSetSelectedUS(this)'>");
                write("<font id=cellTextUS Victor='Liming Weng'> </font>");
                write("</td>")
            }
            write("</tr>");
        }
    }
}

function fUpdateCalUS(iYear, iMonth) 
{
    myMonth = fBuildCalUS(iYear, iMonth);
    var i = 0;
    for (w = 0;w < 6;w++)
        for (d = 0;d < 7;d++)
            with (cellTextUS[(7*w)+d]) 
            {
                Victor = i++;
                if (myMonth[w+1][d]<0) 
                {
                    color = gcGray;
                    innerText = -myMonth[w+1][d];
                }
                else
                {
                    color = ((d==0)||(d==6))?"red":"black";
                    innerText = myMonth[w+1][d];
                }
            }
}

function fSetYearMonUS(iYear, iMon)
{
    tbSelMonthUS.options[iMon-1].selected = true;
    for (i = 0;i < tbSelYearUS.length;i++)
        if (tbSelYearUS.options[i].value == iYear)
            tbSelYearUS.options[i].selected = true;
    fUpdateCalUS(iYear, iMon);
}

//年翻页还是月翻页
var giListSelUS = 1;
//向前翻页
function fPrevUS()
{
        if (giListSelUS==0)
            fPrevYearUS();
        else
            fPrevMonthUS();
}
//向后翻页
function fNextUS()
{
        if (giListSelUS==0)
            fNextYearUS();
        else
            fNextMonthUS();
}
//向前翻年
function fPrevYearUS(){
  var iMon = tbSelMonthUS.value;
  var iYear = tbSelYearUS.value;
  
  iYear--;
  
  fSetYearMonUS(iYear, iMon);
}
//向后翻年
function fNextYearUS(){
  var iMon = tbSelMonthUS.value;
  var iYear = tbSelYearUS.value;
  
  iYear++;
  
  fSetYearMonUS(iYear, iMon);
}
//向前翻月
function fPrevMonthUS()
{
    var iMon = tbSelMonthUS.value;
    var iYear = tbSelYearUS.value;
        
    if (--iMon<1) 
    {
        iMon = 12;
        iYear--;
    }
        
    fSetYearMonUS(iYear, iMon);
}
//向后翻月
function fNextMonthUS()
{
    var iMon = tbSelMonthUS.value;
    var iYear = tbSelYearUS.value;
        
    if (++iMon>12) 
    {
        iMon = 1;
        iYear++;
    }
        
    fSetYearMonUS(iYear, iMon);
}

function fToggleTagsUS()
{
    with (document.all.tags("SELECT"))
    {
         for (i=0;i<length;i++)
             if ((item(i).Victor!="Won")&&fTagInBoundUS(item(i)))
             {
                 item(i).style.visibility = "hidden";
                 goSelectTag[goSelectTag.length] = item(i);
             }
    }
}

function fTagInBoundUS(aTag)
{
    /*
    with (VicPopCalUS.style)
    {
          var l = parseInt(left);
          var t = parseInt(top);
          var r = l+parseInt(width);
          var b = t+parseInt(height);
        var ptLT = fGetXYUS(aTag);
        return !((ptLT.x>=r)||(ptLT.x+aTag.offsetWidth<=l)||(ptLT.y>=b)||(ptLT.y+aTag.offsetHeight<=t));
    }
    */
    var ptLT = fGetXYUS(aTag);
    var l = parseInt(VicPopCalUS.offsetLeft);
    var t = parseInt(VicPopCalUS.offsetTop);
    var r = l+parseInt(VicPopCalUS.offsetWidth);
    var b = t+parseInt(VicPopCal.offsetHeight);
    return !((ptLT.x>=r)||(ptLT.x+aTag.offsetWidth<=l)||(ptLT.y>=b)||(ptLT.y+aTag.offsetHeight<=t));
}

function fGetXYUS(aTag)
{
    var oTmp = aTag;
    var pt = new PointUS(0,0);
    do 
    {
          pt.x += oTmp.offsetLeft;
          pt.y += oTmp.offsetTop;
          oTmp = oTmp.offsetParent;
    } while(oTmp.tagName!="BODY");
    return pt;
}

// Main: popCtrl is the widget beyond which you want this calendar to appear;
//       dateCtrl is the widget into which you want to put the selected date.
// i.e.: <input type="text" name="dc" style="text-align:center" readonly><INPUT type="button" value="V" onclick="fPopCalendarUS(dc,dc);return false">
function fPopCalendarUS(popCtrl, dateCtrl,strDate)
{
    popCtrl =document.getElementById(popCtrl);
    dateCtrl =document.getElementById(dateCtrl);
    if (popCtrl == previousObject)
    {
        if (VicPopCalUS.style.visibility == "visible")
        {
            HiddenDivUS();
            return true;
        }
    }
    previousObject = popCtrl;
    gdCtrl = dateCtrl;
    fInitialDateUS(document.getElementsByName(strDate));
    fSetYearMonUS(giYear, giMonth);
    var PointUS = fGetXYUS(popCtrl);
    with (VicPopCalUS.style) 
    {
        left = PointUS.x;
        top  = PointUS.y+popCtrl.offsetHeight;
        //width = VicPopCal.offsetWidth;
        //height = VicPopCal.offsetHeight;
        //width = 209;
        //height = 200;
        fToggleTagsUS(PointUS);
        visibility = 'visible';
    }
}

// Added by Danian Zhang/SUI
function fInitialDateUS(strDate)
{
    if( strDate == null || strDate.length != 10 )
        return false;

    var sYear  = strDate.substring(0,4);
    var sMonth = strDate.substring(5,7);
    var sDay   = strDate.substring(8,10);

    if( sMonth.charAt(0) == '0' ) 
    {
        sMonth = sMonth.substring(1,2);
    }
    if( sDay.charAt(0)   == '0' )
    {
        sDay   = sDay.substring(1,2);
    }

    var nYear  = parseInt(sYear );
    var nMonth = parseInt(sMonth);
    var nDay   = parseInt(sDay  );
        
    if ( isNaN(nYear ) )    return false;
    if ( isNaN(nMonth) )    return false;
    if ( isNaN(nDay  ) )    return false;

    var arrMon = new Array(12);
    arrMon[ 0] = 31;arrMon[ 1] = nYear % 4 == 0 ? 29:28;
    arrMon[ 2] = 31;arrMon[ 3] = 30;
    arrMon[ 4] = 31;arrMon[ 5] = 30;
    arrMon[ 6] = 31;arrMon[ 7] = 31;
    arrMon[ 8] = 30;arrMon[ 9] = 31;
    arrMon[10] = 30;arrMon[11] = 31;

    if ( nYear  < 1900 || nYear > 2100 )
        return false;
    if ( nMonth < 1 || nMonth > 12 )
        return false;
    if ( nDay < 1 || nDay > arrMon[nMonth - 1] )
        return false;

    giYear  = nYear;
    giMonth = nMonth;
    giDay   = nDay;
    return true;
}
var gMonthsUS = new Array("Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");
with (document)
{
    write("<Div id='VicPopCalUS' class='popCalendar' style='POSITION:absolute;VISIBILITY:hidden;OVERFLOW:hidden;background-color:buttonface;border:1px outset;z-index:100;'>");
    write("<br style='line-height:3px;'/><fieldset style='cursor:default;width:200;margin:3;'>");
    write("<legend title='Close Calendar' style='cursor:hand;' onclick='HiddenDivUS();'><b disabled>Calendar Window</b></legend>");
    write("<TABLE border=0 width=100% align='center' valign='middle' bgcolor='buttonface'>");
    write("<TR>");

    write("<td valign='middle' align='center'><input type='button' name='PrevMonthUS' style='border-width:1px;height:20;width:20;FONT:bold' value='<' onClick='fPrevUS()'>");
    write("&nbsp;<SELECT name='tbSelYearUS' onclick = 'giListSelUS=0;' onChange='fUpdateCalUS(tbSelYearUS.value, tbSelMonthUS.value)' Victor='Won'>");
    for(i=1950;i<2031;i++)
        write("<OPTION value='"+i+"'>"+i+"</OPTION>");
    write("</SELECT>");
    write("&nbsp;<select name='tbSelMonthUS' onclick= 'giListSelUS=1;' onChange='fUpdateCalUS(tbSelYearUS.value, tbSelMonthUS.value)' Victor='Won'>");
    for (i=0;i<12;i++)
        write("<option value='"+(i+1)+"'>"+gMonthsUS[i]+"</option>");
    write("</SELECT>");
    write("&nbsp;<input type='button' name='PrevMonthUS' style='border-width:1px;height:20;width:20;FONT:bold' value='>' onclick='fNextUS()'>");

    write("</td>");
    write("</TR><TR>");
    write("<td align='center'>");
    write("<DIV class='popCalendar' style='background-color:teal'><table width='100%' cellspacing=0 cellpadding=0 border=0 style='border:1 inset;' rules=all>");
    fDrawCalUS(giYear, giMonth, 14, '9');
    write("</table></DIV>");
    write("</td>");
    write("</TR><TR><TD align='center'>");
    write("<TABLE width='100%' cellspacing=0 cellpadding=0 border=0 style='border:1 inset;'><TR><TD style='border:1 outset;font-family:宋体' align='center'>");
    write("<B style='cursor:hand;' onclick='fSetDateUS(0,0,0)' onMouseOver='this.style.color=gcToggle' onMouseOut='this.style.color=0'>Clear</B>");
    write("</td><td style='border:1 outset;font-family:宋体' algin='center'>");
    write("<B style='cursor:hand;' onclick='fSetDateUS(giYear,giMonth,giDay)' onMouseOver='this.style.color=gcToggle' onMouseOut='this.style.color=0'>Today: "+giYear+"-"+giMonth+"-"+giDay+"</B>");
    write("</td></tr></table>");
    write("</TD></TR>");
    write("</TABLE><br style='line-height:3px;'/></fieldset></Div>");
}

//-----------------------------------------
//---------统一的中英文调用函数
//调出日期
function PopCalendar(cultureOrId)
{
    var sname;
	var culture;
	if (event.srcElement.type == "text")
	{
		curCulture = cultureOrId;
		sname = event.srcElement.id;
		curCtl = event.srcElement;
	}
	else
	{
		if (document.all("hiCulture") != null)
		{
			curCulture = document.all("hiCulture").value;
		}
		else
		{
			alert("请添加id为'hiCulture'的隐藏控件并赋值");
		}
		sname = cultureOrId;
		if (document.all(cultureOrId) != null)
		{
			curCtl = document.all(cultureOrId);
		}
		else
		{
			alert("不存在id为"+cultureOrId+"的控件");
		}
	}
	
    if (curCulture=="en-US")
    {
        HiddenDivUS();
        fPopCalendarUS(sname,sname);
    }
    else 
    {
        HiddenDiv();
        fPopCalendar(sname,sname);
    }
    return false;
}
//************************************************************************************************************

/*************************************************************************************************************/
// 修改记录：2 2006-08-23 王永恒 实现当日历出现后，鼠标点击非日历区可关闭此日历 开始
// 当鼠标左键按下时触发此事件
// 特别注意：不能使用document.onclick()事件，否则会和日历正常弹出的事件有所冲突（特别是页面有两个以上日历时）
function onmousedown()
{
    if (curCtl == null) return;
    var obj = window.event.srcElement;
    if (obj.id == curCtl.id) return;
    var cls = obj.className;
    var rtn = getTopValidTag(obj);
    if (rtn.className != "popCalendar")
    {
        if (curCulture == "en-US")
        {
            HiddenDivUS();
        }
        else 
        {
            HiddenDiv();
        }   
             
        try
		{
			HiddenWfolYearMonthDiv();
		}
		catch(er)
		{}
    }

} // end document.onmousedown()

// 获取当前被点击控件的顶层控件
function getTopValidTag(temp)
{
    if (temp.tagName == "DIV") return temp;
    var oTmp = temp;
    while(oTmp.tagName != "DIV")
    {
        if (oTmp.tagName == "BODY") break;
        oTmp = oTmp.offsetParent;
    }
    return oTmp;
} // end testTag()
// 修改记录：2 结束
/*************************************************************************************************************/