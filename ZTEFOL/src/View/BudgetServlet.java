package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Common.DeptMngt;
import Common.ProductMngt;
import Common.ProjectMngt;
import Common.UserMngt;
import Common.DelegateMngt;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.Common.Countersign.CountersignDao;
import Data.DBHandler.DBConnectorFactory;
import Data.Project.DProject;
import Jmail.EmailHander;
/**
 * 预算当月提单:下月预算
 */
public class BudgetServlet extends HttpServlet{
	private static Logger logger = FOLLogger.getLogger(BudgetServlet.class);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	    HttpSession session = req.getSession();
		String userID = (String)session.getAttribute("userID");
		String action = req.getParameter("action");
		int deptId = UserMngt.getInstance().getUserInfoMap().get(userID).getDeptId();
		int roleId = UserMngt.getInstance().getUserInfoMap().get(userID).getRoleId();
		String userName = UserMngt.getInstance().getUserInfoMap().get(userID).getName();
		
		ArrayList<String> budgetAllNos = DelegateMngt.getBudgetAllNos(userID);
		ArrayList<Integer> subProIds = new ArrayList<Integer>();
		int flag = 0 ;
		String timeID = getTimeID();
		if(budgetAllNos.size()!=0){
			flag = 1;
			int k = budgetAllNos.size();
			for(int i=0,j=0; j<k; j++){
				if(getJudge(budgetAllNos.get(i),timeID))
				{
					budgetAllNos.remove(i);
				}
				else{
					i++;
				}
			}
		}
		if(deptId==6 || deptId==8 || deptId==9 || deptId==10 || deptId==11 || deptId==12 || deptId==13 || roleId == 2)
		{
			flag = 1;
			if(!getJudge(userID,timeID)){
				budgetAllNos.add(userID);
			}
		}
		if(action == null)
		{			
			if(flag == 1){
				if(budgetAllNos.size()==0)
				{
					req.setAttribute("flag", flag);
					req.getRequestDispatcher("CurrentBudgetJudge.jsp").forward(req, resp);
				}
				else
				{
					String reRealID = budgetAllNos.get(0);
					Integer realProID = 0;
					String  realProName = "";
					DProject project = ProjectMngt.getProjectByOwner(reRealID);
					realProID = project.getProjectid();
					realProName = project.getProjectname();
					subProIds = getSubProdsId(realProID);
					req.setAttribute("realDeptName", realProName);
					req.setAttribute("realID", reRealID);
					req.setAttribute("subProIds", subProIds);
					req.setAttribute("subProducts", ProductMngt.getSubProducts());
				    req.setAttribute("budgetAllNos", budgetAllNos);
					req.setAttribute("userInfoMap", UserMngt.getInstance().getUserInfoMap());
				    req.setAttribute("courseMap", getCourse());
					req.getRequestDispatcher("CurrentBudget.jsp").forward(req, resp);
				}
			}
			else
			{
				req.setAttribute("flag", flag);
				req.getRequestDispatcher("CurrentBudgetJudge.jsp").forward(req, resp);
			}
		}
		else if(action.equals("refresh"))
		{
			String realID = req.getParameter("realUserno");
			int realDeptID = UserMngt.getInstance().getUserInfoMap().get(realID).getDeptId();
			String realDeptName = DeptMngt.getInstance().getDeptName(realDeptID);
			subProIds = getSubProdsId(realDeptID);
			req.setAttribute("subProIds", subProIds);
			req.setAttribute("realDeptName", realDeptName);
			req.setAttribute("realID", realID);
			req.setAttribute("subProducts", ProductMngt.getSubProducts());
		    req.setAttribute("budgetAllNos", budgetAllNos);
			req.setAttribute("userInfoMap", UserMngt.getInstance().getUserInfoMap());
		    req.setAttribute("courseMap", getCourse());
			req.getRequestDispatcher("CurrentBudget.jsp").forward(req, resp);
		}
		else if(action.equals("submit"))
		{
		    int count = Integer.valueOf(req.getParameter("count"));
		    ArrayList<String> courseName = new ArrayList<String>();
			ArrayList<String> moneys = new ArrayList<String>();
			ArrayList<String> descriptions = new ArrayList<String>();	
			for(int i = 0; i < count; i++)
			{  
				 courseName.add(ParameterUtil.getChineseString(req, "courseName" + i,"utf-8"));
			     if((req.getParameter("money" + i) != null)&& !(req.getParameter("money" + i).equals("")))
			     {
			    	 String money = req.getParameter("money" + i);
			    	 moneys.add(money);  	 
			     }
			     else
			     {
			    	 moneys.add("0");
			     }
			     
			     if((req.getParameter("description" + i) != null) && !(req.getParameter("description" + i).equals("")))
			     {
			    	 String description = "description" + i;
			    	 String description1 = ParameterUtil.getChineseString(req, description,"utf-8");
			    	 descriptions.add(description1);  	 
			     }
			     else
			     {
			    	 descriptions.add("-");
			     }
			}
			int status = 2;
			int institute = DeptMngt.getInstance().getDepts1().get(deptId).getInstitute();
//			if(institute == 1)
//			{
//				status = 1; 
//			}
//			else 
//				status = 2; 
			String budgetId = getBudgetId();
			String realID = req.getParameter("userNo");
			Integer projectId = 0;
			String  projectName = "";
			boolean isSucess;
			if(realID.equals(userID)){
				DProject project = ProjectMngt.getInstance().getProjectByOwner(userID);
				projectId = project.getProjectid();
				projectName = project.getProjectname();
				isSucess = createBudget(budgetId, count ,userID , userName,projectId,projectName, courseName, moneys, descriptions,status);
			}
			else
			{
				DProject project = ProjectMngt.getInstance().getProjectByOwner(realID);
				projectId = project.getProjectid();
				projectName = project.getProjectname();
				userName = UserMngt.getInstance().getUserInfoMap().get(realID).getName();
				isSucess = DelegateMngt.createBudget(budgetId, count ,userID, userName ,realID ,projectId,projectName,courseName, moneys, descriptions,status);
			}
			req.setAttribute("isSucess", isSucess);
			int itemNum = Integer.valueOf(req.getParameter("itemNum11"));
			if(isSucess){
				ArrayList<String> subMoneys = new ArrayList<String>();
				subProIds = getSubProdsId(ProjectMngt.getInstance().getProjectByOwner(realID).getProjectid());
				if(subProIds.size()!=0)
				{
					for(int i = 0; i < count; i++)
					{  						 
					     String subMoney="";
					     for(int j=0;j<subProIds.size();j++)
					     {
						     if((req.getParameter("submoney" + i + j) != null) && !(req.getParameter("submoney" + i + j).equals("")))
						     {
						    	 subMoney = subMoney + subProIds.get(j) + "," + req.getParameter("submoney" + i + j) + ",";
						     }
						     else
						     {
						    	 subMoney = subMoney + subProIds.get(j) + ",0,";
						     }
					     }
					     if(!subMoney.equals(""))
					     {
					    	 subMoneys.add(subMoney.substring(0,subMoney.length()-1));
					     }
					     else
					     {
					    	 subMoneys.add(subMoney);
					     }
					}
					insertSubMoney(budgetId,count,courseName,subMoneys);
				}
				budgetAllNos.remove(realID);
				req.setAttribute("flag", flag);
				int nextRole = 0;
				if(institute == 1){
				   nextRole = 3;
				}
				else
					nextRole = 4;
				//EmailHander.emailHanderBudget(budgetId, 1, realID, nextRole,realID);
			}
			if(itemNum==1 && isSucess)
			{
			    req.getRequestDispatcher("CurrentBudgetJudge.jsp").forward(req, resp);
			}
			else {				
				String reRealID = budgetAllNos.get(0);
				req.setAttribute("realID", reRealID);
				int realDeptID = UserMngt.getInstance().getUserInfoMap().get(reRealID).getDeptId();
				String realDeptName = DeptMngt.getInstance().getDeptName(realDeptID);
				subProIds = getSubProdsId(realDeptID);
				req.setAttribute("subProIds", subProIds);
				req.setAttribute("realDeptName", realDeptName);
				req.setAttribute("subProducts", ProductMngt.getSubProducts());
			    req.setAttribute("courseMap", getCourse());
			    req.setAttribute("budgetAllNos", budgetAllNos);
				req.setAttribute("userInfoMap", UserMngt.getInstance().getUserInfoMap());
			    req.getRequestDispatcher("CurrentBudget.jsp").forward(req, resp);
			}
		}
    }	

	private boolean insertSubMoney(String budgetId, int count, ArrayList<String> courseName,
			ArrayList<String> subMoneys) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if(connection == null)
		{
			return false;
		}
	    try 
		{		   
	    	for(int i=0;i<count;i++)
	    	{
		    	String sql1 = "update BUDGETDETAIL set subMoney=? where budgetId=? and courseName=?";
				statement = connection.prepareStatement(sql1);
				statement.setString(1,subMoneys.get(i));
				statement.setString(2,budgetId);
				statement.setString(3,courseName.get(i));
				statement.executeUpdate();
	    	}
			return true;		
		}
		 catch (SQLException e) 
		 {
			e.printStackTrace();
			logger.error(e.getMessage());
		 }
		 finally
		 {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		 } 
		 return false;	
	}

	private HashMap<String, ArrayList<Integer>> getSubProds(
			ArrayList<String> budgetAllNos) {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<Integer>> subProds = new HashMap<String, ArrayList<Integer>>();
		for(int i=0;i<budgetAllNos.size();i++)
		{
			String no = budgetAllNos.get(i);
			int deptId = UserMngt.getInstance().getUserInfoMap().get(no).getDeptId();
			ArrayList<Integer> SubProdsIds = getSubProdsId(deptId);
			subProds.put(no, SubProdsIds);
		}
		return subProds;
	}

	public ArrayList<Integer> getSubProdsId(int deptId) {
		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		ArrayList<Integer> SubProdsIds = new ArrayList<Integer>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM SUBPRODUCT WHERE item='" + deptId + "' and SUBPRODUCT.isDelete=0";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				SubProdsIds.add(rs.getInt("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(conn, stmt, rs);
		}
		return SubProdsIds;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	 doGet(req, resp);
	}
	
	private boolean getJudge(String userID, String timeID) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		Statement statement = null;
		ResultSet rs = null;
		if(connection == null)
		{
			return false;
		}
	    try 
		{
			statement = connection.createStatement();
		    String sql = "select * from BUDGETINFO where no='" + userID +"' and budgetId like '%" + timeID + "%'";
		    rs = statement.executeQuery(sql);
		    if(rs.next())
		    {
		    	return true;
		    }
		}
		 catch (SQLException e) 
		 {
			e.printStackTrace();
			logger.error(e.getMessage());
		 }
		 finally
		 {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		 } 
		 return false;
	}

	private String getTimeID() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0,6);
		if(dateString.substring(4,6)=="12"){
			dateString = (Integer.valueOf(dateString.substring(0,4)) + 1) + "01";				
		}
		else{
			dateString = (Integer.valueOf(dateString) + 1) + "" ;				
		}
		return dateString;
	}

	private boolean createBudget(String budgetId, int count, String userID, String userName, Integer pId,String pName,ArrayList<String> courseName,
			ArrayList<String> moneys, ArrayList<String> descriptions, int status) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if(connection == null)
		{
			return false;
		}
	    try 
		{
			for(int i = 0; i < count; i++)
			{		
			    String sql = "insert into BUDGETDETAIL(budgetId,courseName,money,description)" +
	            "values (?,?,?,?)";
				statement = connection.prepareStatement(sql);		
			    statement.setString(1, budgetId);
			    statement.setString(2,courseName.get(i));
			    statement.setInt(3, Integer.valueOf(moneys.get(i)));
			    statement.setString(4, descriptions.get(i));		     
				statement.executeUpdate();
		    }
		    String sql = "insert into BUDGETINFO(budgetId,no,username,billTime,status,projectId,projectName)" +
            "values (?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);		
		    statement.setString(1, budgetId);
		    statement.setString(2,userID);
		    statement.setString(3,userName);
		    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		    statement.setInt(5, status);	
		    statement.setInt(6, pId);	
		    statement.setString(7, pName);	
			return statement.executeUpdate() == 1;
		}
		 catch (SQLException e) 
		 {
			e.printStackTrace();
			logger.error(e.getMessage());
		 }
		 finally
		 {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		 } 
		 return false;
	}
	
	public static synchronized String getBudgetId()
	{
		// 读文本中数据 今天的时间
		// 和现在的时间合并
		// 修改文本数据
		// 存进去 文本和数据库
		String file = BudgetServlet.class.getResource("").toString();
//		file = file.substring(6) + "id.txt";                     //windows系统
		file = "/" + file.substring(6) + "id.txt";
//		file = file.replace("/", "//");
		System.out.println(file);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String data = br.readLine();
			br.close();
			String getDatas[] = data.split(",");
			String getDate = getDatas[0];
			String getId = getDatas[1]; 

			// 取得现在日期
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			dateString = dateString.replace("-", "");
			String fulldayString = dateString;
			dateString = dateString.substring(0,6);
			//20号之前提的预算还算本月的预算
			if(Integer.valueOf(fulldayString.substring(6,8)) >= 20) {		
				if(dateString.substring(4,6).equals("12")){
					dateString = (Integer.valueOf(dateString.substring(0,4)) + 1) + "01";				
				}
				else{
					dateString = (Integer.valueOf(dateString) + 1) + "" ;				
				}
			}
			// System.out.println(dateString);

			String saveDate = "";
			int saveId = 1;
			if (dateString.equals(getDate)) {
				saveId = Integer.parseInt(getId) + 1;
				DecimalFormat ndf = new DecimalFormat("000");
				String nsaveId = ndf.format(saveId);
				saveDate = dateString;
				saveDate = saveDate.replace("-", "");
				// 组合存进数据库操作
				// 将结果写入txt
				String saveResult = saveDate + "," + saveId;
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(file)));				
				writer.write(saveResult);
				writer.close();
				System.out.println(saveDate + nsaveId);
				return saveDate + nsaveId;
			} else {
				saveId = 1;
				DecimalFormat ndf = new DecimalFormat("000");
				String nsaveId = ndf.format(saveId);
				saveDate = dateString;
				saveDate = saveDate.replace("-", "");
				// 组合存进数据库操作
				// 将结果写入txt
				String saveResult = saveDate + "," + saveId;
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(file)));
				writer.write(saveResult);
				writer.close();
				System.out.println(saveDate + nsaveId);
				return saveDate + nsaveId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;		
	}
   
    private HashMap<Integer, String> getCourse() {
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return courseMap;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from COURSE");
		    int i=0;
			while (rs.next()) 
			{
				courseMap.put(i, rs.getString("courseName"));
				i++;
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
		return courseMap;
		
	}
}
