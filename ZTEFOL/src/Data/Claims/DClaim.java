package Data.Claims;

import java.sql.Timestamp;

import Common.UserMngt;

public class DClaim {

	private int id;

	private String no;

	private String invoiceNo;

	private int invoiceType;

	private String billNo;

	private int productId;
	private String productName;

	private int subProductId;

	private boolean hasBill;

	private int billLoaction;

	private boolean directToFinance;

	private int status;
	private String statusDesc;

	private String summary;

	private int accountAdjust;

	private int employLevel;

	private int currencyType;

	private int hedgeAccount;

	private int payType;

	private int deptId;
	private String deptName;

	private float totalFee;

	private String approvalId;
	private String approvalname;

	private Timestamp submitDate;

	private float approvalAmount;
	private int approvalFlowId;

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean isHasBill() {
		return hasBill;
	}

	public void setHasBill(boolean hasBill) {
		this.hasBill = hasBill;
	}

	public int getBillLoaction() {
		return billLoaction;
	}

	public void setBillLoaction(int billLoaction) {
		this.billLoaction = billLoaction;
	}

	public boolean isDirectToFinance() {
		return directToFinance;
	}

	public void setDirectToFinance(boolean directToFinance) {
		this.directToFinance = directToFinance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getAccountAdjust() {
		return accountAdjust;
	}

	public void setAccountAdjust(int accountAdjust) {
		this.accountAdjust = accountAdjust;
	}

	public int getEmployLevel() {
		return employLevel;
	}

	public void setEmployLevel(int employLevel) {
		this.employLevel = employLevel;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public int getHedgeAccount() {
		return hedgeAccount;
	}

	public void setHedgeAccount(int hedgeAccount) {
		this.hedgeAccount = hedgeAccount;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public float getApprovalAmount() {
		return approvalAmount;
	}

	public void setApprovalAmount(float approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	public int getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(int subProductId) {
		this.subProductId = subProductId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/***
	 * 该单据提单人是否为报销项目的创建者
	 * 
	 * @return
	 */
	public boolean isProjectOwner() {
//		if (productId == ProductMngt.Sales) {
//			String owner = ProductMngt.getSubProductById(subProductId).getOwner();
//			return no.equals(owner);
//		}
		return UserMngt.getInstance().getUserInfoMap().get(no).getDeptId() == productId;
	}

	public String getApprovalname() {
		return approvalname;
	}

	public void setApprovalname(String approvalname) {
		this.approvalname = approvalname;
	}

	public int getApprovalFlowId() {
		return approvalFlowId;
	}

	public void setApprovalFlowId(int approvalFlowId) {
		this.approvalFlowId = approvalFlowId;
	}

}
