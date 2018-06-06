package View;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Common.Location.LocationMngt;

public class CityServlet extends HttpServlet 
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
			   deleteCity(req);
		   }
		   else if(action.equals("add"))
		   {
			   addCity(req);
		   }
		   req.setAttribute("provinceInfo", LocationMngt.getInstance().getProvinceMap());
//		   req.setAttribute("cityInfo", LocationMngt.getInstance().getCityMap());
		   req.getRequestDispatcher("CityIndex.jsp").forward(req, resp);
	    }
	   


		@Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
	    	 doGet(req, resp);
	    }
		
	    private void deleteCity(HttpServletRequest req) 
	    {
			int id = Integer.parseInt(req.getParameter("id")); 
			boolean isOk =  LocationMngt.getInstance().deleteCity(id);
			req.setAttribute("DelisSuccess", isOk);
			LocationMngt.getInstance().refresh();	
		}
	    
		private void addCity(HttpServletRequest req) 
		{
			int provinceid = Integer.parseInt(req.getParameter("provinceID")); 
			int id = Integer.parseInt(req.getParameter("txtCityID")); 
			String name = ParameterUtil.getChineseString(req, "txtCityName");
			boolean isOk =  LocationMngt.getInstance().addCity(provinceid, id, name);
			req.setAttribute("AddisSuccess", isOk);
			LocationMngt.getInstance().refresh();
		}
	    
	    
	
}
