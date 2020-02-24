package ru.otus.orm.jdbc.dao;

public class JDBCTemplateException extends RuntimeException {

    public JDBCTemplateException(Exception exception) {
        super(exception);
    }
}
