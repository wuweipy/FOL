package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Business.Budget.BudgetDetailInfo;
import Data.DBHandler.DBConnectorFactory;

public class PermitBudgetMngt {
	private static Logger logger = FOLLogger.getLogger(PermitBudgetMngt.class);

	private static PermitBudgetMngt permitBudgetMngt = new PermitBudgetMngt();
	
	public static ArrayList<String> getBudgetIds(String dateNo, int status)
	{
		ArrayList<String> budgetIds = new ArrayList<String>();
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		Statement statement = null;
		ResultSet rs = null;
		if (connection == null) {
			return budgetIds;
		}
		try {
			statement = connection.createStatement();
			String sql = "select budgetId from BUDGETINFO where status='"
					+ status
					+ "' and budgetId like '%"
					+ dateNo
					+ "%'";
			rs = statement.executeQuery(sql);
			while(rs.next()) 
			{
				budgetIds.add(rs.getString("budgetId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return budgetIds;
	}
	
	public static boolean updateFinanceBudgetInfo(ArrayList<String> budgetIds, String approvalId, int nextStatus)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if (connection == null) {
			return false;
		}
	    try 
		{
	    	for(int i=0; i<budgetIds.size(); i++)
	    	{
		        String budgetId = budgetIds.get(i);
			  	String sql = "update BUDGETINFO set finance=?, financeTime=?, status=? where budgetId=?";
				statement = connection.prepareStatement(sql);	
				statement.setString(1,approvalId);
				statement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));	
				statement.setInt(3,nextStatus);
				statement.setString(4,budgetId);
				statement.executeUpdate();
				
		    	String sql1 = "update BUDGETDETAIL set financeOpinion=? where budgetId=?";
				statement = connection.prepareStatement(sql1);
				statement.setString(1,"同意");
				statement.setString(2,budgetId);			
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
	
	public static boolean updateInstituteBudgetInfo(ArrayList<String> budgetIds, String approvalId, int nextStatus)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if (connection == null) {
			return false;
		}
	    try 
		{
	    	for(int i=0; i<budgetIds.size(); i++)
	    	{
		        String budgetId = budgetIds.get(i);
			  	String sql = "update BUDGETINFO set institute=?, instituteTime=?, status=? where budgetId=?";
				statement = connection.prepareStatement(sql);	
				statement.setString(1,approvalId);
				statement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));	
				statement.setInt(3,nextStatus);
				statement.setString(4,budgetId);
				statement.executeUpdate();
				
		    	String sql1 = "update BUDGETDETAIL set instituteOpinion=? where budgetId=?";
				statement = connection.prepareStatement(sql1);
				statement.setString(1,"同意");
				statement.setString(2,budgetId);			
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
	
	public static boolean updateManagerBudgetInfo(ArrayList<String> budgetIds, String approvalId, int nextStatus,List<BudgetDetailInfo> adjustBudgetList)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if (connection == null) {
			return false;
		}
	    try 
		{
	    	connection.setAutoCommit(false);
	    	statement = connection.prepareStatement("update BUDGETDETAIL set money = ? where budgetId = ? and courseName = ?");	
	    	for(int i = 0; i < adjustBudgetList.size(); i++)
	    	{
	    		BudgetDetailInfo info = adjustBudgetList.get(i);
	    		statement.setInt(1, info.getMoney());
	    		statement.setString(2, info.getBudgetId());
	    		statement.setString(3, info.getCourseName());
	    		statement.addBatch();
	    	}
	    	statement.executeBatch();
	    	DBConnectorFactory.getConnectorFactory().freeDB(null, statement, null);
	    	
	    	statement = connection.prepareStatement("update BUDGETINFO set manager=?, managerTime=?, status=? where budgetId=?");	
	    	for(int i=0; i<budgetIds.size(); i++)
	    	{
		        String budgetId = budgetIds.get(i);	
				statement.setString(1,approvalId);
				statement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));	
				statement.setInt(3,nextStatus);
				statement.setString(4,budgetId);
				statement.addBatch();
	    	}
	    	statement.executeBatch();
	    	DBConnectorFactory.getConnectorFactory().freeDB(null, statement, null);
	    	
	    	statement = connection.prepareStatement("update BUDGETDETAIL set managerOpinion=? where budgetId=?");	
	    	for(int i=0; i<budgetIds.size(); i++)
	    	{
		        String budgetId = budgetIds.get(i);	
				statement.setString(1,"同意");
				statement.setString(2,budgetId);			
				statement.executeUpdate();
				statement.addBatch();
	    	}
	    	statement.executeBatch();
	    	connection.commit();
			return true;			
		}
		 catch (SQLException e) 
		 {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("error to rollback the budget manager approval");
			}
			e.printStackTrace();
			logger.error(e.getMessage());
		 }
		 finally
		 {
			 try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("error to set auto commit true :" + e.getMessage());
				e.printStackTrace();
			}
			 DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		 } 
		 return false;
	}
	
	public static boolean updateManagerBudgetInfo(ArrayList<String> budgetIds, String approvalId, int nextStatus)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if (connection == null) {
			return false;
		}
	    try 
		{
	    	for(int i=0; i<budgetIds.size(); i++)
	    	{
		        String budgetId = budgetIds.get(i);
			  	String sql = "update BUDGETINFO set manager=?, managerTime=?, status=? where budgetId=?";
				statement = connection.prepareStatement(sql);	
				statement.setString(1,approvalId);
				statement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));	
				statement.setInt(3,nextStatus);
				statement.setString(4,budgetId);
				statement.executeUpdate();
				
		    	String sql1 = "update BUDGETDETAIL set managerOpinion=? where budgetId=?";
				statement = connection.prepareStatement(sql1);
				statement.setString(1,"同意");
				statement.setString(2,budgetId);			
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
}
