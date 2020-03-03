package ru.otus.web_server.server.servlet;

import ru.otus.web_server.server.service.TemplateProcessor;
import ru.otus.web_server.server.service.UserAuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
   public static final String CONTENT_TYPE = "text/html;charset=utf-8";
   private static final String LOGIN_PAGE_TEMPLATE = "login.html";
   private static final String INVALID_AUTHENTICATION_MESSAGE = "Login or password is invalid";
   private TemplateProcessor templateProcessor;
   private UserAuthService userAuthService;

   public LoginServlet(UserAuthService userAuthService) {
      this.userAuthService = userAuthService;
      this.templateProcessor = new TemplateProcessor();
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Map<String, Object> variables = new HashMap<>();
      variables.put("message", "");
      response.setContentType(CONTENT_TYPE);
      response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, variables));
      response.setStatus(HttpServletResponse.SC_OK);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      if (userAuthService.verifyAuthenticationData(username, password)) {
         request.getSession();
         response.sendRedirect("/admin");
      } else {
         Map<String, Object> variables = new HashMap<>();
         variables.put("message", INVALID_AUTHENTICATION_MESSAGE);
         response.setContentType(CONTENT_TYPE);
         response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, variables));
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
   }
}
