package Common.Location;

import java.util.ArrayList;

public class BProvince  extends BLocation
{ 
   private ArrayList<BCity> cities = new ArrayList<BCity>();
   
   public void addCity(BCity city)
   {
	   cities.add(city);
   }
   
   public ArrayList<BCity> getCities()
   {
	   return cities;
   }
}
