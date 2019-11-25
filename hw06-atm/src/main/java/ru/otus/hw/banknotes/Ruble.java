package ru.otus.hw.banknotes;

public enum Ruble implements Banknote {
    RUB_50(50),
    RUB_100(100),
    RUB_500(500),
    RUB_1000(1000),
    RUB_5000(5000);
    private int nominal;

    Ruble(int nominal) {
        this.nominal = nominal;
    }

//    Ruble(String type) {
//        this.type = type;
//    }
//
//    @Override
//    public String getType() {
//        return "Ruble";
//    }

    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public Ruble[] getNominals() {
        return Ruble.values();
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
