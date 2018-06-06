package View;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.Claims.BMyClaim;
import Common.CourseMngt;
import Common.DeptMngt;
import Data.Approval.MyClaimDAO;
import Data.Project.DApprovalFlow;

/* “我审批的”对应的服务端动作 */
public class MyApprovalMngtServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		queryViewInitInfo(req, resp);

		req.setAttribute("statuses", DApprovalFlow.statuses);
		if (req.getParameter("action") != null && req.getParameter("action").equals("query")) {
			ArrayList<BMyClaim> claimInfos = new ArrayList<BMyClaim>();
			claimInfos = queryClaimInfo(req, resp);
			req.setAttribute("claimInfos", claimInfos);
		}
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.setAttribute("doFlag", true);
		req.getRequestDispatcher("MyApprovalMngtIndex.jsp").forward(req, resp);
	}

	private ArrayList<BMyClaim> queryClaimInfo(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("userID");

		String invoiceNo = "";
		int deptID = -1;
		int invoiceType = -1;
		int status = -1;
		String billNo = "";
		int payMax = -1;
		int payMin = -1;
		int approvalFlag = -1;
		String employeeName = "";

		String dateFrom = "";
		String dateTo = "";

		if (req.getParameter("txtInvoiceNo") != null) {
			invoiceNo = req.getParameter("txtInvoiceNo").trim();
		}
		if (req.getParameter("deptID") != null) {
			deptID = Integer.valueOf(req.getParameter("deptID"));
		}
		if (req.getParameter("typeID") != null) {
			invoiceType = Integer.valueOf(req.getParameter("typeID"));
		}
		if (req.getParameter("statusID") != null) {
			status = Integer.valueOf(req.getParameter("statusID"));
		}

		if (req.getParameter("txtPayFrom") != null && req.getParameter("txtPayFrom") != "") {
			payMax = Integer.valueOf(req.getParameter("txtPayFrom"));
		}
		if (req.getParameter("txtPayTo") != null && req.getParameter("txtPayTo") != "") {
			payMin = Integer.valueOf(req.getParameter("txtPayTo"));
		}
		/* dplApprovalFlag-审批意见 */
		if (req.getParameter("dplApprovalFlag") != null && req.getParameter("dplApprovalFlag") != "") {
			approvalFlag = Integer.valueOf(req.getParameter("dplApprovalFlag"));
		}
		/* txtEmployeeName-报销人 */
		if (req.getParameter("txtEmployeeName") != null && req.getParameter("txtEmployeeName") != "") {
			employeeName = ParameterUtil.getChineseString(req, "txtEmployeeName");
		}
		String username = (String) session.getAttribute("userName");

		if (req.getParameter("txtSubmitDateFrom") != null && req.getParameter("txtSubmitDateFrom") != "") {
			DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			timeFormat.setLenient(false);
			String str_date = req.getParameter("txtSubmitDateFrom");
			dateFrom = getStartDate(str_date);
		}
		if (req.getParameter("txtSubmitDateTo") != null && req.getParameter("txtSubmitDateTo") != "") {
			DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			timeFormat.setLenient(false);
			String str_date = req.getParameter("txtSubmitDateTo");
			dateTo = getEndDate(str_date);
		}

		MyClaimDAO dao = new MyClaimDAO();
		return dao.getMyApprovalInfos(id, invoiceNo, deptID, invoiceType, status, payMax, payMin, dateFrom, dateTo,
				approvalFlag, employeeName);

	}

	public String getStartDate(String date) {
		String from[] = date.split("-");
		DecimalFormat ndf = new DecimalFormat("00");
		String startDate = from[0] + "-" + ndf.format(Integer.valueOf(from[1])) + "-"
				+ ndf.format(Integer.valueOf(from[2]));
		return startDate;
	}

	public String getEndDate(String date) {
		String to[] = date.split("-");
		DecimalFormat ndf = new DecimalFormat("00");
		String endDate = to[0] + "-" + ndf.format(Integer.valueOf(to[1])) + "-" + ndf.format(Integer.valueOf(to[2]))
				+ " 23:59:59";
		return endDate;
	}

	public static void main(String[] args) {
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		timeFormat.setLenient(false);
		String ss = "2014-6-30 10:59:59 下午";
		try {
			// timeFormat.parse(ss);
			Timestamp dateTo = new Timestamp(timeFormat.parse(ss).getTime());
			System.out.println(timeFormat.parse(ss).getTime());
			System.out.println(dateTo.toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		DateFormat dateFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String abc = "2008-07-10 07:20:00 下午";
		try {
			Date date = null;
			date = dateFormat12.parse(abc);
			abc = dateFormat24.format(date);
			System.out.println(abc);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/* 显示页面控件中的下拉列表内容（包括显示部门下拉列表内容及单据类型） */
	private void queryViewInitInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		queryAllDeptInfo(req, resp);
		queryAllTypeInfo(req, resp);
	}

	private void queryAllTypeInfo(HttpServletRequest req, HttpServletResponse resp) {
		HashMap<Integer, String> types = new HashMap<Integer, String>();
		types.put(1, "国内差旅费");
		req.setAttribute("TypeInfos", types);

	}

	private void queryAllDeptInfo(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("DeptInfos", DeptMngt.getInstance().getDepts());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
