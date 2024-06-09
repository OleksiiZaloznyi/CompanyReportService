package com.example.demo.model.mongo;

import java.util.Map;
import java.util.UUID;
import javax.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@RequiredArgsConstructor
public class ReportDetails {
    @Id
    private UUID reportId;
    private Map<String, Object> financialData;
    private String comments;
}
