package Business.BankAccount;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.BankAccount.BankAccountDAO;
import Data.BankAccount.DBankAccount;
import Data.BankAccount.IBankAccountDAO;
import Data.BankAccount.TestBankAccountDAO;

public class BBankAccountHandler {
	
	private static Logger logger = FOLLogger.getLogger(BBankAccountHandler.class);

	public BBankAccount getAccount(String userID)
	{
		logger.info("user : " + userID + " get account");
		IBankAccountDAO dao = new BankAccountDAO();
		DBankAccount dBankAccount = dao.getAccount(userID);
		if(dBankAccount == null) 
			return null;
		return new BBankAccount(dBankAccount);
	}
	
	public String[] getAccountStrings(String userID)
	{
		BBankAccount bBankAccount = getAccount(userID);
		if(bBankAccount == null)
		{
			return null;
		}
		return bBankAccount.getStrings();
	}

	public boolean saveBankAccount(BBankAccount bankAccount, String userId) {
		DBankAccount dBankAccount = getAccountFromBAccount(bankAccount, userId);
		IBankAccountDAO dao = new BankAccountDAO();
		return dao.saveBankAccount(dBankAccount);
	}
	
	private DBankAccount getAccountFromBAccount(BBankAccount bankAccount, String userId)
	{
		DBankAccount dBankAccount = new DBankAccount();
		dBankAccount.setCurrency(bankAccount.getCurrency());
		dBankAccount.setProvince(bankAccount.getProvince());
		dBankAccount.setCity(bankAccount.getCity());
		dBankAccount.setBank(bankAccount.getBank());
		dBankAccount.setAccountBank(bankAccount.getAccountBank());
		dBankAccount.setAccountName(bankAccount.getAccountName());
		dBankAccount.setAccount(bankAccount.getAccount());
		dBankAccount.setNo(userId);
		return dBankAccount;
	}

	public boolean createBankAccount(BBankAccount bankAccount, String userId) {
		DBankAccount dBankAccount = getAccountFromBAccount(bankAccount, userId);
		IBankAccountDAO dao = new BankAccountDAO();
		return dao.createBankAccount(dBankAccount);
	}

	public boolean deleteBankAccount(String userId) 
	{
		IBankAccountDAO dao = new BankAccountDAO();
		return dao.deleteBankAccount(userId); 	
	}
	
}
