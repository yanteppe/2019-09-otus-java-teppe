package ru.otus.hibernate.orm.dao;

import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.session_manager.SessionManager;

import java.util.Optional;

public class UserDaoORM implements UserDao {

   @Override
   public long saveUser(User user) {
      return 0;
   }

   @Override
   public Optional<User> findById(long id) {
      return Optional.empty();
   }

   @Override
   public Optional<User> loadUser(long id) {
      return Optional.empty();
   }

   @Override
   public SessionManager getSessionManager() {
      return null;
   }
}
