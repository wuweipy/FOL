package Data.Claims;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.Common.Countersign.DCountersign;
import Data.DBHandler.DBConnectorFactory;
import Data.Project.DApprovalFlow;
import Data.UserDetail.UserDetailDao;

public class ClaimsDAO implements IClaimsDAO {

	private static Logger logger = FOLLogger.getLogger(UserDetailDao.class);

	public boolean savePayMode(int payMode, String invoiceNo) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "update CLAIMS set payType=? where " + "invoiceNo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, payMode);
			statement.setString(2, invoiceNo);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}

	public boolean saveClaim(DClaim claim) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "update CLAIMS set summary=?,accountAdjust=?,employeeLevel=?,hedgeAccount=?,totalFee=? ,hasBill=?"
					+ ",productId=?,subProductId=?,billLocation=? ,invoiceType=?,approvalId=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, claim.getSummary());
			statement.setInt(2, claim.getAccountAdjust());
			statement.setInt(3, claim.getEmployLevel());
			statement.setInt(4, claim.getHedgeAccount());
			statement.setFloat(5, claim.getTotalFee());
			statement.setBoolean(6, claim.isHasBill());
			statement.setInt(7, claim.getProductId());
			statement.setInt(8, claim.getSubProductId());
			statement.setInt(9, claim.getBillLoaction());
			statement.setInt(10, claim.getInvoiceType());
			statement.setString(11, claim.getApprovalId());
			statement.setString(12, claim.getInvoiceNo());
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}

	public boolean createClaim(DClaim claim) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "insert into CLAIMS(invoiceNo,billNo,productId,subProductId,hasBill,no,billLocation,"
					+ "directToFinance,deptId,submitDate,invoiceType,approvalId) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, claim.getInvoiceNo());
			statement.setString(2, claim.getBillNo());
			statement.setInt(3, claim.getProductId());
			statement.setInt(4, claim.getSubProductId());
			statement.setBoolean(5, claim.isHasBill());
			statement.setString(6, claim.getNo());
			statement.setInt(7, claim.getBillLoaction());
			statement.setBoolean(8, claim.isDirectToFinance());
			statement.setInt(9, claim.getDeptId());
			statement.setTimestamp(10, claim.getSubmitDate());
			statement.setInt(11, claim.getInvoiceType());
			statement.setString(12, claim.getApprovalId());

			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}

	public String getMaxInvoiceNo() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			String sql = "select max(invoiceNo) from CLAIMS";
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return null;
	}

	public DCountersign getClaim(String invoiceNO) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();

		DCountersign countersign = null;
		if (connection == null) {
			return countersign;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from CLAIMS where invoiceNo = '" + invoiceNO + "'");
			while (rs.next()) {
				countersign = new DCountersign();
				// countersign.setId(rs.getInt("id"));
				// countersign.setPayType(rs.getInt("payType"));
				countersign.setInvoiceType(rs.getInt("invoiceType"));
				countersign.setProductId(rs.getInt("productId"));
				// countersign.setBillLoaction(rs.getInt("billLocation"));
				// countersign.setStatus(rs.getInt("status"));
				// countersign.setAccountAdjust(rs.getInt("accountAdjust"));
				// countersign.setEmployLevel(rs.getInt("employeeLevel"));
				// countersign.setCurrencyType(rs.getInt("currencyType"));
				// countersign.setHedgeAccount(rs.getInt("hedgeAccount"));
				countersign.setNo(rs.getString("no"));
				// countersign.setInvoiceNo(rs.getString("invoiceNo"));
				// countersign.setBillNo(rs.getString("billNo"));
				countersign.setSummary(rs.getString("summary"));
				// countersign.setHasBill(rs.getBoolean("hasBill"));
				// countersign.setDirectToFinance(rs.getBoolean("directToFinance"));
				// countersign.setDeptId(rs.getInt("deptID"));
				countersign.setTotalFee(rs.getInt("totalFee"));
				countersign.setSubmitDate(rs.getTimestamp("submitDate"));
				countersign.setApprovalId(rs.getString("approvalId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return countersign;
	}

	public static void main(String[] args) {
		/*
		 * DClaim dClaim = new DClaim(); dClaim.setNo("10144183");
		 * dClaim.setInvoiceNo("11111111111");
		 * dClaim.setBillNo("22222222222222"); dClaim.setProductId(1);
		 * dClaim.setHasBill(true);
		 * 
		 * ClaimsDAO claimsDAO = new ClaimsDAO();
		 * System.out.println(claimsDAO.createClaim(dClaim));
		 * 
		 * dClaim.setNo("10144183"); dClaim.setInvoiceNo("11111111112");
		 * dClaim.setBillNo("22222222222222"); dClaim.setProductId(1);
		 * dClaim.setHasBill(true);
		 * System.out.println(claimsDAO.createClaim(dClaim));
		 * 
		 * System.out.println(claimsDAO.getMaxInvoiceNo());
		 */

		ClaimsDAO claimsDAO = new ClaimsDAO();
		DClaim claim = new DClaim();
		claim.setInvoiceNo("101441831375236992109");
		claim.setBillNo("123123");
		claim.setProductId(1);
		claim.setHasBill(true);
		claim.setNo("101441831375236992109");
		claim.setBillLoaction(1);
		claim.setDirectToFinance(false);
		claim.setDeptId(1);
		claim.setApprovalId("111111");
		claim.setInvoiceType(1);
		claim.setSummary("ddddd");
		claim.setAccountAdjust(200);
		claim.setEmployLevel(3);
		claim.setHedgeAccount(200);
		claim.setSubmitDate(new Timestamp(System.currentTimeMillis()));
		System.out.println(claimsDAO.createClaim(claim));
	}

	public boolean submit(String invoiceNo, DApprovalFlow flow) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "update CLAIMS set approvalflowid=?, status=?,approvalid=? where " + "invoiceNo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, flow.getId());
			statement.setInt(2, DApprovalFlow.APPROVALSTATUS_APPROVING);
			statement.setString(3, flow.getApprovalid());
			statement.setString(4, invoiceNo);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}

	}

}
