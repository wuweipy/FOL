package Data.ApprovalInfo;

/**
 * <ul>
 * <li>文件名称: DApprovalInfo</li>
 * <li>文件描述: </li>
 * <li>版权所有: 版权所有(C) 2003</li>
 * <li>公   司: 中兴通讯股份有限公司</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期: Sep 4, 2013 </li>
 * </ul>
 * <ul>
 * <li>修改记录: </li>
 * <li>版 本 号: </li>
 * <li>修改日期: </li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
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
