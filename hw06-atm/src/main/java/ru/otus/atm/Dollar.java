package ru.otus.atm;

public class Dollar extends Banknote {

    Dollar(int nominalValue) {
        super(nominalValue);
        super.setBanknoteType("Dollar");
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
