package ru.otus.ioc.orm.session_manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.ioc.repository.session_manager.SessionManager;
import ru.otus.ioc.repository.session_manager.SessionManagerException;

@Component
public class SessionManagerORM implements SessionManager {
   private DatabaseSessionORM databaseSessionORM;
   private SessionFactory sessionFactory;

   @Autowired
   public SessionManagerORM(SessionFactory sessionFactory) {
      if (sessionFactory == null) {
         throw new SessionManagerException("SessionFactory - null");
      }
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void beginSession() {
      try {
         databaseSessionORM = new DatabaseSessionORM(sessionFactory.openSession());
      } catch (Exception exception) {
         throw new SessionManagerException(exception);
      }
   }

   @Override
   public void commitSession() {
      checkSession();
      try {
         databaseSessionORM.getTransaction().commit();
         databaseSessionORM.getORMSession().close();
      } catch (Exception exception) {
         throw new SessionManagerException(exception);
      }
   }

   @Override
   public void rollbackSession() {
      checkSession();
      try {
         databaseSessionORM.getTransaction().rollback();
         databaseSessionORM.getORMSession().close();
      } catch (Exception exception) {
         throw new SessionManagerException(exception);
      }
   }

   @Override
   public void close() {
      if (databaseSessionORM == null) {
         return;
      }
      Session session = databaseSessionORM.getORMSession();
      if (session == null || !session.isConnected()) {
         return;
      }
      Transaction transaction = databaseSessionORM.getTransaction();
      if (transaction == null || !transaction.isActive()) {
         return;
      }
      try {
         databaseSessionORM.closeSession();
         databaseSessionORM = null;
      } catch (Exception e) {
         throw new SessionManagerException(e);
      }
   }

   @Override
   public DatabaseSessionORM getCurrentSession() {
      checkSession();
      return databaseSessionORM;
   }

   private void checkSession() {
      if (databaseSessionORM == null) {
         throw new SessionManagerException("DatabaseSession is not open");
      }
      Session session = databaseSessionORM.getORMSession();
      if (session == null || !session.isConnected()) {
         throw new SessionManagerException("Session is not open");
      }
      checkTransaction();
   }

   private void checkTransaction() {
      var transaction = databaseSessionORM.getTransaction();
      if (transaction == null || !transaction.isActive()) {
         throw new SessionManagerException("Transaction is not open");
      }
   }
}
