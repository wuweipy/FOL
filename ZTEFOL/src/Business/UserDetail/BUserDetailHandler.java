package Business.UserDetail;

import org.apache.log4j.Logger;

import Business.BankAccount.BBankAccountHandler;
import Common.FOLLogger;
import Data.UserDetail.DUserDetail;
import Data.UserDetail.IUserDetailDAO;
import Data.UserDetail.UserDetailDao;

public class BUserDetailHandler {
	
	private static Logger logger = FOLLogger.getLogger(BBankAccountHandler.class);

	public BUserDetail getUserDetail(String userID, String userName)
	{
		logger.info("user : " + userID + " get account");
		IUserDetailDAO dao = new UserDetailDao();
		DUserDetail userDetail= dao.getUserDetail(userID);
		if(userDetail == null) 
			return null;
		return new BUserDetail(userDetail, userName);
	}
	
	public String[] getUserDetailStrings(String userID, String userName)
	{
		BUserDetail bUserDetail = getUserDetail(userID, userName);
		if(bUserDetail == null)
		{
			return null;
		}
		return bUserDetail.getStrings();
	}
	
	public boolean saveUserDetail(String userID,BUserDetail userDetail)
	{
		DUserDetail detail = new DUserDetail();
		detail.setEmail(userDetail.getEmail());
		detail.setTel(userDetail.getTel());
		detail.setNo(userID);
		return new UserDetailDao().saveUserDetail(detail);
	}
	

}
