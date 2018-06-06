package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Business.Budget.BudgetInfo;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Common.UserMngt;
import Data.DBHandler.DBConnectorFactory;
/**
 * ‘§À„≤∆ŒÒ…Û≈˙
 */
public class BudgetFinanceServlet extends HttpServlet{
	private static Logger logger = FOLLogger.getLogger(BudgetFinanceServlet.class);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	    HttpSession session = req.getSession();
		String userID = (String)session.getAttribute("userID");
		String action = req.getParameter("action");
		int status = 2;
		int role = UserMngt.getInstance().getUserInfoMap().get(userID).getRoleId();
		if(role == 4)
		{
			if(action!=null && action.equals("query")){
				req.setAttribute("budgetInfos", getApprovalBudget(req,status,getBudgetId(req),getNo(req),getProject(req)));
			}
			else{
				req.setAttribute("budgetInfos", getApprovalBudget(req,status,"-1","-1","-1"));
			}
				
		}
		else 
		{
			List<BudgetInfo> budgetInfos = new ArrayList<BudgetInfo>();
			req.setAttribute("budgetInfos", budgetInfos);
		}
		req.setAttribute("projectInfo", ProjectMngt.getInstance().getAllProjectInfo());
		req.getRequestDispatcher("BudgetFinanceApproval.jsp").forward(req, resp);
	}
	
	private String getBudgetId(HttpServletRequest req)
	{
		String budgetId = " ";
		if(ParameterUtil.getChineseString(req, "budgetId").equals(""))
			budgetId = "-1";
		else 
			budgetId = ParameterUtil.getChineseString(req, "budgetId");
		return budgetId;
	}
	
	private String getNo(HttpServletRequest req)
	{
		String no = " ";
		String name = ParameterUtil.getChineseString(req, "userName","utf-8");
		if(name.equals("")){
			no = "-1";
		}
		else 
		{
			if(UserMngt.getInstance().getUserInfoMapName().get(name)==null)
				no= "-2";
			else
			no = UserMngt.getInstance().getUserInfoMapName().get(name).getNo();
		}
		return no;
	}
	
	private String getProject(HttpServletRequest req)
	{   
		String department = "-1";
		if(!(ParameterUtil.getChineseString(req, "project").equals(""))){
			department = ParameterUtil.getChineseString(req, "project","utf-8");			
		}
        return department;		
	}
	
	private List<BudgetInfo> getApprovalBudget(HttpServletRequest req,int status, String budgetId, String no, String projectName) {
		// TODO Auto-generated method stub			
		
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	List<BudgetInfo> budgetInfos = new ArrayList<BudgetInfo>();
    	BudgetInfo budgetInfo = null;
		String queryStr = " ";
		if(!budgetId.equalsIgnoreCase("-1"))
			queryStr += "and budgetId = '" + budgetId + "' ";
		if(!no.equalsIgnoreCase("-1"))
			queryStr += "and no = '" + no + "' ";
		if(connection == null)
		{
			return budgetInfos;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BUDGETINFO,USERINFO where BUDGETINFO.no=USERINFO.no and status = '" + status + "' "  + queryStr);
			while (rs.next()) 
			{
				budgetInfo = new BudgetInfo();
				budgetInfo.setBudgetId(rs.getString("budgetId"));
				budgetInfo.setNo(rs.getString("no"));
				budgetInfo.setStatus(status);
				budgetInfo.setSubmitDate(rs.getTimestamp("billTime"));
				budgetInfo.setTotalMoney(gettotalMoney(rs.getString("budgetId")));
				budgetInfo.setProjectName(rs.getString("projectName"));
				budgetInfo.setName(getName(rs.getString("no")));
				if(rs.getString("agencyNo")!=null){
					budgetInfo.setAgencyNo(rs.getString("agencyNo"));
					budgetInfo.setAgencyName(getName(rs.getString("agencyNo")));
				}
				else{
					budgetInfo.setAgencyNo("-");
					budgetInfo.setAgencyName("-");
				}
				if(!projectName.equals("-1")){
					if(budgetInfo.getProjectName().equals(projectName))
					{				
						budgetInfos.add(budgetInfo);
					}
				}
				else
					budgetInfos.add(budgetInfo);

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
		return budgetInfos;
		
	}

	private String getName(String no) {
		// TODO Auto-generated method stub
		return UserMngt.getInstance().getUserInfoMap().get(no).getName();
	}

	private String getDept(String no) {

		return DeptMngt.getInstance().getDepts().get(UserMngt.getInstance().getUserInfoMap().get(no).getDeptId());
	}

	private int gettotalMoney(String budgetId) {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		int totalMoney = 0;
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	Statement statement = null;
		try{
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from BUDGETDETAIL where budgetId = '" + budgetId + "'");
			while(rs.next())
			{
				totalMoney += rs.getInt("money");				
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
		return totalMoney;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	 doGet(req, resp);
	}
	
	
	
	
}

