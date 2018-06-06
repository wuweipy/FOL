package Common;

import java.util.HashMap;

public class BankAccountConst {

	/***
	 * ʡ�ݴ���
	 */
	public static final int HUBEI = 0;
	public static final int SHANGHAI = 1;
	public static HashMap<Integer, String> province = new HashMap<Integer, String>();
	
	/***
	 * ���д���
	 */
	public static final int WUHAN = 0;
	public static HashMap<Integer, String> city = new HashMap<Integer, String>();
	
	/***
	 * ���д���
	 */
	public static final int ZHAOHANG = 0;
	public static final int ZHONGHANG = 1;
	public static final int GONGHANG = 2;
	public static HashMap<Integer, String> bank = new HashMap<Integer, String>();
	
	
	static
	{
		province.put(HUBEI,"����ʡ");
		province.put(SHANGHAI,"�Ϻ�");
		
		city.put(WUHAN, "�人��");
		city.put(SHANGHAI, "�Ϻ���");
		
		bank.put(ZHAOHANG, "��������");
		bank.put(ZHONGHANG, "�й�����");
		bank.put(GONGHANG, "�й���������");
		
	}
	
	public static String getProvince(int code)
	{
		return province.get(code);
	}
	
	public static String getCity(int code)
	{
		return city.get(code);
	}
	
	public static String getBank(int code)
	{
		return bank.get(code);
	}
}
