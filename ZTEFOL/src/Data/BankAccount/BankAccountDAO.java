package Data.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class BankAccountDAO implements IBankAccountDAO
{
	
	private static Logger logger = FOLLogger.getLogger(BankAccountDAO.class);

	public DBankAccount getAccount(String userID) 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		DBankAccount dBankAccount = null; 
		if(connection == null)
		{
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BANKACCOUNT where NO = '" + userID + "'");
			while (rs.next()) 
			{
				dBankAccount = new DBankAccount();
				dBankAccount.setAccount(rs.getString("account"));
				dBankAccount.setAccountName(rs.getString("accountName"));
				dBankAccount.setAccountBank(rs.getString("bankName"));
				dBankAccount.setBank(rs.getInt("bankId"));
				dBankAccount.setCity(rs.getInt("cityId"));
				dBankAccount.setProvince(rs.getInt("proviceId"));
				dBankAccount.setCurrency(rs.getInt("currencyId"));
				dBankAccount.setNo(userID);
				return dBankAccount;
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
		return dBankAccount;
	}
	
	public static void main(String[] args) {
/*		BankAccountDAO bankAccountDAO = new BankAccountDAO();
		DBankAccount dAccount = bankAccountDAO.getAccount("10144183");
		
		dAccount.setAccountBank("招商银行洪山支行");
		System.out.println(bankAccountDAO.saveBankAccount(dAccount));
		
		dAccount.setNo("10144182");
		System.out.println(bankAccountDAO.createBankAccount(dAccount));*/
		
		BankAccountDAO bankAccountDAO = new BankAccountDAO();
		System.out.println(bankAccountDAO.deleteBankAccount("10144183"));
	}

	public boolean saveBankAccount(DBankAccount dBankAccount) 
	{
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "update BANKACCOUNT set currencyId=?,proviceId=?,cityId=?,bankId=?,bankName=?," +
					"accountName=?,account=? where NO=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, dBankAccount.getCurrency());
			statement.setInt(2, dBankAccount.getProvince());
			statement.setInt(3, dBankAccount.getCity());
			statement.setInt(4, dBankAccount.getBank());
			statement.setString(5, dBankAccount.getAccountBank());
			statement.setString(6, dBankAccount.getAccountName());
			statement.setString(7, dBankAccount.getAccount());
			statement.setString(8, dBankAccount.getNo());
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

	public boolean createBankAccount(DBankAccount dBankAccount) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into BANKACCOUNT(currencyId,proviceId,cityId,bankId,bankName,accountName,account,NO) values " +
					"(?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, dBankAccount.getCurrency());
			statement.setInt(2, dBankAccount.getProvince());
			statement.setInt(3, dBankAccount.getCity());
			statement.setInt(4, dBankAccount.getBank());
			statement.setString(5, dBankAccount.getAccountBank());
			statement.setString(6, dBankAccount.getAccountName());
			statement.setString(7, dBankAccount.getAccount());
			statement.setString(8, dBankAccount.getNo());
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

	public boolean deleteBankAccount(String userId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "delete from BANKACCOUNT where NO = '" + userId +"'";
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

}
