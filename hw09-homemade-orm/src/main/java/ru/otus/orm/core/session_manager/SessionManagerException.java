package ru.otus.orm.core.session_manager;


public class SessionManagerException extends RuntimeException {

    public SessionManagerException(String msg) {
        super(msg);
    }

    public SessionManagerException(Exception ex) {
        super(ex);
    }
}
