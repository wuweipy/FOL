package View;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Business.BusinessFactory;
import Business.BankAccount.BBankAccount;
import Business.BankAccount.BBankAccountHandler;
import Common.BankMngt;
import Common.CurrencyMngt;
import Common.Location.LocationMngt;

/**
 * 与我有关：报销帐户
 * 
 * */

public class BankAccountServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	String action = req.getParameter("action");
    	if(action == null)
    	{
    		queryBankAccount(req,resp);
    	}
    	else if(action.equals("edit"))
    	{
    		editBankAccount(req,resp);
    	}
    	else if(action.equals("delete"))
    	{
    		removeBankAccount(req,resp);
    	}
    	else if(action.equals("add"))
    	{
    		addBankAccount(req,resp);
    	}
    }  

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 doGet(req, resp);
    }
	
	private void queryBankAccount(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException
	{
		setAccountStrings(req, resp);
    	req.getRequestDispatcher("bankAccount.jsp").forward(req, resp);
	}
	
	private void editBankAccount(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException 
	{
		
		HttpSession session = req.getSession();
    	BBankAccountHandler hander = new BBankAccountHandler();
		if(req.getParameter("dplCurrency") != null)
		{
			boolean saveSuccess = saveBankAccount(req,(String)session.getAttribute("userID"));
			req.setAttribute("success", saveSuccess);
		}
		
		BBankAccount bankAccount = hander.getAccount((String)session.getAttribute("userID"));
    	req.setAttribute("bankAccount", bankAccount);
        req.setAttribute("currencyInfo", CurrencyMngt.getInstance().getCurrencyMap());
        req.setAttribute("provinceInfo", LocationMngt.getInstance().getProvinceMap());
        req.setAttribute("bankInfo", BankMngt.getInstance().getBankMap());
        req.setAttribute("action", "edit");
    	req.getRequestDispatcher("EmpBankAccountEdit.jsp").forward(req, resp);
		
	}
	
	private boolean saveBankAccount(HttpServletRequest req, String userId) 
	{
		BBankAccount bankAccount = getAccountFromReq(req);
		return BusinessFactory.getFactory().getBankAccountHandler().saveBankAccount(bankAccount,userId);
	}
	
	private BBankAccount getAccountFromReq(HttpServletRequest req)
	{
		BBankAccount bankAccount = new BBankAccount();
		bankAccount.setCurrency(Integer.parseInt(req.getParameter("dplCurrency")));
		bankAccount.setProvince(Integer.parseInt(req.getParameter("province")));
		bankAccount.setCity(Integer.parseInt(req.getParameter("selectCity")));
		bankAccount.setBank(Integer.parseInt(req.getParameter("dptBankType")));
		bankAccount.setAccountBank(ParameterUtil.getChineseString(req, "txtAccountBank"));
		bankAccount.setAccountName(ParameterUtil.getChineseString(req, "txtAccountName"));
		bankAccount.setAccount(req.getParameter("txtAccountNumber"));
		return bankAccount;
	}
	
	private void setAccountStrings(HttpServletRequest req,
			HttpServletResponse resp)
	{
		HttpSession session = req.getSession();
    	BBankAccountHandler hander = new BBankAccountHandler();
    	String[] bankAccountStrings = hander.getAccountStrings((String)session.getAttribute("userID")); 
    	req.setAttribute("bankAccountStrings", bankAccountStrings);
	}
	

	private void addBankAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		HttpSession session = req.getSession();
		if(req.getParameter("dplCurrency") != null)
		{
			 boolean success = createBankAccount(req,(String)session.getAttribute("userID"));
			 req.setAttribute("success", success);
		}
		req.setAttribute("currencyInfo", CurrencyMngt.getInstance().getCurrencyMap());
	    req.setAttribute("provinceInfo", LocationMngt.getInstance().getProvinceMap());
	    req.setAttribute("bankInfo", BankMngt.getInstance().getBankMap());
	    req.setAttribute("action", "add");
	    req.getRequestDispatcher("EmpBankAccountEdit.jsp").forward(req, resp);
		
	}


	private boolean createBankAccount(HttpServletRequest req, String userId) 
	{
		BBankAccount bankAccount = getAccountFromReq(req);
		return BusinessFactory.getFactory().getBankAccountHandler().createBankAccount(bankAccount,userId);
	}
	
	private void removeBankAccount(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		BusinessFactory.getFactory().getBankAccountHandler().deleteBankAccount((String)session.getAttribute("userID"));
		req.setAttribute("bankAccountStrings", null);
		req.getRequestDispatcher("bankAccount.jsp").forward(req, resp);
	}
	
}
