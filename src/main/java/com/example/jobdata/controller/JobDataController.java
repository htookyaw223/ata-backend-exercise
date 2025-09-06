package com.example.jobdata.controller;

import com.example.jobdata.model.JobData;
import com.example.jobdata.service.JobDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/job_data")
public class JobDataController {

    private final JobDataService service;

    public JobDataController(JobDataService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getJobs(
            @RequestParam(required = false, name = "job_title") String jobTitle,
            @RequestParam(required = false, name = "gender") String gender,
            @RequestParam(required = false, name = "salary[gte]") Long salaryGte,
            @RequestParam(required = false, name = "salary[lte]") Long salaryLte,
            @RequestParam(required = false) String fields,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "ASC") String sort_type
    ) {
        List<String> fieldList = null;
        if (fields != null && !fields.isEmpty()) {
            fieldList = Arrays.asList(fields.split(","));
        }
        List<JobData> jobs = service.getJobDataList(jobTitle, gender, salaryGte, salaryLte, fieldList, sort, sort_type);

        Map<String, Object> response = new HashMap<>();
        response.put("count", jobs.size());
        response.put("data", jobs);

        return ResponseEntity.ok(response);
    }
}
