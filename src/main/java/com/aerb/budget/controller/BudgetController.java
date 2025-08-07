package com.aerb.budget.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aerb.budget.dto.BudgetCheckRequest;
import com.aerb.budget.dto.BudgetItemDTO;
import com.aerb.budget.dto.BudgetReportEntryDTO;
import com.aerb.budget.dto.BudgetReportItemDTO;
import com.aerb.budget.dto.BudgetSubmissionDTO;
import com.aerb.budget.dto.DepartmentBudgetDTO;
import com.aerb.budget.entity.BudgetEntry;
import com.aerb.budget.entity.BudgetReportRequest;
import com.aerb.budget.repository.BudgetEntryRepository;
import com.aerb.budget.repository.BudgetHeadRepository;
import com.aerb.budget.service.BudgetService;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetHeadRepository budgetHeadRepository;

    @Autowired
    private BudgetEntryRepository repository;

    @GetMapping("/head-accounts")
    public List<String> getHeadAccounts() {
        logger.info("Fetching all head account names");
        return budgetHeadRepository.findAllNames();
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitBudget(@RequestBody BudgetSubmissionDTO submission) {
        logger.info("Submitting budget for division: {}, year: {}", submission.getDivision(), submission.getFinancialYear());
        try {
            budgetService.saveBudget(submission);
            logger.info("Budget submitted successfully for division: {}", submission.getDivision());
            return ResponseEntity.ok(Map.of("message", "Budget submitted successfully."));
        } catch (RuntimeException ex) {
            logger.error("Failed to submit budget for division: {}, error: {}", submission.getDivision(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/report/division")
    public List<BudgetEntry> getDivisionReport(@RequestParam String division, @RequestParam String year) {
        logger.info("Fetching division report for division: {}, year: {}", division, year);
        return budgetService.getDivisionReport(division, year);
    }

    @PostMapping("/report")
    public ResponseEntity<List<BudgetReportItemDTO>> getBudgetReport(@RequestBody BudgetReportRequest request) {
        logger.info("Generating report for Division: {}, Type: {}, Year: {}",
                request.getDivision(), request.getBudgetType(), request.getFinancialYear());

        List<BudgetReportItemDTO> report = budgetService.getReport(
                request.getDivision(),
                request.getBudgetType(),
                request.getFinancialYear()
        );
        logger.info("Report generated successfully. Entries: {}", report.size());
        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/consolidated")
    public List<BudgetEntry> getConsolidatedReport(@RequestParam String year) {
        logger.info("Fetching consolidated report for year: {}", year);
        return budgetService.getConsolidatedReport(year);
    }

    @GetMapping("/types")
    public List<String> getBudgetTypes() {
        logger.info("Fetching all budget types");
        return List.of("Capital", "Revenue");
    }

    @GetMapping("/financial-years")
    public List<String> getFinancialYears() {
        logger.info("Fetching list of financial years");
        return List.of("2024-25", "2025-26", "2026-27");
    }

    @GetMapping("/report-types")
    public List<String> getReportTypes() {
        logger.info("Fetching available report types (RE/BE)");
        return List.of("RE", "BE");
    }

    @GetMapping("/getreport")
    public List<BudgetReportEntryDTO> getReport(
            @RequestParam String year,
            @RequestParam String type,
            @RequestParam String reportType) {
        logger.info("Fetching raw report data for Year: {}, Type: {}, Report Type: {}", year, type, reportType);
        return repository.findReportByYearAndType(year, type, reportType);
    }

    @GetMapping("/modes")
    public List<String> getReportModes() {
        logger.info("Fetching available report modes");
        return List.of("Divisional Specific", "Consolidated");
    }

    @GetMapping("/headwise-be")
    public Map<String, Integer> getHeadwiseBE(@RequestParam String year) {
        logger.info("Fetching head-wise BE for year: {}", year);
        List<Map<String, Object>> raw = repository.getHeadwiseBE(year);

        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> row : raw) {
            String head = (String) row.get("head");
            Number total = (Number) row.get("total");
            result.put(head, total != null ? total.intValue() : 0);
        }

        logger.info("Head-wise BE report generated with {} entries", result.size());
        return result;
    }
    
    @PostMapping("/check-duplicate")
    public boolean checkDuplicateBudget(@RequestBody BudgetCheckRequest criteria) {
        return repository.existsByDivision_NameAndBudgetTypeAndFinancialYear(
            criteria.getDivision(), criteria.getBudgetType(), criteria.getFinancialYear()
        );
    }

    @GetMapping("/total-estimated")
    public ResponseEntity<BigDecimal> getEstimatedBudget(
            @RequestParam String year,
            @RequestParam String type) {
        BigDecimal total = budgetService.getTotalEstimatedBudget(year, type);
        return ResponseEntity.ok(total != null ? total : BigDecimal.ZERO);
    }

    
    @GetMapping("/departments")
    public List<DepartmentBudgetDTO> getDepartmentBudgets(
            @RequestParam String year,
            @RequestParam String type
    ) {
        return budgetService.getDepartmentBudgetSummary(year, type);
    }

}
