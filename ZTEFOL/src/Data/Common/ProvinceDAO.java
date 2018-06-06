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

public class ProvinceDAO {
  

	private static Logger logger = FOLLogger.getLogger(ProvinceDAO.class);
	
	public List<DProvince> getProvinceList() 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DProvince> locationList = new ArrayList<DProvince>();
			DProvince location = null;
			if(connection == null)
			{
				return locationList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from PROVINCE;");
				while (rs.next()) 
				{
					location = new DProvince();
					location.setId(rs.getInt("id"));
					location.setName(rs.getString("name"));
					locationList.add(location);
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
			return locationList;
	}
	
	public static void main(String[] args) {
	      ProvinceDAO daoLocationDAO = new ProvinceDAO();
	      System.out.println(daoLocationDAO.getProvinceList().get(1).getName());
	}
	
}
