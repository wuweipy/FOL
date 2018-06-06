/**
  * 当前版本：2.5.1.06
  * 版本历史：
  *     2.5.1.05：增加多语言支持；
  *
**/

//老的头声明需要；新的不用
//window.attachEvent('onscroll', investRoll);
//---------------------------标签初始化-------------------------------------------------

var INVESTIGATE_TAG = "itp:investigateCtrl";

//服务端组件上下文（测试环境）
//var invest_serverContext = "http://10.3.64.37:8001/itpInvestServer/itpInvestigate";
//服务端组件上下文(正式环境)
var invest_serverContext = "http://itp.zte.com.cn:8080/itpInvestServer/itpInvestigate";

var invest_subSysCode = "";
var invest_funCode = "";
var invest_getFunCodeMethodName = "";
var invest_getEmployeeIdMethodName = "";
var invest_callBackMethodName = "";
var invest_getLanguageMethodName = "";
var itpInvestigatPath = "";
var invest_isInit = false; //组件是否已初始化
var invest_isAlert = false; //是否弹出窗口
var invest_type = "";
var invest_language = "zh-CN"; //zh-CN en-US
var invest_version = "2.5.1.06"; //组件版本号

var invest_submitBtnText = "";
var invest_cancleBtnText = "";
var invest_promptInfo = "";
var invest_message = "";
var invest_buttonText = "";
var invest_subject = "";
var invest_successInfo = "";
var invest_warnInfo = "";


//标签初始化
function itpInvestigateCtrl_init()
{
    //先获取当前文件的路径
    var itpSelectScripts = document.getElementsByTagName("script");
    for(var i=0;i<itpSelectScripts.length;i++)
    {
        var src = itpSelectScripts[i].getAttribute("src");
        if(src != null && src.indexOf("itp_investigate") > -1)
        {
            itpInvestigatPath = src.substring(0,src.indexOf("itp_investigate"));
        }
    }
    
    //加载样式表
    itp_css_include(itpInvestigatPath + "invest.css");
    
    //加载标签内容
    var tags = document.getElementsByTagName(INVESTIGATE_TAG);
    for(var i =0;i<tags.length;i++)
    {
        itpInvestigateCtrlShow(tags[i]);
    }
    
    invest_isInit = true;
    if(invest_isAlert)
    {
        itpInvestigateCtrlShowAlert();
    }
}

//中英文
function itpInvestSetText()
{
    invest_submitBtnText = getInvestResource("submitBtnText");
    invest_cancleBtnText = getInvestResource("cancleBtnText");
    invest_promptInfo = getInvestResource("promptInfo");
    invest_message = getInvestResource("message");
    invest_buttonText = getInvestResource("buttonText");
    invest_subject = getInvestResource("subject");
    invest_successInfo = getInvestResource("successInfo");
    invest_warnInfo = getInvestResource("warnInfo");
}

/**
标签展示
*/
function itpInvestigateCtrlShow(labelObj)
{ 
    invest_type = (labelObj.getAttribute("type")||"");
    invest_subSysCode = (labelObj.getAttribute("subSysCode")||"");
    invest_funCode = (labelObj.getAttribute("funCode")||"");
    invest_getEmployeeIdMethodName = (labelObj.getAttribute("getEmployeeIdMethodName")||"");
    invest_getLanguageMethodName = (labelObj.getAttribute("getLanguageMethodName")||"");
    invest_getFunCodeMethodName = (labelObj.getAttribute("getFunCodeMethodName")||"");
    //获取三级菜单编号
    if(invest_funCode == "")
    {
       if(invest_getFunCodeMethodName != "")
       {
          invest_funCode = eval(invest_getFunCodeMethodName + "()");
       }
    }
    //设置语言
    if(invest_getLanguageMethodName != "")
    {
        invest_language = eval(invest_getLanguageMethodName + "()");
        itpInvestSetText();
    }
    
    //方案一
    if(invest_type == "showOnRight")
    {
        var invest = "";
        if(invest_getEmployeeIdMethodName != "")
        {
            invest = eval(invest_getEmployeeIdMethodName + "()");
        }
        
        var src = invest_serverContext + "/investContentOne_" + invest_language + ".html?";
        src += "subSysCode=" + invest_subSysCode + "&funCode=" + invest_funCode + "&invest=" + invest;
        
        var html = "";
        html += '<div id="invest_div" style="width:24px;height:250px;">';
        html += '<div class="invest_title_ver_' + invest_language.replace("-","_") + '" id="clickdiv" onmouseover="janover(this)" onmouseout="janout(this)"></div>';
        //老的头声明用
        html += '<div class="invest_list_ver" id="investList" style="display:none; width:348px; height:240px; margin-top:-109px;_margin-top:0px">';
        //新的头声明用
        //html += '<div class="invest_list_ver" id="investList" style="display:none; width:348px; height:240px; margin-top:-109px;_margin-bottom:0px">';
        html += '<table cellspacing="0" cellpadding="0" width="100%" border="0">';
        html += '<tr>';
        html += '<td colspan="4">' ;
        html += '<iframe scrolling="no" id="invest" width="348px" height="205px" src="'+ src + '"frameborder="0"></iframe>';
        html += '</td>';
        html += '</tr>';
        html += '<tr align="center">';
        html += '<td colspan="4" style="padding-left:15px;">';
    		html += '<a href="#" class="link" id="yes" onclick="invokeChild()">' + invest_submitBtnText + '</a> <a href="#" class="link" id="no" style="margin-left:5px;">' + invest_cancleBtnText + '</a>';
    		html += '</td>';
        html += '</tr>';
        html += '</table>';
        html += '</div>';
        html += '</div>';
        
        labelObj.outerHTML = html;
    }    
    //方案二
    else if(invest_type == "showAfterSubmit")
    {
        invest_callBackMethodName = (labelObj.getAttribute("callBack")||"");
        
        var invest = "";
        if(invest_getEmployeeIdMethodName != "")
        {
            invest = eval(invest_getEmployeeIdMethodName + "()");
        }
        
        var src = invest_serverContext + "/investContentTwo_" + invest_language + ".html?";
        src += "subSysCode=" + invest_subSysCode + "&funCode=" + invest_funCode + "&invest=" + invest;
        
        var message = labelObj.getAttribute("message");
        var buttonText = labelObj.getAttribute("buttonText");
        var investSubject = labelObj.getAttribute("investSubject");
        if(message != null && message != "")
        {
           invest_message = message;
        }
        if(buttonText != null && buttonText != "")
        {
           invest_buttonText = buttonText;
        }
        if(investSubject != null && investSubject != "")
        {
           invest_subject = investSubject;
        }
        var html = "";
        html += '<div class="submit_div" id="investigatDiv" style="display:none;width:348px;height:175px;">';
        html += '<div class="submit_title">';
        html += '<div class="left_title">' + invest_promptInfo + '</div>';
        html += '<div class="tips_close"><a href="#" class="div_close"><img onclick="closeInvestigatDiv()" src="' + itpInvestigatPath + 'close.gif" alt="关闭" border="0"/></a></div>';
        html += '</div>';
        html += '<div class="submit_icon"> <img src="' + itpInvestigatPath + 'tips_right.gif"/> </div>';
        html += '<div class="submit_content">' + invest_message + '</div>';
        html += '<div class="submit_operate">';
        html += '<div style=" width:70px;margin:0 auto;">';
        html += '<a href="#" class="link" id="investigatDivClose" onclick="closeInvestigatDiv()">' + invest_buttonText + '</a>';
        html +=  '</div>';
        html += '</div>';
        html += '<div class="clear"></div>';
        html += '<table cellspacing="0" cellpadding="0" class="tb_invest">';
        html += '<tr>';
        html += '<td colspan="4" class="title" nowrap><a href="#" onclick="feedback()" class="fb_' + invest_language.replace("-","_") + '">' + invest_subject + '</a></td>';
        html += '</tr>';
        html += '<tr style="display:none" id="feed_list" class="tr_top">';
        
        html += '<td>';
        html += '<table cellspacing="0" cellpadding="0" width="100%" border="0">';
        html += '<tr>';
        html += '<td colspan="4">' ;
        html += '<iframe scrolling="no" id="invest" width="310px" height="210px" src="'+ src + '" frameborder="0"></iframe>'
        html += '</td>';
        html += '</tr>';
        html += '<tr>';
        html += '<td colspan="4" style="padding-left:110px;padding-bottom:10px;">';
    		html += '<a href="#" class="link" id="yes" onclick="invokeChild()">' + invest_submitBtnText + '</a>';
    		html += '</td>';
        html += '</tr>';
        html += '</table>';
        html += '</td>';
        
        html += '</tr>';
        html += '</table>';
        html += '</div>';
        
        labelObj.outerHTML = html;
    }
}


//动态加载样式表
function itp_css_include(cssUrl) 
{
    var link = document.createElement("link")
    link.rel = "stylesheet"; 
    link.type = "text/css"; 
    link.href = cssUrl; 
    var head = document.getElementsByTagName('head').item(0);
    head.appendChild(link);
}

//动态加载JS
function itp_js_include($script) 
{
    var script = document.createElement('script');
    script.src = $script;
    script.type = 'text/javascript';
    var head = document.getElementsByTagName('head').item(0);
    head.appendChild(script);
}

function janover(obj)
{
    obj.className="invest_title_ver_hover_" + invest_language.replace("-","_");
}

function janout(obj)
{
    obj.className="invest_title_ver_" + invest_language.replace("-","_");
}

function janoverTop(obj)
{
    obj.className="invest_title_hor_hover_" + invest_language.replace("-","_");
}

function janoutTop(obj)
{
    obj.className="invest_title_hor_" + invest_language.replace("-","_");
}

function ShowOrHideDIV()  {
    if($("#investList:hidden").length) {
        ShowDIV();
    }
    else {
        HideDIV();
    }
}

function ShowDIV() {
    var investDiv = $("#invest_div");
    investDiv.css('width', '372px');
    $("#investList").show("normal");
}

function HideDIV() {
    $("#investList").hide("normal", function() {
        $("#invest_div").css('width', '24px');
    });
}

function itpInvestigateCtrlShowAlert()
{
    invest_isAlert = true;
    if(invest_isInit)
    {	
    	$.blockUI({ message: $('#investigatDiv'), css: { width: '350px',height:'162px' } });
    }
}

function closeInvestigatDiv()
{
    $.unblockUI();
    //提交完成以后，调用配置的回调方法
    if(invest_callBackMethodName != "")
    {
       eval(invest_callBackMethodName + "()");
    } 
    return false; 
}

function feedback()
{
    if(document.getElementById("feed_list").style.display=="none")
    {    
        document.getElementById("feed_list").style.display="";
        document.getElementById("investigatDiv").style.height="420px";
    }
    else
    {    
        document.getElementById("feed_list").style.display="none";
        document.getElementById("investigatDiv").style.height="175px";
    }
}
    
$(document).ready(
    function() 
    {
        $(document).click(function(event) 
        {
            if (event.target.id == "investList") 
            {
                ShowDIV();
                event.stopPropagation();
            } 
            else if (event.target.id == "clickdiv") 
            {
                ShowOrHideDIV();
                event.stopPropagation();
            }
            else 
            {
                HideDIV();
            }
        });
    }
);

function invokeChild()
{    
    document.getElementById("invest").src = document.getElementById("invest").src + "#childSubmit";
    alert(invest_successInfo);
    if(invest_type == "showOnRight")
    {
        HideDIV();
    }
    else if(invest_type == "showAfterSubmit")
    {
        closeInvestigatDiv();
    }
}

//多语言
var invest_Resources = {
    "en_US":{
        submitBtnText : "Submit",
        cancleBtnText : "Cancel",
        promptInfo : "Message",
        message : "Saved successfully!",
        buttonText : "Close",
        subject : "Your use experience",
        successInfo : "Saved successfully!",
        warnInfo : "You have submitted!"
    }, 

    "zh_CN":{
        submitBtnText : "提交",
        cancleBtnText : "取消",
        promptInfo : "提示信息",
        message : "您提交的信息保存成功!",
        buttonText : "关闭",
        subject : "说说您的使用感受",
        successInfo : "提交成功!",
        warnInfo : "你已经提交过了!"
    }
};

//获取多语资源
function getInvestResource(vName)
{
    return eval("invest_Resources." + invest_language.replace("-","_") + "." + vName);
}
function investRoll()
{
   var invest_div = document.getElementById("invest_div");
   if(invest_div != null)
   {
       invest_div.style.top = window.document.body.scrollTop + 200;
   }
}