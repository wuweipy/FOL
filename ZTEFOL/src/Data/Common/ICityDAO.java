package Data.Common;

import java.util.List;

public interface ICityDAO {
	List<DCity> getCityList(int provinceId);
	
	boolean delete(int id);
	
	boolean add(DCity city);
}
