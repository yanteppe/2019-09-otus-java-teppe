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

//   public static void main(String[] args) throws Exception {
//      String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
//      var dataSource = new DataSourceH2DB();
//      SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
////      new H2DBInit().initTables(dataSource);
//      DbExecutor<User> userDbExecutor = new DbExecutor<>();
//      UserDao userDao = new UserDaoJDBC(sessionManager, userDbExecutor);
//      DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
//      UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthService(userDao);
//      LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
////      LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userDao);
//
//      Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
//      TemplateProcessor templateProcessor = new TemplateProcessor(TEMPLATES_DIR);
//
//      UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
//            FILTER_BASED,
//            userAuthServiceForFilterBasedSecurity,
//            loginServiceForBasicSecurity,
//            userDao,
//            gson,
//            templateProcessor);
//
//      usersWebServer.start();
//      usersWebServer.join();
//   }
}
