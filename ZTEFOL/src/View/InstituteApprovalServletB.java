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
import Common.DeptMngt;
import Common.FOLLogger;
import Common.PermitBudgetMngt;
import Common.UserMngt;
import Data.DBHandler.DBConnectorFactory;
import Jmail.EmailHander;

public class InstituteApprovalServletB extends HttpServlet {

	private static Logger logger = FOLLogger.getLogger(InstituteApprovalServletB.class);
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
		HashMap<Integer,Integer> getTotalBudgetFee = new HashMap<Integer, Integer>(); //heji
		ArrayList<Integer> dept = new ArrayList<Integer>();    //所有科室
		ArrayList<Integer> dept1 = new ArrayList<Integer>();   //武汉科室
		ArrayList<Integer> dept2 = new ArrayList<Integer>();   //上海科室
		String startDate = "";
		String endDate = "";
		String dateNo = "";
		boolean flag = false;
		if(roleId==3)
		{
			String year = "";
			String month = "";
			if(action == null || action.equals("submit"))
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
			
			getBudgetFee(budgetfeeList1,budgetfeeList2,dateNo);
			getSumBudgetUsefeeList(sumBudgetUsefeeList,budgetfeeList1,budgetfeeList2);
			getDept(budgetfeeList1,budgetfeeList2,dept,dept1,dept2);
			getTotalBudgetFee(getTotalBudgetFee,dateNo);
			setooo(getTotalBudgetFee,dept);  //将没有的项设置为0
			req.setAttribute("budgetfeeList1", budgetfeeList1);
			req.setAttribute("budgetfeeList2", budgetfeeList2);
			req.setAttribute("sumBudgetUsefeeList", sumBudgetUsefeeList);
			req.setAttribute("getTotalBudgetFee", getTotalBudgetFee);
			req.setAttribute("dept1", dept1);
			req.setAttribute("dept2", dept2);
			req.setAttribute("dept", dept);
			req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
			req.setAttribute("depts", DeptMngt.getInstance().getDepts());	
			req.setAttribute("startDate", startDate);
			req.setAttribute("endDate", endDate);
			if(action == null || action.equals("query"))
			{
				money = getTotalBudgetFee.get(-3).intValue();
			}
			if(flag)
			{
				EmailHander.emailPermitBudget(year,month,2,4,money);
			}
		}		
		req.setAttribute("role", roleId);
		req.getRequestDispatcher("InstituteApprovalB.jsp").forward(req, resp);
	}
	
	private boolean permitBudget(String year, String month , String userID) {
		// TODO Auto-generated method stub
		String dateNo = getDateNoBySelect(year,month);
		ArrayList<String> budgetIds = PermitBudgetMngt.getBudgetIds(dateNo,1);
		return PermitBudgetMngt.updateInstituteBudgetInfo(budgetIds, userID, 2);
	}

	private void setooo(
			HashMap<Integer, Integer> getTotalBudgetFee,
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
		  		getTotalBudgetFee.put(dept.get(i), 0);
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
//		System.out.println(lastDate.toString());
        return year +"-"+ ndf.format(Integer.valueOf(month)) +"-"+ s[2] + " 23:59:59";
	}

	private String getStartDateBySelect(String year, String month) {
		// TODO Auto-generated method stub
		DecimalFormat ndf = new DecimalFormat("00");
        return year +"-"+ ndf.format(Integer.valueOf(month)) +"-"+ "01";
	}

	private void getDept(
			ArrayList<String[]> budgetfeeList1,
			ArrayList<String[]> budgetfeeList2, ArrayList<Integer> dept, 
			ArrayList<Integer> dept1, ArrayList<Integer> dept2) {
		// TODO Auto-generated method stub        
        for(int i=0;i<budgetfeeList1.size();i++)
        {
            int x = 0;
            String[] myString = budgetfeeList1.get(i);
            int deptxx = Integer.valueOf(myString[1]); 
            for(int j=0;j<dept.size();j++){
              	if(dept.get(j).equals(deptxx)){
              	  x = 1;
              	  break;
              	}           
            }
            if(x==0){
            dept.add(deptxx);
            dept1.add(deptxx);
            }
        }
        
        for(int i=0;i<budgetfeeList2.size();i++)
        {
            int x = 0;
            String[] myString = budgetfeeList2.get(i);
            int deptxx = Integer.valueOf(myString[1]); 
            for(int j=0;j<dept.size();j++){
              	if(dept.get(j).equals(deptxx)){
              	  x = 1;
              	  break;
              	}           
            }
            if(x==0){
            dept.add(deptxx);
            dept2.add(deptxx);
            }
        }		
	}

	private void getSumBudgetUsefeeList(ArrayList<String[]> sumBudgetUsefeeList, 
			ArrayList<String[]> budgetfeeList1, ArrayList<String[]> budgetfeeList2) {
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
			for(int i=0;i<budgetfeeList2.size();i++)
			{
				String[] myString = budgetfeeList2.get(i);
				if(myString[0].equals(sum[0]))
				{					
					sum5 += Integer.valueOf(myString[2]);
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
		
	private void getBudgetFee(ArrayList<String[]> budgetfeeList1,
			ArrayList<String[]> budgetfeeList2, String dateNo) {
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
		    rs = statement.executeQuery("select sum(money) as money,d.courseId as invoiceType,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=1 and a.budgetId like '%" + 
		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by d.courseId,b.deptId,e.institute");
			while (rs.next()) 
			{
				String[] myString = new String[3];
				myString[0] = rs.getString("invoiceType");
				myString[1] = rs.getString("deptid");
				myString[2] = rs.getString("money");												
				if(Integer.valueOf(rs.getString("institute"))==1)
				{
					budgetfeeList1.add(myString);
				}
				else
				{
					budgetfeeList2.add(myString);
				}			
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

	
	private void getTotalBudgetFee(HashMap<Integer, Integer> getTotalBudgetFee, String dateNo) {
		// TODO Auto-generated method stub
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return;
		}
		Statement statement = null;
		ResultSet rs = null;
		int total1 = 0;    //wuhan
		int total2 = 0;    //shanghai
		int total3 = 0;    //zonghe
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("select sum(money) as money,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=1 and a.budgetId like '%" + 
		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by b.deptId,e.institute");
			while (rs.next()) 
			{
				String[] myString = new String[3];
				myString[1] = rs.getString("deptid");
				myString[2] = rs.getString("money");												
				if(Integer.valueOf(rs.getString("institute"))==1)
				{
					total1 += Integer.valueOf(myString[2]);
				}
				else
				{
					total2 += Integer.valueOf(myString[2]);
				}			
				getTotalBudgetFee.put(Integer.valueOf(myString[1]), Integer.valueOf(myString[2]));
		    }
			total3 = total1 + total2;
			getTotalBudgetFee.put(-1, total1);
			getTotalBudgetFee.put(-2, total2);
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

}
