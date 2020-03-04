package ru.otus.web_server.orm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.web_server.core.model.AddressDataSet;
import ru.otus.web_server.core.model.PhoneDataSet;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.service.DbServiceUserImpl;
import ru.otus.web_server.orm.dao.UserDaoORM;
import ru.otus.web_server.orm.session_manager.SessionManagerORM;

import java.util.List;

public class ORMStarter {
   private String hibernateConfigFile;
   private List<Class> entities;

   public ORMStarter(String hibernateConfigFile, List<Class> entities) {
      this.hibernateConfigFile = hibernateConfigFile;
      this.entities = entities;
   }

   public void start() {
      SessionFactory sessionFactory = getSessionFactory(hibernateConfigFile, entities);
      var sessionManager = new SessionManagerORM(sessionFactory);
      var userDao = new UserDaoORM(sessionManager);
      var dbServiceUser = new DbServiceUserImpl(userDao);
      // Create User
      var user = new User("User 1");
      user.setAddress(new AddressDataSet("Address 1"));
      user.setPhones(List.of(
            new PhoneDataSet("+799999999", user),
            new PhoneDataSet("+788888888", user)));
      // Save User to database
      dbServiceUser.saveUser(user);
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
