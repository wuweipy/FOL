package Data.ApprovalInfo;

import java.util.ArrayList;

/**
 * <ul>
 * <li>�ļ�����: IApprovalInfo</li>
 * <li>�ļ�����:</li>
 * <li>��Ȩ����: ��Ȩ����(C) 2003</li>
 * <li>�� ˾: ����ͨѶ�ɷ����޹�˾</li>
 * <li>����ժҪ:</li>
 * <li>����˵��:</li>
 * <li>�������: Sep 4, 2013</li>
 * </ul>
 * <ul>
 * <li>�޸ļ�¼:</li>
 * <li>�� �� ��:</li>
 * <li>�޸�����:</li>
 * <li>�� �� ��:</li>
 * <li>�޸�����:</li>
 * </ul>
 * 
 * @author Administrator
 * @version
 */
public interface IApprovalInfoDAO {
	public ArrayList<DApprovalInfo> getApprovalInfo(String invoiceNo, String approvalId, int deptId, int productId);

	public ArrayList<DApprovalInfo> getApprovalInfoByinoiceNo(String invoiceNo);

	public ArrayList<DApprovalInfo> getApprovalInfoByApprovalId(String approvalId);
}
