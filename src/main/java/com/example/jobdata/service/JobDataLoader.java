package com.example.jobdata.service;

import com.example.jobdata.model.JobData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class JobDataLoader {

    private final ObjectMapper objectMapper;
    private List<JobData> jobs;

    public JobDataLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/job_data.json")) {
            jobs = objectMapper.readValue(is, new TypeReference<List<JobData>>() {});
        }
    }

    public List<JobData> getJobs() {
        return jobs;
    }
}
