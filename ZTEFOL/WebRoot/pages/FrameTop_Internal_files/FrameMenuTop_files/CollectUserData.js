
var collectPolicy = "";
var serviceUri = "";
var VirtualPath = "";
var RootVirtual = false;
var getPloicyXmlHttp = GetXmlHttpRequestObject();
var xmlhttp = GetXmlHttpRequestObject();

GetCollectPolicy();

function GetXmlHttpRequestObject() 
{
	if (window.XMLHttpRequest) 
	{
		return new XMLHttpRequest();
	} 
	else if (window.ActiveXObject) 
	{
		return new ActiveXObject("Microsoft.XMLHTTP");
	} 
	else 
	{
		alert( 'Status: Cound not create XmlHttpRequest Object.Consider upgrading your browser.');
	}
}

function GetVpath()
{
	if (VirtualPath == undefined || VirtualPath == "" || VirtualPath == null)
	{ 	
		var pot = location.port;
		if (pot=="")
		{
			pot = "80";
		}
		
		if(RootVirtual)
		{
			if(pot==80)
			{
				VirtualPath = location.href.substr(0,location.href.indexOf("//")+2) + "/";
			}
			else
			{
				VirtualPath = location.href.substr(0,location.href.indexOf("//")+2) + location.hostname + ":" + pot + "/";
			}
		}		
		else
		{
		    //virutal path must be a full path ,otherwise it will not work because of access right
		    var pathname = location.pathname;
		    var arr = pathname.split("/");
			if(pot=="80")
			{
				VirtualPath = location.href.substr(0,location.href.indexOf("//")+2) + location.hostname + "/";
			}
			else
			{
				VirtualPath = location.href.substr(0,location.href.indexOf("//")+2) + location.hostname + ":" + pot + "/";
			}
		    if (arr.length > 2)
		    {
				if(arr[1].toUpperCase() == "UILOADER")
				{
					if(pot=="80")
					{
						VirtualPath = location.href.substr(0,location.href.indexOf("//")+2) + location.hostname + "/";
					}
					else
					{
						VirtualPath = location.href.substr(0,location.href.indexOf("//")+2) + location.hostname + ":" + pot + "/";
					}
					
				}
				else
				{
					VirtualPath = VirtualPath + arr[1] + "/";
				}
		    }
		}
	}
	
	return VirtualPath;
}

function GetCollectPolicy()
{	
	if (getPloicyXmlHttp)
     {
		var path = GetVpath() + "CollectPolicy.xml";
								     	
		getPloicyXmlHttp.open("get", path, true);
		getPloicyXmlHttp.onreadystatechange = GetCollectPolicyCallback;
		getPloicyXmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		getPloicyXmlHttp.send(null);
	}
}

function GetCollectPolicyCallback()
{
  if (getPloicyXmlHttp.readyState == 4) 
	{
	  if (getPloicyXmlHttp.status == 200)
	  {		
		var responseXml = getPloicyXmlHttp.responseXML;	
		if (responseXml == null) return;
		var appSetings = responseXml.getElementsByTagName('configuration')[0].childNodes[0];
		
		for (var i=0; i<appSetings.childNodes.length; i++)
		{
		   var add = appSetings.childNodes[i];
		   
		   if (add.nodeType ==1 && add.getAttribute('key') == 'CollectPolicy')
		   {
		      var policy = add.getAttribute('value');
		      collectPolicy = policy;
		   }
		   else if (add.nodeType ==1 && add.getAttribute('key') == 'CollectServerURI')
		   {
		   	 serviceUri = add.getAttribute('value');
		   	 if (serviceUri.indexOf("http") < 0)
		   	 {
		   		var arr = serviceUri.split("/");
		   		if(arr.length > 2)
		   		{
		   			serviceUri = GetVpath() + arr[2];
		   		}
		   		else
		   		{
		   			serviceUri = GetVpath() + arr[1];
		   		}
		   	 }
		   }
		}
	  }
	}
}

function SendCollectData(postData, uri) 
{
	if (xmlhttp)
     {
		xmlhttp.open("post", uri, true);
		xmlhttp.onreadystatechange = SendCollectDataCallback;
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlhttp.send(postData);
	}
}

function Send(menuId, userId, menuLevel, uri)
{
	var postData = menuId + ',' + userId + ',' + '' + ',' + '';

	if (null == collectPolicy || ''  == collectPolicy)
	{
		SendCollectData(postData, uri);
	}
	else
	{
		if (collectPolicy.indexOf(menuLevel) >= 0)
		{
			SendCollectData(postData, uri);
		}
	}
}

function PostData(menuId, userId, menuLevel)
{
	var uri = serviceUri;
	if (uri == "" || uri == '')
	{
		uri = GetVpath() + "UserInfoCollectServlet.uic";
	}
	
	Send(menuId, userId, menuLevel, uri);
}

function SendCollectDataCallback()
{
	if (xmlhttp.readyState == 4) 
	{
		var response = xmlhttp.responseText;	
	}
}