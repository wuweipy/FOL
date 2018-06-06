package Data.Approval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import Business.Claims.BMyClaim;
import Common.ApprovalFlowMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Common.UserMngt;
import Data.Common.DDept;
import Data.Common.DeptDAO;
import Data.Common.IDeptDAO;
import Data.DBHandler.DBConnectorFactory;
import Data.Project.DApprovalFlow;

public class MyClaimDAO {

	private HashMap<Integer, String> depts = new HashMap<Integer, String>();
	private HashMap<Integer, String> types = new HashMap<Integer, String>();

	public MyClaimDAO() {
		super();
		initModelMaps();
	}

	private void initModelMaps() {
		queryAllDeptInfo();
		queryAllTypeInfo();
	}

	private void queryAllDeptInfo() {
		IDeptDAO deptDAO = new DeptDAO();
		Iterator<DDept> iterator = deptDAO.getDepts().iterator();
		while (iterator.hasNext()) {
			DDept dept = iterator.next();
			depts.put(dept.getId(), dept.getName());
		}
	}

	private void queryAllTypeInfo() {
		this.types.put(Integer.valueOf(0), "国内差旅费");
	}

	private static Logger logger = FOLLogger.getLogger(MyClaimDAO.class);

	public ArrayList<BMyClaim> getMyClaimInfos(String id, String invoiceNo, int deptID, int invoiceType, int status,
			String billNo, int payMax, int payMin, Timestamp dateFrom, Timestamp dateTo) {

		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<BMyClaim> bMyClaims = new ArrayList<BMyClaim>();
		BMyClaim bMyClaim = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		String queryStr = "";

		if (!invoiceNo.equalsIgnoreCase("")) {
			queryStr += " and invoiceNo = '" + invoiceNo + "'";
		}

		if (deptID != -1) {
			queryStr += " and productId = " + deptID + "";
		}

		if (invoiceType != -1) {
			queryStr += " and  invoiceType= " + invoiceType + "";
		}

		if (status != -1) {
			queryStr += " and  status= " + status + "";
		}

		if (!billNo.equalsIgnoreCase("")) {
			queryStr += " and billNo = '" + billNo + "'";
		}

		if ((payMax != -1) && (payMin != -1) && (payMin <= payMax)) {
			queryStr += " and  totalFee between '" + payMin + " ' and ' " + payMin + "'";
		}

		Date date = new Date(0, 0, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = format.format(date);
		Timestamp dateZero = Timestamp.valueOf(time);
		if ((!dateFrom.equals(dateZero)) && (!dateTo.equals(dateZero))) {
			queryStr += " and  submitDate between '" + dateFrom + " ' and ' " + dateTo + "'";
		}

		try {
			statement = connection.createStatement();

			rs = statement.executeQuery("SELECT * from CLAIMS where no = '" + id + "' and status!=0" + queryStr
					+ " order by invoiceNo DESC");

			for (int i = 0; rs.next(); i++) {
				bMyClaim = new BMyClaim();
				bMyClaim.setId(rs.getInt("id"));
				String rsInvoiceNo = rs.getString("invoiceNo");
				bMyClaim.setInvoiceNo(rs.getString("invoiceNo"));
				int deptId = rs.getInt("deptId");
				bMyClaim.setDeptId(deptId);
				bMyClaim.setDeptName(depts.get(deptId));
				int productId = rs.getInt("productId");
				bMyClaim.setProductId(productId);
				bMyClaim.setProductName(ProjectMngt.getProjectName(productId));
				bMyClaim.setSubmitDate(rs.getTimestamp("submitDate"));
				int payType = rs.getInt("invoiceType");
				bMyClaim.setPayType(payType);
				bMyClaim.setPayTypeStr(types.get(payType));
				int rsStatus = rs.getInt("status");
				System.out.println(rsInvoiceNo);
				bMyClaim.setStatus(rsStatus);
				int afid = rs.getInt("approvalflowid");
				if (rsStatus != DApprovalFlow.APPROVALSTATUS_APPROVING) {
					bMyClaim.setStatusStr(DApprovalFlow.getStatusMoreDesc(rsStatus));
				} else {
					bMyClaim.setStatusStr("待" + ApprovalFlowMngt.getCurrApprovalFlow(afid).getApprovalmoredesc());
				}

				String sql = "SELECT * from APPROVALINFO where invoiceNo = '" + rsInvoiceNo + "'";
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2.executeQuery(sql);
				if (rs2.next()) {
					String endDate = rs2.getString("appDate");
					bMyClaim.setEndDate(endDate);
				} else {
					bMyClaim.setEndDate("");
				}

				bMyClaim.setBillNo(rs.getString("billNo"));
				bMyClaim.setSummary(rs.getString("summary"));
				bMyClaim.setTotalFee(rs.getFloat("totalFee"));
				bMyClaim.setCheckFee(rs.getFloat("approvalAmount"));

				bMyClaims.add(bMyClaim);

			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}

		return bMyClaims;

	}

	public ArrayList<BMyClaim> getMyApprovalInfos(String id, String invoiceNo, int deptID, int invoiceType, int status,
			int payMax, int payMin, String dateFrom, String dateTo, int approvalFlag, String employeeName) {

		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<BMyClaim> bMyClaims = new ArrayList<BMyClaim>();
		BMyClaim bMyClaim = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		String queryStr = "";

		if (!invoiceNo.equalsIgnoreCase("")) {
			queryStr += " and a.invoiceNo = '" + invoiceNo + "'";
		}

		if (deptID != -1) {
			queryStr += " and a.deptId = '" + deptID + "'";
		}

		if (invoiceType != -1) {
			queryStr += " and  a.invoiceType= '" + invoiceType + "'";
		}

		if (status != -1) {
			queryStr += " and  a.status= '" + status + "'";
		}

		if ((payMax != -1) && (payMin != -1) && (payMin <= payMax)) {
			queryStr += " and  a.totalFee between '" + payMin + " ' and ' " + payMin + "'";
		}

		if ((!dateFrom.equals("")) && (!dateTo.equals(""))) {
			queryStr += " and  a.submitDate between '" + dateFrom + " ' and ' " + dateTo + "'";
		}

		if (!employeeName.equalsIgnoreCase("")) {
			queryStr += " and  a.no in ( select c.NO from USERINFO c where c.USERNAME ='" + employeeName + " ') ";
		}

		try {
			statement = connection.createStatement();
			if (approvalFlag != -1) {
				rs = statement.executeQuery("SELECT * from CLAIMS a, APPROVALINFO b where b.approvalId = '" + id
						+ "' and a.invoiceNo=b.invoiceNo and b.appState =' " + approvalFlag + "'" + queryStr
						+ " ORDER BY a.invoiceNo DESC");
			} else {
				rs = statement.executeQuery("SELECT * from CLAIMS a, APPROVALINFO b where b.approvalId = '" + id
						+ "' and a.invoiceNo=b.invoiceNo" + queryStr + " ORDER BY a.invoiceNo DESC");
			}

			while(rs.next()) {
				bMyClaim = new BMyClaim();
				bMyClaim.setId(rs.getInt("id"));
				String rsInvoiceNo = rs.getString("invoiceNo");
				bMyClaim.setInvoiceNo(rs.getString("invoiceNo"));
				int deptId = rs.getInt("deptId");
				bMyClaim.setDeptId(deptId);
				bMyClaim.setDeptName(depts.get(deptId));
				int productId = rs.getInt("productId");
				bMyClaim.setProductId(productId);
				bMyClaim.setProductName(ProjectMngt.getProjectName(productId));
				bMyClaim.setSubmitDate(rs.getTimestamp("submitDate"));
				int payType = rs.getInt("invoiceType");
				bMyClaim.setPayType(payType);
				bMyClaim.setPayTypeStr(types.get(payType));
				int rsStatus = rs.getInt("status");
				bMyClaim.setStatus(rsStatus);
				int afid = rs.getInt("approvalflowid");
				if (rsStatus != DApprovalFlow.APPROVALSTATUS_APPROVING) {
					bMyClaim.setStatusStr(DApprovalFlow.getStatusMoreDesc(rsStatus));
				} else {
					bMyClaim.setStatusStr("待" + ApprovalFlowMngt.getCurrApprovalFlow(afid).getApprovalmoredesc());
				}

				String sql = "SELECT * from APPROVALINFO where invoiceNo = '" + rsInvoiceNo + "'";
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2.executeQuery(sql);
				if (rs2.next()) {
					String endDate = rs2.getString("appDate");
					bMyClaim.setEndDate(endDate);
				} else {
					bMyClaim.setEndDate("");
				}

				String no = rs.getString("no");
				sql = "SELECT * from USERINFO where no = '" + no + "'";
				Statement statement3 = connection.createStatement();
				ResultSet rs3 = statement3.executeQuery(sql);
				if (rs3.next()) {
					String employName = rs3.getString("USERNAME");
					bMyClaim.setName(employName);
				} else {
					bMyClaim.setName("");
				}

				bMyClaim.setBillNo(rs.getString("billNo"));
				bMyClaim.setSummary(rs.getString("summary"));
				bMyClaim.setTotalFee(rs.getFloat("totalFee"));
				bMyClaim.setCheckFee(rs.getFloat("approvalAmount"));

				bMyClaims.add(bMyClaim);

			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}

		return bMyClaims;

	}

	public ArrayList<BMyClaim> getMyCancelInfos(String id, String invoiceNo, int invoiceType, Timestamp dateFrom,
			Timestamp dateTo, String employeeName) {

		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<BMyClaim> bMyClaims = new ArrayList<BMyClaim>();
		BMyClaim bMyClaim = null;
		if (connection == null) {
			return null;
		}
		Statement statement = null;
		ResultSet rs = null;

		String queryStr = "";

		if (!invoiceNo.equalsIgnoreCase("")) {
			queryStr += " and a.invoiceNo = '" + invoiceNo + "'";
		}

		if (invoiceType != -1) {
			queryStr += " and  a.payType= '" + invoiceType + "'";
		}

		Date date = new Date(0, 0, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = format.format(date);
		Timestamp dateZero = Timestamp.valueOf(time);
		if ((!dateFrom.equals(dateZero)) && (!dateTo.equals(dateZero))) {
			queryStr += " and  a.submitDate between '" + dateFrom + " ' and ' " + dateTo + "'";
		}

		if (!employeeName.equalsIgnoreCase("")) {
			queryStr += " and  a.no in ( select b.no from USERINFO b where b.username ='" + employeeName + " ') ";
		}

		try {
			statement = connection.createStatement();
			int j = UserMngt.getInstance().getUserInfoMap().get(id).getRoleId();
			int deptID = UserMngt.getInstance().getUserInfoMap().get(id).getDeptId();
			int institute = DeptMngt.getInstance().getDepts1().get(deptID).getInstitute();
			// if (institute == 1) {
			// if (j == 2) {
			// rs = statement.executeQuery(
			// "SELECT * from CLAIMS a where a.no = '" + id + "' and
			// a.status='2' " + queryStr);
			// } else if (j == 3) {
			// rs = statement.executeQuery(
			// "SELECT * from CLAIMS a where a.no = '" + id + "' and
			// a.status='3' " + queryStr);
			// } else {
			rs = statement
					.executeQuery("SELECT * from CLAIMS a where a.no = '" + id + "' and a.status='1' " + queryStr);
			// }
			// } else {
			// if (j == 5) {
			// rs = statement.executeQuery(
			// "SELECT * from CLAIMS a where a.no = '" + id + "' and
			// a.status='3' " + queryStr);
			// } else if (j == 1) {
			// rs = statement.executeQuery(
			// "SELECT * from CLAIMS a where a.no = '" + id + "' and
			// a.status='1' " + queryStr);
			// } else {
			// rs = statement.executeQuery(
			// "SELECT * from CLAIMS a where a.no = '" + id + "' and
			// a.status='7' " + queryStr);
			// }
			// }

			for (int i = 0; rs.next(); i++) {
				bMyClaim = new BMyClaim();
				bMyClaim.setId(rs.getInt("id"));
				String rsInvoiceNo = rs.getString("invoiceNo");
				bMyClaim.setInvoiceNo(rs.getString("invoiceNo"));
				int deptId = rs.getInt("deptId");
				bMyClaim.setDeptId(deptId);
				bMyClaim.setDeptName(depts.get(deptId));
				int productId = rs.getInt("productId");
				bMyClaim.setProductId(productId);
				bMyClaim.setProductName(ProjectMngt.getProjectName(productId));
				bMyClaim.setSubmitDate(rs.getTimestamp("submitDate"));
				int payType = rs.getInt("invoiceType");
				bMyClaim.setPayType(payType);
				bMyClaim.setPayTypeStr(types.get(payType));
				int rsStatus = rs.getInt("status");
				bMyClaim.setStatus(rsStatus);
				int afid = rs.getInt("approvalflowid");
				if (afid == -1) {
					bMyClaim.setStatusStr(DApprovalFlow.getStatusMoreDesc(rsStatus));
				} else {
					bMyClaim.setStatusStr(ApprovalFlowMngt.getCurrApprovalFlow(afid).getApprovalmoredesc());
				}

				String sql = "SELECT * from APPROVALINFO where invoiceNo = '" + rsInvoiceNo + "'";
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2.executeQuery(sql);
				if (rs2.next()) {
					String endDate = rs2.getString("appDate");
					bMyClaim.setEndDate(endDate);
				} else {
					bMyClaim.setEndDate("");
				}

				String no = rs.getString("no");
				sql = "SELECT * from USERINFO where no = '" + no + "'";
				Statement statement3 = connection.createStatement();
				ResultSet rs3 = statement3.executeQuery(sql);
				if (rs3.next()) {
					String employName = rs3.getString("USERNAME");
					bMyClaim.setName(employName);
				} else {
					bMyClaim.setName("");
				}

				bMyClaim.setBillNo(rs.getString("billNo"));
				bMyClaim.setSummary(rs.getString("summary"));
				bMyClaim.setTotalFee(rs.getFloat("totalFee"));

				bMyClaims.add(bMyClaim);

			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}

		return bMyClaims;

	}

	public boolean toCancelClaim(String invoiceNo) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "update CLAIMS set status=?,approvalflowid=?,approvalid=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, DApprovalFlow.APPROVALSTATUS_CANCEL);
			statement.setInt(2, -1);
			statement.setString(3, "-1");
			statement.setString(4, invoiceNo);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}

		return false;
	}

}
