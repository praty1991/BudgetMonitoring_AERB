package com.aerb.budget.controller;

import com.aerb.budget.dto.ApprovedBudgetRequest;
import com.aerb.budget.entity.ApprovedBudget;
import com.aerb.budget.service.ApprovedBudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budget")
public class ApprovedBudgetController {

    private static final Logger logger = LoggerFactory.getLogger(ApprovedBudgetController.class);

    private final ApprovedBudgetService service;

    public ApprovedBudgetController(ApprovedBudgetService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveApprovedBudgets(@RequestBody ApprovedBudgetRequest request) {
        logger.info("Received request to save approved budget: {}", request);
        try {
            service.saveApprovedBudgets(request);
            logger.info("Approved budget saved successfully.");
            return ResponseEntity.ok(Map.of("message", "Saved successfully"));
        } catch (Exception e) {
            logger.error("Error while saving approved budget: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save approved budget");
        }
    }

    @GetMapping("/check-duplicate")
    public ResponseEntity<Boolean> checkDuplicate(
            @RequestParam String year,
            @RequestParam String budgetType
    ) {
        logger.info("Checking for duplicate entry for year: {}, typeOfBudget: {}", year, budgetType);
        try {
            boolean exists = service.isDuplicate(year, budgetType);
            logger.info("Duplicate check result for year={}, typeOfBudget={} => {}", year, budgetType, exists);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            logger.error("Error during duplicate check: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/report")
    public ResponseEntity<List<ApprovedBudget>> getReport(
            @RequestParam String year,
            @RequestParam String budgetType
    ) {
        logger.info("Fetching report for year: {}, typeOfBudget: {}", year, budgetType);
        try {
            List<ApprovedBudget> report = service.getReport(year, budgetType);
            logger.info("Report fetched successfully. Entries found: {}", report.size());
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            logger.error("Error while fetching report: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/total-approved")
    public ResponseEntity<BigDecimal> getApprovedBudget(
            @RequestParam String year,
            @RequestParam String type) {
        BigDecimal total = service.getTotalApprovedBudget(year, type);
        return ResponseEntity.ok(total != null ? total : BigDecimal.ZERO);
    }

}
