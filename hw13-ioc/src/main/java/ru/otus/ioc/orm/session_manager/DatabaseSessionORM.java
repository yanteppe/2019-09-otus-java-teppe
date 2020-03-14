package ru.otus.ioc.orm.session_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.ioc.repository.session_manager.DatabaseSession;

public class DatabaseSessionORM implements DatabaseSession {
   private final Session session;
   private final Transaction transaction;

   DatabaseSessionORM(Session session) {
      this.session = session;
      this.transaction = session.beginTransaction();
   }

   public Session getORMSession() {
      return session;
   }

   public void closeSession() {
      if (transaction.isActive()) {
         transaction.commit();
      }
      session.close();
   }

   public Transaction getTransaction() {
      return transaction;
   }
}
