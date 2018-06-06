package Common;

import java.util.HashMap;
import java.util.Iterator;
import Data.Common.DDept;
import Data.Common.DeptDAO;
import Data.Common.IDeptDAO;

public class DeptMngt 
{
	private static DeptMngt deptMngt = new DeptMngt();
	
	public static final int WuHan = 1;
	
	public static final int ShangHai = 2;
	
	private HashMap<Integer, DDept> depts1 = new HashMap<Integer, DDept>();
	
	private HashMap<Integer, String> depts = new HashMap<Integer, String>();
	
	private DeptMngt()
	{
		init();
	}

	private void init() 
	{
        IDeptDAO deptDAO = new DeptDAO();
        Iterator<DDept> iterator = deptDAO.getDepts().iterator();
        while(iterator.hasNext())
        {
        	DDept dept = iterator.next();
        	depts1.put(dept.getId(), dept);
           	depts.put(dept.getId(), dept.getName());
        }
	}

	public static DeptMngt getInstance() 
	{
		return deptMngt;
	}

	public String getDeptName(int deptID) 
	{
		return depts.get(deptID);
	}
	
	public HashMap<Integer, DDept> getDepts1()
	{
		return depts1;
	}
	
	public HashMap<Integer, String> getDepts()
	{
		return depts;
	}
	
	public static void main(String[] args) {
		DeptMngt.getInstance();
	}

	public boolean deleteDept(int id) {
		IDeptDAO deptDAO = new DeptDAO();
		return deptDAO.delete(id);
	}
	
	public boolean addDept(int id,String name , int instituteID)
	{
		DDept dept = new DDept();
		dept.setId(id);
		dept.setName(name);
		dept.setInstitute(instituteID);
		return new DeptDAO().add(dept);
	}
	
	public void refresh()
	{
		depts1.clear();
		depts.clear();
		init();
	}

}
