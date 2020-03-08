package ru.otus.web_server.repository.dao;

public class UserDaoException extends RuntimeException {

   public UserDaoException(Exception exception) {
      super(exception);
   }
}
