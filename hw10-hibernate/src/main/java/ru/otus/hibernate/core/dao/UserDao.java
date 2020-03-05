package ru.otus.hibernate.core.dao;

import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.session_manager.SessionManager;

import java.util.Optional;

public interface UserDao {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> findById(long id);

    SessionManager getSessionManager();
}
