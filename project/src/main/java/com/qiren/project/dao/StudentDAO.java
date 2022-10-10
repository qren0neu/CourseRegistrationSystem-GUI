package com.qiren.project.dao;

import com.qiren.project.pojo.Staff;
import com.qiren.project.pojo.Student;

public class StudentDAO extends AbstractDAO {

    public Student getStudentById(String id) {
        beginTransaction();
        Student student = simpleQuery("studentId", id, Student.class);
        endTransaction();
        return student;
    }

    public Student getStudentByEmail(String email) {
        beginTransaction();
        Student student = simpleQuery("email", email, Student.class);
        endTransaction();
        return student;
    }

    public void updateStudent(Student student) throws RuntimeException {
        beginTransaction();
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(getTableName()).append(" ");
        sql.append("set name = ?");
        sql.append(", address = ?");
        sql.append(", phone = ?");
        sql.append(", email = ?");
        sql.append(" where (studentId = ?);");
        update(sql.toString(), student.getName(),
                student.getAddress(), student.getPhone(),
                student.getEmail(), student.getStudentId());
        endTransaction();
    }

    public void updatePassword(String id, String password) throws RuntimeException {
        beginTransaction();
        String sql = "update " + getTableName() + " set password = ? where (studentid = ?);";
        update(sql, password, id);
        endTransaction();
    }

    public void addStudent(Student staff) throws RuntimeException {
        beginTransaction();
        simpleInsert(null, staff.getName(),
                staff.getAddress(), staff.getPhone(),
                staff.getEmail(), "toronto");
        endTransaction();
    }

    @Override
    protected String getTableName() {
        return "student";
    }
}
