package View;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import Common.FOLLogger;


public class ParameterUtil {

  private static Logger logger = FOLLogger.getLogger(ParameterUtil.class);
 
public static String getChineseString(HttpServletRequest requst, String s)
{
    return getChineseString(requst,s,"utf-8");
}

  public static String getChineseString(HttpServletRequest requst, String s,String format)
  {
    try {
      return new String(requst.getParameter(s).getBytes("ISO-8859-1"), format);
    } catch (Exception e) {
      logger.error("parse the string " + s + " error ");
      return null;
    }
  }

public static boolean isEmpty(String s)
{
    return !((s != null) && (!s.equals("")));
}

}
