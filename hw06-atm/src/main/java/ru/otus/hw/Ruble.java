package ru.otus.hw;

import ru.otus.hw.nominals.RubleNominal;

public class Ruble extends Banknote {

    Ruble(int nominalValue) {
        super(nominalValue);
        super.setBanknoteType("Ruble");
    }

    Ruble(RubleNominal next) {
        super(next);
    }

    @Override
    public void setBanknoteType(String banknoteType) {
        super.setBanknoteType(banknoteType);
    }

    @Override
    public String toString() {
        return "Banknote: " + super.getBanknoteType() + ", nominal = " + super.getNominalValue();
    }
}
