package com.qiren.project.dao;

import com.qiren.project.pojo.Session;
import com.qiren.project.util.DateManager;

import java.util.Date;

public class SessionDAO extends AbstractDAO {

    public void createSession(Session session) throws RuntimeException {
        beginTransaction();
        simpleInsert(session.getSessionid(), session.getTimeout(),
                session.getUserId(), session.getType());
        endTransaction();
    }

    public void deleteSession() throws RuntimeException {
        beginTransaction();
        String sql = "delete from " + getTableName()
                + " where (timeout < '2100-01-01 00:00:00');";
        delete(sql);
        endTransaction();
    }

    public Session getSession() throws RuntimeException {
        beginTransaction();
        String sql = "select * from " + getTableName()
                + " where (timeout > '" + DateManager.getTimeFormatter().format(new Date()) + "');";
        Session session = query(sql, Session.class);
        endTransaction();
        return session;
    }

    @Override
    protected String getTableName() {
        return "session";
    }
}
