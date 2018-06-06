package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import Business.Budget.DelegateInfo;

import org.apache.log4j.Logger;

import Data.DBHandler.DBConnectorFactory;

public class DelegateMngt {
	private static Logger logger = FOLLogger.getLogger(DelegateMngt.class);
	
	private static DelegateMngt delegateMngt = new DelegateMngt();
	
	private HashMap<Integer, String> delegateMap = new HashMap<Integer, String>();	
	
	private DelegateMngt()
	{
		refresh();
	}
	public void refresh() 
	{
		delegateMap = getDelegate();
	}
	
	public static DelegateMngt getInstance()
	{
		return delegateMngt;
	}

	public HashMap<Integer, String> getDelegateMap() 
	{
		return delegateMap;
	}
	
    private HashMap<Integer, String> getDelegate() {
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> delegateMap1 = new HashMap<Integer, String>();
		if(connection == null)
		{
			return delegateMap1;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from ACTION");
			while (rs.next()) 
			{
				delegateMap1.put(Integer.valueOf(rs.getString("id")), rs.getString("name"));
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
		return delegateMap1;		
	}
    
    public static ArrayList<DelegateInfo> getDelegateInfo(String no){
    	
    	ArrayList<DelegateInfo> delegateInfos = new ArrayList<DelegateInfo>();
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return delegateInfos;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from DELEGATE where no='" + no + "'");
			while (rs.next()) 
			{
				DelegateInfo delegateInfo = new DelegateInfo();
				delegateInfo.setNo(no);
				delegateInfo.setAgency(rs.getString("agency"));
				delegateInfo.setActionId(Integer.valueOf(rs.getString("actionId")));
				delegateInfos.add(delegateInfo);
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
    	return delegateInfos;
    }    

	public static boolean addDelegate(int actionId, String agencyNo,
			String userID) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into DELEGATE (no,agency,actionId) values (?,?,?)";
			statement = connection.prepareStatement(sql);
		    statement.setString(1, userID);
		    statement.setString(2,agencyNo);
		    statement.setInt(3,actionId);
			return statement.executeUpdate() == 1;	 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		} 
		return false;
	}

	public static boolean deleteDelegate(String userID, String agencyNo,
			int actionId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "delete from DELEGATE where no = '" + userID + "' and agency='" + agencyNo + "' and actionId=" + actionId;
			statement = connection.prepareStatement(sql);
			return statement.executeUpdate() == 1;	 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}
	
	/*得到总经理预算委托审批人*/
	public static String getDelegateBudgetNo(int actionId) {
		String nextApprovalId = "";
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return nextApprovalId;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
			String sql = "select * from DELEGATE where actionId=" + actionId;
			rs = statement.executeQuery(sql);
			if(rs.next())
			{
				nextApprovalId = rs.getString("agency");
			} 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return nextApprovalId;
	}
	
	/*判断是否是委托*/
	public static boolean wheatherDelegate(String agencyNo, int actionId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
			String sql = "select * from DELEGATE where agency='" + agencyNo + "' and actionId=" + actionId;
			rs = statement.executeQuery(sql);
			if(rs.next())
			{
				return true;
			} 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}
	
    public static ArrayList<String> getBudgetAllNos(String no){
    	
    	ArrayList<String> budgetAllNos = new ArrayList<String>();
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return budgetAllNos;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from DELEGATE where agency='" + no + "' and actionId=1");
			while (rs.next()) 
			{
				budgetAllNos.add(rs.getString("no"));
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
    	return budgetAllNos;
    }
    
	public static boolean createBudget(String budgetId, int count, String userID, String userName, String realID, Integer pId,String pName,ArrayList<String> courseName,
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
		    String sql = "insert into BUDGETINFO(budgetId,no,username,agencyNo,billTime,status,projectId,projectName)" +
            "values (?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);		
		    statement.setString(1, budgetId);
		    statement.setString(2,realID);
		    statement.setString(3,userName);
		    statement.setString(4,userID);
		    statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
		    statement.setInt(6, status);
		    statement.setInt(7, pId);
		    statement.setString(8,pName);
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
	
}
