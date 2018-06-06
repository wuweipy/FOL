package Business.Login;

import Data.UserInfo.DUser;
import Data.UserInfo.IUserDAO;
import Data.UserInfo.UserInfoDao;

public class BLogin {
   
	public BUser getUser(String username,String password)
	{
	   IUserDAO dao = new UserInfoDao();
	   DUser dUser = dao.getUser(username, password);
	   if(dUser == null)
		   return null;
	   return new BUser(dUser.getName(), dUser.getPassword(), dUser.getNo(), dUser.getRoleId());
	}
	
}
