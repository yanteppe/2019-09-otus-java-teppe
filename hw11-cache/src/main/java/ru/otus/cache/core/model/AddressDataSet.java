package ru.otus.cache.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AddressDataSet {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String street;

   public AddressDataSet() {
   }

   public AddressDataSet(String street) {
      this.street = street;
   }

   public String getStreet() {
      return street;
   }

   public void setStreet(String street) {
      this.street = street;
   }

   @Override
   public String toString() {
      return "AddressDataSet{" +
            "id=" + id +
            ", street='" + street + '\'' +
            '}';
   }
}
