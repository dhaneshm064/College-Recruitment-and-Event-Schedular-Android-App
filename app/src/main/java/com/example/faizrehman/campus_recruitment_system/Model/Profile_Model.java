package com.example.faizrehman.campus_recruitment_system.Model;

/**
 * Created by faizrehman on 1/26/17.
 */

public class Profile_Model {
    private String fname;
    private String lname;
    private String email;
    private String contactno;
    private String address;
    private String ssc;
    private String fsc;
    private String university;
    private String password;
    private String cunpass;
    private String gender;
    private String sscyear;
    private String hscyear;
    private String dpt;
    private String uid;

    public Profile_Model() {
    }

    public Profile_Model(String fname, String lname, String email, String contactno, String address, String ssc, String fsc, String university,String gender, String sscyear, String hscyear, String dpt, String uid) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contactno = contactno;
        this.address = address;
        this.ssc = ssc;
        this.fsc = fsc;
        this.university = university;
        this.gender = gender;
        this.sscyear = sscyear;
        this.hscyear = hscyear;
        this.dpt = dpt;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSsc() {
        return ssc;
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }

    public String getFsc() {
        return fsc;
    }

    public void setFsc(String fsc) {
        this.fsc = fsc;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCunpass() {
        return cunpass;
    }

    public void setCunpass(String cunpass) {
        this.cunpass = cunpass;
    }


    public String getSscyear() {
        return sscyear;
    }

    public void setSscyear(String sscyear) {
        this.sscyear = sscyear;
    }

    public String getHscyear() {
        return hscyear;
    }

    public void setHscyear(String hscyear) {
        this.hscyear = hscyear;
    }

    public String getDpt() {
        return dpt;
    }

    public void setDpt(String dpt) {
        this.dpt = dpt;
    }
}
