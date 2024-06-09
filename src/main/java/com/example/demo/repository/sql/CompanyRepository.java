package com.example.demo.repository.sql;

import java.util.UUID;
import com.example.demo.model.sql.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
