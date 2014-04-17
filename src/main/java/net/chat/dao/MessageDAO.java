package net.chat.dao;

import net.chat.entity.Message;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by sergey on 3/31/14.
 */
@Repository
public class MessageDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(Message message) {
        sessionFactory.getCurrentSession().save(message);
    }

    public List<Message> getNotReadMessages(Long fromUserId, Long toUserId) {
        String sql =
                "SELECT " +
                  "mess.ID AS id, " +
                  "mess.FROM_USER_ID AS fromUserId, " +
                  "userFrom.FIRST_NAME AS fromUserName, " +
                  "mess.TO_USER_ID AS toUserId, " +
                  "userTo.FIRST_NAME AS toUserName, " +
                  "mess.MESSAGE AS message, " +
                  "mess.DATE_MESSAGE AS dateMessage, " +
                  "mess.IS_READ AS isRead " +
                "FROM " +
                  "MESSAGES mess " +
                  " LEFT JOIN USERS userFrom ON mess.FROM_USER_ID = userFrom.ID " +
                  " LEFT JOIN USERS userTo ON mess.TO_USER_ID = userTo.ID " +
                "WHERE mess.FROM_USER_ID = :fromUserId AND mess.TO_USER_ID = :toUserId AND mess.IS_READ = 0";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addScalar("id", LongType.INSTANCE);
        query.addScalar("fromUserId", LongType.INSTANCE);
        query.addScalar("fromUserName", StringType.INSTANCE);
        query.addScalar("toUserId", LongType.INSTANCE);
        query.addScalar("toUserName", StringType.INSTANCE);
        query.addScalar("message", StringType.INSTANCE);
        query.addScalar("dateMessage", DateType.INSTANCE);
        query.addScalar("isRead", BooleanType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Message.class));
        query.setLong("fromUserId", fromUserId);
        query.setLong("toUserId", toUserId);

        return query.list() ;
    }

    public void markMessagesAsRead(List<Long> ids) {
        String sql =
                "UPDATE MESSAGES SET IS_READ = 1 WHERE ID IN (:ids) ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
    }
}
