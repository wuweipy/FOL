<!-- 

  function do_recorder_change() {
    //is_recorder_change = 1;
    document.Form1.hiRecorderChange.value = "1";
  }

  //增加页面处理数据事件
  for (i=0;i<document.all.length;i++) {
    //if (document.all[i].name=="C1" || document.all[i].name=="R1")
    //  document.all("textresult").value += 'tagName:'+document.all[i].tagName+'a\n'; 
    
	//判断哪些页面控件需要处理数据改变
    if (document.all[i].tagName=="INPUT" || document.all[i].tagName=="TEXTAREA"
     || document.all[i].tagName=="SELECT") {
	  //alert(document.all[i].onpropertychange);
	  if (document.all[i].onchange == null && document.all[i].onpropertychange == null) {  //没有onchange事件增加onchange事件
        document.all[i].onchange = do_recorder_change;      
      }
    }
  }
//--> 
