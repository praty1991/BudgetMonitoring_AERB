package com.aerb.budget.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aerb.budget.entity.Division;

public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT SUM(a.amount) FROM ApprovedBudget a WHERE a.financialYear = :year AND a.typeOfBudget = :type")
    BigDecimal findTotalApprovedBudget(@Param("year") String financialYear, @Param("type") String budgetType);
    
    
    @Query(value = """
            SELECT 
                bh.name AS name,
                COALESCE(SUM(be.re_value), 0) AS estimated,
                COALESCE(SUM(ab.amount), 0) AS approved,
                COALESCE(SUM(me.amount), 0) AS expenditure
            FROM budget_heads bh
            LEFT JOIN budget_entries be 
                ON bh.id = be.head_id AND be.financial_year = :year AND be.budget_type = :type
            LEFT JOIN approved_budget ab 
                ON bh.name = ab.head_of_account AND ab.financial_year = :year AND ab.type_of_budget = :type
            LEFT JOIN monthly_expenditures me 
                ON bh.name = me.head_of_account AND me.financial_year = :year AND me.budget_type = :type
            GROUP BY bh.name
            ORDER BY bh.name
        """, nativeQuery = true)
        List<Object[]> getDepartmentBudgetSummary(@Param("year") String year, @Param("type") String type);


}

