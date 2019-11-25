package ru.otus.hw;

import ru.otus.hw.atm.ATM;
import ru.otus.hw.banknotes.Dollar;
import ru.otus.hw.banknotes.Ruble;

/**
 * Simple ATM emulator demo
 */
public class ATMDemo {
    public static void main(String[] args) {
        // Create ATM
        ATM atm = new ATM();
        // Fold ruble banknotes in ATM: 5 banknotes of each face value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Ruble.values().length; j++) {
                atm.acceptBanknote(Ruble.values()[j]);
            }
        }
        // Fold dollar banknotes in ATM: 5 banknotes of each face value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Dollar.values().length; j++) {
                atm.acceptBanknote(Dollar.values()[j]);
            }
        }
        // Print account state
        atm.printAccountState();

        // Get rubles
//        atm.getBanknotes("ruble", 5650);
//        atm.printAccountState();
        // Get dollars
        atm.getBanknotes("dollar", 500);
        atm.printAccountState();
    }
}