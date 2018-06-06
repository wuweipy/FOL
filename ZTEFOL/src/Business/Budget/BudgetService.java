package Business.Budget;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Data.DBHandler.DBConnectorFactory;

public class BudgetService {

	public static void queryAndSet(ArrayList<String[]> wuhanBudgetList,
			ArrayList<String[]> shanghaiBudgetList,ArrayList<String[]> salesfeelist,Set<String> sales, String dateNo) {
		// TODO Auto-generated method stub
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return;
		}
		Statement statement = null;
		ResultSet rs = null;
		ResultSet rsForSales = null; 
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("select sum(money) as money,a.budgetId as id,d.courseId as invoiceType,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=3 and a.budgetId like '%" + 
		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by d.courseId,b.deptId,e.institute");
			while (rs.next()) 
			{
				String[] myString = new String[4];
				myString[0] = rs.getString("invoiceType");
				myString[1] = rs.getString("deptid");
				myString[2] = rs.getString("money");
				myString[3] = rs.getString("id");
				if(Integer.valueOf(rs.getString("institute"))==1)
				{
					wuhanBudgetList.add(myString);
				}
				else
				{
					shanghaiBudgetList.add(myString);
				}			
		    }
			
			rsForSales = statement.executeQuery("select money,a.no as no,a.budgetId as id,d.courseId as invoiceType,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=3 and a.budgetId like '%" + 
		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId and b.deptId = 6");
			while (rsForSales.next()) 
			{
				String[] myString = new String[5];
				myString[0] = rsForSales.getString("invoiceType");
				myString[1] = rsForSales.getString("deptid");
				myString[2] = rsForSales.getString("money");
				myString[3] = rsForSales.getString("id");
				myString[4] = rsForSales.getString("no");
				salesfeelist.add(myString);	
				sales.add(myString[4]);
		    }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rsForSales);
		}
	}
	
}
