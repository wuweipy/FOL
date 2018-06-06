package Business.Claims;

import java.sql.Date;

public class BClaimItem 
{
	    private int id;
	    
	    private Date startDate;
	    
	    private Date endDate;
	    
	    private String startCity;
	    
	    private int desProvince;
	    
	    private String desCity;
	    
	    private int transportation;
	    
	    private float transportCost;
	    
	    private float accommodation;
	    
	    private float otherCost;
	    
	    private int allowance;

		private String invoinceNO;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public String getStartCity() {
			return startCity;
		}

		public void setStartCity(String startCity) {
			this.startCity = startCity;
		}

		public int getDesProvince() {
			return desProvince;
		}

		public void setDesProvince(int desProvince) {
			this.desProvince = desProvince;
		}

		public String getDesCity() {
			return desCity;
		}

		public void setDesCity(String desCity) {
			this.desCity = desCity;
		}

		public int getTransportation() {
			return transportation;
		}

		public void setTransportation(int transportation) {
			this.transportation = transportation;
		}

		public float getTransportCost() {
			return transportCost;
		}

		public void setTransportCost(float transportCost) {
			this.transportCost = transportCost;
		}

		public float getAccommodation() {
			return accommodation;
		}

		public void setAccommodation(float accommodation) {
			this.accommodation = accommodation;
		}

		public String getInvoinceNO() { 
			return invoinceNO;
		}

		public void setInvoinceNO(String invoinceNO) {
			this.invoinceNO = invoinceNO;
		}

		public float getOtherCost() {
			return otherCost;
		}

		public void setOtherCost(float otherCost) {
			this.otherCost = otherCost;
		}
		
		public float getTotalFee()
		{
		   	return transportCost + otherCost + accommodation + getAllowance();
		}

		public int getAllowance() 
		{
			int days = (int)((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			return days * 150;
		}
	    
}
