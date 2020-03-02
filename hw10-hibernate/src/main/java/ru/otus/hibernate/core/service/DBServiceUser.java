package ru.otus.hibernate.core.service;

import ru.otus.hibernate.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    //void updateUser(User user);

    Optional<User> getUser(long id);
}
