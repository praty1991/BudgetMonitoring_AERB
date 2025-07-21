package com.aerb.budget.dto;
public class ExpenditureReportDto {
    private String code;
    private String headOfAccount;
    private double be;
    private double monthlyExpenditure;
    private double progressiveRupees;
    private double expenditureLakhs;
    private double progressiveLakhs;
    private double percentageOfBE;
    private double balance;
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
	public double getBe() {
		return be;
	}
	public void setBe(double be) {
		this.be = be;
	}
	public double getMonthlyExpenditure() {
		return monthlyExpenditure;
	}
	public void setMonthlyExpenditure(double monthlyExpenditure) {
		this.monthlyExpenditure = monthlyExpenditure;
	}
	public double getProgressiveRupees() {
		return progressiveRupees;
	}
	public void setProgressiveRupees(double progressiveRupees) {
		this.progressiveRupees = progressiveRupees;
	}
	public double getExpenditureLakhs() {
		return expenditureLakhs;
	}
	public void setExpenditureLakhs(double expenditureLakhs) {
		this.expenditureLakhs = expenditureLakhs;
	}
	public double getProgressiveLakhs() {
		return progressiveLakhs;
	}
	public void setProgressiveLakhs(double progressiveLakhs) {
		this.progressiveLakhs = progressiveLakhs;
	}
	public double getPercentageOfBE() {
		return percentageOfBE;
	}
	public void setPercentageOfBE(double percentageOfBE) {
		this.percentageOfBE = percentageOfBE;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

    // Getters and Setters
}
