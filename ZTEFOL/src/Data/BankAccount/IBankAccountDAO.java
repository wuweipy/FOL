package Data.BankAccount;

public interface IBankAccountDAO {

	public DBankAccount getAccount(String userID);

	public boolean saveBankAccount(DBankAccount bankAccount);

	public boolean createBankAccount(DBankAccount bankAccount);

	public boolean deleteBankAccount(String userId);
	
}
