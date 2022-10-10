package com.qiren.project.pojo;

public class RegistrationPlan {
    private String regisId;
    private String regisStatus;
    private String date;
    private String grade;
    private String fkStudent;
    private String fkPlan;
    private String planId;
    private String weekday;
    private String fromTime;
    private String toTime;
    private String status;
    private String fkCourse;
    private String fkSem;
    private String fkStaff;
    private String uid;

    public RegistrationPlan() {
    }

    public String getRegisId() {
        return regisId;
    }

    public void setRegisId(String regisId) {
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFkCourse() {
        return fkCourse;
    }

    public void setFkCourse(String fkCourse) {
        this.fkCourse = fkCourse;
    }

    public String getFkSem() {
        return fkSem;
    }

    public void setFkSem(String fkSem) {
        this.fkSem = fkSem;
    }

    public String getFkStaff() {
        return fkStaff;
    }

    public void setFkStaff(String fkStaff) {
        this.fkStaff = fkStaff;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
