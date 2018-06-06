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

public class CityDAO implements ICityDAO
{

	private static Logger logger = FOLLogger.getLogger(CityDAO.class);
	
	public List<DCity> getCityList(int provinceId) 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DCity> cityList = new ArrayList<DCity>();
			DCity city = null;
			if(connection == null)
			{
				return cityList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from CITY where provinceId = " + provinceId + " ");
				while (rs.next()) 
				{
					city = new DCity();
					city.setId(rs.getInt("id"));
					city.setName(rs.getString("name"));
					city.setProvinceId(rs.getInt("provinceId"));
					cityList.add(city);
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
			return cityList;
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
			String sql = "delete from CITY where id = " + id + "";
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
	
	public boolean add(DCity city) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into CITY values (?,?,?)";
			statement = connection.prepareStatement(sql);
		    statement.setInt(1, city.getId());			
		    statement.setInt(2, city.getProvinceId());
		    statement.setString(3,city.getName());
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
		CityDAO daoProductDAO = new CityDAO();
		System.out.println(daoProductDAO.getCityList(2).get(0).getName());
	}


}
