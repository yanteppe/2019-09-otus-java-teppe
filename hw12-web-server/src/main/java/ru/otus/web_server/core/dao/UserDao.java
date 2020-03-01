package ru.otus.web_server.core.dao;

import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.session_manager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    long saveUser(User user);

    void updateUser(User user);

    Optional<User> findById(long id);

    Optional<User> findUserByParameter(String login);

    List<User> getAllUsers();

    SessionManager getSessionManager();
}
