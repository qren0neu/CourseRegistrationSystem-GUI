package com.qiren.project.services;

import com.qiren.project.dao.CourseDAO;
import com.qiren.project.dao.DAOBuilder;
import com.qiren.project.pojo.Course;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.CourseConstants;
import com.qiren.project.util.ExceptionCenter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService extends AbstractService {

    private CourseDAO courseDAO = DAOBuilder.getInstance().build(CourseDAO.class);

    public boolean addCourse(JFrame frame, String crn, String name,
                             String credits, String desc, String subject) {
        if (!precheck(frame, crn, name, credits, desc, subject)) {
            return false;
        }
        if (null == credits || credits.isBlank()) {
            credits = "4"; // default 4
        }
        if (null == desc || desc.isBlank()) {
            desc = "The lecture has not provided the description";
        }
        Course course = new Course();
        course.setFkSubject(subject);
        course.setCourseName(name);
        course.setCretdits(Integer.parseInt(credits));
        course.setDescription(desc);
        course.setRegistrationNumber(crn);
        try {
            Course tmp = courseDAO.getCourseByCrn(crn);
            if (null != tmp) {
                if (tmp.getCretdits() > 0) {
                    CoreUtils.showErrorDialog(frame, "This CRN already exists!");
                    return false;
                } else {
                    int res = JOptionPane.showConfirmDialog(
                            frame, "This course has been closed, do you want to reopen it?");
                    if (res == 0) {
                        tmp.setCretdits(course.getCretdits());
                        updateCourseInfo(frame, tmp);
                        return true;
                    }
                    return false;
                }
            }
        } catch (Exception e) {
            // okay to have exception here, so don't return
            ExceptionCenter.handleException(e);
        }
        try {
            courseDAO.addNewCourse(course);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
            return false;
        }
        return true;
    }

    public boolean updateCourseInfo(JFrame frame, Course course) {
        if (!precheck(frame,
                course.getRegistrationNumber(),
                course.getCourseName(),
                course.getCretdits() + "",
                course.getDescription(), course.getFkSubject())) {
            return false;
        }
        course.setFkSubject(course.getFkSubject());
        try {
            courseDAO.updateCourse(course);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
            return false;
        }
        return true;
    }

    public boolean precheck(JFrame frame, String crn, String name,
                            String credits, String desc, String subject) {
        if (null == crn || crn.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must enter a crn!");
            return false;
        }
        if (null == name || name.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must enter course name!");
            return false;
        }
        if (null == subject || subject.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must select a subject!");
            return false;
        }
        return true;
    }

    public Course getCourseByCrn(String crn) {
        if (null == crn) {
            return null;
        }
        Course course = null;
        try {
            course = courseDAO.getCourseByCrn(crn);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return course;
    }

    public List<Course> getAllCourses() {
        try {
            return courseDAO.getAllCourses();
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public List<Course> getCoursesBySubject(String subject) {
        try {
            return courseDAO.getSubjectCourses(subject);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public int subject2Index(String subj) {
        return switch (subj) {
            case CourseConstants.INFORMATION_SYSTEMS -> 0;
            case CourseConstants.COMPUTER_SYSTEM_ENGINEERING -> 1;
            case CourseConstants.DATA_MANAGEMENT -> 2;
            default -> 0;
        };
    }
}
