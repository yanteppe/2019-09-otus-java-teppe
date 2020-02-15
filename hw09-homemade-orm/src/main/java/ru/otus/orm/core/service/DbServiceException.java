package ru.otus.orm.core.service;

public class DbServiceException extends RuntimeException {

  public DbServiceException(Exception exception) {
    super(exception);
  }
}
