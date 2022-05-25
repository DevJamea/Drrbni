package com.example.drrbni.Models;

public class Job {
    private String jobName,major,jobLink,jobDescription;

    public Job(String jobName, String major, String jobLink, String jobDescription) {
        this.jobName = jobName;
        this.major = major;
        this.jobLink = jobLink;
        this.jobDescription = jobDescription;
    }

    public Job() {}

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}
