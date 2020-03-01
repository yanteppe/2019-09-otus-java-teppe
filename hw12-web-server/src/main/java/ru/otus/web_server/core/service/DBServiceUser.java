package ru.otus.web_server.core.service;

import ru.otus.web_server.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    void updateUser(User user);

    Optional<User> getUserById(long id);

    Optional<User> getUserByParameter(String login);

    List<User> getAllUsers();
}
