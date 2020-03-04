package ru.otus.hibernate.core.model;

import javax.persistence.*;

@Entity
public class PhoneDataSet {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String number;
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id",nullable = false)
   private User user;

   public PhoneDataSet() {
   }

   public PhoneDataSet(String number) {
      this.number = number;
   }

   public PhoneDataSet(String number, User user) {
      this.number = number;
      this.user = user;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   @Override
   public String toString() {
      return "PhoneDataSet{" +
            "id=" + id +
            ", number='" + number + '\'' +
            '}';
   }
}
