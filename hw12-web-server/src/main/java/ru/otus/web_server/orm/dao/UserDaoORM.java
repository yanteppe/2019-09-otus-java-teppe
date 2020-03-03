package ru.otus.web_server.orm.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import ru.otus.web_server.core.dao.UserDao;
import ru.otus.web_server.core.dao.UserDaoException;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.session_manager.SessionManager;
import ru.otus.web_server.orm.session_manager.DatabaseSessionORM;
import ru.otus.web_server.orm.session_manager.SessionManagerORM;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDaoORM implements UserDao {
   private final SessionManagerORM sessionManagerORM;
   private static Logger logger = LogManager.getLogger(UserDaoORM.class);

   public UserDaoORM(SessionManagerORM sessionManagerORM) {
      this.sessionManagerORM = sessionManagerORM;
   }

   @Override
   public long saveUser(User user) {
      DatabaseSessionORM currentSession = sessionManagerORM.getCurrentSession();
      try {
         Session session = currentSession.getORMSession();
         if (user.getId() > 0) {
            session.merge(user);
         } else {
            session.persist(user);
         }
         return user.getId();
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
         throw new UserDaoException(exception);
      }
   }

   @Override
   public Optional<User> findById(long id) {
      DatabaseSessionORM session = sessionManagerORM.getCurrentSession();
      try {
         return Optional.ofNullable(session.getORMSession().find(User.class, id));
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Optional.empty();
   }

   @Override
   public Optional<User> getUser(long id) {
      DatabaseSessionORM session = sessionManagerORM.getCurrentSession();
      try {
         return Optional.ofNullable(session.getORMSession().load(User.class, id));
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Optional.empty();
   }

   @Override
   public List<User> getAllUsers() {
      DatabaseSessionORM session = sessionManagerORM.getCurrentSession();
      try {
         return session.getORMSession().createCriteria(User.class).list();
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Collections.emptyList();
   }

   @Override
   public SessionManager getSessionManager() {
      return sessionManagerORM;
   }
}
