package com.example.demo.repository.sql;

import java.util.List;
import java.util.UUID;
import com.example.demo.model.sql.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    List<Report> findByCompanyId(UUID companyId);
}
