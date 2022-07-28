package com.example.job;

public class PutPdf {
    String name;
    String url;
    String job_name;
    String applicant_name;

    public PutPdf(String name, String url, String job_name, String applicant_name) {
        this.name = name;
        this.url = url;
        this.job_name = job_name;
        this.applicant_name = applicant_name;
    }

    public PutPdf() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }
}
