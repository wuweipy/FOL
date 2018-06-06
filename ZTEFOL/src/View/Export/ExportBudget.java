package View.Export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import Data.DBHandler.DBConnectorFactory;
import Common.CourseMngt;
import jxl.Cell;
import jxl.NumberCell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportBudget extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	public static String downloadPath = "";

	public void init(ServletConfig config) throws ServletException {
		downloadPath = config.getServletContext().getRealPath("/")
				+ "download/";
		super.init(config);
	}
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String ms_code = "budget";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String myarray = request.getParameter("myArray");
		String colums = request.getParameter("colums");
		String rows = request.getParameter("rows");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		boolean isSales = Boolean.parseBoolean(request.getParameter("isSales"));
		String dateNo = getDateNoBySelect(year,month);
		myarray = myarray.replaceAll("\t", "");
		myarray = myarray.replaceAll("\b", "");
		myarray = myarray.replaceAll("\n", "");
		int status = Integer.valueOf(request.getParameter("status"));
		/* 生成表单开始 */
		exportExcel(downloadPath+ms_code+".xls",ms_code,myarray,colums,rows,dateNo,status,isSales);
		/* 生成表单结束 */
		
		ms_code = new String(ms_code.getBytes("utf-8"));
		File source = new File(downloadPath + ms_code + ".xls");
		response.reset();
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ source.getName() + "\"");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write("budget.xls");
	}

	public static void exportExcel(String fileName, String ms_code,String myArray,String colums,String rows,String dateNo, int status,boolean isSales) {
		WritableWorkbook wwb;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);
			int col = Integer.valueOf(colums);
			// 创建一个工作表
			WritableSheet ws = wwb.createSheet(ms_code, 10);
			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, col,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);

			// 填充数据的内容
			StringBuffer sb = new StringBuffer();
			String[] dates = myArray.split(",");

			// 创建表头     单据号,报销部门,报销人,所属项目,提交日期,单据类型,审批状态,审批结束日期,票据号码,摘要,申请金额,审核金额
			for (int i = 0; i < Integer.parseInt(colums); i++) {
				ws.addCell(new Label(i, 0, dates[i], wcf));
			}

			// 填充数据
			for (int i = 0; i < Integer.parseInt(rows)-1; i++) {
				for(int j =0;j<Integer.parseInt(colums);j++){
					ws.addCell(getCell(j,i + 1, dates[(i+1)*col+j],wcf));
				}			
			}
			
//			if ( isSales == false )
//			{
//				HashMap<String,String> whBudgetMap = new HashMap<String, String>();
//				HashMap<String,String> shBudgetMap = new HashMap<String, String>();
//				getBudgetMap(whBudgetMap,shBudgetMap,dateNo,status);
//				ws.addCell(new Label(col, 0, "武汉研究所资金预算提报依据", wcf));
//				ws.addCell(new Label(col+1, 0, "上海资金预算提报依据", wcf));
//				for(int i=1;i<Integer.parseInt(rows)-1;i++)
//				{
//					String cos = dates[i*col].replaceAll(" ","");
//					String whdetail = whBudgetMap.get(cos);
//					String shdetail = shBudgetMap.get(cos);
//					ws.addCell(getCell(col, i, whdetail, wcf));
//					ws.addCell(getCell(col + 1, i, shdetail, wcf));
//				}
//			}
			
			wwb.write();
			wwb.close();
		} catch (IOException e) {
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
		}
	}
	
	private static WritableCell getCell(int row, int col ,String value,WritableCellFormat format)
	{
		value = value.trim();
		try {
			return new jxl.write.Number(row, col, Integer.parseInt(value), format);
		} catch (Exception e) {
			return new Label(row, col, value, format);
		}
	}
	
	public static void getBudgetMap(HashMap<String, String> whBudgetMap, HashMap<String, String> shBudgetMap,String dateNo, int status){
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return;
		}
		Statement statement = null;
		ResultSet rs = null;
    	HashMap<Integer, String> courseMap = CourseMngt.getInstance().getCourseMap();
    	Iterator<Integer> iterator = courseMap.keySet().iterator();
		try 
		{			
			statement = connection.createStatement();
	  	    while(iterator.hasNext()) 
	  	    { 
	  	    	int key = iterator.next();
	  	    	String course = courseMap.get(key);			
			    rs = statement.executeQuery("SELECT c.USERNAME,d.institute,d.name ,description FROM BUDGETDETAIL a,BUDGETINFO b,USERINFO c,DEPT d WHERE a.budgetId=b.budgetId and b.budgetId like '%" + 
			    		dateNo + "%' and courseName='" + course + "' and b.status="+ status+ " and b.no=c.NO and d.id=c.DEPTID");
				String wuhan = "";
				String shanghai = "";
			    while (rs.next()) 
				{
					if(!rs.getString("description").equals("-"))
					{
						if(rs.getInt("institute")==1)
						{
							if(rs.getString("name").equals("销售")){
								wuhan = wuhan + rs.getString("name") + rs.getString("USERNAME")+ "：" + rs.getString("description") + "，";
							}
							else{
								wuhan = wuhan + rs.getString("name")+ "：" + rs.getString("description") + "，";
							}																					
						}
						else
						{
							if(rs.getString("name").equals("销售")){
								shanghai = shanghai + rs.getString("name") + rs.getString("USERNAME")+ "：" + rs.getString("description") + "，";
							}
							else{
								shanghai = shanghai + rs.getString("name")+ "：" + rs.getString("description") + "，";
							}
						}
					}			
			    }	
			    if(!wuhan.equals("")){
			    	whBudgetMap.put(course,wuhan.substring(0,wuhan.length()-1));
			    }
			    else
			    {
			    	whBudgetMap.put(course,wuhan);
			    }
			    if(!shanghai.equals("")){
			    shBudgetMap.put(course,shanghai.substring(0,shanghai.length()-1));
			    }
			    else
			    {
			    	shBudgetMap.put(course,shanghai);
			    }
	  	    }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}		
	}
	
	private String getDateNoBySelect(String year, String month) {
		// TODO Auto-generated method stub
		DecimalFormat ndf = new DecimalFormat("00");
        return year + ndf.format(Integer.valueOf(month));
	}

}
