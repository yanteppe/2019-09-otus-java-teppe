package ru.otus.cache.core.service;

import ru.otus.cache.core.dao.UserDao;
import ru.otus.cache.core.model.User;
import ru.otus.cache.mycache.Cache;
import ru.otus.cache.mycache.CacheImpl;

import java.util.Optional;

public class DbUserCacheService extends DbServiceUserImpl {
   private final Cache<String, User> userCache;

   public DbUserCacheService(UserDao userDao) {
      super(userDao);
      this.userCache = new CacheImpl<>();
   }

   public DbUserCacheService(UserDao userDao, Cache<String, User> userCache) {
      super(userDao);
      this.userCache = userCache;
   }

   @Override
   public long saveUser(User user) {
      long id = super.saveUser(user);
      userCache.put(String.valueOf(user.getId()), user);
      return id;
   }

   @Override
   public Optional<User> getUser(long id) {
      return Optional.of(userCache.get(String.valueOf(id))).or(() -> super.getUser(id).map(userFromDb -> {
         userCache.put(String.valueOf(userFromDb.getId()), userFromDb);
         return userFromDb;
      }));
   }
}
