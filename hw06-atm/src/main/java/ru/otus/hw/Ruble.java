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

    public static int[] getNominals() {
        return new int[]{
                RUB_5000.getNominal(),
                RUB_2000.getNominal(),
                RUB_1000.getNominal(),
                RUB_500.getNominal(),
                RUB_200.getNominal(),
                RUB_100.getNominal(),
                RUB_50.getNominal()
            };
    }
}