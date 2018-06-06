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

import Data.DBHandler.DBConnectorFactory;
/**
 * ‘§À„Œ“…Û≈˙µƒ
 */
public class MyApprovalBudget extends HttpServlet {

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

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");


		String userid = request.getSession().getAttribute("userID").toString();
		
		String getDeptid = null;
		String getDeptname = null;


		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		try {
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

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
		
		String getid = "";
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
				getid = rs.getString("no");
				
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
			request.setAttribute("getresult", getresult);
			request.setAttribute("getCombolist", getCombolist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(getid);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("MyApprovalBudget.jsp").forward(request,response);
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

		doGet(request, response);

	}

}
