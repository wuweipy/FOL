package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.UserInfo.DUser;
import Data.UserInfo.IUserDAO;
import Data.UserInfo.UserInfoDao;

public class ModifyPasswordServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getParameter("newPassword") != "" && req.getParameter("newPassword") != null ) {
			modifyPassword(req, resp);
		}

		req.getRequestDispatcher("ModifyUserPassword.jsp").forward(req, resp);

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private void modifyPassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");

		IUserDAO dao = new UserInfoDao();

		HttpSession session = req.getSession();
		DUser dUser = dao.getUser((String) session.getAttribute("userID"),
				oldPassword);
		if (dUser == null) {
			req.setAttribute("wrongPassword", true);
			return;
		}

		boolean saveSuccess = dao.modifyPassword(dUser, newPassword);
		req.setAttribute("success", saveSuccess);
	}

}
