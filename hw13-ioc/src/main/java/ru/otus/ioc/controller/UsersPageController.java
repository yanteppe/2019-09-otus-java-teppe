package ru.otus.ioc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.ioc.repository.domain.User;
import ru.otus.ioc.repository.service.DbServiceUserImpl;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UsersPageController {
   private DbServiceUserImpl dbServiceUser;

   public UsersPageController(DbServiceUserImpl dbServiceUser) {
      this.dbServiceUser = dbServiceUser;
   }

   @GetMapping("/users")
   public String allUsers(Model model) {
      model.addAttribute("users", dbServiceUser.getUsers());
      model.addAttribute("user", new User());
      return "users-page";
   }

   @PostMapping("/create")
   public RedirectView createUser(@ModelAttribute("user") User user, HttpServletRequest request) {
      dbServiceUser.saveUser(user);
      return new RedirectView("/users", true);
   }
}
