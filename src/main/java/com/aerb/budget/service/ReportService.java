package com.aerb.budget.service;
import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ReportService {
    byte[] generateDivisionSpecificReport(String year, String type, String division, String format);
    byte[] generateConsolidatedReport(String year, String type, String reportType, String format);
}