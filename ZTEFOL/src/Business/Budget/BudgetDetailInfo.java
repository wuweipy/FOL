package Business.Budget;

import java.util.ArrayList;

import Data.Common.Entry;

public class BudgetDetailInfo {
	private String budgetId;
	
	private String courseName;
	
	private int money;
	
	private String description;
	
	private String instituteOpinion;
	
	private String financeOpinion;
	
	private String managerOpinion;
	
	private ArrayList<Entry> submoneys;

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstituteOpinion() {
		return instituteOpinion;
	}

	public void setInstituteOpinion(String instituteOpinion) {
		this.instituteOpinion = instituteOpinion;
	}

	public String getFinanceOpinion() {
		return financeOpinion;
	}

	public void setFinanceOpinion(String financeOpinion) {
		this.financeOpinion = financeOpinion;
	}

	public String getManagerOpinion() {
		return managerOpinion;
	}

	public void setManagerOpinion(String managerOpinion) {
		this.managerOpinion = managerOpinion;
	}

	public ArrayList<Entry> getSubmoneys() {
		return submoneys;
	}

	public void setSubmoneys(ArrayList<Entry> submoneys) {
		this.submoneys = submoneys;
	}	
}
