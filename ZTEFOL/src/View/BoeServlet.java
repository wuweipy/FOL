package View;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Business.BusinessFactory;
import Business.Claims.BClaim;
import Business.Claims.BClaimItem;
import Business.UserDetail.BUserDetail;
import Common.EmployeeMngt;
import Common.Location.LocationMngt;
import Data.Common.EmployeeTypeDAO;
import static View.ParameterUtil.*;

public class BoeServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	BusinessFactory factory = BusinessFactory.getFactory();
    	HttpSession session = req.getSession();
    	BUserDetail userDetail = factory.getBUserDetailHanlder().getUserDetail(
    			(String)session.getAttribute("userID"),(String)session.getAttribute("userName"));
    	req.setAttribute("userInfo", userDetail);
		String action = req.getParameter("action");
		String saveActoin = req.getParameter("save");
		if(isEmpty(action))
		{
			if(!isEmpty(saveActoin) && saveActoin.equalsIgnoreCase("save"))
			{
				req.setAttribute("claim",createClaim(req, userDetail));
			}
			else if(!isEmpty(saveActoin) && saveActoin.equalsIgnoreCase("pre"))
			{
				req.setAttribute("claim", session.getAttribute("claim"));
			}
			req.setAttribute("cityInfo", LocationMngt.getInstance().getCityMap());
			req.getRequestDispatcher("BoeWizardIndex.jsp").forward(req, resp); 
		}
		else if(action.equalsIgnoreCase("2"))
		{
			if(!isEmpty(saveActoin) && saveActoin.equalsIgnoreCase("save"))
			{
				BClaim claim = saveClaimStep2(req,session);
				req.setAttribute("claim",claim);
				session.setAttribute("claim", claim);
			}
			else if(!isEmpty(saveActoin) && saveActoin.equalsIgnoreCase("pre"))
			{
				req.setAttribute("claim", session.getAttribute("claim"));
			}
			else 
			{
				BClaim claim = createClaim(req, userDetail);
				if(session.getAttribute("claim") != null)
				{
				   req.setAttribute("claim", session.getAttribute("claim"));
				}
				session.setAttribute("claim", claim);
			}
			
			req.setAttribute("employeeInfo",EmployeeMngt.getInstance().getEmployeTypes());
			req.getRequestDispatcher("BoeWizardIndex2.jsp").forward(req, resp);
		}
		else if(action.equalsIgnoreCase("3"))
		{
			BClaim claim = null;
			if(!isEmpty(saveActoin) && saveActoin.equalsIgnoreCase("save"))
			{
				claim = saveClaimStep3(req,session);	
			}
			else if(!isEmpty(saveActoin) && saveActoin.equalsIgnoreCase("submit"))
			{
				claim = saveClaimStep3(req,session);
			}
			else
			{
			    claim = saveClaimStep2(req,session);
			    session.setAttribute("claim", claim);
			}
			if(claim == null)
			{
				claim = (BClaim)session.getAttribute("claim");	
			}
			req.setAttribute("claim",claim);
			req.getRequestDispatcher("BoeWizardIndex3.jsp").forward(req, resp);
		}	
    }
    
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }
	
	private BClaim createClaim(HttpServletRequest req,BUserDetail userDetail)
	{
	   BClaim claim = new BClaim();
	   claim.setHasBill(Boolean.parseBoolean(req.getParameter("isHasBill")));
	   claim.setNo(userDetail.getNo());
	   claim.setProductId(userDetail.getProductId());
	   claim.setBillLoaction(Integer.parseInt(req.getParameter("deliver_area")));
	   claim.setDirectToFinance(Boolean.parseBoolean(req.getParameter("directTofinance")));
	   return BusinessFactory.getFactory().getClaimHandler().createClaim(claim);
	}
	
	private BClaim saveClaimStep2(HttpServletRequest req, HttpSession session) 
	{
		BClaim claim = (BClaim)session.getAttribute("claim");
		claim.setSummary(ParameterUtil.getChineseString(req, "boe_abstract"));
		claim.setAccountAdjust(getOptionParam(req,"adjust_amount"));
		claim.setEmployLevel(Integer.parseInt(req.getParameter("employee_grade")));
		claim.setHedgeAccount(getOptionParam(req,"strike_balance_amount"));
		claim.setItems(getClaimItems(req,claim.getInvoiceNo()));
		return BusinessFactory.getFactory().getClaimHandler().saveClaim(claim);
	}
	
	private BClaimItem[] getClaimItems(HttpServletRequest req,String invoinceNO)
	{
		int count = Integer.parseInt(req.getParameter("count"));
		BClaimItem[] items = new BClaimItem[count];
		for(int i = 3; i < count + 3; i++)
		{
		BClaimItem item = new BClaimItem();
		item.setStartDate(Date.valueOf(req.getParameter("start_date" + i)));
		item.setEndDate(Date.valueOf(req.getParameter("end_date" + i)));
		item.setStartCity(req.getParameter("Errand_City" + i));
		item.setDesProvince(Integer.parseInt(req.getParameter("province" + i)));
		item.setDesCity(req.getParameter("city" + i));
		item.setTransportation(Integer.parseInt(req.getParameter("traffic_type" + i)));
		item.setTransportCost(getOptionParam(req,"traffic_fee_amount" + i));
		item.setAccommodation(getOptionParam(req,"hotel_fee_amount" + i));
		item.setOtherCost(getOptionParam(req,"other_fee_amount" + i));
		item.setInvoinceNO(invoinceNO);
		items[i - 3] = item;
		}
		return items;
	}
	
	private BClaim saveClaimStep3(HttpServletRequest req, HttpSession session) {
		BClaim claim = (BClaim)session.getAttribute("claim");
		int payMode = Integer.parseInt(req.getParameter("paymentMode"));
		claim.setPayType(payMode);
		if(BusinessFactory.getFactory().getClaimHandler().savePayMode(payMode, claim.getInvoiceNo()))
		{
			return claim;
		}
		return null;
	}
	
	private int getOptionParam(HttpServletRequest req,String para)
	{
		if(req.getParameter(para).equals(""))
		{
			return -1;
		}
		return Integer.parseInt(req.getParameter(para));
	}
 
}
