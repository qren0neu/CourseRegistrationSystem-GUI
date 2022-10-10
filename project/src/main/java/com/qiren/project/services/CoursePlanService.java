package com.qiren.project.services;

import com.qiren.project.dao.CoursePlanDAO;
import com.qiren.project.dao.DAOBuilder;
import com.qiren.project.dao.SemesterDAO;
import com.qiren.project.dao.SubjectDAO;
import com.qiren.project.pojo.Course;
import com.qiren.project.pojo.CoursePlan;
import com.qiren.project.pojo.Semester;
import com.qiren.project.pojo.Subject;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.DateManager;
import com.qiren.project.util.ExceptionCenter;

import javax.swing.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoursePlanService extends AbstractService {

    private SemesterDAO semesterDAO =
            DAOBuilder.getInstance().build(SemesterDAO.class);
    private CoursePlanDAO coursePlanDAO =
            DAOBuilder.getInstance().build(CoursePlanDAO.class);
    private SubjectDAO subjectDAO =
            DAOBuilder.getInstance().build(SubjectDAO.class);

    UserService userService = ServiceBuilder.getInstance().build(UserService.class);

    public List<CoursePlan> getSelfCoursePlan() {
        try {
            return coursePlanDAO.getCoursePlanByStaffId(userService.getUserName());
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public List<CoursePlan> getSelfCoursePlanWithSemester(String semesterId) {
        try {
            return coursePlanDAO.getCoursePlanByStaffIdAndSem(userService.getUserName(), semesterId);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public List<CoursePlan> getAllCoursePlan() {
        try {
            if (userService.isStaff()) {
                return getSelfCoursePlan();
            } else {
                return coursePlanDAO.getAllCoursePlan();
            }
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public List<CoursePlan> getFilteredCoursePlan(String filter) {
        try {
            return coursePlanDAO.getAllCoursePlan(filter);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public CoursePlan getById(String cpid) {
        try {
            return coursePlanDAO.getById(cpid);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new CoursePlan();
    }

    public List<Semester> getSemesterList() {
        try {
            List<Semester> semesters = semesterDAO.getSemList();
            return semesters;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public boolean addNewCoursePlan(JFrame frame, Course course, Semester semester,
                                    List<String> from, List<String> to, List<String> weekday) {
        // pre check
        if (null == course) {
            CoreUtils.showErrorDialog(frame, "You must select a course to make a plan!");
            return false;
        }
        if (null == semester) {
            CoreUtils.showErrorDialog(frame, "You must select a semester to make a plan!");
            return false;
        }
        if (null == from || from.isEmpty()) {
            CoreUtils.showErrorDialog(frame, "You must select time to make a plan!");
            return false;
        }
        if (null == to || to.isEmpty()) {
            CoreUtils.showErrorDialog(frame, "You must select time to make a plan!");
            return false;
        }
        if (null == weekday || weekday.isEmpty()) {
            CoreUtils.showErrorDialog(frame, "You must select weekday to make a plan!");
            return false;
        }
        // do
        coursePlanDAO.selfBeginTransaction();
        for (int i = 0; i < Math.min(from.size(), to.size()); i++) {
            try {
                CoursePlan cp = coursePlanDAO.checkConflict(semester.getSemId() + "",
                        userService.getUserId() + "", weekday.get(i), from.get(i), to.get(i));
                if (null != cp) {
                    CoreUtils.showErrorDialog(frame,
                            "There are conflicts in your time!\n" +
                                    "check: " + cp.toString());
                    coursePlanDAO.selfEndTransaction();
                    return false;
                }
            } catch (Exception e) {
                ExceptionCenter.handleException(e);
            }
        }
        long uniqueTime = System.currentTimeMillis();
        for (int i = 0; i < Math.min(from.size(), to.size()); i++) {
            CoursePlan coursePlan = new CoursePlan();
            coursePlan.setFkCourse(course.getRegistrationNumber() + " " + course.getCourseName());
            coursePlan.setWeekDay(weekday.get(i));
            coursePlan.setFromTime(from.get(i));
            coursePlan.setToTime(to.get(i));
            coursePlan.setFkSem(semester.getSemName());
            coursePlan.setFkStaff(userService.getUserName());
            coursePlan.setUid(uniqueTime + "");
            try {
                coursePlanDAO.insertCoursePlan(coursePlan);
            } catch (Exception e) {
                ExceptionCenter.handleException(e);
                coursePlanDAO.selfEndTransaction();
                return false;
            }
        }
        coursePlanDAO.selfEndTransaction();
        return true;
    }

    public List<Subject> getSubjectList() {
        try {
            return subjectDAO.getSubjectList();
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public boolean checkPlanAvailable(String semName) {
        List<Semester> semesterList = getSemesterList();
        for (Semester semester : semesterList) {
            if (semName.equals(semester.getSemName())) {
                String from = semester.getFromDate();
                String to = semester.getToDate();
                try {
                    Date fromDate = DateManager.getDateFormatter().parse(from);
                    Date toDate = DateManager.getDateFormatter().parse(to);
                    Date now = new Date();
                    return now.after(fromDate) && now.before(toDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    public void cancelPlan(String id) {
        try {
            coursePlanDAO.cancelCoursePlan(id);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
    }

    public void closeAllPlanOfCourse(String id) {
        try {
            coursePlanDAO.closePlanByCourse(id);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
    }
}
