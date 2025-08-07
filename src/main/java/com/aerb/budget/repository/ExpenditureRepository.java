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
		        ROUND(SUM(ab.amount) / 100000.0, 2) AS be,
		        ROUND(SUM(CASE WHEN me.month = :month THEN me.amount ELSE 0 END), 2) AS monthlyExpenditure,
		        ROUND(SUM(CASE WHEN me.month <= :month THEN me.amount ELSE 0 END), 2) AS progressiveRupees,
		        ROUND(SUM(CASE WHEN me.month = :month THEN me.amount ELSE 0 END) / 100000.0, 2) AS expenditureLakhs,
		        ROUND(SUM(CASE WHEN me.month <= :month THEN me.amount ELSE 0 END) / 100000.0, 2) AS progressiveLakhs,
		        ROUND(
		            (SUM(CASE WHEN me.month <= :month THEN me.amount ELSE 0 END) / 100000.0)
		            / NULLIF(SUM(ab.amount) / 100000.0, 0) * 100,
		            2
		        ) AS percentageOfBE,
		        ROUND(
		            SUM(ab.amount) / 100000.0 
		            - (SUM(CASE WHEN me.month <= :month THEN me.amount ELSE 0 END) / 100000.0), 
		            2
		        ) AS balance
		    FROM approved_budget ab
		    JOIN budget_heads bh ON RIGHT(ab.code, LENGTH(bh.code)) = bh.code
		    LEFT JOIN monthly_expenditures me 
		        ON RIGHT(me.code, LENGTH(bh.code)) = bh.code
		        AND REPLACE(me.financial_year, '-', '') = REPLACE(:year, '-', '')
		        AND me.budget_type = 'Capital'
		    WHERE REPLACE(ab.financial_year, '-', '') = REPLACE(:year, '-', '')
		      AND ab.type_of_budget = 'Capital'
		    GROUP BY bh.code, bh.name
		    ORDER BY bh.code
		""", nativeQuery = true)
		List<Map<String, Object>> getExpenditureReport(@Param("year") String year, @Param("month") int month);


}
