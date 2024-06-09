package com.example.demo.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Company;
import com.example.demo.repository.sql.CompanyRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    private Company testCompany1;
    private Company testCompany2;

    @BeforeEach
    void setUp() {
        testCompany1 = createTestCompany("Test Company 1", "12345", "Address 1");
        testCompany2 = createTestCompany("Test Company 2", "67890", "Address 2");
    }

    @Test
    void testGetAllCompanies() {
        companyService.saveCompany(testCompany1);
        companyService.saveCompany(testCompany2);

        List<Company> companies = companyService.getAllCompanies();

        assertThat(companies).hasSizeGreaterThanOrEqualTo(2);
        assertThat(companies).extracting(Company::getName)
                .contains("Test Company 1", "Test Company 2");
    }

    @Test
    void testGetCompanyById() {
        Company savedCompany = companyService.saveCompany(testCompany1);

        Company retrievedCompany = companyService.getCompanyById(savedCompany.getId());

        assertThat(retrievedCompany).isNotNull();
        assertThat(retrievedCompany.getId()).isEqualTo(savedCompany.getId());
        assertThat(retrievedCompany.getName()).isEqualTo(testCompany1.getName());
        assertThat(retrievedCompany.getRegistrationNumber()).isEqualTo(testCompany1.getRegistrationNumber());
        assertThat(retrievedCompany.getAddress()).isEqualTo(testCompany1.getAddress());
    }

    @Test
    void testSaveCompany() {
        Company savedCompany = companyService.saveCompany(testCompany1);

        assertNotNull(savedCompany.getId());
        assertEquals(testCompany1.getName(), savedCompany.getName());
    }

    @Test
    void testUpdateCompany() {
        Company savedCompany = companyService.saveCompany(testCompany1);

        UUID companyId = savedCompany.getId();
        Company updatedCompany = new Company();
        updatedCompany.setId(companyId);
        updatedCompany.setName("Updated Company");
        updatedCompany.setRegistrationNumber("REG54321");
        updatedCompany.setAddress("456 Updated Street");
        updatedCompany.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Company result = companyService.updateCompany(companyId, updatedCompany);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(companyId);
        assertThat(result.getName()).isEqualTo("Updated Company");
        assertThat(result.getRegistrationNumber()).isEqualTo("REG54321");
        assertThat(result.getAddress()).isEqualTo("456 Updated Street");

        Company retrievedCompany = companyService.getCompanyById(companyId);
        assertThat(retrievedCompany).isNotNull();
        assertThat(retrievedCompany.getId()).isEqualTo(companyId);
        assertThat(retrievedCompany.getName()).isEqualTo("Updated Company");
        assertThat(retrievedCompany.getRegistrationNumber()).isEqualTo("REG54321");
        assertThat(retrievedCompany.getAddress()).isEqualTo("456 Updated Street");
    }

    @Test
    void testDeleteCompany() {
        Company savedCompany = companyService.saveCompany(testCompany1);

        UUID companyId = savedCompany.getId();
        companyService.deleteCompany(companyId);

        assertFalse(companyRepository.existsById(companyId));
        assertThat(companyService.getAllCompanies()).doesNotContain(savedCompany);
    }

    private Company createTestCompany(String name, String registrationNumber, String address) {
        Company company = new Company();
        company.setName(name);
        company.setRegistrationNumber(registrationNumber);
        company.setAddress(address);
        company.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return company;
    }
}