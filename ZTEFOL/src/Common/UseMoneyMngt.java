package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import Data.DBHandler.DBConnectorFactory;

public class UseMoneyMngt {
	private static Logger logger = FOLLogger.getLogger(CourseMngt.class);

	private static UseMoneyMngt useMoneyMngt = new UseMoneyMngt();

	public static String getProjectOwnerByInvoice(String invoiceNo) {
		String projectOwner = new String();
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		if (connection == null) {
			return projectOwner;
		}
		try {
			statement = connection.createStatement();
			String sql = "SELECT a.ownerno FROM PROJECT a,CLAIMS b WHERE a.projectid = b.productId AND b.invoiceNo = " + invoiceNo;
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				projectOwner = rs.getString("ownerno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return projectOwner;
	}
	
	
	public static String[] getPermitUseMoney(String userID, String timeID,
			String invoiceTypeName) {
		String[] money = { "0", "0", "0" };
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		if (connection == null) {
			return money;
		}
		try {
			statement = connection.createStatement();
			String sql = "select money, a.budgetId, useMoney from BUDGETDETAIL a , BUDGETINFO b where b.status=5 and b.no='"
					+ userID
					+ "' and a.budgetId=b.budgetId  and a.coursename='"
					+ invoiceTypeName
					+ "'"
					+ "and a.budgetId like '%"
					+ timeID
					+ "%'";
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				money[0] = rs.getString("budgetId");
				money[1] = rs.getString("money");
				if (rs.getString("useMoney") != null) {
					money[2] = rs.getString("useMoney");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return money;
	}

	public static String[] getPermitUseMoneyByDept(int deptId,
			String timeID, String invoiceTypeName) {
		String[] money = { "0", "0", "0" };
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		if (connection == null) {
			return money;
		}
		try {
			statement = connection.createStatement();
			String sql = "select money, a.budgetId, useMoney from BUDGETDETAIL a , BUDGETINFO b ,USERINFO c  where b.status=5 and b.no=c.no and a.budgetId=b.budgetId  and a.coursename='"
					+ invoiceTypeName
					+ "' and a.budgetId like '%"
					+ timeID
					+ "%' and c.deptid= '" + deptId + "' and (c.ruleid=2 or c.ruleid=4)";
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				money[0] = rs.getString("budgetId");
				money[1] = rs.getString("money");
				if (rs.getString("useMoney") != null) {
					money[2] = rs.getString("useMoney");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return money;
	}

	public static boolean updateUseMoney(String budgetId,
			String invoiceTypeName, float use) {
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		PreparedStatement statement = null;
		if (connection == null) {
			return false;
		}
		try {
			String sql = "update BUDGETDETAIL set useMoney=? where budgetId=? and courseName=?";
			statement = connection.prepareStatement(sql);
			statement.setFloat(1, use);
			statement.setString(2, budgetId);
			statement.setString(3, invoiceTypeName);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return false;
	}
	
	public static boolean deleteUseMoney(String invoiceTypeName,
			String invoiceNo, float use) {
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		String budgetId = getBudgetId(invoiceNo);
		PreparedStatement statement = null;
		if (connection == null) {
			return false;
		}
		try {
			String sql = "update BUDGETDETAIL set useMoney=useMoney-? where budgetId=? and courseName=?";
			statement = connection.prepareStatement(sql);
			statement.setFloat(1, use);
			statement.setString(2, budgetId);
			statement.setString(3, invoiceTypeName);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return false;
	}
	
	public static String getBudgetId(String invoiceNo) {
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		String budgetId = "";
		Statement statement = null;
		ResultSet rs = null;
		if (connection == null) {
			return budgetId;
		}
		try {
			statement = connection.createStatement();
			String sql = "select budgetId from CLAIMS where invoiceNo='" + invoiceNo +"'";
			rs = statement.executeQuery(sql);
			if(rs.next()){
				budgetId = rs.getString("budgetId");				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, null);
		}
		return budgetId;
	}
}
