function NavigateClick(ShowValue, topMenuData) 
{
    setTopMenuStyle(ShowValue);
    onMenuClick(ShowValue, topMenuData);
    showLeftMenu();
}

function NavigateClick2(ShowValue, topMenuData) 
{
    setTopMenuStyle(ShowValue);
    onMenuClick2(ShowValue, topMenuData);
}

function onMenuClick(ShowValue, topMenuData)
{
    // 执行菜单点击事件
    for(var i=0;i< topMenuData.length; i++)
    {
        if(topMenuData[i][0] == ShowValue)
        {
	        top.leftup.location.href= 'FrameLeftMenu.aspx?TopMenuID='+ topMenuData[i][0];
	        if(topMenuData[i][1] != "")
	        {
		        top.right.location.href= '../Redirect.aspx?pageCode=' + topMenuData[i][1] + 
		            '&menuId=' + topMenuData[i][0]; 
	        }
        }
    }
}

function onMenuClick2(ShowValue, topMenuData)
{
    // 执行菜单点击事件
    for(var i=0;i< topMenuData.length; i++)
    {
        if(topMenuData[i][0] == ShowValue)
        {
	        top.fourMenuLink.location.href = '../FourMenuLink.aspx?TopMenuID='+ topMenuData[i][0] + 
	            '&pageCode=' + topMenuData[i][1] + '&hasFourMenu=' + topMenuData[i][2];
	        
//	        if(topMenuData[i][1] != "")
//	        {
//		        top.right.location.href= '../Redirect.aspx?pageCode=' + topMenuData[i][1] + 
//		            '&menuId=' + topMenuData[i][0]; 
//	        }
        }
    }
}

function setTopMenuStyle(ShowValue)
{
    PrevSelectedValue =  document.all("PrevSelectedValue").value;
    
    if (PrevSelectedValue != ShowValue)
    {
        var trs = document.getElementsByTagName("td");
        //清除以前的选中显示
        if (PrevSelectedValue != "")   // 第一次刷新页面时候PrevSelectedValue为空
        {
	        var preSelectedTd = "tdmenu" + PrevSelectedValue;
	        document.getElementById(preSelectedTd).className  = "tab";
			
	        for(var i=0;i <trs.length;i++)
	        {
		        if(trs.item(i).id == preSelectedTd)
		        {
				    trs.item(i-1).className  = "vline";
			        trs.item(i+1).className = "vline";
		        }
	        }
			
        }
       
        
        //高亮当前的显示
        var showTd = "tdmenu" + ShowValue;
        document.getElementById(showTd).className = "active";
        for(var i=0;i <trs.length;i++)
        {
            if( trs.item(i).id == showTd )
            {
                trs.item(i-1).className  = "";
                trs.item(i+1).className  = "";
            }
        }
        
        document.all("PrevSelectedValue").value = ShowValue;
    }
}
function showLeftMenu()
{
    if (window.top.mainFrameSet.cols == "0,*")
    {
       // 如果第一次进入菜单框架不需要默认显示某一级菜单时加上条件if(IsFirstTime == false)
       //if(IsFirstTime == false)
       //{
	        window.top.mainFrameSet.cols = "145,*";
	        top.rightup.document.all("showtoc").style.display = "none";
       //}
    }
}

//点击率顶层菜单
function onTopMenuClick(ShowValue, topMenuData, userId)
{
    // 执行菜单点击事件
    for(var i=0;i< topMenuData.length; i++)
    {
        if(topMenuData[i][0] == ShowValue)
        {
	       PostData(topMenuData[i][0],userId,1);	        
        }
    }
}

//点击率顶层菜单
function onTopMenuClick2(ShowValue, topMenuData, userId)
{
    // 执行菜单点击事件
    for(var i=0;i< topMenuData.length; i++)
    {
        if(topMenuData[i][0] == ShowValue)
        {	        
	        PostData(topMenuData[i][0],userId,1);
        }
    }
}
