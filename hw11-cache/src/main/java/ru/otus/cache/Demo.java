package ru.otus.cache;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.cache.core.model.AddressDataSet;
import ru.otus.cache.core.model.PhoneDataSet;
import ru.otus.cache.core.model.User;
import ru.otus.cache.core.service.DbServiceUserImpl;
import ru.otus.cache.core.service.DbUserCacheService;
import ru.otus.cache.mycache.Cache;
import ru.otus.cache.mycache.CacheImpl;
import ru.otus.cache.orm.dao.UserDaoORM;
import ru.otus.cache.orm.session_manager.SessionManagerORM;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Demo {
   private static List<Class<?>> entities = Arrays.asList(User.class, AddressDataSet.class, PhoneDataSet.class);
   private static final String HIBERNATE_CONFIG_FILE = "hibernate.cfg.xml";

   public static void main(String[] args) {
      // Preconditions
      var sessionFactory = getSessionFactory(HIBERNATE_CONFIG_FILE, entities);
      var sessionManager = new SessionManagerORM(sessionFactory);
      var userDao = new UserDaoORM(sessionManager);
      Cache<String, User> cache = new CacheImpl<>();

      // Create User
      System.out.println("\n------- USER DEMO -------");
      var user = new User("User 1");
      var address = new AddressDataSet("Address 1");
      user.setAddress(address);
      user.setPhones(List.of(
            new PhoneDataSet("+799999999", user),
            new PhoneDataSet("+788888888", user)));
      System.out.println("\nCREATE USER:");
      System.out.println(user.toString() + "\n");

      // Save User to database and cache
      System.out.println("USER IS SAVE IN DATABASE AND CACHED:");
      var dbUserCacheService = new DbUserCacheService(userDao, cache);
      long userId = dbUserCacheService.saveUser(user);

      // Load User from cache
      long startTimeLoadFromCache = System.currentTimeMillis();
      Optional<User> userFromCache = getUserFromCache(dbUserCacheService, userId);
      long endTimeLoadFromCache = System.currentTimeMillis();
      long loadFromResultResult = endTimeLoadFromCache - startTimeLoadFromCache;
      System.out.println("\nGET USER FROM CACHE:");
      System.out.println(userFromCache.toString() + "\n");

      // Load User from database
      var dbServiceUser = new DbServiceUserImpl(userDao);
      long startTimeLoadFromDatabase = System.currentTimeMillis();
      Optional<User> userFromDatabase = getUserFromDatabase(userId, dbServiceUser);
      long endTimeLoadFromDatabase = System.currentTimeMillis();
      long loadFromDatabaseResult = endTimeLoadFromDatabase - startTimeLoadFromDatabase;
      System.out.println("\nGET USER FROM DATABASE:");
      System.out.println(userFromDatabase.toString() + "\n");

      System.out.println("RUNTIME RESULT:");
      System.out.println("LOAD FROM CACHE TIME: " + loadFromResultResult + " ms");
      System.out.println("LOAD FROM DATABASE TIME : " + loadFromDatabaseResult + " ms");
   }

   private static Optional<User> getUserFromDatabase(long userId, DbServiceUserImpl dbServiceUser) {
      return dbServiceUser.getUser(userId);
   }

   private static Optional<User> getUserFromCache(DbUserCacheService dbUserCacheService, long userId) {
      return dbUserCacheService.getUser(userId);
   }

   public static SessionFactory getSessionFactory(String configFile, List<Class<?>> entityClasses) {
      Configuration configuration = new Configuration().configure(configFile);
      MetadataSources metadataSources = new MetadataSources(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
      for (Class<?> clazz : entityClasses) {
         metadataSources.addAnnotatedClass(clazz);
      }
      Metadata metadata = metadataSources.getMetadataBuilder().build();
      return metadata.getSessionFactoryBuilder().build();
   }
}