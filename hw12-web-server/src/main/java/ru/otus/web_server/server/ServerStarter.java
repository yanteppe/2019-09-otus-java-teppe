package ru.otus.web_server.server;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.web_server.core.service.DbServiceUserImpl;
import ru.otus.web_server.server.service.UserAuthService;
import ru.otus.web_server.server.servlet.AdminServlet;
import ru.otus.web_server.server.servlet.LoginServlet;
import ru.otus.web_server.server.servlet.filter.AuthorizationFilter;

import java.io.IOException;

public class ServerStarter {
   private int SERVER_PORT;
   private String STATIC_RESOURCES;
   private static final UserAuthService USER_AUTHENTICATION_SERVICE = new UserAuthService();
   private DbServiceUserImpl dbServiceUser;

   public ServerStarter(int port, String resourcesPath) {
      SERVER_PORT = port;
      STATIC_RESOURCES = resourcesPath;
   }

   public void start() {
      var resourceHandler = new ResourceHandler();
      resourceHandler.setBaseResource(Resource.newClassPathResource(STATIC_RESOURCES));
      var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
      context.addServlet(new ServletHolder(new LoginServlet(USER_AUTHENTICATION_SERVICE)), "/login");
      try {
         context.addServlet(new ServletHolder(new AdminServlet(dbServiceUser)), "/admin");
      } catch (IOException exception) {
         exception.printStackTrace();
      }
      context.addFilter(new FilterHolder(new AuthorizationFilter()), "/admin", null);
      var server = new org.eclipse.jetty.server.Server(SERVER_PORT);
      server.setHandler(new HandlerList(resourceHandler, context));
      try {
         server.start();
      } catch (Exception exception) {
         exception.printStackTrace();
      }
      try {
         server.join();
      } catch (InterruptedException interruptedException) {
         interruptedException.printStackTrace();
      }
   }
}
