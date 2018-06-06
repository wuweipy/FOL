package Data.BankAccount;

public class DBankAccount {
  
	private int province;
	
	private int city;
	
	private int bank;
	
	private int currency;
	
	private String accountBank;
	
	private String accountName;
	
	private String account;
	
	private String no;
	
	public DBankAccount(int province,int city,int bank,int currency,String accountBank,String accountName,String account)
	{
		this.province = province;
		this.city = city;
		this.bank = bank;
		this.currency = currency;
		this.accountBank = accountBank;
		this.accountName = accountName;
		this.account = account;
	}
	
	public DBankAccount()
	{
		
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
}
