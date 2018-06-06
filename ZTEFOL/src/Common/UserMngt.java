package Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Business.UserDetail.BUserDetail;
import Data.UserDetail.DUserDetail;
import Data.UserInfo.DUser;
import Data.UserInfo.UserInfoDao;

public class UserMngt {

	private static UserMngt userInfoMngt = new UserMngt();
	
	private ArrayList<DUser> userInfos = new ArrayList<DUser>();
	
	private ArrayList<DUserDetail> userDetails = new ArrayList<DUserDetail>();
	
	private HashMap<String, DUser> userInfoMap = new HashMap<String, DUser>();

	private HashMap<String, DUser> userInfoMapName = new HashMap<String, DUser>();
	
	private HashMap<String, DUserDetail> userDetailMap = new HashMap<String, DUserDetail>();
	
	private boolean isDirty = false;
	
	public UserMngt()
	{
		init();
	}
	
	private void init()
	{
		UserInfoDao userInfoDao = new UserInfoDao();
		userInfos = userInfoDao.getAllUsers();
        Iterator<DUser> iterator = userInfos.iterator();
        while(iterator.hasNext())
        {
        	DUser dUser = iterator.next();
        	userInfoMap.put(dUser.getNo(), dUser);
        	userInfoMapName.put(dUser.getName(), dUser);        	
        }
        
        userDetails = userInfoDao.getAllUserDetails();
        Iterator<DUserDetail> iterator1 = userDetails.iterator();
        while(iterator1.hasNext())
        {
        	DUserDetail dUser = iterator1.next();
        	userDetailMap.put(dUser.getNo(), dUser);      	
        }
	}

	public static UserMngt getInstance()
	{
		return userInfoMngt;
	}
	
	public ArrayList<DUser> getAllUserInfo()
	{
		if(isDirty)
		{
			refresh();
		}
		return userInfos;
	}
	
	public HashMap<String, DUser> getUserInfoMap()
	{
		if(isDirty)
		{
			refresh();
		}
		return userInfoMap;
	}
	
	public HashMap<String, DUserDetail> getuserDetailMap()
	{
		if(isDirty)
		{
			refresh();
		}
		return userDetailMap;
	}
	
	public HashMap<String, DUser> getUserInfoMapName()
	{
		if(isDirty)
		{
			refresh();
		}
		return userInfoMapName;
	}

	public boolean deleteUser(String workNum) 
	{
		UserInfoDao userInfoDao = new UserInfoDao();
		boolean rtn = userInfoDao.deleteUser(workNum);
		if(rtn) {
			setDirty(true);
		}
		return rtn;
	}
	
	public boolean addUser(DUser userInfo) 
	{
		UserInfoDao userInfoDao = new UserInfoDao();
		boolean rtn = userInfoDao.addUser(userInfo);
		if(rtn) {
			setDirty(true);
		}
		return rtn;
	}

	public void refresh() 
	{
		userInfos.clear();
		init();
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	
	public boolean isSales(String userNo)
	{
		return userInfoMap.get(userNo).getDeptId() == 6 || userInfoMap.get(userNo).getDeptId() == 22;
	}
	
}
