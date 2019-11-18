package ru.otus.atm;

public class Ruble extends Banknote {

    Ruble(int nominalValue) {
        super(nominalValue);
        super.setBanknoteName("Ruble");
    }

    @Override
    public void setBanknoteName(String banknoteName) {
        super.setBanknoteName(banknoteName);
    }

    @Override
    public String toString() {
        return "Banknote: " + super.getBanknoteName() + ", nominal = " + super.getNominalValue();
    }
}
