package ru.otus.web_server.core.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.web_server.core.dao.UserDao;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.session_manager.SessionManager;

import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LogManager.getLogger(DbServiceUserImpl.class);
    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userRecordId = userDao.saveUser(user);
                sessionManager.commitSession();
                logger.info("Save User to DB with record ID: " + userRecordId);
                return userRecordId;
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
                throw new DbServiceException(exception);
            }
        }
    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.updateUser(user);
                sessionManager.commitSession();
                logger.info("Update User to: " + user.toString());
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
                throw new DbServiceException(exception);
            }
        }
    }

    @Override
    public Optional<User> getUserById(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
                logger.info("Get User from DB by ID: " + userOptional.orElse(null));
                return userOptional;
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByParameter(String parameter) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findUserByParameter(parameter);
                logger.info("Get User from DB by Login: " + userOptional.orElse(null));
                return userOptional;
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
