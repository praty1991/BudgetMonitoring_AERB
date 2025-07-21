package com.aerb.budget.service;

import java.util.List;
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

}
