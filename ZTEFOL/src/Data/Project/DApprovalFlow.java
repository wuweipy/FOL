package Data.Project;

import java.util.HashMap;

public class DApprovalFlow {
	// id
	private int id;
	// 审批流id
	private int flowid;
	// 子项目id
	private int projectid;
	// 审批人编号
	private String approvalid;
	// 审批人姓名
	private String approvalname;
	// 审批详细描述
	private String approvalmoredesc;
	// 审批次序
	private int approvalorder;
	// 是否计入预算 0不计入 1计入
	private int needbudget;
	// 是否走特别审批
	private int special;
	// 特别审批申请人
	private String specialno;
	// 审批限额
	private int limit;

	public static int APPROVALSTATUS_APPROVING = 1;
	public static int APPROVALSTATUS_SUCCESS = 2;
	public static int APPROVALSTATUS_RETURN = 3;
	public static int APPROVALSTATUS_CANCEL = 4;

	public static HashMap<Integer, String> statuses = new HashMap<Integer, String>();

	static {
		statuses.put(1, "待审批");
		statuses.put(2, "审批完成");
		statuses.put(3, "被退回");
		statuses.put(4, "被撤销");
	}

	public static String getStatusMoreDesc(int status) {
		switch (status) {
		case 1:
			return "待审批";
		case 2:
			return "审批完成";
		case 3:
			return "被退回";
		case 4:
			return "被撤销";
		}
		return "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlowid() {
		return flowid;
	}

	public void setFlowid(int flowid) {
		this.flowid = flowid;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int tProjectid) {
		this.projectid = tProjectid;
	}

	public String getApprovalid() {
		return approvalid;
	}

	public void setApprovalid(String approvalid) {
		this.approvalid = approvalid;
	}

	public String getApprovalmoredesc() {
		return approvalmoredesc;
	}

	public void setApprovalmoredesc(String approvalmoredesc) {
		this.approvalmoredesc = approvalmoredesc;
	}

	public int getApprovalorder() {
		return approvalorder;
	}

	public void setApprovalorder(int approvalorder) {
		this.approvalorder = approvalorder;
	}

	public String getApprovalname() {
		return approvalname;
	}

	public void setApprovalname(String approvalname) {
		this.approvalname = approvalname;
	}

	public int getNeedbudget() {
		return needbudget;
	}

	public void setNeedbudget(int needbudget) {
		this.needbudget = needbudget;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getSpecialno() {
		return specialno;
	}

	public void setSpecialno(String specialno) {
		this.specialno = specialno;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
