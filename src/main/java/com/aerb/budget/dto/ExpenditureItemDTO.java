package com.aerb.budget.dto;

import java.math.BigDecimal;

public class ExpenditureItemDTO {
    private String code;
    private String headOfAccount;
    private BigDecimal amount;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}

