package com.example.demo.repository.mongo;

import java.util.UUID;
import com.example.demo.model.mongo.ReportDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDetailsRepository extends MongoRepository<ReportDetails, UUID> {
    ReportDetails save(ReportDetails reportDetails);
    ReportDetails findByReportId(UUID reportId);
}
