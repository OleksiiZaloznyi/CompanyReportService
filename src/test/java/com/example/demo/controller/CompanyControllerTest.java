package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Company;
import com.example.demo.service.CompanyService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;
    @Autowired
    private CompanyService companyService;

    private Company company1;
    private Company company2;

    @BeforeEach
    void setUp() {
        company1 = createTestCompany("Test Company 1", "12345", "Address 1");
        company2 = createTestCompany("Test Company 2", "67890", "Address 2");
    }

    @Test
    void testGetAllCompanies() {
        companyService.saveCompany(company1);
        companyService.saveCompany(company2);

        List<Company> companies = companyController.getAllCompanies();

        assertThat(companies.get(0).getName()).isEqualTo("Test Company 1");
        assertThat(companies.get(1).getName()).isEqualTo("Test Company 2");
    }

    @Test
    void testGetCompanyById() {
        Company savedCompany = companyService.saveCompany(company1);

        UUID companyId = savedCompany.getId();
        Company retrievedCompany = companyController.getCompanyById(companyId);

        assertThat(retrievedCompany).isNotNull();
        assertThat(retrievedCompany.getName()).isEqualTo("Test Company 1");
    }

    @Test
    void testCreateCompany() {
        Company createdCompany = companyController.createCompany(company1);

        assertNotNull(createdCompany.getId());
        assertEquals("Test Company 1", createdCompany.getName());
    }

    @Test
    void testUpdateCompany() {
        Company savedCompany = companyService.saveCompany(company1);

        UUID companyId = savedCompany.getId();
        company1.setName("Updated Company");

        Company updatedCompany = companyController.updateCompany(companyId, company1);

        assertNotNull(updatedCompany);
        assertEquals("Updated Company", updatedCompany.getName());
    }

    @Test
    void testDeleteCompany() {
        Company savedCompany = companyService.saveCompany(company1);

        UUID companyId = savedCompany.getId();
        companyController.deleteCompany(companyId);

        List<Company> companies = companyController.getAllCompanies();
        assertThat(companies).doesNotContain(savedCompany);
    }

    private Company createTestCompany(String name, String regNumber, String address) {
        return new Company(name, regNumber, address, new Timestamp(System.currentTimeMillis()));
    }
}