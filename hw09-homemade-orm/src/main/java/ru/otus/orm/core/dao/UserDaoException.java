package ru.otus.orm.core.dao;

public class UserDaoException extends RuntimeException {

    public UserDaoException(Exception exception) {
        super(exception);
    }
}
