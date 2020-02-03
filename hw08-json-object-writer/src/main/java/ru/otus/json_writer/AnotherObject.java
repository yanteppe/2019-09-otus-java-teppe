package ru.otus.json_writer;

import java.util.Random;

public class AnotherObject {
    private int id = new Random().nextInt(99);
    private String name;

    public AnotherObject(String name) {
        this.name = name;
    }
}
