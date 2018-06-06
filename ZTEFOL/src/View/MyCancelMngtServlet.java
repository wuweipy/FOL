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
import Common.DeptMngt;
import Data.Approval.MyClaimDAO;
import Common.CourseMngt;

/* “我撤销的”对应的服务端动作 */
public class MyCancelMngtServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		queryViewInitInfo(req, resp);
		
		if(req.getParameter("action") != null && req.getParameter("action").equals("cancel"))
		{
			String toCancelInvoice = req.getParameter("InvoiceNo");
			String [] invoiceNos=toCancelInvoice.split(";");
			int size = invoiceNos.length-1;
			for(int i=1;i<=size;i++)
			{
				String invoiceNo= invoiceNos[i];
				MyClaimDAO dao = new MyClaimDAO();
				if(!dao.toCancelClaim(invoiceNo))
				{
					req.setAttribute("cancelFail", true);	
				}
			}
			
		}
		ArrayList<BMyClaim> claimInfos = new ArrayList<BMyClaim>();
		claimInfos = queryClaimInfo(req, resp);
		req.setAttribute("claimInfos", claimInfos);	
		
		req.getRequestDispatcher("MyCancelMngtIndex.jsp").forward(req, resp);
	}

	private ArrayList<BMyClaim> queryClaimInfo(HttpServletRequest req,
			HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("userID");

		String invoiceNo = "";
		int invoiceType = -1;
	    String employeeName ="";

		Date date = new Date(0, 0, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = format.format(date);
		Timestamp dateFrom = Timestamp.valueOf(time);
		Timestamp dateTo = Timestamp.valueOf(time);

		if (req.getParameter("txtInvoiceNo") != null) {
			invoiceNo = req.getParameter("txtInvoiceNo").trim();
		}
		
		if (req.getParameter("typeID") != null) {
			invoiceType = Integer.valueOf(req.getParameter("typeID"));
		}
		
		
		/* txtEmployeeName-报销人
		if (req.getParameter("txtEmployeeName") != null
				&& req.getParameter("txtEmployeeName") != "") {
			employeeName = req.getParameter("txtEmployeeName").trim();
		} */

		if (req.getParameter("txtSubmitDateFrom") != null
				&& req.getParameter("txtSubmitDateFrom") != "") {
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
		if (req.getParameter("txtSubmitDateTo") != null
				&& req.getParameter("txtSubmitDateTo") != "") {
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
		return dao.getMyCancelInfos(id, invoiceNo, invoiceType, dateFrom, dateTo,"");

	}

	/* 显示页面控件中的下拉列表内容（包括显示单据类型下拉列表内容） */
	private void queryViewInitInfo(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		queryAllTypeInfo(req, resp);
	}

	private void queryAllTypeInfo(HttpServletRequest req,
			HttpServletResponse resp) {
		HashMap<Integer, String> types = CourseMngt.getInstance().getCourseMap();
		req.setAttribute("TypeInfos", types);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
