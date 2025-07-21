package com.aerb.budget.dto;

import java.util.List;

public class BudgetSubmissionDTO {
    public String division;
    public String financialYear;
    public String budgetType;
    public List<BudgetItemDTO> items;
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public List<BudgetItemDTO> getItems() {
		return items;
	}
	public void setItems(List<BudgetItemDTO> items) {
		this.items = items;
	}
}
