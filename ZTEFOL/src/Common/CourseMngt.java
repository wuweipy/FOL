package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.apache.log4j.Logger;
import Data.DBHandler.DBConnectorFactory;

public class CourseMngt {
	private static Logger logger = FOLLogger.getLogger(CourseMngt.class);
	
	private static CourseMngt courseMngt = new CourseMngt();
	
	private HashMap<Integer, String> courseMap = new HashMap<Integer, String>();	
	
	private CourseMngt()
	{
		init();
	}

	private void init() 
	{
		courseMap = getCourse();
	}
	
	public static CourseMngt getInstance()
	{
		return courseMngt;
	}

	public HashMap<Integer, String> getCourseMap() 
	{
		return courseMap;
	}
	
    private HashMap<Integer, String> getCourse() {
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return courseMap;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from COURSE");
			while (rs.next()) 
			{
				courseMap.put(Integer.valueOf(rs.getString("courseId")), rs.getString("courseName"));
		    }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
		return courseMap;		
	}

	public boolean addCourse(int id, String name) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into COURSE values (?,?)";
			statement = connection.prepareStatement(sql);
		    statement.setInt(1, id);
		    statement.setString(2,name);
			return statement.executeUpdate() == 1;	 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		} 
		return false;
	}

	public void refresh() {
		// TODO Auto-generated method stub	
		init();
	}

	public boolean deleteCourse(int id) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "delete from COURSE where courseid = " + id + "";
			statement = connection.prepareStatement(sql);
			return statement.executeUpdate() == 1;	 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		}
		return false;
	}
}
