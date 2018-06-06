package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Common.UseMoneyMngt;
import Common.CountersignMngt;
import Common.CourseMngt;
import Common.DeptMngt;
import Common.UserMngt;
import Data.Approval.ApprovalDAO;
import Data.ApprovalInfo.ApprovalInfoDAO;
import Data.Claims.ClaimsDAO;
import Data.Common.ProductDAO;
import Data.Common.Countersign.DCountersign;
import Data.DBHandler.DBConnectorFactory;
import Data.UserInfo.DUser;
import Data.UserInfo.UserInfoDao;
import Jmail.EmailHander;

/**
 * 报销总经理审批
 */
public class GeneralManagerServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int chao = -1;
		int status = 7; // 状态：待总经理审批
		List<String> notPermit = new ArrayList<String>();
		String approvalId = getapprovalId(req);
		HttpSession session = req.getSession();
		String approvalName = (String) session.getAttribute("userName");
		String invoiceNoD = req.getParameter("lblBoe_No");
		String action = req.getParameter("action");
		String comment = ParameterUtil.getChineseString(req, "comment");
		String nextApprovalId = getNextApprovalId();
		if (action == null) {
			CountersignMngt.getInstance().init(approvalId, status);
		} else if (action.equals("query")) {
			queryCountersign(req, approvalId, status);
		} else if (action.equals("permit")) {
			chao = permitCountersign(req, approvalId, status, nextApprovalId,
					approvalName, notPermit);
		} else if (action.equals("notPermit")) {
			notPermitCountersign(req, approvalId, status, approvalName);
		} else if (action.equals("permitone")) {
			int productId = new ClaimsDAO().getClaim(invoiceNoD).getProductId();
			String no = req.getParameter("lblEmployeeNumber");
			float totalFee = Float.valueOf(req.getParameter("TotalFee"));
			String timeID = getTimeID();
			int deptId = UserMngt.getInstance().getUserInfoMap().get(no)
					.getDeptId();
			//deptId = productId;
			int invoiceType = Integer.valueOf(req.getParameter("invoiceType"));
			String invoiceTypeName = CourseMngt.getInstance().getCourseMap()
					.get(invoiceType);
			String[] money;
			if ( noNeedCalcuteMoney(invoiceNoD) )
			{
				ArrayList<String> invoices = new ArrayList<String>();
				invoices.add(invoiceNoD);
				CountersignMngt.getInstance().permitCountersign(invoices,
						approvalId, status, comment, nextApprovalId, getDate(),
						approvalName);
			}
			else {
		    if (deptId == 6 || deptId == 9 || deptId == 10
					|| deptId == 11 || deptId == 12) {
				money = UseMoneyMngt.getPermitUseMoney(no, timeID,
						invoiceTypeName);
			} 
			else 
			{
				money = UseMoneyMngt.getPermitUseMoneyByDept(deptId, timeID,
						invoiceTypeName);
			}
			float permitFee = Float.valueOf(money[1]);
			float useFee = Float.valueOf(money[2]);
			String budgetId = money[0];
			if (useFee + totalFee > permitFee) {
				chao = 1;
			} else {
				if (permitone(invoiceNoD, approvalId, status, comment,
						nextApprovalId, approvalName ,budgetId)) {
					UseMoneyMngt.updateUseMoney(budgetId, invoiceTypeName,
							useFee + totalFee);
					EmailHander.emailHander(nextApprovalId, invoiceNoD, 1);
				}
			}
		  }
		} else if (action.equals("notPermitone")) {
			notPermitone(invoiceNoD, approvalId, status, comment, approvalName);
			EmailHander.emailHander(approvalId, invoiceNoD, 2);
		}
		HashMap<Integer, DCountersign> map = CountersignMngt.getInstance()
				.getCountersignMap();
		ProductDAO productDAO = new ProductDAO();
		req.setAttribute("productInfo", productDAO.getProductList());
		req.setAttribute("deptInfo", DeptMngt.getInstance().getDepts());
		req.setAttribute("userInfo", UserMngt.getInstance().getUserInfoMap());
		req.getSession().setAttribute("count", map.size());
		req.setAttribute("countersignInfo", map);
		req.setAttribute("chao", chao);
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.setAttribute("notPermit", notPermit);
		req.getRequestDispatcher("GeneralManagerIndex.jsp").forward(req, resp);
	}

	private boolean noNeedCalcuteMoney(String invoiceNoD) {
		return new ApprovalInfoDAO().getApprovalInfoByinoiceNo(invoiceNoD).size() > 0;
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private String getTimeID() {
		// TODO Auto-generated method stub
		Date currentTime = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0, 6);
		return dateString;
	}

	public String getNextApprovalId() {
		String nextApprovalId = "0";
		UserInfoDao userInfoDao = new UserInfoDao();
		ArrayList<DUser> userInfo = userInfoDao.getAllUsers();
		if (!userInfo.isEmpty()) {
			for (int i = 0; i < userInfo.size(); i++) {
				// RoleId=4为财务审批的角色ID
				if (userInfo.get(i).getRoleId() == 4) {
					nextApprovalId = userInfo.get(i).getNo();
				}
			}
		}
		return nextApprovalId;
	}

	public String getapprovalId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String approvalId = (String) session.getAttribute("userID");
		return approvalId;
	}

	public String getDate() {
		Date appDate = new Date(System.currentTimeMillis());
		String appDateStr = appDate.toLocaleString();
		return appDateStr;
	}

	private int permitCountersign(HttpServletRequest req, String approvalId,
			int status, String nextApprovalId, String approvalName,
			List<String> notPermit) {
		String comment = "同意";
		HttpSession session = req.getSession();
		int count = (Integer) session.getAttribute("count");
		String timeID = getTimeID();
		for (int i = 1; i <= count; i++) {
			if ((req.getParameter("isCheck" + i) != null)
					&& req.getParameter("isCheck" + i).equals("on")) {
				String invoiceNo = req.getParameter("InvoiceNo" + i);
				String no = req.getParameter("UserNo" + i);
				int deptId = Integer.valueOf(req.getParameter("DeptId" + i));
				int invoiceType = Integer.valueOf(req.getParameter("invoiceType" + i));
				String invoiceTypeName = CourseMngt.getInstance().getCourseMap().get(invoiceType);
				String[] money;
				if (deptId == 6 || deptId == 9 || deptId == 10
						|| deptId == 11 || deptId == 12) {
					money = UseMoneyMngt.getPermitUseMoney(no, timeID,invoiceTypeName);
				} else {
					money = UseMoneyMngt.getPermitUseMoneyByDept(deptId,timeID, invoiceTypeName);
				}
				float totalFee = Float.valueOf(req.getParameter("TotalFee" + i));
				float permitFee = Float.valueOf(money[1]);
				float useFee = Float.valueOf(money[2]);
				String budgetId = money[0];
				if ((totalFee + useFee) > permitFee) {
					notPermit.add(invoiceNo);
				} else {
					ArrayList<String> invoices = new ArrayList<String>();
					invoices.add(invoiceNo);
					if(CountersignMngt.getInstance().permitCountersignBudget(invoices,
							approvalId, status, comment, nextApprovalId,
							getDate(), approvalName,budgetId)){
						UseMoneyMngt.updateUseMoney(budgetId, invoiceTypeName,
								useFee + totalFee);
						EmailHander.emailHander(nextApprovalId, invoiceNo, 1);
						}
					}
				}
			}
		if (notPermit.size() != 0) {
			return 1;
		}
		return 2;
	}

	private void notPermitCountersign(HttpServletRequest req,
			String approvalId, int status, String approvalName) {
		String comment = "退回";
		HttpSession session = req.getSession();
		int count = (Integer) session.getAttribute("count");
		ArrayList<String> invoices = new ArrayList<String>();
		for (int i = 1; i <= count; i++) {
			if ((req.getParameter("isCheck" + i) != null)
					&& req.getParameter("isCheck" + i).equals("on")) {
				String no = req.getParameter("InvoiceNo" + i);
				invoices.add(no);
				EmailHander.emailHander(approvalId, no, 2);
			}
		}
		CountersignMngt.getInstance().notPermitCountersign(invoices,
				approvalId, status, comment, getDate(), approvalName);
	}

	private boolean permitone(String invoiceNo, String approvalId, int status,
			String comment, String nextApprovalId, String approvalName, String budgetId) {

		ArrayList<String> invoices = new ArrayList<String>();
		invoices.add(invoiceNo);
		return CountersignMngt.getInstance().permitCountersignBudget(invoices,
				approvalId, status, comment, nextApprovalId, getDate(),
				approvalName, budgetId);
	}

	private void notPermitone(String invoiceNo, String approvalId, int status,
			String comment, String approvalName) {
		ArrayList<String> invoices = new ArrayList<String>();
		invoices.add(invoiceNo);
		CountersignMngt.getInstance().notPermitCountersign(invoices,
				approvalId, status, comment, getDate(), approvalName);
	}

	private void queryCountersign(HttpServletRequest req, String approvalId,
			int status) {
		String invoiceNo = " ";
		if (ParameterUtil.getChineseString(req, "invoiceNo").equals(""))
			invoiceNo = "-1";
		else
			invoiceNo = ParameterUtil.getChineseString(req, "invoiceNo");

		int invoiceType = 0;
		if (ParameterUtil.getChineseString(req, "invoiceType").equals(""))
			invoiceType = -1;
		else
			invoiceType = Integer.parseInt(req.getParameter("invoiceType"));

		int department = 0;
		if (ParameterUtil.getChineseString(req, "department").equals(""))
			department = -1;
		else
			department = Integer.parseInt(req.getParameter("department"));

		String no = " ";
		String name = ParameterUtil.getChineseString(req, "userName", "utf-8");
		if (name.equals(""))
			no = "-1";
		else {
			if (UserMngt.getInstance().getUserInfoMapName().get(name) == null)
				no = "-2";
			else
				no = UserMngt.getInstance().getUserInfoMapName().get(name)
						.getNo();
		}

		int productId = 0;
		if (ParameterUtil.getChineseString(req, "product").equals(""))
			productId = -1;
		else
			productId = Integer.parseInt(req.getParameter("product"));

		CountersignMngt.getInstance().queryCountersign(status, approvalId,
				invoiceNo, invoiceType, no, department, productId);
		// CountersignMngt.getInstance().refresh();
	}
}
