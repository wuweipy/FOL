package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.UserMngt;
import Common.DelegateMngt;

public class DelegateServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    HttpSession session = req.getSession();
		String userID = (String)session.getAttribute("userID");
		String action = req.getParameter("action");
		int roleId = UserMngt.getInstance().getUserInfoMap().get(userID).getRoleId();
		if(roleId==3 || roleId==5){
			if (action == null) 
			{
	
			} 		
			else if (action.equals("delete")) 
			{
				deleteCourse(req,userID);
			} 			
			else if (action.equals("add")) 
			{
				addDelegate(req,userID);
			}
			req.setAttribute("userInfoMap", UserMngt.getInstance().getUserInfoMap());	
			DelegateMngt.getInstance().refresh();
			req.setAttribute("delegateMap", DelegateMngt.getInstance().getDelegateMap());
			req.setAttribute("delegateInfo", DelegateMngt.getDelegateInfo(userID));
		}
		req.setAttribute("roleId", roleId);
		req.getRequestDispatcher("DelegateServlet.jsp").forward(req, resp);
	}

	private void addDelegate(HttpServletRequest req , String userID) {
		int actionId = Integer.parseInt(req.getParameter("delegate")); 
		String agencyNo = req.getParameter("userNo");
		boolean isOk =  DelegateMngt.addDelegate(actionId, agencyNo,userID);
		req.setAttribute("AddisSuccess", isOk);
	}

	private void deleteCourse(HttpServletRequest req , String userID ) {
		String agencyNo = req.getParameter("agencyNo"); 
		int actionId = Integer.valueOf(req.getParameter("actionId")); 				
		boolean isOk =  DelegateMngt.deleteDelegate(userID,agencyNo,actionId);
		req.setAttribute("DelisSuccess", isOk);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
