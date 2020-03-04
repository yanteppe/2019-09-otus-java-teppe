package ru.otus.web_server;

import ru.otus.web_server.core.model.AddressDataSet;
import ru.otus.web_server.core.model.PhoneDataSet;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.orm.ORMStarter;
import ru.otus.web_server.server.ServerStarter;

import java.util.Arrays;
import java.util.List;

/**
 * Start App<br>
 * Authentication - login: admin, password: admin
 */
public class Main {
   private static List<Class> entities = List.of(User.class, AddressDataSet.class, PhoneDataSet.class);

   public static void main(String[] args) {
      new ORMStarter("hibernate.cfg.xml", entities).start();
      new ServerStarter(8989, "/static").start();
   }
}
