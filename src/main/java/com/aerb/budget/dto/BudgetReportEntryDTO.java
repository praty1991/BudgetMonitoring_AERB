package com.aerb.budget.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class BudgetReportEntryDTO {

    private Long headId;
    private String headName;
    private String headCode;

    private Long divisionId;
    private String divisionName;

    private Double value;

    public BudgetReportEntryDTO(Long headId, String headName, String headCode,
                                Long divisionId, String divisionName, Double value) {
        this.headId = headId;
        this.headName = headName;
        this.headCode = headCode;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.value = value;
    }

    // Getters and setters

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadCode() {
        return headCode;
    }

    public void setHeadCode(String headCode) {
        this.headCode = headCode;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
