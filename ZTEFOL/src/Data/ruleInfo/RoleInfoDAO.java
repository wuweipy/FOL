package Data.ruleInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class RoleInfoDAO implements IRoleDAO 
{
	private static Logger logger = FOLLogger.getLogger(RoleInfoDAO.class);

	public ArrayList<DRole> getAllRoleInfos() 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<DRole> roleList = new ArrayList<DRole>();
		DRole roleInfo = null;
		if(connection == null)
		{
			return roleList;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from ROLE");
			while (rs.next()) 
			{
				roleInfo = new DRole();
				roleInfo.setId(rs.getInt("roleId"));
				roleInfo.setName(rs.getString("roleName"));
				roleList.add(roleInfo);
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
		return roleList;
	}

	public static void main(String[] args)
	{
		RoleInfoDAO dao = new RoleInfoDAO();
		System.out.println(dao.getAllRoleInfos().size());
	}
	
}
