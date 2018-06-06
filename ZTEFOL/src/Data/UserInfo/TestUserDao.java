package Data.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import Common.Employee;

public class TestUserDao implements IUserDAO
{

	private HashMap<String, DUser> userInfo = new HashMap<String,DUser>();
	
	public TestUserDao()
	{
		DUser user = new DUser("admin", "admin", "10144183", Employee.COMMON);
		userInfo.put(user.getNo(), user);
		user = new DUser("root", "root", "10144182", Employee.COMMON);
		userInfo.put(user.getNo(), user);
	}
	
	public DUser getUser(String no, String password) {
		if(userInfo.get(no) != null && userInfo.get(no).getPassword().equals(password)) 
			return userInfo.get(no);
		return null;
	}

	public boolean addUser(DUser userInfo) {
		return false;
		
	}

	public boolean deleteUser(String no) {
		return false;
	}

	public ArrayList<DUser> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean modifyPassword(DUser userInfo, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

}
