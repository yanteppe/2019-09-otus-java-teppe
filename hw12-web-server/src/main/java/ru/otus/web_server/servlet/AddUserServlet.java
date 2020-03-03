package ru.otus.web_server.servlet;

import ru.otus.web_server.core.service.DBServiceUser;
import ru.otus.web_server.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {
   private static final String USER_NAME = "name";
   private static final String USER_LOGIN = "login";
   private static final String USER_PASSWORD = "password";
   //private static final String PAGE_TEMPLATE = "users.html";
   private final TemplateProcessor templateProcessor;
   private final DBServiceUser dbServiceUser;

   public AddUserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
      this.templateProcessor = templateProcessor;
      this.dbServiceUser = dbServiceUser;
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String name = request.getParameter(USER_NAME);
      String login = request.getParameter(USER_LOGIN);
      String password = request.getParameter(USER_PASSWORD);
      User newUser = new User(name);
      dbServiceUser.saveUser(newUser);
      response.sendRedirect("/users");
//      if (dbServiceUser.findUserByLogin(login).isEmpty()) {
//         User newUser = new User(name);
//         dbServiceUser.saveUser(newUser);
//         response.sendRedirect("/users");
//      }
   }
}
