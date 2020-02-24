package ru.otus.orm.core.model;

import ru.otus.orm.core.Id;

import java.math.BigDecimal;

public class Account {
    @Id
    private long number;
    private String type;
    private BigDecimal rest;

    public Account() {
    }

    public Account(long number, String type, BigDecimal rest) {
        this.number = number;
        this.type = type;
        this.rest = rest;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
