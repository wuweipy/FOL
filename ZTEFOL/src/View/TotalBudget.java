package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import Data.DBHandler.DBConnectorFactory;
/**
 * 预算总表
 */
public class TotalBudget extends HttpServlet {

	private static Logger logger = FOLLogger.getLogger(TotalBudget.class);
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
		ArrayList<Integer> projects = new ArrayList<Integer>();    //所有科室
		String startDate = "";
		String endDate = "";
		String dateNo = "";
		boolean delegate = DelegateMngt.wheatherDelegate(userID, 2);
		if(roleId==4 || roleId==5 || delegate)
		{
			if(action == null)
			{
				startDate = getStartDate();
				endDate = getEndDate();
				dateNo = getDateNo();
			}
			else if(action.equals("query"))
			{
				String year = req.getParameter("year");
				String month = req.getParameter("month");
				if(year.equals(""))
				{
					year = getStartDate().substring(0,4);
				}
				if(month.equals(""))
				{
					month = getStartDate().substring(5,7);
				}

				startDate = getStartDateBySelect(year,month);
				endDate = getEndDateBySelect(year,month);
				dateNo = getDateNoBySelect(year,month);

			}

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
		}
		req.setAttribute("role", roleId);
		req.setAttribute("delegate", delegate);
		req.getRequestDispatcher("totalBudget.jsp").forward(req, resp);
	}
	
	private void setooo(
			HashMap<Integer, Float> getTotalBudgetFee,
			ArrayList<Integer> projects) {
		for(int i=0;i<projects.size();i++)
		{
			int n = 0 ;
		  	Iterator<Integer> iterator1 = getTotalBudgetFee.keySet().iterator();
		  	while(iterator1.hasNext()) 	   
		  	{ 
		  	   	int key = iterator1.next();
		  	   	if(key==projects.get(i))
		  	   	{
		  	   		n = 1;
		  	   		break;
		  	   	}
		  	}
		  	if(n==0){
		  		getTotalBudgetFee.put(projects.get(i), 0.0f);
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
			ArrayList<String[]> budgetfeeList1, ArrayList<Integer> projects) {
		// TODO Auto-generated method stub        
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
			float sum1 = 0 ;   //武汉使用费用
			float sum2 = 0 ;   //上海使用费用
			float sum4 = 0 ;   //武汉预算费用
			float sum5 = 0 ;   //上海预算费用
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
			float sum3 = sum1 + sum2;   //总使用费用
			float sum6 = sum4 + sum5;   //总预算费用
			sum[1] = sum1 + "";
			sum[2] = sum2 + "";
			sum[3] = sum3 + "";
			sum[4] = sum4 + "";
			sum[5] = sum5 + "";
			sum[6] = sum6 + "";
			sumBudgetUsefeeList.add(sum);
		}		
	}
	
	private String getStartDate() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
//		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0,8) + "01";
		return dateString;
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

		    rs = statement.executeQuery("SELECT SUM(money) AS money,a.`projectId`,d.courseId AS invoiceType FROM BUDGETINFO a,BUDGETDETAIL c,COURSE d WHERE d.courseName=c.courseName AND a.status=5 and a.budgetId like '%" + 
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
		Float total1 = 0.0f;    //wuhan
		Float total2 = 0.0f;    //shanghai
		Float total3 = 0.0f;    //zonghe
		try 
		{
			statement = connection.createStatement();
//		    rs = statement.executeQuery("select sum(money) as money,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=2 and a.budgetId like '%" + 
//		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by b.deptId,e.institute");
		    
		    rs = statement.executeQuery("SELECT SUM(money) AS money,a.projectId FROM BUDGETINFO a,BUDGETDETAIL c,COURSE d WHERE d.courseName=c.courseName AND a.status=5 AND a.budgetId LIKE '" + 
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0,6);
		System.out.println(dateString);
	}

}
