﻿// JScript 文件
		var movx;
		function movstar(a,time)
		{ 
			if(movx)
			{
				clearInterval(movx); 
			}
			movx=setInterval("mov("+a+")",time);
		}
		function movover(){ 
			clearInterval(movx); 
		}
		function mov(a)
		{ 
			scrollx=news.document.body.scrollLeft; 
			scrolly=news.document.body.scrollTop; 
			scrollx=scrollx+a; 
			news.window.scroll(scrollx,scrolly); 
		} 

    	function gomainpage()
    	{
    	    var top;
    		javascript:window.top.mainFrameSet.cols = '0,*';
    		//window.parent.rightup.document.all('showtoc').style.display = 'none';
    		top.rightup.document.all("showtoc").style.display = "block";
    		top.right.location.href='NavigationBar.aspx';
    	}
    function OpenWin(url)
    {
        //统一打开新窗口方法
        //winioffice=window.open(url,"","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,dependent,height=340, width=510,left=0,top=0");
        //winioffice.moveTo((screen.availWidth-510)/2,(screen.availHeight-340)/2);
        if(url == "")
        {
			return;
        }
        window.open(url,"","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,dependent,width="+(screen.availWidth-300)+",height="+(screen.availHeight-300)+",left=0,top=0")
    }