package com.aerb.budget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aerb.budget.entity.MonthlyExpenditure;

public interface MonthlyExpenditureRepository extends JpaRepository<MonthlyExpenditure, Long> {
    List<MonthlyExpenditure> findByFinancialYearAndMonth(String financialYear, int month);
    
    boolean existsByFinancialYearAndMonth(String financialYear, int month);
    
    @Query("SELECT SUM(m.amount) FROM MonthlyExpenditure m WHERE m.financialYear = :year AND m.budgetType = :type")
    Double sumExpenditureByYearAndType(@Param("year") String year, @Param("type") String type);
    
    @Query(value = "SELECT m.month, SUM(m.amount) FROM monthly_expenditures m " +
            "WHERE m.financial_year = :year AND m.budget_type = :budgetType " +
            "GROUP BY m.month ORDER BY m.month", nativeQuery = true)
List<Object[]> findMonthlyTotals(@Param("year") String year, @Param("budgetType") String budgetType);



}
