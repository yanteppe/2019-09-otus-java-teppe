package ru.otus.web_server.servlet;

import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.service.DBServiceUser;
import ru.otus.web_server.core.dao.UserDao;
import ru.otus.web_server.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersServlet extends HttpServlet {
   private static final String USERS_PAGE_TEMPLATE = "users.html";
   private static final String USERS_TEMPLATE = "user";
   private final UserDao userDao;
//   private final DBServiceUser dbServiceUser;
   private final TemplateProcessor templateProcessor;

   public UsersServlet(TemplateProcessor templateProcessor, UserDao userDao) {
      this.templateProcessor = templateProcessor;
      this.userDao = userDao;
   }

//   public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
//      this.templateProcessor = templateProcessor;
//      this.dbServiceUser = dbServiceUser;
//   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse response) {
      Map<String, Object> parameters = new HashMap<>();
      List<User> usersList = userDao.getAllUsers();
      parameters.put(USERS_TEMPLATE, usersList);
      response.setContentType("text/html");
      try {
         response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, parameters));
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }
   }
}
