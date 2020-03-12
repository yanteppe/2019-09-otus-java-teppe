package ru.otus.ioc.repository.dao;

import ru.otus.ioc.repository.domain.User;
import ru.otus.ioc.repository.session_manager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

   long saveUser(User user);

   Optional<User> findById(long id);

   Optional<User> getUser(long id);

   List<User> getAllUsers();

   SessionManager getSessionManager();
}
