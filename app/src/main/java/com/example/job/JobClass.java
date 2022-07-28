package com.example.job;
public class JobClass {
    public JobClass() {

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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getCompany_postal() {
        return company_postal;
    }

    public void setCompany_postal(String company_postal) {
        this.company_postal = company_postal;
    }

    public String getFirst_qualification() {
        return first_qualification;
    }

    public void setFirst_qualification(String first_qualification) {
        this.first_qualification = first_qualification;
    }

    public String getSecond_qualification() {
        return second_qualification;
    }

    public void setSecond_qualification(String second_qualification) {
        this.second_qualification = second_qualification;
    }

    public String getThird_qualification() {
        return third_qualification;
    }

    public void setThird_qualification(String third_qualification) {
        this.third_qualification = third_qualification;
    }

    public String getFourth_qualification() {
        return fourth_qualification;
    }

    public void setFourth_qualification(String fourth_qualification) {
        this.fourth_qualification = fourth_qualification;
    }

    public String getFifth_qualification() {
        return fifth_qualification;
    }

    public void setFifth_qualification(String fifth_qualification) {
        this.fifth_qualification = fifth_qualification;
    }

    public JobClass(String job_tittle, String job_location, String job_salary, String job_vacancies, String select_due_date, String company_name, String company_email, String company_postal, String first_qualification, String second_qualification, String third_qualification, String fourth_qualification, String fifth_qualification) {
        this.job_tittle = job_tittle;
        this.job_location = job_location;
        this.job_salary = job_salary;
        this.job_vacancies = job_vacancies;
        this.select_due_date = select_due_date;
        this.company_name = company_name;
        this.company_email = company_email;
        this.company_postal = company_postal;
        this.first_qualification = first_qualification;
        this.second_qualification = second_qualification;
        this.third_qualification = third_qualification;
        this.fourth_qualification = fourth_qualification;
        this.fifth_qualification = fifth_qualification;
    }

    String job_tittle,job_location,job_salary,job_vacancies,select_due_date,company_name,company_email,company_postal,first_qualification,second_qualification,third_qualification,fourth_qualification,fifth_qualification;
}
