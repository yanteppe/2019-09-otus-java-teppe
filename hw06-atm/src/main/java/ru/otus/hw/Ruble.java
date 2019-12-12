package ru.otus.hw;

public enum Ruble {
    RUB_50(50),
    RUB_100(100),
    RUB_200(200),
    RUB_500(500),
    RUB_1000(1000),
    RUB_2000(2000),
    RUB_5000(5000),;

    int nominal;

    Ruble(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}