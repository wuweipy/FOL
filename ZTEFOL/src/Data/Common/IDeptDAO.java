package Data.Common;

import java.util.List;



public interface IDeptDAO {

	List<DDept> getDepts();

	boolean delete(int id);
	
	boolean add(DDept dept);
	
}
