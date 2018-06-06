package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import Business.Budget.BudgetDetailInfo;
import Common.DelegateMngt;
import Common.DeptMngt;
import Common.FOLLogger;
import Common.UserMngt;
import Data.Common.Entry;
import Data.DBHandler.DBConnectorFactory;
import Jmail.EmailHander;
/**
 * 预算：我提交的修改
 */
public class Updatemybudget extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	
	private static Logger logger = FOLLogger.getLogger(Updatemybudget.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String getlistsize = request.getParameter("resultsize");
		String budgetid = request.getParameter("budgetid");
		String handle = request.getParameter("handle");
		String billTime = request.getParameter("billTime");
		int status = Integer.valueOf(request.getParameter("budgetstate"));
		int newStatus = 0;
		String userid = request.getSession().getAttribute("userID").toString();
		ArrayList<Integer> subProIds = new ArrayList<Integer>();
		getBudgetDInfo(budgetid,subProIds);
		String getDeptid;
		String getDeptname = null;
		String budgetstate = null;
		int doubleSubmit = 0;
        if(status == 5)
        {
        	doubleSubmit = 1;
        }
//		if(status != 4){
//			Connection conn = DBConnectorFactory.getConnectorFactory()
//					.getConnection();
//	
//			for (int i = 0; i < Integer.parseInt(getlistsize); i++) {
//				String courseid = request.getParameter("course" + i);
//				String getsum = request.getParameter("sum" + i);
//				String getdsc = request.getParameter("dsc" + i);
//				try {
//	
//					Statement stmt0 = conn.createStatement();
//					String sql0 = "SELECT * FROM USERINFO where NO='" + userid
//							+ "';";
//					ResultSet rs0 = stmt0.executeQuery(sql0);
//	
//					rs0.next();
//	
//					getDeptid = rs0.getString("DEPTID");
//					stmt0.close();
//	
//					Statement stmt1 = conn.createStatement();
//					String sql1 = "SELECT * FROM DEPT WHERE id='" + getDeptid
//							+ "';";
//					ResultSet rs1 = stmt1.executeQuery(sql1);
//	
//					rs1.next();
//	
//					getDeptname = rs1.getString("name");
//					stmt1.close();
//	
//	
//					Statement stmt2 = conn.createStatement();
//					String sql2 = "SELECT * FROM BUDGETINFO where budgetId='"
//							+ budgetid + "';";
//					ResultSet rs2 = stmt2.executeQuery(sql2);
//					rs2.next();
//					budgetstate = rs2.getString("status");
//	
//					Statement stmt3 = conn.createStatement();
//					String sql3 = "SELECT * FROM BUDGETINFO where budgetId='"
//							+ budgetid + "';";
//					ResultSet rs3 = stmt3.executeQuery(sql3);
//					rs3.next();
//					String institute = rs3.getString("institute");
//					if (institute == null) {
//						institute = "无";
//					} else {
//						institute = useridToUserName(institute) + institute;
//					}
//					String instituteTime;
//					if (rs3.getString("instituteTime") != null) {
//						instituteTime = rs3.getString("instituteTime").substring(0,
//								19);
//					} else {
//						instituteTime = "无";
//					}
//					String finance = rs3.getString("finance");
//					if (finance == null) {
//						finance = "无";
//					} else {
//						finance = useridToUserName(finance) + finance;
//					}
//					String financeTime;
//					if (rs3.getString("financeTime") != null) {
//						financeTime = rs3.getString("financeTime").substring(0, 19);
//					} else {
//						financeTime = "无";
//					}
//					String manager = rs3.getString("manager");
//					if (manager == null) {
//						manager = "无";
//					} else {
//						manager = useridToUserName(manager) + manager;
//					}
//					String managerTime;
//					if (rs3.getString("managerTime") != null) {
//						managerTime = rs3.getString("managerTime").substring(0, 19);
//					} else {
//						managerTime = "无";
//					}
//					String[] times = new String[6];
//					times[0] = institute;
//					times[1] = instituteTime;
//					times[2] = finance;
//					times[3] = financeTime;
//					times[4] = manager;
//					times[5] = managerTime;
//	
//					request.setAttribute("times", times);
//	
//					Statement stmt = conn.createStatement();
//					String sql = "update BUDGETDETAIL set money='" + getsum
//							+ "',description ='" + getdsc + "' where budgetId = '"
//							+ budgetid + "' and courseName = '" + courseid + "';";
//					stmt.executeUpdate(sql);
//					stmt.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			request.setAttribute("budgetid", budgetid);
//			request.setAttribute("handle", handle);
//			request.setAttribute("billTime", billTime);
//			request.setAttribute("deptname", getDeptname);
//			request.setAttribute("budgetstate", budgetstate);
//	
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			request.getRequestDispatcher("LoadMyBudget.do").forward(request,
//					response);
//		}
//		
//		else {
			int deptId = UserMngt.getInstance().getUserInfoMap().get(userid).getDeptId();

			newStatus = 2; 
		    ArrayList<String> courseName = new ArrayList<String>();
			ArrayList<String> moneys = new ArrayList<String>();
			ArrayList<String> descriptions = new ArrayList<String>();
			ArrayList<String> subMoneys = new ArrayList<String>();
			int count = Integer.parseInt(getlistsize);
			for(int i = 0; i < count; i++)
			{  
				 courseName.add(request.getParameter("course" + i));
			     if((request.getParameter("sum" + i) != null)&& !(request.getParameter("sum" + i).equals("")))
			     {
			    	 String money = request.getParameter("sum" + i);
			    	 moneys.add(money);
			     }
			     else
			     {
			    	 moneys.add("0");
			     }			     
			     if((request.getParameter("dsc" + i) != null) && !(request.getParameter("dsc" + i).equals("")))
			     {
			    	 String description1 = request.getParameter("dsc" + i);
			    	 descriptions.add(description1);  	 
			     }
			     else
			     {
			    	 descriptions.add("-");
			     }
			     String subMoney="";
			     for(int j=0;j<subProIds.size();j++)
			     {
				     if(request.getParameter("submoney" + i + j) != null)
				     {
				    	if(!(request.getParameter("submoney" + i + j).equals("")))
				    	{
				    	     subMoney = subMoney + subProIds.get(j) + "," + request.getParameter("submoney" + i + j) + ",";
				    	}
				    	else
				    	{
				    		subMoney = subMoney + subProIds.get(j) + "," + 0 + ",";
				    	}
				     }
			     }
			     if(!subMoney.equals(""))
			     {
			    	 subMoneys.add(subMoney.substring(0,subMoney.length()-1));
			     }
			     else
			     {
			    	 subMoneys.add(subMoney);
			     }
			}
			boolean isSucess = updateBudget(budgetid, count, courseName, moneys, subMoneys ,descriptions, newStatus ,doubleSubmit);
			String[] noandagency = getNoAndAgency(budgetid);
			if(isSucess && (status==5 || status == 4)){
				int nextRole = 0;
				nextRole = 4;
				//EmailHander.emailHanderBudget(budgetid, 1, noandagency[0], nextRole,noandagency[0]);				
			}
			request.setAttribute("isSucess", isSucess);
			request.getRequestDispatcher("myBudget.do").forward(request,
					response);
	}
		
	private boolean updateBudget(String budgetid, int count,
			ArrayList<String> courseName, ArrayList<String> moneys,
			ArrayList<String> subMoneys, ArrayList<String> descriptions,
			int status, int doubleSubmit) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if(connection == null)
		{
			return false;
		}
	    try 
		{		   
	    	for(int i=0;i<count;i++)
	    	{
		    	String sql1 = "update BUDGETDETAIL set money=?,subMoney=?,description=?,instituteOpinion=null," +
		    			"financeOpinion=null,managerOpinion=null where budgetId=? and courseName=?";
				statement = connection.prepareStatement(sql1);
				statement.setInt(1,Integer.valueOf(moneys.get(i)));
				statement.setString(2,subMoneys.get(i));
				statement.setString(3,descriptions.get(i));
				statement.setString(4,budgetid);
				statement.setString(5,courseName.get(i));
				statement.executeUpdate();
	    	}
	    	
	    	String sql = "update BUDGETINFO set status=?,doubleSubmit=?,institute=null,instituteTime=null," +
	    			"finance=null,financeTime=null,manager=null,managerTime=null where budgetId=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1,status);
			statement.setInt(2,doubleSubmit);			
			statement.setString(3,budgetid);
			return statement.executeUpdate()==1;		
		}
		 catch (SQLException e) 
		 {
			e.printStackTrace();
			logger.error(e.getMessage());
		 }
		 finally
		 {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		 } 
		 return false;	
	}

	private List<BudgetDetailInfo> getBudgetDInfo(String budgetId, ArrayList<Integer> subProIds) {
		// TODO Auto-generated method stub
		List<BudgetDetailInfo> budgetDetailInfos = new ArrayList<BudgetDetailInfo>();
		BudgetDetailInfo budgetDetailInfo = null;	
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return budgetDetailInfos;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BUDGETDETAIL where budgetId = '" + budgetId + "'");
			while (rs.next()) 
			{
				budgetDetailInfo = new BudgetDetailInfo();
				budgetDetailInfo.setCourseName(rs.getString("courseName"));
				budgetDetailInfo.setMoney(rs.getInt("money"));
				budgetDetailInfo.setDescription(rs.getString("description"));
				budgetDetailInfo.setInstituteOpinion(rs.getString("instituteOpinion"));
				budgetDetailInfo.setFinanceOpinion(rs.getString("financeOpinion"));
				ArrayList<Entry> submoneys = new ArrayList<Entry>();
				if(rs.getString("subMoney")!=null)
				{
					String[] subM = rs.getString("subMoney").split(",");
					int count = subM.length / 2;
					for(int i=0;i<count;i++)
					{
						Entry entry = new Entry();
						entry.setId(Integer.valueOf(subM[2*i]));
						entry.setName(subM[2*i+1]);
						submoneys.add(entry);
					}
					budgetDetailInfo.setSubmoneys(submoneys);
					if(subProIds.size()==0)
					{
						for(int i=0;i<count;i++)
						{
							subProIds.add(submoneys.get(i).getId());
						}
					}
				}
				budgetDetailInfo.setSubmoneys(submoneys);
				budgetDetailInfos.add(budgetDetailInfo);
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
		
		return budgetDetailInfos;
	}
	
	private String[] getNoAndAgency(String budgetid) {
		// TODO Auto-generated method stub
		String[] noandagency = new String[2];
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return noandagency;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BUDGETINFO where budgetId='" + budgetid + "'");
			rs.next();
			noandagency[0] = rs.getString("no");
			if(rs.getString("agencyNo")!=null)
			{
				noandagency[1] = rs.getString("agencyNo");
			}			
			else
				noandagency[1] = "-";			
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
		return noandagency;
		
	}

	private boolean updateStatus(String budgetid) {
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			PreparedStatement statement = null;
			if(connection == null)
			{
				return false;
			}
		    try 
			{		    	
		    	String sql1 = "update BUDGETINFO set status=? where budgetId=?";
				statement = connection.prepareStatement(sql1);
				statement.setString(1,"0");
				statement.setString(2,budgetid);
				return statement.executeUpdate()==1;				
			}
			 catch (SQLException e) 
			 {
				e.printStackTrace();
				logger.error(e.getMessage());
			 }
			 finally
			 {
				DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
			 } 
			 return false;		
	}

	private boolean createBudget(String budgetId, int count, String userID, ArrayList<String> courseName,
			ArrayList<String> moneys, ArrayList<String> descriptions, int status) {
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		PreparedStatement statement = null;
		if(connection == null)
		{
			return false;
		}
	    try 
		{
			for(int i = 0; i < count; i++)
			{		
			    String sql = "insert into BUDGETDETAIL(budgetId,courseName,money,description)" +
	            "values (?,?,?,?)";
				statement = connection.prepareStatement(sql);		
			    statement.setString(1, budgetId);
			    statement.setString(2,courseName.get(i));
			    statement.setInt(3, Integer.valueOf(moneys.get(i)));
			    statement.setString(4, descriptions.get(i));		     
				statement.executeUpdate();
		    }
		    String sql = "insert into BUDGETINFO(budgetId,no,billTime,status)" +
            "values (?,?,?,?)";
			statement = connection.prepareStatement(sql);		
		    statement.setString(1, budgetId);
		    statement.setString(2,userID);
		    statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		    statement.setInt(4, status);		     
			return statement.executeUpdate() == 1;
		}
		 catch (SQLException e) 
		 {
			e.printStackTrace();
			logger.error(e.getMessage());
		 }
		 finally
		 {
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		 } 
		 return false;
	}

	public static synchronized String getBudgetId()
	{
		// 读文本中数据 今天的时间
		// 和现在的时间合并
		// 修改文本数据
		// 存进去 文本和数据库
		String file = BudgetServlet.class.getResource("").toString();
//		file = file.substring(6) + "id.txt";          //windows系统
		file = "/" + file.substring(6) + "id.txt";
//		file = file.replace("/", "//");
		System.out.println(file);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String data = br.readLine();
			br.close();
			String getDatas[] = data.split(",");
			String getDate = getDatas[0];
			String getId = getDatas[1]; 

			// 取得现在日期
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			dateString = dateString.replace("-", "");
			dateString = dateString.substring(0,6);
			// System.out.println(dateString);
			if(dateString.substring(4,6)=="12"){
				dateString = (Integer.valueOf(dateString.substring(0,4)) + 1) + "01";				
			}
			else{
				dateString = (Integer.valueOf(dateString) + 1) + "" ;				
			}

			String saveDate = "";
			int saveId = 1;
			if (dateString.equals(getDate)) {
				saveId = Integer.parseInt(getId) + 1;
				DecimalFormat ndf = new DecimalFormat("000");
				String nsaveId = ndf.format(saveId);
				saveDate = dateString;
				saveDate = saveDate.replace("-", "");
				// 组合存进数据库操作
				// 将结果写入txt
				String saveResult = saveDate + "," + saveId;
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(file)));				
				writer.write(saveResult);
				writer.close();
				System.out.println(saveDate + nsaveId);
				return saveDate + nsaveId;
			} else {
				saveId = 1;
				DecimalFormat ndf = new DecimalFormat("000");
				String nsaveId = ndf.format(saveId);
				saveDate = dateString;
				saveDate = saveDate.replace("-", "");
				// 组合存进数据库操作
				// 将结果写入txt
				String saveResult = saveDate + "," + saveId;
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(file)));
				writer.write(saveResult);
				writer.close();
				System.out.println(saveDate + nsaveId);
				return saveDate + nsaveId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public String useridToUserName(String userid) {
		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		String username = null;
		Statement stmt = null;
		ResultSet rs =null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM USERINFO WHERE NO='" + userid + "';";
			rs = stmt.executeQuery(sql);

			rs.next();

			username = rs.getString("USERNAME");

		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(conn, stmt, rs);
		}
		
		return username;
	}
}
