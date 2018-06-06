package View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.ProductMngt;
import Common.ProjectMngt;
import Data.Project.DProject;
import Data.Project.ProjectDAO;

public class ProductServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String userID = (String) session.getAttribute("userID");
		String action = req.getParameter("action");
		DProject project = ProjectMngt.getInstance().getProjectByOwner(userID);
		
		int flag = 0;
		if (project != null) {
			flag = 1;
		}
		req.setAttribute("flag", flag);
		if (flag == 1) {
			// String deptName = DeptMngt.getInstance().getDepts().get(deptId);
			if (action == null) {
			} else if (action.equals("add")) {
				addSubProduct(req, userID);
			} else if (action.equals("delete")) {
				deleteSubProduct(req, userID);
			} else if (action.equals("modify")) {
				modifySubProduct(req, userID);
			}
			
			HashMap<Integer, String> subProducts = ProductMngt.getSubProduct(project.getProjectid());
			req.setAttribute("projectId", project.getProjectid());
			req.setAttribute("projectName", project.getProjectname());
			req.setAttribute("subProducts", subProducts);
		}
		req.getRequestDispatcher("SubProduct.jsp").forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void addSubProduct(HttpServletRequest req, String userId) {
		int productId = Integer.parseInt(req.getParameter("deptId"));
		String subProductName = ParameterUtil.getChineseString(req, "subProductName", "utf-8");
		boolean isOk = new ProjectDAO().addSubProject(productId, ProjectMngt.getProjectName(productId), subProductName,
				userId);
		req.setAttribute("AddisSuccess", isOk);
	}

	private void deleteSubProduct(HttpServletRequest req, String userId) {
		int subProductId = Integer.valueOf(req.getParameter("subProductId"));
		if (!authCheck(subProductId, userId)) {
			req.setAttribute("noAuth", 1);
			return;
		}
		boolean isOk = new ProjectDAO().deleteSubProject(subProductId);
		req.setAttribute("DelisSuccess", isOk);
	}

	private void modifySubProduct(HttpServletRequest req, String userId) {
		// TODO Auto-generated method stub
		int subProductId = Integer.valueOf(req.getParameter("subProductId"));
		if (!authCheck(subProductId, userId)) {
			req.setAttribute("noAuth", 1);
			return;
		}
		String subName = ParameterUtil.getChineseString(req, "subName" + subProductId, "utf-8");
		boolean isOk = new ProjectDAO().modifySubProject(subProductId, subName);
		req.setAttribute("ModifySuccess", isOk);
	}

	private boolean authCheck(int subProjectId, String userId) {
		DProject project = new ProjectDAO().getSubProjectById(subProjectId);
		if (project == null || project.getOwnerno() == null) {
			return true;
		}
		return userId.equals(project.getOwnerno());
	}
}
