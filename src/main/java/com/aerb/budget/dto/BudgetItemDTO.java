package com.aerb.budget.dto;
public class BudgetItemDTO {
    public String description;
    public double re;
    public double be;
    public String head;
    public String remarks;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRe() {
		return re;
	}
	public void setRe(double re) {
		this.re = re;
	}
	public double getBe() {
		return be;
	}
	public void setBe(double be) {
		this.be = be;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}

