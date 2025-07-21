package com.aerb.budget.repository;

import java.math.BigDecimal;
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

}

