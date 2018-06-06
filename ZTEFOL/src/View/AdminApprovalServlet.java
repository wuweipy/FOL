package View;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.ApprovalFlowMngt;
import Common.CountersignMngt;
import Common.CourseMngt;
import Common.DeptMngt;
import Common.ProjectMngt;
import Common.UseMoneyMngt;
import Common.UserMngt;
import Data.Approval.ApprovalDAO;
import Data.Claims.DClaim;
import Data.Common.DDept;
import Data.Common.DeptDAO;
import Data.Common.Countersign.DCountersign;
import Data.Project.ApprovalFlowDAO;
import Data.Project.DApprovalFlow;
import Jmail.EmailHander;

/**
 * 
 */
public class AdminApprovalServlet extends HttpServlet {

	private static int KeyMoney = 1000;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int chao = -1;
		String action = req.getParameter("action");
		List<String> notPermit = new ArrayList<String>();
		if (action == null || action.equals("")) {
			init(req);
		} else if (action.equals("query")) {
			queryClaims(req);
		} else if (action.equals("approval")) {
			chao = approval(req, notPermit);
		} else if (action.equals("approvalone")) {
			chao = approvalone(req, notPermit);
		}
		req.setAttribute("chao", chao);
		req.setAttribute("notPermit", notPermit);
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());

		HashMap<Integer, DCountersign> map = CountersignMngt.getInstance().getCountersignMap();
		req.setAttribute("deptInfo", DeptMngt.getInstance().getDepts());
		req.setAttribute("userInfo", UserMngt.getInstance().getUserInfoMap());
		req.getSession().setAttribute("count", map.size());
		req.setAttribute("countersignInfo", map);
		req.setAttribute("chao", chao);
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.setAttribute("notPermit", notPermit);
		req.setAttribute("doFlag", false);
		HttpSession session = req.getSession();
		String approvalId = (String) session.getAttribute("userID");
		if(UserMngt.getInstance().getUserInfoMap().get(approvalId).getRoleId()==4){
			req.setAttribute("displayFlag", 1);
		}else{
			req.setAttribute("displayFlag", 0);
		}
		HashMap<Integer, String> projectmap = ProjectMngt.getProjectMap();
		req.setAttribute("projects", projectmap);
		HashMap<Integer, String> statusmap = DApprovalFlow.statuses;
		req.setAttribute("statuses", statusmap);

		req.getRequestDispatcher("AdminApprovalIndex.jsp").forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void queryClaims(HttpServletRequest req) {
		int status = 1;
		HttpSession session = req.getSession();
		String approvalId = (String) session.getAttribute("userID");
		String invoiceNo = req.getParameter("invoiceNo");
		int invoiceType = Integer.parseInt(req.getParameter("invoiceType"));
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		int productId = Integer.parseInt(req.getParameter("productId"));
		String employeeName = ParameterUtil.getChineseString(req, "employeeName", "utf-8");

		ApprovalDAO approvalDAO = new ApprovalDAO();
		ArrayList<DClaim> claimDetail = approvalDAO.getClaimDetail(status, approvalId, invoiceNo, invoiceType, deptId,
				employeeName, productId);
		req.setAttribute("claimDetail", claimDetail);

		AllDataInfoInit(req);

	}

	private int approval(HttpServletRequest req, List<String> notPermit) {
		HttpSession session = req.getSession();
		String approvalId = (String) session.getAttribute("userID");
		String approvalName = (String) session.getAttribute("userName");
		int flag = Integer.parseInt(req.getParameter("flag"));
		String comment="";
		if(flag==1){
			comment="同意";
		}else if(flag==0){
			comment="退回";
		}
		int selectedCount = Integer.parseInt(req.getParameter("selectedCount"));

		String timeID = getTimeID();
		Date appDate = new Date(System.currentTimeMillis());
		String appDateStr = appDate.toLocaleString();
		ApprovalDAO approvalDAO = new ApprovalDAO();

		for (int i = 0; i < selectedCount; i++) {
			String invoiceNo = req.getParameter("InvoiceNo" + i);
			int curflowid = Integer.parseInt(req.getParameter("ApprovalFlowId" + i));
			DApprovalFlow curflow = ApprovalFlowMngt.getCurrApprovalFlow(curflowid);
			DApprovalFlow nextflow = ApprovalFlowMngt.getNextApprovalFlow(curflowid);
			int invoiceType = Integer.valueOf(req.getParameter("invoiceType" + i));
			float totalFee = Float.parseFloat(req.getParameter("TotalFee" + i));
			float appAmount = Float.parseFloat(req.getParameter("ApprovalAmount"+i));

			approvalCommon(req, notPermit, approvalId, approvalName, flag, timeID, appDateStr, approvalDAO, invoiceType,
					totalFee, invoiceNo, curflow, nextflow,appAmount,comment);
		}

		init(req);
		if (notPermit.size() != 0) {
			return 1;
		}
		return 2;
	}

	private String approvalCommon(HttpServletRequest req, List<String> notPermit, String approvalId, String approvalName,
			int flag, String timeID, String appDateStr, ApprovalDAO approvalDAO, int invoiceType, float totalFee,
			String invoiceNo, DApprovalFlow curflow, DApprovalFlow nextflow,float appAmount,String comment) {
		String budgetId = "-1";
		String nextApprovalId = "-1";
		int curStatus = -1;
		int nextStatus = -1;
		int nextfid = -1;

		if (flag == 0) {
			// 审批退回
			nextApprovalId = "-1";
			nextStatus = DApprovalFlow.APPROVALSTATUS_CANCEL;
			curStatus = DApprovalFlow.APPROVALSTATUS_CANCEL;
			nextfid = -1;

			boolean ss = approvalDAO.approval(invoiceNo, flag, curStatus, nextStatus, approvalId, approvalName,
					appDateStr, approvalId, 0, budgetId, nextfid,appAmount,comment);
			if (ss) {
				// 提醒被退回
				EmailHander.emailHander(approvalId, invoiceNo, 2);
			}
		} else if (flag == 1) {
			// 审批同意
			if (curflow.getLimit() != -1 && curflow.getLimit() < totalFee) {
				// 如果额度超限则走特殊审批
				nextflow = new ApprovalFlowDAO().getBySpecialno(curflow.getApprovalid());
			}

			if (nextflow != null) {
				nextApprovalId = nextflow.getApprovalid();
				nextStatus = DApprovalFlow.APPROVALSTATUS_APPROVING;
				curStatus = DApprovalFlow.APPROVALSTATUS_SUCCESS;
				nextfid = nextflow.getId();
			} else {
				nextApprovalId = "-1";
				nextStatus = DApprovalFlow.APPROVALSTATUS_SUCCESS;
				curStatus = DApprovalFlow.APPROVALSTATUS_SUCCESS;
				nextfid = -1;
			}

			String invoiceTypeName = CourseMngt.getInstance().getCourseMap().get(invoiceType);
			String pOwerId = UseMoneyMngt.getProjectOwnerByInvoice(invoiceNo);
			String[] money = UseMoneyMngt.getPermitUseMoney(pOwerId, timeID, invoiceTypeName);
			float permitMoney = Float.parseFloat(money[1]);
			float useFee = Float.parseFloat(money[2]);
			budgetId = money[0];

			if (curflow.getNeedbudget() == 1 && (totalFee + useFee) > permitMoney) {
				notPermit.add(invoiceNo);
			} else {
				boolean ss = approvalDAO.approval(invoiceNo, flag, curStatus, nextStatus, approvalId, approvalName,
						appDateStr, nextApprovalId, totalFee, budgetId, nextfid,appAmount,comment);
				if (ss) {
					// 更新预算表
					UseMoneyMngt.updateUseMoney(budgetId, invoiceTypeName, totalFee + useFee);
					if (nextflow != null) {
						// 提醒下一级审批
						EmailHander.emailHander(nextApprovalId, invoiceNo, 1);
					} else {
						// 提醒已审批
						EmailHander.emailHander(approvalId, invoiceNo, 3);
					}
				}
			}
		}
		return budgetId;
	}

	private String getTimeID() {
		Date currentTime = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0, 6);
		return dateString;
	}

	private int approvalone(HttpServletRequest req, List<String> notPermit) {
		HttpSession session = req.getSession();
		String approvalId = (String) session.getAttribute("userID");
		String approvalName = (String) session.getAttribute("userName");
		int flag = Integer.parseInt(req.getParameter("flag"));

		String timeID = getTimeID();
		Date appDate = new Date(System.currentTimeMillis());
		String appDateStr = appDate.toLocaleString();
		ApprovalDAO approvalDAO = new ApprovalDAO();

		String invoiceNo = req.getParameter("lblBoe_No");
		int curflowid = Integer.parseInt(req.getParameter("ApprovalFlowId"));
		float appAmount = Float.parseFloat(req.getParameter("ApprovalAmount"));
		String comment = ParameterUtil.getChineseString(req, "comment");
		DApprovalFlow curflow = ApprovalFlowMngt.getCurrApprovalFlow(curflowid);
		DApprovalFlow nextflow = ApprovalFlowMngt.getNextApprovalFlow(curflowid);

		int invoiceType = Integer.valueOf(req.getParameter("invoiceType"));
		float totalFee = Float.parseFloat(req.getParameter("totalFee"));

		approvalCommon(req, notPermit, approvalId, approvalName, flag, timeID, appDateStr, approvalDAO, invoiceType, totalFee,
				invoiceNo, curflow, nextflow,appAmount,comment);

		init(req);
		if (notPermit.size() != 0) {
			return 1;
		}
		return 2;
	}

	private void init(HttpServletRequest req) {
		int status = 1;
		HttpSession session = req.getSession();
		String approvalId = (String) session.getAttribute("userID");
		String invoiceNo = "-1";
		int invoiceType = -1;
		int deptId = -1;
		String employeeName = "-1";
		int productId = -1;

		ApprovalDAO approvalDAO = new ApprovalDAO();
		ArrayList<DClaim> claimDetail = approvalDAO.getClaimDetail(status, approvalId, invoiceNo, invoiceType, deptId,
				employeeName, productId);
		req.setAttribute("claimDetail", claimDetail);

		AllDataInfoInit(req);
	}

	private void AllDataInfoInit(HttpServletRequest req) {
		req.setAttribute("userInfo", UserMngt.getInstance().getUserInfoMap());

		DeptDAO deptDAO = new DeptDAO();
		List<DDept> deptsInfo = deptDAO.getDepts();
		req.setAttribute("deptsInfo", deptsInfo);

	}
}
