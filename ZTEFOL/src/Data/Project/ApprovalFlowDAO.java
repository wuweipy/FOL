package Data.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class ApprovalFlowDAO implements IApprovalFlowDAO {
	private static Logger logger = FOLLogger.getLogger(ApprovalFlowDAO.class);

	public HashMap<Integer, DApprovalFlow> getAllApprovalFlows() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		HashMap<Integer, DApprovalFlow> map = new HashMap<Integer, DApprovalFlow>();
		DApprovalFlow item = null;
		if (connection == null) {
			return map;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * from APPROVALFLOW");
			while (rs.next()) {
				item = new DApprovalFlow();
				item.setId(rs.getInt("id"));
				item.setApprovalid(rs.getString("approvalid"));
				item.setApprovalmoredesc(rs.getString("approvalmoredesc"));
				item.setApprovalname(rs.getString("approvalname"));
				item.setApprovalorder(rs.getInt("approvalorder"));
				item.setFlowid(rs.getInt("flowid"));
				item.setProjectid(rs.getInt("projectid"));
				item.setLimit(rs.getInt("limit"));
				item.setSpecial(rs.getInt("special"));
				item.setSpecialno(rs.getString("specialno"));
				item.setNeedbudget(rs.getInt("needbudget"));
				map.put(item.getId(), item);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return map;
	}

	@Override
	public HashMap<Integer, String> getStatuses() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		DApprovalFlow item = null;
		if (connection == null) {
			return map;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT DISTINCT approvalstatus,approvalstatusdesc FROM APPROVALFLOW ");
			while (rs.next() && rs.getInt("approvalstatus") != 0) {
				map.put(rs.getInt("approvalstatus"), rs.getString("approvalstatusdesc"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return map;
	}

	public HashMap<Integer, String> getMyStatuses() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		DApprovalFlow item = null;
		if (connection == null) {
			return map;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT DISTINCT projectstatus,projectstatusdesc FROM APPROVALFLOW");
			while (rs.next()) {
				map.put(rs.getInt("projectstatus"), rs.getString("projectstatusdesc"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return map;
	}

	public DApprovalFlow getBySubprojectid(int subprojectid) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		DApprovalFlow item = null;
		if (connection == null) {
			return item;
		}
		Statement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM APPROVALFLOW where projectid= " + subprojectid + " order by approvalorder asc";
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				item = new DApprovalFlow();
				item.setApprovalid(rs.getString("approvalid"));
				item.setApprovalname(rs.getString("approvalname"));
				item.setApprovalmoredesc(rs.getString("approvalmoredesc"));
				item.setApprovalorder(rs.getInt("approvalorder"));
				item.setFlowid(rs.getInt("flowid"));
				item.setId(rs.getInt("id"));
				item.setProjectid(rs.getInt("projectid"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return item;
	}

	public DApprovalFlow getBySpecialno(String specialno) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		DApprovalFlow item = null;
		if (connection == null) {
			return item;
		}
		Statement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM APPROVALFLOW where specialno= '" + specialno + "' order by approvalorder asc";
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				item = new DApprovalFlow();
				item.setApprovalid(rs.getString("approvalid"));
				item.setApprovalname(rs.getString("approvalname"));
				item.setApprovalmoredesc(rs.getString("approvalmoredesc"));
				item.setApprovalorder(rs.getInt("approvalorder"));
				// item.setApprovalstatus(rs.getInt("approvalstatus"));
				// item.setApprovalstatusdesc(rs.getString("approvalstatusdesc"));
				item.setFlowid(rs.getInt("flowid"));
				item.setId(rs.getInt("id"));
				item.setProjectid(rs.getInt("projectid"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return item;
	}

	public DApprovalFlow getNextApprovalFlow(int approvalFlowId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		DApprovalFlow item = null;
		if (connection == null) {
			return item;
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "SELECT a.id,a.* FROM APPROVALFLOW a,APPROVALFLOW b  WHERE a.flowid=b.flowid AND a.approvalorder=b.approvalorder+1 AND b.id=? ";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, approvalFlowId);
			rs = statement.executeQuery();
			if (rs.next()) {
				item = new DApprovalFlow();
				item.setApprovalid(rs.getString("approvalid"));
				item.setApprovalname(rs.getString("approvalname"));
				item.setApprovalmoredesc(rs.getString("approvalmoredesc"));
				item.setApprovalorder(rs.getInt("approvalorder"));
				// item.setApprovalstatus(rs.getInt("approvalstatus"));
				// item.setApprovalstatusdesc(rs.getString("approvalstatusdesc"));
				item.setFlowid(rs.getInt("flowid"));
				item.setId(rs.getInt("id"));
				item.setProjectid(rs.getInt("projectid"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return item;
	}

}
