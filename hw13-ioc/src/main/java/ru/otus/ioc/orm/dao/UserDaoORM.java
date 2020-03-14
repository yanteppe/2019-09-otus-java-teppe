package ru.otus.ioc.orm.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.otus.ioc.orm.session_manager.DatabaseSessionORM;
import ru.otus.ioc.orm.session_manager.SessionManagerORM;
import ru.otus.ioc.repository.dao.UserDao;
import ru.otus.ioc.repository.dao.UserDaoException;
import ru.otus.ioc.repository.domain.User;
import ru.otus.ioc.repository.session_manager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoORM implements UserDao {
   private final SessionManagerORM sessionManagerORM;
   private static Logger logger = LogManager.getLogger(UserDaoORM.class);

   public UserDaoORM(SessionManagerORM sessionManagerORM) {
      this.sessionManagerORM = sessionManagerORM;
   }

   @Override
   public long saveUser(User user) {
      var currentSession = sessionManagerORM.getCurrentSession();
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
      var session = sessionManagerORM.getCurrentSession();
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
      var session = sessionManagerORM.getCurrentSession();
      try {
         return session.getORMSession().createQuery("SELECT user FROM User user", User.class).list();
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
