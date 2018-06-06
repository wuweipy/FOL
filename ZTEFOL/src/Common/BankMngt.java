package Common;

import java.util.HashMap;
import java.util.Iterator;

import Data.Common.DBank;
import Data.Common.DProvince;
import Data.Common.BankDAO;

public class BankMngt 
{
    private static BankMngt bankMngt = new BankMngt();
	
	private HashMap<Integer, String> bankMap = new HashMap<Integer, String>();
	
	private BankMngt()
	{
		init();
	}

	private void init() 
	{
        BankDAO bankDAO = new BankDAO();
        Iterator<DBank> iterator = bankDAO.getBankList().iterator();
        while(iterator.hasNext())
        {
        	DBank bank = iterator.next();
        	bankMap.put(bank.getId(), bank.getName());
        }
	}

	public static BankMngt getInstance() 
	{
		return bankMngt;
	}

	public String getBankName(int bankID) 
	{
		return bankMap.get(bankID);
	}
	
	public HashMap<Integer, String> getBankMap()
	{
		return bankMap;
	}
	
	public boolean addBank(int id, String name)
	{
		DBank bank = new DBank();
		bank.setId(id);
		bank.setName(name);
		
		return new BankDAO().add(bank);		
	}
	
	public boolean deleteBank(int id)
	{
		BankDAO bankDAO = new BankDAO();
		return bankDAO.delete(id);
	}
	
	public void refresh()
	{
		bankMap.clear();
		init();
	}
	
	public static void main(String[] args) {
		DeptMngt.getInstance();
	}

}
