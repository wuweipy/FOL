package View;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.Claims.BClaim;
import Business.UserDetail.BUserDetail;
import Common.CourseMngt;
import Common.ProjectMngt;
import Common.RoleMngt;
import Common.UserMngt;
import Data.ApprovalInfo.ApprovalInfoDAO;
import Data.ApprovalInfo.DApprovalInfo;
import Data.ApprovalInfo.IApprovalInfoDAO;
import Data.Common.InvoiceDetailDAO;
/**
 * 报销总经理审批详细
 */
public class InvoiceDetailServletGM extends HttpServlet{
	   @Override
	    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
		   String invoiceNo = req.getParameter("InvoiceNoDe");
		   InvoiceDetailDAO invoiceDetail = new InvoiceDetailDAO();
		   BUserDetail userDetail = invoiceDetail.getUserDetail(invoiceNo);
		   BClaim bClaim = invoiceDetail.getBClaim(invoiceNo);
		   IApprovalInfoDAO approvalInfo = new ApprovalInfoDAO();
		   ArrayList<DApprovalInfo> dApprovalInfoList = approvalInfo.getApprovalInfoByinoiceNo(invoiceNo);
		   		   
		   req.setAttribute("roleInfo", RoleMngt.getInstance().getAllRoleInfo());
		   req.setAttribute("userInfoNo", UserMngt.getInstance().getUserInfoMap());		   
		   req.setAttribute("dApprovalInfoList", dApprovalInfoList);
		   req.setAttribute("userInfo", userDetail);
		   req.setAttribute("claim", bClaim);
		   req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		   req.setAttribute("prodInfo", ProjectMngt.getProjectMap());
		   req.getRequestDispatcher("InvoiceDetailInfoGM.jsp").forward(req, resp);	
	    }
	   
		@Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
	    	 doGet(req, resp);
	    }
}
