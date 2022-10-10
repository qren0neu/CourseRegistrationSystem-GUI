package com.qiren.project.services;

import com.qiren.project.dao.AdminDAO;
import com.qiren.project.dao.DAOBuilder;
import com.qiren.project.dao.StaffDAO;
import com.qiren.project.dao.StudentDAO;
import com.qiren.project.pojo.Staff;
import com.qiren.project.pojo.Student;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.ExceptionCenter;
import com.qiren.project.util.LoggingCenter;

import javax.swing.*;

public class UserService extends AbstractService {

    public static final String TYPE_STUDENT = "T";
    public static final String TYPE_STAFF = "F";

    private Staff staff;
    private Student student;

    private StaffDAO staffDAO = DAOBuilder.getInstance().build(StaffDAO.class);
    private AdminDAO adminDAO = DAOBuilder.getInstance().build(AdminDAO.class);
    private StudentDAO studentDAO = DAOBuilder.getInstance().build(StudentDAO.class);

    public int getUserId() {
        if (null != staff) {
            return staff.getStaffId();
        }
        if (null != student) {
            return student.getStudentId();
        }
        LoggingCenter.error("Get user without initialize!");
        return 0;
    }

    public String getUserName() {
        if (null != staff) {
            return staff.getName();
        }
        if (null != student) {
            return student.getName();
        }
        LoggingCenter.error("Get user without initialize!");
        return "";
    }

    public void saveUserById(String id, String type) {
        try {
            if (TYPE_STAFF.equals(type)) {
                staff = staffDAO.getStaffById(id);
            }
            if (TYPE_STUDENT.equals(type)) {
                student = studentDAO.getStudentById(id);
            }
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
    }

    public String getUserAddress() {
        return isStaff() ? staff.getAddress() : student.getAddress();
    }

    public String getUserPhone() {
        return isStaff() ? staff.getPhone() : student.getPhone();
    }

    public String getUserEmail() {
        return isStaff() ? staff.getEmail() : student.getEmail();
    }

    public boolean isLogin() {
        return null != staff || null != student;
    }

    public void saveStaff(Staff staff) {
        this.staff = staff;
    }

    public void saveStudent(Student student) {
        this.student = student;
    }

    public boolean staffLogin(String email, String password) {
        if (null == password) {
            return false;
        }
        try {
            Staff stf = staffDAO.getStaffByEmail(email);
            if (null != stf && password.equals(stf.getPassword())) {
                saveStaff(stf);
                return true;
            }
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return false;
    }

    public boolean studentLogin(String email, String password) {
        if (null == password) {
            return false;
        }
        try {
            Student std = studentDAO.getStudentByEmail(email);
            if (null != std && password.equals(std.getPassword())) {
                saveStudent(std);
                return true;
            }
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return false;
    }

    public boolean checkPassword(String password) {
        if (null == password) {
            return false;
        }
        return isStaff() ? password.equals(staff.getPassword()) : password.equals(student.getPassword());
    }

    public boolean isStaff() {
        if (!isLogin()) {
            LoggingCenter.error("Get user without Login!");
        }
        return null != staff && null == student;
    }

    public boolean changeUserInfo(JFrame frame,
                                  String name,
                                  String email,
                                  String phone,
                                  String address,
                                  String city,
                                  String state,
                                  String postcode) {
        return changeUserInfo(frame, name, null, email, phone, address, city, state, postcode, false);
    }

    public boolean addUserInfo(JFrame frame,
                               String name,
                               String type,
                               String email,
                               String phone,
                               String address,
                               String city,
                               String state,
                               String postcode) {
        return changeUserInfo(frame, name, type, email, phone, address, city, state, postcode, true);
    }

    public boolean changeUserInfo(JFrame frame,
                                  String name,
                                  String type,
                                  String email,
                                  String phone,
                                  String address,
                                  String city,
                                  String state,
                                  String postcode,
                                  boolean isAdd) {
        if (null == email || email.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide an email!");
            return false;
        }
        if (!CoreUtils.checkEmail(email)) {
            CoreUtils.showErrorDialog(frame, "Email pattern not valid!");
            return false;
        }
        if (null == address || address.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide an address!");
            return false;
        }
        if (null == city || city.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide your city!");
            return false;
        }
        if (null == state || state.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide your state/province!");
            return false;
        }
        if (null == postcode || postcode.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide your postcode!");
            return false;
        }
        if (null == name || name.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide your name!");
            return false;
        }
        if (null == phone || phone.isBlank()) {
            CoreUtils.showErrorDialog(frame, "You must provide your phone!");
            return false;
        }
        if (!CoreUtils.checkPhone(phone)) {
            CoreUtils.showErrorDialog(frame, "Phone pattern not valid!");
            return false;
        }
        if (null != type && !type.isBlank()) {
            if ("Staff".equals(type)) {
                Staff newStaff = new Staff();
                newStaff.setStaffId(staff.getStaffId());
                newStaff.setEmail(email);
                newStaff.setAddress(address + ", " + city + ", " + state + ", " + postcode);
                newStaff.setName(name);
                newStaff.setPhone(phone);
                try {
                    staffDAO.addStaff(newStaff);
                } catch (Exception e) {
                    ExceptionCenter.handleException(e);
                    return false;
                }
            } else {
                Student newStudent = new Student();
                newStudent.setStudentId(student.getStudentId());
                newStudent.setEmail(email);
                newStudent.setAddress(address + ", " + city + ", " + state + ", " + postcode);
                newStudent.setName(name);
                newStudent.setPhone(phone);
                try {
                    studentDAO.addStudent(newStudent);
                } catch (Exception e) {
                    ExceptionCenter.handleException(e);
                    return false;
                }
            }
            return true;
        }
        if (isStaff()) {
            Staff newStaff = new Staff();
            newStaff.setStaffId(staff.getStaffId());
            newStaff.setEmail(email);
            newStaff.setAddress(address + ", " + city + ", " + state + ", " + postcode);
            newStaff.setName(name);
            newStaff.setPhone(phone);
            try {
                staffDAO.updateStaff(newStaff);
                newStaff.setPassword(staff.getPassword());
                staff = newStaff;
            } catch (Exception e) {
                ExceptionCenter.handleException(e);
                return false;
            }
        } else {
            Student newStudent = new Student();
            newStudent.setStudentId(student.getStudentId());
            newStudent.setEmail(email);
            newStudent.setAddress(address + ", " + city + ", " + state + ", " + postcode);
            newStudent.setName(name);
            newStudent.setPhone(phone);
            try {
                studentDAO.updateStudent(newStudent);
                newStudent.setPassword(student.getPassword());
                student = newStudent;
            } catch (Exception e) {
                ExceptionCenter.handleException(e);
                return false;
            }
        }
        return true;
    }

    public boolean changeUserPassword(JFrame frame, String oldPassword, String newPassword, String confrimPassword) {
        if (!checkPassword(oldPassword)) {
            CoreUtils.showErrorDialog(frame, "Password incorrect, please enter again!");
            return false;
        }
        if (!newPassword.isBlank()) {
            if (newPassword.length() < 6) {
                CoreUtils.showErrorDialog(frame,
                        "Password too short! You should enter at least 6 characters.");
                return false;
            }
            if (newPassword.equals(confrimPassword)) {
                try {
                    if (isStaff()) {
                        staffDAO.updatePassword(staff.getStaffId() + "", newPassword);
                        staff.setPassword(newPassword);
                    } else {
                        studentDAO.updatePassword(student.getStudentId() + "", newPassword);
                        student.setPassword(newPassword);
                    }
                    return true;
                } catch (Exception e) {
                    ExceptionCenter.handleException(e);
                }
            } else {
                CoreUtils.showErrorDialog(frame, "Please confirm your new password!");
            }
        } else {
            CoreUtils.showErrorDialog(frame, "Please enter new password!");
        }
        return false;
    }

    public boolean isAdmin() {
        try {
            return adminDAO.getCountById(getUserId() + "") > 0;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return false;
    }

    public void addNewStudent(Student student) {
        studentDAO.addStudent(student);
    }

    public void addNewStaff(Staff staff) {
        staffDAO.addStaff(staff);
    }

    public void clearLoginInfo() {
        staff = null;
        student = null;
    }

    public void createEmptyStudent() {
        staff = null;
        student = new Student();
    }

    public void createEmptyStaff() {
        student = null;
        staff = new Staff();
    }
}
