package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.example.demo.model.mongo.ReportDetails;
import com.example.demo.model.sql.Company;
import com.example.demo.model.sql.Report;
import com.example.demo.repository.mongo.ReportDetailsRepository;
import com.example.demo.repository.sql.CompanyRepository;
import com.example.demo.repository.sql.ReportRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportDetailsServiceTest {
    @Autowired
    private ReportService reportService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportDetailsService reportDetailsService;

    @Autowired
    private ReportDetailsRepository reportDetailsRepository;

    private Report testReport;
    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = createTestCompany("Test Company", "REG67890", "789 Delete Street");
        companyRepository.save(testCompany);
        testReport = createTestReport(testCompany, new BigDecimal("1000.00"), new BigDecimal("200.00"));
    }

    @Test
    void testGetReportDetails() {
        Report savedReport = reportService.saveReport(testReport);

        ReportDetails reportDetails = createTestReportDetails(savedReport.getId(), "Some comments", "key1", "value1");
        reportDetailsRepository.save(reportDetails);

        ReportDetails foundDetails = reportDetailsService.getReportDetails(savedReport.getId());

        assertNotNull(foundDetails);
        assertThat(foundDetails.getReportId()).isEqualTo(savedReport.getId());
        assertThat(foundDetails.getFinancialData().get("key1")).isEqualTo("value1");
        assertThat(foundDetails.getComments()).isEqualTo("Some comments");
    }

    @Test
    void testSaveReportDetails() {
        Report savedReport = reportService.saveReport(testReport);

        ReportDetails reportDetails = createTestReportDetails(savedReport.getId(), "Some comments", "key1", "value1");

        ReportDetails savedDetails = reportDetailsService.saveReportDetails(reportDetails);

        assertNotNull(savedDetails.getReportId());
        assertThat(savedDetails.getReportId()).isEqualTo(savedReport.getId());
        assertThat(savedDetails.getFinancialData().get("key1")).isEqualTo("value1");
        assertThat(savedDetails.getComments()).isEqualTo("Some comments");

        ReportDetails foundDetails = reportDetailsRepository.findByReportId(savedDetails.getReportId());
        assertNotNull(foundDetails);
        assertThat(foundDetails.getReportId()).isEqualTo(savedReport.getId());
        assertThat(foundDetails.getFinancialData().get("key1")).isEqualTo("value1");
        assertThat(foundDetails.getComments()).isEqualTo("Some comments");
    }

    private Company createTestCompany(String name, String registrationNumber, String address) {
        Company company = new Company();
        company.setName(name);
        company.setRegistrationNumber(registrationNumber);
        company.setAddress(address);
        company.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return company;
    }

    private Report createTestReport(Company company, BigDecimal totalRevenue, BigDecimal netProfit) {
        Report report = new Report();
        report.setCompany(company);
        report.setReportDate(new Timestamp(System.currentTimeMillis()));
        report.setTotalRevenue(totalRevenue);
        report.setNetProfit(netProfit);
        return report;
    }

    private ReportDetails createTestReportDetails(UUID reportId, String comments, String key, String value) {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setReportId(reportId);
        Map<String, Object> financialData = new HashMap<>();
        financialData.put(key, value);
        reportDetails.setFinancialData(financialData);
        reportDetails.setComments(comments);
        return reportDetails;
    }
}