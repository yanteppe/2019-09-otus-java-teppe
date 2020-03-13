package ru.otus.ioc.repository.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter@Setter @ToString @NoArgsConstructor
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String name;
   private String surname;
   private int age;
   private String city;

   public User(String name, String surname, int age, String city) {
      this.name = name;
      this.surname = surname;
      this.age = age;
      this.city = city;
   }
}