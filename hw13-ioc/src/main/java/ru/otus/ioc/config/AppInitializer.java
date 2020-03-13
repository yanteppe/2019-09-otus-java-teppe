package ru.otus.ioc.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class<?>[]{ORMConfig.class, AppSecurityConfig.class};
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class<?>[]{AppConfig.class};
   }

   @Override
   protected String[] getServletMappings() {
      return new String[]{"/"};
   }

   @Override
   protected Filter[] getServletFilters() {
      var characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      characterEncodingFilter.setForceEncoding(true);
      return new Filter[]{characterEncodingFilter};
   }
}
