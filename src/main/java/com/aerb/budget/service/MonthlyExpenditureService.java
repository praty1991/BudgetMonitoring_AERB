package com.aerb.budget.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerb.budget.dto.ExpenditureFormDTO;
import com.aerb.budget.entity.MonthlyExpenditure;
import com.aerb.budget.repository.MonthlyExpenditureRepository;

@Service
public class MonthlyExpenditureService {

    @Autowired
    private MonthlyExpenditureRepository repository;

    public void saveExpenditures(ExpenditureFormDTO form) {
        List<MonthlyExpenditure> entries = form.getItems().stream().map(item -> {
            MonthlyExpenditure exp = new MonthlyExpenditure();
            exp.setFinancialYear(form.getFinancialYear());
            exp.setBudgetType(form.getBudgetType());
            exp.setMonth(form.getMonth());
            exp.setCode(item.getCode());
            exp.setHeadOfAccount(item.getHeadOfAccount());
            exp.setAmount(item.getAmount());
            return exp;
        }).collect(Collectors.toList());

        repository.saveAll(entries);
    }
    
    public List<MonthlyExpenditure> getExpendituresByMonthAndYear(String financialYear, int month) {
        return repository.findByFinancialYearAndMonth(financialYear, month);
    }
    
    public boolean isExistsByFinancialYearAndMonth(String financialYear, int month) {
        return repository.existsByFinancialYearAndMonth(financialYear, month);
    }
    
    public Double  sumExpenditureByYearAndType(String financialYear, String type) {
        return repository.sumExpenditureByYearAndType(financialYear, type);
    }

    public List<Double> getMonthlyTotals(String financialYear, String budgetType) {
        financialYear = financialYear.trim().replace("–", "-"); // ← key fix
        budgetType = budgetType.trim();

        List<Object[]> raw = repository.findMonthlyTotals(financialYear, budgetType);

        Map<Integer, Double> monthAmountMap = new HashMap<>();

        for (Object[] row : raw) {
            Integer month = (Integer) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            monthAmountMap.put(month, amount.doubleValue());
        }

        List<Double> monthlyTotals = new ArrayList<>(Collections.nCopies(12, 0.0));
        for (int i = 1; i <= 12; i++) {
            monthlyTotals.set(i - 1, monthAmountMap.getOrDefault(i, 0.0));
        }

        return monthlyTotals;
    }





}
