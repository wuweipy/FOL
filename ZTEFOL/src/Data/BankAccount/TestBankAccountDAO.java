package Data.BankAccount;

import java.util.HashMap;

import Common.BankAccountConst;

public class TestBankAccountDAO implements IBankAccountDAO {

	private HashMap<String, DBankAccount> accountMap = new HashMap<String, DBankAccount>();
	
	public TestBankAccountDAO()
	{
	    DBankAccount dBankAccount = new DBankAccount(BankAccountConst.HUBEI,BankAccountConst.WUHAN,BankAccountConst.ZHAOHANG,
	    		1,"招商银行武汉分行东湖支行","刘闯","1111111111111111111111111");  
	    accountMap.put("10144183", dBankAccount);
	}
	
	public DBankAccount getAccount(String userID) {
		return accountMap.get(userID);
	}

	public boolean saveBankAccount(DBankAccount bankAccount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean createBankAccount(DBankAccount bankAccount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteBankAccount(String userId) {
		// TODO Auto-generated method stub
		return false;
	}
 
}
