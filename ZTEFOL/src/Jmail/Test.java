package Jmail;

public class Test  
{  
    public static void main(String[] args)  
    {  
        // �������Ҫ�������ʼ�  
        MailSenderInfo mailInfo = new MailSenderInfo();  
        mailInfo.setMailServerHost("szsmtp06.zte.com.cn");// ��������qq���䣬��ô�˴����ɣ�smtp.qq.com  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);   
        mailInfo.setUserName("MaZhiNan0224000019");// ��������qq���䣬��ô�˴��������qq�����˺�  
        mailInfo.setPassword("ztesj20140327");// ������������  
        mailInfo.setFromAddress("STRAWCOM-FOL");// ��������qq���䣬��ô�˴��������qq�����˺�  
        //Ŀ�����䣬����ָ��Ҫ���͵��ĸ�����������˺�  
        mailInfo.setToAddress("ji.weiwei127@zte.com.cn"); // ��������qq���䣬��ô�˴��������qq�����˺�  
        mailInfo.setSubject("����˼�ղ�����ñ�������֪ͨ");  
        mailInfo.setContent("��������������ӽ�������˼�ղ�����ñ���������<a href=http://10.112.27.149>����˼�ղ�����ñ���ϵͳ</a>");  
        // �������Ҫ�������ʼ�   
        SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo);// ����html��ʽ   
        
        mailInfo.setToAddress("ren.wenyi@zte.com.cn");
        sms.sendHtmlMail(mailInfo);// ����html��ʽ
        
        mailInfo.setToAddress("ye.guowei@zte.com.cn");
        sms.sendHtmlMail(mailInfo);// ����html��ʽ
        
        
        
    }  
}  