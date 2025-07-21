package com.aerb.budget.dto;
public class BudgetCheckRequest {
    private String division;
    private String budgetType;
    private String financialYear;

    // Getters and setters
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public String getBudgetType() { return budgetType; }
    public void setBudgetType(String budgetType) { this.budgetType = budgetType; }

    public String getFinancialYear() { return financialYear; }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }
}
