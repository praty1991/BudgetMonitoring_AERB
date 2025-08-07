package com.aerb.budget.dto;
public class DepartmentBudgetDTO {
    public DepartmentBudgetDTO(String name, double estimated, double approved, double expenditure) {
		super();
		this.name = name;
		this.estimated = estimated;
		this.approved = approved;
		this.expenditure = expenditure;
	}
	private String name;
    private double estimated;
    private double approved;
    private double expenditure;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getEstimated() {
		return estimated;
	}
	public void setEstimated(double estimated) {
		this.estimated = estimated;
	}
	public double getApproved() {
		return approved;
	}
	public void setApproved(double approved) {
		this.approved = approved;
	}
	public double getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}

    // Constructors, Getters, Setters
}
