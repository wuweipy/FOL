package Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Data.Project.DProject;
import Data.Project.IProjectDAO;
import Data.Project.ProjectDAO;
import Data.UserInfo.DUser;

public class ProjectMngt {

	private static ProjectMngt projectInfoMngt = new ProjectMngt();
	
	public static String getProjectName(int projectNo) {
		return new ProjectDAO().getProjectMap().get(projectNo);
	}

	public static HashMap<Integer, String> getProjectMap() {
		return new ProjectDAO().getProjectMap();
	}

	public static HashMap<String, String> getSubProjectMap() {
		IProjectDAO projectDAO = new ProjectDAO();
		return projectDAO.getSubProjectMap();
	}

	public static HashMap<Integer, String> getSubProjectMap2() {
		IProjectDAO projectDAO = new ProjectDAO();
		return projectDAO.getSubProjectMap2();
	}

//	public static HashMap<Integer, DProject> getSubProjectMapByOwner(String no) {
//		IProjectDAO projectDAO = new ProjectDAO();
//		return projectDAO.getSubProjectMapByOwner(no);
//	}

	public static DProject getProjectByOwner(String no) {
		IProjectDAO projectDAO = new ProjectDAO();
		return projectDAO.getProjectByOwner(no);
	}

	public static ProjectMngt getInstance()
	{
		return projectInfoMngt;
	}
	
	public boolean deleteProjectById(Integer pid)
	{
		IProjectDAO projectDAO = new ProjectDAO();
		return projectDAO.deleteProject(pid);	
	}

	public boolean addProject(DProject project)
	{
		IProjectDAO projectDAO = new ProjectDAO();
		return projectDAO.addProject(project);	
	}
	
	public ArrayList<DProject> getAllProjectInfo()
	{
		IProjectDAO projectDAO = new ProjectDAO();
		return projectDAO.getProjectList();
	}
}
