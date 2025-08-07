package com.aerb.budget.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerb.budget.dto.ExpenditureReportDto;
import com.aerb.budget.repository.ExpenditureRepository;

@Service
public class ExpenditureService {

    @Autowired
    private ExpenditureRepository repository;

    public List<ExpenditureReportDto> getReport(String year, int month) {
        List<Map<String, Object>> rawResults = repository.getExpenditureReport(year, month);
        List<ExpenditureReportDto> reports = new ArrayList<>();

        for (Map<String, Object> row : rawResults) {
            ExpenditureReportDto dto = new ExpenditureReportDto();
            dto.setCode((String) row.get("code"));
            dto.setHeadOfAccount((String) row.get("headOfAccount"));
            dto.setBe(((Number) row.get("be")).doubleValue());
            dto.setMonthlyExpenditure(((Number) row.get("monthlyExpenditure")).doubleValue());
            dto.setProgressiveRupees(((Number) row.get("progressiveRupees")).doubleValue());
            dto.setExpenditureLakhs(((Number) row.get("expenditureLakhs")).doubleValue());
            dto.setProgressiveLakhs(((Number) row.get("progressiveLakhs")).doubleValue());
            dto.setPercentageOfBE(row.get("percentageOfBE") != null ? ((Number) row.get("percentageOfBE")).doubleValue() : 0.0);
            dto.setBalance(((Number) row.get("balance")).doubleValue());
            reports.add(dto);
        }

        return reports;
    }
}
