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
import Data.FinanceApproval.FinInvoiceDetailDAO;

/**
 * 报销财务审批详细
 */

public class FinInvoiceDetailServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException 
    {        
        String invoiceNo = req.getParameter("InvoiceNoDe");
        FinInvoiceDetailDAO invoiceDetail = new FinInvoiceDetailDAO();
        BUserDetail userDetail = invoiceDetail.getUserDetail(invoiceNo);
        BClaim bClaim = invoiceDetail.getBClaim(invoiceNo);
        
        ApprovalInfoDAO appInfoDAO = new ApprovalInfoDAO();
        ArrayList<DApprovalInfo> dAppInfoList = appInfoDAO.getApprovalInfoByinoiceNo(invoiceNo);

        req.setAttribute("roleInfo", RoleMngt.getInstance().getAllRoleInfo());
        req.setAttribute("userInfoNo", UserMngt.getInstance().getUserInfoMap());     
        
        req.setAttribute("userInfo", userDetail);
        req.setAttribute("claim", bClaim);
        req.setAttribute("dApprovalInfoList", dAppInfoList);
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.setAttribute("prodInfo", ProjectMngt.getProjectMap());
        req.getRequestDispatcher("FinInvoiceDetailInfo.jsp").forward(req, resp); 
    }

    
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException 
    {
        doGet(req, resp);
    }
    
}
