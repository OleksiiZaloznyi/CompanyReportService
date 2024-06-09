package com.example.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import com.example.demo.model.mongo.ReportDetails;
import com.example.demo.model.sql.Company;
import com.example.demo.model.sql.Report;
import com.example.demo.repository.mongo.ReportDetailsRepository;
import com.example.demo.repository.sql.CompanyRepository;
import com.example.demo.service.ReportDetailsService;
import com.example.demo.service.ReportService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportDetailsControllerTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDetailsService reportDetailsService;

    @Autowired
    private ReportDetailsRepository reportDetailsRepository;

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
    void testGetReportDetails() {
        Report savedReport = reportService.saveReport(testReport);

        ReportDetails reportDetails = createTestReportDetails(savedReport);
        reportDetailsRepository.save(reportDetails);

        ReportDetails foundDetails = reportDetailsService.getReportDetails(savedReport.getId());

        assertThat(foundDetails.getReportId()).isEqualTo(savedReport.getId());
    }

    @Test
    void testCreateReportDetails() {
        Report savedReport = reportService.saveReport(testReport);

        ReportDetails reportDetails = createTestReportDetails(savedReport);
        ReportDetails savedDetails = reportDetailsService.saveReportDetails(reportDetails);

        assertNotNull(savedDetails.getReportId());
        assertThat(savedDetails.getFinancialData().get("key1")).isEqualTo("value1");
        assertThat(savedDetails.getComments()).isEqualTo("Some comments");
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

    private ReportDetails createTestReportDetails(Report report) {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setReportId(report.getId());
        Map<String, Object> financialData = new HashMap<>();
        financialData.put("key1", "value1");
        reportDetails.setFinancialData(financialData);
        reportDetails.setComments("Some comments");
        return reportDetails;
    }
}