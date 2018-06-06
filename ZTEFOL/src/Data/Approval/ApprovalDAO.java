package Data.Approval;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import Common.ApprovalFlowMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Common.UserMngt;
import Data.Claims.DClaim;
import Data.DBHandler.DBConnectorFactory;
import Data.Project.DApprovalFlow;

public class ApprovalDAO implements IApprovalDAO {

	private static Logger logger = FOLLogger.getLogger(ApprovalDAO.class);

	public ArrayList<DClaim> getClaimDetail(int status, String approvalId, String invoiceNo, int invoiceType,
			int deptId, String employeeName, int productId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<DClaim> dClaimDetail = new ArrayList<DClaim>();
		DClaim dClaim = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		String queryStr = "";
		if (!invoiceNo.equalsIgnoreCase("-1")) {
			queryStr += " and a.invoiceNo = '" + invoiceNo + "'";
		}

		if (invoiceType != -1) {
			queryStr += " and a.invoiceType = '" + invoiceType + "'";
		}

		if (deptId != -1) {
			queryStr += " and a.deptId = '" + deptId + "'";
		}

		if (!employeeName.equalsIgnoreCase("-1")) {
			queryStr += " and b.USERNAME = '" + employeeName + "'";
		}

		if (productId != -1) {
			queryStr += " and a.productId = '" + productId + "'";
		}

		if (!approvalId.equalsIgnoreCase("-1")) {
			queryStr += " and a.approvalId = '" + approvalId + "'";
		}

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(
					"SELECT * from CLAIMS a, USERINFO b,APPROVALFLOW af where a.no = b.NO and a.approvalflowid=af.id and a.status = "
							+ status + queryStr +" order by invoiceNo DESC");

			while (rs.next()) {
				dClaim = new DClaim();
				dClaim.setId(rs.getInt("id"));
				dClaim.setInvoiceNo(rs.getString("invoiceNo"));
				dClaim.setInvoiceType(rs.getInt("invoiceType"));
				dClaim.setDeptId(rs.getInt("deptId"));
				dClaim.setDeptName(DeptMngt.getInstance().getDeptName(dClaim.getDeptId()));
				dClaim.setBillNo(rs.getString("billNo"));
				dClaim.setDirectToFinance(rs.getBoolean("directToFinance"));
				dClaim.setBillLoaction(rs.getInt("billLocation"));
				dClaim.setProductId(rs.getInt("productId"));
				dClaim.setProductName(ProjectMngt.getProjectName(dClaim.getProductId()));
				dClaim.setProductName(ProjectMngt.getProjectName(dClaim.getProductId()));
				dClaim.setHasBill(rs.getBoolean("hasBill"));
				dClaim.setNo(rs.getString("no"));
				dClaim.setStatus(rs.getInt("status"));
				if (dClaim.getStatus() == DApprovalFlow.APPROVALSTATUS_APPROVING) {
					dClaim.setStatusDesc("´ý"
							+ ApprovalFlowMngt.approvalFlowMap.get(rs.getInt("approvalflowid")).getApprovalmoredesc());
				} else {
					dClaim.setStatusDesc(DApprovalFlow.statuses.get(dClaim.getStatus()));
				}
				dClaim.setSummary(rs.getString("summary"));
				dClaim.setAccountAdjust(rs.getInt("accountAdjust"));
				dClaim.setEmployLevel(rs.getInt("employeeLevel"));
				dClaim.setCurrencyType(rs.getInt("currencyType"));
				dClaim.setHedgeAccount(rs.getInt("hedgeAccount"));
				dClaim.setPayType(rs.getInt("payType"));
				dClaim.setApprovalId(rs.getString("approvalId"));
				dClaim.setApprovalname(
						ApprovalFlowMngt.approvalFlowMap.get(rs.getInt("approvalflowid")).getApprovalname());
				dClaim.setTotalFee(rs.getFloat("totalFee"));
				dClaim.setSubmitDate(rs.getTimestamp("submitDate"));
				dClaim.setApprovalFlowId(rs.getInt("approvalflowid"));
				dClaimDetail.add(dClaim);

			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return dClaimDetail;
	}

	public boolean approval(String invoiceNo, int flag, int status, int nextStatus, String approvalId,
			String approvalName, String appDate, String nextApprovalId, float totalFee, String budgetId,
			int approvalflowid, float appAmount,String comment) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		if (flag == 0) {
			nextApprovalId = "-1";
			nextStatus = DApprovalFlow.APPROVALSTATUS_RETURN;
			approvalflowid = -1;
		}

		try {
			String sql = null;
			if (flag == 0 || UserMngt.getInstance().getUserInfoMap().get(approvalId).getRoleId() != 4) {
				sql = "update CLAIMS set status=?, approvalId=?,budgetId=?,approvalflowid=? where invoiceNo=?";
			} else {
				sql = "update CLAIMS set status=?, approvalId=?,budgetId=?,approvalflowid=?,approvalamount=" + appAmount
						+ " where invoiceNo=?";
			}
			statement = connection.prepareStatement(sql);
			statement.setInt(1, nextStatus);
			statement.setString(2, nextApprovalId);
			statement.setString(3, budgetId);
			statement.setInt(4, approvalflowid);
			statement.setString(5, invoiceNo);

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

			return statement2.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement2, null);
		}

		return false;
	}

	public boolean approvalone(String invoiceNo, int flag, int status, int nextStatus, String approvalId,
			String approvalName, Date appDate, String nextApprovalId, String comment, float totalFee, String budgetId,
			int approvalflowid) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		boolean result = true;
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;

		try {
			connection.setAutoCommit(false);
			String sql = "update CLAIMS set status=?, approvalId=?, budgetId=?,approvalflowid=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, nextStatus);
			statement.setString(2, nextApprovalId);
			statement.setString(3, budgetId);
			statement.setInt(4, approvalflowid);
			statement.setString(5, invoiceNo);

			result = result && statement.executeUpdate() == 1;

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
			result = result && statement2.executeUpdate() == 1;
			connection.commit();
			if (result == false) {
				connection.rollback();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("error to set auto commit true :" + e.getMessage());
				e.printStackTrace();
			}
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement2, null);
		}
		return result;
	}

	public static void main(String[] args) {
		ApprovalDAO claimsDAO = new ApprovalDAO();
		ArrayList<DClaim> detail = claimsDAO.getClaimDetail(1, "10144183", "-1", -1, -1, "-1", -1);
		System.out.println(detail.size());
		System.out.println(detail.isEmpty());

		if (!detail.isEmpty()) {
			for (int index = 0; index < detail.size(); index++) {
				System.out.println(index + "  " + detail.get(index).getInvoiceNo());
				System.out.println(index + "  " + detail.get(index).getInvoiceType());
				System.out.println(index + "  " + detail.get(index).getDeptId());
				System.out.println(index + "  " + detail.get(index).getStatus());
				System.out.println(index + "  " + detail.get(index).getSummary());
				System.out.println(index + "  " + detail.get(index).getNo());
				System.out.println(index + "  " + detail.get(index).getApprovalId());
				System.out.println(index + "  " + detail.get(index).getTotalFee());
				System.out.println(index + "  " + (detail.get(index).getSubmitDate().getYear() + 1900) + "-"
						+ (detail.get(index).getSubmitDate().getMonth() + 1) + "-"
						+ detail.get(index).getSubmitDate().getDate());
			}
		}
	}
}
