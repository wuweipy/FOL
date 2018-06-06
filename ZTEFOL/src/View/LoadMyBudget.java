package View;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import Business.Budget.BudgetDetailInfo;
import Common.FOLLogger;
import Common.ProductMngt;
import Data.Common.Entry;
import Data.DBHandler.DBConnectorFactory;
/**
 * 我提交的预算申请：详细
 */
public class LoadMyBudget extends HttpServlet {
	private static Logger logger = FOLLogger.getLogger(LoadMyBudget.class);
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String handle = request.getParameter("handle");
		String budgetid = request.getParameter("budgetid");
		String deptname = ParameterUtil.getChineseString(request, "deptname","utf-8");		
		String userid = request.getSession().getAttribute("userID").toString();
		String getDeptid;
		String getDeptname;
		String budgetstate;
		String billTime = request.getParameter("billTime");
		String action = request.getParameter("action");
		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		try {

			Statement stmt0 = conn.createStatement();
			String sql0 = "SELECT * FROM USERINFO where NO='" + userid + "';";
			ResultSet rs0 = stmt0.executeQuery(sql0);

			rs0.next();

			getDeptid = rs0.getString("DEPTID");
			stmt0.close();

			Statement stmt1 = conn.createStatement();
			String sql1 = "SELECT * FROM DEPT WHERE id='" + getDeptid + "';";
			ResultSet rs1 = stmt1.executeQuery(sql1);

			rs1.next();

			getDeptname = rs1.getString("name");
			stmt1.close();


			Statement stmt2 = conn.createStatement();
			String sql2 = "SELECT * FROM BUDGETINFO where budgetId='"
					+ budgetid + "';";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			rs2.next();
			budgetstate = rs2.getString("status");

			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM BUDGETDETAIL where budgetId='"
					+ budgetid + "' ;";
			ResultSet rs = stmt.executeQuery(sql);
			List<String[]> getresult = new ArrayList<String[]>();

			while (rs.next()) {
				String[] onerecord = new String[6];
				onerecord[0] = rs.getString("courseName");
				onerecord[1] = rs.getString("money");
				if (rs.getString("description") == null) {
					onerecord[2] = "";
				} else {
					onerecord[2] = rs.getString("description");
				}
				if (rs.getString("instituteOpinion") == null) {
					onerecord[3] = "";
				} else {
					onerecord[3] = rs.getString("instituteOpinion");
				}
				if (rs.getString("financeOpinion") == null) {
					onerecord[4] = "";
				} else {
					onerecord[4] = rs.getString("financeOpinion");
				}
				if (rs.getString("managerOpinion") == null) {
					onerecord[5] = "";
				} else {
					onerecord[5] = rs.getString("managerOpinion");
				}
				getresult.add(onerecord);
			}
			stmt.close();


			Statement stmt3 = conn.createStatement();
			String sql3 = "SELECT * FROM BUDGETINFO where budgetId='"
					+ budgetid + "';";
			ResultSet rs3 = stmt3.executeQuery(sql3);
			rs3.next();
			String institute = rs3.getString("institute");
			if (institute == null) {
				institute = "";
			}else{
				institute = useridToUserName(institute)+institute;
			}
			String instituteTime;
			if (rs3.getString("instituteTime") != null) {
				instituteTime = rs3.getString("instituteTime").substring(0, 19);
			} else {
				instituteTime = "";
			}
			String finance = rs3.getString("finance");
			if (finance == null) {
				finance = "";
			}else{
				finance = useridToUserName(finance)+finance;
			}
			String financeTime;
			if (rs3.getString("financeTime") != null) {
				financeTime = rs3.getString("financeTime").substring(0, 19);
			} else {
				financeTime = "";
			}
			String manager = rs3.getString("manager");
			if (manager == null) {
				manager = "";
			}else{
				manager = useridToUserName(manager)+manager;
			}
			String managerTime;
			if (rs3.getString("managerTime") != null) {
				managerTime = rs3.getString("managerTime").substring(0, 19);
			} else {
				managerTime = "";
			}
			String[] times = new String[6];
			times[0] = institute;
			times[1] = instituteTime;
			times[2] = finance;
			times[3] = financeTime;
			times[4] = manager;
			times[5] = managerTime;

			request.setAttribute("getresult", getresult);
			request.setAttribute("handle", handle);
			request.setAttribute("budgetid", budgetid);
			request.setAttribute("deptname", deptname);
			request.setAttribute("budgetstate", budgetstate);
			request.setAttribute("billTime", billTime);
			request.setAttribute("times", times);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(action!=null && action.equals("submit")){
			request.setAttribute("isSucess", true);
		}
		ArrayList<Integer> subProIds = new ArrayList<Integer>();
		List<BudgetDetailInfo> buidgetList = getBudgetDInfo(budgetid,subProIds);
		request.setAttribute("budgetDetailInfos", getBudgetDInfo(budgetid,subProIds));
		request.setAttribute("subProducts", ProductMngt.getSubProducts());
		request.setAttribute("subProIds", subProIds);
		request.getRequestDispatcher("MyBudgetTo.jsp").forward(request,
				response);
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

		response.setContentType("text/html");
		doGet(request, response);
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

	public String useridToUserName(String userid) {
		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		String username = null;
		Statement stmt = null;
		ResultSet rs = null;
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
