package ru.otus.cache.core.service;

import ru.otus.cache.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);
}
