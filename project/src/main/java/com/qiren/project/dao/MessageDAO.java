package com.qiren.project.dao;

import com.qiren.project.pojo.Message;

import java.util.List;

public class MessageDAO extends AbstractDAO {

    public void sendMessage(Message message) {
        beginTransaction();
        simpleInsert(null, message.getTitle(), message.getContent(),
                message.getMessageFrom(), message.getMessageTo(),
                message.getTargetPage(), message.getTime(), message.getReplyId());
        endTransaction();
    }

    public List<Message> getUserMessage(String userId) {
        beginTransaction();
        String sql = "SELECT * FROM course1.message where messageTo = ? order by time desc;";
//        List<Message> messages =
//                (List<Message>) simpleQueryList("messageTo", userId, Message.class);
        List<Message> messages = (List<Message>) queryList(sql, Message.class, userId);
        endTransaction();
        return messages;
    }

    @Override
    protected String getTableName() {
        return "message";
    }
}
