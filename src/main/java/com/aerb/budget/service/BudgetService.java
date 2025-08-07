package com.aerb.budget.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerb.budget.dto.BudgetItemDTO;
import com.aerb.budget.dto.BudgetReportItemDTO;
import com.aerb.budget.dto.BudgetSubmissionDTO;
import com.aerb.budget.dto.DepartmentBudgetDTO;
import com.aerb.budget.entity.BudgetEntry;
import com.aerb.budget.entity.BudgetHead;
import com.aerb.budget.entity.Division;
import com.aerb.budget.repository.BudgetEntryRepository;
import com.aerb.budget.repository.BudgetHeadRepository;
import com.aerb.budget.repository.DivisionRepository;

@Service
public class BudgetService {

    @Autowired private DivisionRepository divisionRepo;
    @Autowired private BudgetHeadRepository headRepo;
    @Autowired private BudgetEntryRepository entryRepo;

    public void saveBudget(BudgetSubmissionDTO submission) {
        Division division = divisionRepo.findByName(submission.division)
            .orElseThrow(() -> new RuntimeException("Division not found"));

        for (BudgetItemDTO item : submission.items) {
            BudgetHead head = headRepo.findByName(item.head)
                .orElseThrow(() -> new RuntimeException("Head not found: " + item.head));

            BudgetEntry entry = new BudgetEntry();
            entry.setDivision(division);
            entry.setHead(head);
            entry.setItemDescription(item.description);
            entry.setReValue(item.re);
            entry.setBeValue(item.be);
            entry.setRemarks(item.remarks);
            entry.setFinancialYear(submission.financialYear);
            entry.setBudgetType(submission.budgetType);
            System.out.print("pratendra");
            entryRepo.save(entry);
        }
    }

    public List<BudgetEntry> getDivisionReport(String division, String financialYear) {
        return entryRepo.findByDivisionNameAndFinancialYear(division, financialYear);
    }

    public List<BudgetEntry> getConsolidatedReport(String financialYear) {
        return entryRepo.findByFinancialYear(financialYear);
    }
    
    public List<BudgetReportItemDTO> getReport(String division, String budgetType, String financialYear) {
        List<BudgetEntry> entries = entryRepo
            .findReportEntries(division, budgetType, financialYear);

        AtomicInteger counter = new AtomicInteger(1);
        return entries.stream().map(entry -> {
            BudgetReportItemDTO dto = new BudgetReportItemDTO();
            dto.setSerialNo(counter.getAndIncrement());
            dto.setHead(entry.getHead().getName()); // assuming entry.getHead() gives BudgetHead entity
            dto.setDescription(entry.getItemDescription());
            dto.setRe(entry.getReValue());
            dto.setBe(entry.getBeValue());
            dto.setRemarks(entry.getRemarks());
            return dto;
        }).collect(Collectors.toList());
    }


    public BigDecimal getTotalEstimatedBudget(String financialYear, String budgetType) {
        return entryRepo.findTotalEstimatedBudget(financialYear, budgetType);
    }
    
    public List<DepartmentBudgetDTO> getDepartmentBudgetSummary(String year, String type) {
        List<Object[]> rows = divisionRepo.getDepartmentBudgetSummary(year, type);
        List<DepartmentBudgetDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            String name = (String) row[0];
            double estimated = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
            double approved = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
            double expenditure = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;

            result.add(new DepartmentBudgetDTO(name, estimated, approved, expenditure));
        }

        return result;
    }

}
