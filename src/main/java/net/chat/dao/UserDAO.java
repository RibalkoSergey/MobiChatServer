package net.chat.dao;

/**
 * Created by sergey on 2/3/14.
 */

import java.util.List;


import net.chat.entity.User;
import net.chat.entity.UserViewBean;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public User getUserByLogin(String login) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User user where user.login = '" + login + "'").uniqueResult();
    }

    public User getUserByLoginAndPassword(String login, String password) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User user where user.login = '" + login + "' and user.password = '" + password + "'")
                .uniqueResult();
    }

    public List<UserViewBean> getFriendsListByUser(Long userId) {
        String sql =
                "SELECT " +
                "  max(user.ID) AS id, " +
                "  max(user.FIRST_NAME) AS firstName, " +
                "  max(user.LAST_NAME) AS lastName, " +
                "  max(user.EMAIL) AS email, " +
                "  max(user.IS_ONLINE) AS online, " +
                "  max(user.PHOTO) AS photo, " +
                "  count(mess.MESSAGE) AS countNotReadMessages " +
                "FROM " +
                "  USER_FRIENDS_MAPP mapp " +
                "    JOIN USERS user ON mapp.FRIEND_ID = user.ID " +
                "    LEFT JOIN messages mess ON mapp.USER_ID = mess.TO_USER_ID and mapp.FRIEND_ID = mess.FROM_USER_ID and mess.IS_READ = 0 " +
                "WHERE mapp.USER_ID = :userId " +
                "group by user.ID, user.FIRST_NAME, user.LAST_NAME, user.EMAIL, user.IS_ONLINE, user.PHOTO ";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addScalar("id", LongType.INSTANCE);
        query.addScalar("firstName", StringType.INSTANCE);
        query.addScalar("lastName", StringType.INSTANCE);
        query.addScalar("email", StringType.INSTANCE);
        query.addScalar("online", BooleanType.INSTANCE);
        query.addScalar("photo", StringType.INSTANCE);
        query.addScalar("countNotReadMessages", LongType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(UserViewBean.class));
        query.setLong("userId", userId);

        return query.list() ;
    }

    public User getUserById(Long id) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User user where user.id = " + id).uniqueResult();
    }

    public List<UserViewBean> getFriendsListByName(String name) {
        String sql =
                "SELECT " +
                        "  user.ID AS id, " +
                        "  user.FIRST_NAME AS firstName, " +
                        "  user.LAST_NAME AS lastName, " +
                        "  user.EMAIL AS email, " +
                        "  user.IS_ONLINE AS online " +
                        "FROM " +
                        "  USERS user " +
                        "WHERE user.FIRST_NAME like '%" + name + "%' or user.LAST_NAME like '%" + name + "%' ";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addScalar("id", LongType.INSTANCE);
        query.addScalar("firstName", StringType.INSTANCE);
        query.addScalar("lastName", StringType.INSTANCE);
        query.addScalar("email", StringType.INSTANCE);
        query.addScalar("online", BooleanType.INSTANCE);
        //query.addScalar("photo", StringType.INSTANCE);
        //query.addScalar("countNotReadMessages", LongType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(UserViewBean.class));

        return query.list() ;

    }

    public void sendInvite(Long fromIdUser, Long toIdUser) {
        String sql = "INSERT INTO INVITES (FROM_USER_ID, TO_USER_ID) VALUES (:fromIdUser, :toIdUser) ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setLong("fromIdUser", fromIdUser);
        query.setLong("toIdUser", toIdUser);
        query.executeUpdate();
    }

    public List<UserViewBean> getInvite(Long currentUserId) {
        String sql =
                "SELECT " +
                        "  user.ID AS id, " +
                        "  user.FIRST_NAME AS firstName, " +
                        "  user.LAST_NAME AS lastName, " +
                        "  user.EMAIL AS email, " +
                        "  user.IS_ONLINE AS online " +
                        "FROM " +
                        "  INVITES inv " +
                        "  JOIN USERS user ON inv.FROM_USER_ID = user.ID " +
                        "WHERE inv.TO_USER_ID = :currentUserId";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addScalar("id", LongType.INSTANCE);
        query.addScalar("firstName", StringType.INSTANCE);
        query.addScalar("lastName", StringType.INSTANCE);
        query.addScalar("email", StringType.INSTANCE);
        query.addScalar("online", BooleanType.INSTANCE);
        //query.addScalar("photo", StringType.INSTANCE);
        //query.addScalar("countNotReadMessages", LongType.INSTANCE);
        query.setLong("currentUserId", currentUserId);

        query.setResultTransformer(Transformers.aliasToBean(UserViewBean.class));
        return query.list();
    }

    public void addFriend(Long currentUserId, Long friendId) {
        String sql = "INSERT INTO USER_FRIENDS_MAPP (USER_ID, FRIEND_ID) VALUES (:currentUserId, :friendId) ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setLong("currentUserId", currentUserId);
        query.setLong("friendId", friendId);
        query.executeUpdate();

        sql = "INSERT INTO USER_FRIENDS_MAPP (USER_ID, FRIEND_ID) VALUES (:friendId, :currentUserId) ";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setLong("currentUserId", currentUserId);
        query.setLong("friendId", friendId);
        query.executeUpdate();


    }

    public void deleteInvite(Long currentUserId, Long friendId) {
        String sql = "DELETE FROM INVITES WHERE FROM_USER_ID = :friendId and TO_USER_ID = :currentUserId ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setLong("currentUserId", currentUserId);
        query.setLong("friendId", friendId);
        query.executeUpdate();
    }
}
