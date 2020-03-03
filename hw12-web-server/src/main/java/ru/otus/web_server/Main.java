package ru.otus.web_server;

import ru.otus.web_server.core.model.AddressDataSet;
import ru.otus.web_server.core.model.PhoneDataSet;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.orm.ORMStarter;
import ru.otus.web_server.server.ServerStarter;

import java.util.Arrays;
import java.util.List;

public class Main {
   private static final String HIBERNATE_CONFIG_FILE = "hibernate.cfg.xml";
   private static List<Class> annotatedClasses = Arrays.asList(User.class, AddressDataSet.class, PhoneDataSet.class);
   private static final int SERVER_PORT = 8989;
   private static final String STATIC_RESOURCES = "/static";

   public static void main(String[] args) {
      new ORMStarter(HIBERNATE_CONFIG_FILE, annotatedClasses).start();
      new ServerStarter(SERVER_PORT, STATIC_RESOURCES).start();
   }
}
