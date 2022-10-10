package com.qiren.project.dao;

import com.qiren.project.exceptions.MysqlConnectionException;
import com.qiren.project.exceptions.MysqlDriverNotFoundException;
import com.qiren.project.pojo.Course;
import com.qiren.project.pojo.CoursePlan;

import java.util.List;

public class CoursePlanDAO extends AbstractDAO {

    public void selfBeginTransaction() throws MysqlConnectionException, MysqlDriverNotFoundException {
        super.beginTransaction();
    }

    public void selfEndTransaction() throws MysqlConnectionException, MysqlDriverNotFoundException {
        super.endTransaction();
    }

    public CoursePlan checkConflict(String sem, String staffId,
                                    String weekDay, String fromTime, String toTime) throws RuntimeException {
        // beginTransaction();
        String sql = "select * from " + getTableName()
                + " where (fkSem = ? and fkStaff = ? " +
                "and weekday = ? and ((toTime > ? and fromTime < ?) or (fromTime < ? and toTime > ?)))";
        return query(sql, CoursePlan.class, sem, staffId, weekDay, fromTime, fromTime, toTime, toTime);
        // endTransaction();
    }

    public void insertCoursePlan(CoursePlan coursePlan) throws RuntimeException {
        //beginTransaction();
        simpleInsert(null, coursePlan.getWeekDay(), coursePlan.getFromTime(), coursePlan.getToTime(),
                "t", coursePlan.getFkCourse(), coursePlan.getFkSem(), coursePlan.getFkStaff(), coursePlan.getUid());
        //endTransaction();
    }

    public void cancelCoursePlan(String id) {
        beginTransaction();
        String sql = "UPDATE " + getTableName() + " SET `status` = 'c' WHERE (`uid` = ?);";
        update(sql, id);
        endTransaction();
    }

    public void closePlanByCourse(String id) {
        beginTransaction();
        String sql = "UPDATE " + getTableName() + " SET `status` = 'a' WHERE (`fkCourse` = ?);";
        update(sql, id);
        endTransaction();
    }

    public CoursePlan getById(String cpid) {
        beginTransaction();
        CoursePlan sp = simpleQuery("planId", cpid, CoursePlan.class);
        endTransaction();
        return sp;
    }

    public List<CoursePlan> getCoursePlanByStaffId(String staffId) throws RuntimeException {
        beginTransaction();
        String sql = "SELECT sum(planId) planId," +
                "group_concat(weekday) weekDay, " +
                "group_concat(fromTime) fromTime, " +
                "group_concat(toTime) toTime," +
                "group_concat(status) status," +
                "fkCourse, fkSem, fkStaff, uid " +
                "FROM course1.courseplan " +
                "where fkstaff = '" + staffId + "' " +
                "group by fkCourse, fkSem, fkStaff, uid";
        List<CoursePlan> coursePlanList = (List<CoursePlan>)
                queryList(sql, CoursePlan.class);
        endTransaction();
        return coursePlanList;
    }

    public List<CoursePlan> getCoursePlanByStaffIdAndSem(String staffId, String semId) throws RuntimeException {
        beginTransaction();
        String sql = "SELECT * "+
                "FROM course1.courseplan " +
                "where fkstaff = ? and fkSem = ?";
        List<CoursePlan> coursePlanList = (List<CoursePlan>)
                queryList(sql, CoursePlan.class, staffId, semId);
        endTransaction();
        return coursePlanList;
    }

    public List<CoursePlan> getAllCoursePlan() throws RuntimeException {
        beginTransaction();
        String sql = "SELECT sum(planId) planId," +
                "group_concat(weekday) weekDay, " +
                "group_concat(fromTime) fromTime, " +
                "group_concat(toTime) toTime," +
                "group_concat(status) status," +
                "fkCourse, fkSem, fkStaff, uid " +
                "FROM course1.courseplan " +
                "group by fkCourse, fkSem, fkStaff, uid";
        List<CoursePlan> coursePlanList = (List<CoursePlan>)
                queryList(sql, CoursePlan.class);
        endTransaction();
        return coursePlanList;
    }

    public List<CoursePlan> getAllCoursePlan(String filter) throws RuntimeException {
        beginTransaction();
        String sql = "SELECT sum(planId) planId," +
                "group_concat(weekday) weekDay, " +
                "group_concat(fromTime) fromTime, " +
                "group_concat(toTime) toTime," +
                "group_concat(status) status," +
                "fkCourse, fkSem, fkStaff, uid " +
                "FROM course1.courseplan " +
                filter + " " +
                "group by fkCourse, fkSem, fkStaff, uid";
        List<CoursePlan> coursePlanList = (List<CoursePlan>)
                queryList(sql, CoursePlan.class);
        endTransaction();
        return coursePlanList;
    }

    @Override
    protected String getTableName() {
        return "courseplan";
    }
}
