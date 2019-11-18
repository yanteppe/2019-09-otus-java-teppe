package ru.otus.atm;

/**
 * Monetary unit
 */
public abstract class Banknote {
    private String banknoteName;
    private int nominalValue;

    Banknote(int nominalValue) {
        this.nominalValue = nominalValue;
    }

    public String getBanknoteName() {
        return banknoteName;
    }

    public void setBanknoteName(String banknoteName) {
        this.banknoteName = banknoteName;
    }

    public int getNominalValue() {
        return nominalValue;
    }

//    public void setNominalValue(int nominalValue) {
//        this.nominalValue = nominalValue;
//    }
}
