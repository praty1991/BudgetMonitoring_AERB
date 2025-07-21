package com.aerb.budget.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aerb.budget.dto.BudgetReportEntryDTO;
import com.aerb.budget.entity.BudgetEntry;

public interface BudgetEntryRepository extends JpaRepository<BudgetEntry, Long> {
    List<BudgetEntry> findByDivisionNameAndFinancialYear(String division, String financialYear);
    List<BudgetEntry> findByFinancialYear(String financialYear);
    

    @Query("SELECT e FROM BudgetEntry e WHERE e.division.name = :division AND e.budgetType = :budgetType AND e.financialYear = :financialYear")
    List<BudgetEntry> findReportEntries(@Param("division") String division,
                                        @Param("budgetType") String budgetType,
                                        @Param("financialYear") String financialYear);
    
    @Query("""
    	    SELECT new com.aerb.budget.dto.BudgetReportEntryDTO(
    	        b.head.id, b.head.name, b.head.code,
    	        b.division.id, b.division.name,
    	        CASE WHEN :reportType = 'BE' THEN b.beValue ELSE b.reValue END
    	    )
    	    FROM BudgetEntry b
    	    WHERE b.financialYear = :year AND b.budgetType = :type
    	""")
    	List<BudgetReportEntryDTO> findReportByYearAndType(String year, String type, String reportType);
    
    
    
    @Query("SELECT new map(h.name as head, SUM(b.beValue) as total) " +
    	       "FROM BudgetEntry b JOIN b.head h " +
    	       "WHERE b.financialYear = :year " +
    	       "GROUP BY h.name")
    	List<Map<String, Object>> getHeadwiseBE(@Param("year") String year);
    
    
    boolean existsByDivision_NameAndBudgetTypeAndFinancialYear(
    		  String divisionName, String budgetType, String financialYear
    		);



    @Query("SELECT SUM(beValue) FROM BudgetEntry b WHERE b.financialYear = :year AND b.budgetType = :type")
    BigDecimal findTotalEstimatedBudget(@Param("year") String financialYear, @Param("type") String budgetType);

}
