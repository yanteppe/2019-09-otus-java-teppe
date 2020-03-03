package ru.otus.web_server.core.service;

import ru.otus.web_server.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsers();
}
