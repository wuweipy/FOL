package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.DeptMngt;

public class DeptServlet extends HttpServlet 
{
	   @Override
	    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
		   String action = req.getParameter("action");
		   if(action == null)
		   {
		      
		   }
		   else if(action.equals("delete"))
		   {
			   deleteDept(req);
		   }
		   else if(action.equals("add"))
		   {
			   addDept(req);
		   }
		   req.setAttribute("detpInfo", DeptMngt.getInstance().getDepts1());
		   req.getRequestDispatcher("MyProposerIndex.jsp").forward(req, resp);
	    }
	   


		@Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
	    	 doGet(req, resp);
	    }
		
	    private void deleteDept(HttpServletRequest req) 
	    {
			int id = Integer.parseInt(req.getParameter("id")); 
			boolean isOk =  DeptMngt.getInstance().deleteDept(id);
			req.setAttribute("DelisSuccess", isOk);
			DeptMngt.getInstance().refresh();	
		}
	    
		private void addDept(HttpServletRequest req) 
		{
			int id = Integer.parseInt(req.getParameter("txtDeptID")); 
			String name = ParameterUtil.getChineseString(req, "txtDeptName");
			int instituteID = 1; 
			boolean isOk =  DeptMngt.getInstance().addDept(id, name ,instituteID);
			req.setAttribute("AddisSuccess", isOk);
			DeptMngt.getInstance().refresh();
		}
	    
	    
	
}
