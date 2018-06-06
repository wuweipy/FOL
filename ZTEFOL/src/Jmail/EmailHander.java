package Jmail;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeUtility;

import Common.CourseMngt;
import org.apache.log4j.Logger;

import View.BudgetServlet;

import Common.FOLLogger;
import Common.UserMngt;
import Data.Claims.ClaimsDAO;
import Data.Common.Countersign.DCountersign;
import Data.DBHandler.DBConnectorFactory;

import Business.Budget.BudgetDetailInfo;
import Business.Budget.BudgetInfo;

public class EmailHander 
{   private static Logger logger = FOLLogger.getLogger(EmailHander.class);
    private static String href = "<a href=http://10.112.28.32:8080/FOL>����˼�ղ���ϵͳ</a>";
    private static String mailHost = "10.30.18.230";    
    public static void emailHander(String nextApprovalId,String invoiceNo, int flag)
    {
    	DCountersign countersign = getDCountersign(invoiceNo);
    	String no = countersign.getNo();
    	String name = UserMngt.getInstance().getUserInfoMap().get(no).getName();
    	String summary = countersign.getSummary();
    	float totalFee = countersign.getTotalFee();
    	Timestamp submitDate = countersign.getSubmitDate();
    	int invoiceType = countersign.getInvoiceType();
    	String approvalName = UserMngt.getInstance().getUserInfoMap().get(nextApprovalId).getName();
    	String invoiceTypeName = CourseMngt.getInstance().getCourseMap().get(invoiceType);
    	String submitemail = UserMngt.getInstance().getuserDetailMap().get(no).getEmail();
        MailSenderInfo mailInfo = new MailSenderInfo();  
        mailInfo.setMailServerHost(mailHost);// ��������qq���䣬��ô�˴����ɣ�smtp.qq.com  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);   
        mailInfo.setUserName("MaZhiNan0224000019");// ��������qq���䣬��ô�˴��������qq�����˺�  
        mailInfo.setPassword("alanzx1227!!");// ������������  
        mailInfo.setFromAddress("STRAWCOM-FOL@zte.com.cn");// ��������qq���䣬��ô�˴��������qq�����˺�  
        //Ŀ�����䣬����ָ��Ҫ���͵��ĸ�����������˺�  
//        mailInfo.setToAddress(email); // ��������qq���䣬��ô�˴��������qq�����˺�  
        if(flag == 1)
        {
        	String email = UserMngt.getInstance().getuserDetailMap().get(nextApprovalId).getEmail();
        	mailInfo.setToAddress(email);
        	String subject = "<����˼�ղ���ϵͳ> ����˼�ղ���������֪ͨ:" + name + "��"+ invoiceTypeName +"��������������";
        	mailInfo.setSubject(getCodeWord(subject));  
            mailInfo.setContent("��������������ӽ�������˼�ղ���������:" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>���ݺ�:</td><td>"+invoiceNo+"</td></tr><tr><td>�ᵥ��:</td><td>"+name+"</td></tr><tr><td>ժҪ:</td><td>"+summary+"</td></tr>" +
            				"<tr><td>�ܷ���:</td><td>"+totalFee+"</td></tr>" +
            						"<tr><td>������:</td><td>"+approvalName+"</td></tr>" +
            								"<tr><td>�ύʱ��:</td><td>"+submitDate+"</td></tr>" +
            										"</table>"); 
        }       
        else if(flag == 2)
        {
        	mailInfo.setToAddress(submitemail);
        	String subject = "<����˼�ղ���ϵͳ> ����˼�ղ������˻�֪ͨ��" +approvalName+ "�ܾ�������" + invoiceTypeName + "������";
        	mailInfo.setSubject(getCodeWord(subject)); 
            mailInfo.setContent("���Ĳ���������δͨ���������鿴���飺" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>���ݺ�:</td><td>"+invoiceNo+"</td></tr><tr><td>�ᵥ��:</td><td>"+name+"</td></tr><tr><td>ժҪ:</td><td>"+summary+"</td></tr>" +
            				"<tr><td>�ܷ���:</td><td>"+totalFee+"</td></tr>" +
            						"<tr><td>������:</td><td>"+approvalName+"</td></tr>" +
            								"<tr><td>�ύʱ��:</td><td>"+submitDate+"</td></tr>" +
            										"</table>"); 
        }
        else if(flag==3)
        {
        	mailInfo.setToAddress(submitemail);
        	String subject = "<����˼�ղ���ϵͳ> "+approvalName+"����ͨ��������" + invoiceTypeName + "����������鿴";
        	mailInfo.setSubject(getCodeWord(subject));  
            mailInfo.setContent("��������˼�ղ���������ͨ���������鿴���飺" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>���ݺ�:</td><td>"+invoiceNo+"</td></tr><tr><td>�ᵥ��:</td><td>"+name+"</td></tr><tr><td>ժҪ:</td><td>"+summary+"</td></tr>" +
            				"<tr><td>�ܷ���:</td><td>"+totalFee+"</td></tr>" +
            						"<tr><td>������:</td><td>"+approvalName+"</td></tr>" +
            								"<tr><td>�ύʱ��:</td><td>"+submitDate+"</td></tr>" +
            										"</table>"); 
        }
        // �������Ҫ�������ʼ�   
/*        SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo); */   	
//        EmailSender.send(mailInfo);
    }
    
    private static String getCodeWord(String subject) {
		// TODO Auto-generated method stub
    	try {
			subject = MimeUtility.encodeWord(subject, "UTF-8", "Q");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subject;
	}

	public static void emailHanderBudget(String budgetId,int flag,String no,int nextRole, String approvalId)
    {
    	String nextApprovalId = getApprovalId(nextRole);
    	String email = UserMngt.getInstance().getuserDetailMap().get(nextApprovalId).getEmail();
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(mailHost);// ��������qq���䣬��ô�˴����ɣ�smtp.qq.com  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);   
        mailInfo.setUserName("MaZhiNan0224000019");// ��������qq���䣬��ô�˴��������qq�����˺�  
        mailInfo.setPassword("alanzx1227!!");// ������������  
        mailInfo.setFromAddress("STRAWCOM-FOL@zte.com.cn");;// ��������qq���䣬��ô�˴��������qq�����˺�       
        //Ŀ�����䣬����ָ��Ҫ���͵��ĸ�����������˺�  
        mailInfo.setToAddress(email); // ��������qq���䣬��ô�˴��������qq�����˺�  
        
//    	String name = UserMngt.getInstance().getUserInfoMap().get(no).getName();
    	List<BudgetDetailInfo> budgetDetailInfos = new ArrayList<BudgetDetailInfo>();
    	budgetDetailInfos = getBudgetDInfo(budgetId);
    	String submitTime = getSubmitTime(budgetId);
    	String[] getNoAndAgency = getNoAndAgency(budgetId);
    	String realNo = getNoAndAgency[0];
    	String realName = UserMngt.getInstance().getUserInfoMap().get(realNo).getName();
    	String agencyNo = "-";
    	String agencyName = "-";
    	if(!getNoAndAgency[1].equals("-")){
        	agencyNo = getNoAndAgency[1];
        	agencyName = UserMngt.getInstance().getUserInfoMap().get(agencyNo).getName();
    	}
    	StringBuffer message = new StringBuffer();
    	int totalFee = 0;
    	message.append("<br>��Ŀ��������:<br><table>");
    	for(int i=0;i<budgetDetailInfos.size();i++){
    		message.append("<tr>" + budgetDetailInfos.get(i).getCourseName() + "��");
    		message.append(budgetDetailInfos.get(i).getMoney() + ",");    		
    		message.append(budgetDetailInfos.get(i).getDescription() + "</tr>");  
    		totalFee += budgetDetailInfos.get(i).getMoney();
    	}
    	message.append("</table>");
        if(flag == 1)
        {
        	String subject = "<����˼�ղ���ϵͳ> ����˼�ղ���Ԥ������֪ͨ:" + realName + "��Ԥ����������";
        	mailInfo.setSubject(getCodeWord(subject));
            mailInfo.setContent("��������������ӽ�������˼�ղ���Ԥ��������" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>Ԥ���:"+budgetId+"</tr><tr><td>�ᵥ��:"+realName+realNo+"</td></tr><tr><td>ί����:"+agencyName+agencyNo+"</td></tr><tr><td>�ύʱ��:"+submitTime+"</td></tr>" +
            		"<tr><td>Ԥ���ܽ��:"+totalFee+"</td></tr>" +
            		message);
        }       
        else if(flag == 2)
        {
        	if(agencyNo.equals("-")){
            	email = UserMngt.getInstance().getuserDetailMap().get(realNo).getEmail();
        	}
        	else {
        		email = UserMngt.getInstance().getuserDetailMap().get(agencyNo).getEmail();
        	}
        	mailInfo.setToAddress(email);
        	String approvalName = UserMngt.getInstance().getUserInfoMap().get(approvalId).getName();
        	String subject = "<����˼�ղ���ϵͳ> ����˼�ղ���Ԥ�������˻�֪ͨ:" + approvalName + "�˻�������Ԥ������";
        	mailInfo.setSubject(getCodeWord(subject));
            mailInfo.setContent("���Ĳ���Ԥ������δͨ���������鿴���飺" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>Ԥ���:"+budgetId+"</tr><tr><td>�ᵥ��:"+realName+realNo+"</td></tr><tr><td>ί����:"+agencyName+agencyNo+"</td></tr><tr><td>�ύʱ��:"+submitTime+"</td></tr>" +
            		"<tr><td>Ԥ���ܽ��:"+totalFee+"</td></tr>" +
            		message);
        }
        else
        {   email = UserMngt.getInstance().getuserDetailMap().get(no).getEmail();
    	    mailInfo.setToAddress(email);
        	String approvalName = UserMngt.getInstance().getUserInfoMap().get(approvalId).getName();
        	String subject = "<����˼�ղ���ϵͳ> "+approvalName+"����ͨ��������Ԥ�����룬��鿴";
        	mailInfo.setSubject(getCodeWord(subject)); 
            mailInfo.setContent("���Ĳ���Ԥ������ͨ���������鿴���飺" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>Ԥ���:"+budgetId+"</tr><tr><td>�ᵥ��:"+realName+realNo+"</td></tr><tr><td>ί����:"+agencyName+agencyNo+"</td></tr><tr><td>�ύʱ��:"+submitTime+"</td></tr>" +
            		"<tr><td>Ԥ���ܽ��:"+totalFee+"</td></tr>" +
            		message);
        }
        // �������Ҫ�������ʼ�   
/*        SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo); */
//        EmailSender.send(mailInfo);
    }
	
	/*
	 * ������ܾ���Ԥ������
	*/
	public static void emailPermitBudget(String year,String month,int flag,int nextRole,int money)
    {
    	String nextApprovalId = getApprovalId(nextRole);
    	String email = UserMngt.getInstance().getuserDetailMap().get(nextApprovalId).getEmail();
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(mailHost);// ��������qq���䣬��ô�˴����ɣ�smtp.qq.com  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);   
        mailInfo.setUserName("MaZhiNan0224000019");// ��������qq���䣬��ô�˴��������qq�����˺�  
        mailInfo.setPassword("alanzx1227!!");// ������������  
        mailInfo.setFromAddress("STRAWCOM-FOL@zte.com.cn");;// ��������qq���䣬��ô�˴��������qq�����˺�       
        //Ŀ�����䣬����ָ��Ҫ���͵��ĸ�����������˺�  
        mailInfo.setToAddress(email); // ��������qq���䣬��ô�˴��������qq�����˺�   
        String date = year + "��" + month + "��";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(flag == 2)
        {
        	String subject = "<����˼�ղ���ϵͳ> ����˼�ղ���Ԥ������֪ͨ:����˼���人�о���" + month + "��Ԥ����������";
        	mailInfo.setSubject(getCodeWord(subject));
        	String financeId = getApprovalId(3);
        	String financeName = UserMngt.getInstance().getUserInfoMap().get(financeId).getName();;
            mailInfo.setContent("��������������ӽ�������˼�ղ���Ԥ������:" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>����:</td><td>"+date+"</td></tr><tr><td>Ԥ���ܽ��:</td><td>"+money+"</td></tr><tr><td>��������:</td><td>"+financeName+"</td></tr>" +
            				"<tr><td>��������ʱ��:</td><td>"+timestamp+"</td></tr>" +
            										"</table>"); 
        } 
        if(flag == 3)
        {
        	String subject = "<����˼�ղ���ϵͳ> ����˼�ղ���Ԥ������֪ͨ:����˼��" + month + "��Ԥ����������";
        	mailInfo.setSubject(getCodeWord(subject));
        	String financeId = getApprovalId(4);
        	String financeName = UserMngt.getInstance().getUserInfoMap().get(financeId).getName();;
            mailInfo.setContent("��������������ӽ�������˼�ղ���Ԥ������:" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>����:</td><td>"+date+"</td></tr><tr><td>Ԥ���ܽ��:</td><td>"+money+"</td></tr><tr><td>��������:</td><td>"+financeName+"</td></tr>" +
            				"<tr><td>��������ʱ��:</td><td>"+timestamp+"</td></tr>" +
            										"</table>"); 
        }    
        // �������Ҫ�������ʼ�   
/*        SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo); */
//        EmailSender.send(mailInfo);
    }
	
	/*
	 * �ܾ���Ԥ��ί������
	*/
	public static void emailDelegateBudget(String year,String month,String nextApprovalId,int money)
    {
    	String email = UserMngt.getInstance().getuserDetailMap().get(nextApprovalId).getEmail();
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(mailHost);// ��������qq���䣬��ô�˴����ɣ�smtp.qq.com  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);   
        mailInfo.setUserName("MaZhiNan0224000019");// ��������qq���䣬��ô�˴��������qq�����˺�  
        mailInfo.setPassword("alanzx1227!!");// ������������  
        mailInfo.setFromAddress("STRAWCOM-FOL@zte.com.cn");;// ��������qq���䣬��ô�˴��������qq�����˺�       
        //Ŀ�����䣬����ָ��Ҫ���͵��ĸ�����������˺�  
        mailInfo.setToAddress(email); // ��������qq���䣬��ô�˴��������qq�����˺�   
        String date = year + "��" + month + "��";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String subject = "<����˼�ղ���ϵͳ> ����˼�ղ���Ԥ������֪ͨ:����˼��" + month + "��Ԥ����������";
        mailInfo.setSubject(getCodeWord(subject));
        String financeId = getApprovalId(4);
        String financeName = UserMngt.getInstance().getUserInfoMap().get(financeId).getName();
        mailInfo.setContent("��������������ӽ�������˼�ղ���Ԥ������:" + href +"<br /><br>��������:<br>" +
            		"<table><tr><td>����:</td><td>"+date+"</td></tr><tr><td>Ԥ���ܽ��:</td><td>"+money+"</td></tr><tr><td>��������:</td><td>"+financeName+"</td></tr>" +
            				"<tr><td>��������ʱ��:</td><td>"+timestamp+"</td></tr>" +
            										"</table>");   
        // �������Ҫ�������ʼ�   
/*        SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo); */
//        EmailSender.send(mailInfo);
    }	
    
	private static String getSubmitTime(String budgetId) {
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		String submitTime = " ";
		if(connection == null)
		{
			return submitTime;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT * from BUDGETINFO where budgetId = '" + budgetId + "'");
			while (rs.next()) 
			{
				submitTime = rs.getTimestamp("billTime").toString().substring(0,16);
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
		return submitTime;
	}

	private static List<BudgetDetailInfo> getBudgetDInfo(String budgetId) {
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
	
	private static String getApprovalId(int nextRole) {
    	Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
    	String nextApprovalId = "";
		if(connection == null)
		{
			return nextApprovalId;
		}
		Statement statement = null;
		ResultSet rs = null;
		try 
		{
			statement = connection.createStatement();
		    rs = statement.executeQuery("SELECT no from USERINFO where ruleid='" + nextRole + "'");
			while (rs.next()) 
			{
				nextApprovalId = rs.getString("no");
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
		return nextApprovalId;
	}
    
	private static String[] getNoAndAgency(String budgetid) {
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
	
    public static DCountersign getDCountersign(String invoiceNo)
    {    	
    	ClaimsDAO claim = new ClaimsDAO();
    	return claim.getClaim(invoiceNo);   	 
    }
}
