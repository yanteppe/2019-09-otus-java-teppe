package ru.otus.web_server.core.dao;

import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.session_manager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

   long saveUser(User user);

   Optional<User> findById(long id);

   Optional<User> getUser(long id);

   List<User> getAllUsers();

   SessionManager getSessionManager();
}
