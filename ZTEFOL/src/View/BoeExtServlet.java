package View;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import View.BoeServletHelper.Page;
import View.BoeServletHelper.PageState;

public class BoeExtServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	HttpSession session = req.getSession();
		String action = req.getParameter("save");
		String first = req.getParameter("isFirst");
		Page page = (Page)session.getAttribute("page");
		if(first != null && first.equals("true"))
		{
			page = new Page(req,resp);
			page.setPageState(PageState.First);
			session.setAttribute("page", page);
		}
        if(action != null)
        {
    	    page.setReqResp(req, resp);
    		page.setPageAction(action);   	
        }
    }
    
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }
 
}