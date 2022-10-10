package com.qiren.project.pojo;

public class Registration {
    private int regisId;
    private String regisStatus;
    private String date;
    private float grade;
    private String fkStudent;
    private String fkPlan;

    public Registration() {
    }

    public int getRegisId() {
        return regisId;
    }

    public void setRegisId(int regisId) {
        this.regisId = regisId;
    }

    public String getRegisStatus() {
        return regisStatus;
    }

    public void setRegisStatus(String regisStatus) {
        this.regisStatus = regisStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getFkStudent() {
        return fkStudent;
    }

    public void setFkStudent(String fkStudent) {
        this.fkStudent = fkStudent;
    }

    public String getFkPlan() {
        return fkPlan;
    }

    public void setFkPlan(String fkPlan) {
        this.fkPlan = fkPlan;
    }
}
