package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.CourseMngt;
import Common.CurrencyMngt;

/**
 * 单据类型管理
 */
public class CourseServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action == null) 
		{

		} 
		
		else if (action.equals("delete")) 
		{
			deleteCourse(req);
		} 
		
		else if (action.equals("add")) 
		{
			addCourse(req);
		}
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.getRequestDispatcher("CourseIndex.jsp").forward(req, resp);
	}

	private void addCourse(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("txtCourseID")); 
		String name = ParameterUtil.getChineseString(req, "txtCourseName");
		boolean isOk =  CourseMngt.getInstance().addCourse(id, name);
		req.setAttribute("AddisSuccess", isOk);
		CourseMngt.getInstance().refresh();
	}

	private void deleteCourse(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id")); 
		boolean isOk =  CourseMngt.getInstance().deleteCourse(id);
		req.setAttribute("DelisSuccess", isOk);
		CourseMngt.getInstance().refresh();	
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
