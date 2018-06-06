package Data.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IProjectDAO {

	ArrayList<DProject> getProjectList();

	HashMap<Integer, String> getProjectMap();

	HashMap<String, String> getSubProjectMap();

	HashMap<Integer, String> getSubProjectMap2();

	//HashMap<Integer, DProject> getSubProjectMapByOwner(String no);

	boolean addProject(DProject project);
	
	boolean addSubProject(int productId, String projectname, String subProjectName, String userId);

	boolean deleteSubProject(int subProjectId);

	boolean modifySubProject(int subProjectId, String subName);

	DProject getSubProjectById(int subProjectId);

	boolean deleteProject(int projectId);

	DProject getProjectByOwner(String no);
}
