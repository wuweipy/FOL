package Data.Common.Countersign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class CountersignDao implements ICountersignDao{
    private static Logger logger = FOLLogger.getLogger(CountersignDao.class);
	
	public List<DCountersign> getCountersignList(int status,String approvalId,String invoiceNo,int invoiceType,String no,int deptID,int productId) 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DCountersign> countersignList = new ArrayList<DCountersign>();
			DCountersign countersign = null;
			if(connection == null)
			{
				return countersignList;
			}
			Statement statement = null;
			ResultSet rs = null;
			String queryStr = " ";
			if(!invoiceNo.equalsIgnoreCase("-1"))
				queryStr += "and invoiceNo = '" + invoiceNo + "' ";
			if(!(invoiceType==-1))
				queryStr += "and invoiceType = " + invoiceType + " ";	
			if(!no.equalsIgnoreCase("-1"))
				queryStr += "and no = '" + no + "' ";
			if(!(deptID==-1))
				queryStr += "and deptID = '" + deptID + "' ";
			if(!(productId==-1))
				queryStr += "and productId = '" + productId + "' ";
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from CLAIMS where no in (SELECT NO FROM USERINFO) and status = " + status + " and approvalId = '" + approvalId + "' " + queryStr);
				while (rs.next()) 
				{
					countersign = new DCountersign();
					countersign.setId(rs.getInt("id"));
					countersign.setPayType(rs.getInt("payType"));
					countersign.setInvoiceType(rs.getInt("invoiceType"));
					countersign.setProductId(rs.getInt("productId"));
					countersign.setBillLoaction(rs.getInt("billLocation"));
					countersign.setStatus(rs.getInt("status"));
					countersign.setAccountAdjust(rs.getInt("accountAdjust"));
					countersign.setEmployLevel(rs.getInt("employeeLevel"));
					countersign.setCurrencyType(rs.getInt("currencyType"));
					countersign.setHedgeAccount(rs.getInt("hedgeAccount"));
					countersign.setNo(rs.getString("no"));
					countersign.setInvoiceNo(rs.getString("invoiceNo"));
					countersign.setBillNo(rs.getString("billNo"));
					countersign.setSummary(rs.getString("summary"));
					countersign.setHasBill(rs.getBoolean("hasBill"));
					countersign.setDirectToFinance(rs.getBoolean("directToFinance"));
					countersign.setDeptId(rs.getInt("deptID"));	
					countersign.setTotalFee(rs.getInt("totalFee"));
					countersign.setSubmitDate(rs.getTimestamp("submitDate"));
					countersign.setApprovalAmount(rs.getInt("approvalAmount"));					
					countersignList.add(countersign);
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
			return countersignList;
	}
	
	public boolean permit(ArrayList<String> invoices,String approvalId,int status,String comment,String nextApprovalId, String appDate, String approvalName)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
	    try 
		{
	    	connection.setAutoCommit(false);
			for(int i = 0; i < invoices.size(); i++)
			{
		  	String sql = "update CLAIMS set status=?, approvalId=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);

			statement.setInt(1,3);
			statement.setString(2,nextApprovalId);			
		    statement.setString(3,invoices.get(i));		
			statement.executeUpdate();
			
		    sql = "insert into APPROVALINFO(invoiceNo,status,approvalId,approvalName,comment,appState,appDate)" +
            "values (?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);		
		    statement.setString(1, invoices.get(i));
		    statement.setInt(2,status);
		    statement.setString(3, approvalId);
		    statement.setString(4, approvalName);		    
		    statement.setString(5,comment);		
            statement.setInt(6, 1);
            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
		    }
			connection.commit();
			return true;
		}
		 catch (SQLException e) 
		 {
	        	try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
					e1.printStackTrace();
				}
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

	public boolean permit(ArrayList<String> invoices,String approvalId,int status,String comment,String nextApprovalId, String appDate, String approvalName ,String budgetId)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
	    try 
		{
	    	connection.setAutoCommit(false);
			for(int i = 0; i < invoices.size(); i++)
			{
		  	String sql = "update CLAIMS set status=?, approvalId=?, budgetId=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);

			statement.setInt(1,3);
			statement.setString(2,nextApprovalId);
			statement.setString(3,budgetId);	
		    statement.setString(4,invoices.get(i));		
			statement.executeUpdate();
			
		    sql = "insert into APPROVALINFO(invoiceNo,status,approvalId,approvalName,comment,appState,appDate)" +
            "values (?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);		
		    statement.setString(1, invoices.get(i));
		    statement.setInt(2,status);
		    statement.setString(3, approvalId);
		    statement.setString(4, approvalName);		    
		    statement.setString(5,comment);		
            statement.setInt(6, 1);
            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
		    }
            connection.commit();
			return true;
		}
		 catch (SQLException e) 
		 {
	        	try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
					e1.printStackTrace();
				}
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
	
	public void notPermit(ArrayList<String> invoices,String approvalId,int status,String comment, String appDate, String approvalName)
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
//		if(connection == null)
//		{
//			return false;
//		}
		PreparedStatement statement = null;
	    try 
		{
	    	connection.setAutoCommit(false);
			for(int i = 0; i < invoices.size(); i++)
			{
		  	String sql = "update CLAIMS set status=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);

			statement.setInt(1,5);
		    statement.setString(2,invoices.get(i));		
			statement.executeUpdate();
			
		    sql = "insert into APPROVALINFO(invoiceNo,status,approvalId,approvalName,comment,appState,appDate)" +
            "values (?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);		
		    statement.setString(1, invoices.get(i));
		    statement.setInt(2,status);
		    statement.setString(3, approvalId);
		    statement.setString(4, approvalName);		    
		    statement.setString(5,comment);	
            statement.setInt(6, 0);
            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));	    
			statement.executeUpdate();
		    }
			connection.commit();
		}
		 catch (SQLException e) 
		 {
	        	try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
					e1.printStackTrace();
				}
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
//		return false;
	}
	
	
	public static void main(String[] args) {
		  CountersignDao countersignDao = new CountersignDao();
//	      System.out.println(countersignDao.getCountersignList(2,"10144183","12345678910",1,"-1").get(0).isDirectToFinance());
	}
}
