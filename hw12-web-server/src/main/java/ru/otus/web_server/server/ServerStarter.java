package ru.otus.web_server.server;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.web_server.repository.service.DBServiceUser;
import ru.otus.web_server.server.service.UserAuthService;
import ru.otus.web_server.server.servlet.AdminServlet;
import ru.otus.web_server.server.servlet.LoginServlet;
import ru.otus.web_server.server.servlet.filter.AuthorizationFilter;

public class ServerStarter {
   private int serverPort;
   private String staticResources;

   public ServerStarter(int port, String resourcesPath) {
      serverPort = port;
      staticResources = resourcesPath;
   }

   public void start(DBServiceUser dbServiceUser) {
      var resourceHandler = new ResourceHandler();
      resourceHandler.setBaseResource(Resource.newClassPathResource(staticResources));
      var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
      context.addServlet(new ServletHolder(new LoginServlet(new UserAuthService())), "/login");
      context.addServlet(new ServletHolder(new AdminServlet(dbServiceUser)), "/admin");
      context.addFilter(new FilterHolder(new AuthorizationFilter()), "/admin", null);

      var server = new org.eclipse.jetty.server.Server(serverPort);
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
