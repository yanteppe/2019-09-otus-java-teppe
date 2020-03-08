package ru.otus.web_server.server.servlet;

import ru.otus.web_server.core.model.AddressDataSet;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.service.DBServiceUser;
import ru.otus.web_server.server.service.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
   private final TemplateProcessor templateProcessor;
   private DBServiceUser dbServiceUser;
   private static final String CONTENT_TYPE = "text/html;charset=utf-8";
   private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
   private static final String CREATE_USER = "create";
   private static final String USERS_LIST = "users";

   public AdminServlet(DBServiceUser dbServiceUser) {
      this.dbServiceUser = dbServiceUser;
      this.templateProcessor = new TemplateProcessor();
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Map<String, Object> variables = new HashMap<>();
      variables.put(USERS_LIST, Collections.emptyList());
      variables.put(CREATE_USER, Collections.emptyList());
      response.setContentType(CONTENT_TYPE);
      response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, variables));
      response.setStatus(HttpServletResponse.SC_OK);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      if (request.getParameter(CREATE_USER) != null) {
         String userName = request.getParameter("userName");
         String address = request.getParameter("userAddress");
         var savedUser = new User(userName);
         savedUser.setAddress(new AddressDataSet(address));
         dbServiceUser.saveUser(savedUser);
         Map<String, Object> variables = new HashMap<>();
         variables.put(CREATE_USER, Collections.singletonList(savedUser));
         variables.put(USERS_LIST, Collections.emptyList());
         response.setContentType(CONTENT_TYPE);
         response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, variables));
         response.setStatus(HttpServletResponse.SC_OK);
      } else if (request.getParameter("getUsers") != null) {
         Map<String, Object> variables = new HashMap<>();
         variables.put(CREATE_USER, Collections.emptyList());
         variables.put(USERS_LIST, dbServiceUser.getUsers());
         response.setContentType(CONTENT_TYPE);
         response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, variables));
         response.setStatus(HttpServletResponse.SC_OK);
      } else if (request.getParameter("exitAdmin") != null) {
         request.getSession().invalidate();
         response.sendRedirect("/login");
      }
   }
}
