package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Common.CurrencyMngt;


public class CurrencyServlet extends HttpServlet 
{
	   @Override
	    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
		   String action = req.getParameter("action");
		   if(action == null)
		   {
		      
		   }
		   else if(action.equals("delete"))
		   {
			   deleteCurrency(req);
		   }
		   else if(action.equals("add"))
		   {
			   addCurrency(req);
		   }
		   req.setAttribute("currencyInfo", CurrencyMngt.getInstance().getCurrencyMap());
		   req.getRequestDispatcher("CurrencyIndex.jsp").forward(req, resp);
	    }
	   


		@Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
	    	 doGet(req, resp);
	    }
		
	    private void deleteCurrency(HttpServletRequest req) 
	    {
			int id = Integer.parseInt(req.getParameter("id")); 
			boolean isOk =  CurrencyMngt.getInstance().deleteCurrency(id);
			req.setAttribute("DelisSuccess", isOk);
			CurrencyMngt.getInstance().refresh();	
		}
	    
		private void addCurrency(HttpServletRequest req) 
		{
			int id = Integer.parseInt(req.getParameter("txtCurrencyID")); 
			String name = ParameterUtil.getChineseString(req, "txtCurrencyName");
			boolean isOk =  CurrencyMngt.getInstance().addCurrency(id, name);
			req.setAttribute("AddisSuccess", isOk);
			CurrencyMngt.getInstance().refresh();
		}
	    
	    
	
}
