package Data.Common.Countersign;

import java.util.ArrayList;
import java.util.List;

public interface ICountersignDao {
	List<DCountersign> getCountersignList(int status,String approvalId,String invoiceNo,int invoiceType,String no,int deptID,int productId);



	boolean permit(ArrayList<String> invoices, String approvalId, int status ,String comment, String nextApprovalId, String appDate, String approvalName);

	boolean permit(ArrayList<String> invoices, String approvalId, int status ,String comment, String nextApprovalId, String appDate, String approvalName ,String budgetId);

	void notPermit(ArrayList<String> invoices, String approvalId, int status ,String comment, String appDate, String approvalName);


}
