package Common;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FOLLogger {

	   private static Logger logger = Logger.getLogger(FOLLogger.class);
	    
	   static{
		   System.out.println(Thread.currentThread().getContextClassLoader().getResource("log4j.properties"));
		   PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("log4j.properties"));
	   }
	   
	   public static Logger getLogger(Class<?> c)
	   {
		   return Logger.getLogger(c);
	   }
	   
	   public static void Debug(String message)
	   {
		   logger.debug(message);
	   }
	   
	   public static void Info(String message)
	   {
		   logger.info(message);
	   }
	   
	   public static void main(String[] args) {
		new FOLLogger();
	}
	   
	}