package com.aerb.budget.entity;

import java.util.List;

import lombok.Data;

@Data
public class BudgetSubmission {
    private String division;
    private String type;
    private String budgetType;
    private List<BudgetItem> items;
}
