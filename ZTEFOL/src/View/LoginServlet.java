package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.Login.BLogin;
import Business.Login.BUser;
import Common.DeptMngt;
import Common.UserMngt;

public class LoginServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	BLogin login = new BLogin();
    	HttpSession session = req.getSession(true);
    	String username = req.getParameter("username");
    	String password = req.getParameter("password");
    	BUser bUser = login.getUser(username, password);
    	if(bUser == null)
    	{
    		req.setAttribute("error", "error");
    		session.invalidate();
    		req.getRequestDispatcher("login.jsp").forward(req, resp);
    	}
    	else
    	{
			int deptId = UserMngt.getInstance().getUserInfoMap().get(username).getDeptId();
			int institutexx = DeptMngt.getInstance().getDepts1().get(deptId).getInstitute();
    		req.setAttribute("user", bUser.getName());
    		session.setAttribute("userID", username);
    		session.setAttribute("userName", bUser.getName());
    		session.setAttribute("ruleID", bUser.getRoleId());
			session.setAttribute("institutexx", institutexx);
    		
    		if(bUser.getRoleId() == 0)
    		{
    			resp.sendRedirect("pages/adminIndex.jsp");
    		}
    		else
    		{
    			resp.sendRedirect("pages/index.jsp");
    		}
    		
    	}	
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }
}
