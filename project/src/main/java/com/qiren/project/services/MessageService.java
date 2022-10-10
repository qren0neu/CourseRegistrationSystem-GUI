package com.qiren.project.services;

import com.qiren.project.dao.DAOBuilder;
import com.qiren.project.dao.MessageDAO;
import com.qiren.project.pojo.Message;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.DateManager;
import com.qiren.project.util.ExceptionCenter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageService extends AbstractService {

    private MessageDAO messageDAO = DAOBuilder.getInstance().build(MessageDAO.class);
    private UserService userService = ServiceBuilder.getInstance().build(UserService.class);

    public void sendSystemMessage(JFrame frame, String title, String message, String target, String page) {
        sendMessage(frame, title, "system", message, target, page, null);
    }

    public void sendMessage(JFrame frame, String title, String message, String target, String page) {
        sendMessage(frame, title, userService.getUserName(), message, target, page, null);
    }

    public void sendMessage(JFrame frame, String title, String userId, String message, String target, String page, Message reply) {
        if (null == title || title.isBlank()) {
            CoreUtils.showErrorDialog(frame, "Cannot send empty title!");
        }
        if (null == message || message.isBlank()) {
            CoreUtils.showErrorDialog(frame, "Cannot send empty message!");
        }
        Message m = new Message();
        m.setMessageFrom(userId);
        m.setTitle(title);
        m.setContent(message);
        if (null != reply) {
            m.setReplyId(reply.getMessageId());
        }
        m.setTargetPage(page);
        m.setMessageTo(target);
        m.setTime(DateManager.getTimeFormatter().format(new Date()));
        messageDAO.sendMessage(m);
    }

    public List<Message> getMyMessage() {
        try {
            List<Message> messages = messageDAO.getUserMessage(userService.getUserName());
            return messages;
        } catch (Exception e) {
            ExceptionCenter.handleException(e);
        }
        return new ArrayList<>();
    }
}
