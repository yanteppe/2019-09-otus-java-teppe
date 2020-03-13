package ru.otus.ioc.repository.service;

import ru.otus.ioc.repository.domain.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

   long saveUser(User user);

   Optional<User> getUser(long id);

   List<User> getUsers();
}
