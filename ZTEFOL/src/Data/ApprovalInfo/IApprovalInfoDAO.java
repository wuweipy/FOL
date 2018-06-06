package Data.ApprovalInfo;

import java.util.ArrayList;

/**
 * <ul>
 * <li>文件名称: IApprovalInfo</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2003</li>
 * <li>公 司: 中兴通讯股份有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>完成日期: Sep 4, 2013</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
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
