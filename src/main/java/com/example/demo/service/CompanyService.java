package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Company;
import com.example.demo.repository.sql.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(UUID id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(UUID id, Company company) {
        Company existingCompany = getCompanyById(id);

        existingCompany.setName(company.getName());
        existingCompany.setAddress(company.getAddress());
        existingCompany.setRegistrationNumber(company.getRegistrationNumber());
        existingCompany.setCreatedAt(company.getCreatedAt());

        return saveCompany(existingCompany);
    }

    public void deleteCompany(UUID id) {
        companyRepository.deleteById(id);
    }
}
