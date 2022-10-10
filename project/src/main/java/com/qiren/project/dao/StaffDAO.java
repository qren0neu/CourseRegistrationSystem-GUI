package com.qiren.project.dao;

import com.qiren.project.pojo.Staff;

public class StaffDAO extends AbstractDAO {

    public Staff getStaffByEmail(String email) throws RuntimeException {
        beginTransaction();
        Staff staff = simpleQuery("email", email, Staff.class);
        endTransaction();
        return staff;
    }

    public Staff getStaffById(String id) throws RuntimeException {
        beginTransaction();
        Staff staff = simpleQuery("staffId", id, Staff.class);
        endTransaction();
        return staff;
    }

    public void updateStaff(Staff staff) throws RuntimeException {
        beginTransaction();
        StringBuilder sql = new StringBuilder();
//        sql.append("update ").append(getTableName()).append(" ");
//        sql.append("set name = '").append(staff.getName()).append("'");
//        sql.append(", address = ").append(staff.getAddress()).append("'");
//        sql.append(", phone = ").append(staff.getPhone()).append("'");
//        sql.append(", email = ").append(staff.getEmail()).append("'");
//        sql.append(" where (staffId = '").append(staff.getStaffId()).append("'");
        sql.append("update ").append(getTableName()).append(" ");
        sql.append("set name = ?");
        sql.append(", address = ?");
        sql.append(", phone = ?");
        sql.append(", email = ?");
        sql.append(" where (staffId = ?);");
        update(sql.toString(), staff.getName(),
                staff.getAddress(), staff.getPhone(),
                staff.getEmail(), staff.getStaffId());
        endTransaction();
    }

    public void addStaff(Staff staff) throws RuntimeException {
        beginTransaction();
        simpleInsert(null, staff.getName(),
                staff.getAddress(), staff.getPhone(),
                staff.getEmail(), "toronto");
        endTransaction();
    }

    public void updatePassword(String id, String password) throws RuntimeException {
        beginTransaction();
        String sql = "update " + getTableName() + " set password = ? where (staffid = ?);";
        update(sql, password, id);
        endTransaction();
    }

    @Override
    protected String getTableName() {
        return "staff";
    }
}
