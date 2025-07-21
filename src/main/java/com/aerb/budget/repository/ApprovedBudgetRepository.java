package com.aerb.budget.repository;

import com.aerb.budget.entity.ApprovedBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ApprovedBudgetRepository extends JpaRepository<ApprovedBudget, Long> {

    List<ApprovedBudget> findByFinancialYearAndTypeOfBudget(String financialYear, String typeOfBudget);

    @Query("SELECT COUNT(a) > 0 FROM ApprovedBudget a WHERE a.financialYear = :financialYear AND a.typeOfBudget = :typeOfBudget")
    boolean existsByFinancialYearAndTypeOfBudget(String financialYear, String typeOfBudget);
    
    
    @Query("SELECT SUM(a.amount) FROM ApprovedBudget a WHERE a.financialYear = :year AND a.typeOfBudget = :type")
    BigDecimal findTotalApprovedBudget(@Param("year") String financialYear, @Param("type") String budgetType);

}
