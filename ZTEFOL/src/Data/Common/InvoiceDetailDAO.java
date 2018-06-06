package Data.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import Business.Claims.BClaim;
import Business.Claims.BClaimItem;
import Business.UserDetail.BUserDetail;
import Common.ApprovalFlowMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Data.DBHandler.DBConnectorFactory;
import Data.UserDetail.DUserDetail;
import Data.UserInfo.DUser;

public class InvoiceDetailDAO {
	private static Logger logger = FOLLogger.getLogger(InvoiceDetailDAO.class);

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
				bClaim.setApprovalName(ApprovalFlowMngt.approvalFlowMap.get(rs.getInt("approvalflowid")).getApprovalname());
				bClaim.setBillNo(rs.getString("billNo"));
				bClaim.setSummary(rs.getString("summary"));
				bClaim.setHasBill(rs.getBoolean("hasBill"));
				bClaim.setDirectToFinance(rs.getBoolean("directToFinance"));
				bClaim.setDeptId(rs.getInt("deptId"));
				bClaim.setDeptName(DeptMngt.getInstance().getDeptName(rs.getInt("deptId")));
				bClaim.setTotalFee(rs.getFloat("totalFee"));
				bClaim.setInvoiceType(rs.getInt("invoiceType"));
				bClaim.setSubmitDate(rs.getTimestamp("submitDate"));
				bClaim.setApprovalFlowId(rs.getInt("approvalFlowId"));
				bClaim.setStatusDesc(
						ApprovalFlowMngt.approvalFlowMap.get(rs.getInt("approvalflowid")).getApprovalmoredesc());
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
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return bClaim;
	}

	public void permit(ArrayList<String> invoices, String approvalId, int status) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		// if(connection == null)
		// {
		// return false;
		// }
		PreparedStatement statement = null;
		try {
			for (int i = 0; i < invoices.size(); i++) {
				String sql = "update CLAIMS set status=? where invoiceNo=?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, 3);
				statement.setString(2, invoices.get(i));
				statement.executeUpdate();

				sql = "insert into APPROVALINFO values (?,?,?,?,?)";
				statement = connection.prepareStatement(sql);
				statement.setString(1, invoices.get(i));
				statement.setInt(2, status);
				statement.setString(3, approvalId);
				statement.setString(4, approvalId);
				statement.setString(5, "permit");
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
	}

	public void notPermit(ArrayList<String> invoices, String approvalId, int status) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		try {
			for (int i = 0; i < invoices.size(); i++) {
				String sql = "update CLAIMS set status=? where invoiceNo=?";
				statement = connection.prepareStatement(sql);

				statement.setInt(1, 5);
				statement.setString(2, invoices.get(i));
				statement.executeUpdate();

				sql = "insert into APPROVALINFO values (?,?,?,?,?)";
				statement = connection.prepareStatement(sql);
				statement.setString(1, invoices.get(i));
				statement.setInt(2, status);
				statement.setString(3, approvalId);
				statement.setString(4, approvalId);
				statement.setString(5, "NotPermit");
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		// return false;
	}

}
