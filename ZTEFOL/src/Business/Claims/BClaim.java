package Business.Claims;

import java.sql.Timestamp;

public class BClaim {

	private int id;

	private String no;

	private String invoiceNo;

	private String billNo;

	private int productId;

	private int subProductId;

	private String productName;

	private String subProductName;

	private boolean hasBill;

	private int billLoaction;

	private boolean directToFinance;

	private String summary;

	private int accountAdjust;

	private int employLevel;

	private int currencyType;

	private int hedgeAccount;

	private int payType;

	private int deptId;

	private String deptName;

	private float totalFee;

	private float transportCost;

	private float accommodation;

	private float otherCost;

	private String approvalId;

	private String approvalName;

	private int invoiceType;

	private int allowance;

	private Timestamp submitDate;

	private float approvalAmount;

	private BClaimItem[] items;
	private int approvalFlowId;
	private String statusDesc;

	public BClaimItem[] getItems() {
		return items;
	}

	public void setItems(BClaimItem[] items) {
		this.items = items;
		totalFee = transportCost = accommodation = otherCost = allowance = 0;
		for (int i = 0; i < items.length; i++) {
			totalFee += items[i].getTotalFee();
			transportCost += items[i].getTransportCost();
			accommodation += items[i].getAccommodation();
			otherCost += items[i].getOtherCost();
			allowance += items[i].getAllowance();
		}
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public float getTotalFeeOther() {

		return totalFee;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
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

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getAllowance() {
		return allowance;
	}

	public void setAllowance(int allowance) {
		this.allowance = allowance;
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	public float getApprovalAmount() {
		return approvalAmount;
	}

	public void setApprovalAmount(float approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	public int getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(int subProductId) {
		this.subProductId = subProductId;
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

	public float getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(float otherCost) {
		this.otherCost = otherCost;
	}

	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}

	public float getTotalFee() {
		return totalFee;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getApprovalFlowId() {
		return approvalFlowId;
	}

	public void setApprovalFlowId(int approvalFlowId) {
		this.approvalFlowId = approvalFlowId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}
