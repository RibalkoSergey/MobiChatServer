package net.chat.service;

import net.chat.dao.UserDAO;
import net.chat.entity.User;
import net.chat.entity.UserViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Created by sergey on 2/3/14.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void registerUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    public User getUserBylogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Transactional
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public boolean isExistsUserByEmail(String email) {
        return false;
    }

    public boolean isExistsUserByLogin(String login) {
        return false;
    }

    @Transactional
    public User loginUser(String login, String password) {
        return userDAO.getUserByLoginAndPassword(login, password);
    }

    @Transactional
    public List<UserViewBean> getFriendsListByUser(Long userId) {
        return userDAO.getFriendsListByUser(userId);
    }

    @Transactional
    public List<UserViewBean> getFriendsListByName(String name) {
        return userDAO.getFriendsListByName(name);
    }

    @Transactional
    public void sendInvite(Long fromUserId, Long toUserId) {
        userDAO.sendInvite(fromUserId, toUserId);
    }

    @Transactional
    public List<UserViewBean> getInvite(Long currentUserId) {
        return userDAO.getInvite(currentUserId);
    }

    @Transactional
    public void addFriend(Long currentUserId, Long friendId) {
        userDAO.addFriend(currentUserId, friendId);
    }

    @Transactional
    public void deleteInvite(Long currentUserId, Long friendId) {
        userDAO.deleteInvite(currentUserId, friendId);
    }
}
