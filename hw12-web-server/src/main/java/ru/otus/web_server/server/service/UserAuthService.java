package ru.otus.web_server.server.service;

public class UserAuthService {
   private static final String ADMIN_LOGIN = "admin";
   private static final String ADMIN_PASSWORD = "admin";

   public boolean verifyAuthenticationData(String username, String password) {
      return (username.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD));
   }
}
