package ru.otus.hw.nominals;

public enum RubleNominal {
    RUB_50(50),
    RUB_100(100),
    RUB_500(500),
    RUB_1000(1000),
    RUB_5000(5000);
    private int nominal;

    RubleNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
