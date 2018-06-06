package View;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.CourseMngt;
import Common.UserMngt;
import Common.UseMoneyMngt;
import Data.Claims.DClaim;
import Data.Common.DDept;
import Data.Common.DProduct;
import Data.Common.DeptDAO;
import Data.Common.ProductDAO;
import Data.FinanceApproval.FinInvoiceDetailDAO;
import Data.FinanceApproval.FinanceApprovalDAO;
import Jmail.EmailHander;

/**
 * 报销财务审批
 */
public class FinanceApprovalServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
    throws ServletException, IOException
    {
    	int chao = -1;
        String action = req.getParameter("action");
		List<String> notPermit = new ArrayList<String>();
        if(action == null || action.equals(""))
        {
             init(req);
        }
        else if (action.equals("query")) 
        {
             queryClaims(req);
        }
        else if (action.equals("approval"))
        {
             chao = approval(req,notPermit);
        }
        else if (action.equals("approvalOne"))
        {
             chao = approvalOne(req);
             init(req);
        }
		req.setAttribute("chao", chao);
		req.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		req.setAttribute("notPermit", notPermit);
        req.getRequestDispatcher("FinanceApprovalIndex.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
                           throws ServletException, IOException 
    {
        doGet(req, resp);
    }

    private void queryClaims(HttpServletRequest req) 
    {
        int status = 3; //状态：财务审批
        HttpSession session = req.getSession();
        String approvalId = (String)session.getAttribute("userID");
        String invoiceNo = req.getParameter("invoiceNo");
        int invoiceType = Integer.parseInt(req.getParameter("invoiceType"));
        int deptId = Integer.parseInt(req.getParameter("deptId"));
        int productId = Integer.parseInt(req.getParameter("productId"));
        String employeeName = ParameterUtil.getChineseString(req, "employeeName", "utf-8");
        
        FinanceApprovalDAO finApprovalDAO = new FinanceApprovalDAO();
        ArrayList<DClaim> claimDetail = finApprovalDAO.getClaimDetail(status, approvalId, invoiceNo, invoiceType, deptId, employeeName, productId);  
        req.setAttribute("claimDetail", claimDetail);
        
        AllDataInfoInit(req);

    }
    
    private int approval(HttpServletRequest req , List<String> notPermit) 
    {
        int status = 3; //状态：财务审批
        int chao = -1;
        HttpSession session = req.getSession();
        String approvalId = (String)session.getAttribute("userID");
        String approvalName = (String)session.getAttribute("userName");
        
        int flag = Integer.parseInt(req.getParameter("flag"));
        int selectedCount = Integer.parseInt(req.getParameter("selectedCount"));
        
        Date  appDate = new Date(System.currentTimeMillis());
        String appDateStr = appDate.toLocaleString();
        
        for(int i=0; i<selectedCount; i++)
        {
            String invoiceNo = req.getParameter("InvoiceNo"+i);
            int appAmount = Integer.parseInt(req.getParameter("ApprovalAmount"+i));
    		String no = req.getParameter("baoxiaoNo"+i);
    		int roleId = UserMngt.getInstance().getUserInfoMap().get(no).getRoleId();
    		if(roleId == 5 && flag==1)
    		{
    			int invoiceType = Integer.valueOf(req.getParameter("invoiceTypeNo"+i));
    			String invoiceTypeName = CourseMngt.getInstance()
    					.getCourseMap().get(invoiceType);
    			String timeID = getTimeID();
    			String[] money = UseMoneyMngt.getPermitUseMoney(no, timeID, invoiceTypeName);
    			float permitFee = Float.valueOf(money[1]);
    			float useFee = Float.valueOf(money[2]);
    			String budgetId = money[0];	
    			float totalFee = Float.valueOf(req.getParameter("totalFeeT"+i));
    			if ((totalFee + useFee) > permitFee) {							
    				   chao = 1;
    				   notPermit.add(invoiceNo);
    			} 
    			else {
    	            FinanceApprovalDAO finApprovalDAO = new FinanceApprovalDAO();
    		        if(finApprovalDAO.approval(invoiceNo, flag, status, appAmount, approvalId, approvalName, appDateStr ,budgetId))
    		        {
    			        UseMoneyMngt.updateUseMoney(budgetId, invoiceTypeName, totalFee + useFee);		        	
    		        }
    			}
    		} 
    		else{
	            FinanceApprovalDAO finApprovalDAO = new FinanceApprovalDAO();
	            finApprovalDAO.approval(invoiceNo, flag, status, appAmount, approvalId, approvalName, appDateStr);
    		}
            if(flag==0){ 
            	int invoiceTypeNo = Integer.valueOf(req.getParameter("invoiceTypeNo"+i));
            	String invoiceTypeName = CourseMngt.getInstance()
    			.getCourseMap().get(invoiceTypeNo);
            	float use = Float.valueOf(req.getParameter("totalFeeT"+i)); 
            	UseMoneyMngt.deleteUseMoney(invoiceTypeName, invoiceNo, use);
            }
        }
        //初始化页面
        init(req);
        return chao;
    }
    
    private int approvalOne(HttpServletRequest req) 
    {
        int status = 3; //状态：财务审批
        int chao = -1;
        HttpSession session = req.getSession();
        String approvalId = (String)session.getAttribute("userID");
        String approvalName = (String)session.getAttribute("userName");
        Date  appDate = new Date(System.currentTimeMillis());
        String appDateStr = appDate.toLocaleString();
        String invoiceNo = req.getParameter("InvoiceNoDe");
        float appAmount = Float.parseFloat(req.getParameter("ApprovalAmount"));
        String comment = ParameterUtil.getChineseString(req, "comment");
		String no = req.getParameter("lblEmployeeNo");
        int flag = Integer.parseInt(req.getParameter("flag"));
		int roleId = UserMngt.getInstance().getUserInfoMap().get(no).getRoleId();
		if(roleId == 5 && flag==1)
		{
			int invoiceType = Integer.valueOf(req.getParameter("invoiceType"));
			String invoiceTypeName = CourseMngt.getInstance()
					.getCourseMap().get(invoiceType);
			String timeID = getTimeID();
			String[] money = UseMoneyMngt.getPermitUseMoney(no, timeID, invoiceTypeName);
			float permitFee = Float.valueOf(money[1]);
			float useFee = Float.valueOf(money[2]);
			String budgetId = money[0];	
			float totalFee = Float.valueOf(req.getParameter("totalFee"));
			if ((totalFee + useFee) > permitFee) {							
				   chao = 1;						
			} 
			else {
		        FinInvoiceDetailDAO finInvoiceDetailDAO = new FinInvoiceDetailDAO();
		        if(finInvoiceDetailDAO.approvalOneBudget(invoiceNo, flag, status, appAmount, approvalId, approvalName, appDateStr, comment,budgetId))
		        {
			        UseMoneyMngt.updateUseMoney(budgetId, invoiceTypeName, totalFee + useFee);		        	
		        }
			}
		}
		else
		{	       
	        FinInvoiceDetailDAO finInvoiceDetailDAO = new FinInvoiceDetailDAO();
	        finInvoiceDetailDAO.approvalOne(invoiceNo, flag, status, appAmount, approvalId, approvalName, appDateStr, comment);		
		}   
        if(flag==0){
        	float use = Float.valueOf(req.getParameter("totalFee"));
        	int invoiceTypeNo = Integer.valueOf(req.getParameter("invoiceType"));
        	String invoiceTypeName = CourseMngt.getInstance()
			.getCourseMap().get(invoiceTypeNo);
        	UseMoneyMngt.deleteUseMoney(invoiceTypeName, invoiceNo, use);       	
        }	
        return chao;
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
	
    private void init(HttpServletRequest req) 
    {
        int status = 3; //状态：财务审批
        HttpSession session = req.getSession();
        String approvalId = (String)session.getAttribute("userID");
        String invoiceNo = "-1";
        int invoiceType = -1;
        int deptId = -1;
        String employeeName = "-1";
        int productId = -1;
        
        FinanceApprovalDAO finApprovalDAO = new FinanceApprovalDAO();
        ArrayList<DClaim> claimDetail = finApprovalDAO.getClaimDetail(status, approvalId, invoiceNo, invoiceType, deptId, employeeName, productId);
        req.setAttribute("claimDetail", claimDetail);
        
        AllDataInfoInit(req); 
    }
    
    private void AllDataInfoInit(HttpServletRequest req)
    {
        req.setAttribute("userInfo", UserMngt.getInstance().getUserInfoMap());
        
        DeptDAO deptDAO = new DeptDAO();
        List<DDept> deptsInfo = deptDAO.getDepts();
        req.setAttribute("deptsInfo", deptsInfo);
        
        ProductDAO productDAO = new ProductDAO();
        List<DProduct> productList = productDAO.getProductList();
        req.setAttribute("productInfo", productList);
    }
}
