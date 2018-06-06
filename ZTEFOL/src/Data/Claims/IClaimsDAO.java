package Data.Claims;

public interface IClaimsDAO {
	
	//DClaim getClaim(int id);
	
	boolean saveClaim(DClaim claim);
	
	boolean createClaim(DClaim claim);
	
	String getMaxInvoiceNo();

}
