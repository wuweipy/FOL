package Jmail;

public class Test  
{  
    public static void main(String[] args)  
    {  
        // 这个类主要是设置邮件  
        MailSenderInfo mailInfo = new MailSenderInfo();  
        mailInfo.setMailServerHost("szsmtp06.zte.com.cn");// 如果你的是qq邮箱，那么此处换成：smtp.qq.com  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);   
        mailInfo.setUserName("MaZhiNan0224000019");// 如果你的是qq邮箱，那么此处换成你的qq邮箱账号  
        mailInfo.setPassword("ztesj20140327");// 您的邮箱密码  
        mailInfo.setFromAddress("STRAWCOM-FOL");// 如果你的是qq邮箱，那么此处换成你的qq邮箱账号  
        //目的邮箱，就是指你要发送到哪个邮箱的邮箱账号  
        mailInfo.setToAddress("ji.weiwei127@zte.com.cn"); // 如果你的是qq邮箱，那么此处换成你的qq邮箱账号  
        mailInfo.setSubject("中兴思秸财务差旅报销审批通知");  
        mailInfo.setContent("请您点击以下链接进行中兴思秸财务差旅报销审批：<a href=http://10.112.27.149>中兴思秸财务差旅报销系统</a>");  
        // 这个类主要来发送邮件   
        SimpleMailSender sms = new SimpleMailSender();  
        sms.sendHtmlMail(mailInfo);// 发送html格式   
        
        mailInfo.setToAddress("ren.wenyi@zte.com.cn");
        sms.sendHtmlMail(mailInfo);// 发送html格式
        
        mailInfo.setToAddress("ye.guowei@zte.com.cn");
        sms.sendHtmlMail(mailInfo);// 发送html格式
        
        
        
    }  
}  