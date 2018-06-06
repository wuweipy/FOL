package Business;

import java.util.HashMap;

import Business.BankAccount.BBankAccountHandler;
import Business.Claims.ClaimHandler;
import Business.UserDetail.BUserDetailHandler;

/*git ≤‚ ‘*/
public class BusinessFactory {
	
	private HashMap<String, Object> hanlderMap = new HashMap<String, Object>();
	
	private static BusinessFactory factory = new BusinessFactory();
	
	private BusinessFactory()
	{
		
	}
	
	public static BusinessFactory getFactory()
	{
		return factory;
	}

    public BUserDetailHandler getBUserDetailHanlder()
    {
    	BUserDetailHandler handler = (BUserDetailHandler)hanlderMap.get("BUserDetailHanlder");
    	if(handler == null)
    	{
    		handler = new BUserDetailHandler();
    		hanlderMap.put("BUserDetailHanlder", handler);
    	}
    	return handler;
    }
    
    public ClaimHandler getClaimHandler()
    {
    	ClaimHandler handler = (ClaimHandler)hanlderMap.get("ClaimHandler");
    	if(handler == null)
    	{
    		handler = new ClaimHandler();
    		hanlderMap.put("ClaimHandler", handler);
    	}
    	return handler;
    }
    
    public BBankAccountHandler getBankAccountHandler()
    {
    	BBankAccountHandler handler = (BBankAccountHandler)hanlderMap.get("BBankAccountHandler");
    	if(handler == null)
    	{
    		handler = new BBankAccountHandler();
    		hanlderMap.put("BBankAccountHandler", handler);
    	}
    	return handler;
    }

}
