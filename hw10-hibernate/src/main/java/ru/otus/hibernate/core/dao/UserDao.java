package ru.otus.hibernate.core.dao;

import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.session_manager.SessionManager;

import java.util.Optional;

public interface UserDao {

    long saveUser(User user);

//    void updateUser(User user);

    Optional<User> findById(long id);

    Optional<User> loadUser(long id);

    SessionManager getSessionManager();
}
