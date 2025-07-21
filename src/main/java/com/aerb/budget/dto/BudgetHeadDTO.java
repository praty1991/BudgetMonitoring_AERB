package com.aerb.budget.dto;

public class BudgetHeadDTO {
    private String code;
    private String name;

    public BudgetHeadDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
