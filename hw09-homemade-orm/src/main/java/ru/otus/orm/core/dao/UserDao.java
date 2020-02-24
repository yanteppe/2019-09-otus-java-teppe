package ru.otus.orm.core.dao;

import ru.otus.orm.core.model.User;
import ru.otus.orm.core.session_manager.SessionManager;

import java.util.Optional;

public interface UserDao {

    long saveUser(User user);

    void updateUser(User user);

    Optional<User> findById(long id);

    SessionManager getSessionManager();
}
