package Data.ApprovalInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

/**
 * <ul>
 * <li>文件名称: ApprovalInfoDAO</li>
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
public class ApprovalInfoDAO implements IApprovalInfoDAO {
	private static Logger logger = FOLLogger.getLogger(ApprovalInfoDAO.class);

	public ArrayList<DApprovalInfo> getApprovalInfo(String invoiceNo, String approvalId, int deptId, int productId) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<DApprovalInfo> dApprovalInfoList = new ArrayList<DApprovalInfo>();
		DApprovalInfo dApprovalInfo = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.createStatement();
			rs = statement
					.executeQuery("SELECT * from APPROVALINFO where invoiceNo = '" + invoiceNo + "' order by appDate");

			for (int i = 0; rs.next(); i++) {
				dApprovalInfo = new DApprovalInfo();
				dApprovalInfo.setId(rs.getInt("id"));
				dApprovalInfo.setInvoiceNo(rs.getString("invoiceNo"));
				dApprovalInfo.setApprovalId(rs.getString("approvalId"));
				dApprovalInfo.setApprovalName(rs.getString("approvalName"));
				dApprovalInfo.setAppState(rs.getInt("appState"));
				dApprovalInfo.setComment(rs.getString("comment"));
				dApprovalInfo.setAppDate(rs.getString("appDate"));

				dApprovalInfoList.add(dApprovalInfo);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return dApprovalInfoList;
	}

	public ArrayList<DApprovalInfo> getApprovalInfoByinoiceNo(String invoiceNo) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<DApprovalInfo> dApprovalInfoList = new ArrayList<DApprovalInfo>();
		DApprovalInfo dApprovalInfo = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.createStatement();
			rs = statement
					.executeQuery("SELECT * from APPROVALINFO where invoiceNo = '" + invoiceNo + "' order by appDate");

			for (int i = 0; rs.next(); i++) {
				dApprovalInfo = new DApprovalInfo();
				dApprovalInfo.setId(rs.getInt("id"));
				dApprovalInfo.setInvoiceNo(rs.getString("invoiceNo"));
				dApprovalInfo.setStatus(rs.getInt("status"));
				dApprovalInfo.setApprovalId(rs.getString("approvalId"));
				dApprovalInfo.setApprovalName(rs.getString("approvalName"));
				dApprovalInfo.setAppState(rs.getInt("appState"));
				dApprovalInfo.setComment(rs.getString("comment"));
				dApprovalInfo.setAppDate(rs.getString("appDate"));

				dApprovalInfoList.add(dApprovalInfo);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return dApprovalInfoList;
	}

	public ArrayList<DApprovalInfo> getApprovalInfoByApprovalId(String approvalId) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<DApprovalInfo> dApprovalInfoList = new ArrayList<DApprovalInfo>();
		DApprovalInfo dApprovalInfo = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from APPROVALINFO where approvalId = '" + approvalId + "'");

			for (int i = 0; rs.next(); i++) {
				dApprovalInfo = new DApprovalInfo();
				dApprovalInfo.setId(rs.getInt("id"));
				dApprovalInfo.setInvoiceNo(rs.getString("invoiceNo"));
				dApprovalInfo.setStatus(rs.getInt("status"));
				dApprovalInfo.setApprovalId(rs.getString("approvalId"));
				dApprovalInfo.setApprovalName(rs.getString("approvalName"));
				dApprovalInfo.setAppState(rs.getInt("appState"));
				dApprovalInfo.setComment(rs.getString("comment"));
				dApprovalInfo.setAppDate(rs.getString("appDate"));

				dApprovalInfoList.add(dApprovalInfo);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return dApprovalInfoList;
	}

	public static void main(String[] args) {
		ApprovalInfoDAO claimsDAO = new ApprovalInfoDAO();
		ArrayList<DApprovalInfo> detail = claimsDAO.getApprovalInfoByinoiceNo("1000");
		System.out.println(detail.size());
		System.out.println(detail.isEmpty());

		if (!detail.isEmpty()) {
			for (int index = 0; index < detail.size(); index++) {
				System.out.println(index + "  " + detail.get(index).getInvoiceNo());
				System.out.println(index + "  " + detail.get(index).getStatus());
				System.out.println(index + "  " + detail.get(index).getComment());
				System.out.println(index + "  " + detail.get(index).getAppState());
				System.out.println(index + "  " + detail.get(index).getApprovalId());
				System.out.println(index + "  " + detail.get(index).getApprovalName());
				System.out.println(index + "  " + detail.get(index).getAppDate());
			}
		}
	}
}
