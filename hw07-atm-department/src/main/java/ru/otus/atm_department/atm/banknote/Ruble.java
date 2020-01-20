package ru.otus.atm_department.atm.banknote;

public enum Ruble {
    RUB_5000(5000),
    RUB_2000(2000),
    RUB_1000(1000),
    RUB_500(500),
    RUB_200(200),
    RUB_100(100),
    RUB_50(50);

    private int nominal;

    Ruble(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}