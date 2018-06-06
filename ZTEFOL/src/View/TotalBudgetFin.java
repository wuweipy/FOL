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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Business.Budget.BudgetService;
import Common.CourseMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.UserMngt;
import Data.DBHandler.DBConnectorFactory;
import Data.UserInfo.DUser;

public class TotalBudgetFin extends HttpServlet{

	private static Logger logger = FOLLogger.getLogger(TotalBudgetFin.class);
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
		ArrayList<Integer> dept = new ArrayList<Integer>();    //���п���
		ArrayList<Integer> dept1 = new ArrayList<Integer>();   //�人����
		ArrayList<Integer> dept2 = new ArrayList<Integer>();   //�Ϻ�����
		Set<String> sales = new HashSet<String>();
		ArrayList<String[]> salesfeeList = new ArrayList<String[]>(); //sales
		String startDate = "";
		String endDate = "";
		String dateNo = "";
		if(roleId==4)
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

			//getBudgetFee(budgetfeeList1,budgetfeeList2,dateNo);
			BudgetService.queryAndSet(budgetfeeList1, budgetfeeList2, salesfeeList, sales, dateNo);
			getSumBudgetUsefeeList(sumBudgetUsefeeList,budgetfeeList1,budgetfeeList2);
			getDept(budgetfeeList1,budgetfeeList2,dept,dept1,dept2);
			getTotalBudgetFee(getTotalBudgetFee,dateNo);
			setooo(getTotalBudgetFee,dept);  //��û�е�������Ϊ0
			req.setAttribute("budgetfeeList1", budgetfeeList1);
			req.setAttribute("budgetfeeList2", budgetfeeList2);
			req.setAttribute("sumBudgetUsefeeList", sumBudgetUsefeeList);
			req.setAttribute("getTotalBudgetFee", getTotalBudgetFee);
			req.setAttribute("dept1", dept1);
			req.setAttribute("dept2", dept2);
			req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
			req.setAttribute("depts", DeptMngt.getInstance().getDepts());	
			req.setAttribute("startDate", startDate);
			req.setAttribute("endDate", endDate);
			req.setAttribute("salesfeelist", salesfeeList);
			req.setAttribute("sales", getSales(sales));
		}
		req.setAttribute("role", roleId);
		req.getRequestDispatcher("totalBudgetFin.jsp").forward(req, resp);
	}
	
	private List<DUser> getSales(Set<String> salesNoSet) {
		List<DUser> sales = new ArrayList<DUser>();
		for ( String no : salesNoSet )
		{
			 sales.add(UserMngt.getInstance().getUserInfoMap().get(no));
		}
		return sales;
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
		System.out.println(lastDate.toString());
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
			int sum1 = 0 ;   //�人ʹ�÷���
			int sum2 = 0 ;   //�Ϻ�ʹ�÷���
			int sum4 = 0 ;   //�人Ԥ�����
			int sum5 = 0 ;   //�Ϻ�Ԥ�����
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
			int sum3 = sum1 + sum2;   //��ʹ�÷���
			int sum6 = sum4 + sum5;   //��Ԥ�����
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
		    rs = statement.executeQuery("select sum(money) as money,d.courseId as invoiceType,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=3 and a.budgetId like '%" + 
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
		    rs = statement.executeQuery("select sum(money) as money,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=3 and a.budgetId like '%" + 
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
