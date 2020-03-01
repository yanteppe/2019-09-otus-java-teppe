package ru.otus.web_server.core.jdbc.dao;

public class JDBCTemplateException extends RuntimeException {

    public JDBCTemplateException(Exception exception) {
        super(exception);
    }
}
