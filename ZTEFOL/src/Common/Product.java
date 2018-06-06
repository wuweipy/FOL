package Common;

public class Product {
  
	private int id;
	
	private String name;
	
	private String owner;
	
	private int parentProductId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(int parentProductId) {
		this.parentProductId = parentProductId;
	}

	 
	
	
}
