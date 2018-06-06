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
		// ����������ͨ��
		DApprovalFlow flow = new ApprovalFlowDAO().getBySpecialno(no);
		if (flow == null) {
			// ��ȡ��ְͨԱ������
			flow = ApprovalFlowMngt.getApprovalFlow(productId);
			// ������Ϊ������ʱ���ϼ��쵼����
			flow = getActFLow(claim, flow);
		}
		return flow;
	}

	private static DApprovalFlow getActFLow(BClaim claim, DApprovalFlow flow) {
		if (flow.getApprovalid().equals(claim.getNo())) {
			// һ�����������ϼ��쵼����
			flow = ApprovalFlowMngt.getNextApprovalFlow(flow.getId());
			getActFLow(claim, flow);
		}
		return flow;
	}

}
