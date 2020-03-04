package ru.otus.web_server;

import ru.otus.web_server.core.model.AddressDataSet;
import ru.otus.web_server.core.model.PhoneDataSet;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.orm.ORMStarter;
import ru.otus.web_server.server.ServerStarter;

import java.util.List;

/**
 * Start App
 * Start page: http://localhost:8989/index.html
 * Authentication - login: admin, password: admin
 */
public class Main {
   private static List<Class> entities = List.of(User.class, AddressDataSet.class, PhoneDataSet.class);

   public static void main(String[] args) {
      var dbServiceUser = new ORMStarter("hibernate.cfg.xml", entities).start();
      new ServerStarter(8989, "/static").start(dbServiceUser);
   }
}
