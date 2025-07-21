package com.aerb.budget.dto;
public class BudgetReportItemDTO {
    private int serialNo;
    private String head;          // Head of Account
    private String description;   // Item Description
    private double re;            // RE 2025-26
    private double be;            // BE 2026-27
    private String remarks;
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    // Getters and setters
}
