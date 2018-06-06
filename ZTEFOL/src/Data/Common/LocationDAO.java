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

public class LocationDAO {
  

	private static Logger logger = FOLLogger.getLogger(LocationDAO.class);
	
	public List<DLocation> getLocationList() 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DLocation> locationList = new ArrayList<DLocation>();
			DLocation location = null;
			if(connection == null)
			{
				return locationList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from LOCATION;");
				while (rs.next()) 
				{
					location = new DLocation();
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
	      LocationDAO daoLocationDAO = new LocationDAO();
	      System.out.println(daoLocationDAO.getLocationList().get(0).getName());
	}
	
}
