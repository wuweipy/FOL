package View;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.DeptMngt;
import Common.ProductMngt;
import Common.ProjectMngt;
import Common.RoleMngt;
import Common.UserMngt;
import Data.Project.DProject;
import Data.UserInfo.DUser;

public class ProjectMngtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean DProject = false;
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	String action = req.getParameter("action");
    	if(action == null)
    	{
    		
    	}
    	else if(action.equalsIgnoreCase("delete"))
    	{
    		deleteProject(req, resp);
    	}
    	else if(action.equalsIgnoreCase("add"))
    	{
    		addProject(req, resp);
    	}
    	queryViewInitInfo(req, resp);
    	req.getRequestDispatcher("ProjectMngtIndex.jsp").forward(req, resp);
    }

    private boolean checkProject(Integer projectId ,String projectName)
    {
    	ArrayList<DProject> projectList = ProjectMngt.getInstance().getAllProjectInfo();
    	for(DProject project:projectList)
    	{
    		if((project.getProjectid() == projectId)||(project.getProjectname().equals(projectName) ))
    			return false;
    	}
    	return true;
    }
    
    private boolean checkUser(String workNo)
    {
    	ArrayList<DUser> userList = UserMngt.getInstance().getAllUserInfo();
    	for(DUser user:userList)
    	{
    		if(user.getNo().equals(workNo))
    			return true;
    	}
    	return false;
    }
    
	private void addProject(HttpServletRequest req, HttpServletResponse resp)
	{
		Integer projectId = Integer.valueOf(req.getParameter("txtProjectId"));
		String projectName = ParameterUtil.getChineseString(req, "txtProjectName");
		String ownerNo =  ParameterUtil.getChineseString(req, "txtWorkNo");
		if((checkProject(projectId,projectName) == false)||(checkUser(ownerNo) == false))
		{
			req.setAttribute("AddisSuccess", false);
			return;
		}
		DProject projectInfo = new DProject(projectId, projectName, ownerNo);
		boolean rst = ProjectMngt.getInstance().addProject(projectInfo);
		if(rst == true)
			rst = ProductMngt.getInstance().addSubProduct(projectId, "公用项目", null);
		req.setAttribute("AddisSuccess", rst);
		UserMngt.getInstance().refresh();
	}

	private void deleteProject(HttpServletRequest req, HttpServletResponse resp) 
	{
		Integer projectId = Integer.valueOf(req.getParameter("projectId"));
		boolean rst = ProjectMngt.getInstance().deleteProjectById(projectId);
		req.setAttribute("DelisSuccess", rst);
		UserMngt.getInstance().refresh();
	}

	private void queryViewInitInfo(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		queryProjectsInfo(req, resp);
	}  

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }

	private void queryProjectsInfo(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("UserInfoMap", UserMngt.getInstance().getUserInfoMap());
		req.setAttribute("ProjectsInfo", ProjectMngt.getInstance().getAllProjectInfo());
	}

}
