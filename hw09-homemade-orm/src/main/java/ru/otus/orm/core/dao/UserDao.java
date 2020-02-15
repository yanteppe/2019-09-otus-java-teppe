package ru.otus.orm.core.dao;

import ru.otus.orm.core.model.User;
import ru.otus.orm.core.session_manager.SessionManager;

import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();
}
