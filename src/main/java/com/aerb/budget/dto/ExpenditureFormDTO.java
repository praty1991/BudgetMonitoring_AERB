package com.aerb.budget.dto;

import java.util.List;

public class ExpenditureFormDTO {
    private String financialYear;
    private int month;  // 1 = Jan, 2 = Feb, etc.
    private List<ExpenditureItemDTO> items;
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public List<ExpenditureItemDTO> getItems() {
		return items;
	}
	public void setItems(List<ExpenditureItemDTO> items) {
		this.items = items;
	}
}
