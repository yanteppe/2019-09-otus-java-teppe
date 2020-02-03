package ru.otus.sandbox;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private int age;
    private List<String> geoLocation = new ArrayList<>();
    private Book book;

    public Person(String name, int age, List<String> geoLocation, Book book) {
        this.name = name;
        this.age = age;
        this.geoLocation = geoLocation;
        this.book = book;
    }
}
