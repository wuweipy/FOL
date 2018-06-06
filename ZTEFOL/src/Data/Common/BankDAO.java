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

public class BankDAO implements IBankDAO
{
    private static Logger logger = FOLLogger.getLogger(BankDAO.class);
	
	public List<DBank> getBankList() 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DBank> bankList = new ArrayList<DBank>();
			DBank bank = null;
			if(connection == null)
			{
				return bankList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from BANK");
				while (rs.next()) 
				{
					bank = new DBank();
					bank.setId(rs.getInt("id"));
					bank.setName(rs.getString("name"));
					bankList.add(bank);
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
			return bankList;
	}
	
    public boolean add(DBank dBank) 
    {
        Connection conn = DBConnectorFactory.getConnectorFactory().getConnection();
        if(conn == null)
        {
            return false;
        }
 
        PreparedStatement statement = null;
        try 
        {
            String sql = "insert into BANK values (?,?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, dBank.getId());
            statement.setString(2, dBank.getName());
 
            return statement.executeUpdate() == 1;
        } 
        catch (SQLException e)
        {
            logger.error(e.getMessage());
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(conn, statement, null);
        }

        return false;
    }
 
    public boolean delete(int id)
    {
        Connection conn = DBConnectorFactory.getConnectorFactory().getConnection();
        if(conn == null)
        {
            return false; 
        }
 
        PreparedStatement statement = null;
        try 
        {
            String sql = "delete from BANK where id =" + id + "";
            statement = conn.prepareStatement(sql);
 
            return statement.executeUpdate() == 1;
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage());	
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(conn, statement, null);
        }
 
        return false;
    }
 
    public static void main(String[] args) {
          BankDAO bankDAO = new BankDAO();
          System.out.println(bankDAO.getBankList().get(1).getName());
       
          System.out.println("bankDAO.delete(1)--");
          System.out.println(bankDAO.delete(1));
          DBank bank = new DBank();
          bank.setId(11);
          bank.setName("建设银行");
          System.out.println(bankDAO.add(bank));
    }

}
