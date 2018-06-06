package View;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConvertServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
         int lastSlash = req.getRequestURI().lastIndexOf("/");
         int lastPoint = req.getRequestURI().lastIndexOf(".");
         String path = req.getRequestURI().substring(lastSlash,lastPoint);
         String uri = "WEB-INF/pages/convert" + path + ".jsp";
         if(!isLogin(req))
         {
        	 resp.sendRedirect("login.jsp");
         }
         else 
         {
        	 req.getRequestDispatcher(uri).forward(req, resp);
		 }  
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }
    
    private boolean isLogin(HttpServletRequest request)
    {
    	HttpSession session = request.getSession();
    	if(session.getAttribute("userID") != null)
    	{
    		request.setAttribute("user",session.getAttribute("userName"));
	    	return true;
    	} 
    	return false;

    }
}
