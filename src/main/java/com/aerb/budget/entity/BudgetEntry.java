package com.aerb.budget.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "budget_entries")
public class BudgetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id", nullable = false)
    private BudgetHead head;

    @Column(name = "item_description", columnDefinition = "TEXT")
    private String itemDescription;

    @Column(name = "re_value")
    private double reValue;

    @Column(name = "be_value")
    private double beValue;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "financial_year", nullable = false)
    private String financialYear; // e.g. "2024-25"

    @Column(name = "budget_type", nullable = false)
    private String budgetType; // "RE" or "BE"

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public BudgetHead getHead() {
		return head;
	}

	public void setHead(BudgetHead head) {
		this.head = head;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public double getReValue() {
		return reValue;
	}

	public void setReValue(double reValue) {
		this.reValue = reValue;
	}

	public double getBeValue() {
		return beValue;
	}

	public void setBeValue(double beValue) {
		this.beValue = beValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


}
