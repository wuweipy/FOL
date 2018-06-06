package Data.Project;

import java.util.HashMap;

public interface IApprovalFlowDAO {
	public HashMap<Integer, DApprovalFlow> getAllApprovalFlows();

	public HashMap<Integer, String> getStatuses();

	public DApprovalFlow getBySubprojectid(int subprojectid);

	public DApprovalFlow getBySpecialno(String specialno);

	public DApprovalFlow getNextApprovalFlow(int approvalflowid);
}
