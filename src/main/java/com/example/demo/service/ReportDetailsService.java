package com.example.demo.service;

import java.util.UUID;
import com.example.demo.model.mongo.ReportDetails;
import com.example.demo.repository.mongo.ReportDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDetailsService {
    @Autowired
    private ReportDetailsRepository reportDetailsRepository;

    public ReportDetails getReportDetails(UUID reportId) {
        return reportDetailsRepository.findByReportId(reportId);
    }

    public ReportDetails saveReportDetails(ReportDetails reportDetails) {
        return reportDetailsRepository.save(reportDetails);
    }
}
