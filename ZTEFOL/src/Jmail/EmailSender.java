package Jmail;

public class EmailSender extends Thread{
	
	private MailSenderInfo mailInfo;
	
	public EmailSender(MailSenderInfo mailInfo)
	{
		this.mailInfo = mailInfo;
	}
	
	@Override
	public void run()
	{
		SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo);    
	}
	
	public static void send(MailSenderInfo mailInfo)
	{
		new EmailSender(mailInfo).start();
	}
	

}
