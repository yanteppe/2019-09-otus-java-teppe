package ru.otus.hibernate.core.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.session_manager.SessionManager;

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
    public Optional<User> getUser(long id) {
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
    public Optional<User> loadUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.loadUser(id);

                logger.info("Load User: " + userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
