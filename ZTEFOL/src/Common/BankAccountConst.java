package Common;

import java.util.HashMap;

public class BankAccountConst {

	/***
	 * 省份代码
	 */
	public static final int HUBEI = 0;
	public static final int SHANGHAI = 1;
	public static HashMap<Integer, String> province = new HashMap<Integer, String>();
	
	/***
	 * 城市代码
	 */
	public static final int WUHAN = 0;
	public static HashMap<Integer, String> city = new HashMap<Integer, String>();
	
	/***
	 * 银行代码
	 */
	public static final int ZHAOHANG = 0;
	public static final int ZHONGHANG = 1;
	public static final int GONGHANG = 2;
	public static HashMap<Integer, String> bank = new HashMap<Integer, String>();
	
	
	static
	{
		province.put(HUBEI,"湖北省");
		province.put(SHANGHAI,"上海");
		
		city.put(WUHAN, "武汉市");
		city.put(SHANGHAI, "上海市");
		
		bank.put(ZHAOHANG, "招商银行");
		bank.put(ZHONGHANG, "中国银行");
		bank.put(GONGHANG, "中国工商银行");
		
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
