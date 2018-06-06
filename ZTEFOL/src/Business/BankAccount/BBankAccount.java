package Business.BankAccount;

import Common.BankMngt;
import Common.CurrencyMngt;
import Common.Location.LocationMngt;
import Data.BankAccount.DBankAccount;

public class BBankAccount {
   
	private int province;
	
	private int city;
	
	private int bank;
	
	private int currency;
	
	private String accountBank;
	
	private String accountName;
	
	private String account;
	
	public BBankAccount(DBankAccount dBankAccount)
	{
		this.province = dBankAccount.getProvince();
		this.city = dBankAccount.getCity();
		this.bank = dBankAccount.getBank();
		this.currency = dBankAccount.getCurrency();
		this.accountBank = dBankAccount.getAccountBank();
		this.accountName = dBankAccount.getAccountName();
		this.account = dBankAccount.getAccount();
	}

	public BBankAccount() {
		// TODO Auto-generated constructor stub
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
	
	public String[] getStrings()
	{
		LocationMngt locationMngt = LocationMngt.getInstance();
		String[] rtnStrings = new String[7];
		rtnStrings[0] = locationMngt.getProvinceName(province);
		rtnStrings[1] = locationMngt.getCityName(city);
		rtnStrings[2] = BankMngt.getInstance().getBankName(bank);
		rtnStrings[3] = CurrencyMngt.getInstance().getCurrencyName(currency);
		rtnStrings[4] = accountBank;
		rtnStrings[5] = accountName;
		rtnStrings[6] = account;
		return rtnStrings;
	}
}
