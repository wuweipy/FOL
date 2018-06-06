package View.BoeServletHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.BusinessFactory;
import Business.Claims.BClaim;
import Business.UserDetail.BUserDetail;

public class ThirdPage extends BoeState {
	
	private HttpSession mSession;
	
	private BUserDetail userDetail;

	public ThirdPage(Page page,HttpServletRequest request,HttpServletResponse response) 
	{
		super(page,request,response);
		init();
	}
	
	private void init()
	{
	 	 BusinessFactory factory = BusinessFactory.getFactory();
		 mSession = mRequest.getSession();
		 userDetail = factory.getBUserDetailHanlder().getUserDetail(
				(String)mSession.getAttribute("userID"),(String)mSession.getAttribute("userName"));
		 mRequest.setAttribute("userInfo", userDetail); 
	}

	@Override
	public void goNext() throws ServletException, IOException 
	{
		saveClaim();
		mPage.setPageState(PageState.Last);
	}

	@Override
	public void goPre() throws ServletException, IOException 
	{
		mPage.setPageState(PageState.Second);
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
		int payMode = Integer.parseInt(mRequest.getParameter("paymentMode"));
		claim.setPayType(payMode);
		BusinessFactory.getFactory().getClaimHandler().savePayMode(payMode, claim.getInvoiceNo());	
		mRequest.setAttribute("claim", claim);
	}

	@Override
	public void enter() throws ServletException, IOException 
	{
		mRequest.setAttribute("claim", mSession.getAttribute("claim"));
		mRequest.getRequestDispatcher("BoeWizardIndex3.jsp").forward(mRequest, mResponse);	
	}

	@Override
	public void refreshServlet() 
	{
		mRequest.setAttribute("userInfo", userDetail);
	}

}
