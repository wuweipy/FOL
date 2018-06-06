package Data.FinanceApproval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import Business.Claims.BClaim;
import Business.Claims.BClaimItem;
import Business.UserDetail.BUserDetail;
import Common.ApprovalFlowMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Common.UserMngt;
import Data.DBHandler.DBConnectorFactory;
import Data.Project.DApprovalFlow;
import Data.UserDetail.DUserDetail;
import Jmail.EmailHander;

/**
 * <ul>
 * <li>文件名称: FinInvoiceDetailDAO</li>
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

public class FinInvoiceDetailDAO implements IFinInvoiceDetailDAO {
	private static Logger logger = FOLLogger.getLogger(FinInvoiceDetailDAO.class);

	public BUserDetail getUserDetail(String invoiceNo) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		BUserDetail bUserDetail = null;
		if (connection == null) {
			return bUserDetail;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from USERDETAIL where NO = (SELECT no from CLAIMS where invoiceNo = '"
					+ invoiceNo + "')");
			DUserDetail dUserDetail = new DUserDetail();
			String no = "";
			while (rs.next()) {
				dUserDetail.setId(rs.getInt("ID"));
				no = rs.getString("NO");
				dUserDetail.setNo(no);
				dUserDetail.setAllNo(rs.getString("ALLNO"));
				dUserDetail.setDeptID(rs.getInt("DEPTID"));
				dUserDetail.setEmail(rs.getString("EMAIL"));
				dUserDetail.setTel(rs.getString("TEL"));
				dUserDetail.setProductNo(rs.getInt("PRODUCTID"));
				dUserDetail.setTechTitleNo(rs.getInt("TECHTITLEID"));
			}

			String userName = "";
			rs = statement.executeQuery("SELECT * from USERINFO where NO = '" + no + "'");
			while (rs.next()) {
				userName = rs.getString("USERNAME");
			}
			bUserDetail = new BUserDetail(dUserDetail, userName);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return bUserDetail;
	}

	@SuppressWarnings("unchecked")
	public BClaim getBClaim(String invoiceNo) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		BClaim bClaim = null;
		if (connection == null) {
			return bClaim;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			bClaim = new BClaim();
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from CLAIMS where invoiceNo = '" + invoiceNo + "'");
			while (rs.next()) {
				bClaim.setId(rs.getInt("id"));
				bClaim.setInvoiceNo(invoiceNo);
				bClaim.setPayType(rs.getInt("payType"));
				bClaim.setProductId(rs.getInt("productId"));
				bClaim.setSubProductId(rs.getInt("subProductId"));
				bClaim.setProductName(ProjectMngt.getProjectName(rs.getInt("productId")));
				bClaim.setSubProductName(ProjectMngt.getSubProjectMap2().get(rs.getInt("subProductId")));
				bClaim.setBillLoaction(rs.getInt("billLocation"));
				bClaim.setAccountAdjust(rs.getInt("accountAdjust"));
				bClaim.setEmployLevel(rs.getInt("employeeLevel"));
				bClaim.setCurrencyType(rs.getInt("currencyType"));
				bClaim.setHedgeAccount(rs.getInt("hedgeAccount"));
				bClaim.setNo(rs.getString("no"));
				bClaim.setApprovalId(rs.getString("approvalId"));
				bClaim.setBillNo(rs.getString("billNo"));
				bClaim.setSummary(rs.getString("summary"));
				bClaim.setHasBill(rs.getBoolean("hasBill"));
				bClaim.setDirectToFinance(rs.getBoolean("directToFinance"));
				bClaim.setDeptId(rs.getInt("deptId"));
				bClaim.setDeptName(DeptMngt.getInstance().getDeptName(rs.getInt("deptId")));
				bClaim.setTotalFee(rs.getFloat("totalFee"));
				bClaim.setSubmitDate(rs.getTimestamp("submitDate"));
				bClaim.setInvoiceType(rs.getInt("invoiceType"));
				bClaim.setApprovalAmount(rs.getInt("approvalAmount"));

				int afid = rs.getInt("approvalflowid");
				if (afid == -1) {
					bClaim.setApprovalName("");
					bClaim.setStatusDesc(DApprovalFlow.getStatusMoreDesc(rs.getInt("status")));
				} else {
					bClaim.setApprovalName(
							ApprovalFlowMngt.approvalFlowMap.get(rs.getInt("approvalflowid")).getApprovalname());
					bClaim.setStatusDesc(
							ApprovalFlowMngt.approvalFlowMap.get(rs.getInt("approvalflowid")).getApprovalmoredesc());
				}
			}
			if (bClaim.getInvoiceType() == 5) {
				rs = statement
						.executeQuery("SELECT * from CLAIMITEM where InvoinceNO = '" + invoiceNo + "' ORDER BY id");
				ArrayList<BClaimItem> items = new ArrayList<BClaimItem>();
				while (rs.next()) {
					BClaimItem item = new BClaimItem();
					item.setId(rs.getInt("id"));
					item.setStartDate(rs.getDate("startDate"));
					item.setEndDate(rs.getDate("endDate"));
					item.setStartCity(rs.getString("startCity"));
					item.setDesProvince(rs.getInt("desProvince"));
					item.setDesCity(rs.getString("desCity"));
					item.setTransportation(rs.getInt("transportation"));
					item.setTransportCost(rs.getFloat("transportCost"));
					item.setAccommodation(rs.getFloat("accommodation"));
					item.setInvoinceNO(invoiceNo);
					item.setOtherCost(rs.getFloat("otherCost"));
					items.add(item);
				}
				BClaimItem[] itemDS = new BClaimItem[items.size()];
				bClaim.setItems(items.toArray(itemDS));
			}
		}

		catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return bClaim;
	}

	public boolean approvalOne(String invoiceNo, int flag, int status, float appAmount, String approvalId,
			String approvalName, String appDate, String comment) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		// String comment = "";
		int nextStatus = 1;
		if (flag == 1) {
			nextStatus = 4; // 财务审批通过之后的下一状态：关闭
			// comment = "同意";
			EmailHander.emailHander(approvalId, invoiceNo, 3);
		} else if (flag == 0) {
			nextStatus = 5; // 财务审批不通过的下一状态：待确定
			// comment = "不同意";
			EmailHander.emailHander(approvalId, invoiceNo, 2);
		}

		try {
			String sql = "update CLAIMS set status=?, approvalAmount=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, nextStatus);
			statement.setFloat(2, appAmount);
			statement.setString(3, invoiceNo);

			statement.executeUpdate();

			String insertSql = "insert into APPROVALINFO(invoiceNo,status,approvalId, "
					+ "approvalName,appState,comment,appDate) values (?,?,?,?,?,?,?)";
			statement2 = connection.prepareStatement(insertSql);
			statement2.setString(1, invoiceNo);
			statement2.setInt(2, status);
			statement2.setString(3, approvalId);
			statement2.setString(4, approvalName);
			statement2.setInt(5, flag);
			statement2.setString(6, comment);
			statement2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

			statement2.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement2, null);
		}

		return true;
	}

	public boolean approvalOneBudget(String invoiceNo, int flag, int status, float appAmount, String approvalId,
			String approvalName, String appDate, String comment, String budgetId) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		// String comment = "";
		int nextStatus = 1;
		if (flag == 1) {
			nextStatus = 4; // 财务审批通过之后的下一状态：关闭
			// comment = "同意";
			EmailHander.emailHander(approvalId, invoiceNo, 3);
		} else if (flag == 0) {
			nextStatus = 5; // 财务审批不通过的下一状态：待确定
			// comment = "不同意";
			EmailHander.emailHander(approvalId, invoiceNo, 2);
		}

		try {
			String sql = "update CLAIMS set status=?, approvalAmount=?, budgetId=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);

			statement.setInt(1, nextStatus);
			statement.setFloat(2, appAmount);
			statement.setString(3, budgetId);
			statement.setString(4, invoiceNo);
			statement.executeUpdate();

			String insertSql = "insert into APPROVALINFO(invoiceNo,status,approvalId, "
					+ "approvalName,appState,comment,appDate) values (?,?,?,?,?,?,?)";
			statement2 = connection.prepareStatement(insertSql);
			statement2.setString(1, invoiceNo);
			statement2.setInt(2, status);
			statement2.setString(3, approvalId);
			statement2.setString(4, approvalName);
			statement2.setInt(5, flag);
			statement2.setString(6, comment);
			statement2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

			statement2.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement2, null);
		}

		return true;
	}
}