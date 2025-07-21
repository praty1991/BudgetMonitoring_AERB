package com.aerb.budget.repository;

import com.aerb.budget.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    List<Process> findByParentIsNull();
    List<Process> findByParentId(Long parentId);
    Optional<Process> findByNameIgnoreCase(String name);
    List<Process> findByParentIsNullAndIsActiveTrue();

}
