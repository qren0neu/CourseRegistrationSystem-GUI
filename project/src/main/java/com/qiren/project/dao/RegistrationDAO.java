package com.qiren.project.dao;

import com.qiren.project.pojo.Registration;
import com.qiren.project.pojo.RegistrationPlan;
import com.qiren.project.util.CoreUtils;

import java.util.List;

public class RegistrationDAO extends AbstractDAO {

    public void register(Registration registration) throws RuntimeException {
        beginTransaction();
        simpleInsert(null, registration.getRegisStatus(),
                registration.getDate(), registration.getGrade(), registration.getFkStudent(), registration.getFkPlan());
        endTransaction();
    }

    public long getRegistrationCount(String id) {
        beginTransaction();
        String sql = "select count(regisid) from registration where fkPlan = " + id;
        long count = queryCount(sql);
        endTransaction();
        return count;
    }

    public Registration getExisting(String studentId, String planId) {
        beginTransaction();
        String sql = "select * from registration where fkPlan = " + planId + " and fkStudent = '" + studentId + "'";
        Registration registration = query(sql, Registration.class);
        endTransaction();
        return registration;
    }

    public List<RegistrationPlan> getRegistration(String key, String value) {
        beginTransaction();
        String sql = """
                SELECT * FROM course1.registration r\s
                left join courseplan c\s
                on r.fkPlan = c.uid\s
                where r.regisStatus = 'succ' and %replace1% = '%replace2%';
                """;
        List<RegistrationPlan> registrationPlans = (List<RegistrationPlan>)
                queryList(CoreUtils.customFormat(sql, key, value), RegistrationPlan.class);
        endTransaction();
        return registrationPlans;
    }

    public List<RegistrationPlan> getRegistrationWithSem(String key, String value, String semId) {
        beginTransaction();
        String sql = """
                SELECT * FROM course1.registration r\s
                left join courseplan c\s
                on r.fkPlan = c.uid\s
                where r.regisStatus = 'succ' and %replace1% = '%replace2%' and fkSem = '%replace3%';
                """;
        List<RegistrationPlan> registrationPlans = (List<RegistrationPlan>)
                queryList(CoreUtils.customFormat(sql, key, value, semId + ""), RegistrationPlan.class);
        endTransaction();
        return registrationPlans;
    }

    public List<RegistrationPlan> getRegistrationForCourse(String courseId) {
        beginTransaction();
        String sql = """
                SELECT * FROM course1.registration r\s
                left join courseplan c\s
                on r.fkPlan = c.uid\s
                where r.regisStatus = 'succ' and c.fkcourse = '%replace1%';
                """;
        List<RegistrationPlan> registrationPlans = (List<RegistrationPlan>)
                queryList(CoreUtils.customFormat(sql, courseId), RegistrationPlan.class);
        endTransaction();
        return registrationPlans;
    }

    public void updateGrade(String id, String grade) {
        beginTransaction();
        String sql = "UPDATE `course1`.`registration` SET `grade` = ? WHERE (`regisId` = ?);";
        update(sql, grade, id);
        endTransaction();
    }

    public void deleteClass(String id) throws RuntimeException {
        beginTransaction();
        simpleDelete("regisId", id);
        endTransaction();
    }

    @Override
    protected String getTableName() {
        return "registration";
    }
}
