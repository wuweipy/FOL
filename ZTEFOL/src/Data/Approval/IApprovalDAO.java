package Data.Approval;

import java.util.ArrayList;

import Data.Claims.DClaim;

public interface IApprovalDAO {

	public ArrayList<DClaim> getClaimDetail(int status, String approvalId, String invoiceNo, int invoiceType,
			int deptId, String employeeName, int productId);

	public boolean approval(String invoiceNo, int flag, int status, int nextStatus, String approvalId,
			String approvalName, String appDate, String nextApprovalId, float totalFee, String budgetId,
			int approvalFlowId, float appAmount,String comment);

}
