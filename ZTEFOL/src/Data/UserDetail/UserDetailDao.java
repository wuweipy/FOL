package Data.UserDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Common.UserMngt;
import Data.DBHandler.DBConnectorFactory;

public class UserDetailDao implements IUserDetailDAO {

	private static Logger logger = FOLLogger.getLogger(UserDetailDao.class);
	
	public DUserDetail getUserDetail(String no) {
		
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		DUserDetail dUserDetail = null; 
		if(connection == null)
		{
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from USERDETAIL where NO = '" + no + "'");
			while (rs.next()) 
			{
				dUserDetail = new DUserDetail();
				dUserDetail.setId(rs.getInt("ID"));
				dUserDetail.setNo(rs.getString("NO"));
				dUserDetail.setAllNo(rs.getString("ALLNO"));
				dUserDetail.setDeptID(rs.getInt("DEPTID"));
				dUserDetail.setEmail(rs.getString("EMAIL"));
				dUserDetail.setTel(rs.getString("TEL"));
				dUserDetail.setProductNo(rs.getInt("PRODUCTID"));
				dUserDetail.setTechTitleNo(rs.getInt("TECHTITLEID"));
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
		return dUserDetail;
	}

	public boolean saveUserDetail(DUserDetail userDetail) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
		    String sql = "update USERDETAIL set EMAIL=?,TEL=? where NO=?";
		    statement = connection.prepareStatement(sql);
		    statement.setString(1, userDetail.getEmail());
		    statement.setString(2, userDetail.getTel());
		    statement.setString(3, userDetail.getNo());
		    if(statement.executeUpdate() == 1)
		    {
		    	UserMngt.getInstance().setDirty(true);
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
	
	public boolean addUserDetail(DUserDetail userDetail) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
		    String sql = "insert into USERDETAIL(DEPTID,EMAIL,TEL,PRODUCTID,NO,ALLNO) values(?,?,?,?,?,?)";
		    statement = connection.prepareStatement(sql);
		    statement.setInt(1, userDetail.getDeptID());
		    statement.setString(2, userDetail.getEmail());
		    statement.setString(3, userDetail.getTel());
		    statement.setInt(4, userDetail.getProductNo());
		    statement.setString(5, userDetail.getNo());
		    statement.setString(6, userDetail.getAllNo());
		    if(statement.executeUpdate() == 1)
		    {
		    	UserMngt.getInstance().setDirty(true);
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
	
	public static void main(String[] args) {
		
		UserDetailDao uDao = new UserDetailDao();
		DUserDetail detail = uDao.getUserDetail("10144183");
		System.out.println(detail.getAllNo());
		
		detail.setDeptID(2);
		System.out.println(uDao.saveUserDetail(detail));
	}
}
