<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <title>
        ERP-FOL 财务在线
    </title>
    <link title="mainStyle" media="screen" href="pages/Index/css/login.css" type="text/css"
        rel="stylesheet">
    <script language="JavaScript" src="pages/jquery.js"></script>  
    <script language="JavaScript" src="pages/jquery.cookie.js"></script>   
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <script language="JavaScript">
        $(function(){
           var name = $.cookie('username');
           if(name)
           {
              $('#userId').val(name);
           }
        });
        
		if(window.top != window)
		{
			window.top.location.href= "login.jsp";
		}		
		function LoadPage()
		{
			var loginErrorType = divErrorType.innerHTML;
			if(loginErrorType == "userId")
				document.frmLogin.userId.focus();
			else if(loginErrorType == "password")
				document.frmLogin.password.focus();			
		}	
        function helpUnvalid()
        {
           document.getElementById("help1").innerHTML = "帮助";    
        }		
			
    </script>

    <style> 
    
#container1 {
	PADDING-RIGHT: 0em; 
	PADDING-LEFT: 0em;
	MARGIN-TOP: 120px;
	margin-left: auto;
	margin-right: auto; 
  BACKGROUND: url(pages/Index/images/login_bg.png) #013d83 no-repeat; 
  PADDING-BOTTOM: 0px; WIDTH: 520px; PADDING-TOP: 0px; 
  POSITION: relative; HEIGHT: 350px; TEXT-ALIGN: left

}
.box 
{ 
border: 1px solid #59aeff; 
width: 76px; 
height: 20px; 
clip: rect( 0px, 181px, 20px, 0px ); 
overflow: hidden; 
} 
.box2 
{ 
border: 1px solid #F4F4F4; 
width: 74px; 
height: 18px; 
clip: rect( 0px, 179px, 18px, 0px ); 
overflow: hidden; 
} 
select 
{ 
position: relative; 
left: -2px; 
top: -2px; 
width: 76px;
background-color: #EFEFEF; 
line-height: 14px; 
border-style: none; 
border-width: 0px;
FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif;
Font-size:11
} 
    </style>
</head>
<body>
    <div id="divErrorType" style="display: none">
    </div>

    <script language="JavaScript">
        function keyPressInUser() {
	        var keyValue;
	        keyValue=window.event.keyCode;
	        if(keyValue==13)
	        {
	            document.getElementById("password").focus();
	            //submitForm();
	        }
        }

        function keyPressInPassword() {
	        var keyValue;
	        keyValue=window.event.keyCode;
	        if(keyValue==13) //document.frmLogin.Login.onclick();
	          submitForm();
        }

        function submitForm() {
            $.cookie('username',$('#userId').val());
	        document.getElementById("Button1").onclick();
        }
        
	    //region: 增加的脚本，用来判断6位工号的输入时的提示信息
        function check()
        {
          var userObj = document.getElementById('userId');
          var divMess = document.getElementById('divMess');
          //var strCulture = document.getElementById("cultureName").value;
          if (Trim(userObj.value).length == 6 || Trim(userObj.value).length ==14)
          {
            if (strCulture =="en-US")
            {
                divMess.innerHTML = "The employee ID have been upgraded,Please use the 8 digists of your new employee ID to login.old ID：188888,new ID：1<font color='black'>00</font>88888";
            }
            else
            {
                divMess.innerHTML = "工号已升位，请使用8位新工号登录<br/>如原工号：188888，新工号：1<font color='black'>00</font>88888";
            }
            return false;
          }
          //document.loginForm.submit();
           $.cookie('username',$('#userId').val());
          document.getElementById('loginForm').submit();
          return true;
        }
        function RTrim(str){
            if (str == '') return '';
            var index = -1;
            for(i = str.length; i > 0; i--){
                if (str.charAt(i - 1) != ' '){
                    index = i;
                    break;
                }                
            }
            if (index == -1) return '';
            return str.substring(0,i);
        }


        function LTrim(str){
            if (str == '') return '';
            var index = -1;
            for (i = 0; i < str.length; i++){
                var s = str.charAt(i);
                if (s != ' '){
                    index = i;
                    break;
                }
            }
            if (index == -1) return '';
            return str.substring(index,str.length);        
        }

        function Trim(str){
            return LTrim(RTrim(str));
        }
        //endregion: 增加的脚本，用来判断6位工号的输入时的提示信息      
               
        
    </script>

    <div id="container1">
        <h1 id="product">
            <span>系统服务总线</span></h1>
        <div id="version">
            V7.8.7
        </div>
     <form method="post" action="login.do" id="loginForm">
                <div id="usernamediv">
                    <span id="userNameString">用户名</span>
                    <input name="username" type="text" id="userId" onkeypress="keyPressInUser()" style="width: 110px" value="" />
                </div>
                <div id="passworddiv">
                    <span id="passwordString">密&nbsp;&nbsp;&nbsp;码</span>
                    <input name="password" type="password" id="password" onkeypress="keyPressInPassword()" style="width: 110px" />
                </div>
                <div id="loginbutton">
                    
                    <span id="help1" style="right: 84px; top: 128px; color:Silver;"></span>
                    <input onclick="javascript:return check();" name="Button1" type="submit" id="Button1" class="loginbutton" value="登录" />
                </div>
              </form>
               <!-- <div>
                    <br />
                    <hr align="right"/>
                </div>-->
                <% if(request.getAttribute("error") != null) { %>
                 <div class="alert-error">用户名或密码错误</div>
                <% }%>
                <div id="divMess" style="color: Red">
                </div>
                <div id="comment" style="position: absolute; right: 3em; bottom: 4.5em"><font color=red>提示：请用中兴思秸10位工号和密码登录.</font><br><font color='#0685ff'>如有问题，请联系网页维护人员.</font></div>
                <pre id="copyright1" style="left: 1.5em; width: 200px; bottom: 3.5em"><span id="copyright">&copy;2014 上海中兴思秸通讯有限公司</span></pre>
        </div>

        <script language="javascript">LoadPage();helpUnvalid();</script>
</body>
</html>