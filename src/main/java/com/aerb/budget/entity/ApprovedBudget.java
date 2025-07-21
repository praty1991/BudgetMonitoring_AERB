package com.aerb.budget.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approved_budget")
public class ApprovedBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String financialYear;

    @Column(name = "type_of_budget")
    private String typeOfBudget; // e.g., "RE" or "BE"

    private String code;

    private String headOfAccount;

    private double amount;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getTypeOfBudget() {
        return typeOfBudget;
    }

    public void setTypeOfBudget(String typeOfBudget) {
        this.typeOfBudget = typeOfBudget;
    }

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
