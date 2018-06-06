package View.BoeServletHelper;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Business.BusinessFactory;
import Business.Claims.BClaim;
import Business.Claims.BClaimItem;
import Common.EmployeeMngt;
import Common.Location.LocationMngt;
import View.ParameterUtil;

public class SecondPage extends BoeState {

	private HttpSession mSession;
	
	public SecondPage(Page page,HttpServletRequest request,HttpServletResponse response) {
		super(page,request,response);
		init();
	}

	private void init() 
	{
		mSession = mRequest.getSession();
		refreshServlet();
	}

	@Override
	public void goNext() throws ServletException, IOException
	{
		saveClaim();
		mPage.setPageState(PageState.Third);	
	}

	@Override
	public void goPre() throws ServletException, IOException
	{
		mPage.setPageState(PageState.First);
	}

	@Override
	public void save() throws ServletException, IOException
	{
		saveClaim();
        enter();
	}
	
	private void saveClaim()
	{
		BClaim claim = (BClaim)mSession.getAttribute("claim");
		claim.setSummary(ParameterUtil.getChineseString(mRequest, "boe_abstract"));
		ParameterUtil.getChineseString(mRequest, "comment","gb2312");
		claim.setAccountAdjust(getOptionParam(mRequest,"adjust_amount"));
		if(mRequest.getParameter("employee_grade") == null)
			claim.setEmployLevel(1);
		else
			claim.setEmployLevel(Integer.parseInt(mRequest.getParameter("employee_grade")));
		claim.setHedgeAccount(getOptionParam(mRequest,"strike_balance_amount"));
		int invoiceType = claim.getInvoiceType();
		if(invoiceType==5){
			claim.setItems(getClaimItems(mRequest,claim.getInvoiceNo()));
		}
		else{
			claim.setTotalFee(Float.valueOf(mRequest.getParameter("expense_amount_total")));			
		}
		claim = BusinessFactory.getFactory().getClaimHandler().saveClaim(claim);
	}
	
	private BClaimItem[] getClaimItems(HttpServletRequest req,String invoinceNO)
	{
		String[] count = null;
		if(req.getParameter("count") != null)
			count = req.getParameter("count").trim().split(" ");
		BClaimItem[] items = new BClaimItem[count.length];
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");  
		Date tDate = null;
		for (int j = 0; j < items.length; j++) 
		{
			int i = Integer.parseInt(count[j]);
			BClaimItem item = new BClaimItem();
			try {
				tDate = new Date(sf.parse(req.getParameter("start_date" + i)).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item.setStartDate(tDate);
			try {
				tDate = new Date(sf.parse(req.getParameter("end_date" + i)).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item.setEndDate(tDate);
			item.setStartCity(ParameterUtil.getChineseString(req, "Errand_City"
					+ i));
			item.setDesProvince(Integer.parseInt(req.getParameter("province"
					+ i)));
			item.setDesCity(ParameterUtil.getChineseString(req, "city" + i));
			item.setTransportation(Integer.parseInt(req.getParameter("traffic_type" + i)));
			item.setTransportCost(getOptionParamFloat(req, "traffic_fee_amount" + i));
			item.setAccommodation(getOptionParamFloat(req, "hotel_fee_amount" + i));
			item.setOtherCost(getOptionParamFloat(req, "other_fee_amount" + i));
			item.setInvoinceNO(invoinceNO);
			items[j] = item;
		}
		return items;
	}
	
	private int getOptionParam(HttpServletRequest req,String para)
	{
		if(req.getParameter(para) == null || req.getParameter(para).equals(""))
		{
			return 0;
		}
		return Integer.parseInt(req.getParameter(para));
	}
	
	private float getOptionParamFloat(HttpServletRequest req,String para)
	{
		if(req.getParameter(para) == null || req.getParameter(para).equals(""))
		{
			return 0;
		}
		return Float.parseFloat(req.getParameter(para));
	}

	@Override
	public void enter() throws ServletException, IOException 
	{
		mRequest.setAttribute("province", LocationMngt.getInstance().getProvinceMap());
		mRequest.setAttribute("employeeInfo",EmployeeMngt.getInstance().getEmployeTypes());
		mRequest.setAttribute("claim", mSession.getAttribute("claim"));
		int invoiceType = 1;
		if((BClaim)mSession.getAttribute("claim") != null)
			invoiceType = ((BClaim)mSession.getAttribute("claim")).getInvoiceType();	
		if(invoiceType==5){
			mRequest.getRequestDispatcher("BoeWizardIndex2.jsp").forward(mRequest, mResponse);
		}
		else{
			mRequest.getRequestDispatcher("BoeWizardIndex2Other.jsp").forward(mRequest, mResponse);
		}
	}

	@Override
	public void refreshServlet() 
	{
		mRequest.setAttribute("employeeInfo",EmployeeMngt.getInstance().getEmployeTypes()); 
	}

}

