package ru.otus.ioc.repository.dao;

public class UserDaoException extends RuntimeException {

   public UserDaoException(Exception exception) {
      super(exception);
   }
}
