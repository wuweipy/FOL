package Data.FinanceApproval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.Claims.DClaim;
import Data.DBHandler.DBConnectorFactory;
import Jmail.EmailHander;

/**
 * <ul>
 * <li>文件名称: FinanceApprovalDAO</li>
 * <li>文件描述: </li>
 * <li>版权所有: 版权所有(C) 2003</li>
 * <li>公   司: 中兴通讯股份有限公司</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期: Sep 3, 2013 </li>
 * </ul>
 * <ul>
 * <li>修改记录: </li>
 * <li>版 本 号: </li>
 * <li>修改日期: </li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 * @author Administrator
 * @version 
 */
public class FinanceApprovalDAO implements IFinanceApprovalDAO
{
    private static Logger logger = FOLLogger.getLogger(FinanceApprovalDAO.class);
    

    public boolean approval(String invoiceNo, int flag, int status, int appAmount, String approvalId, String approvalName, String appDate)
    {
        // TODO Auto-generated method stub
        Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
        if(connection == null)
        {
            return false;
        }
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        String comment = "";
        int nextStatus = 1;
        if(flag == 1)
        {
            nextStatus = 4; //财务审批通过之后的下一状态：关闭
            comment = "同意";
            EmailHander.emailHander(approvalId,invoiceNo, 3);
        }
        else if(flag == 0)
        {
            nextStatus = 5; //财务审批不通过的下一状态：待确定
            comment = "不同意";
            EmailHander.emailHander(approvalId,invoiceNo, 2);
        }
        
        try 
        {
            String sql = "update CLAIMS set status=?, approvalAmount=? where invoiceNo=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, nextStatus);
            statement.setInt(2, appAmount);
            statement.setString(3, invoiceNo);
            
            statement.executeUpdate();
            
            String insertSql = "insert into APPROVALINFO(invoiceNo,status,approvalId, " +
            "approvalName,appState,comment,appDate) values (?,?,?,?,?,?,?)";
            statement2 = connection.prepareStatement(insertSql);
            statement2.setString(1, invoiceNo);
            statement2.setInt(2, status);
            statement2.setString(3, approvalId);
            statement2.setString(4, approvalName);
            statement2.setInt(5, flag);
            statement2.setString(6, comment);
            statement2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            statement2.executeUpdate();
        }
        catch (SQLException e) 
        {
            logger.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement2, null);
        }  
        
        return true;
    }

    public boolean approval(String invoiceNo, int flag, int status, int appAmount, String approvalId, String approvalName, String appDate,String budgetId)
    {
        // TODO Auto-generated method stub
        Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
        if(connection == null)
        {
            return false;
        }
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        String comment = "";
        int nextStatus = 1;
        if(flag == 1)
        {
            nextStatus = 4; //财务审批通过之后的下一状态：关闭
            comment = "同意";
            EmailHander.emailHander(approvalId,invoiceNo, 3);
        }
        else if(flag == 0)
        {
            nextStatus = 5; //财务审批不通过的下一状态：待确定
            comment = "不同意";
            EmailHander.emailHander(approvalId,invoiceNo, 2);
        }
        
        try 
        {
		  	String sql = "update CLAIMS set status=?, approvalAmount=?, budgetId=? where invoiceNo=?";
			statement = connection.prepareStatement(sql);

            statement.setInt(1, nextStatus);
            statement.setInt(2, appAmount);
			statement.setString(3,budgetId);	
		    statement.setString(4,invoiceNo);		
			statement.executeUpdate();	
            
            String insertSql = "insert into APPROVALINFO(invoiceNo,status,approvalId, " +
            "approvalName,appState,comment,appDate) values (?,?,?,?,?,?,?)";
            statement2 = connection.prepareStatement(insertSql);
            statement2.setString(1, invoiceNo);
            statement2.setInt(2, status);
            statement2.setString(3, approvalId);
            statement2.setString(4, approvalName);
            statement2.setInt(5, flag);
            statement2.setString(6, comment);
            statement2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            statement2.executeUpdate();
        }
        catch (SQLException e) 
        {
            logger.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement2, null);
        }  
        
        return true;
    }

    public ArrayList<DClaim> getClaimDetail(int status, String approvalId, String invoiceNo, int invoiceType, int deptId, String employeeName,
                                            int productId)
    {
        // TODO Auto-generated method stub
        Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
        ArrayList<DClaim> dClaimDetail = new ArrayList<DClaim>();
        DClaim dClaim = null;
        if(connection == null)
        {
          return null;
        }
        Statement statement = null;
        ResultSet rs = null;
        
        String queryStr = "";
        if(!invoiceNo.equalsIgnoreCase("-1"))
        {
            queryStr += " and a.invoiceNo = '" + invoiceNo + "'" ;
        }
        
        if(invoiceType != -1)
        {
            queryStr += " and a.invoiceType = '" + invoiceType + "'" ;
        }
        
        if(deptId != -1)
        {
            queryStr += " and a.deptId = '" + deptId + "'" ;
        }
        
        if(!employeeName.equalsIgnoreCase("-1"))
        {
            queryStr += " and b.USERNAME = '" + employeeName + "'" ;
        }

        if(productId != -1)
        {
            queryStr += " and a.productId = '" + productId + "'" ;
        }
        
        try 
        {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from CLAIMS a, USERINFO b where a.no = b.NO and a.status = '" + status + "' and a.approvalId = '" + approvalId + "'" + queryStr);

            for(int i=0; rs.next(); i++ )
            {
              dClaim = new DClaim();
              dClaim.setId(rs.getInt("id"));
              dClaim.setInvoiceNo(rs.getString("invoiceNo"));
              dClaim.setInvoiceType(rs.getInt("invoiceType"));
              dClaim.setDeptId(rs.getInt("deptId"));
              dClaim.setBillNo(rs.getString("billNo"));
              dClaim.setDirectToFinance(rs.getBoolean("directToFinance"));
              dClaim.setBillLoaction(rs.getInt("billLocation"));
              dClaim.setProductId(rs.getInt("productId"));
              dClaim.setHasBill(rs.getBoolean("hasBill"));
              dClaim.setNo(rs.getString("no"));
              dClaim.setStatus(rs.getInt("status"));
              dClaim.setSummary(rs.getString("summary"));
              dClaim.setAccountAdjust(rs.getInt("accountAdjust"));
              dClaim.setEmployLevel(rs.getInt("employeeLevel"));
              dClaim.setCurrencyType(rs.getInt("currencyType"));
              dClaim.setHedgeAccount(rs.getInt("hedgeAccount"));
              dClaim.setPayType(rs.getInt("payType"));
              dClaim.setApprovalId(rs.getString("approvalId"));
              dClaim.setTotalFee(rs.getInt("totalFee"));
              dClaim.setSubmitDate(rs.getTimestamp("submitDate"));
              dClaim.setApprovalAmount(rs.getInt("approvalAmount"));
              dClaimDetail.add(dClaim);           
            }
        }
        catch (SQLException e) 
        {
          logger.error(e.getMessage());
          System.out.print(e.getMessage());
        }
        finally
        {
          DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
        }
        
        return dClaimDetail;
    }
    
    
    public static void main(String[] args) {
    
      FinanceApprovalDAO claimsDAO = new FinanceApprovalDAO();
      ArrayList<DClaim> detail = claimsDAO.getClaimDetail( 1 ,"10144183", "-1", -1, -1, "-1", -1);
      System.out.println(detail.size());
      System.out.println(detail.isEmpty());
    
      if(!detail.isEmpty())
      {
           for(int index = 0; index < detail.size(); index++)
           {
               System.out.println(index + "  " + detail.get(index).getInvoiceNo());
               System.out.println(index + "  " + detail.get(index).getInvoiceType());
               System.out.println(index + "  " + detail.get(index).getDeptId());
               System.out.println(index + "  " + detail.get(index).getStatus());
               System.out.println(index + "  " + detail.get(index).getSummary());
               System.out.println(index + "  " + detail.get(index).getNo());
               System.out.println(index + "  " + detail.get(index).getApprovalId());
               System.out.println(index + "  " + detail.get(index).getTotalFee());
               System.out.println(index + "  " + (detail.get(index).getSubmitDate().getYear()+1900)+ "-" + (detail.get(index).getSubmitDate().getMonth()+1)+ "-" + detail.get(index).getSubmitDate().getDate());
               System.out.println(index + "  " + detail.get(index).getApprovalAmount());
           }
       }
    }

}
