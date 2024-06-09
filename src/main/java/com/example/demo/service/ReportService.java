package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Company;
import com.example.demo.model.sql.Report;
import com.example.demo.repository.sql.CompanyRepository;
import com.example.demo.repository.sql.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public List<Report> getAllReportsByCompanyId(UUID companyId) {
        return reportRepository.findByCompanyId(companyId);
    }

    public Report getReportById(UUID id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public Report saveReport(Report report) {
        Company company = companyRepository.findById(report.getCompany().getId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        report.setCompany(company);
        return reportRepository.save(report);
    }

    public Report updateReport(UUID id, Report report) {
        Report existingReport = getReportById(id);
        if (existingReport == null) {
            throw new RuntimeException("Report not found with id: " + id);
        }

        existingReport.setCompany(report.getCompany());
        existingReport.setReportDate(report.getReportDate());
        existingReport.setTotalRevenue(report.getTotalRevenue());
        existingReport.setNetProfit(report.getNetProfit());
        return saveReport(existingReport);
    }

    public void deleteReport(UUID id) {
        reportRepository.deleteById(id);
    }
}
