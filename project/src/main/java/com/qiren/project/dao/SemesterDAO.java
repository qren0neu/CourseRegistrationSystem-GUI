package com.qiren.project.dao;

import com.qiren.project.pojo.Semester;

import java.util.List;

public class SemesterDAO extends AbstractDAO {

    public List<Semester> getSemList() throws RuntimeException {
        beginTransaction();
        List<Semester> semester = (List<Semester>)
                queryList("select * from " + getTableName(), Semester.class);
        endTransaction();
        return semester;
    }

    public Semester getAvailableSem() throws RuntimeException {
        beginTransaction();
        String sql = """
                SELECT * FROM course1.semester\s
                where now() < fromDate and now() > subDate(fromDate,30);
                """;
        Semester semester = query(sql, Semester.class);
        endTransaction();
        return semester;
    }

    @Override
    protected String getTableName() {
        return "semester";
    }
}
