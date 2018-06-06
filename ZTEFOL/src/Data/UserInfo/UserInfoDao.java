package Data.UserInfo;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import Business.UserDetail.BUserDetail;
import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;
import Data.UserDetail.DUserDetail;
import Data.UserDetail.UserDetailDao;

public class UserInfoDao implements IUserDAO {
	private static Logger logger = FOLLogger.getLogger(UserInfoDao.class);

	public DUser getUser(String no, String password) {
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		DUser dUser = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from USERINFO where NO = '"
					+ no + "'");
			while (rs.next()) {
				String name = rs.getString("USERNAME");
				String psw = rs.getString("PASSWORD");
				int roleId = rs.getInt("RULEID");
				if (!password.equals(psw)) {
					return null;
				}
				dUser = new DUser(name, psw, no, roleId);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, rs);
		}
		return dUser;
	}

	public static void main(String[] args) {
		UserInfoDao userInfoDao = new UserInfoDao();
		DUser dUser = userInfoDao.getUser("10144183", "admin");
		System.out.println(dUser.getName());
	}

	public boolean addUser(DUser userInfo) 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			logger.error("connect db failed!");
			return false;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			String sql = "insert into USERINFO values('" + userInfo.getName() 
			    		+ "','" + userInfo.getPassword() + "','" + userInfo.getNo() 
			    		+ "', " + userInfo.getDeptId() + " , " + userInfo.getRoleId() 
			    		+ ")";
			logger.info(sql);  		
			statement = connection.createStatement();
		    int rst = statement.executeUpdate(sql);
		    if(rst == 1)
		    {
		        DUserDetail userDetail = new DUserDetail();
		        userDetail.setNo(userInfo.getNo());
		        userDetail.setAllNo(userInfo.getNo());
		        userDetail.setDeptID(userInfo.getDeptId());
		        userDetail.setProductNo(userInfo.getDeptId());
		        userDetail.setEmail("");
		        userDetail.setTel("");
		    	return new UserDetailDao().addUserDetail(userDetail);
		    	
		    }
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return false;

	}

	public boolean deleteUser(String no) {

		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		if (connection == null) {
			logger.error("connect db failed!");
			return false;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			int rst = statement
					.executeUpdate("Delete from USERINFO where NO = '" + no
							+ "'");
			if(rst == 1)
			{
				rst = statement
					.executeUpdate("Delete from USERDETAIL where NO = '" + no
							+ "'");
				return rst == 1;
			}
			else 
				return false;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, rs);
		}
		return false;
	}

	public ArrayList<DUser> getAllUsers() {
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		ArrayList<DUser> users = new ArrayList<DUser>();
		DUser dUser = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from USERINFO ORDER BY NO");
			while (rs.next()) {
				String name = rs.getString("USERNAME");
				String psw = rs.getString("PASSWORD");
				String no = rs.getString("NO");
				int deptId = rs.getInt("DEPTID");
				int roleId = rs.getInt("RULEID");
				dUser = new DUser(name, psw, no, deptId, roleId);
				users.add(dUser);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, rs);
		}
		return users;
	}
	
	public ArrayList<DUserDetail> getAllUserDetails() {
		Connection connection = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		ArrayList<DUserDetail> users = new ArrayList<DUserDetail>();
		DUserDetail dUser = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from USERDETAIL ");
			while (rs.next()) {
				dUser = new DUserDetail();
				dUser.setNo(rs.getString("NO"));
				dUser.setEmail(rs.getString("EMAIL"));
				users.add(dUser);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection,
					statement, rs);
		}
		return users;
	}
	
	public boolean modifyPassword(DUser userInfo, String newPassword) 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			logger.error("connect db failed!");
			return false;
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try 
		{
			
			String sql = "update USERINFO set PASSWORD=? where NO=?";
		    statement = connection.prepareStatement(sql);
		    statement.setString(1, newPassword);
		    statement.setString(2, userInfo.getNo());
		    return statement.executeUpdate() == 1;
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return false;

	}
	
	public	static  boolean updateDept(String NO, int deptId) 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			logger.error("connect db failed!");
			return false;
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try 
		{			
			String sql = "update USERINFO set DEPTID=? where NO=?";
		    statement = connection.prepareStatement(sql);
		    statement.setInt(1, deptId);
		    statement.setString(2, NO);
		    return statement.executeUpdate() == 1;
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return false;
	}
}
