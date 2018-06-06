package View;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.Claims.BClaim;
import Business.UserDetail.BUserDetail;
import Common.CourseMngt;
import Common.RoleMngt;
import Common.UserMngt;
import Data.ApprovalInfo.ApprovalInfoDAO;
import Data.ApprovalInfo.DApprovalInfo;
import Data.ApprovalInfo.IApprovalInfoDAO;
import Data.Common.InvoiceDetailDAO;

/**
 * …Û≈˙œÍœ∏
 */
public class ApprovalDetailServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String invoiceNo = req.getParameter("InvoiceNoDe");
		InvoiceDetailDAO invoiceDetail = new InvoiceDetailDAO();
		BUserDetail userDetail = invoiceDetail.getUserDetail(invoiceNo);
		BClaim bClaim = invoiceDetail.getBClaim(invoiceNo);
		IApprovalInfoDAO approvalInfo = new ApprovalInfoDAO();
		HttpSession session = req.getSession();
		ArrayList<DApprovalInfo> dApprovalInfoList = approvalInfo.getApprovalInfo(invoiceNo,
				(String) session.getAttribute("userID"), userDetail.getDeptID(), userDetail.getProductId());

		req.setAttribute("doFlag", req.getParameter("doFlag"));

		String approvalId = (String) session.getAttribute("userID");
		if(UserMngt.getInstance().getUserInfoMap().get(approvalId).getRoleId()==4){
			req.setAttribute("displayFlag", 1);
		}else{
			req.setAttribute("displayFlag", 0);
		}
		req.setAttribute("roleInfo", RoleMngt.getInstance().getAllRoleInfo());
		req.setAttribute("userInfoNo", UserMngt.getInstance().getUserInfoMap());
		req.setAttribute("dApprovalInfoList", dApprovalInfoList);
		req.setAttribute("userInfo", userDetail);
		req.setAttribute("claim", bClaim);
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
//		req.setAttribute("prodInfo", ProductMngt.getInstance().getProducts());
		req.getRequestDispatcher("ApprovalDetailInfo.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
