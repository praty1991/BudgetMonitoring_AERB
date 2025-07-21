package com.aerb.budget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aerb.budget.entity.MonthlyExpenditure;

public interface MonthlyExpenditureRepository extends JpaRepository<MonthlyExpenditure, Long> {
    List<MonthlyExpenditure> findByFinancialYearAndMonth(String financialYear, int month);
    
    boolean existsByFinancialYearAndMonth(String financialYear, int month);
}
