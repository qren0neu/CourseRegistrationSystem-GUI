package com.qiren.project.services;

import com.qiren.project.dao.DAOBuilder;
import com.qiren.project.dao.SessionDAO;
import com.qiren.project.pojo.Session;
import com.qiren.project.util.DateManager;
import com.qiren.project.util.ExceptionCenter;

import java.util.Date;
import java.util.UUID;

public class LoginService extends AbstractService {

    private SessionDAO sessionDAO = DAOBuilder.getInstance().build(SessionDAO.class);

    public boolean checkIfHaveLoginSession() {
        try {
            Session session = sessionDAO.getSession();
            if (null != session) {
                UserService userService = ServiceBuilder.getInstance().build(UserService.class);
                userService.saveUserById(session.getUserId(), session.getType());
                return true;
            }
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return false;
    }

    public void createSession(int userId, String type) {
        Session session = new Session();
        session.setSessionid(UUID.randomUUID().toString());
        session.setTimeout(DateManager.getTimeFormatter()
                .format(new Date(System.currentTimeMillis() + 60 * 60 * 1000)));
        session.setUserId(userId + "");
        session.setType(type);
        try {
            sessionDAO.deleteSession();
            sessionDAO.createSession(session);
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
    }

    public boolean staffLogin(String email, String password) {
        UserService userService = ServiceBuilder.getInstance().build(UserService.class);
        if (userService.staffLogin(email, password)) {
            createSession(userService.getUserId(), UserService.TYPE_STAFF);
            return true;
        }
        return false;
    }

    public boolean studentLogin(String email, String password) {
        UserService userService = ServiceBuilder.getInstance().build(UserService.class);
        if (userService.studentLogin(email, password)) {
            createSession(userService.getUserId(), UserService.TYPE_STUDENT);
            return true;
        }
        return false;
    }
}
