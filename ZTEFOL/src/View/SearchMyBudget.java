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

import Common.DeptMngt;
import Common.UserMngt;
import Data.DBHandler.DBConnectorFactory;
/**
 * 预算我提交的查询
 */
public class SearchMyBudget extends HttpServlet {

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
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String userid = request.getSession().getAttribute("userID").toString();
		String getDeptid = null;
		String getDeptname = null;
		String getusername = null;
		List<String[]> getresult = new ArrayList<String[]>();

		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM USERINFO where NO='" + userid + "';";
			ResultSet rs = stmt.executeQuery(sql);

			rs.next();

			getDeptid = rs.getString("DEPTID");
			stmt.close();

			Statement stmt1 = conn.createStatement();
			String sql1 = "SELECT * FROM DEPT WHERE id='" + getDeptid + "';";
			ResultSet rs1 = stmt1.executeQuery(sql1);

			rs1.next();

			getDeptname = rs1.getString("name");
			stmt1.close();

			Statement stmt2 = conn.createStatement();
			String sql2 = "SELECT * FROM USERINFO WHERE NO='" + userid + "';";
			ResultSet rs2 = stmt2.executeQuery(sql2);

			rs2.next();

			getusername = rs2.getString("USERNAME");
			stmt2.close();

			Statement stmt3 = conn.createStatement();
			String sql3 = "SELECT * FROM BUDGETINFO where (no='" + userid + "' or agencyNo='" + userid + "');";
			ResultSet rs3 = stmt3.executeQuery(sql3);
			while (rs3.next()) {
				String budgetId = rs3.getString("budgetId");
				
				if (budgetId.contains(year+month) || (year==null && month==null)) {
					String[] onerecord = new String[9];
					onerecord[0] = rs3.getString("budgetId");
					onerecord[7] = rs3.getString("doubleSubmit");
					onerecord[1] = rs3.getString("no");
					onerecord[8] =  rs3.getString("username");
					onerecord[2] = DeptMngt.getInstance().getDepts().get(UserMngt.getInstance().getUserInfoMap().get(onerecord[1]).getDeptId());
					if(rs3.getString("agencyNo")!=null){
						onerecord[6] = rs3.getString("agencyNo");
					}
					else
						onerecord[6] = "无";
					
					Statement stmt4 = conn.createStatement();

					String sql4 = "SELECT SUM(money) as summ FROM BUDGETDETAIL where budgetId='"
							+ onerecord[0] + "';";
					ResultSet rs4 = stmt4.executeQuery(sql4);

					rs4.next();
					onerecord[3] = rs4.getString("summ");

					onerecord[4] = rs3.getString("status");
					onerecord[5] = rs3.getString("billTime");
					getresult.add(onerecord);
					stmt1.close();
				}
			}
			request.setAttribute("getresult", getresult);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int deptId = UserMngt.getInstance().getUserInfoMap().get(userid).getDeptId();
		int institute = DeptMngt.getInstance().getDepts1().get(deptId).getInstitute();
		request.setAttribute("institute", institute);
		request.setAttribute("userInfoMap", UserMngt.getInstance().getUserInfoMap());
		request.getRequestDispatcher("MyBudget.jsp").forward(request, response);
	}

}
