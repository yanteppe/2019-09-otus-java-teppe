package ru.otus.cache.core.dao;

import ru.otus.cache.core.model.User;
import ru.otus.cache.core.session_manager.SessionManager;

import java.util.Optional;

public interface UserDao {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> findById(long id);

    SessionManager getSessionManager();
}
