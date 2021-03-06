package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Common.CourseMngt;
import Common.DelegateMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Common.UserMngt;
import Common.PermitBudgetMngt;
import Data.DBHandler.DBConnectorFactory;
import Jmail.EmailHander;

public class BudgetFinanceServletB extends HttpServlet {

	private static Logger logger = FOLLogger.getLogger(TotalBudget.class);
	public int money;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	    HttpSession session = req.getSession();
		String userID = (String)session.getAttribute("userID");
		String action = req.getParameter("action");
		int roleId = UserMngt.getInstance().getUserInfoMap().get(userID).getRoleId();
		ArrayList<String[]> budgetfeeList1 = new ArrayList<String[]>(); //wuhan
		ArrayList<String[]> budgetfeeList2 = new ArrayList<String[]>(); //shanghai
		ArrayList<String[]> sumBudgetUsefeeList = new ArrayList<String[]>();  //zongde
		HashMap<Integer,Float> getTotalBudgetFee = new HashMap<Integer, Float>(); //heji
		ArrayList<Integer> projects = new ArrayList<Integer>();    //所有项目
		String startDate = "";
		String endDate = "";
		String dateNo = "";
		boolean flag = false;
		if(roleId==4)
		{
			String year = "";
			String month = "";
			if(action == null  || action.equals("submit"))
			{
				String[] date = getStartDate();
				year = date[0];
				month = date[1];
			}
			else if(action.equals("query") || action.equals("permit"))
			{
				year = req.getParameter("year");
				month = req.getParameter("month");
				if(year.equals(""))
				{
					year = getEndDate().substring(0,4);
				}
				if(month.equals(""))
				{
					month = getEndDate().substring(5,7);
				}
				if(action.equals("permit"))
				{
					flag = permitBudget(year , month , userID);	
					req.setAttribute("flag", flag);
				}
			}
			startDate = getStartDateBySelect(year,month);
			endDate = getEndDateBySelect(year,month);
			dateNo = getDateNoBySelect(year,month);
			
			getBudgetFee(budgetfeeList1,dateNo);
			getSumBudgetUsefeeList(sumBudgetUsefeeList,budgetfeeList1);
			getProject(budgetfeeList1,projects);
			getTotalBudgetFee(getTotalBudgetFee,dateNo);
			setooo(getTotalBudgetFee,projects);  //将没有的项设置为0
			req.setAttribute("budgetfeeList1", budgetfeeList1);
			req.setAttribute("sumBudgetUsefeeList", sumBudgetUsefeeList);
			req.setAttribute("getTotalBudgetFee", getTotalBudgetFee);
			req.setAttribute("projects", projects);
			req.setAttribute("projectMap", ProjectMngt.getInstance().getProjectMap());
			req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
			req.setAttribute("startDate", startDate);
			req.setAttribute("endDate", endDate);
			if(action == null || action.equals("query"))
			{
				money = getTotalBudgetFee.get(-3).intValue();
			}
			if(flag)
			{
				String nextApprovalId;
				nextApprovalId = DelegateMngt.getDelegateBudgetNo(2);
				if(!nextApprovalId.equals(""))
				{
					EmailHander.emailDelegateBudget(year,month,nextApprovalId,money);
				}				
				EmailHander.emailPermitBudget(year,month,3,5,money);
			}
		}		
		req.setAttribute("role", roleId);
		req.getRequestDispatcher("BudgetFinanceApprovalB.jsp").forward(req, resp);
	}
	
	private boolean permitBudget(String year, String month , String userID) {
		// TODO Auto-generated method stub
		String dateNo = getDateNoBySelect(year,month);
		ArrayList<String> budgetIds = PermitBudgetMngt.getBudgetIds(dateNo,2);
		return PermitBudgetMngt.updateFinanceBudgetInfo(budgetIds, userID, 3);
	}

	private void setooo(
			HashMap<Integer, Float> getTotalBudgetFee,
			ArrayList<Integer> dept) {
		for(int i=0;i<dept.size();i++)
		{
			int n = 0 ;
		  	Iterator<Integer> iterator1 = getTotalBudgetFee.keySet().iterator();
		  	while(iterator1.hasNext()) 	   
		  	{ 
		  	   	int key = iterator1.next();
		  	   	if(key==dept.get(i))
		  	   	{
		  	   		n = 1;
		  	   		break;
		  	   	}
		  	}
		  	if(n==0){
		  		getTotalBudgetFee.put(dept.get(i), 0.0f);
		  	}		  	
		}			
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	 doGet(req, resp);
	}

	private String getDateNoBySelect(String year, String month) {
		// TODO Auto-generated method stub
		DecimalFormat ndf = new DecimalFormat("00");
        return year + ndf.format(Integer.valueOf(month));
	}

	private String getDateNo() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0,6);
		return dateString;
	}

	private String getEndDateBySelect(String year, String month) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.valueOf(year));  
		cal.set(Calendar.MONTH, Integer.valueOf(month)); 
		cal.set(Calendar.DAY_OF_MONTH, 1);  
		cal.add(Calendar.DAY_OF_MONTH, -1);  
		Date lastDate = cal.getTime();
		String[] s =  lastDate.toString().split(" ");
		DecimalFormat ndf = new DecimalFormat("00");
        return year +"-"+ ndf.format(Integer.valueOf(month)) +"-"+ s[2] + " 23:59:59";
	}

	private String getStartDateBySelect(String year, String month) {
		// TODO Auto-generated method stub
		DecimalFormat ndf = new DecimalFormat("00");
        return year +"-"+ ndf.format(Integer.valueOf(month)) +"-"+ "01";
	}

	private void getProject(
			ArrayList<String[]> budgetfeeList1,ArrayList<Integer> projects) {
        for(int i=0;i<budgetfeeList1.size();i++)
        {
            int x = 0;
            String[] myString = budgetfeeList1.get(i);
            int projectxx = Integer.valueOf(myString[1]); 
            for(int j=0;j<projects.size();j++){
              	if(projects.get(j).equals(projectxx)){
              	  x = 1;
              	  break;
              	}           
            }
            if(x==0){
            	projects.add(projectxx);
            }
        }
        
	}

	private void getSumBudgetUsefeeList(ArrayList<String[]> sumBudgetUsefeeList, 
			ArrayList<String[]> budgetfeeList1) {
		// TODO Auto-generated method stub
		HashMap<Integer,String> courseMap = CourseMngt.getInstance().getCourseMap();
		Iterator<Integer> iterator = courseMap.keySet().iterator();
		while(iterator.hasNext())
		{
			int course = iterator.next();
			int sum1 = 0 ;   //武汉使用费用
			int sum2 = 0 ;   //上海使用费用
			int sum4 = 0 ;   //武汉预算费用
			int sum5 = 0 ;   //上海预算费用
			String[] sum = new String[7];
			sum[0] = course + "";
			
			for(int i=0;i<budgetfeeList1.size();i++)
			{
				String[] myString = budgetfeeList1.get(i);
				if(myString[0].equals(sum[0]))
				{					
					sum4 += Integer.valueOf(myString[2]);
				}				
			}
			int sum3 = sum1 + sum2;   //总使用费用
			int sum6 = sum4 + sum5;   //总预算费用
			sum[1] = sum1 + "";
			sum[2] = sum2 + "";
			sum[3] = sum3 + "";
			sum[4] = sum4 + "";
			sum[5] = sum5 + "";
			sum[6] = sum6 + "";
			sumBudgetUsefeeList.add(sum);
		}		
	}
	
	private String[] getStartDate() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		dateString = dateString.replace("-", "");
		int year = Integer.valueOf(dateString.substring(0,4));
		int month = Integer.valueOf(dateString.substring(4,6));
		if(month == 12)
		{
			year++;
			month = 1;
		}
		else
		{
			month++;
		}
		String[] date = new String[2];
		date[0] = year + "";
		date[1] = month + "";		
		return date;
	}
	
	private String getEndDate() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
//		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0,10) + " 23:59:59";
		return dateString;		
	}
		
	private void getBudgetFee(ArrayList<String[]> budgetfeeList,String dateNo) {
		// TODO Auto-generated method stub
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
//		    rs = statement.executeQuery("select sum(money) as money,d.courseId as invoiceType,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=2 and a.budgetId like '%" + 
//		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by d.courseId,b.deptId,e.institute");

		    rs = statement.executeQuery("SELECT SUM(money) AS money,a.`projectId`,d.courseId AS invoiceType FROM BUDGETINFO a,BUDGETDETAIL c,COURSE d WHERE d.courseName=c.courseName AND a.status=2 and a.budgetId like '%" + 
		    		dateNo + "%' AND a.budgetId=c.budgetId GROUP BY d.courseId,a.budgetId;");
			
			while (rs.next()) 
			{
				String[] myString = new String[3];
				myString[0] = rs.getString("invoiceType");
				myString[1] = rs.getString("projectId");
				myString[2] = rs.getString("money");												
				budgetfeeList.add(myString);
		
		    }			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
	}

	
	private void getTotalBudgetFee(HashMap<Integer, Float> getTotalBudgetFee, String dateNo) {
		// TODO Auto-generated method stub
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return;
		}
		Statement statement = null;
		ResultSet rs = null;
		float total1 = 0.0f;    
		float total3 = 0;    
		try 
		{
			statement = connection.createStatement();
//		    rs = statement.executeQuery("select sum(money) as money,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=2 and a.budgetId like '%" + 
//		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by b.deptId,e.institute");
		    
		    rs = statement.executeQuery("SELECT SUM(money) AS money,a.projectId FROM BUDGETINFO a,BUDGETDETAIL c,COURSE d WHERE d.courseName=c.courseName AND a.status=2 AND a.budgetId LIKE '" + 
		    		dateNo + "%' AND a.budgetId=c.budgetId GROUP BY a.budgetId;");
			while (rs.next()) 
			{
				String[] myString = new String[3];
				myString[1] = rs.getString("projectid");
				myString[2] = rs.getString("money");												
				total1 += rs.getFloat("money");
			
				getTotalBudgetFee.put(Integer.valueOf(myString[1]), rs.getFloat("money"));
		    }
			total3 = total1;
			getTotalBudgetFee.put(-1, total1);
			getTotalBudgetFee.put(-3, total3);			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,2014);  
		cal.set(Calendar.MONTH, 2); 
		cal.set(Calendar.DAY_OF_MONTH, 1);  
		cal.add(Calendar.DAY_OF_MONTH, -1);  
		Date lastDate = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);  
		Date firstDate = cal.getTime();
		System.out.println(lastDate);
		System.out.println(lastDate.toString());
		System.out.println(firstDate);
		System.out.println(firstDate.toLocaleString());
		
		Date currentTime = new Date();
		System.out.println(currentTime);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String dateString = formatter.format(currentTime);
		System.out.println(dateString);
		dateString = dateString.replace("-", "");
		String year = dateString.substring(0,4);
		String month = dateString.substring(4,6);
		int mm = Integer.valueOf(month);
		System.out.println(dateString);
		System.out.println(year);
		System.out.println(month);
		System.out.println(mm);
		System.out.println(new Timestamp(System.currentTimeMillis()));
		

	}

}