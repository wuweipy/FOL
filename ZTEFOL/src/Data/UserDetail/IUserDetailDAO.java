package Data.UserDetail;

public interface IUserDetailDAO {

	DUserDetail getUserDetail(String no);
	
	boolean saveUserDetail(DUserDetail userDetail);
}
