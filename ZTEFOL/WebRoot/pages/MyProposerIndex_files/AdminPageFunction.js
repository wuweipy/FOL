/*作　　者：李智勇  内容摘要：主要是系统管理页面的部分公用方法*/
// 设置所有的复选框是否选中
function setAllChecked()
{
	var chked = false;
	if(document.all("chk_SelAll")!=null)
	{
		chked = document.all("chk_SelAll").checked;
	}
	for(var i = 0 ; i < document.all.length ; i ++)
	{
		if (document.all(i).id.indexOf("chk_Sel") > 0)
		{
		    if (document.all(i).disabled == true)
		        continue;
		        
			document.all(i).checked = chked;
		}
	}
}

// 根据当前选择的CheckBox的ID,设置资源授权的相应控制选择
function childChecked(childid,itemCount)
{
	var tmpHead =childid.substring(0,15);
	var tmpid = childid.substring(15);
	var rowNo = parseInt(tmpid.substring(0,tmpid.indexOf("_")));
	var name  = tmpid.substring(tmpid.indexOf("_")+1);
	var chked = document.getElementById(childid).checked;
	var lbl   = document.getElementById(tmpHead+rowNo+"_lbl_Space").innerHTML;
	// 子级选中则父级必选中
	for(var i=rowNo;i>=0;i--)
	{
		var lblName = document.getElementById(tmpHead+i+"_lbl_Space").innerHTML;
		if(lbl != lblName && lbl.length > lblName.length )
		{
			if(chked)
			{
				document.getElementById(tmpHead+i+"_"+name).checked=chked;
			}
		}
		if(lblName.length < lbl.length)
		{
			lbl = lblName;
		}
		if(lblName=="")
		{
			break;
		}
	}
	lbl   = document.getElementById(tmpHead+rowNo+"_lbl_Space").innerHTML;
	var total = itemCount + 5;
	// 子级随父级
	for(var i=rowNo+1;i<=total;i++)
	{
		if(document.all(tmpHead+i+"_lbl_Space")!=null)
		{
			var lblName = document.getElementById(tmpHead+i+"_lbl_Space").innerHTML;
			if(lblName != "" && lbl != lblName && lblName.length > lbl.length)
			{
				document.getElementById(tmpHead+i+"_"+name).checked=chked;
				// 若查询的权限取消则增删改权限取消
				if(name == "chk_Query")
				{
					if(!chked)
					{
						document.getElementById(tmpHead+i+"_chk_Add").checked=chked;
						document.getElementById(tmpHead+i+"_chk_Update").checked=chked;
						document.getElementById(tmpHead+i+"_chk_Delete").checked=chked;
					}
				}
			}
			else
			{
				break;
			}
		}
	}// 结束For
	// 有增删改权限则有查询权限，无查询权限则无增删改权限
	if ( childid.indexOf("chk_Query") > 0 )
	{
		if ( !chked)
		{
			document.getElementById(tmpHead+rowNo+"_chk_Add").checked=chked;
			document.getElementById(tmpHead+rowNo+"_chk_Update").checked=chked;
			document.getElementById(tmpHead+rowNo+"_chk_Delete").checked=chked;
			//document.getElementById(tmpHead+rowNo+"_chk_Grant").checked=chked;
		}
	}
	else
	{
		if(chked)
		{
			document.getElementById(tmpHead+rowNo+"_chk_Query").checked=chked;
		}
	}
}