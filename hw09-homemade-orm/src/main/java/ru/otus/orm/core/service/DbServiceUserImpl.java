package ru.otus.orm.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.orm.core.dao.UserDao;
import ru.otus.orm.core.model.User;
import ru.otus.orm.core.session_manager.SessionManager;

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
                logger.info("Save User to DB with record ID: {}" + userRecordId);
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
}
