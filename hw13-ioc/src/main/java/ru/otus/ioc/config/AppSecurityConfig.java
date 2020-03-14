package ru.otus.ioc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
   private static final String ADMIN_ROLE = "ADMIN";
   private static final String ADMIN_LOGIN = "admin";
   private static final String ADMIN_PASSWORD = "admin";
   private static final String USER_ROLE = "USER";
   private static final String USER_LOGIN = "user";
   private static final String USER_PASSWORD = "user";

   @Override
   protected void configure(AuthenticationManagerBuilder authenticationBuilder) throws Exception {
      var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
      authenticationBuilder.inMemoryAuthentication()
            .withUser(ADMIN_LOGIN).password(encoder.encode(ADMIN_PASSWORD)).roles(ADMIN_ROLE)
            .and()
            .withUser(USER_LOGIN).password(encoder.encode(USER_PASSWORD)).roles(USER_ROLE);
   }

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      var filter = new CharacterEncodingFilter();
      filter.setEncoding("UTF-8");
      httpSecurity.addFilterBefore(filter, CsrfFilter.class);
      filter.setForceEncoding(true);
      httpSecurity.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(HttpMethod.GET, "/static/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/authentication")
            .permitAll()
            .and()
            .logout().permitAll();
      httpSecurity.formLogin().defaultSuccessUrl("/users", true);
   }

   @Override
   public void configure(WebSecurity webSecurity) {
      webSecurity.ignoring().antMatchers("/");
   }
}






