package ru.otus.hw;

import ru.otus.hw.atm.ATM;
import ru.otus.hw.nominals.DollarNominal;
import ru.otus.hw.nominals.RubleNominal;

/**
 * Simple ATM emulator demo
 */
public class ATMDemo {
    public static void main(String[] args) {
        // Create ATM
        ATM atm = new ATM();
        // Fold ruble banknotes in ATM
        for (int i = 0; i < RubleNominal.values().length; i++) {
            atm.acceptBanknote(new Ruble(RubleNominal.values()[i].getNominal()));
            atm.acceptBanknote(new Ruble(RubleNominal.values()[i].getNominal()));
            atm.acceptBanknote(new Ruble(RubleNominal.values()[i].getNominal()));
        }
        // Fold dollar banknotes in ATM
        for (int i = 0; i < DollarNominal.values().length; i++) {
            atm.acceptBanknote(new Dollar(DollarNominal.values()[i].getNominal()));
            atm.acceptBanknote(new Dollar(DollarNominal.values()[i].getNominal()));
            atm.acceptBanknote(new Dollar(DollarNominal.values()[i].getNominal()));
        }
        atm.printDisplayAccountState();
    }
}