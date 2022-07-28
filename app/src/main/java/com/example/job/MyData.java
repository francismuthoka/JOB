package com.example.job;

public class MyData {
    String job_tittle, job_location,job_salary, job_vacancies,select_due_date;
    public MyData(){

    }

    public MyData(String job_tittle, String job_location, String job_salary, String job_vacancies, String select_due_date) {
        this.job_tittle = job_tittle;
        this.job_location = job_location;
        this.job_salary = job_salary;
        this.job_vacancies = job_vacancies;
        this.select_due_date = select_due_date;
    }

    public String getJob_tittle() {
        return job_tittle;
    }

    public void setJob_tittle(String job_tittle) {
        this.job_tittle = job_tittle;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_salary() {
        return job_salary;
    }

    public void setJob_salary(String job_salary) {
        this.job_salary = job_salary;
    }

    public String getJob_vacancies() {
        return job_vacancies;
    }

    public void setJob_vacancies(String job_vacancies) {
        this.job_vacancies = job_vacancies;
    }

    public String getSelect_due_date() {
        return select_due_date;
    }

    public void setSelect_due_date(String select_due_date) {
        this.select_due_date = select_due_date;
    }
}
