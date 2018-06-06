package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.DeptMngt;
import Common.RoleMngt;
import Common.UserMngt;
import Data.UserInfo.DUser;

public class UserMngtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	String action = req.getParameter("action");
    	if(action == null)
    	{
    		
    	}
    	else if(action.equalsIgnoreCase("delete"))
    	{
    		deleteUser(req, resp);
    	}
    	else if(action.equalsIgnoreCase("add"))
    	{
    		addUser(req, resp);
    	}
    	queryViewInitInfo(req, resp);
    	req.getRequestDispatcher("UserMngtIndex.jsp").forward(req, resp);
    }

	private void addUser(HttpServletRequest req, HttpServletResponse resp)
	{
		String workNum = req.getParameter("txtWorkNum").trim();
		String userName = ParameterUtil.getChineseString(req, "txtUserName");
		String password = req.getParameter("txtPassword").trim();
		int deptID = Integer.valueOf(req.getParameter("deptID"));
		int roleID = Integer.valueOf(req.getParameter("roleID"));
		DUser userInfo = new DUser(userName, password, workNum, deptID, roleID);
		boolean rst = UserMngt.getInstance().addUser(userInfo);
		req.setAttribute("AddisSuccess", rst);
		UserMngt.getInstance().refresh();
	}

	private void deleteUser(HttpServletRequest req, HttpServletResponse resp) 
	{
		String workNum = req.getParameter("id").trim();
		boolean rst = UserMngt.getInstance().deleteUser(workNum);
		req.setAttribute("DelisSuccess", rst);
		UserMngt.getInstance().refresh();
	}

	private void queryViewInitInfo(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		queryAllDeptInfo(req, resp);
    	queryAllRoleInfo(req, resp);
    	queryUserInfo(req, resp);
	}  

	private void queryAllDeptInfo(HttpServletRequest req,
			HttpServletResponse resp) {
		req.setAttribute("DeptInfos", DeptMngt.getInstance().getDepts());
	}

	private void queryAllRoleInfo(HttpServletRequest req,
			HttpServletResponse resp) {
		req.setAttribute("RoleInfos", RoleMngt.getInstance().getAllRoleInfo());
	}

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }

	private void queryUserInfo(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("UserInfos", UserMngt.getInstance().getAllUserInfo());
	}

}
