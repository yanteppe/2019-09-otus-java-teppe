package ru.otus.hibernate.core.dao;

public class UserDaoException extends RuntimeException {

   public UserDaoException(Exception exception) {
      super(exception);
   }
}
