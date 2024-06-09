package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import com.example.demo.model.sql.Company;
import com.example.demo.model.sql.Report;
import com.example.demo.repository.mongo.ReportDetailsRepository;
import com.example.demo.repository.sql.CompanyRepository;
import com.example.demo.repository.sql.ReportRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReportDetailsRepository reportDetailsRepository;

    private Company testCompany;
    private Report testReport;

    @BeforeEach
    void setUp() {
        testCompany = createTestCompany("Test Company", "REG67890", "789 Delete Street");
        companyRepository.save(testCompany);

        testReport = createTestReport(testCompany, new BigDecimal("1000.00"), new BigDecimal("200.00"));
    }

    @Test
    void getAllReportsByCompanyId() {
        reportService.saveReport(testReport);

        List<Report> reports = reportService.getAllReportsByCompanyId(testCompany.getId());

        assertThat(reports).isNotEmpty().contains(testReport);
    }

    @Test
    void getReportById() {
        Report savedReport = reportService.saveReport(testReport);

        Report foundReport = reportService.getReportById(savedReport.getId());

        assertNotNull(foundReport);
        assertThat(foundReport.getId()).isEqualTo(savedReport.getId());
        assertThat(foundReport.getTotalRevenue()).isEqualTo(new BigDecimal("1000.00"));
    }

    @Test
    void saveReport() {
        Report savedReport = reportService.saveReport(testReport);

        assertNotNull(savedReport.getId());
        assertThat(savedReport.getTotalRevenue()).isEqualTo(new BigDecimal("1000.00"));
    }

    @Test
    void updateReport() {
        Report savedReport = reportService.saveReport(testReport);

        savedReport.setTotalRevenue(new BigDecimal("1500.00"));
        Report updatedReport = reportService.updateReport(savedReport.getId(), savedReport);

        assertThat(updatedReport.getTotalRevenue()).isEqualTo(new BigDecimal("1500.00"));
    }

    @Test
    void deleteReport() {
        Report savedReport = reportService.saveReport(testReport);

        reportService.deleteReport(savedReport.getId());

        Optional<Report> deletedReport = reportRepository.findById(savedReport.getId());
        assertFalse(deletedReport.isPresent());
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
}