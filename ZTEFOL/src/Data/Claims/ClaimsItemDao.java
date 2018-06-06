package Data.Claims;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;
import Data.UserDetail.UserDetailDao;

public class ClaimsItemDao implements IClaimsItemDAO
{

	private static Logger logger = FOLLogger.getLogger(UserDetailDao.class);
	
	public boolean createIClamsItem(ClaimsItem claimsItem) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
		    String sql = "insert into CLAIMITEM(startDate,endDate,startCity,desProvince,desCity,transportation," +
		    		"transportCost,accommodation,otherCost,InvoinceNO) values (?,?,?,?,?,?,?,?,?,?)";
		    statement = connection.prepareStatement(sql);
		    statement.setDate(1, claimsItem.getStartDate());
		    statement.setDate(2, claimsItem.getEndDate());
		    statement.setString(3, claimsItem.getStartCity());
		    statement.setInt(4, claimsItem.getDesProvince());
		    statement.setString(5, claimsItem.getDesCity());
		    statement.setInt(6, claimsItem.getTransportation());
		    statement.setFloat(7, claimsItem.getTransportCost());
		    statement.setFloat(8, claimsItem.getAccommodation());
		    statement.setFloat(9, claimsItem.getOtherCost());
		    statement.setString(10, claimsItem.getInvoinceNO());
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
		ClaimsItem claimsItem = new ClaimsItem();
		claimsItem.setInvoinceNO("222222");
		claimsItem.setStartDate(new Date(2013,7,1));
		claimsItem.setEndDate(new Date(2013,7,2));
		claimsItem.setStartCity("Œ‰∫∫");
		claimsItem.setDesProvince(1);
		claimsItem.setDesCity("…œ∫£");
		claimsItem.setTransportation(1);
		claimsItem.setTransportCost(1000);
		claimsItem.setAccommodation(2000);
		claimsItem.setOtherCost(1000);
		
		ClaimsItem[] items = new ClaimsItem[2];
		items[0] = claimsItem;
		items[1] = claimsItem;
		System.out.println(new ClaimsItemDao().createItems(items));
		
	}

	public boolean createItems(ClaimsItem[] ditems) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			 String delete = "delete from CLAIMITEM where InvoinceNO = '" + ditems[0].getInvoinceNO() + "'";
			 Statement sta = connection.createStatement();
			 sta.execute(delete);
			 sta.close();
			 
			 String sql = "insert into CLAIMITEM(startDate,endDate,startCity,desProvince,desCity,transportation," +
	    		"transportCost,accommodation,otherCost,InvoinceNO) values (?,?,?,?,?,?,?,?,?,?)";
	         statement = connection.prepareStatement(sql);
			for(int i = 0; i < ditems.length; i++)
			{
		    ClaimsItem claimsItem = ditems[i];
		    statement.setDate(1, claimsItem.getStartDate());
		    statement.setDate(2, claimsItem.getEndDate());
		    statement.setString(3, claimsItem.getStartCity());
		    statement.setInt(4, claimsItem.getDesProvince());
		    statement.setString(5, claimsItem.getDesCity());
		    statement.setInt(6, claimsItem.getTransportation());
		    statement.setFloat(7, claimsItem.getTransportCost());
		    statement.setFloat(8, claimsItem.getAccommodation());
		    statement.setFloat(9, claimsItem.getOtherCost());
		    statement.setString(10, claimsItem.getInvoinceNO());
		    statement.addBatch();
			}
		    statement.executeBatch();
		    return true;
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
     
}
