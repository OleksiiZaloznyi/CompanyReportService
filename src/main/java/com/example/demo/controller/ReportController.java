package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Report;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/company/{companyId}")
    public List<Report> getAllReportsByCompanyId(@PathVariable UUID companyId) {
        return reportService.getAllReportsByCompanyId(companyId);
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable UUID id) {
        return reportService.getReportById(id);
    }

    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportService.saveReport(report);
    }

    @PutMapping("/{id}")
    public Report updateReport(@PathVariable UUID id, @RequestBody Report report) {
        return reportService.updateReport(id, report);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable UUID id) {
        reportService.deleteReport(id);
    }
}
