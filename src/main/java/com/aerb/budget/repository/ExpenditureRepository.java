package com.aerb.budget.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aerb.budget.entity.BudgetEntry;

@Repository
public interface ExpenditureRepository extends JpaRepository<BudgetEntry, Long> {

    @Query(value = """
        SELECT 
            bh.code AS code,
            bh.name AS headOfAccount,
            ROUND(SUM(be.be_value), 2) AS be,
            ROUND(SUM(IF(me.month = :month, me.amount, 0)), 2) AS monthlyExpenditure,
            ROUND(SUM(IF(me.month <= :month, me.amount, 0)), 2) AS progressiveRupees,
            ROUND(SUM(IF(me.month = :month, me.amount, 0)) / 100000.0, 2) AS expenditureLakhs,
            ROUND(SUM(IF(me.month <= :month, me.amount, 0)) / 100000.0, 2) AS progressiveLakhs,
            ROUND(
                (SUM(IF(me.month <= :month, me.amount, 0)) / 100000.0) 
                / NULLIF(SUM(be.be_value), 0) * 100,
                2
            ) AS percentageOfBE,
            ROUND(SUM(be.be_value) - (SUM(IF(me.month <= :month, me.amount, 0)) / 100000.0), 2) AS balance
        FROM budget_entries be
        JOIN budget_heads bh ON be.head_id = bh.id
        LEFT JOIN monthly_expenditures me 
            ON RIGHT(me.code, LENGTH(bh.code)) = bh.code
            AND REPLACE(me.financial_year, '-', '') = REPLACE(:year, '-', '')
        WHERE REPLACE(be.financial_year, '-', '') = REPLACE(:year, '-', '')
          AND be.budget_type = 'Capital'
        GROUP BY bh.code, bh.name
        ORDER BY bh.code
    """, nativeQuery = true)
    List<Map<String, Object>> getExpenditureReport(@Param("year") String year, @Param("month") int month);
}
