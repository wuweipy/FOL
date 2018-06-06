package Business.Claims;

import java.sql.Timestamp;

import Data.Common.Entry;

/* 对应“我报销的”查询表格内容  by yjj*/
public class BMyClaim  extends Entry{
	
	private int id;
	
	private String no;
	
	private String name;	

	private String invoiceNo;
	
	private String billNo;
	
	private int productId;
	
	private String productName;
	
	private String summary;
	
	private int accountAdjust;
	
	private int payType;
	
	private String payTypeStr;
	
	private int deptId;
	
	private String deptName;
	
	private float totalFee;
	
	private float checkFee;
	
	private int approvalId;
	
	private Timestamp submitDate;
	
	private String endDate;
	
	private int status;
	
	private String statusStr;

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public float getTotalFee()
	{
		return totalFee;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(int approvalId) {
		this.approvalId = approvalId;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	public String getPayTypeStr() {
		return payTypeStr;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setCheckFee(float checkFee) {
		this.checkFee = checkFee;
	}

	public float getCheckFee() {
		return checkFee;
	}
	


}
