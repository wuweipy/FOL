package Data.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class DeptDAO implements IDeptDAO
{
	private static Logger logger = FOLLogger.getLogger(DeptDAO.class);

	public List<DDept> getDepts() 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		List<DDept> deptsList = new ArrayList<DDept>();
		DDept dept = null;
		if(connection == null)
		{
			return deptsList;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from DEPT;");
			while (rs.next()) 
			{
				dept = new DDept();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setInstitute(rs.getInt("institute"));
				deptsList.add(dept);
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
		return deptsList;
	}

	
	public boolean delete(int id) 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "delete from DEPT where id = " + id + "";
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
	
	public boolean add(DDept dept) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into DEPT values (?,?,?)";
			statement = connection.prepareStatement(sql);
		    statement.setInt(1, dept.getId());
		    statement.setString(2,dept.getName());
		    statement.setInt(3,dept.getInstitute());
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
	
	public static void main(String[] args) {
		DeptDAO deptDAO = new DeptDAO();
		List<DDept> list = deptDAO.getDepts();
		System.out.println(list.get(0).getName());
		System.out.println(deptDAO.delete(6));
		DDept dept = new DDept();
		dept.setId(6);
		dept.setName("≤‚ ‘");
		System.out.println(deptDAO.add(dept));
	}



	
}
