package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Common.ProjectMngt;
import Common.UserMngt;
import Common.CourseMngt;
import Common.DeptMngt;
import Data.DBHandler.DBConnectorFactory;

/* 预算使用表 */

public class TotalUseServlet extends HttpServlet{
	private static Logger logger = FOLLogger.getLogger(TotalUseServlet.class);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	    HttpSession session = req.getSession();
		String userID = (String)session.getAttribute("userID");
		String action = req.getParameter("action");
		int roleId = UserMngt.getInstance().getUserInfoMap().get(userID).getRoleId();
		ArrayList<String[]> usefeeList1 = new ArrayList<String[]>();
		ArrayList<String[]> usefeeList2 = new ArrayList<String[]>();
		ArrayList<String[]> sumUsefeeList = new ArrayList<String[]>();
		HashMap<Integer,Float> getTotalFee = new HashMap<Integer, Float>();
        ArrayList<Integer> projects = new ArrayList<Integer>();    //所有项目
		String startDate = "";
		String endDate = "";		
		if(roleId==4 || roleId==5)
		{
			if(action == null)
			{
				startDate = getStartDate();
				endDate = getEndDate();
			}
			else if(action.equals("query"))
			{
				if (req.getParameter("txtSubmitDateFrom") != null
						&& req.getParameter("txtSubmitDateFrom") != ""
							&& req.getParameter("txtSubmitDateTo") != null
							&& req.getParameter("txtSubmitDateTo") != "")
				{
					String from[] = req.getParameter("txtSubmitDateFrom").split("-");		
					String to[] = req.getParameter("txtSubmitDateTo").split("-");
					DecimalFormat ndf = new DecimalFormat("00");
					startDate = from[0] +"-"+ ndf.format(Integer.valueOf(from[1])) +"-"+ ndf.format(Integer.valueOf(from[2]));
					endDate = to[0] +"-"+ ndf.format(Integer.valueOf(to[1])) +"-"+ ndf.format(Integer.valueOf(to[2])) + " 23:59:59";
				}
				else
				{
					startDate = getStartDate();
					endDate = getEndDate();					
				}
			}
			getUseFee(usefeeList1,startDate,endDate);
			getSumUsefeeList(usefeeList1,sumUsefeeList);
            getProject(usefeeList1,projects);
			getTotalFee(getTotalFee,startDate,endDate);
			req.setAttribute("usefeeList1", usefeeList1);
			req.setAttribute("sumUsefeeList", sumUsefeeList);
			req.setAttribute("getTotalFee", getTotalFee);
            req.setAttribute("projects", projects);
	        req.setAttribute("projectMap", ProjectMngt.getInstance().getProjectMap());
			req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());	
			req.setAttribute("startDate", startDate);
			req.setAttribute("endDate", endDate);		
		}
		req.setAttribute("role", roleId);
		req.getRequestDispatcher("TotalUseBudget.jsp").forward(req, resp);
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
	
	private void getProject(
			ArrayList<String[]> usefeeList1,ArrayList<Integer> projects) {
        for(int i=0;i<usefeeList1.size();i++)
        {
            int x = 0;
            String[] myString = usefeeList1.get(i);
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
	
	
	private String getEndDate() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
//		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0,10) + " 23:59:59";
		return dateString;		
	}

	private void getTotalFee(HashMap<Integer, Float> getTotalFee, String startDate, String endDate) {
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
		Float total3 = 0.0f;    //zonghe
		try 
		{
			statement = connection.createStatement();
//		    rs = statement.executeQuery("select sum(totalfee) as money , deptid,institute  from APPROVALINFO a,CLAIMS b ,DEPT c where a.status=3 and a.invoiceNo=b.invoiceNo and a.appDate between '" + startDate + 
//		    		"' and '" + endDate +"' and b.deptid=c.id and a.appState=1 group by b.deptid, c.institute");
		    rs = statement.executeQuery("SELECT ROUND(SUM(approvalAmount),2) AS money , b.invoiceType,b.productId,a.appDate FROM APPROVALINFO a,CLAIMS b WHERE b.status=2 AND a.invoiceNo=b.invoiceNo AND a.appDate BETWEEN '" + startDate + 
		    		"' and '" + endDate +"' AND a.appState=1 GROUP BY b.productId");			
			while (rs.next()) 
			{

				total1 += rs.getFloat("money");
				
				getTotalFee.put(rs.getInt("productId"), rs.getFloat("money"));	
		    }
			getTotalFee.put(-1, total1);
			getTotalFee.put(-3, total1);
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

	private void getSumUsefeeList(ArrayList<String[]> usefeeList1, ArrayList<String[]> sumUsefeeList) {
		// TODO Auto-generated method stub
		HashMap<Integer,String> courseMap = CourseMngt.getInstance().getCourseMap();
		Iterator<Integer> iterator = courseMap.keySet().iterator();
		while(iterator.hasNext())
		{
			int course = iterator.next();
			float sum1 = 0 ;
			float sum2 = 0 ;
			String[] sum = new String[4];
			sum[0] = course + "";
			for(int i=0;i<usefeeList1.size();i++)
			{
				String[] myString = usefeeList1.get(i);
				if(myString[0].equals(sum[0]))
				{					
					sum1 += Float.valueOf(myString[2]);
				}				
			}
			
			float sum3 = sum1 + sum2;
			sum[1] = sum1 + "";
			sum[2] = sum2 + "";
			sum[3] = sum3 + "";
			sumUsefeeList.add(sum);
		}		
	}

	private void getUseFee(ArrayList<String[]> usefeeList1, String startDate, String endDate) {
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
//		    rs = statement.executeQuery("select sum(totalfee) as money , b.invoiceType,b.deptid,c.institute  from APPROVALINFO a,CLAIMS b ,DEPT c where a.status=3 and a.invoiceNo=b.invoiceNo and a.appDate between '" + startDate + 
//		    		"' and '" + endDate +"' and b.deptid=c.id and a.appState=1 group by b.invoiceType ,b.deptid, c.institute");
		    rs = statement.executeQuery("SELECT ROUND(SUM(approvalAmount),2) AS money , b.invoiceType,b.productId,a.appDate FROM APPROVALINFO a,CLAIMS b WHERE b.status=2 AND a.invoiceNo=b.invoiceNo AND a.appDate BETWEEN '" + startDate + 
		    		"' and '" + endDate +"' AND a.appState=1 GROUP BY b.invoiceType,b.productId");
			while (rs.next()) 
			{
				String[] myString = new String[3];
				myString[0] = rs.getString("invoiceType");
				myString[1] = rs.getString("productId");
				myString[2] = rs.getString("money");
				usefeeList1.add(myString);
			
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


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	 doGet(req, resp);
	}	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
