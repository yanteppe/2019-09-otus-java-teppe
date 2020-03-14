package ru.otus.ioc.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySources(value = {@PropertySource("classpath:app.properties")})
public class ORMConfig {
   @Value("${hibernate.connection.driver_class}")
   private String driverConnection;
   @Value("${hibernate.connection.url}")
   private String urlConnection;
   @Value("${hibernate.connection.username}")
   private String username;
   @Value("${hibernate.connection.password}")
   private String password;

   @Bean
   public DataSource dataSource() {
      var dataSource = new BasicDataSource();
      dataSource.setDriverClassName(driverConnection);
      dataSource.setUrl(urlConnection);
      dataSource.setUsername(username);
      dataSource.setPassword(password);
      return dataSource;
   }

   private Properties ormProperties() {
      var ormProperties = new Properties();
      ormProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
      ormProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
      ormProperties.setProperty("hibernate.hbm2ddl.charset_name", "UTF-8");
      ormProperties.setProperty("hibernate.connection.characterEncoding", "UTF-8");
      ormProperties.setProperty("hibernate.connection.useUnicode", "UTF-8");
      ormProperties.setProperty("hibernate.enable_lazy_load_no_trans", "false");
      ormProperties.setProperty("hibernate.show_sql", "true");
      return ormProperties;
   }

   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      var sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(dataSource());
      sessionFactory.setPackagesToScan("ru.otus.ioc.repository.domain");
      sessionFactory.setHibernateProperties(ormProperties());
      return sessionFactory;
   }

   @Bean
   public PlatformTransactionManager hibernateTransactionManager() {
      var transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(sessionFactory().getObject());
      return transactionManager;
   }

   @Bean
   public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
      return new PropertySourcesPlaceholderConfigurer();
   }
}
