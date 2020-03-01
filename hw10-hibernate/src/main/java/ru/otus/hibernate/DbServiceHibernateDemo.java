package ru.otus.hibernate;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.hibernate.core.model.AddressDataSet;
import ru.otus.hibernate.core.model.PhoneDataSet;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.service.DbServiceUserImpl;
import ru.otus.hibernate.orm.dao.UserDaoORM;
import ru.otus.hibernate.orm.session_manager.SessionManagerORM;

import java.util.*;

public class DbServiceHibernateDemo {
   private static List<Class> annotatedClasses = Arrays.asList(User.class, AddressDataSet.class, PhoneDataSet.class);
   private static final String HIBERNATE_CONFIG_FILE = "hibernate.cfg.xml";
   private static Logger logger = LogManager.getLogger(DbServiceHibernateDemo.class);

   public static void main(String[] args) {
      // Preconditions
      var sessionFactory = getSessionFactory(HIBERNATE_CONFIG_FILE, annotatedClasses);
      var sessionManager = new SessionManagerORM(sessionFactory);
      var userDao = new UserDaoORM(sessionManager);
      var dbServiceUser = new DbServiceUserImpl(userDao);

      // Create User
      System.out.println("\nUSER DEMO:");
      var user = new User("User 1");
      var address = new AddressDataSet("Address 1");
      user.setAddress(address);
      user.setPhones(List.of(
            new PhoneDataSet("+799999999", user),
            new PhoneDataSet("+788888888", user)));
      logger.info("CREATE USER: " + user.toString());

      // Save User to database
      long userId = dbServiceUser.saveUser(user);
      logger.info("User is save to database");

      // Load User from database
      Optional<User> userFromDatabase = dbServiceUser.getUser(userId);
      System.out.println("User from database:" + userFromDatabase.toString());
   }

   public static SessionFactory getSessionFactory(String configFile, List<Class> annotatedClasses) {
      Configuration configuration = new Configuration().configure(configFile);
      MetadataSources metadataSources = new MetadataSources(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
      for (Class clazz : annotatedClasses) {
          metadataSources.addAnnotatedClass(clazz);
      }
      Metadata metadata = metadataSources.getMetadataBuilder().build();
      return metadata.getSessionFactoryBuilder().build();
   }
}
