package View;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;
/**
 * 预算我审批的查询
 */
public class SearchMyApprovalBudget extends HttpServlet {
	private static Logger logger = FOLLogger.getLogger(TotalUseServlet.class);
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


		String userid = request.getSession().getAttribute("userID").toString();

		String getDeptid = null;
		String getDeptname = null;


		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();

		List getCombolist = new ArrayList<String[]>();

		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM PROJECT" + ";";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String[] mystring = new String[2];
				mystring[0] = rs.getString("projectid");
				mystring[1] = rs.getString("projectname");
				getCombolist.add(mystring);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM BUDGETINFO where institute='" + userid
					+ "' or finance='" + userid + "' or manager ='" + userid
					+ "' order by budgetId DESC;";
			ResultSet rs = stmt.executeQuery(sql);
			List<String[]> getresult = new ArrayList<String[]>();

			while (rs.next()) {
				String[] onerecord = new String[7];
				onerecord[0] = rs.getString("budgetId");
				String getid = rs.getString("no");
				onerecord[6] = getid;
				onerecord[1] =  rs.getString("username") + getid;			
				onerecord[2] = rs.getString("projectName");
				Statement stmt1 = conn.createStatement();

				String sql1 = "SELECT SUM(money) as summ FROM BUDGETDETAIL where budgetId='"
						+ onerecord[0] + "';";
				ResultSet rs1 = stmt1.executeQuery(sql1);

				rs1.next();
				onerecord[3] = rs1.getString("summ");
				onerecord[4] = rs.getString("status");
				onerecord[5] = rs.getString("billTime").substring(0, 19);
				getresult.add(onerecord);
				stmt1.close();
			}
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String projectName = ParameterUtil.getChineseString(request,"project");
			String userno = request.getParameter("userno");
			String budgetid = request.getParameter("budgetid");



			if (userno.equals("") == false) {
				int j = getresult.size();
				for (int i = 0,k=0; k < j;k++) {
					String[] ones = getresult.get(i);
					if (ones[1].indexOf(userno) == -1)
					{
						getresult.remove(i);
					}
					else
					{
						i++;
					}
				}
			}


			if (budgetid.equals("") == false) {
				int j = getresult.size();
				for (int i = 0,k=0; k < j;k++) {
					String[] ones = getresult.get(i);
					if (ones[0].indexOf(budgetid) == -1)
					{
						getresult.remove(i);
					}
					else
					{
						i++;
					}
				}
			}


			if (projectName.equals("") == false) {
				int j = getresult.size();
				for (int i = 0,k=0; k < j;k++) {
					String[] ones = getresult.get(i);

					String oneuserid = ones[6];
					String proName = ones[2];
					
					if (proName.equals(projectName) == false) 
					{
						getresult.remove(i);
					}
					else
					{
						i++;
					}
				}
			}


			if (year.equals("") == false) {
				int j = getresult.size();
				for (int i = 0,k=0; k < j;k++) {
					String[] ones = getresult.get(i);
					String budgetId1 = ones[0];
					if (budgetId1.substring(0, 4).equals(year) == false)
					{
						getresult.remove(i);
					}
					else
					{
						i++;
					}
				}
			}
			
			if (month.equals("") == false) {
				DecimalFormat ndf = new DecimalFormat("00");
				month = ndf.format(Integer.valueOf(month));
				int j = getresult.size();
				for (int i = 0,k=0; k < j;k++) {
					String[] ones = getresult.get(i);
					String budgetId1 = ones[0];
					if (budgetId1.substring(4, 6).equals(month) == false)
					{
						getresult.remove(i);
					}
					else
					{
						i++;
					}
				}
			}
			request.setAttribute("getresult", getresult);
			request.setAttribute("getCombolist", getCombolist);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());			
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("MyApprovalBudget.jsp").forward(request,
				response);
	}

	private List<String[]> getresult(List<String[]> getresult) {
		// TODO Auto-generated method stub
		List<String[]> getresult1 = new ArrayList<String[]>();
		for(int i =0;i<getresult.size();i++){			
			getresult1.add(getresult.get(i));
		}
		return getresult1;
	}

}
