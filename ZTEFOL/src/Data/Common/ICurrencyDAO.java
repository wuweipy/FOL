package Data.Common;

import java.util.List;

public interface ICurrencyDAO 
{
	List<DCurrency> getCurrencyList();
	
	boolean delete(int id);
	
	boolean add(DCurrency currency);
}
