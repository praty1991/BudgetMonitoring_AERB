package com.aerb.budget.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aerb.budget.entity.BudgetHead;

@Repository
public interface BudgetHeadRepository extends JpaRepository<BudgetHead, Long> {

    @Query("SELECT b.name FROM BudgetHead b")
    List<String> findAllNames();
    
    @Query("SELECT b FROM BudgetHead b WHERE b.name = ?1")
    Optional<BudgetHead> findByName(String name);
}

