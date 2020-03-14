package ru.otus.ioc.repository.session_manager;

public interface SessionManager extends AutoCloseable {

   void beginSession();

   void commitSession();

   void rollbackSession();

   void close();

   DatabaseSession getCurrentSession();
}
