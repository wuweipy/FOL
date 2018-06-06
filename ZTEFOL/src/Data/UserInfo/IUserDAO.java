package Data.UserInfo;

import java.util.ArrayList;


public interface IUserDAO 
{
    public DUser getUser(String no,String password);
    
    public ArrayList<DUser> getAllUsers();
    
    public boolean addUser(DUser userInfo);
    
    public boolean deleteUser(String no);
    
    public boolean modifyPassword(DUser userInfo, String newPassword) ;
}
