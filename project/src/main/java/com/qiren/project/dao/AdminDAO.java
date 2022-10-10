package com.qiren.project.dao;

import com.qiren.project.pojo.Staff;

public class AdminDAO extends AbstractDAO {

    public long getCountById(String staffId) {
        beginTransaction();
//        long count = queryCount("select * from admin where fkStaff = " + staffId);
        Staff staff = simpleQuery("fkStaff", staffId, Staff.class);
        endTransaction();
        return null == staff ? 0L : 1L;
    }

    @Override
    protected String getTableName() {
        return "admin";
    }
}
