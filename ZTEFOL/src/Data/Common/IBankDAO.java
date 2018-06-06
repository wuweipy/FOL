package Data.Common;

import java.util.List;

public interface IBankDAO {
	List<DBank> getBankList();
	
	boolean add(DBank dBank);
	boolean delete(int id);	
	
}
