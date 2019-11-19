package ru.otus.hw;

import ru.otus.hw.nominals.RubleNominal;

/**
 * Monetary unit
 */
public abstract class Banknote {
    private String banknoteType;
    private int nominalValue;

    Banknote(int nominalValue) {
        this.nominalValue = nominalValue;
    }

    Banknote(RubleNominal next) {
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
}
