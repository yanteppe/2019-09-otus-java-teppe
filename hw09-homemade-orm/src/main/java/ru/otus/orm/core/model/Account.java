package ru.otus.orm.core.model;

import ru.otus.orm.core.Id;

public class Account {
    @Id
    int number;
    String type;
    int rest;

    public Account(int no, String type, int rest) {
        this.number = no;
        this.type = type;
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + number +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
