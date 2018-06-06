package Data.FinanceApproval;

import Business.Claims.BClaim;
import Business.UserDetail.BUserDetail;

/**
 * <ul>
 * <li>�ļ�����: IFinInvoiceDetailDAO</li>
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
public interface IFinInvoiceDetailDAO
{
    public BUserDetail getUserDetail(String invoiceNo);
    public BClaim getBClaim(String invoiceNo);
    public boolean approvalOne(String invoiceNo, int flag, int status, float appAmount, String approvalId, String approvalName, String appDate, String comment);
}
