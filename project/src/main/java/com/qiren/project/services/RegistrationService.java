package com.qiren.project.services;

import com.qiren.project.dao.DAOBuilder;
import com.qiren.project.dao.RegistrationDAO;
import com.qiren.project.dao.SemesterDAO;
import com.qiren.project.pojo.CoursePlan;
import com.qiren.project.pojo.Registration;
import com.qiren.project.pojo.RegistrationPlan;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.DateManager;
import com.qiren.project.util.ExceptionCenter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationService extends AbstractService {

    private UserService userService = ServiceBuilder.getInstance().build(UserService.class);
    private MessageService messageService = ServiceBuilder.getInstance().build(MessageService.class);
    private SemesterDAO semesterDAO = DAOBuilder.getInstance().build(SemesterDAO.class);
    private RegistrationDAO registrationDAO = DAOBuilder.getInstance().build(RegistrationDAO.class);

    public boolean registerForClass(JFrame frame, CoursePlan cp) {
        Registration registration = new Registration();
        String studentId = userService.getUserName();
        registration.setFkStudent(studentId);
        registration.setDate(DateManager.getDateFormatter().format(new Date()));
        String targetSem = cp.getFkSem();
        String availableSem = semesterDAO.getAvailableSem().getSemName();
        if (!targetSem.equals(availableSem)) {
            CoreUtils.showErrorDialog(frame, "You can only register for semester: " + availableSem);
            return false;
        }
        registration.setFkPlan(cp.getUid());
        registration.setRegisStatus("succ");
        registration.setGrade(0);
        try {
            Registration exist = registrationDAO.getExisting(studentId, cp.getUid());
            if (null != exist) {
                CoreUtils.showErrorDialog(frame, "You have already registered!");
                return false;
            }
            registrationDAO.register(registration);
            messageService.sendSystemMessage(frame,
                    "Course Registration",
                    studentId + " has registered for your class",
                    cp.getFkStaff(),
                    PageRequest.VIEW_PLAN_PAGE
            );
            return true;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return false;
    }

    public void dropClass(String regisId) {
        try {
            registrationDAO.deleteClass(regisId);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
    }

    public List<RegistrationPlan> getRegistration() {
        try {
            List<RegistrationPlan> registrationPlans =
                    registrationDAO.getRegistration(
                            userService.isStaff() ? "c.fkStaff" : "r.fkStudent", userService.getUserName());
            return registrationPlans;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public List<RegistrationPlan> getRegistrationForSem(String semId) {
        try {
            List<RegistrationPlan> registrationPlans =
                    registrationDAO.getRegistrationWithSem(
                            userService.isStaff()
                                    ? "c.fkStaff" : "r.fkStudent", userService.getUserName(), semId);
            return registrationPlans;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public List<RegistrationPlan> getRegistrationForCourse(String courseId) {
        try {
            List<RegistrationPlan> registrationPlans =
                    registrationDAO.getRegistrationForCourse(courseId);
            return registrationPlans;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }

    public long searchForSeat(CoursePlan cp) {
        long seats = registrationDAO.getRegistrationCount(cp.getUid());
        if (seats >= 15l) {
            return 0;
        }
        return 15L - seats;
    }

    public boolean updateGrade(String id, String grade) {
        try {
            registrationDAO.updateGrade(id, grade);
            return true;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
            return false;
        }
    }
}
