package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import Data.Common.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Business.Budget.BudgetDetailInfo;
import Business.Budget.BudgetInfo;
import org.apache.log4j.Logger;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.UserMngt;
import Common.ProductMngt;
import Data.DBHandler.DBConnectorFactory;
import Jmail.EmailHander;
/**
 * 预算总经理审批详细
 */
public class BudgetManagerDetailServlet extends HttpServlet{
	private static Logger logger = FOLLogger.getLogger(BudgetManagerDetailServlet.class);
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		ArrayList<Integer> subProIds = new ArrayList<Integer>();
		String budgetId = ParameterUtil.getChineseString(req, "budgetId","utf-8");
		String name = ParameterUtil.getChineseString(req, "name","utf-8");
		String dept = ParameterUtil.getChineseString(req, "dept","utf-8");
		String submitDate = req.getParameter("submitDate");	
		if(action == null){
			req.setAttribute("budgetDetailInfos", getBudgetDInfo(budgetId,subProIds));
			req.setAttribute("budgetInfo", getApprovalBudget(req,budgetId));
			req.setAttribute("budgetId", budgetId);
			req.setAttribute("name", name);
			req.setAttribute("dept", dept);
			req.setAttribute("submitDate", submitDate);
			req.setAttribute("subProducts", ProductMngt.getSubProducts());
			req.setAttribute("subProIds", subProIds);
			req.getRequestDispatcher("BudgetManagerDetailInfo.jsp").forward(req, resp);			
		}
		else if(action.equals("submit"))
		{
		    int count = Integer.valueOf(req.getParameter("count"));
		    int agg = Integer.valueOf(req.getParameter("agg"));
		    String approvalId = (String)session.getAttribute("userID");
		    ArrayList<String> courseName = new ArrayList<String>();
			ArrayList<String> managerOpinions = new ArrayList<String>();	
			for(int i = 0; i < count; i++)
			{  
				 courseName.add(ParameterUtil.getChineseString(req, "courseName" + i,"utf-8"));	     
			     if((req.getParameter("managerOpinion" + i) != null) && !(req.getParameter("managerOpinion" + i).equals("")))
			     {
			    	 String description = "managerOpinion" + i;
			    	 String description1 = ParameterUtil.getChineseString(req, description,"utf-8");
			    	 managerOpinions.add(description1);  	 
			     }
			     else
			     {
			    	 managerOpinions.add("-");
			     }
			}
			int status = 0;
			int flag = 0 ;
			if(agg==0)
			{
				status = 5;
				flag = 3;
			}
			else 
			{
				status = 4;
				flag = 2;
			}
			boolean isSucess = insertInstituteOpinion(budgetId, count ,courseName, managerOpinions,approvalId,status);
			if(isSucess){
				int nextRole = 5;
				String tidanren = ParameterUtil.getChineseString(req, "tidanren","utf-8");
				String userid = tidanren.substring(tidanren.length()-10);
				EmailHander.emailHanderBudget(budgetId, flag, userid, nextRole,approvalId);			
			}
			req.setAttribute("isSucess", isSucess);
			req.getRequestDispatcher("budgetManagerServletB.do").forward(req, resp);	
		}
	}
	
	private BudgetInfo getApprovalBudget(HttpServletRequest req,String budgetId) {
		// TODO Auto-generated method stub			
		
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	BudgetInfo budgetInfo = new BudgetInfo();
		if(connection == null)
		{
			return budgetInfo;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BUDGETINFO where budgetId = '" + budgetId + "'");
			while (rs.next()) 
			{
				if(rs.getString("institute")!=null){
					budgetInfo.setInstituteDate(rs.getTimestamp("instituteTime"));
					budgetInfo.setInstitute(getName(rs.getString("institute")));	
				}
				if(rs.getString("finance")!=null){
					budgetInfo.setFinanceDate(rs.getTimestamp("financeTime"));
					budgetInfo.setFinance(getName(rs.getString("finance")));	
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
		return budgetInfo;
		
	}
	
	private String getName(String no) {
		// TODO Auto-generated method stub
		return UserMngt.getInstance().getUserInfoMap().get(no).getName() + no;
	}
	
	private boolean insertInstituteOpinion(String budgetId, int count,
			ArrayList<String> courseName, ArrayList<String> managerOpinions,
			String approvalId,int status) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		if(connection == null)
		{
			return false;
		}
	    try 
		{
	    	for(int i=0; i<count; i++)
	    	{
	        String courseNameD = courseName.get(i);
	        String managerOpinion = managerOpinions.get(i);
		  	String sql = "update BUDGETDETAIL set managerOpinion=? where budgetId=? and courseName=?";
			statement = connection.prepareStatement(sql);

			statement.setString(1,managerOpinion);
			statement.setString(2,budgetId);			
		    statement.setString(3,courseNameD);		
			statement.executeUpdate();
	    	}
	    	
	    	String sql1 = "update BUDGETINFO set manager=?, managerTime=?, status=? where budgetId=?";
			statement = connection.prepareStatement(sql1);
			statement.setString(1,approvalId);
			statement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));	
			statement.setInt(3,status);
			statement.setString(4,budgetId);
			return statement.executeUpdate()==1;
			
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

	private List<BudgetDetailInfo> getBudgetDInfo(String budgetId, ArrayList<Integer> subProIds) {
		// TODO Auto-generated method stub
		List<BudgetDetailInfo> budgetDetailInfos = new ArrayList<BudgetDetailInfo>();
		BudgetDetailInfo budgetDetailInfo = null;	
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return budgetDetailInfos;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BUDGETDETAIL where budgetId = '" + budgetId + "'");
			while (rs.next()) 
			{
				budgetDetailInfo = new BudgetDetailInfo();
				budgetDetailInfo.setCourseName(rs.getString("courseName"));
				budgetDetailInfo.setMoney(rs.getInt("money"));
				budgetDetailInfo.setDescription(rs.getString("description"));
				budgetDetailInfo.setInstituteOpinion(rs.getString("instituteOpinion"));
				budgetDetailInfo.setFinanceOpinion(rs.getString("financeOpinion"));
				ArrayList<Entry> submoneys = new ArrayList<Entry>();
				if(rs.getString("subMoney")!=null)
				{
					String[] subM = rs.getString("subMoney").split(",");
					int count = subM.length / 2;
					for(int i=0;i<count;i++)
					{
						Entry entry = new Entry();
						entry.setId(Integer.valueOf(subM[2*i]));
						entry.setName(subM[2*i+1]);
						submoneys.add(entry);
					}
					budgetDetailInfo.setSubmoneys(submoneys);
					if(subProIds.size()==0)
					{
						for(int i=0;i<count;i++)
						{
							subProIds.add(submoneys.get(i).getId());
						}
					}
				}
				budgetDetailInfo.setSubmoneys(submoneys);
				budgetDetailInfos.add(budgetDetailInfo);
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
		
		return budgetDetailInfos;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	 doGet(req, resp);
	}
}
