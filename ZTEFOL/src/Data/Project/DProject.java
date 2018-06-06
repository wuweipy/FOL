package Data.Project;

public class DProject {

	private int projectid;

	private String projectname;

	private String ownerno;
	private int flag;

	public DProject(Integer projectId, String projectName, String ownerNo) {
		this.projectid = projectId;
		this.projectname = projectName;
		this.ownerno = ownerNo;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getOwnerno() {
		return ownerno;
	}

	public void setOwnerno(String ownerno) {
		this.ownerno = ownerno;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
