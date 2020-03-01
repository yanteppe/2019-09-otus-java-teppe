package ru.otus.hibernate.orm.session_manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.otus.hibernate.core.session_manager.DatabaseSession;
import ru.otus.hibernate.core.session_manager.SessionManager;
import ru.otus.hibernate.core.session_manager.SessionManagerException;

public class SessionManagerORM implements SessionManager {
   private DatabaseSessionORM databaseSessionORM;
   private SessionFactory sessionFactory;

   public SessionManagerORM(SessionFactory sessionFactory) {
      if (sessionFactory == null) {
         throw new SessionManagerException("SessionFactory is null");
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
      checkSessionAndTransaction();
      try {
         databaseSessionORM.getTransaction().commit();
         databaseSessionORM.getORMSession().close();
      } catch (Exception exception) {
         throw new SessionManagerException(exception);
      }
   }

   @Override
   public void rollbackSession() {
      checkSessionAndTransaction();
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
      checkSessionAndTransaction();
      return databaseSessionORM;
   }

   private void checkSessionAndTransaction() {
      if (databaseSessionORM == null) {
         throw new SessionManagerException("DatabaseSession not opened ");
      }
      Session session = databaseSessionORM.getORMSession();
      if (session == null || !session.isConnected()) {
         throw new SessionManagerException("Session not opened ");
      }

      Transaction transaction = databaseSessionORM.getTransaction();
      if (transaction == null || !transaction.isActive()) {
         throw new SessionManagerException("Transaction not opened ");
      }
   }
}
