package View.BoeServletHelper;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Page {
	
	private HashMap<PageState, BoeState> states = new HashMap<PageState, BoeState>();
	
	private BoeState mState;
	
	protected HttpServletRequest mRequest;
	   
    protected HttpServletResponse mResponse;
	
	public Page(HttpServletRequest request,HttpServletResponse response)
	{
		this.mRequest = request;
		this.mResponse = response;
		states.put(PageState.First, new FirstPage(this,request,response));
		states.put(PageState.Second, new SecondPage(this,request,response));
		states.put(PageState.Third, new ThirdPage(this,request,response));
		states.put(PageState.Last, new LastPage(this,request,response));
	}
	
	public void setPageState(PageState state) throws ServletException, IOException
	{
		if(states.containsKey(state))
		{
			mState = states.get(state);
			mState.setReqResp(mRequest, mResponse);
			mState.enter();
		}
	}
	
	public void setReqResp(HttpServletRequest request,HttpServletResponse response)
	{
		this.mRequest = request;
		this.mResponse = response;
		this.mState.setReqResp(request, response);
	}
	
	public void setRequest(HttpServletRequest request)
	{
		this.mRequest = request;
		this.mState.setRequest(request);
	}
	
    public void setResponse(HttpServletResponse response)
	{
    	this.mResponse = response;
		this.mState.setResponse(response);
	}
	
	public void setPageAction(String action) throws ServletException, IOException
	{
         if(action.equals("pre"))
		 {
			 mState.goPre();
		 }
		 else if(action.equals("save"))
		 {
			 mState.save();
		 }
		 else if(action.equals("next"))
		 {
			 mState.goNext();
		 }
	}
}
