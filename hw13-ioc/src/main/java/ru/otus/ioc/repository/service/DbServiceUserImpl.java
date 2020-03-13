package ru.otus.ioc.repository.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.ioc.repository.dao.UserDao;
import ru.otus.ioc.repository.domain.User;
import ru.otus.ioc.repository.session_manager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DbServiceUserImpl implements DBServiceUser {
   private static Logger logger = LogManager.getLogger(DbServiceUserImpl.class);
   private final UserDao userDao;

   @Autowired
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
            logger.info("Save User to DB with record ID: {}", userRecordId);
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
            logger.info("Get User from DB by ID: {}", userOptional.orElse(null));
            return userOptional;
         } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
         }
         return Optional.empty();
      }
   }

   @Override
   public List<User> getUsers() {
      try (SessionManager sessionManager = userDao.getSessionManager()) {
         sessionManager.beginSession();
         try {
            return userDao.getAllUsers();
         } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
         }
         return Collections.emptyList();
      }
   }
}
