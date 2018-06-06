package Business.UserDetail;

import Common.DeptMngt;
import Common.ProductMngt;
import Common.TechTitleMngt;
import Data.UserDetail.DUserDetail;

public class BUserDetail 
{
	private int id;
	
	private String name;
	
	private String no;
	
	private String allNo;
	
	private String dept;
	
	private int deptID;
	
	private int productId;
	
	private String email;
	
	private String tel;
	
	private String product;
	
	private String techTitle;

	public BUserDetail(DUserDetail userDetail, String userName) 
	{
		id = userDetail.getId();
		no = userDetail.getNo();
		name = userName;
		allNo = userDetail.getAllNo();
		dept = DeptMngt.getInstance().getDeptName(userDetail.getDeptID());
		deptID = userDetail.getDeptID();
		productId = userDetail.getProductNo();
		email = userDetail.getEmail();
		tel = userDetail.getTel();
		product = ProductMngt.getInstance().getProductName(userDetail.getProductNo());
		techTitle = TechTitleMngt.getInstance().getTechTitleName(userDetail.getTechTitleNo());
	}
	
	public BUserDetail()
	{
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAllNo() {
		return allNo;
	}

	public void setAllNo(String allNo) {
		this.allNo = allNo;
	}

	public String getDeptStr() {
		return dept;
	}

	public void setDeptStr(String deptStr) {
		this.dept = deptStr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}

	public String[] getStrings()
	{
		String[] rtnStrings = new String[7];
		rtnStrings[0] = name;
		rtnStrings[1] = allNo;
		rtnStrings[2] = dept;
		rtnStrings[3] = email;
		rtnStrings[4] = tel;
		rtnStrings[5] = product;
		rtnStrings[6] = techTitle;
		return rtnStrings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	

}
