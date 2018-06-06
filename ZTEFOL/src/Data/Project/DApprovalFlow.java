package Data.Project;

import java.util.HashMap;

public class DApprovalFlow {
	// id
	private int id;
	// ������id
	private int flowid;
	// ����Ŀid
	private int projectid;
	// �����˱��
	private String approvalid;
	// ����������
	private String approvalname;
	// ������ϸ����
	private String approvalmoredesc;
	// ��������
	private int approvalorder;
	// �Ƿ����Ԥ�� 0������ 1����
	private int needbudget;
	// �Ƿ����ر�����
	private int special;
	// �ر�����������
	private String specialno;
	// �����޶�
	private int limit;

	public static int APPROVALSTATUS_APPROVING = 1;
	public static int APPROVALSTATUS_SUCCESS = 2;
	public static int APPROVALSTATUS_RETURN = 3;
	public static int APPROVALSTATUS_CANCEL = 4;

	public static HashMap<Integer, String> statuses = new HashMap<Integer, String>();

	static {
		statuses.put(1, "������");
		statuses.put(2, "�������");
		statuses.put(3, "���˻�");
		statuses.put(4, "������");
	}

	public static String getStatusMoreDesc(int status) {
		switch (status) {
		case 1:
			return "������";
		case 2:
			return "�������";
		case 3:
			return "���˻�";
		case 4:
			return "������";
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
