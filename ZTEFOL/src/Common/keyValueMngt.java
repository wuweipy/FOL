package Common;

public class keyValueMngt 
{
   private String[] transports;
   
   private String[] payTypes;
   
   private static keyValueMngt transportMngt = new keyValueMngt();
   
   private keyValueMngt()
   {
	   transports = new String[]{"�ɻ�","�ִ�","����","�Ա���","��"};
	   payTypes = new String[]{"���д��","�ֽ�"};
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
