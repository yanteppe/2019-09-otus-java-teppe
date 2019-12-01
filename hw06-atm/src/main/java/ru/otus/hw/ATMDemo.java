package ru.otus.hw;

import ru.otus.hw.atm.ATM;

public class ATMDemo {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.acceptBanknotes(new Ruble(50), 3);
        atm.acceptBanknotes(new Ruble(100), 3);
        atm.acceptBanknotes(new Ruble(500), 3);
        atm.acceptBanknotes(new Ruble(1000), 3);
        atm.acceptBanknotes(new Ruble(2000), 3);
        atm.acceptBanknotes(new Ruble(5000), 3);
        atm.displayAccountStatus();
        atm.getBanknotes(1750);
        atm.displayAccountStatus();
    }
}
