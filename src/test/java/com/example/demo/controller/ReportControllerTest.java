package com.example.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Company;
import com.example.demo.model.sql.Report;
import com.example.demo.repository.sql.CompanyRepository;
import com.example.demo.repository.sql.ReportRepository;
import com.example.demo.service.ReportService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportControllerTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company testCompany;
    private Report testReport;

    @BeforeEach
    void setUp() {
        testCompany = createTestCompany("Test Company", "REG67890", "789 Delete Street");
        testReport = createTestReport(testCompany);
    }

    @Test
    void testGetAllReportsByCompanyId() {
        Report report = createTestReport(testCompany);
        reportService.saveReport(report);

        List<Report> reports = reportService.getAllReportsByCompanyId(testCompany.getId());

        assertThat(reports).contains(report);
    }

    @Test
    void testGetReportById() {
        Report savedReport = reportService.saveReport(testReport);

        Report foundReport = reportService.getReportById(savedReport.getId());

        assertEquals(savedReport.getId(), foundReport.getId());
    }

    @Test
    void testCreateReport() {
        Report savedReport = reportService.saveReport(testReport);

        assertNotNull(savedReport.getId());
    }

    @Test
    void testUpdateReport() {
        Report savedReport = reportService.saveReport(testReport);

        savedReport.setTotalRevenue(new BigDecimal("1500.00"));
        Report updatedReport = reportService.updateReport(savedReport.getId(), savedReport);

        Report foundReport = reportService.getReportById(savedReport.getId());

        assertEquals(new BigDecimal("1500.00"), foundReport.getTotalRevenue());
    }

    @Test
    void testDeleteReport() {
        Report savedReport = reportService.saveReport(testReport);

        UUID reportId = savedReport.getId();
        reportService.deleteReport(reportId);

        assertFalse(reportRepository.findById(reportId).isPresent());
    }

    private Company createTestCompany(String name, String regNumber, String address) {
        Company company = new Company();
        company.setName(name);
        company.setRegistrationNumber(regNumber);
        company.setAddress(address);
        company.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return companyRepository.save(company);
    }

    private Report createTestReport(Company company) {
        Report report = new Report();
        report.setCompany(company);
        report.setReportDate(new Timestamp(System.currentTimeMillis()));
        report.setTotalRevenue(new BigDecimal("1000.00"));
        report.setNetProfit(new BigDecimal("200.00"));
        return report;
    }
}