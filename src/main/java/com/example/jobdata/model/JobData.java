package com.example.jobdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobData {
    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Employer")
    private String employer;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("Job Title")
    private String jobTitle;

    @JsonProperty("Years at Employer")
    private String yearsAtEmployer;

    @JsonProperty("Years of Experience")
    private String yearsOfExperience;

    @JsonProperty("Salary")
    private String salary;

    @JsonProperty("Signing Bonus")
    private String signingBonus;

    @JsonProperty("Annual Bonus")
    private String annualBonus;

    @JsonProperty("Annual Stock Value/Bonus")
    private String stockBonus;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("Additional Comments")
    private String comments;

    public Long getSalary() {
        if (salary == null || salary.isEmpty()) return null;
        String cleaned = salary.replaceAll("[^0-9]", "");
        return cleaned.isEmpty() ? null : Long.valueOf(cleaned);
    }

    public JobData filterFields(List<String> fields) {
        JobData filtered = new JobData();

            if (fields.contains("timestamp")) filtered.setTimestamp(this.timestamp);
            if (fields.contains("employer")) filtered.setEmployer(this.employer);
            if (fields.contains("location")) filtered.setLocation(this.location);
            if (fields.contains("job_title")) filtered.setJobTitle(this.jobTitle);
            if (fields.contains("years_at_employer")) filtered.setYearsAtEmployer(this.yearsAtEmployer);
            if (fields.contains("years_of_experience")) filtered.setYearsOfExperience(this.yearsOfExperience);
            if (fields.contains("salary")) filtered.setSalary(this.salary);
            if (fields.contains("signing_bonus")) filtered.setSigningBonus(this.signingBonus);
            if (fields.contains("annual_bonus")) filtered.setAnnualBonus(this.annualBonus);
            if (fields.contains("annual_stock_bonus")) filtered.setStockBonus(this.stockBonus);
            if (fields.contains("gender")) filtered.setGender(this.gender);
            if (fields.contains("comments")) filtered.setComments(this.comments);

        return filtered;
    }
}