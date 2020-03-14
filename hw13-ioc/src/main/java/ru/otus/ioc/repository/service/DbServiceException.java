package ru.otus.ioc.repository.service;

public class DbServiceException extends RuntimeException {

   public DbServiceException(Exception exception) {
      super(exception);
   }
}
