package se.svennesson.svennelist.service;

import se.svennesson.svennelist.dao.UserDao;
import se.svennesson.svennelist.model.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User createUser(final User user) {
        return userDao.saveItem(user);
    }

    public User getUserById(Long id) {
        return userDao.findById(id);
    }
}
