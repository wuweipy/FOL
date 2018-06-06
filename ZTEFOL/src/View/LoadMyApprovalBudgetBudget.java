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
 * Œ“…Û≈˙µƒ‘§À„…Í«Î£∫œÍœ∏
 */
public class LoadMyApprovalBudgetBudget extends HttpServlet {
	private static Logger logger = FOLLogger.getLogger(LoadMyApprovalBudgetBudget.class);
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

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
		String auserid = request.getParameter("getid");
		String getDeptid;
		String projectName = new String();
		String budgetstate;
		String billTime = request.getParameter("billTime");

		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		try {
			
			Statement stmt2 = conn.createStatement();
			String sql2 = "SELECT * FROM BUDGETINFO where budgetId='"
					+ budgetid + "';";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			rs2.next();
			budgetstate = rs2.getString("status");
			projectName = rs2.getString("projectName");
			
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM BUDGETDETAIL where budgetId='"
					+ budgetid + "';";
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


			String[] times = new String[6];
			Statement stmt3 = conn.createStatement();
			String sql3 = "SELECT * FROM BUDGETINFO where budgetId='"
					+ budgetid + "';";
			ResultSet rs3 = stmt3.executeQuery(sql3);
			rs3.next();

			String institute = rs3.getString("institute");
			String instituteTime;

			if (institute != null) {
				institute = useridTouserName(institute) + institute;
				instituteTime = rs3.getString("instituteTime").substring(0, 19);
			} else {
				institute = "";
				instituteTime = "";
			}

			String finance = rs3.getString("finance");
			String financeTime;
			if (finance != null) {
				finance = useridTouserName(finance) + finance;
				financeTime = rs3.getString("financeTime").substring(0, 19);
			} else {
				finance = "";
				financeTime = "";
			}

			String manager = rs3.getString("manager");
			String managerTime;
			if (manager != null) {
				manager = useridTouserName(manager) + manager;
				managerTime = rs3.getString("managerTime").substring(0, 19);
			} else {
				manager = "";
				managerTime = "";
			}

			times[0] = institute;
			times[1] = instituteTime;
			times[2] = finance;
			times[3] = financeTime;
			times[4] = manager;
			times[5] = managerTime;

			request.setAttribute("auserid", useridTouserName(auserid) + auserid);
			request.setAttribute("getresult", getresult);
			request.setAttribute("handle", handle);
			request.setAttribute("budgetid", budgetid);
			request.setAttribute("projectname", projectName);
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
		ArrayList<Integer> subProIds = new ArrayList<Integer>();
		request.setAttribute("budgetDetailInfos", getBudgetDInfo(budgetid,subProIds));
		request.setAttribute("subProducts", ProductMngt.getSubProducts());
		request.setAttribute("subProIds", subProIds);
		request.getRequestDispatcher("MyApprovalBudgetTo.jsp").forward(request,
				response);
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

	public String courseidTocourseName(String courseid) {
		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		String coursename = null;
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM COURSE where courseName='" + courseid
					+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			coursename = rs.getString("courseName");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return coursename;
	}

	public String useridTouserName(String userid) {
		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		String username = null;
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM USERINFO where NO='" + userid + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			username = rs.getString("USERNAME");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return username;
	}
}
