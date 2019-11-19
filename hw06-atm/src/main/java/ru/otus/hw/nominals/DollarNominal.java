package ru.otus.hw.nominals;

public enum DollarNominal {
    DOLLAR_5(5),
    DOLLAR_10(10),
    DOLLAR_20(20),
    DOLLAR_50(50),
    DOLLAR_100(100);
    private int nominal;

    DollarNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
