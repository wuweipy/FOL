package Common.Location;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Common.DeptMngt;
import Data.Common.CityDAO;
import Data.Common.DCity;
import Data.Common.DProvince;
import Data.Common.ICityDAO;
import Data.Common.ProvinceDAO;

public class LocationMngt
{
	private static LocationMngt locationMngt = new LocationMngt();
	
	private HashMap<Integer, BProvince> provinceMap = new HashMap<Integer, BProvince>();
	
	private HashMap<Integer, String> cityMap = new HashMap<Integer, String>();
	
	private LocationMngt()
	{
		init();
	}

	private void init() 
	{
        ProvinceDAO provinceDAO = new ProvinceDAO();
        CityDAO cityDAO = new CityDAO();
        Iterator<DProvince> iterator = provinceDAO.getProvinceList().iterator();
        
        while(iterator.hasNext())
        {
        	 DProvince province = iterator.next();
        	 BProvince bProvince = new BProvince();
        	 bProvince.setId(province.getId());
        	 bProvince.setName(province.getName());
        	 assinToProvince(cityDAO.getCityList(bProvince.getId()), bProvince);
        	 provinceMap.put(bProvince.getId(), bProvince);
        }
	}
	
	private void assinToProvince(List<DCity> dCities,BProvince bProvince)
	{
		for (int i = 0; i < dCities.size(); i++) 
		{
			DCity dCity = dCities.get(i);
			cityMap.put(dCity.getId(), dCity.getName());
			bProvince.addCity(new BCity(dCity.getId(),dCity.getName()));
		}
	}

	public static LocationMngt getInstance() 
	{
		return locationMngt;
	}

	public String getProvinceName(int provinceId) 
	{
		return provinceMap.get(provinceId).getName();
	}
	
	public HashMap<Integer, BProvince> getProvinceMap()
	{
		return provinceMap;
	}
	
	public String getCityName(int cityid) 
	{
		return cityMap.get(cityid);
	}
	
	public HashMap<Integer, String> getCityMap()
	{
		return cityMap;
	}
	
	public static void main(String[] args) {
		DeptMngt.getInstance();
	}

	public boolean deleteCity(int id) {
		ICityDAO cityDAO = new CityDAO();
		return cityDAO.delete(id);
	}
	
	public boolean addCity(int provinceid,int id,String name)
	{
		DCity city = new DCity();
		city.setProvinceId(provinceid);
		city.setId(id);		
		city.setName(name);
		return new CityDAO().add(city);
	}
	
	public void refresh()
	{
		provinceMap.clear();
		cityMap.clear();
		init();
	}
}
