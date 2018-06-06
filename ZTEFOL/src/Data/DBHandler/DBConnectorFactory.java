package Data.DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.*;
import Common.FOLLogger;

public class DBConnectorFactory {
	private static Logger logger = FOLLogger.getLogger(DBConnectorFactory.class);
	private static DBConnectorFactory instance = new DBConnectorFactory();
	private ComboPooledDataSource cpds = null;
	
	private DBConnectorFactory()
	{
		init();
	}
	
	public static DBConnectorFactory getConnectorFactory()
	{
		return instance;
	}
	
	private void init()
	{
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver" ); //loads the jdbc driver            
			cpds.setJdbcUrl("jdbc:mysql://10.112.28.32:3308/testFOL?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
			cpds.setUser("root");                                
			cpds.setPassword("netnumen");        
//			cpds.setJdbcUrl("jdbc:mysql://10.112.28.32:3308/FOL?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
//			cpds.setUser("root");                                
//			cpds.setPassword("netnumen");
			cpds.setMaxPoolSize(20);
			cpds.setMinPoolSize(5);
			cpds.setMaxStatements(100);
			cpds.setMaxIdleTime(3600);
			cpds.setIdleConnectionTestPeriod(3000);
			cpds.setAcquireIncrement(2);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}  	
	}
	
	public Connection getConnection()
	{ 
		Connection connection = null; 
		try {
			connection = cpds.getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return connection;
	}

	public void freeDB(Connection conn, Statement stmt, ResultSet rs)
	{
		try {
			if(conn != null)
			{
				conn.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
			if(rs != null)
			{
				rs.close();
			}
		} 
		catch (Exception e) {
			logger.error("free db error! " + e.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		DBConnectorFactory.getConnectorFactory().getConnection();
		System.out.println("asd");
	}
	
}
