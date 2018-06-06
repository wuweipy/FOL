package Common;

import java.util.HashMap;

import Business.Claims.BClaim;
import Data.Project.ApprovalFlowDAO;
import Data.Project.DApprovalFlow;
import Data.Project.IApprovalFlowDAO;

public class ApprovalFlowMngt {
	public static HashMap<Integer, DApprovalFlow> approvalFlowMap = new HashMap<Integer, DApprovalFlow>();

	static {
		IApprovalFlowDAO approvalFlowDAO = new ApprovalFlowDAO();
		approvalFlowMap = approvalFlowDAO.getAllApprovalFlows();
	}

	public static DApprovalFlow getApprovalFlow(int subproductId) {
		DApprovalFlow flow = new ApprovalFlowDAO().getBySubprojectid(subproductId);
		return flow;
	}

	public static HashMap<Integer, DApprovalFlow> getApprovalFlowMap() {
		return new ApprovalFlowDAO().getAllApprovalFlows();
	}

	public static DApprovalFlow getCurrApprovalFlow(int approvalFlowId) {
		DApprovalFlow flow = approvalFlowMap.get(approvalFlowId);
		return flow;
	}

	public static DApprovalFlow getNextApprovalFlow(int approvalFlowId) {
		DApprovalFlow flow = new ApprovalFlowDAO().getNextApprovalFlow(approvalFlowId);
		return flow;
	}

	public static DApprovalFlow getActApprovalFlow(BClaim claim) {
		int productId = claim.getProductId();
		String no = claim.getNo();
		// 走特殊审批通道
		DApprovalFlow flow = new ApprovalFlowDAO().getBySpecialno(no);
		if (flow == null) {
			// 获取普通职员审批流
			flow = ApprovalFlowMngt.getApprovalFlow(productId);
			// 申请人为审批人时由上级领导审批
			flow = getActFLow(claim, flow);
		}
		return flow;
	}

	private static DApprovalFlow getActFLow(BClaim claim, DApprovalFlow flow) {
		if (flow.getApprovalid().equals(claim.getNo())) {
			// 一级审批人又上级领导审批
			flow = ApprovalFlowMngt.getNextApprovalFlow(flow.getId());
			getActFLow(claim, flow);
		}
		return flow;
	}

}
