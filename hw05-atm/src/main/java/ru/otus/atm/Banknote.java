package ru.otus.atm;

/**
 * Monetary unit
 */
public abstract class Banknote {
    private String banknoteType;
    private int nominalValue;

    Banknote(int nominalValue) {
        this.nominalValue = nominalValue;
    }

    public String getBanknoteType() {
        return banknoteType;
    }

    public void setBanknoteType(String banknoteType) {
        this.banknoteType = banknoteType;
    }

    public int getNominalValue() {
        return nominalValue;
    }

//    public void setNominalValue(int nominalValue) {
//        this.nominalValue = nominalValue;
//    }
}
