package com.aerb.budget.service;

import com.aerb.budget.dto.ApprovedBudgetRequest;
import com.aerb.budget.entity.ApprovedBudget;
import com.aerb.budget.repository.ApprovedBudgetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ApprovedBudgetService {

    private final ApprovedBudgetRepository repository;

    public ApprovedBudgetService(ApprovedBudgetRepository repository) {
        this.repository = repository;
    }

    public boolean isDuplicate(String year, String typeOfBudget) {
        return repository.existsByFinancialYearAndTypeOfBudget(year, typeOfBudget);
    }

    public void saveApprovedBudgets(ApprovedBudgetRequest request) {
        List<ApprovedBudget> entries = request.getItems().stream().map(item -> {
            ApprovedBudget ab = new ApprovedBudget();
            ab.setFinancialYear(request.getFinancialYear());
            ab.setTypeOfBudget(request.getBudgetType()); // ✅ fixed missing semicolon
            ab.setCode(item.getCode());
            ab.setHeadOfAccount(item.getHeadOfAccount());
            ab.setAmount(item.getAmount());
            return ab;
        }).toList();

        repository.saveAll(entries);
    }

    public List<ApprovedBudget> getReport(String year, String typeOfBudget) {
        return repository.findByFinancialYearAndTypeOfBudget(year, typeOfBudget);
    }
    
    public BigDecimal getTotalApprovedBudget(String financialYear, String budgetType) {
        return repository.findTotalApprovedBudget(financialYear, budgetType);
    }

}
