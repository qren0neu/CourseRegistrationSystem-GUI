package com.qiren.project.dao;

import com.qiren.project.pojo.Semester;
import com.qiren.project.pojo.Subject;
import com.qiren.project.util.LoggingCenter;

import java.util.Arrays;
import java.util.List;

public class SubjectDAO extends AbstractDAO {

    public List<Subject> getSubjectList() throws RuntimeException {
        beginTransaction();
        String sql = "select * from " + getTableName() + " order by orderid";
        List<Subject> list = (List<Subject>) queryList(sql, Subject.class);
        endTransaction();
        return list;
    }

    @Override
    protected String getTableName() {
        return "subject";
    }
}
