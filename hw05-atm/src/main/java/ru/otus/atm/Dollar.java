package ru.otus.atm;

public class Dollar extends Banknote {

    Dollar(int nominalValue) {
        super(nominalValue);
        super.setBanknoteName("Dollar");
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
