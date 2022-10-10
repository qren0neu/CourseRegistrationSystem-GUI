package com.qiren.project.pojo;

public class CoursePlan {
    private int planId;
    private String weekDay;
    private String fromTime;
    private String toTime;
    private String status;
    private String fkCourse;
    private String fkSem;
    private String fkStaff;

    private String uid;

    public CoursePlan() {
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "CoursePlan{" +
                "weekDay='" + weekDay + '\'' +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                '}';
    }
}
