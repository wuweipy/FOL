package Data.ApprovalInfo;

/**
 * <ul>
 * <li>�ļ�����: DApprovalInfo</li>
 * <li>�ļ�����: </li>
 * <li>��Ȩ����: ��Ȩ����(C) 2003</li>
 * <li>��   ˾: ����ͨѶ�ɷ����޹�˾</li>
 * <li>����ժҪ: </li>
 * <li>����˵��: </li>
 * <li>�������: Sep 4, 2013 </li>
 * </ul>
 * <ul>
 * <li>�޸ļ�¼: </li>
 * <li>�� �� ��: </li>
 * <li>�޸�����: </li>
 * <li>�� �� ��:</li>
 * <li>�޸�����:</li>
 * </ul>
 * @author Administrator
 * @version 
 */
public class DApprovalInfo
{
    private int id;
    
    private String invoiceNo;
    
    private int status;
    
    private String approvalId;
    
    private String approvalName;
    
    private int appState;
    
    private String comment;
    
    private String appDate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getInvoiceNo()
    {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getApprovalId()
    {
        return approvalId;
    }

    public void setApprovalId(String approvalId)
    {
        this.approvalId = approvalId;
    }

    public String getApprovalName()
    {
        return approvalName;
    }

    public void setApprovalName(String approvalName)
    {
        this.approvalName = approvalName;
    }

    public int getAppState()
    {
        return appState;
    }

    public void setAppState(int appState)
    {
        this.appState = appState;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getAppDate()
    {
        return appDate;
    }

    public void setAppDate(String appDate)
    {
        this.appDate = appDate;
    }

}
