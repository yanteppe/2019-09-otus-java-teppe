package ru.otus.cache.core.session_manager;

public class SessionManagerException extends RuntimeException {

   public SessionManagerException(String message) {
      super(message);
   }

   public SessionManagerException(Exception exception) {
      super(exception);
   }
}
