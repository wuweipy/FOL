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

public class CurrencyDAO implements ICurrencyDAO
{
    private static Logger logger = FOLLogger.getLogger(CurrencyDAO.class);
	
	public List<DCurrency> getCurrencyList() 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DCurrency> currencyList = new ArrayList<DCurrency>();
			DCurrency currency = null;
			if(connection == null)
			{
				return currencyList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from CURRENCY");
				while (rs.next()) 
				{
					currency = new DCurrency();
					currency.setId(rs.getInt("id"));
					currency.setName(rs.getString("name"));
					currencyList.add(currency);
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
			return currencyList;
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
			String sql = "delete from CURRENCY where id = " + id + "";
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
	
	public boolean add(DCurrency currency) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into CURRENCY values (?,?)";
			statement = connection.prepareStatement(sql);
		    statement.setInt(1, currency.getId());
		    statement.setString(2,currency.getName());
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
		  CurrencyDAO curencyDAO = new CurrencyDAO();
	      System.out.println(curencyDAO.getCurrencyList().get(0).getName());
	}
	
}
