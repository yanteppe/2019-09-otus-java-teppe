package ru.otus.hibernate.core.service;

public class DbServiceException extends RuntimeException {

  public DbServiceException(Exception exception) {
    super(exception);
  }
}
