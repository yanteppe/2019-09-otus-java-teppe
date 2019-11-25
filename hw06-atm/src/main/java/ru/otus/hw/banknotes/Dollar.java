package ru.otus.hw.banknotes;

public enum Dollar implements Banknote {
//    TYPE("Dollar"),
    DOLLAR_5(5),
    DOLLAR_10(10),
    DOLLAR_20(20),
    DOLLAR_50(50),
    DOLLAR_100(100);
    private int nominal;
    private String type;

    Dollar(int nominal) {
        this.nominal = nominal;
    }

    Dollar(String type) {
        this.type = type;
    }

//    @Override
//    public String getType() {
//        return type;
//    }

    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public Dollar[] getNominals() {
        return Dollar.values();
    }

    @Override
    public String toString() {
        return DOLLAR_5.nominal + ", "
                + DOLLAR_10.nominal + ", "
                + DOLLAR_20.nominal + ", "
                + DOLLAR_50.nominal + ", "
                + DOLLAR_100.nominal;
    }
}
