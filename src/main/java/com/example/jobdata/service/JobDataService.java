package com.example.jobdata.service;

import com.example.jobdata.model.JobData;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JobDataService {

    private final JobDataLoader loader;

    public JobDataService(JobDataLoader loader) {
        this.loader = loader;
    }

    public List<JobData> queryJobs(
            String jobTitle,
            String gender,
            Long salaryGte,
            Long salaryLte,
            List<String> fields,
            String sort,
            String sortType
    ) {
        // get job stream data
        Stream<JobData> stream = loader.getJobs().stream();

        // Filtering
        if (jobTitle != null) {
            stream = stream.filter(j -> j.getJobTitle() != null &&
                    j.getJobTitle().equalsIgnoreCase(jobTitle));
        }
        if (gender != null) {
            stream = stream.filter(j -> j.getGender() != null &&
                    j.getGender().equalsIgnoreCase(gender));
        }
        if (salaryGte != null) {
            stream = stream.filter(j -> j.getSalary() != null &&
                    j.getSalary() >= salaryGte);
        }
        if (salaryLte != null) {
            stream = stream.filter(j -> j.getSalary() != null &&
                    j.getSalary() <= salaryLte);
        }

        // Sorting
        if (sort != null) {
            Comparator<JobData> comparator;
            switch (sort.toLowerCase()) {
                case "salary":
                    comparator = Comparator.comparing(JobData::getSalary,
                            Comparator.nullsLast(Long::compareTo));
                    break;
                case "job_title":
                    comparator = Comparator.comparing(JobData::getJobTitle,
                            Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
                case "gender":
                    comparator = Comparator.comparing(JobData::getGender,
                            Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
                default:
                    comparator = Comparator.comparing(JobData::getEmployer,
                            Comparator.nullsLast(String::compareToIgnoreCase));
            }
            if ("DESC".equalsIgnoreCase(sortType)) {
                comparator = comparator.reversed();
            }
            stream = stream.sorted(comparator);
        }

        // Sparse fields (return filtered copy data)
        List<JobData> results = stream.collect(Collectors.toList());
        if (fields != null && !fields.isEmpty()) {
            return results.stream()
                    .map(job -> job.filterFields(fields))
                    .collect(Collectors.toList());
        }

        return results;
    }
}
