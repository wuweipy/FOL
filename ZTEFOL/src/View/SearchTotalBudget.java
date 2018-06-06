package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.DeptMngt;
import Data.DBHandler.DBConnectorFactory;
/**
 * 预算总表查询
 */
public class SearchTotalBudget extends HttpServlet {

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
		HashMap<Integer,Integer> getTotalBudgetFee = new HashMap<Integer, Integer>(); //heji
		String year = request.getParameter("year");
		String month = request.getParameter("month").replaceAll("^(0+)", "");
		Calendar now = Calendar.getInstance();
		ArrayList<Integer> dept1 = new ArrayList<Integer>();   //武汉科室
		ArrayList<Integer> dept2 = new ArrayList<Integer>();   //上海科室
		if(!month.equals("")){
			DecimalFormat ndf = new DecimalFormat("00");
			month = ndf.format(Integer.valueOf(month));
		}
		else{
			month = (now.get(Calendar.MONTH) + 1) + "";
			DecimalFormat ndf = new DecimalFormat("00");
			month = ndf.format(Integer.valueOf(month));
		}
		
		if(year.equals("")){
			year = now.get(Calendar.YEAR) + "";
		}

		Connection conn = DBConnectorFactory.getConnectorFactory()
				.getConnection();

		ArrayList<String[]> getprimarydatelist = new ArrayList<String[]>();

		ArrayList<String> getdeptonelist = new ArrayList<String>();

		ArrayList<String> getdepttwolist = new ArrayList<String>();


		ArrayList<String> getcourselist = new ArrayList<String>();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "select distinct courseName from COURSE";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String coursename = rs.getString("courseName");
				getcourselist.add(coursename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(conn, stmt, null);
		} 
        String like = year + month ;
		try {
			conn = DBConnectorFactory.getConnectorFactory()
			.getConnection();
			stmt = conn.createStatement();
			String sql = "select distinct name from BUDGETINFO,USERDETAIL,DEPT,BUDGETDETAIL where BUDGETINFO.no=USERDETAIL.no and USERDETAIL.DEPTID = DEPT.id and BUDGETINFO.budgetId = BUDGETDETAIL.budgetId and DEPT.institute=1 and BUDGETINFO.status=5 and BUDGETINFO.budgetId like '%" + like + "%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String deptname = rs.getString("name");
				getdeptonelist.add(deptname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(conn, stmt, null);
		} 

		try {
			conn = DBConnectorFactory.getConnectorFactory()
			.getConnection();
			stmt = conn.createStatement();
			String sql = "select distinct name from BUDGETINFO,USERDETAIL,DEPT,BUDGETDETAIL where BUDGETINFO.no=USERDETAIL.no and USERDETAIL.DEPTID = DEPT.id and BUDGETINFO.budgetId = BUDGETDETAIL.budgetId and DEPT.institute=2 and BUDGETINFO.status=5 and BUDGETINFO.budgetId like '%" + like + "%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String deptname = rs.getString("name");
				getdepttwolist.add(deptname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(conn, stmt, null);
		} 

		try {
			conn = DBConnectorFactory.getConnectorFactory()
			.getConnection();
			stmt = conn.createStatement();
			String sql = "select BUDGETDETAIL.budgetId,billTime,money,courseName,name as deptName,DEPT.institute from BUDGETINFO,USERDETAIL,DEPT,BUDGETDETAIL where BUDGETINFO.no=USERDETAIL.no and USERDETAIL.DEPTID = DEPT.id and BUDGETINFO.budgetId = BUDGETDETAIL.budgetId and BUDGETINFO.status=5;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String[] mystring = new String[6];

				mystring[0] = rs.getString("budgetId");

				mystring[1] = rs.getString("billTime");

				mystring[2] = rs.getString("money");

				mystring[3] = rs.getString("courseName");

				mystring[4] = rs.getString("deptName");

				mystring[5] = rs.getString("institute");


				String getdate = mystring[0].substring(0,6);
				if (getdate.equals(year+month)) {
					getprimarydatelist.add(mystring);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(conn, stmt, null);
		} 

		ArrayList<String[]> sumlist = new ArrayList<String[]>();

		for (int i = 0; i < getcourselist.size(); i++) {

			String coursename = getcourselist.get(i);
			String[] sums = new String[4];
			int sumone = 0;
			int sumtwo = 0;
			List<String> sumonebiaoti = new ArrayList<String>();
			for (int j = 0; j < getprimarydatelist.size(); j++) {

				String[] ones = getprimarydatelist.get(j);
				if (ones[3].equals(coursename) && ones[5].equals("1")) {
					sumone = sumone + Integer.parseInt(ones[2]);
				}
				if (ones[3].equals(coursename) && ones[5].equals("2")) {
					sumtwo = sumtwo + Integer.parseInt(ones[2]);
				}
			}
			sums[0] = coursename;
			sums[1] = sumone + "";
			sums[2] = sumtwo + "";
			sums[3] = sumone + sumtwo + "";
			sumlist.add(sums);
//			System.out.println(coursename + " " + sumone + " " + sumtwo + " "
//					+ sums[3]);
		}
		request.setAttribute("sumlist", sumlist);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("getdeptonelist", getdeptonelist);
		request.setAttribute("getdepttwolist", getdepttwolist);
		request.setAttribute("getprimarydatelist", getprimarydatelist);
		request.setAttribute("role", 4);
		getTotalBudgetFee(getTotalBudgetFee,year + month,dept1,dept2);
		request.setAttribute("dept1", dept1);
		request.setAttribute("dept2", dept2);
		request.setAttribute("getTotalBudgetFee", getTotalBudgetFee);
		request.setAttribute("depts", DeptMngt.getInstance().getDepts());	
		request.getRequestDispatcher("totalBudget.jsp").forward(request,
				response);
	}
	
	private void getTotalBudgetFee(HashMap<Integer, Integer> getTotalBudgetFee, String dateNo, ArrayList<Integer> dept1, ArrayList<Integer> dept2) {
		// TODO Auto-generated method stub
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		if(connection == null)
		{
			return;
		}
		Statement statement = null;
		ResultSet rs = null;
		int total1 = 0;    //wuhan
		int total2 = 0;    //shanghai
		int total3 = 0;    //zonghe
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("select sum(money) as money,b.deptId,e.institute from BUDGETINFO a,USERINFO b,BUDGETDETAIL c,COURSE d,DEPT e where e.id=b.deptid and d.courseName=c.courseName and a.status=5 and a.budgetId like '%" + 
		    		dateNo + "%' and a.no=b.no and a.budgetId=c.budgetId group by b.deptId,e.institute");
			while (rs.next()) 
			{
				String[] myString = new String[3];
				myString[1] = rs.getString("deptid");
				myString[2] = rs.getString("money");												
				if(Integer.valueOf(rs.getString("institute"))==1)
				{
					total1 += Integer.valueOf(myString[2]);
					dept1.add(Integer.valueOf(myString[1]));
				}
				else
				{
					total2 += Integer.valueOf(myString[2]);
					dept2.add(Integer.valueOf(myString[1]));
				}			
				getTotalBudgetFee.put(Integer.valueOf(myString[1]), Integer.valueOf(myString[2]));
		    }
			total3 = total1 + total2;
			getTotalBudgetFee.put(-1, total1);
			getTotalBudgetFee.put(-2, total2);
			getTotalBudgetFee.put(-3, total3);			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
		}
	}

}
