package ru.otus.hw;

import ru.otus.hw.atm.ATM;

public class ATMDemo {

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.acceptBanknotes(Ruble.RUB_50, 3);
        atm.acceptBanknotes(Ruble.RUB_100, 3);
        atm.acceptBanknotes(Ruble.RUB_200, 3);
        atm.acceptBanknotes(Ruble.RUB_500, 3);
        atm.acceptBanknotes(Ruble.RUB_1000, 3);
        atm.acceptBanknotes(Ruble.RUB_2000, 3);
        atm.acceptBanknotes(Ruble.RUB_5000, 3);
        atm.displayAccountStatus();
        atm.getBanknotes(850);
        atm.displayAccountStatus();
    }
}
