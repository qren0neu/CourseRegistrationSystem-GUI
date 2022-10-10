package com.qiren.project.dao;

import com.qiren.project.pojo.Course;

import java.util.List;

public class CourseDAO extends AbstractDAO {

    public Course getCourseByCrn(String crn) throws RuntimeException {
        beginTransaction();
        Course course = simpleQuery("registrationNumber", crn, Course.class);
        endTransaction();
        return course;
    }

    public List<Course> getAllCourses() throws RuntimeException {
        beginTransaction();
        String sql = "select * from " + getTableName();
        List<Course> courseList = (List<Course>) queryList(sql, Course.class);
        endTransaction();
        return courseList;
    }

    public List<Course> getSubjectCourses(String subject) throws RuntimeException {
        beginTransaction();
        // String sql = "select * from " + getTableName();
        List<Course> courseList = (List<Course>) simpleQueryList("fkSubject", subject, Course.class);
        endTransaction();
        return courseList;
    }

    public void addNewCourse(Course course) throws RuntimeException {
        beginTransaction();
        simpleInsert(null, course.getRegistrationNumber(),
                course.getCourseName(), course.getCretdits(), course.getDescription(), course.getFkSubject());
        endTransaction();
    }

    public void updateCourse(Course course) throws RuntimeException {
        beginTransaction();
        String sql = "UPDATE `course1`.`course` " +
                "SET `registrationNumber` = ?, `courseName` = ?, `cretdits` = ?, `description` = ?, `fkSubject` = ? WHERE (`courseId` = ?);";
        update(sql, course.getRegistrationNumber(),
                course.getCourseName(), course.getCretdits(), course.getDescription(), course.getFkSubject(), course.getCourseId());
        endTransaction();
    }

    public void deleteCourse(String courseId) throws RuntimeException {
        beginTransaction();
        simpleDelete("courseId", courseId);
        endTransaction();
    }

    public void deleteCourseByCrn(String crn) throws RuntimeException {
        beginTransaction();
        simpleDelete("registrationNumber", crn);
        endTransaction();
    }

    @Override
    protected String getTableName() {
        return "course";
    }
}
