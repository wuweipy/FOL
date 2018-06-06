package Business.Budget;

import java.sql.Timestamp;

public class BudgetInfo {
	private String budgetId;
	
	private String name;
	
	private String no;
	
	private String agencyNo;
	
	private String agencyName;
	
	private int projectId;
	
	private String projectName;
	
	private String institute;
	
	private String finance;
	
	private String manager;	
	
	private int totalMoney;
	
	private int status;
	
	private Timestamp submitDate;
	
	private Timestamp instituteDate;	
	
	private Timestamp financeDate;
	
	private Timestamp managerDate;			

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Timestamp getInstituteDate() {
		return instituteDate;
	}

	public void setInstituteDate(Timestamp instituteDate) {
		this.instituteDate = instituteDate;
	}

	public Timestamp getFinanceDate() {
		return financeDate;
	}

	public void setFinanceDate(Timestamp financeDate) {
		this.financeDate = financeDate;
	}

	public Timestamp getManagerDate() {
		return managerDate;
	}

	public void setManagerDate(Timestamp managerDate) {
		this.managerDate = managerDate;
	}

	public String getAgencyNo() {
		return agencyNo;
	}

	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
