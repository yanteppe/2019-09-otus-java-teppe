package ru.otus.atm;

/**
 * Simple ATM emulator demo
 */
public class ATMDemo {
    public static void main(String[] args) {
        Banknote ruble0 = new Ruble(0);
        Banknote ruble5 = new Ruble(5);
        Banknote ruble10 = new Ruble(10);
        Banknote ruble50 = new Ruble(50);
        Banknote ruble100 = new Ruble(100);
        Banknote dollar0 = new Dollar(0);
        Banknote dollar5 = new Dollar(5);
        Banknote dollar10 = new Dollar(10);
        Banknote dollar50 = new Dollar(50);
        Banknote dollar100 = new Dollar(100);
        ATM atm = new ATM();
        atm.acceptBanknote(ruble0);
        atm.acceptBanknote(ruble5);
        atm.acceptBanknote(ruble10);
        atm.acceptBanknote(ruble50);
        atm.acceptBanknote(ruble100);
        atm.acceptBanknote(dollar0);
        atm.acceptBanknote(dollar5);
        atm.acceptBanknote(dollar10);
        atm.acceptBanknote(dollar50);
        atm.acceptBanknote(dollar100);
        atm.printDisplayAccountStatus();
    }
}