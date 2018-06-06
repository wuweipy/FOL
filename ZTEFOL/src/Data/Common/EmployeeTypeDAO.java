package Data.Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class EmployeeTypeDAO 
{

	private static Logger logger = FOLLogger.getLogger(EmployeeTypeDAO.class);
	
	public List<DEmployeeType> getEmployeeList() 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DEmployeeType> employeeList = new ArrayList<DEmployeeType>();
			DEmployeeType employee = null;
			if(connection == null)
			{
				return employeeList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from EMPLOYEETYPE;");
				while (rs.next()) 
				{
					employee = new DEmployeeType();
					employee.setId(rs.getInt("id"));
					employee.setName(rs.getString("name"));
					employeeList.add(employee);
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
			return employeeList;
	}
	
	public static void main(String[] args) {
		EmployeeTypeDAO daoProductDAO = new EmployeeTypeDAO();
		System.out.println(daoProductDAO.getEmployeeList().get(0).getName());
	}
} 
