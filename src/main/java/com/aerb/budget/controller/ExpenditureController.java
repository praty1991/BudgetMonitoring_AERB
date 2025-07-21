package com.aerb.budget.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aerb.budget.dto.BudgetHeadDTO;
import com.aerb.budget.dto.ExpenditureFormDTO;
import com.aerb.budget.dto.ExpenditureReportDto;
import com.aerb.budget.entity.MonthlyExpenditure;
import com.aerb.budget.repository.BudgetEntryRepository;
import com.aerb.budget.repository.BudgetHeadRepository;
import com.aerb.budget.service.BudgetService;
import com.aerb.budget.service.ExpenditureService;
import com.aerb.budget.service.MonthlyExpenditureService;

@RestController
@RequestMapping("/api/budget")
public class ExpenditureController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private MonthlyExpenditureService service;
    
    @Autowired
    private ExpenditureService serviceExp;

    @Autowired
    private BudgetHeadRepository budgetHeadRepository;

    @Autowired
    private BudgetEntryRepository repository;
    
    
    @GetMapping("/budget-heads")
    public List<BudgetHeadDTO> getAllHeads() {
        return budgetHeadRepository.findAll().stream()
                .map(h -> new BudgetHeadDTO("34010010101"+h.getCode(), h.getName()))
                .collect(Collectors.toList());
    }


    @PostMapping("/save-expenditure")
    public ResponseEntity<String> save(@RequestBody ExpenditureFormDTO form) {
        service.saveExpenditures(form);
        return ResponseEntity.ok("Saved successfully");
    }

    @GetMapping("/get-expenditure")
    public List<MonthlyExpenditure> getByMonthAndYear(
        @RequestParam String year,
        @RequestParam int month
    ) {
        return service.getExpendituresByMonthAndYear(year, month);
    }

    
    @GetMapping("/expenditures/check-duplicate")
    public ResponseEntity<Boolean> checkDuplicate(
        @RequestParam String year,
        @RequestParam int month) {
      boolean exists = service.isExistsByFinancialYearAndMonth(year, month);
      return ResponseEntity.ok(exists);
    }

    
    @GetMapping("/expenditure-report")
    public ResponseEntity<List<ExpenditureReportDto>> getReport(
        @RequestParam String year,
        @RequestParam int month
    ) {
        return ResponseEntity.ok(serviceExp.getReport(year, month));
    }
    }

