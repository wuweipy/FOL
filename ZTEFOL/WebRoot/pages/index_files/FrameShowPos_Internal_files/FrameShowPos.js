﻿// JScript 文件
    function displaybutton()
    {
        //判断是否显示显示Frame的按钮图标
        //var menuflag = top.mainFrameSet.cols;
        var menuflag = 0;
        if (menuflag == "0,*")
        {
            document.all("showtoc").style.display = "block";
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    //函数名称： showPageTable
    //功能说明： 显示内容页面的表格或者隐藏表格
    //参数定义： id -- 1 表示显示 0 表示隐藏表格
    //关联关系： 和框架parent.right里面的javascript函数ShowTable关联
    //开发人员： 刘建林
    //完成日期： 2005-03-28
    //////////////////////////////////////////////////////////////////////////////
    function showPageTable(id)
    {
        var ImgPlusScr ="images/icon_expandall.gif"	;      	// Plus  +
        var ImgMinusScr ="images/icon_collapseall.gif"	;	    // Minus - 
        
        var imgCtrls = top.right.document.getElementsByTagName("img");
        var i ;
        
        for(i=0;i <imgCtrls.length;i++)
        {
            // id =1 显示所有表单
            if(imgCtrls.item(i).src.indexOf("icon_expandall") != -1 && id == 1)
            {
                top.right.ShowTable(rightObj.document.getElementById(imgCtrls.item(i).id));
            }
            
            // id =0 显示隐藏表单
            if(imgCtrls.item(i).src.indexOf("icon_collapseall") != -1 && id == 0)
            {
                top.right.ShowTable(rightObj.document.getElementById(imgCtrls.item(i).id));
            }
        }
    }
    function hisBack(obj)
	{
	        var list = new Array("BoeExpenseSubmit.aspx","AdminApprovalPlan.aspx"
			,"FinanceApprovalDetail.aspx","ActiveReason.aspx","AdjustTo.aspx","LoanRepaymentEdit.aspx"
			,"");
	   try{
			if (obj.document.all('frmMain') != undefined)
			{
				//debugger;

				var frameUrl="";
				if(obj.document.all('frmMain').rows == '0,*')
				{
					frameUrl = obj.frames[1].location;
				}
				else if(obj.document.all('frmMain').rows == '*,0')
				{
					frameUrl = obj.frames[0].location;
				}

                 //页面返回
			      for(var i=0;i<list.length;i++)
                  {
				     if(frameUrl.toString().indexOf(list[i]) > -1)
				     {
					     if(obj.document.all('frmMain').rows == '0,*')
				         {
					          obj.frames[1].history.back();
							  return;
				         }
				         else if(obj.document.all('frmMain').rows == '*,0')
				         {
					          obj.frames[0].history.back();
							  return;
				         }
				      }//end if
			       }//end for

				    if(obj.document.all('frmMain').rows == '0,*')
				    {
					    //obj.ShowPage(1,'');
						if(obj.document.all('frmMain') != null)
						{
						   obj.document.all('frmMain').rows="*,0";
						}
				    }//end if
				    /*else if(obj.document.all('frmMain').rows == '*,0')
				    {
					     //obj.ShowPage(0,'');
						if(obj.document.all('frmMain') != null &&  obj.document.all('frame2') != null)
						{
						  obj.document.all('frmMain').rows="0,*";	
					      obj.document.all('frame2').src=url;
						}
				    }*/
			}//end if (obj.frames != undefined)
			else
		    {
			    obj.history.back();
		    }
	  }
	catch(e){}
		/*alert(urlRep);
		if(urlRep.indexOf("Frame.aspx") > -1)
		{
			alert('2222');
			if(obj.ShowPage != null)
			{
				obj.ShowPage(1,'');
			}
		}
		else
		{
			alert('3333');
			obj.history.back();
		}*/
		 //return false; 
	}
