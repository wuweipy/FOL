package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.Login.BLogin;
import Business.Login.BUser;

public class LogoutServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	HttpSession session = req.getSession(true);
    	session.removeAttribute("userID");
    	session.invalidate();
    	//resp.setHeader("Cache-Control","no-cache");
    	//resp.setHeader("Pragma","no-cache");
    	//resp.setDateHeader ("Expires", 0);
    	resp.sendRedirect("login.jsp");
    
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }
}
