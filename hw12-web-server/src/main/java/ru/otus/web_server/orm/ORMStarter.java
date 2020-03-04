package ru.otus.web_server.orm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.web_server.core.service.DBServiceUser;
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

   public DBServiceUser start() {
      var sessionFactory = getSessionFactory(hibernateConfigFile, entities);
      var sessionManager = new SessionManagerORM(sessionFactory);
      var userDao = new UserDaoORM(sessionManager);
      return new DbServiceUserImpl(userDao);
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
