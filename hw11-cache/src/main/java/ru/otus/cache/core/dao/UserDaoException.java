package ru.otus.cache.core.dao;

public class UserDaoException extends RuntimeException {

   public UserDaoException(Exception exception) {
      super(exception);
   }
}
