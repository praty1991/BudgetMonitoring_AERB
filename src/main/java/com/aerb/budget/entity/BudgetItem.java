package com.aerb.budget.entity;

import lombok.Data;

@Data
public class BudgetItem {
    private String description;
    private double re;
    private double be;
    private String head;
    private String remarks;
}

