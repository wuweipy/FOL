package View.BoeServletHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BoeState 
{	
   protected Page mPage;
   
   protected HttpServletRequest mRequest;
   
   protected HttpServletResponse mResponse;
   
   public BoeState(Page page,HttpServletRequest request,HttpServletResponse response)
   {
	   setRequest(request);
	   setResponse(response);
	   this.mPage = page;
   }
   
   public void setRequest(HttpServletRequest request)
   {
	   this.mRequest = request;   
   }
   
   public void setResponse(HttpServletResponse response)
   {
	   this.mResponse = response;
   }
   
   public void setReqResp(HttpServletRequest request,HttpServletResponse response)
   {
	   this.mRequest = request;
	   this.mResponse = response;
	   refreshServlet();
   }
   
   public abstract void refreshServlet();
   
   public abstract void enter() throws ServletException, IOException;
	
   public abstract void goPre() throws ServletException, IOException;
   
   public abstract void save() throws ServletException, IOException;
   
   public abstract void goNext() throws ServletException, IOException;
}
