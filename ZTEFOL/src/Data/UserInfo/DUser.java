package Data.UserInfo;

public class DUser 
{ 
	private String name;
	
	private String password;
	
	private String no;
	
	private int deptId;
	
	private int roleId;
	

	public DUser(String name, String password, String no, int roleId) {
		super();
		this.name = name;
		this.password = password;
		this.no = no;
		this.roleId = roleId;
	}

	public DUser(String name, String password, String no, int deptId, int roleId) {
		super();
		this.name = name;
		this.password = password;
		this.no = no;
		this.deptId = deptId;
		this.roleId = roleId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int type) {
		this.deptId = type;
	}
	
	public int getRoleId(){
		return roleId;
	}
	
	public void setRoleId(int roleId){
		this.roleId = roleId;
	}
	
}
