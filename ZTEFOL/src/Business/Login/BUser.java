package Business.Login;

public class BUser {
	
	private String name;
	
	private String password;
	
	private String no;
	
	private int roleId;

	public BUser(String name, String password, String no, int roleId) {
		super();
		this.name = name;
		this.password = password;
		this.no = no;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
