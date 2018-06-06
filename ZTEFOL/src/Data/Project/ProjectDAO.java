package Data.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Common.Product;
import Data.DBHandler.DBConnectorFactory;

public class ProjectDAO implements IProjectDAO {

	private static Logger logger = FOLLogger.getLogger(ProjectDAO.class);

	public ArrayList<DProject> getProjectList() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		ArrayList<DProject> prudList = new ArrayList<DProject>();
		DProject project = null;
		if (connection == null) {
			return prudList;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * FROM PROJECT");
			while (rs.next()) {
				project = new DProject(0,"","");
				project.setProjectid(rs.getInt("projectid"));
				project.setProjectname(rs.getString("projectname"));
				project.setOwnerno(rs.getString("ownerno"));
				project.setFlag(rs.getInt("flag"));
				prudList.add(project);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return prudList;
	}

	public HashMap<Integer, String> getProjectMap() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		if (connection == null) {
			return map;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT DISTINCT projectid,projectname FROM PROJECT");
			while (rs.next()) {
				map.put(rs.getInt("projectid"), rs.getString("projectname"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return map;
	}

	public HashMap<String, String> getSubProjectMap() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		HashMap<String, String> map = new HashMap<String, String>();
		if (connection == null) {
			return map;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(
					"SELECT CONCAT(item,'__@__',id) AS subprojectid,subItem FROM SUBPRODUCT WHERE isDelete = 0");
			while (rs.next()) {
				map.put(rs.getString("subprojectid"), rs.getString("subItem"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return map;
	}

	public HashMap<Integer, String> getSubProjectMap2() {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		if (connection == null) {
			return map;
		}
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT id,subItem FROM SUBPRODUCT ");
			while (rs.next()) {
				map.put(rs.getInt("id"), rs.getString("subItem"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return map;
	}

	public static void main(String[] args) {
	}

	@Override
	/*项目经理只有一个大项目*/
	public DProject getProjectByOwner(String no) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		DProject project = new DProject(0, "", "");
		if (connection == null) {
			return null;
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM PROJECT where ownerno=? and flag=1";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, no);
			rs = statement.executeQuery();
			if (rs.next()) {
				project.setProjectid(rs.getInt("projectid"));
				project.setProjectname(rs.getString("projectname"));
				project.setOwnerno(rs.getString("ownerno"));
				project.setFlag(rs.getInt("flag"));
			}
			else
			{
				project = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return project;
	}
	@Override
//	public HashMap<Integer, DProject> getSubProjectMapByOwner(String no) {
//		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
//		HashMap<Integer, DProject> map = new HashMap<Integer, DProject>();
//		if (connection == null) {
//			return map;
//		}
//		PreparedStatement statement = null;
//		ResultSet rs = null;
//		String sql = "SELECT * FROM PROJECT where ownerno=? and flag=1";
//		try {
//			statement = connection.prepareStatement(sql);
//			statement.setString(1, no);
//			rs = statement.executeQuery();
//			while (rs.next()) {
//				DProject p = new DProject();
//				p.setProjectid(rs.getInt("projectid"));
//				p.setProjectname(rs.getString("projectname"));
//				p.setSubprojectid(rs.getInt("subprojectid"));
//				p.setSubprojectname(rs.getString("subprojectname"));
//				map.put(rs.getInt("subprojectid"), p);
//			}
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		} finally {
//			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
//		}
//		return map;
//	}

	public boolean deleteProject(int projectId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "DELETE FROM PROJECT WHERE projectid = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, projectId);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}
	
	public boolean addSubProject(int productId, String projectname, String subProjectName, String userId) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "insert into SUBPRODUCT (item,subItem,isDelete) values (?,?,?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, productId);
			statement.setString(2, subProjectName);
			statement.setInt(3, 0);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}

	public boolean deleteSubProject(int subProjectId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "update SUBPRODUCT set isDelete=1 where id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, subProjectId);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}
	
	public boolean modifySubProject(int subProjectId, String subName) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "update SUBPRODUCT set subItem=? where id=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, subName);
			statement.setInt(2, subProjectId);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}

	public  DProject getSubProjectById(int subProjectId) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return null;
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			String sql = "select * from PROJECT where subprojectid=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, subProjectId);
			rs = statement.executeQuery();
			while (rs.next()) {
				DProject project = new DProject(0,"","");
				project.setProjectid(rs.getInt("projectid"));
				project.setProjectname(rs.getString("projectname"));
				project.setOwnerno(rs.getString("ownerno"));
				project.setFlag(rs.getInt("flag"));
				return project;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.print(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return null;
	}

	@Override
	public boolean addProject(DProject project) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if (connection == null) {
			return false;
		}
		PreparedStatement statement = null;
		try {
			String sql = "insert into PROJECT (projectid,projectname,ownerno,flag) values (?,?,?,?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, project.getProjectid());
			statement.setString(2, project.getProjectname());
			statement.setString(3, project.getOwnerno());
			statement.setInt(4, 1);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}

}
