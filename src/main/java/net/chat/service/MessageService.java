package net.chat.service;

import net.chat.dao.MessageDAO;
import net.chat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sergey on 3/31/14.
 */
@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    @Transactional
    public void save(Message message) {
        messageDAO.save(message);
    }

    @Transactional
    public List<Message> getNotReadMessages(Long fromUserId, Long toUserId) {
        List<Message> messages = new ArrayList<Message>();
        messages = messageDAO.getNotReadMessages(fromUserId, toUserId);
        return messages;
    }

    @Transactional
    public void markMessagesAsRead(List<Long> ids) {
        messageDAO.markMessagesAsRead(ids);
    }

    public List<Map<Long, Long>> getCountMessages(Long currentUserId) {

        return null;
    }
}
