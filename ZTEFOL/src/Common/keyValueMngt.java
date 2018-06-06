package Common;

public class keyValueMngt 
{
   private String[] transports;
   
   private String[] payTypes;
   
   private static keyValueMngt transportMngt = new keyValueMngt();
   
   private keyValueMngt()
   {
	   transports = new String[]{"飞机","轮船","汽车","自备车","火车"};
	   payTypes = new String[]{"银行存款","现金"};
   }
   
   public static keyValueMngt getInstance()
   {
	   return transportMngt; 
   }
   
   public String getTransport(int index)
   {
	   return transports[index];
   }
   
   public String getPayType(int index)
   {
	   return payTypes[index];
   }
}
