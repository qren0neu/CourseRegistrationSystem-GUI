package com.qiren.project.pojo;

public class Course {
    private int courseId;
    private String registrationNumber;
    private String courseName;
    private int cretdits;
    private String description;
    private String fkSubject;

    public Course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCretdits() {
        return cretdits;
    }

    public void setCretdits(int cretdits) {
        this.cretdits = cretdits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFkSubject() {
        return fkSubject;
    }

    public void setFkSubject(String fkSubject) {
        this.fkSubject = fkSubject;
    }

    @Override
    public String toString() {
        return getRegistrationNumber() + " " + getCourseName();
    }
}
