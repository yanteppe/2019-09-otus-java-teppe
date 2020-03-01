package ru.otus.hibernate.orm.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.dao.UserDaoException;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.session_manager.SessionManager;
import ru.otus.hibernate.orm.session_manager.DatabaseSessionORM;
import ru.otus.hibernate.orm.session_manager.SessionManagerORM;

import java.util.Optional;

public class UserDaoORM implements UserDao {
   private final SessionManagerORM sessionManager;
   private static Logger logger = LogManager.getLogger(UserDaoORM.class);

   public UserDaoORM(SessionManagerORM sessionManager) {
      this.sessionManager = sessionManager;
   }

   @Override
   public long saveUser(User user) {
      DatabaseSessionORM currentSession = sessionManager.getCurrentSession();
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
      DatabaseSessionORM session = sessionManager.getCurrentSession();
      try {
         return Optional.ofNullable(session.getORMSession().find(User.class, id));
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Optional.empty();
   }

   @Override
   public Optional<User> loadUser(long id) {
      DatabaseSessionORM session = sessionManager.getCurrentSession();
      try {
         return Optional.ofNullable(session.getORMSession().load(User.class, id));
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Optional.empty();
   }

   @Override
   public SessionManager getSessionManager() {
      return sessionManager;
   }
}
