(function(window)
{
	var AllCheck = function(checkBoxId,Pager,func)
	{
		 var checkBox = document.getElementById(checkBoxId);
		 var isChecked = [];
		 checkBox.onclick = function()
		 {
		 	  Pager.currentRowEach(func);
        Pager.currentRow(function(num){isChecked[num] = checkBox.checked ? 1 : 0;});
		 };
		 
		 Pager.registerPageSize(function(){
		 	  isChecked = [];
		 	  Pager.currentRow(function(num){checkBox.checked = false});
		 	});
		 	
		Pager.registerChangePage(function(i){
		   checkBox.checked = (isChecked[i] == 1);
	  });
		 
	}
	window.AllCheck = AllCheck;
})(window);