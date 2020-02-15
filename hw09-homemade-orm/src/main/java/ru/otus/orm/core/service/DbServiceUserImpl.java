package ru.otus.orm.core.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
                long userId = userDao.saveUser(user);
                sessionManager.commitSession();
                logger.debug("save user to DB with ID: " + userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
                logger.info("get user from DB with ID: " + userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
