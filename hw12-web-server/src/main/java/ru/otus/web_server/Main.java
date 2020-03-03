package ru.otus.web_server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import ru.otus.web_server.core.h2db.DataSourceH2DB;
import ru.otus.web_server.core.jdbc.DbExecutor;
import ru.otus.web_server.core.jdbc.dao.UserDaoJDBC;
import ru.otus.web_server.core.jdbc.session_manager.SessionManagerJdbc;
import ru.otus.web_server.core.service.DBServiceUser;
import ru.otus.web_server.core.service.DbServiceUserImpl;
import ru.otus.web_server.core.dao.UserDao;
import ru.otus.web_server.helpers.FileSystemHelper;
import ru.otus.web_server.server.UsersWebServer;
import ru.otus.web_server.server.UsersWebServerImpl;
import ru.otus.web_server.services.*;

import static ru.otus.web_server.server.SecurityType.FILTER_BASED;

public class Main {
   private static final int WEB_SERVER_PORT = 8989;
   private static final String TEMPLATES_DIR = "/templates/";
   private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
   private static final String REALM_NAME = "AnyRealm";

   public static void main(String[] args) throws Exception {
      String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
      var dataSource = new DataSourceH2DB();
      SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
//      new H2DBInit().initTables(dataSource);
      DbExecutor<User> userDbExecutor = new DbExecutor<>();
      UserDao userDao = new UserDaoJDBC(sessionManager, userDbExecutor);
      DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
      UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userDao);
      LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
//      LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userDao);

      Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
      TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

      UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
            FILTER_BASED,
            userAuthServiceForFilterBasedSecurity,
            loginServiceForBasicSecurity,
            userDao,
            gson,
            templateProcessor);

      usersWebServer.start();
      usersWebServer.join();
   }
}
