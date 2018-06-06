package Data.FinanceApproval;

import java.util.ArrayList;

import Data.Claims.DClaim;

/**
 * <ul>
 * <li>�ļ�����: IFinanceApprovalDAO</li>
 * <li>�ļ�����: </li>
 * <li>��Ȩ����: ��Ȩ����(C) 2003</li>
 * <li>��   ˾: ����ͨѶ�ɷ����޹�˾</li>
 * <li>����ժҪ: </li>
 * <li>����˵��: </li>
 * <li>�������: Sep 3, 2013 </li>
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
public interface IFinanceApprovalDAO
{
    public ArrayList<DClaim> getClaimDetail(int status, String approvalId, String invoiceNo, int invoiceType, int deptId, String employeeName, int productId) ;
    public boolean approval(String invoiceNo, int flag, int status, int appAmount, String approvalId, String approvalName, String appDate);

}
