package Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import Data.Common.Countersign.CountersignDao;
import Data.Common.Countersign.ICountersignDao;
import Data.Common.Countersign.DCountersign;

public class CountersignMngt {
    private static CountersignMngt countersignMngt = new CountersignMngt();
	
	private HashMap<Integer, DCountersign> countersignMap = new HashMap<Integer, DCountersign>();
	
	private CountersignMngt()
	{
		
	}

//	private
	public void init(String approvalId,int status) 
	{
		countersignMap.clear();
		ICountersignDao countersignDao = new CountersignDao();
        Iterator<DCountersign> iterator = countersignDao.getCountersignList(status,approvalId,"-1",-1,"-1",-1,-1).iterator();
        while(iterator.hasNext())
        {
        	DCountersign countersign = iterator.next();
        	countersignMap.put(countersign.getId(), countersign);
        }
	}

	public static CountersignMngt getInstance() 
	{
		return countersignMngt;
	}

//	public String getCurrencyName(int currencyID) 
//	{
//		return currencyMap.get(currencyID);
//	}

	public HashMap<Integer, DCountersign> getCountersignMap()
	{
		return countersignMap;
	}
	
	public static void main(String[] args) {
		CurrencyMngt.getInstance();
	}
	
	public void queryCountersign(int status,String approvalId,String invoiceNo,int invoiceType,String no,int department,int productId)
	{
		countersignMap.clear();
		CountersignDao countersignDao = new CountersignDao();
        Iterator<DCountersign> iterator = countersignDao.getCountersignList(status,approvalId,invoiceNo,invoiceType,no,department,productId).iterator();
        while(iterator.hasNext())
        {
        	DCountersign countersign = iterator.next();
        	countersignMap.put(countersign.getId(), countersign);
        }
	}

	public boolean permitCountersign(ArrayList<String> invoices,String approvalId,int status,String comment,String nextApprovalId, String appDate, String approvalName)
	{
		ICountersignDao countersignDao = new CountersignDao();
		boolean ss = countersignDao.permit(invoices,approvalId,status,comment,nextApprovalId,appDate, approvalName);
		
		countersignMap.clear();
		init(approvalId,status);
		return ss;
	}
	
	public boolean permitCountersignBudget(ArrayList<String> invoices,String approvalId,int status,String comment,String nextApprovalId, String appDate, String approvalName ,String budgetId)
	{
		ICountersignDao countersignDao = new CountersignDao();
		boolean ss = countersignDao.permit(invoices,approvalId,status,comment,nextApprovalId,appDate, approvalName ,budgetId);
		
		countersignMap.clear();
		init(approvalId,status);
		return ss;
	}
	
	public void notPermitCountersign(ArrayList<String> invoices,String approvalId,int status,String comment, String appDate, String approvalName)
	{
		ICountersignDao countersignDao = new CountersignDao();
		countersignDao.notPermit(invoices,approvalId,status,comment,appDate, approvalName);
		
		countersignMap.clear();
		init(approvalId,status);
	}	
}
