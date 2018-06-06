package View;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import Common.ProjectMngt;
import Data.Approval.MyClaimDAO;
import Data.Project.DApprovalFlow;

/* “我报销的”对应的服务端动作 */
public class MyClaimMngtServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		queryViewInitInfo(req, resp);
		HashMap<Integer, String> projectmap = ProjectMngt.getProjectMap();
		req.setAttribute("projects", projectmap);
		HashMap<Integer, String> statusmap = DApprovalFlow.statuses;
		req.setAttribute("statuses", statusmap);
		ArrayList<BMyClaim> claimInfos = new ArrayList<BMyClaim>();
		claimInfos = queryClaimInfo(req, resp);
		req.setAttribute("claimInfos", claimInfos);
		req.setAttribute("userName", (String) req.getSession().getAttribute("userName"));
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.setAttribute("doFlag", true);
		req.getRequestDispatcher("MyClaimMngtIndex.jsp").forward(req, resp);
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

		Date date = new Date(0, 0, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = format.format(date);
		Timestamp dateFrom = Timestamp.valueOf(time);
		Timestamp dateTo = Timestamp.valueOf(time);

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
		if (req.getParameter("txtBillNo") != null) {
			billNo = req.getParameter("txtBillNo").trim();
		}
		if (req.getParameter("txtPayFrom") != null && req.getParameter("txtPayFrom") != "") {
			payMax = Integer.valueOf(req.getParameter("txtPayFrom"));
		}
		if (req.getParameter("txtPayTo") != null && req.getParameter("txtPayTo") != "") {
			payMin = Integer.valueOf(req.getParameter("txtPayTo"));
		}

		if (req.getParameter("txtSubmitDateFrom") != null && req.getParameter("txtSubmitDateFrom") != "") {
			DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			timeFormat.setLenient(false);
			String str_date = req.getParameter("txtSubmitDateFrom");
			try {
				dateFrom = new Timestamp(timeFormat.parse(str_date).getTime());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (req.getParameter("txtSubmitDateTo") != null && req.getParameter("txtSubmitDateTo") != "") {
			DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			timeFormat.setLenient(false);
			String str_date = req.getParameter("txtSubmitDateTo");
			try {
				dateTo = new Timestamp(timeFormat.parse(str_date).getTime());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		MyClaimDAO dao = new MyClaimDAO();
		return dao.getMyClaimInfos(id, invoiceNo, deptID, invoiceType, status, billNo, payMax, payMin, dateFrom,
				dateTo);

	}

	/* 显示页面控件中的下拉列表内容（包括显示部门下拉列表内容及单据类型） */
	private void queryViewInitInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		queryAllDeptInfo(req, resp);
		queryAllTypeInfo(req, resp);

	}

	private void queryAllTypeInfo(HttpServletRequest req, HttpServletResponse resp) {
		HashMap<Integer, String> types = new HashMap<Integer, String>();
		types.put(0, "国内差旅费");
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
