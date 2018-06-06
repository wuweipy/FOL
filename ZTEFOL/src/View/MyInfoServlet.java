package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.BusinessFactory;
import Business.UserDetail.BUserDetail;
import Business.UserDetail.BUserDetailHandler;
import Common.DeptMngt;
import Common.ProductMngt;
import Common.UserMngt;
import Data.UserInfo.UserInfoDao;

public class MyInfoServlet extends HttpServlet 
{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		BUserDetailHandler hander = BusinessFactory.getFactory().getBUserDetailHanlder();
		
		if(req.getParameter("btnSave") != null)
		{
			saveInfo(req, resp, hander);
		}		 
        getInfo(req, resp, hander);
	
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private void getInfo(HttpServletRequest req, HttpServletResponse resp,BUserDetailHandler handler) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
    	BUserDetail userDetail = handler.getUserDetail((String)session.getAttribute("userID"),
    			(String)session.getAttribute("userName"));
    	req.setAttribute("userDetail", userDetail);
    	req.setAttribute("detpInfo", DeptMngt.getInstance().getDepts());
    	req.setAttribute("prodInfo", ProductMngt.getInstance().getProducts());
    	session.setAttribute("userInfo", userDetail);
		req.getRequestDispatcher("MyInfoIndex.jsp").forward(req, resp);
	}
	
	private void saveInfo(HttpServletRequest req, HttpServletResponse resp,BUserDetailHandler handler) throws ServletException, IOException 
	{
		BUserDetail userDetail = new BUserDetail();
		userDetail.setEmail(req.getParameter("email"));
		userDetail.setTel(req.getParameter("tel"));
		String userID = (String) req.getSession().getAttribute("userID");
		boolean saveSuccess = handler.saveUserDetail(userID, userDetail);
		UserMngt.getInstance().getuserDetailMap().get(userID).setEmail(req.getParameter("email"));
		req.setAttribute("success", saveSuccess);	
	}

}
