package com.example.demo.controller;

import java.util.UUID;
import com.example.demo.model.mongo.ReportDetails;
import com.example.demo.service.ReportDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report-details")
public class ReportDetailsController {
    @Autowired
    private ReportDetailsService reportDetailsService;

    @GetMapping("/{reportId}")
    public ReportDetails getReportDetails(@PathVariable UUID reportId) {
        return reportDetailsService.getReportDetails(reportId);
    }

    @PostMapping
    public ReportDetails createReportDetails(@RequestBody ReportDetails reportDetails) {
        return reportDetailsService.saveReportDetails(reportDetails);
    }
}
