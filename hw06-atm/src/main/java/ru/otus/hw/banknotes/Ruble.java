package ru.otus.hw.banknotes;

public enum Ruble implements Banknote {
    TYPE("Ruble"),
    RUB_50(50),
    RUB_100(100),
    RUB_500(500),
    RUB_1000(1000),
    RUB_5000(5000);
    private int nominal;
    private String type;

    Ruble(int nominal) {
        this.nominal = nominal;
    }

    Ruble(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public String toString() {
        return RUB_50.nominal + ", "
                + RUB_100.nominal + ", "
                + RUB_500.nominal + ", "
                + RUB_1000.nominal + ", "
                + RUB_5000.nominal;
    }
}
