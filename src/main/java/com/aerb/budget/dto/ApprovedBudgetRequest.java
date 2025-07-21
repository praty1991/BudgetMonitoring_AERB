package com.aerb.budget.dto;

import java.util.List;

public class ApprovedBudgetRequest {
    private String financialYear;
    private String budgetType; // Replaces "month"
    private List<ApprovedBudgetItem> items;

    // Inner DTO class
    public static class ApprovedBudgetItem {
        private String code;
        private String headOfAccount;
        private double amount;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getHeadOfAccount() {
            return headOfAccount;
        }

        public void setHeadOfAccount(String headOfAccount) {
            this.headOfAccount = headOfAccount;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    // Getters and Setters
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

    public List<ApprovedBudgetItem> getItems() {
        return items;
    }

    public void setItems(List<ApprovedBudgetItem> items) {
        this.items = items;
    }
}
