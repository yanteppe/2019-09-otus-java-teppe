package ru.otus.web_server.core.dao;

public class UserDaoException extends RuntimeException {

   public UserDaoException(Exception exception) {
      super(exception);
   }
}
