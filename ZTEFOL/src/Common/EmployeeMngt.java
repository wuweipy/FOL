package Common;

import java.util.HashMap;
import java.util.Iterator;
import Data.Common.DEmployeeType;
import Data.Common.EmployeeTypeDAO;

public class EmployeeMngt {

	private static EmployeeMngt employeeMngt = new EmployeeMngt();
	private HashMap<Integer, String> employees = new HashMap<Integer, String>();
	
	private EmployeeMngt()
	{
		init();
	}

	private void init() 
	{
        EmployeeTypeDAO employeeTypeDAO = new EmployeeTypeDAO();
        Iterator<DEmployeeType> iterator = employeeTypeDAO.getEmployeeList().iterator();
        while(iterator.hasNext())
        {
        	DEmployeeType employeeType = iterator.next();
        	employees.put(employeeType.getId(), employeeType.getName());
        }
	}
	
	public static EmployeeMngt getInstance() {
		return employeeMngt;
	}

	public String getEmployeeType(int empolyTypeId) {
		return employees.get(empolyTypeId);
	}
	
	public HashMap<Integer, String> getEmployeTypes()
	{
		return employees;
	}
}
