package se.svennesson.svennelist.service;

import se.svennesson.svennelist.dao.UserDao;
import se.svennesson.svennelist.exception.EntityNotFoundException;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.util.PBKDF2Hash;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User createUser(final User user) {
        final User userWithEncryptedPassword = new User(user.getName(), user.getEmail(), PBKDF2Hash.createHash(user.getPassword()), user.getAge(), user.getRole());
        return userDao.saveItem(userWithEncryptedPassword);
    }

    public Optional<User> getUserById(final Long id) {
        return userDao.findById(id);

    }

    Optional<User> getUserByEmail(final String email) {
        return userDao.findByEmail(email);
    }
}
