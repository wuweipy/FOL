package Common;

import java.util.HashMap;
import java.util.Iterator;

import Data.Common.CurrencyDAO;
import Data.Common.DCurrency;
import Data.Common.ICurrencyDAO;

public class CurrencyMngt 
{
    private static CurrencyMngt currencyMngt = new CurrencyMngt();
	
	private HashMap<Integer, String> currencyMap = new HashMap<Integer, String>();
	
	private CurrencyMngt()
	{
		init();
	}

	private void init() 
	{
        CurrencyDAO currencyDAO = new CurrencyDAO();
        Iterator<DCurrency> iterator = currencyDAO.getCurrencyList().iterator();
        while(iterator.hasNext())
        {
        	DCurrency currency = iterator.next();
        	currencyMap.put(currency.getId(), currency.getName());
        }
	}

	public static CurrencyMngt getInstance() 
	{
		return currencyMngt;
	}

	public String getCurrencyName(int currencyID) 
	{
		return currencyMap.get(currencyID);
	}
	
	public HashMap<Integer, String> getCurrencyMap()
	{
		return currencyMap;
	}
	
	public static void main(String[] args) {
		CurrencyMngt.getInstance();
	}
	
	public boolean deleteCurrency(int id) {
		ICurrencyDAO currencyDAO = new CurrencyDAO();
		return currencyDAO.delete(id);
	}
	
	public boolean addCurrency(int id,String name)
	{
		DCurrency currency = new DCurrency();
		currency.setId(id);
		currency.setName(name);
		return new CurrencyDAO().add(currency);
	}
	
	public void refresh()
	{
		currencyMap.clear();
		init();
	}

}
