package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.BankMngt;

public class BankServlet extends HttpServlet 
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
          throws ServletException, IOException
    {
		String action = req.getParameter("action");
		if(action == null)
		{
			
		}
		else if (action.equals("add")) 
		{
			addBank(req);
		}
		else if (action.equals("delete"))
		{
			deleteBank(req);
		}
		req.setAttribute("bankInfo", BankMngt.getInstance().getBankMap());
		req.getRequestDispatcher("BankMngt.jsp").forward(req, resp);
	}
    
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
          throws ServletException, IOException 
    {
		doGet(req, resp);
	}

	private void deleteBank(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		boolean isOK = BankMngt.getInstance().deleteBank(id);
		req.setAttribute("isDelSuccess", isOK);
		BankMngt.getInstance().refresh();
	}

	private void addBank(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("txtBankID"));
		String name = ParameterUtil.getChineseString(req, "txtBankName");
		boolean isOK = BankMngt.getInstance().addBank(id, name);
		req.setAttribute("isAddSuccess", isOK);
		BankMngt.getInstance().refresh();
	}
}
