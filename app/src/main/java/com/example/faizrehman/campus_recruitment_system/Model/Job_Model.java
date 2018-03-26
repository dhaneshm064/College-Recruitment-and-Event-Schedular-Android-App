package com.example.faizrehman.campus_recruitment_system.Model;

/**
 * Created by faizrehman on 1/26/17.
 */

public class Job_Model {
   private String job_title;
   private String comp_detail;
    private  String comp_name;
    private String comp_email;
    private String category;

    public Job_Model(String job_title, String comp_detail, String comp_name, String comp_email, String category) {
        this.job_title = job_title;
        this.comp_detail = comp_detail;
        this.comp_name = comp_name;
        this.comp_email = comp_email;
        this.category = category;
    }

    public Job_Model() {
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getComp_detail() {
        return comp_detail;
    }

    public void setComp_detail(String comp_detail) {
        this.comp_detail = comp_detail;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_email() {
        return comp_email;
    }

    public void setComp_email(String comp_email) {
        this.comp_email = comp_email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
