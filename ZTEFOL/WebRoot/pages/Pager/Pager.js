(function(window)
{
		 var defaults = {  
			firstArrow : "Pager/images/first.gif",  
			prevArrow : "Pager/images/prev.gif",
			lastArrow : "Pager/images/last.gif",
			nextArrow : "Pager/images/next.gif",
			rowsPerPage : 10,
			currPage : 1,
			optionsForRows : [5,10,25,50,100]
		};
		
		var Pager = function(table_id)
    {
    	var callback = function(){};
      var table = document.getElementById(table_id);
      var totalPagesId, currPageId, currPageID, rowsPerPageId, firstPageId, prevPageId, nextPageId, lastPageId;
      totalPagesId = 'tablePagination_totalPages' + table_id;
      currPageId = 'tablePagination_currPage'  + table_id;
      currPageID = 'currPageNumberdi'  + table_id;      
      rowsPerPageId = 'tablePagination_rowsPerPage' + table_id;
      firstPageId = 'tablePagination_firstPage'  + table_id;
      prevPageId = 'tablePagination_prevPage'  + table_id;
      nextPageId = 'tablePagination_nextPage'  + table_id;
      lastPageId = 'tablePagination_lastPage'  + table_id;
      
      var tableRows = (function(){
      	var arr = [],
      	    tablerows = table.rows,
      	    igonre = document.getElementById(arguments[0]);
      	for(var i = 0,length = tablerows.length; i < length; i++)
      	{
      		 if(tablerows[i] != igonre)
      		     arr.push(tablerows[i]);
      	}
      	return arr;
      	})(arguments[1]);
      
      var totalPages,defaultNum = defaults.rowsPerPage;
      var currPageNumber = 1;
      var currPageNumberdi =1;
      
      function init()
      {
      	 totalPages = Math.ceil(tableRows.length / defaultNum );
      }
      
      function setDefaultNum(num)
      {
     	   defaultNum = num;
     	   init();
     	   resetCurrentPage(1);
      }
      
      function hideOtherPages(pageNum)
      {
     	  if (pageNum == 0 || pageNum > totalPages)
           return;
        var startIndex = (parseInt(pageNum) - 1) * defaultNum;
        var endIndex = (parseInt(startIndex) + parseInt(defaultNum) - 1);
        show(tableRows);
        for (var i = 0; i < tableRows.length; i++) 
        {
          if (i < startIndex || i > endIndex) 
          {
            tableRows[i].style.display = "none";
          }
        }
     }
     
     function show(tableRows)
     {
     	 for(var i = 0; i < tableRows.length; i++)
       {
     	    tableRows[i].style.display = "";	
     	 }
     }
     
      function resetCurrentPage(currPageNum) 
      {
        if (currPageNum < 1 || currPageNum > totalPages)
          return;
        currPageNumber = currPageNum;
        document.getElementById(currPageId).value = currPageNum;
        document.getElementById(currPageID).innerHTML = currPageNum;
        hideOtherPages(currPageNum);
        callback(currPageNum);
      }
      
      init();
    
      function createPaginationElements() 
      {
        var htmlBuffer = [];
        htmlBuffer.push("<div id='tablePagination'>");
        htmlBuffer.push("<span>");
        htmlBuffer.push("共" + tableRows.length + "条记录");
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span margin:0>");
        htmlBuffer.push("第");
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span margin:0 id='" + currPageID + "'>");
        htmlBuffer.push(currPageNumberdi);
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span>");
        htmlBuffer.push("页/");                      
        htmlBuffer.push("共");
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span margin:0 id='totalPages'>");
        htmlBuffer.push(totalPages);
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span>");
        htmlBuffer.push("页");
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span id='tablePagination_perPage'>");
        htmlBuffer.push("每页 ");
        htmlBuffer.push("<select id='" + rowsPerPageId + "'><option value='5'>5</option></select>");
        htmlBuffer.push(" 行");
        htmlBuffer.push("</span>");
        htmlBuffer.push("<span id='tablePagination_paginater'>");
        htmlBuffer.push("<img id='" + firstPageId + "' src='"+defaults.firstArrow+"'>");
        htmlBuffer.push("<img id='" + prevPageId + "' src='"+defaults.prevArrow+"'>");
        htmlBuffer.push("<input id='" + currPageId + "' type='input' value='"+currPageNumber+"' size='1'>");
        htmlBuffer.push("<span id='tablePagination_totalPages'>" + "</span>");
        htmlBuffer.push("<input type='button' value='GO' id='currPageId'>");
        htmlBuffer.push("<img id='" + nextPageId + "' src='"+defaults.nextArrow+"'>");
        htmlBuffer.push("<img id='" + lastPageId + "' src='"+defaults.lastArrow+"'>");
        htmlBuffer.push("</span>");
        htmlBuffer.push("</div>");
        return htmlBuffer.join("").toString();
      }
      
      var pageElement = document.createElement("div");
      pageElement.id = "pagerDiv";
      pageElement.innerHTML = createPaginationElements();
      table.parentNode.insertBefore(pageElement,table.nextSibling);
      
      var dropdown = document.getElementById(rowsPerPageId);
      var optionsForRows = defaults.optionsForRows;
      for (var i = 0; i < optionsForRows.length; i++) 
      {
      	 dropdown.options[i] = new Option(optionsForRows[i], optionsForRows[i], true, true);
      }
      dropdown.value = defaults.rowsPerPage;
      
      document.getElementById(firstPageId).onclick = function (e) {
        resetCurrentPage(1)
      };
      
      document.getElementById(prevPageId).onclick = function (e) {
        resetCurrentPage(currPageNumber - 1)
      };
      
      document.getElementById(nextPageId).onclick = function (e) {
        resetCurrentPage(parseInt(currPageNumber) + 1)
      }; 
      
      document.getElementById(lastPageId).onclick = function (e) {
        resetCurrentPage(totalPages)
      };       
      
      var changePageSize = function(){};
      document.getElementById(currPageId).onchange = function (e) {
        resetCurrentPage(this.value);
      }; 
      
      document.getElementById(rowsPerPageId).onchange = function (e) {
        setDefaultNum(parseInt(this.value, 10));
        document.getElementById("totalPages").innerHTML = totalPages;
        changePageSize();
      };
      
      setDefaultNum(defaults.rowsPerPage); 
      
      /***
      * 当前页所有数据的迭代处理函数
      */
      this.currentRowEach = function(func){
         var startIndex = (parseInt(currPageNumber) - 1) * defaultNum;
         var endIndex = Math.min(parseInt(startIndex) + parseInt(defaultNum),tableRows.length); 
         for(var i = startIndex; i < endIndex; i++)
         {
         	  func(i);
         }
      };
      
      /***
      * 当前页处理函数
      */
      this.currentRow = function(func){
      	 func(currPageNumber);
      }; 
      
      /***
      * 注册改变页大小时回调函数
      */
      this.registerPageSize = function(func){
         changePageSize = func;
      };
      
      /***
      * 注册翻页时回调函数
      */
      this.registerChangePage = function(func){
         callback = func;
      };        
    };

   window.Pager = Pager;
})(window);