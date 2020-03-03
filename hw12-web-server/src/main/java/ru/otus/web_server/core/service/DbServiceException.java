package ru.otus.web_server.core.service;

public class DbServiceException extends RuntimeException {

   public DbServiceException(Exception exception) {
      super(exception);
   }
}
